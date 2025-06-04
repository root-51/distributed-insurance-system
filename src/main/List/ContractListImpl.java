package main.List;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.DAO.DAO;
import main.Data.Contract;
import main.Enum.ProcessState;

public class ContractListImpl implements ContractList {

	public boolean delete(String contractID) {
		try (DAO dao = new DAO()) {
			dao.executeQuery("DELETE FROM contract WHERE contract_id = ?", contractID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean insert(Contract contract) {
		try (DAO dao = new DAO()) {
			dao.executeQuery(
					"INSERT INTO contract (contract_id,contract_date,expiration_date,product_id,sales_id,state,customer_id) VALUE(?,?,?,?,?,?,?)",
					contract.getContractID(),
					contract.getContractDate(),
					contract.getExpirationDate(),
					contract.getProductID(),
					contract.getSalesID(),
					contract.getState().getValue(),
					contract.getCustomerID());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Contract search(String contractID) {
		try (DAO dao = new DAO()) {
			return dao.executeQuery("SELECT * FROM contract WHERE contract_id = ?", contractID)
					.toContracts().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean update(Contract contract) {
		try (DAO dao = new DAO()) {
			dao.executeQuery(
					"UPDATE contract SET contract_date = ?, expiration_date = ?, product_id = ?, sales_id = ?, state = ?, customer_id = ? WHERE contract_id = ?",
					contract.getContractDate(),
					contract.getExpirationDate(),
					contract.getProductID(),
					contract.getSalesID(),
					contract.getState().getValue(),
					contract.getCustomerID(),
					contract.getContractID());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ArrayList<Contract> getAll() {
		try (DAO dao = new DAO()) {
			return (ArrayList<Contract>) dao.executeQuery("SELECT * FROM contract").toContracts();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Contract> searchByKeyValue(String key, String value) {
		try (DAO dao = new DAO()) {
			String sql = "SELECT * FROM contract WHERE " + key + " = ?" ;
			return (ArrayList<Contract>) dao.executeQuery(sql, value).toContracts();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Contract> searchByStateKeyValue(ProcessState state, String key, String value) {
		try (DAO dao = new DAO()) {
			return dao.executeQuery("SELECT * FROM contract WHERE state = ? AND "+ key + " = ?",
					state.getValue(), value).toContracts();
		} catch (Exception e) {
			return null;
		}
	}
}

