package main.List;

import java.util.ArrayList;
import main.Data.CustomerDTO;

public interface CustomerList {

	/**
	 * 
	 * @param customerID
	 */
	public boolean delete(String customerID);

	/**
	 * 
	 * @param customer
	 */
	public boolean insert(CustomerDTO customer);

	/**
	 * 
	 * @param customerID
	 */
	public CustomerDTO search(String customerID);

	/**
	 * 
	 * @param customer
	 */
	public boolean update(CustomerDTO customer);
	
	public ArrayList<CustomerDTO> getAll();

}