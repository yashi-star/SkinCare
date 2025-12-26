# E-Commerce Microservices Platform
## Complete Project Documentation

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Architecture & System Design](#2-architecture--system-design)
3. [Technology Stack](#3-technology-stack)
4. [Service Details](#4-service-details)
5. [Database Schema](#5-database-schema)
6. [API Endpoints](#6-api-endpoints)
7. [Inter-Service Communication](#7-inter-service-communication)
8. [Setup & Installation](#8-setup--installation)
9. [Running the Project](#9-running-the-project)
10. [Functionalities](#10-functionalities)
11. [Future Enhancements](#11-future-enhancements)
12. [Troubleshooting](#12-troubleshooting)
13. [Best Practices](#13-best-practices)
14. [Deployment Guide](#14-deployment-guide)

---

## 1. Project Overview

### 1.1 What is This Project?

This is a **full-stack e-commerce platform** built using **microservices architecture** specifically designed for a skincare product marketplace. The system is built with Spring Boot and follows modern microservices patterns including service discovery, API gateway, and inter-service communication.

### 1.2 Key Features

- **Microservices Architecture**: Independent, scalable services
- **Service Discovery**: Eureka Server for dynamic service registration
- **API Gateway**: Single entry point for all client requests
- **User Management**: Registration, authentication, profile management
- **Product Catalog**: Complete CRUD operations with filtering and search
- **Shopping Cart**: Add, update, remove items with real-time stock validation
- **Order Management**: Order creation, tracking, and status updates
- **Stock Management**: Real-time inventory tracking and updates
- **Inter-Service Communication**: Feign clients for service-to-service calls

### 1.3 Business Domain

**Skincare E-Commerce Platform** - A specialized marketplace for skincare products with features like:
- Product categorization (Cleanser, Moisturizer, Serum, etc.)
- Skin type filtering (Oily, Dry, Combination, Sensitive)
- Brand-based product browsing
- User skin profile management
- Personalized product recommendations (future)

---

## 2. Architecture & System Design

### 2.1 Microservices Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENT (Frontend)                        │
│                  http://localhost:3000                      │
└───────────────────────┬─────────────────────────────────────┘
                        │
                        │ HTTP Requests
                        │
        ┌───────────────▼───────────────────────┐
        │         API GATEWAY (8080)             │
        │  - Single Entry Point                  │
        │  - Request Routing                    │
        │  - Load Balancing                     │
        │  - CORS Handling                     │
        └───────────────┬───────────────────────┘
                        │
        ┌───────────────┴───────────────────────┐
        │                                       │
        │    EUREKA SERVER (8761)              │
        │    Service Discovery & Registry      │
        │                                       │
        └───────┬───────────────────┬───────────┘
                │                   │
    ┌───────────┴──────────┐  ┌────┴──────────────┐
    │                      │  │                   │
┌───▼──────┐        ┌─────▼───▼────┐    ┌───────▼──────┐
│ PRODUCT  │        │    USER       │    │    ORDER     │
│ SERVICE  │        │   SERVICE     │    │   SERVICE    │
│  (8081)  │        │    (8082)     │    │   (8083)     │
└───┬──────┘        └───────┬───────┘    └───────┬──────┘
    │                       │                     │
    │                       │                     │
┌───▼──────┐        ┌───────▼───────┐    ┌───────▼──────┐
│   CART   │        │   MySQL DB    │    │   MySQL DB   │
│ SERVICE  │        │  (user_db)    │    │ (order_db)   │
│  (8084)  │        └───────────────┘    └──────────────┘
└───┬──────┘
    │
┌───▼──────┐
│   MySQL  │
│ (cart_db)│
└──────────┘
```

### 2.2 Why Microservices?

**Benefits:**
1. **Scalability**: Scale individual services based on demand
2. **Technology Diversity**: Use different technologies for different services
3. **Independent Deployment**: Deploy services independently
4. **Fault Isolation**: Failure in one service doesn't crash the entire system
5. **Team Autonomy**: Different teams can work on different services
6. **Database Per Service**: Each service has its own database (data isolation)

**Trade-offs:**
- Increased complexity in deployment and monitoring
- Network latency between services
- Data consistency challenges (eventual consistency)

### 2.3 Service Communication Flow

```
Client Request Flow:
1. Client → API Gateway (http://localhost:8080/api/products/1)
2. API Gateway → Eureka Server (Find PRODUCT-SERVICE instances)
3. Eureka → API Gateway (Return service instances)
4. API Gateway → Product Service (Load balanced request)
5. Product Service → MySQL Database
6. Response flows back through the chain

Inter-Service Communication:
1. Order Service needs User info → Feign Client → User Service
2. Order Service needs Product info → Feign Client → Product Service
3. Cart Service needs Product info → Feign Client → Product Service
```

---

## 3. Technology Stack

### 3.1 Backend Technologies

| Technology | Version | Purpose | Why We Use It |
|------------|---------|---------|--------------|
| **Java** | 21 | Programming Language | Modern, robust, enterprise-grade |
| **Spring Boot** | 4.0.1 | Framework | Rapid development, auto-configuration |
| **Spring Cloud** | 2025.1.0 | Microservices Tools | Service discovery, gateway, config |
| **Spring Cloud Gateway** | - | API Gateway | Reactive, high-performance routing |
| **Netflix Eureka** | - | Service Discovery | Industry-standard, Spring Cloud integration |
| **Spring Cloud OpenFeign** | - | HTTP Client | Declarative REST client, integrates with Eureka |
| **Spring Data JPA** | - | Data Access | Simplifies database operations |
| **Hibernate** | - | ORM | Object-relational mapping |
| **MySQL** | 8.0+ | Database | Reliable, widely used, ACID compliance |
| **Maven** | - | Build Tool | Dependency management, project structure |
| **Lombok** | - | Code Generation | Reduces boilerplate code |
| **BCrypt** | - | Password Hashing | Secure password encryption |

### 3.2 Why These Technologies?

#### **Spring Boot**
- **Auto-configuration**: Reduces boilerplate code
- **Embedded Server**: No need for external Tomcat
- **Production-ready**: Actuator, metrics, health checks
- **Large Community**: Extensive documentation and support

#### **Spring Cloud**
- **Microservices Patterns**: Implements common patterns out-of-the-box
- **Service Discovery**: Automatic service registration and discovery
- **Load Balancing**: Built-in client-side load balancing
- **Circuit Breaker**: Fault tolerance (can be added)

#### **Eureka Server**
- **Service Registry**: Central registry for all services
- **Health Monitoring**: Tracks service health
- **Load Balancing**: Enables load balancing across instances
- **Self-Healing**: Automatically removes unhealthy instances

#### **Spring Cloud Gateway**
- **Reactive**: Non-blocking, high-performance
- **Route Configuration**: Easy route management via properties
- **Filters**: Request/response transformation
- **CORS**: Built-in CORS support

#### **OpenFeign**
- **Declarative**: Define clients with annotations
- **Eureka Integration**: Automatic service discovery
- **Load Balancing**: Built-in load balancing
- **Error Handling**: Easy error handling with fallbacks

#### **MySQL**
- **ACID Compliance**: Data consistency
- **Relational**: Supports complex queries and relationships
- **Performance**: Optimized for read-heavy workloads
- **Mature**: Battle-tested in production

---

## 4. Service Details

### 4.1 Eureka Server

**Port**: 8761  
**Purpose**: Service Discovery and Registry

**What It Does:**
- Acts as a service registry where all microservices register themselves
- Provides service discovery - services can find each other by name
- Monitors service health and removes unhealthy instances
- Provides a web dashboard at `http://localhost:8761`

**Configuration:**
```properties
server.port=8761
eureka.client.register-with-eureka=false  # Server doesn't register itself
eureka.client.fetch-registry=false       # Server doesn't fetch registry
eureka.server.enable-self-preservation=false
```

**Key Annotations:**
- `@EnableEurekaServer`: Enables Eureka server functionality

**Why It's Needed:**
- Without service discovery, services would need hardcoded URLs
- Enables dynamic scaling (add/remove instances without code changes)
- Provides health monitoring and automatic failover

---

### 4.2 API Gateway

**Port**: 8080  
**Purpose**: Single Entry Point, Request Routing, Load Balancing

**What It Does:**
- Routes all client requests to appropriate microservices
- Load balances requests across multiple service instances
- Handles CORS for frontend applications
- Can add authentication, rate limiting, logging (future)

**Routes Configured:**
```
/api/products/**  → PRODUCT-SERVICE
/api/users/**     → USER-SERVICE
/api/orders/**    → ORDER-SERVICE
/api/cart/**      → CART-SERVICE
/api/payments/**  → PAYMENT-SERVICE
```

**Configuration:**
```properties
spring.cloud.gateway.routes[0].id=product-service
spring.cloud.gateway.routes[0].uri=lb://PRODUCT-SERVICE  # lb = load balanced
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/products/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1  # Removes /api
```

**Why It's Needed:**
- **Single Entry Point**: Clients don't need to know all service URLs
- **Load Balancing**: Distributes traffic across instances
- **Cross-Cutting Concerns**: Centralized authentication, logging, rate limiting
- **Protocol Translation**: Can handle different protocols

---

### 4.3 Product Service

**Port**: 8081  
**Database**: `product_db`  
**Purpose**: Product Catalog Management

**Responsibilities:**
- CRUD operations for products
- Product search and filtering
- Stock management
- Category, brand, skin type filtering

**Key Features:**
- **Soft Delete**: Products marked as inactive instead of deleted
- **Stock Management**: Real-time stock updates and checks
- **Advanced Filtering**: By category, skin type, brand
- **Search**: Name-based product search

**Endpoints:**
- `POST /products` - Create product
- `GET /products` - Get all products (with optional active filter)
- `GET /products/{id}` - Get product by ID
- `GET /products/category/{category}` - Filter by category
- `GET /products/skintype/{skinType}` - Filter by skin type
- `GET /products/brand/{brand}` - Filter by brand
- `GET /products/search?name=...` - Search products
- `PUT /products/{id}` - Update product
- `DELETE /products/{id}` - Soft delete
- `DELETE /products/{id}/permanent` - Hard delete
- `PUT /products/{id}/stock?quantity=X` - Update stock
- `GET /products/{id}/stock/check?quantity=X` - Check stock availability

**Entity Structure:**
```java
Product {
    id, name, description, price, stock,
    category, skinType, brand, imageUrl,
    ingredients, active, createdAt, updatedAt
}
```

**Why Separate Service:**
- Product catalog can scale independently
- Different teams can manage products
- Can use different database optimizations
- Isolated from other business logic

---

### 4.4 User Service

**Port**: 8082  
**Database**: `user_db`  
**Purpose**: User Management and Authentication

**Responsibilities:**
- User registration
- User authentication (login)
- User profile management
- Password hashing and security

**Key Features:**
- **Password Security**: BCrypt password hashing
- **Soft Delete**: Users marked as inactive
- **Profile Management**: Update user information
- **Skin Profile**: Stores user skin type and concerns

**Endpoints:**
- `POST /users/register` - Register new user
- `POST /users/login` - User login
- `GET /users/{id}` - Get user by ID
- `GET /users` - Get all users (with optional active filter)
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Soft delete user
- `DELETE /users/{id}/permanent` - Hard delete user

**Security:**
- **BCrypt Password Encoding**: Passwords are hashed before storage
- **Spring Security**: Configured for authentication (currently permissive)
- **No Password in Response**: Passwords never sent back to client

**Entity Structure:**
```java
User {
    id, email, password (hashed), firstName, lastName,
    phone, address, city, state, zipCode, country,
    skinType, skinConcerns, active, createdAt, updatedAt
}
```

**Why Separate Service:**
- User data is sensitive - isolated security
- Can scale authentication independently
- Different access patterns (read-heavy)
- Can implement different authentication strategies

---

### 4.5 Order Service

**Port**: 8083  
**Database**: `order_db`  
**Purpose**: Order Management

**Responsibilities:**
- Order creation
- Order tracking
- Order status management
- Payment status tracking
- Stock updates (via Product Service)

**Key Features:**
- **Inter-Service Communication**: Uses Feign to call User and Product services
- **Stock Validation**: Validates stock before order creation
- **Automatic Stock Updates**: Updates product stock after order creation
- **Order Status Workflow**: PENDING → CONFIRMED → PROCESSING → SHIPPED → DELIVERED

**Endpoints:**
- `POST /orders` - Create order
- `GET /orders` - Get all orders
- `GET /orders/{id}` - Get order by ID
- `GET /orders/user/{userId}` - Get orders by user
- `PATCH /orders/{id}/status` - Update order status
- `PATCH /orders/{id}/payment` - Update payment status
- `DELETE /orders/{id}` - Cancel order

**Inter-Service Calls:**
- **UserClient**: Fetches user information for order creation
- **ProductClient**: Fetches product details and updates stock

**Entity Structure:**
```java
Order {
    id, userId, orderItems (List<OrderItem>),
    totalAmount, status, shippingAddress,
    paymentMethod, paymentStatus, createdAt, updatedAt
}

OrderItem {
    id, order, productId, productName,
    quantity, price, subtotal
}
```

**Why Separate Service:**
- Order processing is complex - isolated logic
- Can scale order processing independently
- Different database schema optimizations
- Can implement order-specific features (invoicing, shipping)

---

### 4.6 Cart Service

**Port**: 8084  
**Database**: `cart_db`  
**Purpose**: Shopping Cart Management

**Responsibilities:**
- Add items to cart
- Update cart items
- Remove items from cart
- Clear cart
- Real-time stock validation

**Key Features:**
- **Stock Validation**: Validates stock before adding/updating items
- **Automatic Total Calculation**: Calculates cart total automatically
- **Product Information**: Fetches product details from Product Service
- **User-Specific Carts**: One cart per user

**Endpoints:**
- `POST /carts/add` - Add item to cart
- `GET /carts/user/{userId}` - Get cart by user ID
- `PUT /carts/user/{userId}/items/{cartItemId}` - Update cart item
- `DELETE /carts/user/{userId}/items/{cartItemId}` - Remove cart item
- `DELETE /carts/user/{userId}` - Clear cart

**Inter-Service Calls:**
- **ProductClient**: Fetches product information and validates stock

**Entity Structure:**
```java
Cart {
    id, userId, items (List<CartItem>),
    totalAmount, createdAt, updatedAt
}

CartItem {
    id, cart, productId, productName,
    price, quantity, subtotal, imageUrl, addedAt
}
```

**Why Separate Service:**
- Cart operations are frequent - can scale independently
- Temporary data - different retention policies
- Can implement cart-specific features (saved carts, wishlist)
- Isolated from order processing logic

---

## 5. Database Schema

### 5.1 Database Per Service Pattern

Each microservice has its own database:
- `product_db` - Product Service
- `user_db` - User Service
- `order_db` - Order Service
- `cart_db` - Cart Service

**Why Separate Databases?**
- **Data Isolation**: Services can't directly access each other's data
- **Independent Scaling**: Scale databases independently
- **Technology Flexibility**: Can use different database types
- **Fault Isolation**: Database failure doesn't affect other services

### 5.2 Schema Details

#### **Product Database (`product_db`)**

```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000),
    price DECIMAL(19,2) NOT NULL,
    stock INT NOT NULL,
    category VARCHAR(255),
    skin_type VARCHAR(255),
    brand VARCHAR(255),
    image_url VARCHAR(500),
    ingredients VARCHAR(1000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    INDEX idx_category (category),
    INDEX idx_skin_type (skin_type),
    INDEX idx_brand (brand),
    INDEX idx_active (active)
);
```

#### **User Database (`user_db`)**

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100),
    skin_type VARCHAR(50),
    skin_concerns VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_active (active)
);
```

#### **Order Database (`order_db`)**

```sql
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(500),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    created_at DATETIME(6),
    updated_at DATETIME(6),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    quantity INT NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    subtotal DECIMAL(19,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);
```

#### **Cart Database (`cart_db`)**

```sql
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_amount DECIMAL(10, 2) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id (product_id),
    UNIQUE KEY uk_cart_product (cart_id, product_id)
);
```

---

## 6. API Endpoints

### 6.1 Complete API Reference

#### **Product Service** (`http://localhost:8081` or via Gateway `http://localhost:8080/api/products`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/products` | Create new product |
| GET | `/products` | Get all products |
| GET | `/products?active=true` | Get active products only |
| GET | `/products/{id}` | Get product by ID |
| GET | `/products/category/{category}` | Get products by category |
| GET | `/products/skintype/{skinType}` | Get products by skin type |
| GET | `/products/brand/{brand}` | Get products by brand |
| GET | `/products/search?name={name}` | Search products by name |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Soft delete product |
| DELETE | `/products/{id}/permanent` | Hard delete product |
| PUT | `/products/{id}/stock?quantity={qty}` | Update stock |
| GET | `/products/{id}/stock/check?quantity={qty}` | Check stock availability |

#### **User Service** (`http://localhost:8082` or via Gateway `http://localhost:8080/api/users`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/register` | Register new user |
| POST | `/users/login` | User login |
| GET | `/users/{id}` | Get user by ID |
| GET | `/users` | Get all users |
| GET | `/users?active=true` | Get active users only |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Soft delete user |
| DELETE | `/users/{id}/permanent` | Hard delete user |

#### **Order Service** (`http://localhost:8083` or via Gateway `http://localhost:8080/api/orders`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders` | Create new order |
| GET | `/orders` | Get all orders |
| GET | `/orders/{id}` | Get order by ID |
| GET | `/orders/user/{userId}` | Get orders by user |
| PATCH | `/orders/{id}/status?status={status}` | Update order status |
| PATCH | `/orders/{id}/payment?paymentStatus={status}` | Update payment status |
| DELETE | `/orders/{id}` | Cancel order |

#### **Cart Service** (`http://localhost:8084` or via Gateway `http://localhost:8080/api/cart`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/carts/add` | Add item to cart |
| GET | `/carts/user/{userId}` | Get cart by user ID |
| PUT | `/carts/user/{userId}/items/{cartItemId}` | Update cart item |
| DELETE | `/carts/user/{userId}/items/{cartItemId}` | Remove cart item |
| DELETE | `/carts/user/{userId}` | Clear cart |

### 6.2 Example API Requests

#### **Register User**
```bash
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "address": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "country": "USA",
  "skinType": "Oily",
  "skinConcerns": "Acne"
}
```

#### **Create Product**
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Hydrating Facial Cleanser",
  "description": "Gentle cleanser for dry skin",
  "price": 24.99,
  "stock": 100,
  "category": "Cleanser",
  "skinType": "Dry",
  "brand": "CeraVe",
  "imageUrl": "https://example.com/image.jpg",
  "ingredients": "Water, Glycerin, Ceramides"
}
```

#### **Add to Cart**
```bash
POST http://localhost:8080/api/cart/add
Content-Type: application/json

{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}
```

#### **Create Order**
```bash
POST http://localhost:8080/api/orders
Content-Type: application/json

{
  "userId": 1,
  "paymentMethod": "Credit Card",
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

---

## 7. Inter-Service Communication

### 7.1 Feign Clients

**Feign** is used for synchronous HTTP communication between services.

#### **Order Service → User Service**
```java
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
```

#### **Order Service → Product Service**
```java
@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
    
    @PutMapping("/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestParam Integer quantity);
}
```

#### **Cart Service → Product Service**
```java
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
```

### 7.2 Communication Flow

**Order Creation Flow:**
```
1. Order Service receives order request
2. Order Service calls User Service (via Feign) → Get user details
3. Order Service calls Product Service (via Feign) → Get product details
4. Order Service validates stock
5. Order Service creates order in database
6. Order Service calls Product Service → Update stock
7. Order Service returns order response
```

**Cart Operations Flow:**
```
1. Cart Service receives add-to-cart request
2. Cart Service calls Product Service (via Feign) → Get product details
3. Cart Service validates stock availability
4. Cart Service adds item to cart
5. Cart Service returns cart response
```

### 7.3 Why Feign?

- **Declarative**: Define clients with annotations
- **Eureka Integration**: Automatic service discovery
- **Load Balancing**: Built-in load balancing
- **Error Handling**: Can add fallback methods
- **Type Safety**: Strongly typed interfaces

---

## 8. Setup & Installation

### 8.1 Prerequisites

**Required Software:**
- **Java 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)
- **Git** (optional)

**System Requirements:**
- **RAM**: Minimum 8GB (16GB recommended)
- **Disk Space**: 2GB free space
- **OS**: Windows, macOS, or Linux

### 8.2 Installation Steps

#### **Step 1: Install Java 21**
```bash
# Check Java version
java -version

# Should show: openjdk version "21" or higher
```

#### **Step 2: Install Maven**
```bash
# Check Maven version
mvn -version

# Should show: Apache Maven 3.6+ or higher
```

#### **Step 3: Install MySQL**
1. Download MySQL from https://dev.mysql.com/downloads/mysql/
2. Install MySQL Server
3. Set root password (remember this!)
4. Start MySQL service

#### **Step 4: Create Databases**
```sql
-- Connect to MySQL
mysql -u root -p

-- Create databases
CREATE DATABASE product_db;
CREATE DATABASE user_db;
CREATE DATABASE order_db;
CREATE DATABASE cart_db;

-- Verify
SHOW DATABASES;
```

#### **Step 5: Clone/Download Project**
```bash
# If using Git
git clone <repository-url>
cd Ecommerce

# Or extract downloaded ZIP file
```

### 8.3 Configuration

#### **Database Configuration**

Update `application.properties` in each service:

**Product Service** (`backend/product-service/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**User Service** (`backend/user-service/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**Order Service** (`backend/order-service/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**Cart Service** (`backend/cart-service/src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cart_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## 9. Running the Project

### 9.1 Start Order (Critical!)

**IMPORTANT**: Services must be started in this order:

1. **Eureka Server** (must start first)
2. **API Gateway** (depends on Eureka)
3. **Product Service** (other services depend on it)
4. **User Service** (Order Service depends on it)
5. **Cart Service** (depends on Product Service)
6. **Order Service** (depends on User and Product Services)

### 9.2 Starting Services

#### **Option 1: Using IDE (Recommended)**

**IntelliJ IDEA:**
1. Open project in IntelliJ
2. Wait for Maven to download dependencies
3. Right-click on each service's main class → Run
4. Start in this order:
   - `EurekaServerApplication`
   - `ApiGatewayApplication`
   - `ProductServiceApplication`
   - `UserServiceApplication`
   - `CartServiceApplication`
   - `OrderServiceApplication`

**Eclipse:**
1. Import project as Maven project
2. Right-click → Run As → Spring Boot App
3. Start in the same order

#### **Option 2: Using Command Line**

**Terminal 1 - Eureka Server:**
```bash
cd backend/eureka-server
mvn spring-boot:run
```
Wait for: "Started EurekaServerApplication"

**Terminal 2 - API Gateway:**
```bash
cd backend/api-gateway
mvn spring-boot:run
```
Wait for: "Started ApiGatewayApplication"

**Terminal 3 - Product Service:**
```bash
cd backend/product-service
mvn spring-boot:run
```
Wait for: "Started ProductServiceApplication"

**Terminal 4 - User Service:**
```bash
cd backend/user-service
mvn spring-boot:run
```
Wait for: "Started UserServiceApplication"

**Terminal 5 - Cart Service:**
```bash
cd backend/cart-service
mvn spring-boot:run
```
Wait for: "Started CartServiceApplication"

**Terminal 6 - Order Service:**
```bash
cd backend/order-service
mvn spring-boot:run
```
Wait for: "Started OrderServiceApplication"

### 9.3 Verify Services Are Running

#### **Check Eureka Dashboard**
Open browser: `http://localhost:8761`

You should see all services registered:
- API-GATEWAY
- PRODUCT-SERVICE
- USER-SERVICE
- CART-SERVICE
- ORDER-SERVICE

#### **Test API Gateway**
```bash
# Test Product Service via Gateway
curl http://localhost:8080/api/products

# Test User Service via Gateway
curl http://localhost:8080/api/users

# Test Cart Service via Gateway
curl http://localhost:8080/api/cart/user/1
```

### 9.4 Common Issues

**Port Already in Use:**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

**Database Connection Error:**
- Check MySQL is running
- Verify database credentials
- Ensure databases exist

**Service Not Registering with Eureka:**
- Check Eureka Server is running first
- Verify `eureka.client.service-url.defaultZone` in application.properties
- Check network connectivity

---

## 10. Functionalities

### 10.1 Current Features

#### **User Management**
- ✅ User registration with validation
- ✅ User login with password authentication
- ✅ User profile management
- ✅ Password hashing (BCrypt)
- ✅ User soft delete
- ✅ Skin profile management

#### **Product Management**
- ✅ Product CRUD operations
- ✅ Product search and filtering
- ✅ Category-based filtering
- ✅ Skin type filtering
- ✅ Brand filtering
- ✅ Stock management
- ✅ Product soft delete
- ✅ Stock availability checking

#### **Shopping Cart**
- ✅ Add items to cart
- ✅ Update cart items
- ✅ Remove items from cart
- ✅ Clear cart
- ✅ Real-time stock validation
- ✅ Automatic total calculation
- ✅ User-specific carts

#### **Order Management**
- ✅ Order creation
- ✅ Order tracking
- ✅ Order status management
- ✅ Payment status tracking
- ✅ Order cancellation
- ✅ User order history
- ✅ Automatic stock updates

#### **System Features**
- ✅ Service discovery (Eureka)
- ✅ API Gateway routing
- ✅ Load balancing
- ✅ CORS support
- ✅ Inter-service communication (Feign)
- ✅ Error handling
- ✅ Request validation

### 10.2 User Flows

#### **Registration Flow**
```
1. User fills registration form
2. Frontend sends POST /api/users/register
3. User Service validates data
4. User Service hashes password
5. User Service saves user to database
6. User Service returns user DTO (without password)
```

#### **Shopping Flow**
```
1. User browses products (GET /api/products)
2. User adds product to cart (POST /api/cart/add)
3. Cart Service validates stock
4. Cart Service adds item to cart
5. User views cart (GET /api/cart/user/{userId})
6. User creates order (POST /api/orders)
7. Order Service validates user and products
8. Order Service creates order
9. Order Service updates product stock
10. Order Service returns order confirmation
```

#### **Order Processing Flow**
```
1. Order created with status PENDING
2. Payment processed (external service)
3. Payment status updated (PATCH /api/orders/{id}/payment)
4. Order status changes to CONFIRMED
5. Order status updated to PROCESSING
6. Order status updated to SHIPPED
7. Order status updated to DELIVERED
```

---

## 11. Future Enhancements

### 11.1 High Priority

#### **Authentication & Authorization**
- **JWT Tokens**: Implement JWT-based authentication
- **Role-Based Access Control**: Admin, Customer roles
- **OAuth2 Integration**: Social login (Google, Facebook)
- **Refresh Tokens**: Token refresh mechanism

#### **Payment Integration**
- **Payment Service**: Complete payment processing
- **Payment Gateways**: Stripe, PayPal integration
- **Payment History**: Track payment transactions
- **Refund Processing**: Handle refunds

#### **Notification Service**
- **Email Notifications**: Order confirmations, shipping updates
- **SMS Notifications**: Order status updates
- **Push Notifications**: Mobile app notifications
- **In-App Notifications**: Real-time notifications

### 11.2 Medium Priority

#### **Search & Recommendations**
- **Elasticsearch**: Advanced product search
- **Product Recommendations**: Based on skin type, purchase history
- **Trending Products**: Popular products algorithm
- **Recently Viewed**: Track user browsing history

#### **Inventory Management**
- **Low Stock Alerts**: Notify when stock is low
- **Stock Reordering**: Automatic reorder points
- **Inventory Reports**: Stock analytics
- **Multi-Warehouse**: Support multiple warehouses

#### **Order Enhancements**
- **Order Tracking**: Real-time shipment tracking
- **Order History**: Enhanced order history with filters
- **Order Cancellation**: Time-based cancellation rules
- **Order Returns**: Return request processing

#### **User Features**
- **Wishlist**: Save products for later
- **Product Reviews**: User reviews and ratings
- **Loyalty Program**: Points and rewards system
- **Referral Program**: Refer friends and earn rewards

### 11.3 Low Priority

#### **Analytics & Reporting**
- **Sales Analytics**: Revenue, profit reports
- **User Analytics**: User behavior tracking
- **Product Analytics**: Product performance metrics
- **Dashboard**: Admin dashboard with charts

#### **Performance Optimization**
- **Caching**: Redis for caching frequently accessed data
- **CDN**: Content Delivery Network for images
- **Database Optimization**: Query optimization, indexing
- **API Rate Limiting**: Prevent abuse

#### **Monitoring & Logging**
- **Distributed Tracing**: Zipkin or Jaeger
- **Centralized Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)
- **Metrics**: Prometheus and Grafana
- **Health Checks**: Enhanced health endpoints

#### **DevOps**
- **Docker**: Containerize all services
- **Docker Compose**: One-command startup
- **Kubernetes**: Container orchestration
- **CI/CD Pipeline**: Automated testing and deployment

### 11.4 Advanced Features

#### **Microservices Patterns**
- **Circuit Breaker**: Resilience4j for fault tolerance
- **API Gateway**: Advanced routing and filtering
- **Service Mesh**: Istio for service-to-service communication
- **Event-Driven Architecture**: Kafka for async communication

#### **Security Enhancements**
- **API Security**: API keys, rate limiting
- **Data Encryption**: Encrypt sensitive data
- **Audit Logging**: Track all data changes
- **Security Scanning**: Automated vulnerability scanning

#### **Scalability**
- **Horizontal Scaling**: Multiple instances per service
- **Database Sharding**: Distribute data across databases
- **Read Replicas**: Separate read and write databases
- **Message Queues**: Async processing with RabbitMQ/Kafka

---

## 12. Troubleshooting

### 12.1 Common Issues

#### **Service Won't Start**

**Problem**: Service fails to start with port already in use error

**Solution**:
```bash
# Find process using port
netstat -ano | findstr :8081  # Windows
lsof -i :8081                 # Linux/Mac

# Kill process
taskkill /PID <PID> /F        # Windows
kill -9 <PID>                 # Linux/Mac
```

#### **Service Not Registering with Eureka**

**Problem**: Service starts but doesn't appear in Eureka dashboard

**Solution**:
1. Check Eureka Server is running first
2. Verify `eureka.client.service-url.defaultZone=http://localhost:8761/eureka/`
3. Check `spring.application.name` matches service name
4. Verify `@EnableDiscoveryClient` annotation is present
5. Check network connectivity

#### **Database Connection Error**

**Problem**: `Cannot connect to MySQL server`

**Solution**:
1. Verify MySQL is running: `mysql -u root -p`
2. Check database exists: `SHOW DATABASES;`
3. Verify credentials in `application.properties`
4. Check MySQL port (default 3306)
5. Check firewall settings

#### **Feign Client Errors**

**Problem**: `Service unavailable` or `Connection refused`

**Solution**:
1. Verify target service is running
2. Check service is registered in Eureka
3. Verify `@FeignClient` name matches Eureka service name
4. Check `@EnableFeignClients` annotation
5. Verify service port matches configuration

#### **CORS Errors**

**Problem**: Frontend can't call API due to CORS

**Solution**:
1. Verify CORS configuration in API Gateway
2. Check `allowedOrigins` includes frontend URL
3. Verify `allowedMethods` includes required HTTP methods
4. Check `allowCredentials` if using cookies

### 12.2 Debugging Tips

#### **Enable Debug Logging**

Add to `application.properties`:
```properties
logging.level.com.hcl=DEBUG
logging.level.org.springframework.cloud=DEBUG
logging.level.com.netflix.eureka=DEBUG
```

#### **Check Service Health**

```bash
# Eureka Dashboard
http://localhost:8761

# Service Health (if Actuator is enabled)
http://localhost:8081/actuator/health
```

#### **View Logs**

Check console output for:
- Service registration messages
- Database connection messages
- Feign client calls
- Error stack traces

---

## 13. Best Practices

### 13.1 Code Practices

#### **Service Design**
- ✅ Single Responsibility: Each service has one clear purpose
- ✅ Database Per Service: Isolated data storage
- ✅ API Versioning: Version your APIs (`/api/v1/products`)
- ✅ Error Handling: Consistent error responses
- ✅ Input Validation: Validate all inputs

#### **Security**
- ✅ Password Hashing: Never store plain passwords
- ✅ HTTPS: Use HTTPS in production
- ✅ Input Sanitization: Prevent SQL injection, XSS
- ✅ Authentication: Implement proper authentication
- ✅ Authorization: Check permissions

#### **Performance**
- ✅ Database Indexing: Index frequently queried fields
- ✅ Connection Pooling: Configure connection pools
- ✅ Caching: Cache frequently accessed data
- ✅ Pagination: Paginate large result sets
- ✅ Async Processing: Use async for long operations

### 13.2 Microservices Practices

#### **Communication**
- ✅ Use Feign for synchronous calls
- ✅ Use message queues for async operations
- ✅ Implement circuit breakers
- ✅ Add retry logic with backoff
- ✅ Use timeouts

#### **Data Management**
- ✅ Database per service
- ✅ Eventual consistency
- ✅ Saga pattern for distributed transactions
- ✅ CQRS for read/write separation (advanced)

#### **Monitoring**
- ✅ Log all important operations
- ✅ Track service metrics
- ✅ Monitor service health
- ✅ Set up alerts
- ✅ Use distributed tracing

---

## 14. Deployment Guide

### 14.1 Production Checklist

#### **Before Deployment**
- [ ] All tests passing
- [ ] Database migrations ready
- [ ] Environment variables configured
- [ ] Security configurations reviewed
- [ ] Performance tested
- [ ] Monitoring setup
- [ ] Backup strategy in place

#### **Configuration Changes**

**application.properties → application-prod.properties:**
```properties
# Database
spring.datasource.url=jdbc:mysql://prod-db:3306/product_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Eureka
eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka/

# Logging
logging.level.root=INFO
logging.level.com.hcl=INFO

# Security
spring.security.user.name=${ADMIN_USERNAME}
spring.security.user.password=${ADMIN_PASSWORD}
```

### 14.2 Docker Deployment

#### **Dockerfile Example** (Product Service)

```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### **Docker Compose Example**

```yaml
version: '3.8'
services:
  eureka-server:
    build: ./backend/eureka-server
    ports:
      - "8761:8761"
  
  api-gateway:
    build: ./backend/api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
  
  product-service:
    build: ./backend/product-service
    ports:
      - "8081:8081"
    depends_on:
      - eureka-server
      - mysql-product
  
  mysql-product:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: product_db
    volumes:
      - product-data:/var/lib/mysql

volumes:
  product-data:
```

### 14.3 Kubernetes Deployment

#### **Deployment YAML Example**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: product-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: product-service
  template:
    metadata:
      labels:
        app: product-service
    spec:
      containers:
      - name: product-service
        image: product-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: SPRING_DATASOURCE_URL
          value: "jdbc:mysql://mysql-service:3306/product_db"
```

---

## 15. Conclusion

### 15.1 What You've Built

You've created a **production-ready microservices e-commerce platform** with:
- ✅ 6 independent microservices
- ✅ Service discovery and API gateway
- ✅ Complete CRUD operations
- ✅ Inter-service communication
- ✅ User authentication
- ✅ Order processing
- ✅ Shopping cart functionality

### 15.2 Next Steps

1. **Frontend Development**: Build React/Vue/Angular frontend
2. **Testing**: Add unit tests, integration tests
3. **CI/CD**: Set up automated deployment
4. **Monitoring**: Add monitoring and logging
5. **Security**: Implement JWT authentication
6. **Documentation**: API documentation with Swagger

### 15.3 Resources

- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **Spring Cloud Documentation**: https://spring.io/projects/spring-cloud
- **Eureka Documentation**: https://github.com/Netflix/eureka
- **MySQL Documentation**: https://dev.mysql.com/doc/

---

## Appendix A: Project Structure

```
Ecommerce/
├── backend/
│   ├── eureka-server/          # Service Discovery
│   ├── api-gateway/             # API Gateway
│   ├── product-service/         # Product Management
│   ├── user-service/            # User Management
│   ├── order-service/           # Order Management
│   ├── cart-service/            # Shopping Cart
│   └── payment-service/         # Payment (Future)
├── frontend/                    # Frontend Application (Future)
└── PROJECT_DOCUMENTATION.md    # This file
```

---

## Appendix B: Port Reference

| Service | Port | URL |
|---------|------|-----|
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080 |
| Product Service | 8081 | http://localhost:8081 |
| User Service | 8082 | http://localhost:8082 |
| Order Service | 8083 | http://localhost:8083 |
| Cart Service | 8084 | http://localhost:8084 |

---

## Appendix C: Database Reference

| Service | Database | Tables |
|---------|----------|--------|
| Product Service | product_db | products |
| User Service | user_db | users |
| Order Service | order_db | orders, order_items |
| Cart Service | cart_db | carts, cart_items |

---

**Document Version**: 1.0  
**Last Updated**: 2024  
**Author**: Development Team

---

*This documentation is a living document and will be updated as the project evolves.*

