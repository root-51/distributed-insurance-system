package main.Data;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.Enum.ProcessState;

import java.time.LocalDate;
import java.util.Date; // java.util.Date를 사용하는 것으로 가정합니다.

public class Contract {

	private LocalDate contractDate;
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
	public LocalDate getContractDate() {
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

	public InsuranceProduct getInsuranceProduct() {return insuranceProduct;}

	public void receiptContract(boolean isReceipt){
		if(isReceipt) {
			this.state = ProcessState.Completed;
		}
		else {
			this.state = ProcessState.Rejected;
		}
	}

	public String toString() {

		return String.format(
				" || %-" + getKoreanCount(10,"계약ID") + "s%-" + getKoreanCount(30,contractID) + "s ||\n" +
						" || %-" + getKoreanCount(10,"계약날짜") + "s%-" + getKoreanCount(30,contractDate.toString()) + "s ||\n" +
						" || %-" + getKoreanCount(10,"만료일자") + "s%-" + getKoreanCount(30,expirationDate.toString()) + "s ||\n" +
						" || %-" + getKoreanCount(10,"상품ID") + "s%-" + getKoreanCount(30,productID) + "s ||\n" +
						" || %-" + getKoreanCount(10,"영업사원ID") + "s%-" + getKoreanCount(30,salesID) + "s ||\n" +
						" || %-" + getKoreanCount(10,"상태") + "s%-" + getKoreanCount(30,ProcessState.toKoString(state)) + "s ||\n" +
						" || %-" + getKoreanCount(10,"고객ID") + "s%-" + getKoreanCount(30,customerID) + "s ||",
				// 각 라인별 레이블과 실제 값
				"계약ID" + ": ", contractID,
				"계약날짜" + ": ", contractDate,
				"만료일자" + ": ", expirationDate,
				"상품ID" + ": ", productID,
				"영업사원ID" + ": ", salesID,
				"상태" + ": ", ProcessState.toKoString(state),
				"고객ID" + ": ", customerID
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

	// --- Builder Class ---
	public static class Builder {
		private LocalDate contractDate;
		private String contractID; // Builder에서 초기 설정 가능 (예: DB에서 자동 생성되지 않는 경우)
		private String customerID;
		private LocalDate expirationDate;
		private String productID;
		private String salesID;
		private ProcessState state;
		private InsuranceProduct insuranceProduct;

		public Builder contractDate(LocalDate contractDate) {
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