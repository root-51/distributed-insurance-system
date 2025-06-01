package main.Employee;

import java.util.*;
import java.sql.*;

import main.DAO.DAO;
import main.DAO.Utillity;
import main.Enum.Sex;
import main.Data.InsuranceProduct;
import main.List.CustomerList;
import main.List.InsuranceProductList;

public class ProductManagement extends Employee {

	public ProductManagement(int numOfEmployees, EmployeeType employeeType) {
		super(numOfEmployees, employeeType);
	}

	/**
	 * 보험 상품 생성
	 *
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
	public boolean createProduct(HashMap<String, String> coverageByAge,
			int exemptionPeriod, int reductionPeriod, int reductionRatio, String productName, Sex sex,
			int premium,
			int maxAge, int maxNumberEvent, String productManagementId) {
		try {
			if (DAO.executeQuery("SELECT product_id FROM insurance_product WHERE product_name = ?",
					productName).toInsuranceProduct().get(0) == null) {
				System.out.println("동일 상품이 존재합니다.");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		String sql =
				"INSERT INTO insurance_product (product_id, coverage_by_age, exemption_period, max_age, " +
						"max_number_event, premium, product_name, reduction_period, reduction_ratio, sex, product_management_id) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			DAO.executeQuery(sql,
					Utillity.generateID('P'),
					Utillity.map2Json(coverageByAge),
					exemptionPeriod,
					maxAge,
					maxNumberEvent,
					premium,
					productName,
					reductionPeriod,
					reductionRatio,
					sex.getValue(),
					productManagementId);
			return true;
		} catch (Exception e) {
			System.out.println("알수없는 오류로 실패했습니다.");
			return false;
		}
	}

		/**
		 * 보험 상품 삭제
		 * @param productID
		 * @return delete 성공 시 true, 아닐 시 false
		 */
		public boolean deleteProduct(String productID){
			try {
				DAO.executeQuery("DELETE FROM insurance_product WHERE product_id = ?", productID);
				return true;
			} catch (SQLException e) {
				return false;
			}
		}

		/**
		 * 보험 상품 수정
		 * @param product
		 * @return update 성공 시 true, 실패 시 false
		 */
		public boolean updateProduct (InsuranceProduct product) {
			String sql = "UPDATE insurance_product SET " +
					"coverage_by_age = ?, exemption_period = ?, max_age = ?, " +
					"max_number_event = ?, premium = ?, product_name = ?, " +
					"reduction_period = ?, reduction_ratio = ?, sex = ? " +
					"WHERE product_id = ?";
			try {
				DAO.executeQuery(
						sql,Utillity.map2Json(product.getCoverageByAge()),
						product.getExemptionPeriod(),
						product.getMaxAge(),
						product.getMaxNumberEvent(),
						product.getPremium(),
						product.getProductName(),
						product.getReductionPeriod(),
						product.getReductionRatio(),
						product.getSex().getValue(),
						product.getProductID());
				return true;
			} catch (Exception e) {
				return false;
			}
		}


		/**
		 * 상품 id로 조회
		 * @param productID
		 * @return
		 */
		public InsuranceProduct searchProduct (String productID){
			try {
				return DAO.executeQuery("SELECT product_id FROM insurance_product WHERE product_id = ?", productID).toInsuranceProduct().get(0);
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * 보험 상품 키워드 조회 (productid, productname, productmanagementid)
		 * @param key
		 * @param value
		 * @return search 성공 시 true, 실패 시 false
		 */
		public List<InsuranceProduct> searchProducts (InsuranceProductList insuranceProductList, String
		key, String value){
			try {
				return DAO.executeQuery("SELECT product_id FROM ? WHERE product_id = ?",key, value).toInsuranceProduct();
			} catch (Exception e) {
				return null;
			}
		}

}

