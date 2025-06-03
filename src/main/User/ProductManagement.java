package main.User;

import java.util.*;

import main.DAO.Utillity;
import main.Enum.Sex;
import main.Data.InsuranceProduct;
import main.List.InsuranceProductList;
import main.List.InsuranceProductListImpl;


public class ProductManagement extends User {
	public InsuranceProductList insuranceProductList;
  


  public ProductManagement(String userID, String userPW, UserType userType) {
    super(userID, userPW, userType);
	this.insuranceProductList = new InsuranceProductListImpl();
  }

  public boolean createProduct(int exemptionPeriod, int reductionPeriod, int reductionRatio, String productName, Sex sex, int premium,
							   int maxAge,HashMap<String, String> coverageByAge, int maxNumberEvent) {
	  if (insuranceProductList.searchProductsByKey("product_name",productName).size()!=0){
		  return false;
	  }
	  InsuranceProduct product = new InsuranceProduct.Builder().productID(Utillity.generateID('P')).coverageByAge(coverageByAge)
			  .exemptionPeriod(exemptionPeriod).reductionPeriod(reductionPeriod).reductionRatio(reductionRatio)
			  .productManagementID(getUserID()).productName(productName).sex(sex).premium(premium)
			  .maxAge(maxAge).maxNumberEvent(maxNumberEvent).build();
	  return insuranceProductList.insert(product);
	}

	public boolean deleteProduct(String productID) {
		return insuranceProductList.delete(productID);
	}

	public boolean updateProduct(InsuranceProduct product) {
		return insuranceProductList.update(product);
	}

	public ArrayList<InsuranceProduct> getAllProduct(){
		return insuranceProductList.getAll();
	}

	public ArrayList<InsuranceProduct> searchProductsByKey(String key, String value) {
		return insuranceProductList.searchProductsByKey(key, value);
	}
}