package main;

import main.Employee.Employee;
import main.List.*;

public class Main {

	private static Employee loginedEmployee;
	private static Menu menu;
	private static LoadData loadData;
	private static CustomerListImpl customerList;
	private static EmployeeListImpl employeeList;
	private static ContractList contractList;
	private static InsuranceProductListImpl insuranceProductList;

	public static void main(String[] args) {
		customerList = new CustomerListImpl();
		employeeList = new EmployeeListImpl();
		insuranceProductList = new InsuranceProductListImpl();
		contractList = new ContractListImpl();

		loginedEmployee = login("4");

		menu = new Menu(customerList, employeeList, insuranceProductList, contractList, loginedEmployee);
		while (true) {
			menu.printMainMenu();
			int selectedMenu = menu.getUserSelectInt();
			menu.excuteSelectedMenu(selectedMenu);
		}

	}

	public static Employee login(String loginID) {
		return employeeList.search(loginID);
	}

}
