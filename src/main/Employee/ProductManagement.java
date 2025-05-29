package main.Employee;

import java.util.*;

import main.Enum.Sex;
import main.Data.InsuranceProduct;
import main.List.InsuranceProductList;


public class ProductManagement extends Employee {
	public InsuranceProductList insuranceProductList;
	public ProductManagement(int numOfEmployees, EmployeeType employeeType, InsuranceProductList insuranceProductList) {

		super(numOfEmployees, employeeType);
		this.insuranceProductList=insuranceProductList;
	}

	public boolean createProduct(HashMap<String, String> coverageByAge,
			int exemptionPeriod, int reductionPeriod, int reductionRatio, String productName, Sex sex, int premium,
			int maxAge, int maxNumberEvent) {
		InsuranceProduct product = null;
		if (!insuranceProductList.checkProduct(productName)) {
			product = new InsuranceProduct.Builder().coverageByAge(coverageByAge)
					.exemptionPeriod(exemptionPeriod).reductionPeriod(reductionPeriod).reductionRatio(reductionRatio)
					.productManagementID(getUserID()).productName(productName).sex(sex).premium(premium)
					.maxAge(maxAge).maxNumberEvent(maxNumberEvent).build();
		}
		return insuranceProductList.insert(product);
	}

	public boolean deleteProduct(String productID) {
		return insuranceProductList.delete(productID);
	}

	public boolean updateProduct(InsuranceProduct product) {
		return insuranceProductList.update(product);
	}

	public InsuranceProduct searchProduct(String productID) {
		return insuranceProductList.search(productID);
	}

	public InsuranceProductList searchProducts(String key, String value) {
		return insuranceProductList.searchProducts(key, value);
	}

	public InsuranceProduct getProduct(int index) {
		return insuranceProductList.getProduct(index);
	}
	public ArrayList<InsuranceProduct> getAllProducts(){
		return insuranceProductList.getAllProducts();
	}

}