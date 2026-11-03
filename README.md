# Nexus Portal — Spring Boot Seminar Project

A responsive employee/department/project portal built with Java, Spring Boot, Spring Security, Thymeleaf, Spring Data JPA, H2 for local development, MySQL for server deployment, Chart.js, and a JSON REST API.

## Features
- Public home, database, contact, chart, login and registration pages
- Three data tables: employees, departments, projects
- Server-side contact form validation and persisted messages
- Authenticated Messages page, newest first with timestamp
- Admin-only employee CRUD
- REST API: `GET/POST /api/employees`, `GET/PUT/DELETE /api/employees/{id}`
- Chart endpoint: `GET /api/chart/employees-by-department`
- WAR packaging for external Tomcat

## Requirements
- JDK 21 or newer (JDK 25 can run this project)
- Maven 3.9+
- Internet access on first build to download dependencies

## Run locally in VS Code
1. Extract the ZIP and open the `employee-portal` folder in VS Code.
2. Let the Java and Maven extensions import the project.
3. In a terminal run `.\mvnw.cmd spring-boot:run` (or run `EmployeePortalApplication.java`).
4. Open `http://localhost:8080`.
5. Local H2 data is stored in `./data/employeeportal`.

Default local admin username: `admin`.
Set `APP_ADMIN_PASSWORD` before first startup to override the development password. If not set, the starter uses `ChangeMe-Now-2026!`. Change it immediately and never use the development default on a public server. The seeded password is only created when the admin user does not already exist.

## Local test accounts
- Admin: `admin` / development default described above
- Registered visitor: use Register to create an account

## Build WAR
`.\mvnw.cmd clean package`
Output: `target/employee-portal.war`

## University Linux/Tomcat deployment
The assignment requires the WAR name to match your Neptun code, and deployment to `http://rivendell.nje.hu:9443/`. Configure `application-server.properties` and environment variables for the assigned MySQL database, then build with:

`.\mvnw.cmd clean package -DskipTests`

For the server profile set `SPRING_PROFILES_ACTIVE=server`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `APP_CONTEXT` as required by the university. Rename the generated WAR to `<lowercase-neptun-code>.war` before uploading. The server may require the app context to match the WAR filename. Confirm this with the course handout. Do not commit real passwords to GitHub.

## Before submission — important course-specific checks
1. The lecturer requires selecting one database from the course's Google Drive collection. This starter's demo schema is a simple employee portal and is not a confirmed selection from that folder. Replace/adapt the schema to the approved dataset, retaining at least three tables.
2. The assignment mandates developing from the `AUTHENTICATION` lesson solution. Compare this implementation with the provided lesson files and incorporate the prescribed starter solution if required.
3. Confirm Java/Tomcat compatibility and server context-path requirements with the course instructions.
4. Add the names and Neptun codes of both group members at the bottom of every page (the footer currently contains a placeholder).
5. Use Git commits throughout development, at least five partial stages, and document each team member's contribution.
6. Test the REST API with both cURL and Postman and take screenshots.
7. Prepare a minimum 10-page PDF report and submit the PDF to Teams. Each group member submits their own report.

## API examples
List employees:
`curl -i http://localhost:8080/api/employees`
Get one:
`curl -i http://localhost:8080/api/employees/1`
Create employee (local CSRF does not apply to API, but employee department ID must exist):
`curl -i -X POST http://localhost:8080/api/employees -H "Content-Type: application/json" -d '{"fullName":"Example Person","email":"example@example.com","jobTitle":"Analyst","department":{"id":1},"salary":200000}'`

## GitHub
Create a public repository, initialize this folder as the repository, make genuine incremental commits, invite the teammate, and use branches/pull requests where possible. Keep passwords and server credentials out of source control.


## Maven Wrapper (no global Maven installation)

On Windows, run `.\mvnw.cmd spring-boot:run` from this folder. On macOS/Linux, run `./mvnw spring-boot:run`. The first run downloads Maven 3.9.11 into your user profile cache and downloads project dependencies. Java and internet access are required; Maven does not need to be installed globally or added to PATH.






i updated the database to use pizza DB instead of custom data base  for employee portal