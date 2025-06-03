package main.User;

import main.DAO.DAO;
import main.Data.Contract;


public class UnderWriter extends User {

  public UnderWriter(String id, int userType){
    super(id, userType);
  }


  /**
   * @param approve
   * @param ContractID
   */
  public boolean underwrite(String ContractID , boolean approve){
    try (DAO dao = new DAO()){
      dao.executeQuery("UPDATE contract SET state = ? WHERE contract_id = ?",
          approve? 1 : -1, ContractID);
      return true;
    }catch (Exception e) {
      return false;
    }
  }
}//end UnderWriter