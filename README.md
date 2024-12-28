# Salary Slip Generation System

The **Salary Slip Generation System** is a web application developed to automate and streamline the process of generating and managing salary slips for university staff. This system provides a user-friendly interface for both **Administrators** and **Users**, ensuring efficient salary management and smooth operation of administrative tasks.

## Features

### 1. **Admin Dashboard**
Admins have access to a comprehensive dashboard where they can manage user accounts, calculate salaries, and generate salary slips for staff.

### 2. **Salary Calculation and Generation**
Admins can easily calculate the salary components (Gross Salary, Deductions) and generate salary slips in PDF format.

### 3. **Gross Salary Calculation**
The system allows admins to calculate the gross salary based on user input such as basic pay, allowances, etc.

### 4. **Deduction Salary Calculation**
Admins can calculate the deduction salary based on the gross salary, including taxes, insurance, and other deductions.

### 5. **Salary Bill Generation**
Admins can generate salary bills for specific employees. These bills include both zero and non-zero fields for clear and transparent salary records.

### 6. **Salary Slip Generation**
The system generates detailed salary slips that include only the non-zero values, making them concise and informative.

### 7. **PDF Generation**
Once the salary slip is generated, it can be converted to a PDF format for easy sharing and distribution.

### 8. **Email Notifications**
Salary slips are automatically sent to employees via email for seamless delivery.

### 9. **Report Generation**
Admins can generate detailed reports for specific departments or sections and download them in PDF format.

### 10. **Edit Calculation Percentages**
Admins have the ability to modify salary calculation percentages for accurate payroll processing.

### 11. **User Registration and Login**
Users can register, log in, and access their salary information. They can view and download their salary slips and update personal details as needed.

---

## Screenshots

### Home Page
![Home Page](HomePage.jpeg)

### Admin Dashboard
Admins can manage user accounts, calculate salaries, and generate salary slips.  
![Admin Dashboard](AdminPage.jpeg)

### Salary Calculation and Generation
Admins can perform salary calculations and generate salary slips in PDF format.  
![CG](CG.jpeg)

### Gross Salary Calculation
Admins can calculate the gross salary based on user input.  
![Gross Calculation Page](GrossCalculation.png)

### Deduction Salary Calculation
Admins can calculate deductions based on the gross salary.  
![Deduction Calculation Page](DeductionCalculation.png)

### Salary Bill Generation
Admins can generate salary bills for individual employees, showing both zero and non-zero fields.  
![SB1](SB(1).jpeg)  
![SB2](SB(2).jpeg)

### Salary Slip Generation
Salary slips are generated, showing only non-zero values for accuracy.  
![SS1](SS(1).jpeg)  
![SS2](SS(2).jpeg)

### PDF Generation
Salary slips are converted to PDF for easy sharing.  
![PDF Generation Page](PDFConversionPage.jpeg)

### Email Notifications
Admins can send salary slips to employees directly via email.  
![Email Sharing Page](EmailSharingPage.jpeg)

### Report Generation
Admins can generate department-specific reports and download them as PDFs.  
![Report Generation Page](Report(1).jpeg)  
![Report Generation Page](Report(2).jpeg)

### Calculation Percentage Editing
Admins can edit salary calculation percentages to ensure correct payroll processing.  
![CP](CP.jpeg)

### User Dashboard
Users can view and download their salary slips, as well as update their details.  
![User Dashboard](UserPage.jpeg)  
![User Dashboard](VS.jpeg)  
![User Dashboard](VD.jpeg)  
![User Dashboard](UD.jpeg)

---

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: HTML, CSS
- **Database**: MySQL
- **IDE**: IntelliJ IDEA

---

## How to Run the Project

1. Clone the repository:  
   `git clone https://github.com/your-username/Salary-Slip-Generation-System.git`

2. Install the required dependencies:  
   Navigate to the project directory and run:  
   `mvn install`

3. Set up the database:  
   Import the SQL file (located in the `database/` folder) into your MySQL database.

4. Run the application:  
   Use IntelliJ IDEA or your preferred IDE to run the Spring Boot application.

5. Access the application:  
   Open your browser and navigate to `http://localhost:8080`.

---

## Contributing

Contributions are always welcome! If you'd like to contribute, feel free to fork the repository, make your changes, and submit a pull request. Please ensure your code adheres to the project's coding style and includes appropriate tests.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
