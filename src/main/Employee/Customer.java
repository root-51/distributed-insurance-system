package main.Employee;

import java.util.Date;
import main.DAO.DAO;
import main.DAO.Utillity;
import main.Enum.ProcessState;
import main.Enum.Sex;

public class Customer {
	private final String accountNumber;
	private final String address;
	private final int age;
	private final String customerID;
	private final String job;
	private final String name;
	private final String phoneNumber;
	private final String rrn;
	private final Sex sex;

	private Customer(Builder builder) {
		this.accountNumber = builder.accountNumber;
		this.address = builder.address;
		this.age = builder.age;
		this.customerID = builder.customerID;
		this.job = builder.job;
		this.name = builder.name;
		this.phoneNumber = builder.phoneNumber;
		this.rrn = builder.rrn;
		this.sex = builder.sex;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAddress() {
		return address;
	}

	public int getAge() {
		return age;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getJob() {
		return job;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getRrn() {
		return rrn;
	}

	public Sex getSex() {
		return sex;
	}

	/**
	 *
	 * 성공하면 True, 실패하면 False
	 * @param claim_value
	 * @param documents
	 * @param event_date
	 * @param event_description
	 * @param event_location
	 * @param event_receipt_date
	 * @return
	 */
	public boolean acceptEvent(int claim_value, String documents, Date event_date, String event_description, String event_location, Date event_receipt_date){
		try {
			DAO.executeQuery("INSERT into event(event_id, claim_value, documents, event_date, event_description, event_location, event_receipt_date, state_of_evaluation, state_of_compensation, user_id) VALUES(?,?,?,?,?,?,?,?,?,?)",
					Utillity.generateID('E'),claim_value,documents,(java.sql.Date)event_date,event_description,event_location,(java.sql.Date)event_receipt_date,
					ProcessState.Awaiting.getValue(),ProcessState.Awaiting.getValue(),/*여기에 UserID들어가야됨*/);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public String toString() {
		return "Customer{" +
				"accountNumber='" + accountNumber + '\'' +
				", address='" + address + '\'' +
				", age=" + age +
				", customerID='" + customerID + '\'' +
				", job='" + job + '\'' +
				", name='" + name + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", rrn='" + rrn + '\'' +
				", sex=" + sex + 
				'}';
	}


	public static class Builder {
		private String accountNumber;
		private String address;
		private int age;
		private String customerID;
		private String job;
		private String name;
		private String phoneNumber;
		private String rrn;
		private Sex sex;

		public Builder accountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder age(int age) {
			this.age = age;
			return this;
		}
		
		public Builder customerID(String customerID) {
			this.customerID = customerID;
			return this;
		}

		public Builder job(String job) {
			this.job = job;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder rrn(String rrn) {
			this.rrn = rrn;
			return this;
		}

		public Builder sex(Sex sex) {
			this.sex = sex;
			return this;
		}

		public Customer build() {
			// if (name == null || customerID == null) {
			//     throw new IllegalStateException("Name and CustomerID cannot be null");
			// }
			return new Customer(this);
		}
	}
}
