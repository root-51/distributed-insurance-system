package main;

import main.Employee.Customer;
import java.util.HashMap;

import main.Employee.Employee.EmployeeType;
import main.Employee.LossAdjuster;
import main.Employee.ProductManagement;
import main.Employee.Sales;
import main.Enum.Sex;
import main.List.CustomerListImpl;
import main.List.EmployeeListImpl;
import main.List.InsuranceProductList;
import main.List.InsuranceProductListImpl;

public class LoadData {
	private CustomerListImpl customerList;
	private EmployeeListImpl employeeList;
	private InsuranceProductList insuranceProductList;
	
	public LoadData( CustomerListImpl customerList, EmployeeListImpl employeeList, InsuranceProductListImpl insuranceProductList) {
		this.customerList = customerList;
		this.employeeList = employeeList;
		this.insuranceProductList = insuranceProductList;
	}
	public void loadCustomerData() {
		Customer customer1 = new Customer.Builder().accountNumber("333377645328").address("서울특별시 서대문구 북가좌동").age(23)
				.customerID(Integer.toString(0)).job("대학생").name("장소윤").rrn("60221340").sex(Sex.FEMALE)
				.phoneNumber("01077645328").build();
		Customer customer2 = new Customer.Builder().accountNumber("333346965328").address("서울특별시 서대문구 남가좌동").age(23)
				.customerID(Integer.toString(1)).job("대학생").name("박솔민").rrn("60221333").sex(Sex.FEMALE)
				.phoneNumber("01010001000").build();
		Customer customer3 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(2)).job("대학생").name("이종민").rrn("60211111").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		
		customerList.insert(customer1);
		customerList.insert(customer2);
		customerList.insert(customer3);
	}

	public void loadEmployeeData() {
		for (int i = 0; i < 3; i++) { // add temp three Sales
			int numOfEmployees = employeeList.employees.size();
			Sales sales = new Sales(numOfEmployees, EmployeeType.Sales, customerList);
			employeeList.insert(sales);
		}
		for (int i = 0; i < 3; i++) { // add temp three Sales
			int numOfEmployees = employeeList.employees.size();
			ProductManagement productManagement = new ProductManagement(numOfEmployees, EmployeeType.ProductManagement, customerList);
			employeeList.insert(productManagement);
		}
		for (int i = 0; i < 3; i++) { // add temp three lossAdjuster
			int numOfEmployees = employeeList.employees.size();
			LossAdjuster lossAdjuster = new LossAdjuster(numOfEmployees, EmployeeType.LossAdjuster);
			lossAdjuster.genrateDummy(10);
			employeeList.insert(lossAdjuster);
		}

	}

	private HashMap<String, String> parseCoverageJson(String json) {
		HashMap<String, String> map = new HashMap<>();
		json = json.trim();
		if(json.startsWith("{"))
			json = json.substring(1);
		if(json.endsWith("}"))
			json = json.substring(0,json.length()-1);
		String[] pairs = json.split(",");
		for(String pair : pairs){
			String[] keyValue = pair.split(":");
			if(keyValue.length==2){
				String key = keyValue[0].trim().replace("^\"|\"$", "");
				String value = keyValue[1].trim().replaceAll("^\"|\"$", "");
				map.put(key,value);
			}
		}
		return map;
	}
	public void loadContractData() {
		
	}
}
