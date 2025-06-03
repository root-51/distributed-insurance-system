package main.List;
import java.util.ArrayList;
import java.util.List;
import main.Data.Contract;

public interface ContractList {

	/**
	 * 
	 * @param contractID
	 */
	public boolean delete(String contractID);

	/**
	 * 
	 * @param contract
	 */
	public boolean insert(Contract contract);

	/**
	 * 
	 * @param contractID
	 */
	public Contract search(String contractID);

	/**
	 * 
	 * @param contract
	 */
	public boolean update(Contract contract);

	public List<Contract> searchByKeyValue(String key, String value);

	public List<Contract> getAll();
	public List<Contract> getByCustomerID(String customerID);

}