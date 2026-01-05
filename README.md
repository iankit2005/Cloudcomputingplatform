# Cloud Computing Platform – Java Web Application

A role-based cloud resource management platform that allows users to deploy, manage, and monitor cloud resources, while administrators control execution, monitor usage, and analyze billing reports.

This project is developed using **Java Servlets, JSP, MySQL, JDBC, Core Java (OOP), and Multithreading**, following a **layered MVC architecture** as per college cloud computing project guidelines.

---

## 1. Project Overview

The Cloud Computing Platform simulates a real-world cloud environment where:

- Users can deploy and manage personal cloud resources.
- Administrators can monitor all system resources.
- Resource execution is handled asynchronously using multithreading.
- Usage-based billing is calculated per minute.
- Input validation, authentication, and exception handling are implemented.

---

## 2. Features

### 2.1 Admin Module
- Secure admin login
- View all deployed resources (VM & Database)
- Start / Stop resources asynchronously (Thread Pool)
- Real-time monitoring dashboard
- Admin analytics dashboard:
  - Total revenue
  - Total usage minutes
  - Running resources count
  - Stopped resources count
- System-wide billing report
- Admin audit logging

### 2.2 User Module
- Secure user authentication
- Deploy cloud resources
- Input validation (UI + backend)
- View deployed resources and status
- Usage-based billing history
- User dashboard with statistics

---

## 3. Technology Stack

| Layer | Technology |
|------|-----------|
| Frontend | JSP, HTML, CSS |
| Backend | Java Servlets |
| Business Logic | Core Java |
| Database | MySQL |
| Connectivity | JDBC |
| Server | Apache Tomcat |
| IDE | IntelliJ IDEA |
| Version Control | Git & GitHub |

---

## 4. Architecture

The project follows a **Layered MVC Architecture**:

- **Model Layer** – Java model classes
- **View Layer** – JSP pages
- **Controller Layer** – Servlets
- **Service Layer** – Business logic
- **Database Layer** – JDBC via DatabaseManager

This ensures scalability, maintainability, and clean separation of concerns.

---

## 5. Project Structure

# Cloud Computing Platform – Java Web Application

A role-based cloud resource management platform that allows users to deploy, manage, and monitor cloud resources, while administrators control execution, monitor usage, and analyze billing reports.

This project is developed using **Java Servlets, JSP, MySQL, JDBC, Core Java (OOP), and Multithreading**, following a **layered MVC architecture** as per college cloud computing project guidelines.

---

## 1. Project Overview

The Cloud Computing Platform simulates a real-world cloud environment where:

- Users can deploy and manage personal cloud resources.
- Administrators can monitor all system resources.
- Resource execution is handled asynchronously using multithreading.
- Usage-based billing is calculated per minute.
- Input validation, authentication, and exception handling are implemented.

---

## 2. Features

### 2.1 Admin Module
- Secure admin login
- View all deployed resources (VM & Database)
- Start / Stop resources asynchronously (Thread Pool)
- Real-time monitoring dashboard
- Admin analytics dashboard:
  - Total revenue
  - Total usage minutes
  - Running resources count
  - Stopped resources count
- System-wide billing report
- Admin audit logging

### 2.2 User Module
- Secure user authentication
- Deploy cloud resources
- Input validation (UI + backend)
- View deployed resources and status
- Usage-based billing history
- User dashboard with statistics

---

## 3. Technology Stack

| Layer | Technology |
|------|-----------|
| Frontend | JSP, HTML, CSS |
| Backend | Java Servlets |
| Business Logic | Core Java |
| Database | MySQL |
| Connectivity | JDBC |
| Server | Apache Tomcat |
| IDE | IntelliJ IDEA |
| Version Control | Git & GitHub |

---

## 4. Architecture

The project follows a **Layered MVC Architecture**:

- **Model Layer** – Java model classes
- **View Layer** – JSP pages
- **Controller Layer** – Servlets
- **Service Layer** – Business logic
- **Database Layer** – JDBC via DatabaseManager

