package main;

import main.Employee.User;
import main.List.*;

import java.awt.*;
import java.sql.SQLException;

public class Main {

	private static User loginedUser;
	private static SystemManager menu;
	private static CustomerListImpl customerList;
	private static UserListImpl userList;
	private static ContractList contractList;
	private static InsuranceProductListImpl insuranceProductList;

	public static void main(String[] args) throws SQLException {
		customerList = new CustomerListImpl();
		userList = new UserListImpl();
		insuranceProductList = new InsuranceProductListImpl();
		contractList = new ContractListImpl();

		LoginMenu loginMenu = new LoginMenu();
		loginedUser = loginMenu.login();

		menu = new SystemManager(customerList, userList, insuranceProductList, contractList, loginedUser);
		while (true) {
			menu.printMainMenu();
			int selectedMenu = menu.getUserSelectInt();
			menu.excuteSelectedMenu(selectedMenu);
		}

	}

}
