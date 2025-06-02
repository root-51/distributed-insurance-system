package main.List;
import java.util.ArrayList;
import java.util.Iterator;
import main.DAO.DAO;
import main.DAO.Utillity;
import main.Data.InsuranceProduct;

public class InsuranceProductListImpl implements InsuranceProductList {

	private ArrayList<InsuranceProduct> insuranceProducts;
//	private int index = 1;

	public InsuranceProductListImpl(){
		insuranceProducts = new ArrayList<>();
	}

	/**
	 * 보험 상품 삭제
	 * @param productID
	 * @return delete 성공 시 true, 실패 시 false
	 */
	public boolean delete(String productID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM InsuranceProduct WHERE product_id = ?", productID);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean checkProduct(String productName){
		try (DAO dao = new DAO()){
			dao.executeQuery("SELECT * FROM InsuranceProduct WHERE product_name = ?", productName);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 새로운 보험 상품 추가
	 * @param insuranceProduct
	 * @return insert 성공 시 true, 실패 시 false
	 */
	public boolean insert(InsuranceProduct insuranceProduct){
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO InsuranceProduct (product_id,coverage_by_age,exemption_period,max_age,max_number_event,premium,product_name,reduction_period,reduction_ratio,sex,user_id) VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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

	/**
	 * 일치하는 productID 보험 상품 조회
	 * @param productID
	 * @return search 성공 시 해당 상품, 실패 시 null
	 */
	public InsuranceProduct search(String productID){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM insurance_product WHERE product_id = ?",productID).toInsuranceProduct().get(0);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 보험 상품 키워드 조회
	 * @param key
	 * @param value
	 * @return
	 */
	public ArrayList<InsuranceProduct> searchProducts(String key, String value) {
		try (DAO dao = new DAO()){
			return(ArrayList<InsuranceProduct>) dao.executeQuery("SELECT * FROM insurance_product WHERE ? = ?", key, value).toInsuranceProduct();
		}catch(Exception e){
			return null;
		}
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

//	/**
//	 * 보험 상품의 productID를 자동으로 생성
//	 * @return 생성된 productID
//	 */
//	public String generateProductID(){
//		return "P"+String.format("%08d",index++);
//	}

	/**
	 * 보험 상품 리스트의 사이즈 값
	 * @return 보험 상품 개수
	 */
	public int size(){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM InsuranceProduct").toInsuranceProduct().size();
		}catch(Exception e){
			return -1;
		}
	}

	/**
	 * 해당 index값의 보험 상품 반환
	 * @param index
	 * @return 해당 index값의 보험 상품 반환
	 */
	public InsuranceProduct getProduct(int index){
		InsuranceProduct product = null;
		try (DAO dao = new DAO()){
			product = dao.executeQuery("SELECT * FROM InsuranceProduct").toInsuranceProduct().get(index);
		}catch(Exception e){
			return null;
		}
		return product;
	}

	@Override
	public ArrayList<InsuranceProduct> getAllProducts() {
		try (DAO dao = new DAO()){
			return (ArrayList<InsuranceProduct>) dao.executeQuery("SELECT * FROM InsuranceProduct").toInsuranceProduct();
		}catch(Exception e){
			return null;
		}

	}

	public void printAllProducts(){
		ArrayList<InsuranceProduct> products= this.getAllProducts();
		if(products.isEmpty()){
			System.out.println("등록된 보험 상품이 없습니다.");
			return;
		}
		for(InsuranceProduct product: products)
			System.out.println(product.toString());
	}
}