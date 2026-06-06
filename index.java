import java.util.*;

// ================= HRMS PROJECT =================
public class HRMS
{

    // Static block: displays welcome message when program starts
    static
    {
        System.out.println("======================================");
        System.out.println(" Welcome to our HRMS System ");
        System.out.println("======================================");
    }

    Scanner sc = new Scanner(System.in);
    Employee[] employees;
    int count = 0;

    // main(): entry point of the program execution
    public static void main(String[] args)
    {
        HRMS hrms = new HRMS();
        hrms.initialize();
        hrms.start();
    }

    // initialize(): sets employee array capacity based on user input
    void initialize() 
    {
        System.out.print("How many employees in your company: ");
        int size = getIntInput();
        employees = new Employee[size];
        System.out.println("Employee capacity set to: " + size);
    }

    // start(): runs the main menu loop and handles user choices
    void start() 
    {
        int choice;
        do
        {
            System.out.println("\n===== HUMAN RESOURCE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. View All Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Calculate Salary");
            System.out.println("6. Mark Attendance");
            System.out.println("7. Apply Leave");
            System.out.println("8. Delete Employee");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) 
            {
                case 1: addEmployee(); break;
                case 2: viewEmployee(); break;
                case 3: viewAllEmployees(); break;
                case 4: updateEmployee(); break;
                case 5: calculateSalary(); break;
                case 6: markAttendance(); break;
                case 7: applyLeave(); break;
                case 8: deleteEmployee(); break;
                case 9: System.out.println("Thank you for using HRMS."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 9);
    }

    // addEmployee(): collects details and adds a new employee to the system
    void addEmployee() 
    {

        if (count >= employees.length)
        {
            System.out.println("Employee limit reached!");
            return;
        }

        int id = (int)(Math.random() * 9000) + 1000;
        System.out.println("Generated Employee ID: " + id);

        String name;
        do {
            System.out.print("Enter Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty())
                System.out.println("Field cannot be empty!");
        } while (name.isEmpty());

        String department;
        do {
            System.out.print("Enter Department: ");
            department = sc.nextLine().trim();
            if (department.isEmpty())
                System.out.println("Field cannot be empty!");
        } while (department.isEmpty());

        double salary;
        do {
            System.out.print("Enter Salary: ");
            salary = getDoubleInput();
            if (salary <= 0)
                System.out.println("Invalid salary!");
        } while (salary <= 0);

        String mobile;
        while (true) {
            System.out.print("Enter Mobile Number: ");
            mobile = sc.nextLine().trim();

            if (!isValidMobile(mobile))
            {
                continue;
            }
            else if (isMobileExists(mobile))
            {
                System.out.println("Mobile number already exists! Enter different number.");
            }
            else {
                break;
            }
        }

        employees[count++] = new Employee(id, name, department, salary, mobile);
        System.out.println("Employee added successfully!");
    }

    // updateEmployee(): modifies selected employee information
    void updateEmployee() 
    {
        Employee e = findEmployee();
        if (e == null) return;

        System.out.println("1. Name\n2. Department\n3. Salary\n4. Mobile");
        System.out.print("Enter choice: ");
        int ch = getIntInput();

        switch (ch)
        {
            case 1:
                System.out.print("Enter new name: ");
                String n = sc.nextLine().trim();
                if (n.isEmpty()) 
                {
                    System.out.println("Field cannot be empty!");
                    return;
                }
                e.empName = n;
                break;

            case 2:
                System.out.print("Enter new department: ");
                String d = sc.nextLine().trim();
                if (d.isEmpty())
                {
                    System.out.println("Field cannot be empty!");
                    return;
                }
                e.designation = d;
                break;

            case 3:
                System.out.print("Enter new salary: ");
                double s = getDoubleInput();
                if (s <= 0)
                {
                    System.out.println("Invalid salary!");
                    return;
                }
                e.salary = s;
                break;

            case 4:
                System.out.print("Enter new mobile: ");
                String m = sc.nextLine().trim();
                if (!isValidMobile(m) || isMobileExists(m))
                {
                    System.out.println("Invalid or duplicate mobile number!");
                    return;
                }
                e.mobile = m;
                break;

            default:
                System.out.println("Invalid choice!");
        }

        System.out.println("Employee updated successfully!");
    }

    // viewEmployee(): displays details of a single employee
    void viewEmployee()
    {
        Employee e = findEmployee();
        if (e != null) e.display();
    }

    // viewAllEmployees(): displays all employees in the system
    void viewAllEmployees()
    {
        if (count == 0)
        {
            System.out.println("No employees found!");
            return;
        }
        for (int i = 0; i < count; i++)
        {
            employees[i].display();
            System.out.println("---------------------");
        }
    }

    // calculateSalary(): calculates and shows net salary of an employee
    void calculateSalary()
    {
        Employee e = findEmployee();
        if (e != null)
            e.calculateSalary(e.salary, e.empName);
    }

    // markAttendance(): increments employee attendance count
    void markAttendance()
    
    {
        Employee e = findEmployee();
        if (e != null)
        {
            e.attendance++;
            System.out.println("Attendance marked!");
        }
    }

    // applyLeave(): adds leave days for an employee
    void applyLeave() 
    {
        Employee e = findEmployee();
        if (e != null)
        {
            System.out.print("Enter leave days: ");
            e.leave += getIntInput();
            System.out.println("Leave applied!");
        }
    }

    // deleteEmployee(): removes an employee from the system
    void deleteEmployee() 
    {
        System.out.print("Enter Employee ID: ");
        int id = getIntInput();

        for (int i = 0; i < count; i++)
        {
            if (employees[i].empId == id) 
            {
                for (int j = i; j < count - 1; j++)
                    employees[j] = employees[j + 1];
                count--;
                System.out.println("Employee deleted!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    // findEmployee(): searches employee by ID and returns the object
    Employee findEmployee()
    {
        if (count == 0)
        {
            System.out.println("No employees available!");
            return null;
        }

        System.out.print("Enter Employee ID: ");
        int id = getIntInput();

        for (int i = 0; i < count; i++)
            if (employees[i].empId == id)
                return employees[i];

        System.out.println("Employee not found!");
        return null;
    }

    // isValidMobile(): validates mobile number format
    boolean isValidMobile(String mobile)
    {
        if (mobile.length() != 10) 
        {
            System.out.println("Mobile number must be exactly 10 digits!");
            return false;
        }

        char ch = mobile.charAt(0);
        if (ch != '7' && ch != '8' && ch != '9')
        {
            System.out.println("Mobile number must start with 7, 8 or 9!");
            return false;
        }

        for (int i = 0; i < mobile.length(); i++)
        {
            if (!Character.isDigit(mobile.charAt(i))) 
            {
                System.out.println("Mobile number must contain only digits!");
                return false;
            }
        }
        return true;
    }

    // isMobileExists(): checks if a mobile number already exists
    boolean isMobileExists(String mobile) 
    {
        for (int i = 0; i < count; i++)
        {
            if (employees[i].mobile.equals(mobile))
                return true;
        }
        return false;
    }

    // getIntInput(): safely reads integer input
    int getIntInput()
    {
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    // getIntInput(msg): reads integer input with custom message
    int getIntInput(String msg)
    {
        System.out.print(msg);
        return getIntInput();
    }

    // getDoubleInput(): safely reads double input
    double getDoubleInput() 
    {
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}

// ================= EMPLOYEE =================
class Employee extends Payroll
{

    int empId;
    String empName;
    String designation;
    double salary;
    String mobile;
    int attendance = 0;
    int leave = 0;

    // Employee(): constructor initializes employee object
    Employee(int id, String name, String des, double sal, String mob)
    {
        empId = id;
        empName = name;
        designation = des;
        salary = sal;
        mobile = mob;
    }

    // calculateSalary(): calls payroll salary calculation
    void calculateSalary(double basic, String name)
    {
        super.calculateSalary(basic, name);
    }

    // display(): prints employee details
    void display() {
        System.out.println("ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + designation);
        System.out.println("Salary: " + salary);
        System.out.println("Mobile: " + mobile);
        System.out.println("Attendance: " + attendance);
        System.out.println("Leave: " + leave);
    }
}

// ================= PAYROLL =================
class Payroll
{

    // calculateSalary(): calculates net salary using allowances and deductions
    void calculateSalary(double basic, String name) 
    {
        double hra = basic * 0.20;
        double da = basic * 0.10;
        double pf = basic * 0.05;

        System.out.println("\nEmployee: " + name);
        System.out.println("Net Salary: " + (basic + hra + da - pf));
    }
}
