package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import main.Data.Customer;
import main.Data.InsuranceProduct;
import main.Employee.Employee.EmployeeType;
import main.Enum.Sex;
import main.Enum.UserSelection;

public class Menu { // TODO: rename to IOManager
	private String[] menuList;
	private String userTypeText;
	private Scanner scanner;

	public Menu() {
		scanner = new Scanner(System.in);
	}

	public void createPrompt() {

	}

	public void show() {

	}


	public void showDetail() {

	}

	public void search() {

	}

	public void delete() {

	}

	public void update() {

	}

	// common methods
	public void setMenuList(String[] menuList) {
		this.menuList = menuList;
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeText = userTypeStr;
	}

	public void printMenuList() {
		System.out.println("   0. 종료");
		for (int i = 0; i < menuList.length; i++) {
			System.out.println("   " + (i + 1) + ". " + menuList[i]);
		}

	}
	public void printMenuList(String[] menuList) {
		System.out.println("   0. 종료");
		for (int i = 0; i < menuList.length; i++) {
			System.out.println("   " + (i + 1) + ". " + menuList[i]);
		}

	}

	public void printMenuHeader(String message) {
		System.out.println("============================");
		System.out.println(message + "\t\t      " + userTypeText);
		System.out.println("============================\n");

	}

	public void printMenuGuide(String message) {
		System.out.println(message);
	}

	public void printLog(String message, boolean isSuccessed) {
		if (isSuccessed) {
			System.out.print("✔️ ");
		} else {
			System.out.print("❌ ");
		}
		System.out.print(message + "\n");
	}

