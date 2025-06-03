package main.List;

import main.Data.InsuranceProduct;

import java.util.ArrayList;

public interface InsuranceProductList {

	public boolean delete(String productID);

	public boolean insert(InsuranceProduct insuranceProduct);

	public InsuranceProduct search(String productID);

	public ArrayList<InsuranceProduct> searchProducts(String key,String value);

	public boolean update(InsuranceProduct insuranceProduct);

//	public String generateProductID();

	public boolean checkProduct(String productName);

	public int size();

	public InsuranceProduct getProduct(int index);
	public ArrayList<InsuranceProduct> getAllProducts();
	public ArrayList<InsuranceProduct> getProductsByCustomerID(String customerID);
	public void printAllProducts();

}