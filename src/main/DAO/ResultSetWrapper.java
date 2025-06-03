package main.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import main.Data.Compensation;
import main.Data.Contract;
import main.Data.Customer;
import main.Data.Evaluation;
import main.Data.Event;
import main.Data.InsuranceProduct;
import main.Employee.LossAdjuster;
import main.Employee.ProductManagement;
import main.Employee.Sales;
import main.Employee.UnderWriter;
import main.Employee.User;
import main.Employee.User.UserType;
import main.Enum.ProcessState;
import main.Enum.Sex;

public class ResultSetWrapper {
  private final List<Map<String, Object>> resultSetList;


  public ResultSetWrapper(List<Map<String, Object>> resultSetList){
    this.resultSetList = resultSetList;
  }

  public List<Map<String, Object>> get() {
    return resultSetList;
  }


  public User toUser(){
    Map<String,Object> userData = resultSetList.get(0);
    String userID = (String) userData.get("user_id");
    String userPW = (String)userData.get("user_pw");
    int userTypeID = (Integer) userData.get("user_type_id");
    if (userData != null) {
      switch (userTypeID){
        case 1:return new CustomerService(userID, userPW,UserType.fromValue(userTypeID));
        case 2:return new Sales(userID,userPW, UserType.fromValue(userTypeID));
        case 3:return new UnderWriter(userID, userPW,UserType.fromValue(userTypeID));
        case 4:return new ProductManagement(userID, userPW,UserType.fromValue(userTypeID));
        case 5:return new LossAdjuster(userID, userPW, UserType.fromValue(userTypeID));
        default:
      }
    }
    return null;
  }

