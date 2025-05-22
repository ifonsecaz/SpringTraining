drop database librarymanagement;

create database librarymanagement;

use librarymanagement;

create table author(
	author_id BIGINT primary key auto_increment,
    name varchar(50),
    nationality varchar(50),
    birth Date
);

create table book(
	book_id BIGINT primary key auto_increment,
    title varchar(100),
    isbn BIGINT unique not null,
    publish_date Date,
    price decimal(4,2),
    genre varchar(50)
);

create table authorsBooks(
	author_id BIGINT,
    book_id BIGINT,
    primary key (book_id,author_id),
    foreign key (author_id) references author (author_id),
    foreign key (book_id) references book (book_id)
);

INSERT INTO author (name, nationality, birth) VALUES
('J.K. Rowling', 'British', '1965-07-31'),
('George R.R. Martin', 'American', '1948-09-20'),
('Haruki Murakami', 'Japanese', '1949-01-12'),
('Chimamanda Ngozi Adichie', 'Nigerian', '1977-09-15'),
('Gabriel García Márquez', 'Colombian', '1927-03-06'),
('Margaret Atwood', 'Canadian', '1939-11-18');

INSERT INTO book (title, isbn, publish_date, price, genre) VALUES
('Harry Potter and the Philosopher''s Stone', 9780747532743, '1997-06-26', 12.99, 'Fantasy'),
('A Game of Thrones', 9780553103540, '1996-08-01', 15.99, 'Fantasy'),
('Norwegian Wood', 9780375704024, '1987-09-04', 10.99, 'Literary Fiction'),
('Americanah', 9780307271082, '2013-05-14', 14.50, 'Literary Fiction'),
('One Hundred Years of Solitude', 9780060883287, '1967-05-30', 11.25, 'Magical Realism'),
('The Handmaid''s Tale', 9780385490818, '1985-08-01', 13.75, 'Dystopian'),
('Harry Potter and the Chamber of Secrets', 9780747538493, '1998-07-02', 12.99, 'Fantasy'),
('Kafka on the Shore', 9781400079278, '2002-09-12', 9.99, 'Magical Realism');

INSERT INTO authorsBooks (author_id, book_id) VALUES
(1, 1),
(1, 7),
(2, 2),
(3, 3),
(3, 8),
(4, 4),
(5, 5),
(6, 6);

INSERT INTO book (title, isbn, publish_date, price, genre) VALUES
('Good Omens', 9780552137034, '1990-05-10', 14.99, 'Fantasy Comedy');

INSERT INTO author (name, nationality, birth) VALUES
('Terry Pratchett', 'British', '1948-04-28'),
('Neil Gaiman', 'British', '1960-11-10');

INSERT INTO authorsBooks (author_id, book_id) VALUES
(7, 9), 
(8, 9);

select * from book;

select * from author;

select * from authorsbooks;

SELECT author.*, book.* FROM book INNER JOIN authorsbooks ON authorsbooks.book_id = book.book_id INNER JOIN author ON authorsbooks.author_id = author.author_id;

SELECT * FROM book INNER JOIN authorsbooks ON authorsbooks.book_id = book.book_id INNER JOIN author ON authorsbooks.author_id = author.author_id WHERE book.book_id=2;

SHOW COLUMNS FROM book;