package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import main.Data.Customer;
import main.Data.InsuranceProduct;
import main.Enum.Sex;

public class Menu { // TODO: rename to IOManager
	private String[] menuList;
	private String userTypeText;
	private static Scanner scanner;

	public Menu() {
		scanner = new Scanner(System.in);
	}
	public void createPrompt() {}
	public void show() {}
	public void showDetail() {}
	public void search() {}
	public void delete() {}
	public void update() {}
	// common methods
	public void setMenuList(String[] menuList) {
		this.menuList = menuList;
	}
	public void setUserTypeStr(String userTypeStr) {
		this.userTypeText = userTypeStr;
	}
	public void printMenuHeader(String message) {
		System.out.println("============================");
		System.out.println(message + "\t\t      " + userTypeText);
		System.out.println("============================\n");
	}
	public void printMenuList() {
		for (int i = 0; i < menuList.length; i++) {
			System.out.println("   " + (i) + ". " + menuList[i]);
		}
	}
	public void printMenuList(String[] menuList) {
		for (int i = 0; i < menuList.length; i++) {
			System.out.println("   " + (i) + ". " + menuList[i]);
		}
	}public void printMenuGuide(String message) {
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
	public static int getUserSelectInt() {
		System.out.print(">> ");
		return Integer.parseInt(scanner.nextLine());
	}
	private static String getInputStr(String title) {
		String input = "";
		do {
			System.out.print("   " + title + ": ");
			input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   * 필수입력사항입니다.");
			}
		} while (input.isEmpty());
		return input;
	}
	private static String getInputOrKeepStr(String title, String prevValue) {
		String userInput = "";
		System.out.print("   "+title + ": ");
		userInput = scanner.nextLine().trim();
		if (userInput == null || userInput.equals("")) {
			userInput = prevValue;
		}
		return userInput;
	}
	private static int getInputInt(String title) {
		return Integer.parseInt(getInputStr(title));
	}
	private static int getInputOrKeepInt(String title, int prevValue) {
		return Integer.parseInt(getInputOrKeepStr(title, Integer.toString(prevValue)));
	}
	public static int computeMaxPage(int listSize){
		int maxPage=1;
		if(listSize%3==0) maxPage = listSize/3;
		else maxPage = listSize/3+1;

		return maxPage;
	}
	public static Sex getInputSex() {
		Sex sex = null;
		String value = "";
		do{
			System.out.println("   성별 [M/F]:");
			value = scanner.nextLine().trim();
			if(value.isEmpty()){
				System.out.println("   * 필수입력사항입니다.");
			}else if(!(value.equalsIgnoreCase("m")||value.equalsIgnoreCase("f"))){
				System.out.println("   * m 또는 f 를 입력해주세요.");
			}else{
				if(value.equalsIgnoreCase("f")) sex = Sex.FEMALE;
				else sex=Sex.MALE;
			}
		}while(value.isEmpty());
		return sex;
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
			String[] menuList = { "종료","신규고객 등록",  "고객 정보 조회","고객 정보 수정","고객 삭제",  "신규 계약 등록", "계약 삭제", "계약 정보 수정",
					"계약 정보 조회" };
			setMenuList(menuList);
			setUserTypeStr("영업사원");
		}
		@Override
		public void createPrompt() {
			printMenuHeader("신규 고객 등록");
			printMenuGuide("신규 고객의 정보를 입력해주세요.");
		}
		public void show(ArrayList<Customer> customers, int currentPage) {
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
			int maxPage = super.computeMaxPage(customers.size());
			for(int i=startIndex ; i<currentPage*3 && currentPage<=maxPage ; i++){
				if(i<customers.size()) { customersInPage.add(customers.get(i));}
			}

			return customersInPage;
		}
		public void showDetail(Customer customer) {
			System.out.println("이름\t\t" + customer.getName());
			System.out.println("나이\t\t" + customer.getAge());
			System.out.println("주민번호\t" + customer.getRrn());
			System.out.println("계좌번호\t" + customer.getAccountNumber());
			System.out.println("직업\t\t" + customer.getJob());
			System.out.println("전화번호\t" + customer.getPhoneNumber());
			System.out.println("고객ID\t" + customer.getCustomerID());
			System.out.println();
		}
	}
	public static class ProductManagementMenu extends Menu {
		public ProductManagementMenu() {
			String[] menuList = {"종료", "신규 상품 등록", "상품 조회", "상품 수정","상품 삭제" };
			setMenuList(menuList);
			setUserTypeStr("상품관리자");
		}
		@Override
		public void createPrompt() {
			printMenuHeader("신규 상품 등록");
			printMenuGuide("신규 상품의 정보를 입력해주세요.");
		}
		public void show(ArrayList<InsuranceProduct> products, int currentPage) {
			if (products.size() == 0) {
				System.out.println("   등록된 상품이 없습니다.");
			}
			System.out.println("   ID\t\t\tName");

			for (InsuranceProduct product : products) {
				System.out.println("   "+product.getProductID() + "\t" + product.getProductName());
			}
			System.out.println();
			printMenuGuide("메뉴를 선택해주세요.");
		}
		public ArrayList<InsuranceProduct> getNextProductsInPage(ArrayList<InsuranceProduct> products,int currentPage, int startIndex){
			ArrayList<InsuranceProduct> productsInpage= new ArrayList<>();
			int maxPage = super.computeMaxPage(products.size());
			for(int i=startIndex ; i<currentPage*3 && currentPage<=maxPage ; i++){
				if(i<products.size()) { productsInpage.add(products.get(i));}
			}

			return productsInpage;
		}
		public void showDetail(InsuranceProduct product) {
			System.out.println("   상품 ID\t\t" + product.getProductID());
			System.out.println("   상품이름\t\t" + product.getProductName());
			System.out.println("   보험료\t\t" + product.getPremium());
			System.out.println("   최대가입연령\t" + product.getMaxAge());
			System.out.println("   성별\t\t\t" + product.getSex());
			System.out.println("   최대사고횟수\t" + product.getMaxNumberEvent());
			System.out.println("   면책기간\t\t" + product.getExemptionPeriod());
			System.out.println("   감액기간\t\t" + product.getReductionPeriod());
			System.out.println("   감액률\t\t" + product.getReductionRatio());
			System.out.println("   상품개발자ID\t" + product.getProductManagementID());
			System.out.println();
		}
		public InsuranceProduct updateProductPrompt(InsuranceProduct product, String productManagementID){
			InsuranceProduct insuranceProduct = new InsuranceProduct.Builder()
							.productID(product.getProductID())
							.productName(getInputOrKeepStr("보험상품 이름",product.getProductName()))
							.maxAge(getInputOrKeepInt("최대가입연령",product.getMaxAge()))
							.premium(getInputOrKeepInt("보험료",product.getPremium()))
							.coverageByAge(inputCoverageByAge())
							.sex(getInputSex())
							.maxNumberEvent(getInputOrKeepInt("최대사고횟수",product.getMaxNumberEvent()))
							.exemptionPeriod(getInputOrKeepInt("면책기간",product.getExemptionPeriod()))
							.reductionPeriod(getInputOrKeepInt("감액기간",product.getReductionPeriod()))
							.reductionRatio(getInputOrKeepInt("감액비율", product.getReductionRatio()))
							.productManagementID(productManagementID).build();
			return insuranceProduct;
		}
		public HashMap<String,String> inputCoverageByAge(){
			HashMap<String, String> coverageByAge = new HashMap<>();
			System.out.println("   연령대별 보장금액:");
			for(int i=0;i<100;i+=10){
				String coverage = getInputStr(i+"세~"+(i+9)+"세");
				coverageByAge.put(Integer.toString(i),coverage);
			}
			return coverageByAge;
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




