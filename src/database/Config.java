package database;

public class Config {
	private final String databaseName;
	private final String databaseUserId;
	private final String databasePassword;
	private final String databaseURL;
	private FileManager fileManager;
	private String[] lines;

	public Config() {
		fileManager = new FileManager();
		lines = fileManager.readAllLines();

		databaseName = lines[0];
		databaseURL = lines[1];
		databaseUserId = lines[2];
		databasePassword = lines[3];
	}

	public String getDatabaseName() {
		return databaseName;
	}
	public String getDatabaseURL() {
		return databaseURL;
	}

	public String getDatabaseUserId() {
		return databaseUserId;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

}
