package main.List;


import java.util.ArrayList;
import java.util.Iterator;
import main.Employee.Customer;

public class CustomerListImpl implements CustomerList {

	public ArrayList<Customer> customers;

	public CustomerListImpl(){
		this.customers = new ArrayList<Customer>();
	}

	public boolean delete(String customerID){
		Iterator<Customer> iterator = customers.iterator();
		while (iterator.hasNext()) {
			Customer customer = iterator.next();
			if (customer.getCustomerID().equals(customerID)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	public boolean insert(Customer customer){
		return this.customers.add(customer);
	}

	public Customer search(String customerID){
		for (Customer customer : customers) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}

	public boolean update(Customer updatedCustomer){
		for (int i = 0; i < customers.size(); i++) {
			Customer existingCustomer = customers.get(i);
			if (existingCustomer.getCustomerID().equals(updatedCustomer.getCustomerID())) {
				customers.set(i, updatedCustomer);
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Customer> getAll() {
		return this.customers;
	}

}