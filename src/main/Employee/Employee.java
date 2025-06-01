package main.Employee;

public class Employee {

	private String employeeID;
	private EmployeeType employeeType;

	public enum EmployeeType {
		Sales, UnderWriter, ProductManagement, LossAdjuster
	}

	public Employee(int numOfEmployees, EmployeeType employeeType) {
		this.employeeID = Integer.toString(numOfEmployees+1);
		this.employeeType = employeeType;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

}