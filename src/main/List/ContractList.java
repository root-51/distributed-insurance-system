package main.List;
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

}