package main.User;

import java.util.*;
import main.Data.CustomerDTO;
import main.Enum.Sex;
import main.List.CustomerList;
import main.List.CustomerListImpl;

public class Sales extends User {

	public CustomerList customerList;

	public Sales(String id, int employeeType) {
		super(id, employeeType);
		this.customerList = new CustomerListImpl();
	}

	public boolean createCustomer(String accountNumber, String address, int age, String customerID, String pw, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		if(customerList.search(customerID)!=null)
			return false;
		CustomerDTO customer = new CustomerDTO.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).pw(pw).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		System.out.println(customer);
		return customerList.insert(customer);
	}

	public CustomerDTO getCustomer(String customerId) {
		return customerList.search(customerId);
	}

	public ArrayList<CustomerDTO> getAllCustomer() {
		return customerList.getAll();
	}

	public boolean updateCustomer(String accountNumber, String address, int age, String customerID, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		CustomerDTO customer = new CustomerDTO.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		return customerList.update(customer);
	}
	public boolean deleteCustomer(String customerId) {
		return customerList.delete(customerId);
	}



}