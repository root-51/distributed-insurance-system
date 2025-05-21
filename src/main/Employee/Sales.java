package main.Employee;

import java.util.*;
import main.Data.Customer;
import main.Enum.Sex;
import main.List.CustomerList;

public class Sales extends User {

	public CustomerList customerList;

	public Sales(int numOfEmployees, UserType employeeType, CustomerList customerList) {
		super(numOfEmployees, employeeType);
		this.customerList = customerList;
	}

	/**
	 * 
	 * @param accountNumber;
	 * @param address;
	 * @param age;
	 * @param customerID;
	 * @param job;
	 * @param name;
	 * @param phoneNumber;
	 * @param rrn;
	 * @param sex;
	 */
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

	/**
	 *
	 * @param Customer
	 */
	public boolean updateCustomer(String accountNumber, String address, int age, String customerID, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		Customer customer = new Customer.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		return customerList.update(customer);
	}
	/**
	 *
	 * @param ID
	 * @param 품ID
	 */
//	public boolean createContract(String ID, String 품ID, String customerID){
//		// Contract.class�� ������ �댁�⑹�� ���쇰�명�곕� 諛�怨�, 鍮������댁�� �듯�� ���깊��, ContractList�� 異�媛���怨�, �깃났��硫� True, �ㅽ�⑦��硫� flase瑜� 由ы�댄���� 硫�����
//		Contract contract = new Contract.Builder(ID, 품ID, customerID).build();
//		return ContractList.add(contract);
//	}

	/**
	 *
	 * @param ID
	 */
//	public boolean deleteContract(String ID){
//		return ContractList.delete(ID);
//	}

	/**
	 *
	 * @param CustomerID
	 */
	public boolean deleteCustomer(String CustomerID) {
		return customerList.delete(CustomerID);
	}

	/**
	 *
	 * @param Contract
	 */
//	public boolean updateContract(Contract Contract){
//		return ContractList.update(Contract);
//	}

//
//	}

	/**
	 * 
	 * @param 占쏙옙占�ID
	 */
//	public boolean deleteContract(String 占쏙옙占�ID){
//		return false;
//	}

	/**
	 * 
	 * @param CustomerID
	 */
//	public boolean deleteCustomer(String CustomerID){
//		return false;
//	}

	/**
	 * 
	 * @param Contract
	 */
//	public boolean updateContract(Contract Contract){
//		return false;
//	}

	/**
	 * 
	 * @param Customer
	 */
//	public boolean updateCustomer(Customer Customer){
//		return false;
//	}

//	public Contract m_Contract;
//	public boolean createContract(String 占쏙옙占쏙옙占�ID, String 占쏙옙품ID){
//		return false;
//	}
}