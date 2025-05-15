create database usermanagement_test;

create database usermanagement;

use usermanagement;

use usermanagement_test;

create table users(
	user_id bigint primary key auto_increment,
    username varchar(50) unique,
    email varchar(50) unique,
    birth_date Date
);

INSERT INTO users (username, email, birth_date) VALUES
('john_doe', 'john.doe@example.com', '1990-05-15'),
('jane_smith', 'jane.smith@example.com', '1985-08-22'),
('mike_jones', 'mike.jones@example.com', '1995-11-03'),
('sarah_williams', 'sarah.williams@example.com', '1988-04-30'),
('david_brown', 'david.brown@example.com', '1992-07-19'),
('emily_davis', 'emily.davis@example.com', '1998-02-14'),
('robert_miller', 'robert.miller@example.com', '1983-09-25'),
('lisa_taylor', 'lisa.taylor@example.com', '1991-12-08'),
('thomas_wilson', 'thomas.wilson@example.com', '1987-06-17'),
('amanda_moore', 'amanda.moore@example.com', '1994-03-21');