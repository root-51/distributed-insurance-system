package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import main.User.User.UserType;
import main.Menu.*;

import main.Data.*;
import main.User.*;
import main.Enum.*;
import main.List.*;

public class SystemManager {

	private User loginedUser;
	private UserType loginedUserType;
	private Scanner scanner;

	private Menu menu;

	public SystemManager(User loginedUser) {
		this.loginedUser = loginedUser;
		this.loginedUserType = loginedUser.getUserType();
		this.scanner = new Scanner(System.in);

		setMenu();
		menu.printTitle();
	}

	public void setMenu() {
		switch (loginedUserType) {
			case Customer:
				this.menu = new CustomerMenu();
				break;
			case Sales:
				this.menu = new SalesMenu();
				break;
			case ProductManagement:
				this.menu = new ProductManagementMenu();
				break;
			case LossAdjuster:
				this.menu = new LossAdjusterMenu();
				break;
			case UnderWriter:
				this.menu = new UnderWriterMenu();
				break;
		}
	}

	public void printMainMenu() {
		menu.printMenuHeader("메뉴선택");
		menu.printMenuGuide("메뉴를 선택해주세요.");
		menu.printMenuList();
	}

	public void excuteSelectedMenu(int selectedMenu) {
		UserType loginedUserType = loginedUser.getUserType();
		if (loginedUserType == UserType.Sales) {
			switch (selectedMenu) {
				case 0:
					System.out.println("Good Bye...");
					System.exit(0);
				case 1:
					createCustomer();
					break;
				case 2:
					showCustomers("상세정보조회");
					break;
				case 3:
					updateCustomer();
					break;
				case 4:
					deleteCustomer();
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
					showContracts("상세정보조회");
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		} else if (loginedUserType == UserType.ProductManagement) {
			switch (selectedMenu) {
				case 0:
					System.out.println("Good Bye...");
					System.exit(0);
				case 1:
					createInsuranceProduct();
					break;
				case 2:
					showInsuranceProduct("상세정보조회");
					break;
				case 3:
					updateInsuranceProduct();
					break;
				case 4:
					deleteInsuaranceProduct();
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		} else if (loginedUserType == UserType.LossAdjuster) {
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
		} else if (loginedUserType == UserType.UnderWriter) {
			switch (selectedMenu) {
				case 0:
					System.out.println("Good Bye...");
					System.exit(0);
				case 1:
					underWriting();
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		} else if (loginedUserType == UserType.Customer) {
			switch (selectedMenu) {
				case 1: // 등록
					createEvent();
					break;
				case 2: // 조회
					showMyEvents("상세정보조회");
					break;
				case 3: // 수정
					updateEvent();
					break;
				case 4: // 삭제
					deleteEvent();
					break;
				case 5: // 보험료 조회
					showMyPremieums();
					break;
				case 6: // 보험료 지불
					payMyPremieum();
					break;
			}
		}
	}
	// Customer =============================================================

	public void createEvent() {
		menu.createPrompt();
		Event newEvent = ((CustomerMenu) menu).inputNewEventInfo(loginedUser.getUserID());
		if (((CustomerService) loginedUser).createEvent(newEvent)) {
			menu.printLog("사고 접수에 성공하였습니다.", true);
		} else {
			menu.printLog("사고 접수에 실패하였습니다.", false);
		}
	}

	public void showMyEvents(String nextCommand) {
		menu.printMenuHeader(nextCommand);
		CustomerService customer = (CustomerService) loginedUser;
		ArrayList<Event> myEvents = customer.getMyEvents(customer.getUserID());
		int maxPage = menu.computeMaxPage(myEvents.size());
		int currentPage = 1;
		int startIndex = 0;

		((CustomerMenu) menu).showEvents(
				((CustomerMenu) menu).getNextEventsInPage(myEvents, currentPage, startIndex),
				currentPage
		);
		boolean escapeMenu = false;
		do {
			menu.printMenuGuide("메뉴를 선택해주세요.");
			menu.printMenuList(
					new String[]{nextCommand.equals("상세정보조회") ? "종료" : nextCommand, "상세정보조회", "키워드검색",
							"다음페이지"});
			int userSelect = getUserSelectInt();
			switch (userSelect) {
				case 0:
					escapeMenu = true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 사고의 ID를 입력해주세요");
					String selectedEventID = getInputStr("사고 ID");
					menu.printMenuHeader(nextCommand);
					showEventDetail(customer.getMyEvent(selectedEventID));
					break;
				case 2: // TODO: 키워드검색
					System.out.println("서비스 준비중입니다.");
					break;
				case 3: // 다음 페이지 출력
					if (currentPage == maxPage) {
						startIndex = 0;
						currentPage = 1;
					} else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((CustomerMenu) menu).showEvents(
							((CustomerMenu) menu).getNextEventsInPage(myEvents, currentPage, startIndex),
							currentPage);
					break;
			}
		} while (!escapeMenu);
	}

	public Event showEventDetail(Event myEvent) {
		if (myEvent != null) {
			((CustomerMenu) menu).showEventDetail(myEvent);
			return myEvent;
		} else {
			menu.printLog("사고ID를 다시 입력해주세요.", false);
			return null;
		}

	}

	public void updateEvent() {
		CustomerService customer = (CustomerService) loginedUser;
		showMyEvents("사고 갱신");
		menu.printMenuGuide("수정할 사고의 ID를 선택해주세요.");
		String eventID = menu.getInputStr("사고ID");

		Event currentEvent = customer.getMyEvent(eventID);
		if (currentEvent != null) {
			Event updatedEvent = ((CustomerMenu) menu).inputNewOrKeepEventInfo(currentEvent);
			if (customer.updateEvent(updatedEvent)) {
				menu.printLog("사고 갱신을 완료하였습니다.", true);
			} else {
				menu.printLog("사고 갱신에 실패하였습니다. 다시 시도해주세요.", false);
			}
		} else {
			menu.printLog("사고 ID를 다시 입력해주세요", false);
		}

	}

	public void deleteEvent() {
		CustomerService customer = (CustomerService) loginedUser;
		showMyEvents("사고 삭제");
		menu.printMenuGuide("삭제할 사고의 ID를 선택해주세요.");
		String eventID = menu.getInputStr("사고ID");
		if (customer.deleteEvent(eventID)) {
			menu.printLog("사고 삭제 완료하였습니다.", true);
		} else {
			menu.printLog("사고 삭제 실패하였습니다. 다시 시도해주세요.", false);
		}
	}

	public void payMyPremieum() {
		System.out.println("서비스 준비중입니다.");
	}

	public void showMyPremieums() {
		System.out.println("서비스 준비중입니다.");
	}

	// Sales =============================================================
	// 0529 완
	private void createCustomer() { //
		menu.createPrompt();
		if (((Sales) loginedUser).createCustomer(getInputStr("계좌번호"), getInputStr("주소"),
				getInputInt("나이"),
				getInputStr("아이디"), getInputStr("비밀번호"),
				getInputStr("직업"), getInputStr("이름"), getInputStr("전화번호"), getInputStr("주민번호"),
				checkSexInput())) {
			menu.printLog("신규고객 등록에 성공하였습니다.", true);
		} else {
			menu.printLog("신규고객 등록에 실패하였습니다.", false);
		}
	}

	// 0529 완
	private void showCustomers(String nextCommand) {
		menu.printMenuHeader(nextCommand);
		Sales sales = (Sales) loginedUser;
		ArrayList<Customer> customers = sales.getAllCustomer();
		int maxPage = menu.computeMaxPage(customers.size());
		int currentPage = 1;
		int startIndex = 0;

		((SalesMenu) menu).show(
				((SalesMenu) menu).getNextCustomersInPage(customers, currentPage, startIndex), currentPage);
		boolean escapeMenu = false;
		do {
			menu.printMenuList(new String[]{
					nextCommand.equals("상세정보조회") ? "종료" : nextCommand,
					"상세정보조회", "키워드검색", "다음페이지"});
			int userSelect = getUserSelectInt();
			switch (userSelect) {
				case 0:
					escapeMenu = true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 고객의 ID를 입력해주세요");
					String selectedUserID = getInputStr("고객 ID");
					menu.printMenuHeader(nextCommand);
					showCustomerDetail(sales.searchCustomer(selectedUserID));
					break;
				case 2: // TODO: 키워드검색
					searchCustomer();
					break;
				case 3: // 다음 페이지 출력
					if (currentPage == maxPage) {
						startIndex = 0;
						currentPage = 1;
						System.out.println("마지막 페이지입니다.");
					} else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((SalesMenu) menu).show(
							((SalesMenu) menu).getNextCustomersInPage(customers, currentPage, startIndex),
							currentPage
					);
					break;
			}
		} while (!escapeMenu);
	}

	// TODO: searchCustomer 키워드 검색 메뉴
	private Customer searchCustomer() {
		String customerID = getInputStr("검색할 고객 ID를 입력해주세요");
		Sales sales = (Sales) loginedUser;
		Customer selectedCustomer = sales.searchCustomer(customerID);
		return showCustomerDetail(selectedCustomer);
	}

	// 0529 완
	private Customer showCustomerDetail(Customer customer) {
		((SalesMenu) menu).showDetail(customer);
		return customer;
	}

	// 0529 완
	private void updateCustomer() {
		Sales sales = (Sales) loginedUser;
		showCustomers("고객정보 수정");

		menu.printMenuGuide("수정할 고객의 ID를 선택해주세요");
		Customer customer = sales.searchCustomer(getInputStr("고객ID"));
		String customerID = customer.getCustomerID(); // 입력한 고객 ID 에 해당하는 고객이 없는 경우 방지
		System.out.println("수정하려는 정보를 입력해주세요.");
		if (sales.updateCustomer(
				getInputOrKeepStr("계좌번호", customer.getAccountNumber()),
				getInputOrKeepStr("주소", customer.getAddress()),
				getInputOrKeepInt("나이", customer.getAge()),
				customer.getCustomerID(),
				getInputOrKeepStr("직업", customer.getJob()),
				getInputOrKeepStr("이름", customer.getName()),
				getInputOrKeepStr("전화번호", customer.getPhoneNumber()),
				getInputOrKeepStr("주민번호", customer.getRrn()),
				checkSexInput() //TODO: keep 확장 필요
		)
		) {
			menu.printLog("고객 정보 수정에 성공했습니다.", true);
			showCustomerDetail(sales.searchCustomer(customerID));
		} else {
			menu.printLog("고객 정보 수정에 실패했습니다.", false);
		}
	}

	// 0529 완
	private void deleteCustomer() {
		Sales sales = (Sales) loginedUser;
		showCustomers("고객삭제");
		menu.printMenuGuide("삭제할 고객의 ID를 선택해주세요");
		String customerID = getInputStr("고객 ID");

		if (sales.deleteCustomer(customerID)) {
			menu.printLog("고객 삭제에 성공했습니다.", true);
		} else {
			menu.printLog("고객 삭제에 실패했습니다.", false);
		}

	}

	private void createContract() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter localFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			if (((Sales) loginedUser).createContract(LocalDate.parse(getInputStr("계약일")),
					LocalDate.parse(getInputStr("만기일"), localFormat), getInputStr("상품ID"),
					getInputStr("고객ID"))) {
				menu.printLog("신규계약 등록에 성공하였습니다.", true);
			} else
				menu.printLog("신규계약 등록에 실패하였습니다.", false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteContract() {
		Sales sales = (Sales) loginedUser;
		showContracts("계약삭제");
		menu.printMenuGuide("삭제할 계약의 ID를 선택ㅎ주세요");
		String contractID = getInputStr("계약ID");

		if (sales.deleteContract(contractID)) {
			menu.printLog("계약 삭제에 성공했습니다.", true);
		} else {
			menu.printLog("계약 삭제에 실패했습니다.", false);
		}
	}

	public void updateContract() {
		Sales sales = (Sales) loginedUser;
		showContracts("계약정보 수정");

		menu.printMenuGuide("수정할 계약의 ID를 선택해주세요");
		Contract contract = sales.getContract(getInputStr("계약ID"));
		if (contract == null) {
			System.out.println("일치하는 계약이 없습니다.");
			return;
		}
		System.out.println("수정하려는 정보를 입력해주세요.");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		SimpleDateFormat localFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (sales.updateContract(
				getInputOrKeepStr("계약일자", localFormatter.format(contract.getContractDate())),
				getInputOrKeepStr("고객ID", contract.getCustomerID()),
				getInputOrKeepStr("만기일", contract.getExpirationDate().format(dateFormatter)),
				getInputOrKeepStr("상품ID", contract.getProductID()),
				getInputOrKeepInt("상태", contract.getState().getValue()) //TODO: keep 확장 필요
		)
		) {
			menu.printLog("계약 정보 수정에 성공했습니다.", true);
			showContractDetail(sales.getContract(contract.getContractID()));
		} else {
			menu.printLog("계약 정보 수정에 실패했습니다.", false);
		}
	}

	private Contract searchContract() {
		String contractID = getInputStr("검색할 계약 ID를 입력해주세요");
		Sales sales = (Sales) loginedUser;
		Contract selectedContract = sales.getContract(contractID);
		return showContractDetail(selectedContract);
	}

	public void searchContractKeyWord() {
		Sales sales = (Sales) loginedUser;
		List<Contract> contracts = null;

		System.out.println("원하는 키워드를 선택해주세요.");
		System.out.println("1. 계약ID \n2. 고객ID \n3. 상품ID");
		int chooseMenu = getUserSelectInt();

		switch (chooseMenu) {
			case 1:
				String checkProductID = getInputStr("찾으려는 계약의 ID를 입력하세요");
				contracts = sales.searchContractsByKey("contract_id", checkProductID);
				break;
			case 2:
				String checkProductName = getInputStr("찾으려는 계약의 고객ID를 입력하세요");
				contracts = sales.searchContractsByKey("customer_id", checkProductName);
				break;
			case 3:
				String checkProductManagerID = getInputStr("찾으려는 계약의 상품ID를 입력하세요");
				contracts = sales.searchContractsByKey("product_id", checkProductManagerID);
				break;
			default:
				System.out.println("키워드 검색을 종료합니다.");
				return;
		}

		for (Contract contract : contracts) {
			System.out.println(contract.toString());
		}
	}

	private void showContracts(String nextCommand) {
		menu.printMenuHeader(nextCommand);
		Sales sales = (Sales) loginedUser;
		List<Contract> contracts = sales.getAllContract();
		int maxPage = menu.computeMaxPage(contracts.size());
		int currentPage = 1;
		int startIndex = 0;

		((SalesMenu) menu).showContract(
				((SalesMenu) menu).getNextContractsInPage(contracts, currentPage, startIndex), currentPage);
		boolean escapeMenu = false;
		do {
			menu.printMenuList(new String[]{
					nextCommand.equals("상세정보조회") ? "종료" : nextCommand,
					"상세정보조회", "키워드검색", "다음페이지"});
			int userSelect = getUserSelectInt();
			switch (userSelect) {
				case 0:
					escapeMenu = true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 계약의 ID를 입력해주세요");
					String selectedContractID = getInputStr("계약 ID");
					menu.printMenuHeader(nextCommand);
					showContractDetail(sales.getContract(selectedContractID));
					break;
				case 2: // TODO: 키워드검색
					searchContractKeyWord();
					break;
				case 3: // 다음 페이지 출력
					if (currentPage == maxPage) {
						startIndex = 0;
						currentPage = 1;
						System.out.println("마지막 페이지입니다.");
					} else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((SalesMenu) menu).showContract(
							((SalesMenu) menu).getNextContractsInPage(contracts, currentPage, startIndex),
							currentPage
					);
					break;
			}
		} while (!escapeMenu);
	}

	private Contract showContractDetail(Contract contract) {
		((SalesMenu) menu).showContractDetail(contract);
		return contract;
	}

	// ProductManagement =============================================================
	// 0529 완
	public void createInsuranceProduct() {
		menu.createPrompt();

		// TODO: createProduct에 insuranceProductList 를 파라미터로 넣는 이유?
		if (((ProductManagement) loginedUser).createProduct(getInputInt("면책기간(년)"),
				getInputInt("감액기간(년)"),
				getInputInt("감액기간 보장금액 비율(%)"), getInputStr("보험상품 이름"), checkSexInput(), getInputInt("보험료"),
				getInputInt("최대 가입 연령"), checkHashMap(), getInputInt("최대 사고 횟수")))
			menu.printLog("상품이 정상적으로 등록되었습니다.", true);

		else
			menu.printLog("같은 이름의 상품이 있어 등록이 실패했습니다.", false);
	}

	// 0529 완
	private void showInsuranceProduct(String nextCommand) {
		menu.printMenuHeader(nextCommand);
		ProductManagement productManagement = (ProductManagement) loginedUser;

		ArrayList<InsuranceProduct> products = productManagement.getAllProduct();
		int maxPage = menu.computeMaxPage(products.size());
		int currentPage = 1;
		int startIndex = 0;
		((ProductManagementMenu) menu).show(
				((ProductManagementMenu) menu).getNextProductsInPage(products, currentPage, startIndex),
				currentPage);
		boolean escapeMenu = false;
		do {
			menu.printMenuList(new String[]{
					nextCommand.equals("상세정보조회") ? "종료" : nextCommand,
					"상세정보조회", "키워드검색", "다음페이지"});
			int userSelect = getUserSelectInt();
			switch (userSelect) {
				case 0:
					escapeMenu = true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 상품의 ID를 입력해주세요");
					String selectedProductID = getInputStr("상품 ID");
					menu.printMenuHeader(nextCommand);
					ArrayList<InsuranceProduct> productList = ((ProductManagement) loginedUser).searchProductsByKey(
							"product_id", selectedProductID);
					if (productList.size() == 0)
						System.out.println("일치하는 상품이 없습니다.");
					else
						showProductDetail(productList);
					break;
				case 2: // TODO: 키워드검색
					searchKeyWord();
					break;
				case 3: // 다음 페이지 출력
					if (currentPage == maxPage) {
						startIndex = 0;
						currentPage = 1;
						System.out.println("마지막 페이지입니다.");
					} else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((ProductManagementMenu) menu).show(
							((ProductManagementMenu) menu).getNextProductsInPage(products, currentPage,
									startIndex),
							currentPage
					);
					break;
			}
		} while (!escapeMenu);
	}

	// TODO: 검토
	public void searchInsuranceProduct() {

		int index = 0;
		final int maxCount = 10;
		String input = "" ;

		ArrayList<InsuranceProduct> insuranceProductList = ((ProductManagement) loginedUser).getAllProduct();

		if (insuranceProductList.size() == 0) {
			System.out.println("등록된 상품이 없습니다.");
			return;
		}

		while (index < insuranceProductList.size()) {
			int end = Math.min(index + maxCount, insuranceProductList.size());
			System.out.println("=== 보험 상품 ===");
			for (int i = index; i < end; i++) {
				InsuranceProduct product = insuranceProductList.get(i);
				System.out.println(
						index + 1 + ". " + product.getProductID() + " " + product.getProductName() + " "
								+ product.getProductManagementID());
			}
			index = end;
			System.out.println("조회하고 싶은 상품을 선택해주세요.");
			if (index >= insuranceProductList.size())
				System.out.println("모든 상품을 다 출력했습니다.");
			else
				System.out.println(
						"다음 페이지로 넘어가려면 'next', 키워드 검색을 원하시면 'search', 조회 종료를 원하시면 'end'를 입력해주세요.");
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
				System.out.println(insuranceProductList.get(index - 1).toString());
				break;
			}
		}
	}

	public void searchKeyWord() {
		ProductManagement manager = (ProductManagement) loginedUser;
		ArrayList<InsuranceProduct> products = null;

		System.out.println("원하는 키워드를 선택해주세요.");
		System.out.println("1. 상품 ID \n2. 상품이름 \n3. 상품관리자 ID");
		int chooseMenu = getUserSelectInt();

		switch (chooseMenu) {
			case 1:
				String checkProductID = getInputStr("찾으려는 상품의 ID를 입력하세요");
				products = manager.searchProductsByKey("product_id", checkProductID);
				break;
			case 2:
				String checkProductName = getInputStr("찾으려는 상품의 이름을 입력하세요");
				products = manager.searchProductsByKey("product_name", checkProductName);
				break;
			case 3:
				String checkProductManagerID = getInputStr("찾으려는 상품의 상품관리자 id를 입력하세요");
				products = manager.searchProductsByKey("user_id", checkProductManagerID);
				break;
			default:
				System.out.println("키워드 검색을 종료합니다.");
				return;
		}

		for (InsuranceProduct product : products) {
			System.out.println(product.toString());
		}
	}

	// 0529 완
	private InsuranceProduct showProductDetail(ArrayList<InsuranceProduct> products) {
		if (products != null)
			((ProductManagementMenu) menu).showDetail(products.get(0));
		else
			return null;
		return products.get(0);
	}


	public void updateInsuranceProduct() {
		ProductManagement productManagement = (ProductManagement) loginedUser;
		showInsuranceProduct("상품정보 수정");

		menu.printMenuGuide("수정할 상품의 ID를 선택해주세요");
		InsuranceProduct product = productManagement.searchProductsByKey("product_id",
				getInputStr("상품ID")).get(0);
		String productID = product.getProductID();
		String productManagementID = productManagement.getUserID();
		menu.printMenuGuide("상품 정보를 수정해주세요.(비어있으면 이전 정보가 유지됩니다)");

		if (productManagement.updateProduct(
				((ProductManagementMenu) menu).updateProductPrompt(product, productManagementID))
		) {
			menu.printLog("상품 정보 수정에 성공했습니다.", true);
			showProductDetail(productManagement.searchProductsByKey("product_id", productID));
		} else {
			menu.printLog("상품 정보 수정에 실패했습니다.", false);
		}

	}

	public void deleteInsuaranceProduct() {
		ProductManagement manager = (ProductManagement) loginedUser;
		String input = null;
		boolean result = false;

		while (true) {
			input = getInputStr("삭제하려는 상품의 ID를 입력해주세요");
			if (input != null) {
				result = manager.deleteProduct(input);
				break;
			}
		}
		if (result)
			System.out.println("삭제가 완료되었습니다.");
		else
			System.out.println("문제가 발생했습니다.");
	}

	public HashMap<String, String> checkHashMap() {
		HashMap<String, String> hash = new HashMap<>();
		String ageg[] = {"0", "10", "20", "30", "40", "50", "60"};
		ArrayList<String> inputs = new ArrayList<>();
		System.out.println("연령대별 보장금액");
		while (true) {
			for (String age : ageg) {
				System.out.print(age + "대 : ");
				String input = scanner.nextLine();
				inputs.add(input);
			}
			if (inputs.size() != 7) {
				System.out.println("모든 값을 입력해주세요.");
				continue;
			}
			boolean isInt = true;
			for (String input : inputs) {
				if (!isInteger(input)) {
					System.out.println("숫자를 입력해주세요.");
					isInt = false;
					break;
				}
			}
			if (!isInt)
				continue;
			for (int i = 0; i < ageg.length - 1; i += 2)
				hash.put(ageg[i], inputs.get(i));
			break;
		}
		return hash;
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Sex checkSexInput() {
		Sex sex = null;
		String value = "" ;
		while (true) {
			System.out.print("   성별 [M/F] : ");
			value = scanner.nextLine();
			if (value.equals("m") || value.equals("M") || value.equals("Male") || value.equals("male")) {
				sex = Sex.MALE;
				break;
			} else if (value.equals("f") || value.equals("F") || value.equals("Female") || value.equals(
					"female")) {
				sex = Sex.FEMALE;
				break;
			}
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
		return sex;
	}

	// Loss Adjuster =============================================================

	/**
	 * search관련 메소드 분리되지 않음
	 */
	private void payCompensation() {
		LossAdjuster lossAdjuster = (LossAdjuster) loginedUser; // 관리자 로딩
		EventList eventList = lossAdjuster.getEventList();
		CustomerList customerList = lossAdjuster.getCustomerList();

		//심사 완료, 보상 지급 미완료만 가져와야함
		List<Event> events = eventList.searchEvent(new HashMap<>() {{
			put("state_of_evaluation", Integer.toString(ProcessState.Completed.getValue()));
			put("state_of_compensation", Integer.toString(ProcessState.Awaiting.getValue()));
		}});
		//이벤트 선택
		Event targetEvent = genericPageSelection(events, 2);
		if (targetEvent == null) {
			System.out.println("오류가 발생했습니다.");
			return;
		}
		System.out.println("보상을 지급하시겠습니까? 예상 지급액: "+targetEvent.getEvaluation().getCompensation().getCompensationValue()+"원");
		switch (getUserSelectYorN()) {
			case UserSelection.Yes:
				System.out.println("보상 지급이 " + (lossAdjuster.payCompensation(targetEvent.getEventID(),true) ? "승인되었습니다." : "실패하였습니다."));
				break;
			case UserSelection.No:
				System.out.println("보상 지급이 " + (lossAdjuster.payCompensation(targetEvent.getEventID(),false)? "거절되었습니다." : "실패하였습니다."));
				break;
			case UserSelection.Cancel:
				System.out.println("보상 지급이 취소되었습니다.");
				break;
		}
	}

	private void evaluateCompensation() {
		// 관리자 로딩
		LossAdjuster lossAdjuster = (LossAdjuster) loginedUser;

		// 리스트 로딩
		EventList eventList = lossAdjuster.getEventList();
		ContractList contractList = lossAdjuster.getContractList();
		CustomerList customerList = lossAdjuster.getCustomerList();

		//심사 대기중인 사고만 읽어오기
		Event targetEvent = genericPageSelection(eventList.searchEvent("state_of_evaluation", Integer.toString(ProcessState.Awaiting.getValue())),2);
		if(targetEvent == null) {
			System.out.println("오류가 발생했습니다.");
			return;
		}

		//이벤트의 주체가되는 고객과 고객이 맺은 계약들을 보여준다.
		//lossAdjuster안에서, 상품정보를 조회해서 자동적으로 보상액을 계산해야한다.
		Customer customer = customerList.search(targetEvent.getCustomerID());
		if(customer == null) {
			System.out.println("오류가 발생했습니다.");
			return;
		}

		System.out.println("==고객 정보==\n"+customer+"\n===================================================================================");
		List<Contract> contracts = contractList.searchByStateKeyValue(ProcessState.Completed,"customer_id",customer.getCustomerID()); // 패스된 계약만 가져와야함
		Contract selectedContract = detailContractSelection(contracts);
		if(selectedContract == null) return;


		int predictedCompensationValue = lossAdjuster.predictCompensationValue(selectedContract,targetEvent);
		System.out.println(predictedCompensationValue);
		if(predictedCompensationValue < 0) {
			System.out.println("보상액을 산정할수 없습니다");
			return;
		}

		System.out.println("예상 보상액은 "+predictedCompensationValue+"원 입니다");

		System.out.println("보상 심사를 허가하시겠습니까?");
		switch (getUserSelectYorN()){
			case UserSelection.Yes:
				System.out.println("보상 심사가 "+((lossAdjuster.evaluateCompensation(targetEvent.getEventID(),true,predictedCompensationValue)) ?"승인되었습니다.":"실패하였습니다."));
				break;
			case UserSelection.No:
				System.out.println("보상 심사가 "+((lossAdjuster.evaluateCompensation(targetEvent.getEventID(),true,predictedCompensationValue)) ?"거부되었습니다.":"실패하였습니다."));
				break;
			case UserSelection.Cancel:
				System.out.println("보상 심사가 취소되었습니다.");
				break;
		};
	}

	private Contract detailContractSelection(List<Contract> contracts){
		// 관리자 로딩
		LossAdjuster lossAdjuster = (LossAdjuster) loginedUser;
		InsuranceProductList products = lossAdjuster.getInsuranceProductList();

		System.out.println("상품 정보를 볼 계약을 선택해주세요");
		Contract contract = genericPageSelection(contracts,2);
		if(contract == null) return null;
		InsuranceProduct product = products.searchProductsByKey("product_id",contract.getProductID()).getFirst();
		System.out.println("==상품정보==\n"+product+"\n===================================================================================");
		System.out.println("이 계약으로 선택하시겠습니까? 다시 보고싶으시면 NO를 선택해주세요");
		switch (getUserSelectYorN()){
			case UserSelection.Yes:
				return contract;
			case UserSelection.No:
				return detailContractSelection(contracts);
			case UserSelection.Cancel:
				System.out.println("취소되었습니다.");
				return null;
		};
		System.out.println("알수없는 이유로 실행이 종료되었습니다");
		return null;
	}


	//UW
	private void underWriting(){
		UnderWriter underWriter = (UnderWriter)loginedUser;
		CustomerList customerList =underWriter.getCustomerList();
		InsuranceProductList insuranceProductList =underWriter.getInsuranceProductList();
		ContractList contractList = underWriter.getContractList();
		Contract contract = genericPageSelection(contractList.searchByKeyValue("state","0"),5);
		if(contract == null) return;
		System.out.println(customerList.search(contract.getCustomerID()));
		InsuranceProduct insuranceProduct = insuranceProductList.searchProductsByKey("product_id",contract.getProductID()).getFirst();
		System.out.println(insuranceProduct);
		System.out.println("계약을 승인하시겠습니까");
		switch (getUserSelectYorN()){
			case UserSelection.Yes:
				System.out.println("계약 심사가 "+(underWriter.underwrite(contract.getContractID(),true)?"승인되었습니다.":"실패하였습니다."));
				break;
			case UserSelection.No:
				System.out.println("계약 심사가 "+(underWriter.underwrite(contract.getContractID(),false)?"거절되었습니다.":"실패하였습니다."));
				break;
			case UserSelection.Cancel:
				System.out.println("계약 심사가 취소되었습니다.");
				break;
		};

	}


	// Common method =============================================================

	/**
	 * 아무 리스트 받아서, 보여주고, 선택된거 반환
	 * @param <T> 리스트에 포함될 요소의 타입(무시해도 됨)
	 * @param items 선택할 요소들이 담긴 ArrayList
	 * @param itemsPerPage 한 페이지에 보여줄 요소의 개수 (예: 5)
	 * @return 사용자가 선택한 요소, 선택을 취소하거나 잘못된 입력 시 null 반환
	 */
	private  <T> T genericPageSelection(List<T> items, int itemsPerPage) {
		if (items == null || items.isEmpty()) {
			System.out.println("선택할 항목이 없습니다.");
			return null;
		}
		if (itemsPerPage <= 0) {
			System.out.println("한 페이지에 보여줄 항목 수는 1 이상이어야 합니다.");
			return null;
		}

		int totalItems = items.size();
		int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
		int currentPage = 0; // 0-indexed 페이지 번호

		while (true) {
			System.out.println("\n--- 페이지 " + (currentPage + 1) + " / " + totalPages + " ---");
			int startIndex = currentPage * itemsPerPage;
			int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

			if (startIndex >= totalItems) { // 마지막 페이지를 넘어갔을 때 다시 이전 페이지로
				currentPage = totalPages - 1;
				startIndex = currentPage * itemsPerPage;
				endIndex = Math.min(startIndex + itemsPerPage, totalItems);
			}
			for (int i = startIndex; i < endIndex; i++) {
				// 사용자에게 보여줄 1부터 시작하는 인덱스 번호
				System.out.println((i - startIndex + 1) + ".\n" + items.get(i));
			}

			System.out.println("\n[ 이전 페이지로 이동, ] 다음 페이지로 이동, (1-" + (endIndex - startIndex) + ") 항목 선택, q 종료");
			System.out.print("입력: ");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("q")) {
				System.out.println("항목 선택을 취소합니다.");
				return null;
			} else if (input.equals("[")) {
				if (currentPage > 0) {
					currentPage--;
				} else {
					System.out.println("첫 페이지입니다.");
				}
			} else if (input.equals("]")) {
				if (currentPage < totalPages - 1) {
					currentPage++;
				} else {
					System.out.println("마지막 페이지입니다.");
				}
			} else {
				try {
					int selection = Integer.parseInt(input);
					if (selection >= 1 && selection <= (endIndex - startIndex)) {
						// 사용자가 선택한 번호(1-indexed)를 실제 ArrayList 인덱스(0-indexed)로 변환
						return items.get(startIndex + selection - 1);
					} else {
						System.out.println("유효하지 않은 번호입니다. 다시 시도해주세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
				}
			}
		}
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
	public int getUserSelectInt() {
		System.out.print(">> ");
		String userInput = scanner.nextLine();
		return Integer.parseInt(userInput == null ? "-1" : userInput);
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
			System.out.print("   " + title + ": ");
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

	public void printHorizontallyInColumns(List<?> items, int columns) {
		if (items == null || items.isEmpty()) {
			System.out.println("출력할 항목이 없습니다.");
			return;
		}
		List<List<String>> itemLines = new ArrayList<>();
		int maxLinesPerItem = 0; // 각 객체의 toString() 결과 중 최대 줄 수
		for (Object item : items) {
			// toString() 결과의 첫 번째 줄바꿈 문자열 제거 (예: \n || 로 시작하는 경우)
			String rawString = item.toString();
			if (rawString.startsWith("\n")) {
				rawString = rawString.substring(1);
			}
			List<String> lines = Arrays.asList(rawString.split("\n"));
			itemLines.add(lines);
			if (lines.size() > maxLinesPerItem) {
				maxLinesPerItem = lines.size();
			}
		}
		// 각 줄을 가로로 결합하여 출력합니다.
		for (int lineIndex = 0; lineIndex < maxLinesPerItem; lineIndex++) {
			StringBuilder currentLineOutput = new StringBuilder();
			int currentItemInRow = 0; // 현재 행에 출력된 아이템 개수

			for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
				if (currentItemInRow >= columns) {
					break; // 현재 행에 이미 columns 개수만큼의 아이템을 배치했으므로 다음 행으로 넘어감
				}

				List<String> linesOfCurrentItem = itemLines.get(itemIndex);
				String lineContent = "";
				if (lineIndex < linesOfCurrentItem.size()) {
					lineContent = linesOfCurrentItem.get(lineIndex);
				}
				int itemColumnWidth = 0;
				if (!itemLines.isEmpty() && !itemLines.get(0).isEmpty()) {
					itemColumnWidth = itemLines.get(0).get(0).length();
				}

				currentLineOutput.append(String.format("%-" + itemColumnWidth + "s", lineContent));

				if (currentItemInRow < columns - 1) {
					currentLineOutput.append("   ");
				}
				currentItemInRow++;
			}
			System.out.println(currentLineOutput.toString());
		}

		System.out.println("\n"); // 각 객체 블록 끝에 구분선
	}
}
