package main.Employee;

import main.DAO.DAO;
import main.Data.Contract;
import main.List.ContractList;
import main.List.ContractListImpl;
import main.List.CustomerList;
import main.List.CustomerListImpl;
import main.List.InsuranceProductList;
import main.List.InsuranceProductListImpl;


public class UnderWriter extends User {

  private final ContractList contractList;
  private final CustomerList customerList;
  private final InsuranceProductList insuranceProductList;
  public UnderWriter(String id, String userPW, UserType userType){
    super(id,userPW, userType);
    this.contractList = new ContractListImpl();
    this.customerList = new CustomerListImpl();
    this.insuranceProductList = new InsuranceProductListImpl();

  }

  public ContractList getContractList() {
    return this.contractList;
  }
  public CustomerList getCustomerList(){
    return this.customerList;
  }
  public InsuranceProductList getInsuranceProductList(){
    return this.insuranceProductList;
  }

  /**
   * @param approve
   * @param ContractID
   */
  public boolean underwrite(String ContractID , boolean approve){
    return contractList.update(contractList.search(ContractID).receiptEvaluation(approve));
  }
}//end UnderWriter