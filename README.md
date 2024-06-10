# BankConnect

BankConnect is a distributed system designed for banks to streamline customer interactions. The system allows customers to log in at a kiosk or terminal and employees to call them, enhancing the efficiency of customer service and reducing wait times.

## Features

- **Customer Login:** Customers can log in using a simple interface at a dedicated kiosk or terminal.
- **Employee Call System:** Employees can call logged-in customers from their workstations, ensuring smooth and efficient service.
- **Distributed Architecture:** The system is designed to be distributed, providing robustness and scalability.

## Components

1. **Customer Terminal:**
   - User-friendly login interface for customers.
   - Displays customer information and login status.

2. **Employee Workstation:**
   - Interface for employees to view logged-in customers.
   - Option to call customers to service desks.

3. **Backend Server:**
   - Manages customer and employee interactions.
   - Handles data storage and retrieval.
   - Ensures secure and efficient communication between terminals and workstations.
   - Supports passive redundancy with a secondary server that can take over in case the primary server fails.

