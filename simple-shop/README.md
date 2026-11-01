# Simple Shop – Spring Boot E-Commerce Application

A simple full-stack e-commerce web app built with **Spring Boot 3**, **Spring Data JPA**, **Thymeleaf** and an in-memory **H2** database.

## Features
- Browse a catalogue of products (seeded automatically on startup)
- Add products to a session-based shopping cart
- Remove items from the cart
- Checkout with name and delivery address
- Order confirmation page and order history

## Tech Stack
| Layer     | Technology                     |
|-----------|--------------------------------|
| Backend   | Spring Boot 3.3, Spring MVC    |
| Database  | H2 (in-memory) via Spring Data JPA |
| Frontend  | Thymeleaf + plain CSS          |
| Build     | Maven, Java 17                 |

## Run locally
Requirements: Java 17+ and Maven.

```bash
mvn spring-boot:run
```

Open <http://localhost:8080>.

H2 console (optional): <http://localhost:8080/h2-console>
JDBC URL: `jdbc:h2:mem:shopdb`, user: `sa`, empty password.

## Project structure
```
src/main/java/com/example/shop
├── ShopApplication.java      # entry point
├── DataLoader.java           # seeds sample products
├── cart/                     # session-scoped Cart + CartLine
├── controller/ShopController.java
├── model/                    # Product, CustomerOrder, OrderItem (JPA entities)
└── repository/               # Spring Data repositories
src/main/resources
├── templates/                # products, cart, order, orders (Thymeleaf)
├── static/style.css
└── application.properties
```

## Routes
| Method | URL                  | Description          |
|--------|----------------------|----------------------|
| GET    | `/`                  | Product list         |
| POST   | `/cart/add/{id}`     | Add product to cart  |
| GET    | `/cart`              | View cart + checkout |
| POST   | `/cart/remove/{id}`  | Remove from cart     |
| POST   | `/checkout`          | Place order          |
| GET    | `/orders`            | All orders           |
| GET    | `/orders/{id}`       | Order details        |