This ensures scalability, maintainability, and clean separation of concerns.

---

## 5. Project Structure

Cloudcomputingplatform/
│
├── src/main/java/
│ ├── database/
│ │ └── DatabaseManager.java
│ │
│ ├── models/
│ │ ├── User.java
│ │ ├── AdminUser.java
│ │ ├── NormalUser.java
│ │ ├── Resource.java
│ │ ├── ResourceUsage.java
│ │ └── UsageRecord.java
│ │
│ ├── service/
│ │ ├── UserService.java
│ │ ├── ResourceService.java
│ │ ├── BillingService.java
│ │ ├── AdminBillingService.java
│ │ ├── AdminDashboardService.java
│ │ └── ResourceTask.java
│ │
│ ├── servlet/
│ │ ├── LoginServlet.java
│ │ ├── LogoutServlet.java
│ │ ├── AuthFilter.java
│ │ ├── ResourceServlet.java
│ │ ├── UserBillingServlet.java
│ │ ├── AdminResourceServlet.java
│ │ ├── AdminBillingServlet.java
│ │ ├── AdminDashboardServlet.java
│ │ ├── AdminAuditServlet.java
│ │ └── AdminBillingExportServlet.java
│ │
│ └── utils/
│ ├── DBConfig.java
│ ├── InputValidator.java
│ ├── AppLogger.java
│ ├── InvalidLoginException.java
│ └── TestDB.java
│
├── src/main/webapp/
│ ├── WEB-INF/
│ │ ├── lib/
│ │ │ └── mysql-connector-j-9.5.0.jar
│ │ └── web.xml
│ │
│ ├── index.jsp
│ ├── login.jsp
│ ├── userDashboard.jsp
│ ├── user-billing.jsp
│ ├── admin-dashboard.jsp
│ ├── adminDashboard.jsp
│ ├── admin-resources.jsp
│ ├── admin-billing.jsp
│ └── admin-audit.jsp
│
├── .gitignore
├── Cloudcomputingplatform.iml
└── README.md



---

## 6. Database Design

**Database Name:** `cloud_platform`

### Tables

| Table | Purpose |
|------|--------|
| users | Stores user credentials and roles |
| resources | Stores deployed resources |
| resource_usage | Tracks start/stop time and billing |
| support_tickets | Planned (future enhancement) |
| invoices | Planned (future enhancement) |

### Billing Logic
- Cost per minute: **₹1.6**
- Duration calculated using MySQL `TIMESTAMPDIFF(MINUTE)`

---

## 7. Java Concepts Used

| Concept | Usage |
|------|------|
| OOP | Overall design |
| Inheritance | AdminUser, NormalUser → User |
| Polymorphism | Role-based authentication |
| Encapsulation | Model classes |
| Exception Handling | Login & validation |
| Multithreading | Resource start/stop |
| Collections | List, ArrayList |
| JDBC | Database operations |
| MVC | Servlet + JSP |

---

## 8. How to Run

1. Clone the repository
2. Open project in IntelliJ IDEA
3. Add MySQL Connector JAR to `WEB-INF/lib`
4. Configure database credentials in `DBConfig.java`
5. Import database schema into MySQL
6. Deploy project on Apache Tomcat
7. Access application via browser

---

## 9. Sample Credentials

### Admin
- Email: admin@example.com
- Password: ******

### User
- Email: user@example.com
- Password: ******

---

## 10. Project Status

| Review Phase | Status |
|-------------|--------|
| Review-0 | Completed |
| Review-1 | Completed |
| Review-2 | Core features implemented |
| Final Review | UI polishing & optimization |

---

## 11. Future Enhancements

- Support ticket system
- Invoice generation (PDF)
- Email notifications
- Advanced analytics
- Cloud deployment

---

## 12. Developer

**Name:** Ankit Kumar  
**Role:** Java Developer  
**Program:** B.Tech CSE (Cloud Computing)

---

## 13. Notes

- Project strictly follows college cloud computing guidelines.
- Designed for maximum evaluation score.
- Scalable and extensible architecture.

