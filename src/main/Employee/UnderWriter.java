package main.Employee;

import main.DAO.DAO;
import main.Data.Contract;
import main.List.ContractList;
import main.List.CustomerList;

public class UnderWriter extends Employee {

  public UnderWriter(int numOfEmployees, EmployeeType employeeType){
    super(numOfEmployees, employeeType);
  }

  /**
   * @param approve
   * @param ContractID
   */
  public boolean underwrite(String ContractID , boolean approve){
    try {
      DAO.executeQuery("UPDATE contract SET state = ? WHERE contract_id = ?",
          approve? 1 : -1, ContractID);
      return true;
    }catch (Exception e) {
      return false;
    }
  }
}//end UnderWriter