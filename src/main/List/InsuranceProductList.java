package main.List;

import main.Data.InsuranceProduct;

import java.util.ArrayList;

public interface InsuranceProductList {

	public boolean delete(String productID);

	public boolean insert(InsuranceProduct insuranceProduct);

	public ArrayList<InsuranceProduct> searchProductsByKey(String key,String value);

	public boolean update(InsuranceProduct insuranceProduct);

	public ArrayList<InsuranceProduct> getAll();
}