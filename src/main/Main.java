package main;

import main.Employee.User;
import main.List.*;

public class Main {

	private static User loginedUser;
	private static SystemManager menu;

	private static LoadData loadData;
	private static CustomerListImpl customerList;
	private static UserListImpl userList;
	private static InsuranceProductList insuranceProductList;
	private static ContractList contractList;

	public static void main(String[] args) {
		customerList = new CustomerListImpl();
		userList = new UserListImpl();
		insuranceProductList = new InsuranceProductListImpl();
		contractList = new ContractListImpl();
		loadData = new LoadData(customerList, userList,insuranceProductList);

		loadData.loadCustomerData();
		loadData.loadEmployeeData();
		loadData.loadInsuranceProductData();
		loadData.loadContractData();

		loginedUser = login("4");

		menu = new SystemManager(customerList, userList, insuranceProductList, contractList, loginedUser);
		while (true) {
			menu.printMainMenu();
			int selectedMenu = menu.getUserSelectInt();
			menu.excuteSelectedMenu(selectedMenu);
		}

	}

	public static User login(String loginID) {
		return userList.search(loginID);
	}

}
