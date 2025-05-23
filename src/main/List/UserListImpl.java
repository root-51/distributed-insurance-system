package main.List;

import java.util.ArrayList;
import java.util.Iterator;
import main.Employee.User;

public class EmployeeListImpl implements EmployeeList {

	public ArrayList<User> employees;

	public EmployeeListImpl() {
		this.employees = new ArrayList<User>();
	}

	/**
	 *
	 * @param employeeID
	 */
	public boolean delete(String employeeID) {
		Iterator<User> iterator = employees.iterator();
		while (iterator.hasNext()) {
			User employee = iterator.next();
			if (employee.getUserID().equals(employeeID)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param employee
	 */
	public boolean insert(User employee) {
		return this.employees.add(employee);
	}

	/**
	 *
	 * @param employeeID
	 */
	public User search(String employeeID) {
		for (User employee : employees) {
			if (employee.getUserID().equals(employeeID)) {
				return employee;
			}
		}
		return null;
	}

	/**
	 *
	 * @param updatedEmployee
	 */
	public boolean update(User updatedEmployee) {
		for (int i = 0; i < employees.size(); i++) {
			User existingCustomer = employees.get(i);
			if (existingCustomer.getUserID().equals(updatedEmployee.getUserID())) {
				employees.set(i, updatedEmployee);
				return true;
			}
		}
		return false;
	}

}