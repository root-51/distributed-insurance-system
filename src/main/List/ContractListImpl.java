package main.List;
import java.sql.Date;
import main.DAO.DAO;
import main.Data.Contract;

public class ContractListImpl implements ContractList {
	/**
	 * 
	 * @param contractID
	 */
	public boolean delete(String contractID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM contract WHERE ContractID = ?", contractID);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 
	 * @param contract
	 */
	public boolean insert(Contract contract){
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO contract (contract_id,contract_date,expiration_date,product_id,sales_id,state,customer_id) VALUE(?,?,?,?,?,?,?)",
					contract.getContractID(),
					contract.getContractDate(),
					contract.getExpirationDate(),
					contract.getProductID(),
					contract.getSalesID(),
					contract.getState().getValue(),
					contract.getCustomerID());
			return true;
		}catch(Exception e){
			return false;
		}
  }

	/**
	 * 
	 * @param contractID
	 */
	public Contract search(String contractID){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM contract WHERE ContractID = ?", contractID).toContracts().get(0);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 
	 * @param contract
	 */
	public boolean update(Contract contract){
		try (DAO dao = new DAO()){
			dao.executeQuery("UPDATE contract SET contract_date = ?, expiration_date = ?, product_id = ?, sales_id = ?, state = ?, customer_id = ? WHERE contract_id = ?",
					contract.getContractDate(),
					contract.getExpirationDate(),
					contract.getProductID(),
					contract.getSalesID(),
					contract.getState().getValue(),
					contract.getCustomerID(),
					contract.getContractID());
			return true;
		}catch(Exception e){ return false; }
	}

}