package main.Employee;

public class User {

	private String userID;
	private String userPW;
	private UserType userType;

	public enum UserType {
		Customer(1), Sales(2), UnderWriter(3), ProductManagement(4), LossAdjuster(5);
		private int value;
		UserType(int value){
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
		public static UserType fromValue(int value){
			return switch (value) {
				case 1 -> Customer;
				case 2 -> Sales;
				case 3 -> UnderWriter;
				case 4 -> ProductManagement;
				case 5 -> LossAdjuster;
				default -> null;
			};
		}
	}

	public User(String userID, String userPW, UserType userType){
		this.userID = userID;
		this.userPW = userPW;
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