	public void printTitle() {
		System.out.println("+--------------------------+\n");
		System.out.println("         보험사 프로그램         \n");
		System.out.println("             박솔민, 이종민 장소윤 ");
		System.out.println("+--------------------------+\n\n");

		System.out.println();
	}
	public int getUserSelectInt() {
		System.out.print(">> ");
		return Integer.parseInt(scanner.nextLine());
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
	public int computMaxPage(int listSize){
		int maxPage=1;
		if(listSize%3==0) maxPage = listSize/3;
		else maxPage = listSize/3+1;

		return maxPage;
	}

	// inner class =================================
	public static class CustomerMenu extends Menu {
		public CustomerMenu() {
			String[] menuList = { "보험료 납부", "사고 접수", "사고 갱신", "사고 접수 이력 조회" };
			setMenuList(menuList);
			setUserTypeStr("고객");

		}
	}

	public static class SalesMenu extends Menu {
		public SalesMenu() {
			String[] menuList = { "신규고객 등록", "고객 삭제", "고객 정보 수정", "고객 정보 조회", "신규 계약 등록", "계약 삭제", "계약 정보 수정",
					"계약 정보 조회" };
			setMenuList(menuList);
			setUserTypeStr("영업사원");

		}

		@Override
		public void createPrompt() {
			printMenuHeader("신규 고객 등록");
			printMenuGuide("신규 고객의 정보를 입력해주세요.");

			// TODO: SystemManager에서 createCustomer할 때 input을 받고있는데, 이 함수에서 input을 받고싶었지만 여러
			// 이유로 못함
		}


		public void show(ArrayList<Customer> customers, int currentPage) {
			printMenuHeader("고객 조회");
			if (customers.size() == 0) {
				System.out.println("   등록된 고객이 없습니다.");
			}
			System.out.println("   ID \tName");
			for (Customer customer : customers) {
				System.out.println("   "+customer.getCustomerID() + "\t" + customer.getName());
			}
			System.out.println();
			printMenuGuide("메뉴를 선택해주세요.");
		}
		public ArrayList<Customer> getNextCustomersInPage(ArrayList<Customer> customers,int currentPage, int startIndex){
			ArrayList<Customer> customersInPage= new ArrayList<>();
			int maxPage = super.computMaxPage(customers.size());
			for(int i=startIndex ; i<currentPage*3 && currentPage<=maxPage ; i++){
				if(i<customers.size()) { customersInPage.add(customers.get(i));}
			}

			return customersInPage;
		}


		public void showDetail(Customer customer) {
			System.out.println("이름\t" + customer.getName());
			System.out.println("나이\t" + customer.getAge());
			System.out.println("주민번호\t" + customer.getRrn());
			System.out.println("계좌번호\t" + customer.getAccountNumber());
			System.out.println("직업\t" + customer.getJob());
			System.out.println("전화번호\t" + customer.getPhoneNumber());
			System.out.println("고객ID\t" + customer.getCustomerID());
		}
	}

	public static class ProductManagementMenu extends Menu {
		public ProductManagementMenu() {
			String[] menuList = { "상품 등록", "상품 수정", "상품 조회", "상품 삭제" };
			setMenuList(menuList);
			setUserTypeStr("상품관리자");

		}

		@Override
		public void createPrompt() {
			printMenuHeader("신규 상품 등록");
			printMenuGuide("신규 상품의 정보를 입력해주세요.");
		}

		public void showTenPerPage(ArrayList<InsuranceProduct> products){ //10개씩 보여주기
			int currentPage=1;
			int startIndex=0;
			ArrayList<InsuranceProduct> productsInPage= new ArrayList<>();
			for(int i=startIndex;i<currentPage*10;i++){
				productsInPage.add(products.get(i));
			}
            int userSelect = 3;
            if(userSelect==3){
                currentPage++;
                startIndex=currentPage*10-1;
                productsInPage.clear();
                productsInPage = getNextProductsInPage(products,currentPage,startIndex);
            }

		}

		public ArrayList<InsuranceProduct> getNextProductsInPage(ArrayList<InsuranceProduct> products,int currentPage, int startIndex){
            ArrayList<InsuranceProduct> productsInPage= new ArrayList<>();
            for(int i=startIndex;i<currentPage*10;i++){
                productsInPage.add(products.get(i));
            }
            return productsInPage;
		}
		public void show(ArrayList<InsuranceProduct> products) {
			printMenuHeader("보험 상품 목록");
			printMenuGuide("메뉴를 선택해주세요.");
			if (products.size() == 0) {
				System.out.println("   등록된 고객이 없습니다.");
			}
			System.out.println("상품ID\t보험상품명\t상품개발자ID");
			for (int i = 0; i < 10; i++) {
				System.out.println(
						products.get(i).getProductID() + "\t" +
						products.get(i).getProductName()+ "\t" +
						products.get(i).getProductManagementID()
				);
			}
			printMenuList(new String[] {"보험상품 상세정보 조회","보험상품 키워드 검색", "다음 페이지"});
			int userSelect = getUserSelectInt();
			switch(userSelect) {
			case 1: //보험상품 상세정보 조회
				printMenuGuide("조회할 상품의 번호를 입력해주세요.");
				int selectedProductNum = super.getInputInt("보험상품 ID");
				showDetail(products.get(selectedProductNum-1));
				break;
			case 2: //보험상품 키워드 검색
				break;
			case 3: //보험상품 키워드 검색
				break;
			}
		}

		public void showDetail(InsuranceProduct insuranceProduct) {
			System.out.println("상품명\t" + insuranceProduct.getProductName());
//			System.out.println("나이\t" + insuranceProduct.getAge());
//			System.out.println("주민번호\t" + insuranceProduct.getRrn());
//			System.out.println("계좌번호\t" + insuranceProduct.getAccountNumber());
//			System.out.println("직업\t" + insuranceProduct.getJob());
//			System.out.println("전화번호\t" + insuranceProduct.getPhoneNumber());
//			System.out.println("고객ID\t" + insuranceProduct.getCustomerID());
		}
	}

	public static class LossAdjusterMenu extends Menu {
		public LossAdjusterMenu() {

			String[] menuList = { "보상 지급", "보상 심사" };
			setMenuList(menuList);
			setUserTypeStr("영업사원");

		}
	}

	public static class UnderWriterMenu extends Menu {
		public UnderWriterMenu() {
			String[] menuList = { "신규고객 등록", "고객 삭제", "고객 정보 수정", "고객 정보 조회", "신규 계약 등록", "계약 삭제", "계약 정보 수정",
					"계약 정보 조회" };
			setMenuList(menuList);
			setUserTypeStr("영업사원");

		}
	}

}
