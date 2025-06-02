package main;

import java.util.*;

import main.Employee.User.UserType;
import main.Menu.*;

import main.Data.*;
import main.Employee.*;
import main.Enum.*;
import main.List.*;

public class SystemManager {

	private User loginedUser;
	private UserType loginedUserType;
	private CustomerList customerList;
	private InsuranceProductList insuranceProductList;
	private ContractList contractList;
	private Scanner scanner;

	private Menu menu;

	public SystemManager(CustomerList customerList,
			InsuranceProductList insuranceProductList, ContractList contractList, User loginedUser) {
		this.loginedUser = loginedUser;
		this.loginedUserType = loginedUser.getUserType();
		this.customerList = customerList;
		this.insuranceProductList = insuranceProductList;
		this.contractList = contractList;
		this.scanner = new Scanner(System.in);

		setMenu();
		menu.printTitle();
	}

	public void setMenu() {
		switch (loginedUserType) {
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
				// createContract();
				break;
			case 6:
				// deleteContract();
				break;
			case 7:
				// updateContract();
				break;
			case 8:
				// searchContract();
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
		}

	}

	// Sales =============================================================
	// 0529 완
	private void createCustomer() { //
		menu.createPrompt();
		if (((Sales) loginedUser).createCustomer(getInputStr("계좌번호"), getInputStr("주소"), getInputInt("나이"),
				Integer.toString(customerList.getAll().size()), // TODO: CustomerID는 DB에서 부여하나?
				getInputStr("직업"), getInputStr("이름"), getInputStr("전화번호"), getInputStr("주민번호"), checkSexInput())) {
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
        int maxPage=menu.computeMaxPage(customers.size());
        int currentPage=1;
        int startIndex=0;

        ((SalesMenu)menu).show(((SalesMenu)menu).getNextCustomersInPage(customers,currentPage,startIndex), currentPage);
		boolean escapeMenu = false;
		do{
			menu.printMenuList(new String[]{
					nextCommand.equals("상세정보조회")?"종료":nextCommand,
					"상세정보조회", "키워드검색","다음페이지"});
			int userSelect = getUserSelectInt();
			switch(userSelect){
				case 0:
					escapeMenu=true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 고객의 ID를 입력해주세요");
					int selectedUserID=getInputInt("고객 ID");
					menu.printMenuHeader(nextCommand);
					showCustomerDetail(customers.get(selectedUserID-1));
					break;
				case 2: // TODO: 키워드검색
					System.out.println("서비스 준비중입니다.");
					break;
				case 3: // 다음 페이지 출력
					if(currentPage==maxPage){
						startIndex=0;
						currentPage=1;
					}else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((SalesMenu)menu).show(
							((SalesMenu)menu).getNextCustomersInPage(customers,currentPage,startIndex),
							currentPage
					);
					break;
			}
		}while(!escapeMenu);
	}

	// TODO: searchCustomer 키워드 검색 메뉴
	private Customer searchCustomer() {
		String customerID = getInputStr("검색할 고객 ID를 입력해주세요");
		Sales sales = (Sales) loginedUser;
		Customer selectedCustomer = sales.getCustomer(customerID);
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
		Customer customer = sales.getCustomer(getInputStr("고객ID"));
		String customerID = customer.getCustomerID(); // 입력한 고객 ID 에 해당하는 고객이 없는 경우 방지

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
			menu.printLog("고객 정보 수정에 성공했습니다.",true);
			showCustomerDetail(sales.getCustomer(customerID));
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

		if (sales.deleteCustomer(customerID)){
			menu.printLog("고객 삭제에 성공했습니다.",true);
		} else {
			menu.printLog("고객 삭제에 실패했습니다.", false);
		}

	}

	// ProductManagement =============================================================
	// 0529 완
	public void createInsuranceProduct() {
		menu.createPrompt();

		// TODO: createProduct에 insuranceProductList 를 파라미터로 넣는 이유?
		if (((ProductManagement) loginedUser).createProduct(checkHashMap(), getInputInt("면책기간(년)"), getInputInt("감액기간(년)"),
				getInputInt("감액기간 보장금액 비율(%)"), getInputStr("보험상품 이름"), checkSexInput(), getInputInt("보험료"),
				getInputInt("최대 가입 연령"), getInputInt("최대 사고 횟수")))
			menu.printLog("상품이 정상적으로 등록되었습니다.", true);

		else
			menu.printLog("같은 이름의 상품이 있어 등록이 실패했습니다.", false);
	}

	// 0529 완
	private void showInsuranceProduct(String nextCommand){
		menu.printMenuHeader(nextCommand);
		ProductManagement productManagement = (ProductManagement) loginedUser;

		ArrayList<InsuranceProduct> products = productManagement.getAllProducts();
		int maxPage=menu.computeMaxPage(products.size());
		int currentPage=1;
		int startIndex=0;
		((ProductManagementMenu)menu).show(((ProductManagementMenu)menu).getNextProductsInPage(products,currentPage,startIndex),currentPage);
		boolean escapeMenu = false;
		do{
			menu.printMenuList(new String[]{
					nextCommand.equals("상세정보조회")?"종료":nextCommand,
					"상세정보조회", "키워드검색","다음페이지"});
			int userSelect = getUserSelectInt();
			switch(userSelect){
				case 0:
					escapeMenu=true;
					break;
				case 1: // 상세정보조회
					menu.printMenuGuide("상세정보를 조회할 상품의 ID를 입력해주세요");
					String selectedProductID=getInputStr("상품 ID");
					menu.printMenuHeader(nextCommand);
					showProductDetail(productManagement.searchProduct(selectedProductID));
					break;
				case 2: // TODO: 키워드검색
					System.out.println("서비스 준비중입니다.");
					break;
				case 3: // 다음 페이지 출력
					if(currentPage==maxPage){
						startIndex=0;
						currentPage=1;
					}else {
						startIndex = currentPage * 3;
						currentPage++;
					}
					((ProductManagementMenu)menu).show(
							((ProductManagementMenu)menu).getNextProductsInPage(products,currentPage,startIndex),
							currentPage
					);
					break;
			}
		}while(!escapeMenu);
	}

	// TODO: 검토
	public void searchInsuranceProduct() {

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
				System.out.println( ((ProductManagement) loginedUser).getProduct( index - 1).toString());
				break;
			}
		}
	}
	public void searchKeyWord() {
		ProductManagement manager = (ProductManagement) loginedUser;
		ArrayList<InsuranceProduct> products = null;

		System.out.println("원하는 키워드를 선택해주세요.");
		System.out.println("1.product id \n2.product name \n3.product management id");
		int chooseMenu = getUserSelectInt();

		switch (chooseMenu) {
			case 1:
				String checkProductID = getInputStr("찾으려는 상품의 ID를 입력하세요");
				products = manager.searchProducts("productID", checkProductID);
				break;
			case 2:
				String checkProductName = getInputStr("찾으려는 상품의 이름을 입력하세요");
				products = manager.searchProducts("productName", checkProductName);
				break;
			case 3:
				String checkProductManagerID = getInputStr("찾으려는 상품의 상품관리자 id를 입력하세요");
				products = manager.searchProducts("productManagementID", checkProductManagerID);
				break;
		}

		for(InsuranceProduct product : products){
			System.out.println(product);
		}
	}

	// 0529 완
	private InsuranceProduct showProductDetail(InsuranceProduct product) {
		((ProductManagementMenu) menu).showDetail(product);
		return product;
	}


	public void updateInsuranceProduct() {
		ProductManagement productManagement = (ProductManagement) loginedUser;
		showInsuranceProduct("상품정보 수정");

		menu.printMenuGuide("수정할 상품의 ID를 선택해주세요");
		InsuranceProduct product = productManagement.searchProduct(getInputStr("상품ID"));
		String productID = product.getProductID();
		String productManagementID= productManagement.getUserID();
		menu.printMenuGuide("상품 정보를 수정해주세요.(비어있으면 이전 정보가 유지됩니다)");

		if (productManagement.updateProduct(
				((ProductManagementMenu)menu).updateProductPrompt(product,productManagementID))
		) {
			menu.printLog("상품 정보 수정에 성공했습니다.",true);
			showProductDetail(productManagement.searchProduct(productID));
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
		while (true) {
			System.out.println("연령대별 보장금액: ");
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
	public Sex checkSexInput() {
		Sex sex = null;
		String value = "";
		while (true) {
			System.out.print("   성별 [M/F] : ");
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

	// Loss Adjuster =============================================================

	/**
	 * search관련 메소드 분리되지 않음
	 */
	private void payCompensation() {
		LossAdjuster lossAdjuster = (LossAdjuster) loginedUser; // 관리자 로딩

		EventList eventList = lossAdjuster.getEventList(); // 컴포지션... 관리자가 리스트를 들고 있음, 가져와야함

		// 보상 지급 대기중인 보상 조회 로직, 라인넘버 통해서 선택함,
		System.out.println("===CompensationList===");
		ArrayList<Event> events = eventList.searchEvent("state_of_compensation", "0"); // 일반 보상 지급이 아직 되지 않은 경우만 골라오긴 하는데,
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
		LossAdjuster lossAdjuster = (LossAdjuster) loginedUser; // 관리자 로딩

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
		ArrayList<Event> events = eventList.searchEvent("state_of_evaluation", "0"); // 심사 대기중 리스트 가져옴
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


	// Common method =============================================================

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
}
