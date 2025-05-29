package main.List;
import java.util.ArrayList;
import java.util.Iterator;
import main.Data.InsuranceProduct;

public class InsuranceProductListImpl implements InsuranceProductList {

	private ArrayList<InsuranceProduct> insuranceProducts;
	private int index = 1;

	public InsuranceProductListImpl(){
		insuranceProducts = new ArrayList<>();
	}

	/**
	 * 보험 상품 삭제
	 * @param productID
	 * @return delete 성공 시 true, 실패 시 false
	 */
	public boolean delete(String productID){
		Iterator<InsuranceProduct> iterator = insuranceProducts.iterator();
		while(iterator.hasNext()){
			InsuranceProduct insuranceProduct = iterator.next();
			if (insuranceProduct.getProductID().equals(productID)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	public boolean checkProduct(String productName){
		Iterator<InsuranceProduct> iterator = insuranceProducts.iterator();
		while(iterator.hasNext()){
			InsuranceProduct insuranceProduct = iterator.next();
			if (insuranceProduct.getProductName().equals(productName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 새로운 보험 상품 추가
	 * @param insuranceProduct
	 * @return insert 성공 시 true, 실패 시 false
	 */
	public boolean insert(InsuranceProduct insuranceProduct){
		if(insuranceProduct==null)
			return false;
		insuranceProduct.setProductID(generateProductID());
		return this.insuranceProducts.add(insuranceProduct);
	}

	/**
	 * 일치하는 productID 보험 상품 조회
	 * @param productID
	 * @return search 성공 시 해당 상품, 실패 시 null
	 */
	public InsuranceProduct search(String productID){
		Iterator<InsuranceProduct> iterator = insuranceProducts.iterator();
		while(iterator.hasNext()){
			InsuranceProduct insuranceProduct = iterator.next();
			if (insuranceProduct.getProductID().equals(productID)) {
				return insuranceProduct;
			}
		}
		return null;
	}

	/**
	 * 보험 상품 키워드 조회
	 * @param key
	 * @param value
	 * @return
	 */
	public InsuranceProductList searchProducts(String key, String value) {
		InsuranceProductList insuranceProductList = new InsuranceProductListImpl();

		for (InsuranceProduct product : insuranceProducts) {
			boolean match = false;

			switch (key.toLowerCase()) {
				case "productid":
					match = product.getProductID().equals(value);
					break;
				case "productname":
					match = product.getProductName().equals(value);
					break;
				case "productmanagementid":
					match = product.getProductManagementID().equals(value);
					break;
				default:
					System.out.println("지원하지 않는 검색 필드입니다: " + key);
					return insuranceProductList;
			}

			if (match) {
				insuranceProductList.insert(product);
			}
		}

		if (insuranceProductList.size()==0) {
			System.out.println("일치하는 보험상품이 없습니다.");
		}

		return insuranceProductList;
	}


	/**
	 *보험 상품 정보 업데이트
	 * @param updatedInsuranceProduct
	 * @return update 성공 시 true, 실패 시 false
	 */
	public boolean update(InsuranceProduct updatedInsuranceProduct){
		for (int i = 0; i < insuranceProducts.size(); i++) {
			InsuranceProduct existingInsuranceProduct = insuranceProducts.get(i);
			if (existingInsuranceProduct.getProductID().equals(updatedInsuranceProduct.getProductID())) {
				insuranceProducts.set(i, updatedInsuranceProduct);
				return true;
			}
		}
		return false;
	}

	/**
	 * 보험 상품의 productID를 자동으로 생성
	 * @return 생성된 productID
	 */
	public String generateProductID(){
		return "P"+String.format("%08d",index++);
	}

	/**
	 * 보험 상품 리스트의 사이즈 값
	 * @return 보험 상품 개수
	 */
	public int size(){
		return this.insuranceProducts.size();
	}

	/**
	 * 해당 index값의 보험 상품 반환
	 * @param index
	 * @return 해당 index값의 보험 상품 반환
	 */
	public InsuranceProduct getProduct(int index){
		return this.insuranceProducts.get(index);
	}

	@Override
	public ArrayList<InsuranceProduct> getAllProducts() {
		return this.insuranceProducts;
	}

	public void printAllProducts(){
		if(insuranceProducts.isEmpty()){
			System.out.println("등록된 보험 상품이 없습니다.");
			return;
		}
		for(InsuranceProduct product: insuranceProducts)
			System.out.println(product.toString());
	}
}