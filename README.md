# InternIntelligence_E-Commerce
InternIntelligence_Project

# _**_E-Commerce Project_**_

This is an E-commerce application built using the Spring Framework, Spring Security, and MySQL. The application allows users to browse products, manage their cart, place orders, and make payments securely. The backend uses RESTful APIs for all the interactions.

## Features

User Authentication & Authorization: Managed via Spring Security (Role-based access control).<br/>
Product Management: Users can view products and add them to their cart.
Cart & CartItem: Users can add, remove, and view items in their shopping cart.
Order & OrderItem: Users can place orders containing multiple products.
Payment: Integration for payment handling for orders.
Address Management: Users can manage multiple shipping addresses.
Category: Products are categorized for better browsing.

## Key Entities

Address: Represents a user's shipping address, including fields like street, city, state, country, and pincode.
Cart: Represents a user's shopping cart.
CartItem: Represents an individual item in the cart (associated with a product).
Category: Represents product categories for organizing products.
Order: Represents a user's placed order, including payment details and status.
OrderItem: Represents an item in an order (links to a product).
Payment: Represents payment details for an order (could include payment status, amount, payment method).
Product: Represents the products available in the store.
Role: Defines roles (e.g., ADMIN, USER) for role-based access control in Spring Security.
User: Represents the user profile with details like username, password, and roles.

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