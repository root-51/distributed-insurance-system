package main.Employee;

import java.util.*;

import main.Enum.Sex;
import main.Data.InsuranceProduct;
import main.List.CustomerList;
import main.List.InsuranceProductList;

public class ProductManagement extends User {

	public ProductManagement(int numOfEmployees, UserType employeeType, CustomerList customerList) {
		super(numOfEmployees, employeeType);
	}

	/**
	 * 보험 상품 생성
	 * 
	 * @param insuranceProducts
	 * @param coverageByAge
	 * @param exemptionPeriod
	 * @param reductionPeriod
	 * @param reductionRatio
	 * @param productName
	 * @param sex
	 * @param premium
	 * @param maxAge
	 * @param maxNumberEvent
	 * @return 생성 성공 시 true, 실패 시 false
	 */
	public boolean createProduct(InsuranceProductList insuranceProducts, HashMap<String, String> coverageByAge,
			int exemptionPeriod, int reductionPeriod, int reductionRatio, String productName, Sex sex, int premium,
			int maxAge, int maxNumberEvent) {
		InsuranceProduct product = null;
		if (!insuranceProducts.checkProduct(productName)) {
			product = new InsuranceProduct.InsuranceBuilder().coverageByAge(coverageByAge)
					.exemptionPeriod(exemptionPeriod).reductionPeriod(reductionPeriod).reductionRatio(reductionRatio)
					.productManagementID(getUserID()).productName(productName).sex(sex).premium(premium)
					.maxAge(maxAge).maxNumberEvent(maxNumberEvent).build();
		}
		return insuranceProducts.insert(product);
	}

	/**
	 * 보험 상품 삭제
	 * 
	 * @param insuranceProductList
	 * @param productID
	 * @return delete 성공 시 true, 아닐 시 false
	 */
	public boolean deleteProduct(InsuranceProductList insuranceProductList, String productID) {
		return insuranceProductList.delete(productID);
	}

	/**
	 * 보험 상품 수정
	 * 
	 * @param insuranceProductList
	 * @param product
	 * @return update 성공 시 true, 실패 시 false
	 */
	public boolean updateProduct(InsuranceProductList insuranceProductList, InsuranceProduct product) {
		return insuranceProductList.update(product);
	}

	/**
	 * 상품 id로 조회
	 * 
	 * @param insuranceProductList
	 * @param productID
	 * @return
	 */
	public InsuranceProduct searchProduct(InsuranceProductList insuranceProductList, String productID) {
		return insuranceProductList.search(productID);
	}

	/**
	 * 보험 상품 키워드 조회 (productid, productname, productmanagementid)
	 * 
	 * @param insuranceProductList
	 * @param key
	 * @param value
	 * @return search 성공 시 true, 실패 시 false
	 */
	public InsuranceProductList searchProducts(InsuranceProductList insuranceProductList, String key, String value) {
		return insuranceProductList.searchProducts(key, value);
	}

	/**
	 * 보험 상품 리스트 중 index 이용해 조회
	 * 
	 * @param insuranceProductList
	 * @param index
	 * @return 사용자가 선택한 index값의 보험 상품 정보 반환
	 */
	public InsuranceProduct getProduct(InsuranceProductList insuranceProductList, int index) {
		return insuranceProductList.getProduct(index);
	}

}