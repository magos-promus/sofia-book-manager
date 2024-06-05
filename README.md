# Sofia Book Manager

Example for GitHub

## Roadmap

- [ ] Migrate from MariaDB to SQLite
- [ ] Migrate from Java to C++ / Qt
- [ ] Separate `sofia-book-manager` into `libsofia` and `sofiaqt`

## Current Database Structure

```sql
CREATE DATABASE sofiadb;

USE sofiadb;

CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    year INT,
    genre VARCHAR(50),
    author VARCHAR(50),
    pages INT
);
```

```sql
INSERT INTO books (title, year, genre, author, pages) VALUES
('The Great Gatsby', 1925, 'Classic', 'F. Scott Fitzgerald', 180),
('To Kill a Mockingbird', 1960, 'Fiction', 'Harper Lee', 324),
('1984', 1949, 'Science Fiction', 'George Orwell', 328),
('Pride and Prejudice', 1813, 'Romance', 'Jane Austen', 432),
('The Catcher in the Rye', 1951, 'Fiction', 'J.D. Salinger', 224),
('Harry Potter and the Sorcerer''s Stone', 1997, 'Fantasy', 'J.K. Rowling', 320),
('The Hobbit', 1937, 'Fantasy', 'J.R.R. Tolkien', 310),
('Moby-Dick', 1851, 'Adventure', 'Herman Melville', 624),
('Frankenstein', 1818, 'Horror', 'Mary Shelley', 280),
('The Lord of the Rings', 1954, 'Fantasy', 'J.R.R. Tolkien', 1178),
('Alice''s Adventures in Wonderland', 1865, 'Fantasy', 'Lewis Carroll', 272),
('The Da Vinci Code', 2003, 'Thriller', 'Dan Brown', 454),
('The Grapes of Wrath', 1939, 'Fiction', 'John Steinbeck', 464),
('Brave New World', 1932, 'Science Fiction', 'Aldous Huxley', 288),
('War and Peace', 1869, 'Historical Fiction', 'Leo Tolstoy', 1225),
('The Adventures of Huckleberry Finn', 1884, 'Adventure', 'Mark Twain', 366),
('The Picture of Dorian Gray', 1890, 'Gothic Fiction', 'Oscar Wilde', 254),
('Dracula', 1897, 'Gothic Horror', 'Bram Stoker', 418),
('The Hunger Games', 2008, 'Science Fiction', 'Suzanne Collins', 374),
('The Odyssey', -800, 'Epic', 'Homer', 475);
```
