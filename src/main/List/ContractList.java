package main.List;
import java.util.ArrayList;
import java.util.List;
import main.Data.Contract;

public interface ContractList {

	public boolean delete(String contractID);

	public boolean insert(Contract contract);

	public Contract search(String contractID);

	public boolean update(Contract contract);

	public ArrayList<Contract> searchByKeyValue(String key, String value);

	public ArrayList<Contract> getAll();

}