package main.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import main.DAO.DAO;
import main.DAO.Utillity;
import main.Data.InsuranceProduct;

public class InsuranceProductListImpl implements InsuranceProductList {


	public InsuranceProductListImpl(){
	}


	public boolean delete(String productID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM insurance_product WHERE product_id = ?", productID);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public boolean insert(InsuranceProduct insuranceProduct){
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO insurance_product (product_id,coverage_by_age,exemption_period,max_age,max_number_event,premium,product_name,reduction_period,reduction_ratio,sex,user_id) VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					insuranceProduct.getProductID(),
					Utillity.map2Json(insuranceProduct.getCoverageByAge()),
					insuranceProduct.getExemptionPeriod(),
					insuranceProduct.getMaxAge(),
					insuranceProduct.getMaxNumberEvent(),
					insuranceProduct.getPremium(),
					insuranceProduct.getProductName(),
					insuranceProduct.getReductionPeriod(),
					insuranceProduct.getReductionRatio(),
					insuranceProduct.getSex().getValue(),
					insuranceProduct.getProductManagementID());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public ArrayList<InsuranceProduct> searchProductsByKey(String key, String value) {
		try (DAO dao = new DAO()){
			String sql = "SELECT * FROM insurance_product WHERE "+key+" = ?";
			return (ArrayList<InsuranceProduct>) dao.executeQuery(sql, value).toInsuranceProduct();
		}catch(Exception e){
			return null;
		}
	}

	public boolean update(InsuranceProduct product){
		try(DAO dao = new DAO()){
			String sql = "UPDATE insurance_product set product_name = ? , max_age = ? , premium = ? , max_number_event = ? , sex = ? , exemption_period = ? , reduction_period = ? , reduction_ratio = ? , coverage_by_age = ? WHERE product_id = ?";
			dao.executeQuery(sql,product.getProductName(),
					product.getMaxAge(),
					product.getPremium(),
					product.getMaxNumberEvent(),
					product.getSex().getValue(),
					product.getExemptionPeriod(),
					product.getReductionPeriod(),
					product.getReductionRatio(),
					mapToJson(product.getCoverageByAge()),
					product.getProductID());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public String mapToJson(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int count = 0;
		for (HashMap.Entry<String, String> entry : map.entrySet()) {
			sb.append("\"")
					.append(entry.getKey())
					.append("\":\"")
					.append(entry.getValue())
					.append("\"");
			if (++count < map.size()) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public ArrayList<InsuranceProduct> getAll() {
		try (DAO dao = new DAO()){
			return (ArrayList<InsuranceProduct>) dao.executeQuery("SELECT * FROM insurance_product").toInsuranceProduct();
		}catch(Exception e){
			return null;
		}

	}

}