# Nexus Pizza

A responsive pizza shop website built with Java 25, Spring Boot, Spring Security, Thymeleaf, Spring Data JPA, Bootstrap 5, Chart.js and a JSON REST API. It uses the **Pizza** database from the course's database collection (tables: pizza, category, orders).

The project was developed from the lecturer's AUTHENTICATION lesson solution (an employee portal). Some technical names such as the package `com.example.employeeportal` come from that starting point; in this application they handle the Pizza data.

**Live site:** http://rivendell.nje.hu:9443/cw6nlt/

## Features
- Home page with hero banner, statistics, bestsellers and contact information
- Database page: pizzas, categories and recent orders (three related tables, Spring Data repositories)
- Order page: choose a pizza and an amount, the order is saved in the orders table
- Contact form with server-side validation, saved to the database
- Messages page for logged-in users, newest message first, with sending time
- Chart page with Chart.js (pizzas per category, loaded from the REST endpoint)
- Admin-only CRUD for pizzas
- Registration, login, logout with BCrypt passwords and three roles: visitor, registered user, admin
- REST API for pizzas

## Roles
| Role | Can use |
|---|---|
| Visitor (not logged in) | Home, Database, Contact, Order, Chart, REST API, Login, Register |
| Registered user | everything above plus Messages |
| Admin | everything above plus CRUD |

Admin username: `admin`. The password is taken from the environment variable `APP_ADMIN_PASSWORD`; if it is not set, the development default described in the documentation is used.

## Requirements
- JDK 25
- Maven wrapper included (`mvnw`, `mvnw.cmd`)
- Internet access on the first build (dependencies) and for Bootstrap, Chart.js and the images

## Run locally
1. Open the project folder in VS Code.
2. Run `.\mvnw.cmd spring-boot:run`
3. Open http://localhost:8080

Local data is stored in H2 (see `application.properties`).

## Build the WAR for the server
`.\mvnw.cmd clean package -DskipTests`

Output: `target/cw6nlt.war`. The WAR is uploaded to Tomcat on rivendell.nje.hu; the server uses MySQL (`application-server.properties`).

## REST API
| Method | URL | Description |
|---|---|---|
| GET | `/api/pizzas` | list all pizzas |
| GET | `/api/pizzas/{pname}` | one pizza |
| POST | `/api/pizzas` | create a pizza |
| PUT | `/api/pizzas/{pname}` | update a pizza |
| DELETE | `/api/pizzas/{pname}` | delete a pizza |
| GET | `/api/chart/pizzas-by-category` | chart data |

Example with cURL:

```
curl -i http://localhost:8080/api/pizzas
curl -i http://localhost:8080/api/pizzas/Hawaii
```

## Author
Abdurrahman Umar Baba (Neptun: cw6nlt)