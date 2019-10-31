# Billsoft
This project is a replication of an Invoicing software used in Restaurants to generate invoices for each order.
A Login Credential is required for the operation of this software and there are 2 types of logins.

1.	Admin Login
An Admin has access to all the features of the software and he cannot be registered from the main login screen. An Admin can-
•	Upload an excel sheet for the New Inventory.
•	View and Delete current employee records from the software along with there login access.
•	View all the inventory left.
•	Generate invoices for orders.

2.	Employee Login
An Employee can register himself on the main login screen by clicking on the register button and they can log themselves in. But they will be restricted to only a few features of the software like Generating Invoices and Viewing the Inventory List. An Employee cannot view and delete other employees and cannot upload the New Inventory details.


The project uses MySQL to hold records for the employees, admins and the products and Swing for displaying a GUI (Graphical User Interface). Java has been connected to MySQL through a JDBC driver and new inventory can be uploaded to the Database by keeping their records in a Microsoft Excel Workbook. To accomplish this task external Java libraries are used called Apache POI.
 
