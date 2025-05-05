K-pop Store Backend

## Project Description

This project is the backend for an online store specializing in the sale of K-pop albums. It implements core features such as user registration, order processing, payment handling, and review submission.

## Project Structure

The project is organized into several packages, each responsible for different aspects of functionality:

* **bookstrap**: Contains the initial setup and configuration of the application, including configurations for starting and setting up the backend.
* **config**: Includes configurations for security, database connections, and other external services.
* **controllers**: Controllers that handle HTTP requests and manage data flow between the client and the server.
* **entities**: Entities representing objects stored in the database, such as users, orders, albums, payments, and reviews.
* **repositories**: Interfaces for interacting with the database using Spring Data JPA.
* **dto (Data Transfer Objects)**: Objects designed for transferring data between application layers. They are used for simplifying communication with the client.
* **exceptions**: Custom exceptions and error handling throughout the application.
* **service**: Services that implement the business logic of the application, such as handling orders, managing users, and processing payments.
* **mappers**: Package for mapping between entities and DTOs, converting data for transfer between application layers.

## Entities

* **User**: Represents a user of the store. Each user has a username, email, password, and role (e.g., customer or admin).
* **Order**: A user's order, which includes a list of ordered albums, total price, and status.
* **OrderItem**: An item in an order, representing an album and its quantity.
* **Payment**: A payment associated with an order, including the payment method and status.
* **Review**: A review left by a user for a purchased album, containing a comment and a rating.

## Usage

Build and run the application:

```bash
mvn spring-boot:run
```

## 🔐 Authentication & Authorization

The backend uses **JWT (JSON Web Tokens)** for authenticating and authorizing users. Two types of tokens are issued upon successful login:

* **Access Token**: A short-lived token (e.g., 15 minutes) used to access protected endpoints.
* **Refresh Token**: A long-lived token (e.g., 7 days) used to obtain a new access token without requiring the user to log in again. This token is stored securely in the database.

### Authentication Flows

#### 1. **Traditional Login (Username & Password)**

* Endpoint: `POST /auth/login`
* Request Body:

  ```json
  {
    "username": "your_username",
    "password": "your_password"
  }
  ```
* Response:

  ```json
  {
    "accessToken": "JWT_ACCESS_TOKEN",
    "refreshToken": "JWT_REFRESH_TOKEN"
  }
  ```

#### 2. **Registration**

* Endpoint: `POST /auth/register`
* Request Body:

  ```json
  {
    "username": "new_user",
    "email": "user@example.com",
    "password": "securepassword"
  }
  ```
* Users are automatically assigned the role `USER`.

#### 3. **OAuth2 Login**

* OAuth2 providers like Google, GitHub, and Facebook are supported.
* The OAuth2 login is handled via Spring Security with a custom `CustomOAuth2UserService`.

### Token Handling

* The **access token** is required for accessing secured endpoints.
* The **refresh token** is used at `/auth/refresh` (endpoint to be implemented) to generate a new access token when the old one expires.
* The refresh tokens are stored in the database and linked to the authenticated user.

### Security Configuration

* Certain endpoints like `/auth/**` and `/oauth2/**` are publicly accessible.
* All other routes require a valid JWT access token.
* Passwords are securely hashed using `BCryptPasswordEncoder`.
* Stateless session management is enforced using `SessionCreationPolicy.STATELESS`.
