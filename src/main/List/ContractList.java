package main.List;
import java.util.ArrayList;
import java.util.List;
import main.Data.Contract;
import main.Enum.ProcessState;

public interface ContractList {

	public boolean delete(String contractID);

	public boolean insert(Contract contract);

	public Contract search(String contractID);

	public boolean update(Contract contract);

	public List<Contract> searchByKeyValue(String key, String value);

	public List<Contract> searchByStateKeyValue(ProcessState state, String key, String value);

	public List<Contract> getAll();

}