package main.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.Data.Contract;
import main.Enum.ProcessState;

public class DAO {
  private static final String URL = "DB주소";
  private static final String USER = "사용자이름";
  private static final String PASSWORD = "비번";
  private static Connection con;

  public DAO() throws SQLException {
    try{
      if (con == null || con.isClosed()) {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
      }
    }catch(SQLException e){
      System.err.println("데이터베이스 연결 오류: " + e.getMessage());
      throw e;
    }
  }
  /**
   * SQL 쿼리를 실행하고 결과를 반환합니다.
   * SELECT 쿼리: 쿼리 결과인 ResultSet을 ResultSetClass객체에 담아 반환
   * ResultSet은 각 mapper를 통해, DataClass로 변환
   * INSERT/UPDATE/DELETE 쿼리: 결과 집합이 없으므로 null을 반환
   * @param sql 쿼리 문자열 (예: "SELECT id, name FROM users", "INSERT INTO users (name) VALUES (?)")
   * @param params PreparedStatement에 설정할 파라미터들 (순서대로, 없을 경우 생략)
   * @return SELECT 쿼리인 경우 ResultSet 객체, 그 외의 경우 null
   * @throws SQLException SQL 실행 중 오류 발생 시
   */
  public static ResultSetWrapper executeQuery(String sql, Object... params) throws SQLException {
    // SQL 쿼리 타입을 대략적으로 판단 (SELECT, INSERT, UPDATE, DELETE)
    String trimmedSql = sql.trim().toUpperCase();
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = con.prepareStatement(sql);
      // 파라미터 설정
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }
      if (trimmedSql.startsWith("SELECT")) {
        return resultSetWrapping(preparedStatement.executeQuery());
      } else {
        preparedStatement.executeUpdate();
        return null;
      }
    } catch (SQLException e) {
      // 예외 발생 시 PreparedStatement 닫기 시도
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException innerE) {
          System.err.println("에러발생" + innerE.getMessage());
        }
      }
      throw e;
    }
  }


  /**
   * resultSet을 List<Map<String, Object>>형태로 메모리에 로드하고, ResultSetClass에 래핑해서 반환
   * @param resultSet
   * @return
   * @throws SQLException
   */
  private static ResultSetWrapper resultSetWrapping(ResultSet resultSet) throws SQLException {
    List<Map<String, Object>> resultList = new ArrayList<>();
    if (resultSet == null) {
      return null; // null ResultSet에 대한 처리
    }
    try {
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();

      while (resultSet.next()) {
        Map<String, Object> row = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
          // getColumnLabel()을 사용하여 컬럼 별칭(alias)이 있을 경우에도 올바른 이름을 사용합니다.
          String columnName = metaData.getColumnLabel(i);
          Object columnValue = resultSet.getObject(i); // JDBC 타입에 따라 적절히 변환하여 반환
          row.put(columnName, columnValue);
        }
        resultList.add(row);
      }
    } finally {
      // ResultSet을 반드시 닫습니다.
      try {
        if (resultSet != null && !resultSet.isClosed()) {
          resultSet.close();
        }
      } catch (SQLException e) {
        System.err.println("에러발생 " + e.getMessage());
        // 오류 로깅 후 예외를 다시 던지거나 무시할 수 있습니다. 여기서는 로깅만 합니다.
      }
    }
    return new ResultSetWrapper(resultList);
  }

//  public static void main(String[] args) throws SQLException {
//    DAO dao = new DAO();
//    for(Contract c: dao.executeQuery(eSQL.findAllContract.sql()).toContracts()){
//      System.out.println(c);
//    }
//
//    LocalDate today = LocalDate.now();
//    java.sql.Date sqlDateToday = java.sql.Date.valueOf(today);
//
//    LocalDate specificDate = LocalDate.of(2024, 5, 24);
//    java.sql.Date sqlSpecificDate = java.sql.Date.valueOf(specificDate);
//
//    //(contract_id, contract_date, expiration_date, product_id, sales_id, state, customer_id)
//    dao.executeQuery(eSQL.insertContract.sql(),
//        "test",
//        sqlDateToday,
//        sqlSpecificDate,
//        "test",
//        "test",
//        ProcessState.Awaiting.getValue(),
//        "test");
//
//    for(Contract c: dao.executeQuery("select * from contract").toContracts()){
//      System.out.println(c);
//    }
//  }



}