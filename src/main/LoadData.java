package main;

import main.Data.Customer;

import main.Data.InsuranceProduct;

import main.Employee.LossAdjuster;
import main.Employee.ProductManagement;
import main.Employee.Sales;
import main.Employee.User.UserType;
import main.Enum.Sex;
import main.List.CustomerListImpl;

import main.List.InsuranceProductList;
import main.List.UserListImpl;

import java.util.HashMap;

public class LoadData {
	private CustomerListImpl customerList;
	private UserListImpl userList;
	private InsuranceProductList insuranceProductList;
	
	public LoadData(CustomerListImpl customerList,UserListImpl userList,InsuranceProductList insuranceProductList) {
		this.customerList = customerList;
		this.userList = userList;
		this.insuranceProductList = insuranceProductList;
	}
	public void loadCustomerData() {
		Customer customer1 = new Customer.Builder().accountNumber("333377645328").address("서울특별시 서대문구 북가좌동").age(23)
				.customerID(Integer.toString(1)).job("대학생").name("장소윤").rrn("60221340").sex(Sex.FEMALE)
				.phoneNumber("01077645328").build();
		Customer customer2 = new Customer.Builder().accountNumber("333346965328").address("서울특별시 서대문구 남가좌동").age(23)
				.customerID(Integer.toString(2)).job("대학생").name("박솔민").rrn("60221333").sex(Sex.FEMALE)
				.phoneNumber("01010001000").build();
		Customer customer3 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(3)).job("대학생").name("이종민").rrn("60211111").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer4 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(4)).job("대학생").name("김가가").rrn("60211112").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer5 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(5)).job("대학생").name("김나나이로무지개").rrn("60211113").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer6 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(6)).job("대학생").name("김다다다").rrn("60211114").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer7 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(7)).job("대학생").name("김라라의스타일기").rrn("60211115").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer8 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(8)).job("대학생").name("김마마아킬더맨").rrn("60211116").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer9 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(9)).job("대학생").name("김바바바엣취").rrn("60211117").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();
		Customer customer10 = new Customer.Builder().accountNumber("333324555328").address("서울특별시 서대문구 북가좌동").age(25)
				.customerID(Integer.toString(10)).job("대학생").name("김사사건건시비걸지마").rrn("60211118").sex(Sex.FEMALE)
				.phoneNumber("01020002000").build();


		
		customerList.insert(customer1);
		customerList.insert(customer2);
		customerList.insert(customer3);
		customerList.insert(customer4);
		customerList.insert(customer5);
		customerList.insert(customer6);
		customerList.insert(customer7);
		customerList.insert(customer8);
		customerList.insert(customer9);
		customerList.insert(customer10);



	}

	public void loadEmployeeData() {
		for (int i = 0; i < 3; i++) { // add temp three Sales
			int numOfEmployees = userList.employees.size();
			Sales sales = new Sales(numOfEmployees, UserType.Sales, customerList);
			userList.insert(sales);
		}
		for (int i = 0; i < 3; i++) { // add temp three Sales
			int numOfEmployees = userList.employees.size();
			ProductManagement productManagement = new ProductManagement(numOfEmployees, UserType.ProductManagement, insuranceProductList);

			userList.insert(productManagement);
		}
		for (int i = 0; i < 3; i++) { // add temp three lossAdjuster
			int numOfEmployees = userList.employees.size();
			LossAdjuster lossAdjuster = new LossAdjuster(numOfEmployees, UserType.LossAdjuster);
			lossAdjuster.genrateDummy(10);
			userList.insert(lossAdjuster);
		}

	}
	public void loadInsuranceProductData() {
		HashMap<String,String> coverageByAge = new HashMap<>();
		coverageByAge.put("0","1000");
		coverageByAge.put("1","1000");
		coverageByAge.put("2","1000");
		coverageByAge.put("3","1000");
		coverageByAge.put("4","1000");
		coverageByAge.put("5","1000");
		coverageByAge.put("6","1000");
		coverageByAge.put("7","1000");
		coverageByAge.put("8","1000");
		coverageByAge.put("8","1000");

		InsuranceProduct product1 = new InsuranceProduct.Builder()
				.productID("1")
				.productName("여성손해보험")
				.premium(1000)
				.maxAge(20)
				.sex(Sex.FEMALE)
				.maxNumberEvent(1)
				.exemptionPeriod(1)
				.reductionPeriod(1)
				.reductionRatio(50)
				.productManagementID("1")
				.coverageByAge(coverageByAge)
				.build();
		InsuranceProduct product2 = new InsuranceProduct.Builder()
				.productID("2")
				.productName("남성손해보험")
				.premium(1000)
				.maxAge(20)
				.sex(Sex.MALE)
				.maxNumberEvent(1)
				.exemptionPeriod(1)
				.reductionPeriod(1)
				.reductionRatio(50)
				.productManagementID("1")
				.coverageByAge(coverageByAge)
				.build();
		InsuranceProduct product3 = new InsuranceProduct.Builder()
				.productID("3")
				.productName("여아손해보험")
				.premium(1000)
				.maxAge(10)
				.sex(Sex.FEMALE)
				.maxNumberEvent(1)
				.exemptionPeriod(1)
				.reductionPeriod(1)
				.reductionRatio(50)
				.productManagementID("1")
				.coverageByAge(coverageByAge)
				.build();
		InsuranceProduct product4 = new InsuranceProduct.Builder()
				.productID("4")
				.productName("남아손해보험")
				.premium(1000)
				.maxAge(10)
				.sex(Sex.FEMALE)
				.maxNumberEvent(1)
				.exemptionPeriod(1)
				.reductionPeriod(1)
				.reductionRatio(50)
				.productManagementID("1")
				.coverageByAge(coverageByAge)
				.build();
		insuranceProductList.insert(product1);
		insuranceProductList.insert(product2);
		insuranceProductList.insert(product3);
		insuranceProductList.insert(product4);

	}
	public void loadContractData() {
		
	}
}
