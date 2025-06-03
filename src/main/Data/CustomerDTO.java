package main.Data;

import main.Enum.Sex;

public class CustomerDTO {
	private final String accountNumber;
	private final String address;
	private final int age;
	private final String customerID;
	private final String job;
	private final String name;
	private final String phoneNumber;
	private final String rrn;
	private final Sex sex;
	private final String pw;

	public CustomerDTO(Builder builder) {
		this.accountNumber = builder.accountNumber;
		this.address = builder.address;
		this.age = builder.age;
		this.customerID = builder.customerID;
		this.pw = builder.pw;
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

	public String getPw(){
		return pw;
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

	public static Builder builder() {
		return new Builder();
	}


	public static class Builder {
		private String accountNumber;
		private String address;
		private int age;
		private String customerID;
		private String pw;
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

		public Builder pw(String pw){
			this.pw = pw;
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

		public CustomerDTO build() {

			// if (name == null || customerID == null) {
			//     throw new IllegalStateException("Name and CustomerID cannot be null");
			// }
			return new CustomerDTO(this);
		}
	}
}
