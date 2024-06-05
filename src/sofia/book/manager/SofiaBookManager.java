package sofia.book.manager;

import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class manages the book collection.
 */
public class SofiaBookManager extends JFrame {

    private static Connection connection;

    private String idString = "ID: ";
    private String titleString = "Title: ";
    private String yearString = "Year: ";
    private String genreString = "Genre: ";
    private String authorString = "Author: ";
    private String pagesString = "Pages: ";
    private String searchString = "Search";
    private String addString = "Add";
    private String removeString = "Remove (by ID)";
    private String updateString = "Update (by ID)";
    private String exportString = "Export";
    private String exitString = "Exit";
    private String errorString = "There was an error: ";

    // JTextField
    private JTextField idField;
    private JTextField titleField;
    private JTextField yearField;
    private JTextField genreField;
    private JTextField authorField;
    private JTextField pagesField;

    // JButton
    private JButton searchButton = new JButton(searchString);
    private JButton addButton = new JButton(addString);
    private JButton removeButton = new JButton(removeString);
    private JButton updateButton = new JButton(updateString);
    private JButton exportButton = new JButton(exportString);
    private JButton exitButton = new JButton(exitString);

    // JTextArea
    private JTextArea resultsArea = new JTextArea();

    /**
     * Constructor to initialize the SofiaBookManager GUI.
     */
    public SofiaBookManager() {
        setTitle("Book Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setLocationRelativeTo(null);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel panelFields = new JPanel();
        Border panelFieldsBorder = BorderFactory.createTitledBorder("Book Information");
        panelFields.setBorder(panelFieldsBorder);
        panelFields.setLayout(gridbag);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;

        panelFields.add(new JLabel(idString));
        idField = new JTextField();
        gridbag.setConstraints(idField, constraints);
        panelFields.add(idField);

        panelFields.add(new JLabel(titleString));
        titleField = new JTextField();
        gridbag.setConstraints(titleField, constraints);
        panelFields.add(titleField);

        panelFields.add(new JLabel(yearString));
        yearField = new JTextField();
        gridbag.setConstraints(yearField, constraints);
        panelFields.add(yearField);

        panelFields.add(new JLabel(genreString));
        genreField = new JTextField();
        gridbag.setConstraints(genreField, constraints);
        panelFields.add(genreField);

        panelFields.add(new JLabel(authorString));
        authorField = new JTextField();
        gridbag.setConstraints(authorField, constraints);
        panelFields.add(authorField);

        panelFields.add(new JLabel(pagesString));
        pagesField = new JTextField();
        gridbag.setConstraints(pagesField, constraints);
        panelFields.add(pagesField);

        JPanel panelButtons = new JPanel();
        TitledBorder panelButtonsBorder = BorderFactory.createTitledBorder("Operations");
        panelButtons.setBorder(panelButtonsBorder);
        panelButtons.setLayout(gridbag);
        constraints.gridwidth = 1;

        searchButton.addActionListener(e -> search());
        gridbag.setConstraints(searchButton, constraints);
        panelButtons.add(searchButton);

        addButton.addActionListener(e -> add());
        gridbag.setConstraints(addButton, constraints);
        panelButtons.add(addButton);

        removeButton.addActionListener(e -> remove());
        gridbag.setConstraints(removeButton, constraints);
        panelButtons.add(removeButton);

        updateButton.addActionListener(e -> update());
        gridbag.setConstraints(updateButton, constraints);
        panelButtons.add(updateButton);

        exportButton.addActionListener(e -> export());
        gridbag.setConstraints(exportButton, constraints);
        panelButtons.add(exportButton);

        exitButton.setForeground(Color.red);
        exitButton.addActionListener(e -> System.exit(0));
        gridbag.setConstraints(exitButton, constraints);
        panelButtons.add(exitButton);

        JScrollPane scrollpane = new JScrollPane(resultsArea);
        Border scrollpaneBorder = BorderFactory.createTitledBorder("Results");
        scrollpane.setBorder(scrollpaneBorder);

        getContentPane().add(panelFields, BorderLayout.NORTH);
        getContentPane().add(panelButtons, BorderLayout.SOUTH);
        getContentPane().add(scrollpane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    /**
     * Searches for books based on all fields.
     */
    private void search() {
        try {
            String sql = "SELECT id, title, year, genre, author, pages FROM books WHERE " +
                    "id LIKE ? OR title LIKE ? OR year LIKE ? OR genre LIKE ? OR author LIKE ? OR pages LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + idField.getText() + "%");
                statement.setString(2, "%" + titleField.getText() + "%");
                statement.setString(3, "%" + yearField.getText() + "%");
                statement.setString(4, "%" + genreField.getText() + "%");
                statement.setString(5, "%" + authorField.getText() + "%");
                statement.setString(6, "%" + pagesField.getText() + "%");

                ResultSet rs = statement.executeQuery();
                StringBuilder sb = new StringBuilder();

                while (rs.next()) {
                    sb.append(formatResult(rs));
                }
                resultsArea.setText(sb.toString());
            }
        } catch (SQLException e) {
            resultsArea.setText(errorString + e.getMessage());
        }
    }

    /**
     * Adds a new book to the database.
     */
    private void add() {
        try {
            String sql = "INSERT INTO books (title, year, genre, author, pages) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, titleField.getText());
                statement.setInt(2, Integer.parseInt(yearField.getText()));
                statement.setString(3, genreField.getText());
                statement.setString(4, authorField.getText());
                statement.setInt(5, Integer.parseInt(pagesField.getText()));
                statement.executeUpdate();
                resultsArea.setText("Book added successfully");
            }
        } catch (SQLException e) {
            resultsArea.setText(errorString + e.getMessage());
        } catch (NumberFormatException e) {
            resultsArea.setText("Incorrect format for year/pages");
        }
    }

    /**
     * Removes a book from the database by ID.
     */
    private void remove() {
        try {
            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idField.getText());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    resultsArea.setText("Book removed successfully");
                } else {
                    resultsArea.setText("No book found with the given ID");
                }
            }
        } catch (SQLException e) {
            resultsArea.setText(errorString + e.getMessage());
        }
    }

    /**
     * Updates the information of a book by ID.
     */
    private void update() {
        try {
            String sql = "UPDATE books SET title = ?, year = ?, genre = ?, author = ?, pages = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, titleField.getText());
                statement.setInt(2, Integer.parseInt(yearField.getText()));
                statement.setString(3, genreField.getText());
                statement.setString(4, authorField.getText());
                statement.setInt(5, Integer.parseInt(pagesField.getText()));
                statement.setInt(6, Integer.parseInt(idField.getText()));
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    resultsArea.setText("Book updated successfully");
                } else {
                    resultsArea.setText("No book found with the given ID");
                }
            }
        } catch (SQLException e) {
            resultsArea.setText(errorString + e.getMessage());
        } catch (NumberFormatException e) {
            resultsArea.setText("Incorrect format for year/pages");
        }
    }

    /**
     * Exports the book list to a file.
     */
    private void export() {
        try {
            String sql = "SELECT id, title, year, genre, author, pages FROM books";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                StringBuilder sb = new StringBuilder();

                while (rs.next()) {
                    sb.append(formatResult(rs));
                }
                writeFile(sb);
                resultsArea.setText("Exported successfully\n" + sb.toString());
            }
        } catch (SQLException e) {
            resultsArea.setText(errorString + e.getMessage());
        }
    }

    /**
     * Writes the book list to a file.
     *
     * @param sb The string builder containing the book list.
     */
    private void writeFile(CharSequence sb) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("books.txt")))) {
            out.append(sb);
            resultsArea.setText("Book list exported successfully");
        } catch (IOException e) {
            resultsArea.setText(errorString + e.getMessage());
        }
    }

    /**
     * Formats the result of a book query.
     *
     * @param rs The ResultSet containing the book data.
     * @return The formatted string of the book data.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private String formatResult(ResultSet rs) throws SQLException {
        return idString + rs.getString("id") + " - " +
                titleString + rs.getString("title") + " - " +
                yearString + rs.getString("year") + " - " +
                genreString + rs.getString("genre") + " - " +
                authorString + rs.getString("author") + " - " +
                pagesString + rs.getString("pages") + "\n";
    }

    /**
     * Main method to start the application.
     *
     * @param args Command line arguments (none implemented yet).
     */
    public static void main(String[] args) {
        JPasswordField pf = new JPasswordField();
        Object[] message = { "Password:", pf };
        int option = JOptionPane.showConfirmDialog(null, message, "Authentication", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            try {
                connection = DatabaseConnection.getConnection(password);
                SwingUtilities.invokeLater(SofiaBookManager::new);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } else {
            System.exit(0);
        }
    }
}
