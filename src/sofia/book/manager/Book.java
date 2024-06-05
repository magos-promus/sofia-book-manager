package sofia.book.manager;

public class Book {
    private int id;
    private String title;
    private int year;
    private String genre;
    private String author;
    private int pages;

    // Constructor
    public Book(int id, String title, int year, String genre, String author, int pages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.author = author;
        this.pages = pages;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
