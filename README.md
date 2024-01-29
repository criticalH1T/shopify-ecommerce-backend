# Ecommerce Backend

## Overview
Welcome to our e-commerce platform, a comprehensive solution for online shopping that combines a user-friendly interface with robust functionality. This project is designed to emulate the seamless experience of popular e-commerce websites while offering additional features for both users and administrators.

## Configuration

This project requires Java 17 and PostgreSQL 16.1.

**Step 1:** Clone the project:

```bash
git clone https://github.com/criticalH1T/shopify-ecommerce-backend
```

**Step 2:** Navigate to the demo directory to find the `ecommerce_db.sql` file:

```bash
cd shopify-ecommerce-backend/demo
```

**Step 3:** Create database:

```bash
psql -U [your-username] -h localhost -p 5432 -f ecommerce-db.sql
```

This should create the `ecommerce` database and populate it with some mock data.

**Please make sure to update your username and password in the `application.yml` file.**

To do this you need to edit the following code in application.yml:

    url: jdbc:postgresql://localhost:5432/ecommerce
    username: [your-username]
    password: [your-password]

**Step 4:** Build and run:

```bash
./gradlew build
```

```bash
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## Endpoints

The API is documented using OpenAPI (Swagger). After you've run the application,
you can access the OpenAPI documentation on the following link:

[OpenAPI Documentation](http://localhost:8080/swagger-ui/index.html#/)

**Important note:**

As of now, OpenAPI still has no support for authentication using http-only cookies,
so if you want to access secured endpoints, please make sure to hit the `/authenticate` endpoint first.
The credentials for it are given in its description.


## Authentication

We have implemented a secure authentication mechanism using JSON Web Tokens (JWT) and HTTP-only cookies. This approach ensures a robust and stateless authentication process for our backend services.

### JSON Web Tokens (JWT)

We leverage JWT as a token-based authentication system. When a user successfully logs in or authenticates, a JWT is generated on the server and sent back to the client. This JWT contains encoded information about the user, such as user ID and roles. Subsequent requests from the client include this JWT in the Authorization header, allowing the server to verify the user's identity without the need for session storage on the server side.

### HTTP-only Cookies

To enhance security and mitigate certain types of attacks, we utilize HTTP-only cookies to store the JWT on the client side. HTTP-only cookies restrict access to the cookie to the HTTP(S) protocols, preventing client-side scripts from accessing the token directly. This helps protect against Cross-Site Scripting (XSS) attacks.

#### Example Flow:

1. **User Authentication:**
   - The user provides valid credentials (username and password) for authentication.
   - The server validates the credentials and generates a JWT.

2. **JWT Issuance:**
   - The server sends the JWT to the client in an HTTP-only cookie.
   - The client stores the JWT securely, ensuring it is not accessible by client-side scripts.

3. **Subsequent Requests:**
   - The client includes the JWT in the cookies of each subsequent API request.
   - The server verifies the JWT to authenticate and authorize the user.

By combining JWT and HTTP-only cookies, we establish a secure and efficient authentication mechanism for our backend services. This approach ensures that user sessions remain stateless on the server while maintaining a high level of security against common web application vulnerabilities.

## Database Schema

### Categories Table
- **Fields:**
  - `category_id` (Primary Key, Integer): Unique identifier for each category. Auto-incremented using a sequence.
  - `category_name` (String): The name of the product category. Limited to 30 characters.

### Contact Us Table
- **Fields:**
  - `form_id` (Primary Key, Integer): Unique identifier for each contact form entry. Auto-incremented using a sequence.
  - `name` (String): Name of the person contacting. Limited to 255 characters.
  - `email` (String): Email address of the person contacting. Limited to 255 characters.
  - `comment` (Text): Message or comment from the contact.

### Order Items Table
- **Fields:**
  - `order_item_id` (Primary Key, Integer): Unique identifier for each order item. Auto-incremented using a sequence.
  - `order_id` (Foreign Key, Integer): ID of the associated order. Auto-incremented using a sequence.
  - `product_id` (Foreign Key, Integer): ID of the associated product. Auto-incremented using a sequence.
  - `quantity` (Integer): Quantity of the product in the order.
  - `subtotal` (Numeric): Subtotal for the order item.

### Orders Table
- **Fields:**
  - `order_id` (Primary Key, Integer): Unique identifier for each order. Auto-incremented using a sequence.
  - `user_id` (Foreign Key, Integer): ID of the associated user. Auto-incremented using a sequence. Can be NULL (YES) to handle orders without a registered user.
  - `order_date` (Date): Date when the order was placed.
  - `order_total` (Numeric): Total cost of the order.

### Products Table
- **Fields:**
  - `product_id` (Primary Key, Integer): Unique identifier for each product. Auto-incremented using a sequence.
  - `category_id` (Foreign Key, Integer): ID of the associated product category. Auto-incremented using a sequence.
  - `price` (Numeric): Price of the product.
  - `stock_quantity` (Integer): Current stock quantity of the product.
  - `volume` (Integer): Volume or size of the product.
  - `name` (String): Name of the product. Limited to 30 characters.
  - `description` (String): Description of the product. Limited to 255 characters.
  - `image_url` (Text): URL or path to the image of the product. Can be NULL (YES).

### Recipes Table
- **Fields:**
  - `recipe_id` (Primary Key, Integer): Unique identifier for each recipe. Auto-incremented using a sequence.
  - `product_id` (Foreign Key, Integer): ID of the associated product. Can be NULL (YES) for recipes not linked to a specific product.
  - `name` (String): Name of the recipe.
  - `description` (String): Description of the recipe.
  - `ingredients` (Array): Array containing the ingredients of the recipe.
  - `steps` (Array): Array containing the steps or instructions for preparing the recipe.
  - `image_path` (String): Path to the image of the recipe.

### User Login Information Table
- **Fields:**
  - `user_login_id` (Primary Key, Integer): Unique identifier for each user login entry. Auto-incremented using a sequence.
  - `email` (String): Email address associated with the user login. Limited to 255 characters.
  - `password` (String): Hashed password associated with the user login. Limited to 255 characters.

### Users Table
- **Fields:**
  - `user_id` (Primary Key, Integer): Unique identifier for each user. Auto-incremented using a sequence.
  - `user_login_info` (Foreign Key, Integer): ID referencing the associated user login info. Auto-incremented using a sequence.
  - `first_name` (String): First name of the user.
  - `last_name` (String): Last name of the user.
  - `address` (String): Address of the user. Can be NULL (YES) if not provided.
  - `role` (String): Role or user type of the user.

## Technologies Used:

- **Frontend:**
  - Framework: Angular 16.2
  - Styling: SCSS
  - State Management:
    - Custom implementation using services and parent-child component communication
  - UI Components:
    - Bootstrap Icons
  - Responsive Design:
    - Custom implementation using media queries
  - API Requests:
    - RxJS for handling asynchronous operations
    - Angular HttpClient for making API requests

- **Backend:**
  - Framework: Spring Boot
  - Build Tool: Gradle
  - Java Version: 17
  - Key Dependencies:
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Web
    - Spring Boot Starter Validation
    - Spring Boot Starter Security
    - Spring Boot Starter Test
    - Lombok
    - PostgreSQL
    - MapStruct 
    - JWT API 
    - Spring Boot Configuration Processor

- **Database:**
  - PostgreSQL 16.1
 
## Demo:
You can view a demo of the project uploaded on OneDrive through the following link:
[Demo](https://onedrive.live.com/?authkey=%21ACRLctqJ7whxTZw&id=42AA43D042607B76%21191966&cid=42AA43D042607B76)

## How to Contribute:

We welcome contributions from the community to enhance and improve our e-commerce platform.
Thank you for exploring our e-commerce platform. We hope you enjoy the shopping experience!
