package database;


import java.sql.*;

public class DatabaseManager {
	private Config config;
	private Connection connection;
	private String databaseURL;
	private String username;
	private String password;

	public DatabaseManager() {
		setDatabseLoginInfo();
		findDatabaseDriver();
		connectMySQL();
	}

	// init
	public void setDatabseLoginInfo() {
		config = new Config();
		databaseURL = "jdbc:mysql://"+config.getDatabaseURL()+"/" + config.getDatabaseName();
		username = config.getDatabaseUserId();
		password = config.getDatabasePassword();
	}

	public void findDatabaseDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("❌ JDBC 로드 실패");
			e.printStackTrace();
		}
	}

	public void connectMySQL() {
		try {
			connection = DriverManager.getConnection(databaseURL, username, password);
		} catch (SQLException e) {
			System.out.println("❌ 데이터베이스 접속 실패");
			e.printStackTrace();
		}
	}

	// create
	
	// retrieve
	public String[][] getAllRows(String tableName) {
		Statement statement;
		String query = "SELECT * FROM " + tableName;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("❌ 쿼리 실행 실패");
			e.printStackTrace();
		}
		return resultsetToStringList(result, tableName);

	}
	// update

	// delete

	// common function
	public String[][] resultsetToStringList(ResultSet result, String tableName) {

		int numOfColumn = getNumOfColumn(tableName);
		int numOfRow = getNumOfRow(tableName);

		String[][] stringList = new String[numOfRow][numOfColumn];
		try {
			for (int i = 0; i < numOfRow; i++) {
				result.next();
				for (int j = 0; j < numOfColumn; j++) {
					stringList[i][j] = result.getString(j + 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stringList;

	}

	public int getNumOfRow(String tableName) {
		int numOfRow = -1;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT COUNT(*) FROM " + tableName;
			ResultSet result = statement.executeQuery(query);
			result.next();
			numOfRow = result.getInt(1);
		} catch (SQLException e) {
			System.out.println("❌ 쿼리 실행 실패");
			e.printStackTrace();
		}
		return numOfRow;
	}

	public int getNumOfColumn(String tableName) {
		int numOfColumn = -1;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='"
					+ config.getDatabaseName() + "' AND TABLE_NAME = '" + tableName + "'";
			ResultSet result = statement.executeQuery(query);
			result.next();
			numOfColumn = result.getInt(1);

		} catch (SQLException e) {
			System.out.println("❌ 쿼리 실행 실패");
			e.printStackTrace();
		}
		return numOfColumn;
	}
}
