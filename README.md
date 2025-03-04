# InternIntelligence_E-Commerce
InternIntelligence_Project

# _**_E-Commerce Project_**_

This is an E-commerce application built using the Spring Framework, Spring Security, and MySQL. The application allows users to browse products, manage their cart, place orders, and make payments securely. The backend uses RESTful APIs for all the interactions.

## Features

User Authentication & Authorization: Managed via Spring Security (Role-based access control).<br/>
Product Management: Users can view products and add them to their cart.<br/>
Cart & CartItem: Users can add, remove, and view items in their shopping cart.<br/>
Order & OrderItem: Users can place orders containing multiple products.<br/>
Payment: Integration for payment handling for orders.<br/>
Address Management: Users can manage multiple shipping addresses.<br/>
Category: Products are categorized for better browsing.<br/>

## Key Entities

Address: Represents a user's shipping address, including fields like street, city, state, country, and pincode.<br/>
Cart: Represents a user's shopping cart.<br/>
CartItem: Represents an individual item in the cart (associated with a product).<br/>
Category: Represents product categories for organizing products.<br/>
Order: Represents a user's placed order, including payment details and status.<br/>
OrderItem: Represents an item in an order (links to a product).<br/>
Payment: Represents payment details for an order (could include payment status, amount, payment method).<br/>
Product: Represents the products available in the store.<br/>
Role: Defines roles (e.g., ADMIN, USER) for role-based access control in Spring Security.<br/>
User: Represents the user profile with details like username, password, and roles.<br/>

## Technologies Used

Spring Boot: Framework for building the backend RESTful APIs.
Spring Security: For securing the application with authentication and authorization.
MySQL: Relational database for persisting the data.
JPA (Java Persistence API): To interact with the database using entities.
ModelMapper: For mapping between entity objects and DTOs (Data Transfer Objects).
Lombok: For reducing boilerplate code like getters, setters, and constructors.

Java 17 <br/>
MySQL Database <br/>
Maven (for managing dependencies) <br/>
Postman or any API testing tool <br/>

# **Setup & Installation**

### 1. Clone the Repository
   git clone https://github.com/YasharHmdv/InternIntelligence_E-Commerce.git <br/>

### 2. Set Up MySQL Database

   Make sure MySQL is installed and running on your machine. Create a new database for the project.<br/>

### CREATE DATABASE ecommerce_db;

### 3. Configure Database Connection

   Open src/main/resources/application.properties and set the database connection properties:<br/>
yaml file: <br/>
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db<br/>
spring.datasource.username=root<br/>
spring.datasource.password=yourpassword<br/>
spring.jpa.hibernate.ddl-auto=update<br/>
spring.jpa.show-sql=true<br/>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver<br/>