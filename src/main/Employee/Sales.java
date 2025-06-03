package main.Employee;

import java.util.*;
import main.Data.Customer;
import main.Enum.Sex;
import main.List.CustomerList;

public class Sales extends User {

	public CustomerList customerList;


	public Sales(String id, String userPW, UserType employeeType) {
		super(id,userPW, employeeType);
	}

	public boolean createCustomer(String accountNumber, String address, int age, String customerID, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		Customer customer = new Customer.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		System.out.println(customer);
		return customerList.insert(customer);
	}

	public Customer getCustomer(String customerId) {
		return customerList.search(customerId);
	}

	public ArrayList<Customer> getAllCustomer() {
		return customerList.getAll();
	}

	public boolean updateCustomer(String accountNumber, String address, int age, String customerID, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		Customer customer = new Customer.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		return customerList.update(customer);
	}
	public boolean deleteCustomer(String CustomerID) {
		return customerList.delete(CustomerID);
	}



}