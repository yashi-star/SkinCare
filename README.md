# E-Commerce Microservices Platform

A full-stack e-commerce platform built with Spring Boot microservices architecture, specifically designed for skincare product marketplace.

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+
- MySQL 8.0+
- 8GB+ RAM recommended

### Quick Setup

1. **Create Databases**
```sql
CREATE DATABASE product_db;
CREATE DATABASE user_db;
CREATE DATABASE order_db;
CREATE DATABASE cart_db;
```

2. **Update Database Credentials**
   - Update `application.properties` in each service with your MySQL credentials

3. **Start Services (In Order)**
   ```bash
   # Terminal 1: Eureka Server
   cd backend/eureka-server && mvn spring-boot:run
   
   # Terminal 2: API Gateway
   cd backend/api-gateway && mvn spring-boot:run
   
   # Terminal 3: Product Service
   cd backend/product-service && mvn spring-boot:run
   
   # Terminal 4: User Service
   cd backend/user-service && mvn spring-boot:run
   
   # Terminal 5: Cart Service
   cd backend/cart-service && mvn spring-boot:run
   
   # Terminal 6: Order Service
   cd backend/order-service && mvn spring-boot:run
   ```

4. **Verify**
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8080

## 📚 Documentation

**Full Documentation**: See [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md)

**Convert to PDF**:
```bash
# Using Pandoc
pandoc PROJECT_DOCUMENTATION.md -o PROJECT_DOCUMENTATION.pdf --pdf-engine=xelatex

# Or use online tools:
# - https://www.markdowntopdf.com/
# - https://dillinger.io/ (Export as PDF)
```

## 🏗️ Architecture

```
Client → API Gateway → Eureka Server → Microservices → Databases
```

## 📦 Services

| Service | Port | Purpose |
|---------|------|---------|
| Eureka Server | 8761 | Service Discovery |
| API Gateway | 8080 | Request Routing |
| Product Service | 8081 | Product Management |
| User Service | 8082 | User Management |
| Order Service | 8083 | Order Processing |
| Cart Service | 8084 | Shopping Cart |

## 🔑 Key Features

- ✅ Microservices Architecture
- ✅ Service Discovery (Eureka)
- ✅ API Gateway
- ✅ User Authentication
- ✅ Product Catalog
- ✅ Shopping Cart
- ✅ Order Management
- ✅ Inter-Service Communication (Feign)

## 📝 API Endpoints

All APIs are accessible via API Gateway: `http://localhost:8080/api`

- Products: `/api/products/**`
- Users: `/api/users/**`
- Orders: `/api/orders/**`
- Cart: `/api/cart/**`

## 🛠️ Technology Stack

- **Backend**: Spring Boot 4.0.1, Spring Cloud
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: MySQL 8.0+
- **Communication**: OpenFeign
- **Build Tool**: Maven

## 📖 For Detailed Information

See [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md) for:
- Complete architecture explanation
- Service details
- Database schemas
- API documentation
- Troubleshooting guide
- Deployment guide
- Future enhancements

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

---

**Need Help?** Check the [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md) for detailed guides and troubleshooting.

