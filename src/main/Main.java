package main;

import main.Employee.Employee;
import main.List.*;

public class Main {

	private static Employee loginedEmployee;
	private static SystemManager menu;
	private static LoadData loadData;
	private static CustomerListImpl customerList;
	private static EmployeeListImpl employeeList;
	private static InsuranceProductList insuranceProductList;
	private static ContractList contractList;

	public static void main(String[] args) {
		customerList = new CustomerListImpl();
		employeeList = new EmployeeListImpl();
		insuranceProductList = new InsuranceProductListImpl();
		contractList = new ContractListImpl();
		loadData = new LoadData(customerList, employeeList);

		loadData.loadCustomerData();
		loadData.loadEmployeeData();
		loadData.loadInsuranceProductData();
		loadData.loadContractData();

		loginedEmployee = login("4");

		menu = new SystemManager(customerList, employeeList, insuranceProductList, contractList, loginedEmployee);
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
