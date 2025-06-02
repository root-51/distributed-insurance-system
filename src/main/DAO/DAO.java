package main.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO implements AutoCloseable {
  private Config config;
  private Connection connection;
  private String databaseURL;
  private String username;
  private String password;
  private Connection con;

  public DAO() throws SQLException {
    setDatabseLoginInfo();
    try{
      if (con == null || con.isClosed()) {
        con = DriverManager.getConnection(databaseURL, username, password);
      }
    }catch(SQLException e){
      System.err.println("데이터베이스 연결 오류: " + e.getMessage());
      throw e;
    }
  }

  private void setDatabseLoginInfo() {
    config = new Config();
    databaseURL = "jdbc:mysql://"+config.getDatabaseURL()+"/" + config.getDatabaseName();
    username = config.getDatabaseUserId();
    password = config.getDatabasePassword();
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
  public ResultSetWrapper executeQuery(String sql, Object... params) throws SQLException {
    // 쿼리 실행 전에 현재 Connection이 유효한지 다시 한번 확인합니다.
    if (this.con == null || this.con.isClosed()) {
      System.err.println("DB 연결이 유효하지 않습니다. DAO 객체를 다시 생성해야 합니다.");
      throw new SQLException("데이터베이스 연결이 닫혀있거나 유효하지 않습니다.");
    }

    String trimmedSql = sql.trim().toUpperCase();
    PreparedStatement preparedStatement = null; // PreparedStatement 선언
    ResultSet rs = null; // ResultSet 선언

    try {
      // 현재 DAO 인스턴스의 Connection 객체를 사용합니다.
      preparedStatement = this.con.prepareStatement(sql);

      // 파라미터 설정
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }

      if (trimmedSql.startsWith("SELECT")) {
        rs = preparedStatement.executeQuery();
        // ResultSetWrapper로 래핑하고 메모리에 로드한 후, rs와 preparedStatement를 닫습니다.
        return resultSetWrapping(rs, preparedStatement);
      } else {
        preparedStatement.executeUpdate();
        return null; // INSERT/UPDATE/DELETE 쿼리는 ResultSet이 없으므로 null 반환
      }
    } catch (SQLException e) {
      System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
      throw e; // 예외를 다시 던져 호출자에게 알림
    } finally {
      // PreparedStatement와 ResultSet은 쿼리 실행 후 즉시 닫습니다.
      // Connection은 이 DAO 인스턴스의 close() 메서드에서 닫힙니다.
      closeResources(rs, preparedStatement);
    }
  }


  /**
   * resultSet을 List<Map<String, Object>>형태로 메모리에 로드하고, ResultSetClass에 래핑해서 반환
   * @param resultSet
   * @return
   * @throws SQLException
   */
  private ResultSetWrapper resultSetWrapping(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
    List<Map<String, Object>> resultList = new ArrayList<>();
    if (resultSet == null) {
      return new ResultSetWrapper(resultList); // null ResultSet 대신 빈 리스트를 가진 Wrapper 반환
    }

    try {
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();

      while (resultSet.next()) {
        Map<String, Object> row = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
          String columnName = metaData.getColumnLabel(i); // 컬럼 별칭 고려
          Object columnValue = resultSet.getObject(i); // JDBC 타입에 따라 적절히 변환
          row.put(columnName, columnValue);
        }
        resultList.add(row);
      }
    } finally {
      // **주의:** ResultSet은 여기서 닫지 않습니다.
      // executeQuery의 finally 블록 (closeResources 메서드)에서 닫히도록 책임 분리합니다.
      // 이렇게 하면 executeQuery 메서드의 try-catch-finally 구조가 더 명확해집니다.
    }
    return new ResultSetWrapper(resultList);
  }


  private void closeResources(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        System.err.println("ResultSet 닫기 오류: " + e.getMessage());
      }
    }
    if (pstmt != null) {
      try {
        pstmt.close();
      } catch (SQLException e) {
        System.err.println("PreparedStatement 닫기 오류: " + e.getMessage());
      }
    }
  }

  @Override
  public void close() {
    if (this.con != null) {
      try {
        this.con.close();
      } catch (SQLException e) {
        System.err.println("DB 연결 해제 중 오류 발생: " + e.getMessage());
      } finally {
        this.con = null; // 연결이 닫혔음을 명시하여 혹시 모를 재사용 방지
      }
    }
  }
}