package main.Data;

import java.time.LocalDate;
import java.util.*;
import main.Enum.ProcessState;

import java.time.LocalDate;
import java.util.Date; // java.util.Date를 사용하는 것으로 가정합니다.

public class Contract {

	private Date contractDate;
	private String contractID;
	private String customerID;
	private LocalDate expirationDate;
	private String productID;
	private String salesID;
	private ProcessState state; // 아래에서 정의할 ENUM
	private InsuranceProduct insuranceProduct; // 아래에서 정의할 클래스 (또는 DB ID만 저장)

	// 모든 필드를 포함하는 생성자 (빌더에서만 사용)
	private Contract(Builder builder) {
		this.contractID = builder.contractID;
		this.contractDate = builder.contractDate;
		this.customerID = builder.customerID;
		this.expirationDate = builder.expirationDate;
		this.productID = builder.productID;
		this.salesID = builder.salesID;
		this.state = builder.state;
		this.insuranceProduct = builder.insuranceProduct;
	}

	// 모든 필드에 대한 Getter
	public Date getContractDate() {
		return contractDate;
	}

	public String getContractID() {
		return contractID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public String getProductID() {
		return productID;
	}

	public String getSalesID() {
		return salesID;
	}

	public ProcessState getState() {
		return state;
	}

	public String toString(){
		return "계약ID :'" + contractID + '\'' +
				"\n계약날짜 :" + contractDate + '\'' +
				"\n만료일자 : " + expirationDate + '\'' +
				"\n상품ID : "+ productID + '\''+
				"\n영업사원ID : "+salesID+'\''+
				"\n상태 : "+state+'\''+
				"\n고객ID : "+customerID+
				"\n ================================================";
	}

	// --- Builder Class ---
	public static class Builder {
		private Date contractDate;
		private String contractID; // Builder에서 초기 설정 가능 (예: DB에서 자동 생성되지 않는 경우)
		private String customerID;
		private LocalDate expirationDate;
		private String productID;
		private String salesID;
		private ProcessState state;
		private InsuranceProduct insuranceProduct;

		public Builder contractDate(Date contractDate) {
			this.contractDate = contractDate;
			return this;
		}

		public Builder contractID(String contractID) {
			this.contractID = contractID;
			return this;
		}

		public Builder customerID(String customerID) {
			this.customerID = customerID;
			return this;
		}

		public Builder expirationDate(LocalDate expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder productID(String productID) {
			this.productID = productID;
			return this;
		}

		public Builder salesID(String salesID) {
			this.salesID = salesID;
			return this;
		}

		public Builder state(ProcessState state) {
			this.state = state;
			return this;
		}

		public Builder insuranceProduct(InsuranceProduct insuranceProduct) {
			this.insuranceProduct = insuranceProduct;
			return this;
		}

		public Contract build() {
			return new Contract(this);
		}
	}
}