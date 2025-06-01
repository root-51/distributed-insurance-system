package main;

import java.time.LocalDate;
import java.util.*;

import main.Data.*;
import main.Employee.*;
import main.Employee.Employee.*;
import main.Enum.*;
import main.List.*;

public class Menu {

	private Employee loginedEmployee;
	private CustomerList customerList;
	private EmployeeList employeeList;
	private InsuranceProductList insuranceProductList;
	private ContractList contractList;
	private Scanner scanner;

	public Menu(CustomerListImpl customerList, EmployeeListImpl employeeList, InsuranceProductList insuranceProductList,
			ContractList contractList, Employee loginedEmployee) {
		this.loginedEmployee = loginedEmployee;
		this.customerList = customerList;
		this.employeeList = employeeList;
		this.insuranceProductList = insuranceProductList;
		this.contractList = contractList;
		this.scanner = new Scanner(System.in);
	}

	public void printMainMenu() {
		EmployeeType loginedEmployeeType = loginedEmployee.getEmployeeType();

		String[] menuList = {};
		if (loginedEmployeeType == EmployeeType.Sales) {
			String[] salesMenuList = { "add customer", "delete customer", "modify customer", "search customer",
					"add contract", "delete contract", "modify contract", "search contract" };
			menuList = salesMenuList;
		} else if (loginedEmployeeType == EmployeeType.ProductManagement) {
			String[] productManagementMenuList = { "상품 등록", "상품 수정", "상품 조회", "상품 삭제" };
			menuList = productManagementMenuList;
		} else if (loginedEmployeeType == EmployeeType.LossAdjuster) {
			menuList = new String[] { "보상 지급", "보상 심사" };
		}

		System.out.println("\nSelect Menu===");
		for (int i = 0; i < menuList.length; i++) {
			System.out.printf("%d. %s \n", i + 1, menuList[i]);
		}
		System.out.println("\nEnter 0 for EXIT.");
	}

