package main.List;


import java.util.ArrayList;

import main.DAO.DAO;
import main.Data.CustomerDTO;

public class CustomerListImpl implements CustomerList {

	public ArrayList<CustomerDTO> customers;

	public CustomerListImpl(){
		this.customers = new ArrayList<CustomerDTO>();
	}

	public boolean delete(String customerID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM event WHERE user_id = ?",customerID);
			dao.executeQuery("DELETE FROM customer WHERE user_id = ?", customerID);
			dao.executeQuery("DELETE FROM user WHERE user_id = ?",customerID);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean insert(CustomerDTO customer){
		System.out.println("test");
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO user(user_id,user_pw,user_type_id) VALUES(?,?,?)",
					customer.getCustomerID(), customer.getPw(), 1);
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

	public CustomerDTO search(String customerID){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM customer WHERE user_id = ?", customerID).toCustomer().get(0);
		}catch (RuntimeException e){
			return null;
		}
		catch(Exception e){
			//다른 엑션 추가해야할까봐 넣어둠
			return null;
		}
	}

	public boolean update(CustomerDTO updatedCustomer){
		try (DAO dao = new DAO()){
			dao.executeQuery("UPDATE customer SET account_number = ?, address = ?, age = ?,job = ?, name = ?, phone_num = ?, rrn = ?, sex = ? WHERE user_id = ?",
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
	public ArrayList<CustomerDTO> getAll() {
		try (DAO dao = new DAO()){
			return (ArrayList<CustomerDTO>) dao.executeQuery("SELECT * FROM customer").toCustomer();
		}catch(Exception e){
			return null;
		}
	}

}