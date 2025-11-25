# Cloud Computing Platform – Java GUI Project

A role-based cloud resource management platform that allows organizations to deploy, manage, and monitor their cloud services.  
The project is developed using **Java Swing (GUI), MySQL, JDBC & Core OOP principles**, with a modular and industry-style architecture.

---

## ⭐ Features

### 🔹 Admin Module
- Manage platform users  
- View deployed resources  
- Real-time monitoring (Multithreaded)  
- Support request management (coming in Review–2)  
- Billing & invoices (coming in Review–2)

### 🔹 User Module
- Deploy & manage personal cloud resources  
- Resource monitoring  
- Support request creation  
- Billing & usage history  
- Profile management

---

## 🏗 Architecture Overview (MVC Layered)

```
/src
├── models
│   ├── User
│   ├── AdminUser
│   ├── NormalUser
│   └── Resource
├── database
│   └── DatabaseManager
├── ui
│   ├── LoginPage
│   ├── AdminDashboard
│   └── UserDashboard
└── utils
    ├── DBConfig
    └── InvalidLoginException
```

---

## 🗄 Database Schema (MySQL)

**Database name:** `cloud_platform`

| Table | Purpose |
|--------|--------|
| users | stores login and role information |
| resources | tracks user-deployed cloud resources |
| support_tickets | (Upcoming) user support requests |
| invoices | (Upcoming) billing history |

📌 SQL file used for creation: `cloud_platform.sql`

---

## 🧰 Technology Stack

| Layer | Tools |
|-------|-------|
| Frontend | Java Swing |
| Backend | Core Java (OOP + Collections + Multithreading) |
| Database | MySQL |
| Connectivity | JDBC |
| IDE | IntelliJ IDEA |
| Version Control | Git + GitHub |

---

## 🔑 Key Java Concepts Used

| Concept | Where Used |
|--------|------------|
| Inheritance | AdminUser & NormalUser extend User |
| Polymorphism | authenticateUser() returns subclass object |
| Interface | Dashboard interface implemented by both dashboards |
| Exception Handling | InvalidLoginException in LoginPage |
| Collections & Generics | List<User> & List<Resource> in DatabaseManager + Users tab |
| Multithreading | Monitoring tab in AdminDashboard uses live background thread |

✔ **100% Review–1 marking rubric concepts complete**

---

## ▶️ How to Run

1. Clone the repository  
2. Import the project into **IntelliJ IDEA**  
3. Add MySQL Connector JAR in `/lib`  
4. Update DB credentials in: `DBConfig.java`  
5. Import database using `cloud_platform.sql`  
6. Run main class: `src/ui/LoginPage.java`

### 🔐 Sample Credentials
**Admin Login**  
Email: `admin@example.com`  
Password: `******`

**User Login**  
Email: `user@example.com`  
Password: `*******`

(If not inserted, add via SQL script)

---

## 🚦 Development Status

| Review Stage | Status |
|-------------|--------|
| Review-0 | ✔ Completed |
| Review-1 | ✔ Completed |
| Review-2 | 🔄 CRUD + functional modules (next)  🔄 Final polishing + deployment |

---

## 🍳 Cooked By

| Name | Role |
|------|------|
| Ankit Kumar | Full Stack Developer |

---

## 🌟 Notes
This project strictly follows **college cloud computing project guidelines** and the **Java GUI-based marking rubric**.  
The codebase is designed to be highly scalable for future cloud computing functions.

---

## 💬 Final Request
**Please kindly check my code.  
If you have any suggestions, corrections, or improvements — please notify me.  
Thank you for your valuable suggestions — _Ankit (Developer)._**
