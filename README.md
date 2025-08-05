# ⚡ Electricity Bill Management System

A web-based application designed to manage electricity bills for consumers and administrators. Built using **Java**, **JSP**, **Servlets**, and **MySQL**, this system allows seamless handling of bill generation, payment tracking, and user management.


## 🚀 Features

### 👤 User Features
- View personal billing history
- Download and pay bills online
- Track payment status (paid/pending)
- Check due amounts and deadlines

### 🛠️ Admin Features
- Create and manage customer records
- Generate new bills for customers
- View and filter all unpaid bills
- Track total pending amounts


## 🧱 Tech Stack

| Layer          | Technology         |
|-|--|
| Frontend       | HTML, CSS, JSP     |
| Backend        | Java (Servlets, JSP)|
| Database       | MySQL              |
| Server         | Apache Tomcat      |


## 📁 Project Structure

ElectricityBillManagement/
├── src/
│   ├── com/
│   │   ├── Model/        # POJO classes like Bill.java, Customer.java
│   │   ├── Controller/   # Servlets to handle business logic
│   │   └── DAO/          # Database access layer
├── WebContent/
│   ├── pages/            # JSP pages (dashboard, login, billing)
│   ├── styles/           # CSS files
│   ├── scripts/          # JS files
│   ├── WEB-INF/
│   │   └── tags/         # Reusable JSP tag files (header, footer, sidebar)
│   └── index.jsp         # Entry point
├── database/
│   └── schema.sql        # DB setup script
└── README.md

## 🖥️ How to Run Locally

### 🔧 Prerequisites

* Java JDK 8+
* Apache Tomcat 9+
* MySQL Server
* IDE (Eclipse / IntelliJ / VS Code with Java)

### 📦 Setup Instructions

1. **Clone the repo:**

   bash
   git clone https://github.com/yourusername/ElectricityBillManagement.git
   

2. **Import the project** into Eclipse/IntelliJ as a Dynamic Web Project.

3. **Set up the database:**

   * Run the SQL script in database/schema.sql on your MySQL server.
   * Update DB credentials in your DAO or DBConnection class.

4. **Deploy to Tomcat:**

   * Right-click the project → Run on Server → Select Apache Tomcat.

5. **Access the app:**

   * Open your browser and go to http://localhost:8080/ElectricityBillManagement/

## 📚 Future Enhancements

* Add payment gateway integration (UPI/Credit Card)
* Add charts and analytics for consumption
* OTP/email verification for account creation
* REST API version for mobile apps

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).


