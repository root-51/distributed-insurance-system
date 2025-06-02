package main;

import main.DAO.DAO;
import main.Employee.User;
import main.List.*;

public class Main {

	private static User loginedUser;
	private static SystemManager menu;

	private static LoadData loadData;
	private static CustomerList customerList;
	private static InsuranceProductList insuranceProductList;
	private static ContractList contractList;

	public static void main(String[] args) {
		customerList = new CustomerListImpl();
		insuranceProductList = new InsuranceProductListImpl();
		contractList = new ContractListImpl();
//		loadData = new LoadData(customerList, employeeList,insuranceProductList);
//
//		loadData.loadCustomerData();
//		loadData.loadEmployeeData();
//		loadData.loadInsuranceProductData();
//		loadData.loadContractData();

		loginedUser = login("soyun");

		menu = new SystemManager(customerList, insuranceProductList, contractList,
				loginedUser);
		while (true) {
			menu.printMainMenu();
			int selectedMenu = menu.getUserSelectInt();
			menu.excuteSelectedMenu(selectedMenu);
		}

	}

//	public static User login(String loginID) {
//		return employeeList.search(loginID);
//	}

	public static User login(String userID) {
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM user WHERE user_id = ?",userID).toUser();
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

}