	public void excuteSelectedMenu(int selectedMenu) {
		EmployeeType loginedEmployeeType = loginedEmployee.getEmployeeType();
		if (loginedEmployeeType == EmployeeType.Sales) {
			switch (selectedMenu) {
			case 0:
				System.out.println("Good Bye...");
				System.exit(0);
			case 1:
				createCustomer();
				break;
			case 2:
				deleteCustomer();
				break;
			case 3:
				updateCustomer();
				break;
			case 4:
				searchCustomer();
				break;
			case 5:
				createContract();
				break;
			case 6:
				deleteContract();
				break;
			case 7:
				updateContract();
				break;
			case 8:
				searchContract();
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		} else if (loginedEmployeeType == EmployeeType.ProductManagement) {
			switch (selectedMenu) {
			case 0:
				System.out.println("Good Bye...");
				System.exit(0);
			case 1:
				createInsuaranceProduct();
				break;
			case 2:
				updateInsuaranceProduct();
				break;
			case 3:
				searchInsuaranceProduct();
				break;
			case 4:
				deleteInsuaranceProduct();
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		} else if (loginedEmployeeType == EmployeeType.LossAdjuster) {
			switch (selectedMenu) {
			case 0:
				System.out.println("Good Bye...");
				System.exit(0);
			case 1:
				payCompensation();
				break;
			case 2:
				evaluateCompensation();
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		}

	}

	private void createCustomer() {
		System.out.println("Enter customer details:");
		String accountNumber = getInputStr("Account Number");
		String address = getInputStr("Address");
		int age = getInputInt("Age");
		String customerID = Integer.toString(customerList.getAll().size());
		String job = getInputStr("Job");
		String name = getInputStr("Name");
		String phoneNumber = getInputStr("Phone Number");
		String rrn = getInputStr("RRN");
		Sex sexStr = checkSexInput();

		Sales sales = (Sales) loginedEmployee;
		if (sales.createCustomer(accountNumber, address, age, customerID, job, name, phoneNumber, rrn, sexStr)) {
			System.out.println("Customer added successfully.");
		} else
			System.out.printf("false");
	}

	private Customer searchCustomer() {
		showCustomers();
		String customerID = getInputStr("select customerID");
		Sales sales = (Sales) loginedEmployee;
		Customer selectedCustomer = sales.getCustomer(customerID);
		return showCustomerDetail(selectedCustomer);
	}

	private void showCustomers() {
		Sales sales = (Sales) loginedEmployee;

		System.out.println("Customer List===");
		System.out.println("ID \tName");
		ArrayList<Customer> customers = sales.getAllCustomer();
		for (Customer customer : customers) {
			System.out.println(customer.getCustomerID() + "\t" + customer.getName());
		}
	}

	private Customer showCustomerDetail(Customer customer) {
		System.out.println("이름\t" + customer.getName());
		System.out.println("나이\t" + customer.getAge());
		System.out.println("주민번호\t" + customer.getRrn());
		System.out.println("계좌번호\t" + customer.getAccountNumber());
		System.out.println("직업\t" + customer.getJob());
		System.out.println("전화번호\t" + customer.getPhoneNumber());
		System.out.println("고객ID\t" + customer.getCustomerID());
		return customer;
	}

	private void updateCustomer() {
		Sales sales = (Sales) loginedEmployee;

		Customer customer = searchCustomer();
		String customerID = customer.getCustomerID();

		System.out.println("Enter customer details:");
		String accountNumber = getInputOrKeepStr("Account Number", customer.getAccountNumber());
		String address = getInputOrKeepStr("Address", customer.getAddress());
		int age = getInputOrKeepInt("Age", customer.getAge());
		String job = getInputOrKeepStr("Job", customer.getJob());
		String name = getInputOrKeepStr("Name", customer.getName());
		String phoneNumber = getInputOrKeepStr("Phone Number", customer.getPhoneNumber());
		String rrn = getInputOrKeepStr("RRN", customer.getRrn());
		String sexStr = getInputOrKeepStr("Sex (M/F)", customer.getSex().toString().substring(0, 1)); // SEX의 ENUM을
																										// String으로 변환 후
																										// 첫 글자만 가져옴
		Sex sex = sexStr.equalsIgnoreCase("M") ? Sex.MALE : Sex.FEMALE;
		String log = "";
		if (sales.updateCustomer(accountNumber, address, age, customerID, job, name, phoneNumber, rrn, sex)) {
			log = "Customer updated successfully.";
			showCustomerDetail(sales.getCustomer(customerID));
		} else {
			log = "Failed: cannot update customer(" + customerID + ")";
		}
		System.out.println(log);
	}

	private String getInputOrKeepStr(String title, String prevValue) {
		String userInput = "";
		System.out.print(title + ": ");
		userInput = scanner.nextLine().trim();
		if (userInput == null || userInput.equals("")) {
			userInput = prevValue;
		}
		return userInput;
	}

	private int getInputOrKeepInt(String title, int prevValue) {
		return Integer.parseInt(getInputOrKeepStr(title, Integer.toString(prevValue)));
	}

	private void deleteCustomer() {
		Sales sales = (Sales) loginedEmployee;

		showCustomers();
		System.out.println("Enter customer ID to delete.");
		String customerID = getInputStr("customerID");

		String log = "";
		if (sales.deleteCustomer(customerID)) {
			log = "Customer(" + customerID + ") deleted successfully.";
		} else {
			log = "Failed: cannot delete customer(" + customerID + ")";
		}
		System.out.println(log);

	}

	private void createContract() {
		System.out.println("Enter contract details:");
		String customerID = getInputStr("customerID");
		LocalDate expirationDate = getInputDate("expiration date");

	}

	private void searchContract() {

	}

	private void updateContract() {

	}

	private void deleteContract() {

	}

	public int getUserSelectInt() {
		System.out.print(">> ");
		return Integer.parseInt(scanner.nextLine());
	}

	public UserSelection getUserSelectYorN() {
		System.out.print("Yes/No/Cancel >> ");
		String userInput = scanner.nextLine();
		return switch (userInput.toLowerCase()) {
		case "yes", "y" -> UserSelection.Yes;
		case "no", "n" -> UserSelection.No;
		case "cancel", "c" -> UserSelection.Cancel;
		default -> {
			System.out.println("잘못된 입력입니다. 다시 시도해주세요");
			yield getUserSelectYorN();
		}
		};

	}

	private String getInputStr(String title) {
		String input = "";
		do {
			System.out.print(title + ": ");
			input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("* this field cannot be null.");
			}
		} while (input.isEmpty());
		return input;
	}

	private int getInputInt(String title) {
		return Integer.parseInt(getInputStr(title));
	}

	private LocalDate getInputDate(String title) {
		return null;
	}
// ==============================

	public void createInsuaranceProduct() {

		String productName = getInputStr("product name");

		int maxAge = getInputInt("max age");
		int maxNumberEvent = getInputInt("max number event");
		int premium = getInputInt("premium");
		int reductionPeriod = getInputInt("reduction period");
		int reductionRatio = getInputInt("reduction ratio");
		Sex sex = checkSexInput();
		int exemptionPeriod = getInputInt("exemption period");
		HashMap<String, String> coverageByAge = checkHashMap();

		ProductManagement manager = (ProductManagement) loginedEmployee;
		if (manager.createProduct(insuranceProductList, coverageByAge, exemptionPeriod, reductionPeriod, reductionRatio,
				productName, sex, premium, maxAge, maxNumberEvent))
			System.out.println("상품이 정상적으로 등록되었습니다.");
		else // 같은 이름의 상품이 있는 경우 예외 처리
			System.out.println("문제가 발생하였습니다.");
	}

	public void searchInsuaranceProduct() {
		ProductManagement manager = (ProductManagement) loginedEmployee;
		int index = 0;
		final int maxCount = 10;
		String input = "";

		if (insuranceProductList.size() == 0) {
			System.out.println("등록된 상품이 없습니다.");
			return;
		}

		while (index < insuranceProductList.size()) {
			int end = Math.min(index + maxCount, insuranceProductList.size());
			System.out.println("=== 보험 상품 ===");
			for (int i = index; i < end; i++) {
				InsuranceProduct product = insuranceProductList.getProduct(i);
				System.out.println(index + 1 + ". " + product.getProductID() + " " + product.getProductName() + " "
						+ product.getProductManagementID());
			}
			index = end;
			System.out.println("조회하고 싶은 상품을 선택해주세요.");
			if (index >= insuranceProductList.size())
				System.out.println("모든 상품을 다 출력했습니다.");
			else
				System.out.println("다음 페이지로 넘어가려면 'next', 키워드 검색을 원하시면 'search', 조회 종료를 원하시면 'end'를 입력해주세요.");
			input = scanner.nextLine();
			if (input.equals("search")) {
				searchKeyWord();
				break;
			} else if (input.equals("end")) {
				System.out.println("상품 조회를 종료합니다.");
				break;
			} else if (!input.equals("next")) {
				try {
					index = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					index = getInputInt("잘못된 입력입니다. 번호를 다시 입력해주세요 ");
				}
				System.out.println(manager.getProduct(insuranceProductList, index - 1).toString());
				break;
			}
		}
	}

	public void searchKeyWord() {
		ProductManagement manager = (ProductManagement) loginedEmployee;
		InsuranceProductList products = null;

		System.out.println("원하는 키워드를 선택해주세요.");
		System.out.println("1.product id \n2.product name \n3.product management id");
		int chooseMenu = getUserSelectInt();

		switch (chooseMenu) {
		case 1:
			String checkProductID = getInputStr("찾으려는 상품의 ID를 입력하세요");
			products = manager.searchProducts(insuranceProductList, "productID", checkProductID);
			break;
		case 2:
			String checkProductName = getInputStr("찾으려는 상품의 이름을 입력하세요");
			products = manager.searchProducts(insuranceProductList, "productName", checkProductName);
			break;
		case 3:
			String checkProductManagerID = getInputStr("찾으려는 상품의 상품관리자 id를 입력하세요");
			products = manager.searchProducts(insuranceProductList, "productManagementID", checkProductManagerID);
			break;
		}

		insuranceProductList.printAllProducts();
	}

	public void updateInsuaranceProduct() {
		String productID = getInputStr("업데이트하려는 상품의 ID를 적어주세요");

		ProductManagement manager = (ProductManagement) loginedEmployee;
		InsuranceProduct product = manager.searchProduct(insuranceProductList, productID);
		if (product == null) {
			System.out.println("일치하는 상품이 없습니다.");
			return;
		}

		System.out.println("수정하려는 정보를 선택해주세요.");
		String[] menuList = { "product name", "max age", "max number event", "premium", "reduction period",
				"reduction ratio", "sex", "exemption period", "coverage by age" };
		for (int i = 0; i < menuList.length; i++) {
			System.out.println((i + 1) + " " + menuList[i]);
		}
		int chooseMenu = getUserSelectInt();
		System.out.println("수정된 값을 입력해주세요 : ");
		boolean result = false;

		switch (chooseMenu) {
		case 1:
			result = product.setProductName(scanner.nextLine());
			break;
		case 2:
			result = product.setMaxAge(getInputInt("product name"));
			break;
		case 3:
			result = product.setMaxNumberEvent(getInputInt("max number event"));
			break;
		case 4:
			result = product.setPremium(getInputInt("premium"));
			break;
		case 5:
			result = product.setReductionPeriod(getInputInt("reduction period"));
			break;
		case 6:
			result = product.setReductionRatio(getInputInt("reduction ratio"));
			break;
		case 7:
			result = product.setSex(checkSexInput());
			break;
		case 8:
			result = product.setExemptionPeriod(getInputInt("exemption period"));
			break;
		case 9:
			result = product.setCoverageByAge(checkHashMap());
			break;
		}

		if (result){
			System.out.println("성공적으로 수정되었습니다.");
			manager.updateProduct(insuranceProductList,product);
		}else
			System.out.println("문제가 발생하였습니다.");
	}

	public void deleteInsuaranceProduct() {
		ProductManagement manager = (ProductManagement) loginedEmployee;
		String input = null;
		boolean result = false;

		while (true) {
			input = getInputStr("삭제하려는 상품의 ID를 입력해주세요");
			if (input != null) {
				result = manager.deleteProduct(insuranceProductList, input);
				break;
			}
		}
		if (result)
			System.out.println("삭제가 완료되었습니다.");
		else
			System.out.println("문제가 발생했습니다.");
	}

	/**
	 * string입력 값을 HashMap<String,String>으로 변환
	 * 
	 * @param
	 * @return coverageByAge값을 HashMap<String,String>으로 반환
	 */
	public HashMap<String, String> checkHashMap() {
		HashMap<String, String> hash = new HashMap<>();
		while (true) {
			System.out.println("coverage by age : ");
			String coverageByAgStrings = scanner.nextLine();
			String[] array = coverageByAgStrings.split(" ");
			if (array.length % 2 != 0) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				continue;
			}
			for (int i = 0; i < array.length - 1; i += 2)
				hash.put(array[i], array[i + 1]);
			break;
		}
		return hash;
	}

	/**
	 * 입력값에 따라 Sex 결정
	 * 
	 * @return 입력값에 따른 Sex 반환
	 */
	public Sex checkSexInput() {
		Sex sex = null;
		String value = "";
		while (true) {
			System.out.println("sex : ");
			value = scanner.nextLine();
			if (value.equals("m") || value.equals("M") || value.equals("Male") || value.equals("male")) {
				sex = Sex.MALE;
				break;
			} else if (value.equals("f") || value.equals("F") || value.equals("Female") || value.equals("female")) {
				sex = Sex.FEMALE;
				break;
			}
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
		return sex;
	}

	// ----------------LossAdjuster--------------------------------------

	/**
	 * search관련 메소드 분리되지 않음
	 */
	private void payCompensation() {
		LossAdjuster lossAdjuster = (LossAdjuster) loginedEmployee; // 관리자 로딩

		EventList eventList = lossAdjuster.getEventList(); // 컴포지션... 관리자가 리스트를 들고 있음, 가져와야함

		// 보상 지급 대기중인 보상 조회 로직, 라인넘버 통해서 선택함,
		System.out.println("===CompensationList===");
		ArrayList<Event> events = eventList.searchCompensation("state", "Awaiting"); // 일반 보상 지급이 아직 되지 않은 경우만 골라오긴 하는데,
																						// 보상 지급 결정이 내려졌는지가 반영이 되야할것같음..
																						// DB 마렵네
		if (events.size() <= 0) {
			System.out.println("보상 지급 대기중인 항목이 없습니다");
			return;
		}
		for (int i = 0; i < events.size(); i++) {
			Compensation targetCompensation = events.get(i).getEvaluation().getCompensation();
			System.out.println((i + 1) + ": Customer:" + targetCompensation.getCustomerID() + ", Amount charged: "
					+ targetCompensation.getAmountOfPaid());
		}
		System.out.println("Select Line Number: ");
		int userSelectNum = getUserSelectInt() - 1;
		if (userSelectNum >= events.size()) {
			System.out.println("선택 범위를 초과했습니다. 다시 시도해주세요"); // Exception으로 바꾸면 좋을텐데
			return;
		}
		Event selectedEvent = events.get(userSelectNum);
		Evaluation selectedEvaluation = selectedEvent.getEvaluation();
		Compensation selectedCompensation = selectedEvaluation.getCompensation();

		// 상세정보 표시 및 보상 지급 선택
		System.out.println("==상세정보==\n" + selectedEvent + ", Amount charged: "
				+ selectedEvaluation.getCompensation().getAmountOfPaid());
		System.out.println("보상을 지급하시겠습니까?");
		switch (getUserSelectYorN()) {
		case UserSelection.Yes:
			if (!lossAdjuster.payCompensation(selectedCompensation.getCompensationID(), true))
				System.out.println("시스템 오류로 인해 보상을 지급할 수 없습니다");
			break;
		case UserSelection.No:
			if (!lossAdjuster.payCompensation(selectedCompensation.getCompensationID(), false))
				System.out.println("시스템 오류로 인해 보상을 지급할 수 없습니다");
			break;
		case UserSelection.Cancel:
			System.out.println("보상 지급이 취소되었습니다.");
			break;
		}
	}

	private void evaluateCompensation() {
		LossAdjuster lossAdjuster = (LossAdjuster) loginedEmployee; // 관리자 로딩

		EventList eventList = lossAdjuster.getEventList(); // 컴포지션... 관리자가 리스트를 들고 있음, 가져와야함

		Event selectedEvent = eventDetailVeiw(eventList);
		if (selectedEvent == null) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		Customer selectedCustomer = CustomerDetailView(selectedEvent);
		if (selectedCustomer == null) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		// 계약이 아직 구현되지 않아, 계약 조회는 이후 구현
		System.out.println("심사 결과를 선택해주세요 pass = Yes, nonpass = No, cancel = Cancel");
		switch (getUserSelectYorN()) {
		case UserSelection.Yes:
			if (!lossAdjuster.evaluateCompensation(selectedEvent.getEventID(), true))
				System.out.println("시스템 오류로 인해 심사를 진행할 수 없습니다");
			break;
		case UserSelection.No:
			if (!lossAdjuster.payCompensation(selectedEvent.getEventID(), false))
				System.out.println("시스템 오류로 인해 심사를 진행할 수 없습니다");
			break;
		case UserSelection.Cancel:
			System.out.println("심사가 취소되었습니다.");
			break;
		}

	}

	private Customer CustomerDetailView(Event selectedEvent) {
		Customer selectedCustomer = customerList.search(selectedEvent.getCustomerID());
		if (selectedCustomer == null) {
			System.out.println("해당하는 고객이 없습니다.");
			return null;
		}
		System.out.println("===CustomerDetail===\n" + selectedCustomer);
		System.out.println("고객정보 확인이 끝나셨다면 Yes를 눌러주세요");
		switch (getUserSelectYorN()) {
		case UserSelection.Yes:
			return selectedCustomer;
		case UserSelection.No:
			System.out.println("고객 상세 정보 조회로 돌아갑니다.");
			return CustomerDetailView(selectedEvent);
		case UserSelection.Cancel:
			System.out.println("보상 심사가 취소되었습니다.");
			return null;
		}
		return null;
	}

	private Event eventDetailVeiw(EventList eventList) {
		System.out.println("===EventList===");
		ArrayList<Event> events = eventList.searchEvaluation("state", "Awaiting"); // 심사 대기중 리스트 가져옴
		if (events.size() <= 0) {
			System.out.println("보상 지급 대기중인 항목이 없습니다");
			return null;
		}
		for (int i = 0; i < events.size(); i++) {
			System.out.println((i + 1) + ": Customer:" + events.get(i).getCustomerID() + ", eventID: "
					+ events.get(i).getEventID());
		}
		System.out.println("Select Line Number: ");
		int userSelectNum = getUserSelectInt() - 1;
		if (userSelectNum >= events.size()) {
			System.out.println("선택 범위를 초과했습니다. 다시 시도해주세요"); // Exception으로 바꾸면 좋을텐데
			return null;
		}
		Event selectedEvent = events.get(userSelectNum);
		System.out.println("==상세정보==\n" + selectedEvent + "\n 해당 사고를 선택하시겠습니까?");
		switch (getUserSelectYorN()) {
		case UserSelection.Yes:
			return selectedEvent;
		case UserSelection.No:
			System.out.println("사고 정보 리스트로 돌아갑니다.");
			return eventDetailVeiw(eventList);
		case UserSelection.Cancel:
			System.out.println("보상 심사가 취소되었습니다.");
			return null;
		}
		return null;
	}

}