  public List<Customer> toCustomer(){
    ArrayList<Customer> customers = new ArrayList<>();
    for (Map<String, Object> row : resultSetList) {
      try {
        customers.add(
            new Customer.Builder()
                .customerID((String) row.get("user_id"))
                .accountNumber((String) row.get("account_number"))
                .address((String) row.get("address"))
                .age((Integer) row.get("age"))
                .job((String) row.get("job"))
                .name((String) row.get("name"))
                .phoneNumber((String) row.get("phone_num"))
                .rrn((String) row.get("rrn"))
                .sex(Sex.fromInt((Integer)row.get("sex")))
                .build()
        );
      } catch (NullPointerException | ClassCastException e) {
        throw new RuntimeException("Failed to cast data for Contract object: " + e.getMessage(), e);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Invalid state value for ProcessState: " + row.get("state"), e);
      }
    }
    return customers;
  }

/**
 * Map 형태의 한 행 데이터를 InsuranceProduct 객체로 변환, 리스트에 담아 리턴
 * 이 메소드는 Map에서 값을 가져와 InsuranceProduct.Builder를 사용하여 객체를 생성합니다.
 * @return 매핑된 InsuranceProduct 객체
 * @throws ClassCastException 만약 Map에서 가져온 값의 타입이 InsuranceProduct 필드 타입과 맞지 않을 경우
 * @throws NullPointerException Map에 필수 컬럼이 없거나 값이 null인 경우
 */
  public List<InsuranceProduct> toInsuranceProduct(){
    ArrayList<InsuranceProduct> contracts = new ArrayList<>();
    Utillity parser = new Utillity();
    for (Map<String, Object> row : resultSetList) {
      try {
        contracts.add(
            new InsuranceProduct.Builder()
                .productID((String) row.get("product_id"))
                .coverageByAge(parser.json2Map((String)row.get("coverage_by_age")))
                .exemptionPeriod((Integer) row.get("exemption_period"))
                .maxAge((Integer)row.get("max_age"))
                .maxNumberEvent((Integer)row.get("max_number_event"))
                .premium((Integer)row.get("premium"))
                .productName((String)row.get("product_name"))
                .reductionPeriod((Integer)row.get("reduction_period"))
                .reductionRatio((Integer)row.get("reduction_ratio"))
                .productManagementID((String)row.get("user_id"))
                .sex(Sex.fromInt((Integer)row.get("sex")))
                .build()

        );
      } catch (NullPointerException | ClassCastException e) {
        throw new RuntimeException("Failed to cast data for Contract object: " + e.getMessage(), e);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Invalid state value for ProcessState: " + row.get("state"), e);
      }
    }
    return contracts;
  }

/**
 * Map 형태의 한 행 데이터를 Contract 객체로 변환, 리스트에 담아 리턴
 * 이 메소드는 Map에서 값을 가져와 Contract.Builder를 사용하여 객체를 생성합니다.
 * @return 매핑된 Contract 객체
 * @throws ClassCastException 만약 Map에서 가져온 값의 타입이 Contract 필드 타입과 맞지 않을 경우
 * @throws NullPointerException Map에 필수 컬럼이 없거나 값이 null인 경우
 * @throws IllegalArgumentException Enum 변환 중 유효하지 않은 문자열이 있을 경우
 */
  public List<Contract> toContracts() {
  ArrayList<Contract> contracts = new ArrayList<>();
  for (Map<String, Object> row : resultSetList) {
    try {
      contracts.add(
          new Contract.Builder()
              .contractID((String) row.get("contract_id"))
              .contractDate(new Date(((java.sql.Date) row.get("contract_date")).getTime()))
              .customerID((String) row.get("customer_id"))
              .expirationDate(((java.sql.Date) row.get("expiration_date")).toLocalDate())
              .productID((String) row.get("product_id"))
              .salesID((String) row.get("sales_id"))
              .state(ProcessState.valueOf((String) row.get("state")))
              .build()
      );
    } catch (NullPointerException | ClassCastException e) {
      throw new RuntimeException("Failed to cast data for Contract object: " + e.getMessage(), e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Invalid state value for ProcessState: " + row.get("state"), e);
    }
  }
  return contracts;
}

/**
 * 내부의 List<Map<String, Object>>를 Event 객체 리스트로 변환합니다.
 * 각 행(row) 데이터를 Event 객체로 매핑합니다.
 *   * @return 매핑된 Event 객체들의 리스트. 변환할 데이터가 없으면 빈 리스트를 반환합니다.
 * @throws RuntimeException 데이터 매핑 중 (예: 타입 불일치, 컬럼 누락) 오류 발생 시
 */
  public List<Event> toEvents(){ // 접근자를 public으로 변경했습니다 (일반적으로 외부에서 호출하므로)
  ArrayList<Event> events = new ArrayList<>();
  for (Map<String, Object> row : resultSetList) {
    try {
      // Event 빌더 패턴 사용. 생성자에 필수 파라미터가 있다고 가정합니다.
      events.add(
          new Event.Builder((String) row.get("event_id"), (String) row.get("user_id"))
              .claimValue((Integer) row.get("claim_value"))
              .documents((String) row.get("documents"))
              .eventDate(row.get("event_date") != null ? ((java.sql.Date) row.get("event_date")).toLocalDate() : null)
              .eventDescription((String) row.get("event_description"))
              .eventLocation((String) row.get("event_location"))
              .receiptDate(row.get("event_receipt_date") != null ? ((java.sql.Date) row.get("event_date")).toLocalDate() : null)
              .evaluation(map2Evaluation(row)) // 중첩된 객체 매핑
              .build()
      );
    } catch (NullPointerException | ClassCastException e) {
      System.err.println("Event 매핑 중 타입/널 오류: " + e.getMessage() + " - 데이터: " + row);
      throw new RuntimeException("Failed to cast data for Event object: " + e.getMessage(), e);
    } catch (IllegalArgumentException e) { // Enum 변환 오류 등을 처리
      System.err.println("Event 매핑 중 Enum 변환 오류: " + e.getMessage() + " - 데이터: " + row);
      throw new IllegalArgumentException("Invalid enum value in Event mapping.", e);
    }
  }
  return events;
}

/**
 * Map 형태의 한 행 데이터를 Evaluation 객체로 변환합니다.
 * Event에서 중첩된 객체로 매핑될 때 사용됩니다.
 *   * @param row 한 행의 데이터를 담고 있는 Map
 * @return 매핑된 Evaluation 객체
 * @throws ClassCastException 만약 Map에서 가져온 값의 타입이 Evaluation 필드 타입과 맞지 않을 경우
 * @throws NullPointerException Map에 필수 컬럼이 없거나 값이 null인 경우
 * @throws IllegalArgumentException Enum 변환 중 유효하지 않은 문자열이 있을 경우
 */
  private Evaluation map2Evaluation(Map<String, Object> row){
  try {
    // Evaluation 빌더 패턴 사용. 생성자에 필수 파라미터가 있다고 가정합니다.
    // row.get()을 사용하여 Map에서 값을 가져옵니다.
    return new Evaluation.Builder(
        (String) row.get("event_id"), // event_id
        (String) (row.get("event_id")), // evaluation_id (가정)
        (String) row.get("user_id") // customer_id
    )
        .resultOfEvaluation(row.get("state_of_evaluation") != null ? ProcessState.fromString((String) row.get("state_of_evaluation")) : null) // String to Enum 변환
        .compensation(map2Compensation(row)) // 중첩된 객체 매핑
        .build();
  } catch (NullPointerException | ClassCastException e) {
    System.err.println("Evaluation 매핑 중 타입/널 오류: " + e.getMessage() + " - 데이터: " + row);
    throw new RuntimeException("Failed to cast data for Evaluation object: " + e.getMessage(), e);
  } catch (IllegalArgumentException e) {
    System.err.println("Evaluation 매핑 중 Enum 변환 오류: " + e.getMessage() + " - 데이터: " + row);
    throw new IllegalArgumentException("Invalid enum value in Evaluation mapping.", e);
  }
}

/**
 * Map 형태의 한 행 데이터를 Compensation 객체로 변환합니다.
 * Evaluation에서 중첩된 객체로 매핑될 때 사용됩니다.
 *   * @param row 한 행의 데이터를 담고 있는 Map
 * @return 매핑된 Compensation 객체
 * @throws ClassCastException 만약 Map에서 가져온 값의 타입이 Compensation 필드 타입과 맞지 않을 경우
 * @throws NullPointerException Map에 필수 컬럼이 없거나 값이 null인 경우
 * @throws IllegalArgumentException Enum 변환 중 유효하지 않은 문자열이 있을 경우
 */
  private Compensation map2Compensation(Map<String, Object> row){
  try {
    // Compensation 빌더 패턴 사용. 생성자에 필수 파라미터가 있다고 가정합니다.
    // row.get()을 사용하여 Map에서 값을 가져옵니다.
    return new Compensation.Builder(
        (String) row.get("event_id"), // event_id
        (String) row.get("compensation_id"), // compensation_id (가정)
        (String) row.get("customer_id") // customer_id
    )
        .claimsPaid((Integer) row.get("amount_of_paid"))
        .paidState(row.get("state_of_compensation") != null ? ProcessState.fromString((String) row.get("state_of_compensation")) : null) // String to Enum 변환
        .build();
  } catch (NullPointerException | ClassCastException e) {
    System.err.println("Compensation 매핑 중 타입/널 오류: " + e.getMessage() + " - 데이터: " + row);
    throw new RuntimeException("Failed to cast data for Compensation object: " + e.getMessage(), e);
  } catch (IllegalArgumentException e) {
    System.err.println("Compensation 매핑 중 Enum 변환 오류: " + e.getMessage() + " - 데이터: " + row);
    throw new IllegalArgumentException("Invalid enum value in Compensation mapping.", e);
  }
}



}
