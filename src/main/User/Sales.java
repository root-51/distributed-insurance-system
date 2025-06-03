package main.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import main.DAO.DAO;
import main.DAO.Utillity;
import main.Data.Contract;
import main.Data.Customer;
import main.Data.InsuranceProduct;
import main.Enum.ProcessState;
import main.Enum.Sex;
import main.List.*;

public class Sales extends User {

	public CustomerList customerList;
	public ContractList contractList;
	public InsuranceProductList insuranceProductList;

	public Sales(String id, String userPW, UserType employeeType) {
		super(id,userPW, employeeType);
		this.customerList = new CustomerListImpl();
		this.contractList = new ContractListImpl();
		this.insuranceProductList = new InsuranceProductListImpl();
	}

	public boolean createCustomer(String accountNumber, String address, int age, String customerID, String pw, String job,
			String name, String phoneNumber, String rrn, Sex sex) {
		if(customerList.search(customerID)!=null)
			return false;
		Customer customer = new Customer.Builder().accountNumber(accountNumber).address(address).age(age)
				.customerID(customerID).pw(pw).job(job).name(name).rrn(rrn).sex(sex).phoneNumber(phoneNumber).build();
		System.out.println(customer);
		return customerList.insert(customer);
	}

	public Customer searchCustomer(String customerId) {
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



	public boolean createContract(Date contract_date, LocalDate expiration_date, String productID,String customerID) {
		InsuranceProduct product = searchProduct(productID);
		Customer customer = searchCustomer(customerID);
		if(product!=null && customer !=null){
			Contract contract = new Contract.Builder().contractID(Utillity.generateID('C')).contractDate(contract_date).expirationDate(expiration_date).productID(productID).salesID(getUserID()).state(ProcessState.fromString("awaiting")).customerID(customerID).insuranceProduct(product).build();
			return contractList.insert(contract);
		}else return false;
	}

	public Contract getContract(String selectedContractID) { return contractList.search(selectedContractID);}

	public boolean updateContract(String contract_date, String customerID, String expirationDate, String productID, int state) {
		SimpleDateFormat localFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Contract contract = null;
		try {
			contract = new Contract.Builder().contractDate(localFormatter.parse(contract_date)).customerID(customerID).expirationDate(LocalDate.parse(expirationDate,dateFormatter)).productID(productID).state(ProcessState.fromInteger(state)).build();
			return contractList.update(contract);
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean deleteContract(String contractID) { return contractList.delete(contractID);}

	private InsuranceProduct searchProduct(String productID) {
		return insuranceProductList.searchProductsByKey("product_id",productID).get(0);
	}

	public ArrayList<Contract> getAllContract() {
		return contractList.getAll();
	}

	public ArrayList<Contract> searchContractsByKey(String key, String value) {
		return contractList.searchByKeyValue(key,value);
	}
}