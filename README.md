# K-pop Store Backend

## Project Description

This project is the backend for an online store specializing in the sale of K-pop albums. It implements core features such as user registration, order processing, payment handling, and review submission.

## Project Structure

The project is organized into several packages, each responsible for different aspects of functionality:

- **`bookstrap`**: Contains the initial setup and configuration of the application, including configurations for starting and setting up the backend.
- **`config`**: Includes configurations for security, database connections, and other external services.
- **`controllers`**: Controllers that handle HTTP requests and manage data flow between the client and the server.
- **`entities`**: Entities representing objects stored in the database, such as users, orders, albums, payments, and reviews.
- **`repositories`**: Interfaces for interacting with the database using Spring Data JPA.
- **`dto`** (Data Transfer Objects): Objects designed for transferring data between application layers. They are used for simplifying communication with the client.
- **`exceptions`**: Custom exceptions and error handling throughout the application.
- **`service`**: Services that implement the business logic of the application, such as handling orders, managing users, and processing payments.
- **`mappers`**: Package for mapping between entities and DTOs, converting data for transfer between application layers.

## Entities

- **User**: Represents a user of the store. Each user has a username, email, password, and role (e.g., customer or admin).
- **Order**: A user's order, which includes a list of ordered albums, total price, and status.
- **OrderItem**: An item in an order, representing an album and its quantity.
- **Payment**: A payment associated with an order, including the payment method and status.
- **Review**: A review left by a user for a purchased album, containing a comment and a rating.

## Usage

Build and run the application:

mvn spring-boot:run


