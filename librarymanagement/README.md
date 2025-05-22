# Library Management System

A Spring Boot RESTful API for managing books, authors, and their relationships in a library.

---

## Table of Contents

- [Features](#features)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Authors](#authors)
  - [Books](#books)
  - [Books & Authors](#books--authors)
- [Sample Requests](#sample-requests)

---

## Features

- Add, update, delete, and fetch books and authors
- Link books and authors (many-to-many)
- Fetch books with their authors and vice versa

---

## Setup

1. **Clone the repository**
    ```sh
    git clone <your-repo-url>
    cd librarymanagement
    ```

2. **Configure the database**
    - Edit `src/main/resources/application.properties` and set your MySQL credentials:
      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/librarymanagement
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. **Build the project**
    ```sh
    ./mvnw clean install
    ```
    or on Windows:
    ```sh
    mvnw.cmd clean install
    ```

---

## Running the Application

Start the Spring Boot application:

```sh
./mvnw spring-boot:run
```
or
```sh
mvnw.cmd spring-boot:run
```

The API will be available at:  
`http://localhost:8080/library`

---

## API Endpoints

### Authors

| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/library/author`       | Add a new author           |
| GET    | `/library/authors`      | List all authors           |
| GET    | `/library/authors/{id}` | Get author by ID           |
| PUT    | `/library/authors/{id}` | Update author by ID        |
| DELETE | `/library/authors/{id}` | Delete author by ID        |

---

### Books

| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/library/book`         | Add a new book             |
| GET    | `/library/books`        | List all books             |
| GET    | `/library/books/{id}`   | Get book by ID             |
| PUT    | `/library/books/{id}`   | Update book by ID          |
| DELETE | `/library/books/{id}`   | Delete book by ID          |

---

### Books & Authors

| Method | Endpoint                                                        | Description                                 |
|--------|-----------------------------------------------------------------|---------------------------------------------|
| POST   | `/library/bookAndAuthor`                                        | Add a book and an author together           |
| POST   | `/library/bookAndAuthor/{id}`                                   | Add an author to an existing book           |
| POST   | `/library/bookAndAuthor/book/{id1}/author/{id2}`                | Link existing book and author by IDs        |
| DELETE | `/library/removeAuthorFromBook/book/{id1}/author/{id2}`         | Remove an author from a book                |
| GET    | `/library/bookWithAuthors/{id}`                                 | Get a book with its authors (DTO)           |
| GET    | `/library/booksWithAuthors`                                     | Get all books with their authors (DTO), prefered      |
| GET    | `/library/booksInfo`                                            | Get all books and authors (custom DTO)      |
| GET    | `/library/books/title?title={title}`                            | Search books by title                       |
| GET    | `/library/books/author?author={author}`                         | Search books by author name                 |

---

## Sample Requests

### Add a New Author

```http
POST /library/author
Content-Type: application/json

{
  "name": "J.K. Rowling",
  "nationality": "British",
  "birth": "1965-07-31"
}
```

---

### Add a New Book

```http
POST /library/book
Content-Type: application/json

{
  "title": "Harry Potter and the Philosopher's Stone",
  "isbn": 9780747532699,
  "publish_date": "1997-06-26",
  "price": 20.0,
  "genre": "Fantasy"
}
```

---

### Add a Book and Author Together

```http
POST /library/bookAndAuthor
Content-Type: application/json

{
  "author": {
    "name": "J.R.R. Tolkien",
    "nationality": "British",
    "birth": "1892-01-03"
  },
  "book": {
    "title": "The Hobbit",
    "isbn": 9780547928227,
    "publish_date": "1937-09-21",
    "price": 15.0,
    "genre": "Fantasy"
  }
}
```

---

### Link Existing Author to Existing Book

```http
POST /library/bookAndAuthor/book/1/author/2
```

---

### Remove Author from Book

```http
DELETE /library/removeAuthorFromBook/book/1/author/2
```

---

### Get Book with Authors (DTO)

```http
GET /library/bookWithAuthors/1
```

---

## Notes

- All dates should be in `YYYY-MM-DD` format.
- For more endpoints, see the controller code or use tools like Postman to explore.

---