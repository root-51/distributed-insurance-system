package main.List;


import java.util.ArrayList;
import java.util.Iterator;
import main.DAO.DAO;
import main.Data.Customer;

public class CustomerListImpl implements CustomerList {

	public ArrayList<Customer> customers;

	public CustomerListImpl(){
		this.customers = new ArrayList<Customer>();
	}

	public boolean delete(String customerID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM customer WHERE customer_id = ?", customerID);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean insert(Customer customer){
		try (DAO dao = new DAO()){
			dao.executeQuery(
					"INSERT INTO customer(user_id,account_number,address,age,job,name,phone_num,rrn,sex) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
					customer.getCustomerID(),
					customer.getAccountNumber(),
					customer.getAddress(),
					customer.getAge(),
					customer.getJob(),
					customer.getName(),
					customer.getPhoneNumber(),
					customer.getRrn(),
					customer.getSex().getValue());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public Customer search(String customerID){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM customer WHERE customer_id = ?", customerID).toCustomer().get(0);
		}catch (RuntimeException e){
			return null;
		}
		catch(Exception e){
			//다른 엑션 추가해야할까봐 넣어둠
			return null;
		}
	}

	public boolean update(Customer updatedCustomer){
		try (DAO dao = new DAO()){
			dao.executeQuery("UPDATE customer SET user_id = ?, account_number = ?, address = ?, age,job = ?, name = ?, phone_num = ?, rrn = ?, sex = ? WHERE customer_id = ?",
					updatedCustomer.getAccountNumber(),
					updatedCustomer.getAddress(),
					updatedCustomer.getAge(),
					updatedCustomer.getJob(),
					updatedCustomer.getName(),
					updatedCustomer.getPhoneNumber(),
					updatedCustomer.getRrn(),
					updatedCustomer.getSex().getValue(),
					updatedCustomer.getCustomerID());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public ArrayList<Customer> getAll() {
		try (DAO dao = new DAO()){
			return (ArrayList<Customer>) dao.executeQuery("SELECT * FROM customer").toCustomer();
		}catch(Exception e){
			return null;
		}
	}

}