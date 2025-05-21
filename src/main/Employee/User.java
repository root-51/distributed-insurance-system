package main.Employee;

public class User {

	private String userID;
	private String userPW;
	private UserType userType;

	public enum UserType {
		Customer, Sales, UnderWriter, ProductManagement, LossAdjuster
	}

	public User(int numOfUsers, UserType userType) {
		this.userID = Integer.toString(numOfUsers+1);
		this.userType = userType;
	}

	public String getUserID() {
		return userID;
	}
	public String getUserPW() {
		return userPW;
	}

	public UserType getUserType() {
		return userType;
	}

}