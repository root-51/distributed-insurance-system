package main.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	private final String pw;

	private Customer(Builder builder) {
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

	public String getPw(){return pw;}

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
		final int labelWidth = 8;
		final int valueWidth = 25;

		String ageString = String.valueOf(age);
		String sexString = Sex.getKoString(sex);

		return String.format(
				" || %-" + getKoreanCount(labelWidth, "계좌번호") + "s: %-" + getKoreanCount(valueWidth, accountNumber) + "s || %-" + getKoreanCount(labelWidth, "주소") + "s: %-" + getKoreanCount(valueWidth, address) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "나이") + "s: %-" + getKoreanCount(valueWidth, ageString) + "s || %-" + getKoreanCount(labelWidth, "고객ID") + "s: %-" + getKoreanCount(valueWidth, customerID) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "직업") + "s: %-" + getKoreanCount(valueWidth, job) + "s || %-" + getKoreanCount(labelWidth, "이름") + "s: %-" + getKoreanCount(valueWidth, name) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "전화번호") + "s: %-" + getKoreanCount(valueWidth, phoneNumber) + "s || %-" + getKoreanCount(labelWidth, "주민번호") + "s: %-" + getKoreanCount(valueWidth, rrn) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "성별") + "s: %-" + getKoreanCount(valueWidth, sexString) + "s || %-" + getKoreanCount(labelWidth, "") + "s: %-" + getKoreanCount(valueWidth, "") + "s ||",
				"계좌번호", accountNumber, "주소", address,
				"나이", ageString, "고객ID", customerID,
				"직업", job, "이름", name,
				"전화번호", phoneNumber, "주민번호", rrn,
				"성별", sexString, "", ""
		);
	}
	private int getKoreanCount(int width,String str) {
		if (str == null || str.isEmpty()) {
			return width;
		}
		Matcher matcher = Pattern.compile("[\\uAC00-\\uD7A3]").matcher(str);
		int koreanCount = 0;
		while (matcher.find()) {
			koreanCount++;
		}
		return width - koreanCount/2;
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

		public Customer build() {

			// if (name == null || customerID == null) {
			//     throw new IllegalStateException("Name and CustomerID cannot be null");
			// }
			return new Customer(this);
		}
	}
}
