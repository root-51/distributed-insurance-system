package main.Data;
import java.util.HashMap;
import main.Enum.Sex;

public class InsuranceProduct {

	private HashMap<String,String> coverageByAge;
	private int exemptionPeriod;
	private int maxAge;
	private int maxNumberEvent;
	private int premium;
	public String productID;
	private String productManagementID;
	public String productName;
	private int reductionPeriod;
	private int reductionRatio;
	private Sex sex;

	public InsuranceProduct(Builder builder){
		this.coverageByAge = builder.coverageByAge;
		this.maxAge = builder.maxAge;
		this.maxNumberEvent = builder.maxNumberEvent;
		this.premium = builder.premium;
		this.productManagementID = builder.productManagementID;
		this.productID = builder.productID;
		this.productName = builder.productName;
		this.reductionPeriod = builder.reductionPeriod;
		this.reductionRatio = builder.reductionRatio;
		this.sex = builder.sex;
		this.exemptionPeriod=builder.exemptionPeriod;
	}

	/**
	 * @param customerAge
	 */
//	getCoverageByAge의 매개변수 중 int타입의 연령대 변수가 있는데 필요 없는거 같아서 일단 지웠어요
	public String getCoverageByAge(int customerAge){
		int ageGroup = customerAge/10;
		if(coverageByAge.containsKey(Integer.toString(ageGroup)))
			return coverageByAge.get(Integer.toString(ageGroup));
		return null;
	}

	public HashMap<String, String> getCoverageByAge(){
		return this.coverageByAge;
	}

	public int getExemptionPeriod(){return exemptionPeriod;}
	public boolean setExemptionPeriod(int value){
		this.exemptionPeriod = value;
		return true;
	}
	public int getMaxAge(){
		return maxAge;
	}
	public boolean setMaxAge(int value){
		this.maxAge=value;
		return true;
	}
	public int getMaxNumberEvent(){
		return maxNumberEvent;
	}
	public boolean setMaxNumberEvent(int value){
		this.maxNumberEvent=value;
		return true;
	}
	public int getPremium(){ return premium;}
	public boolean setPremium(int value){
		this.premium=value;
		return true;
	}
	public String getProductID(){return productID;}
	public String getProductManagementID(){return productManagementID;}
	public String getProductName(){return productName;}
	public boolean setProductName(String value){
		this.productName=value;
		return true;
	}
	public int getReductionPeriod(){return this.reductionPeriod;}
	public boolean setReductionPeriod(int value){
		this.reductionPeriod = value;
		return true;
	}
	public int getReductionRatio(){return this.reductionRatio;}
	public boolean setReductionRatio(int value){
		this.reductionRatio = value;
		return true;
	}
	public Sex getSex(){
		return sex;
	}
	public boolean setSex(Sex sex){
		this.sex = sex;
		return true;
	}

	public boolean setCoverageByAge(HashMap<String, String> coverageByAge) {
		this.coverageByAge = coverageByAge;
		return true;
	}

	public void setProductID(String productID){
		this.productID = productID;
	}

	public String toString() {
		StringBuilder coverageStr = new StringBuilder();
		for (HashMap.Entry<String, String> entry : coverageByAge.entrySet()) {
			coverageStr.append(entry.getKey()).append("대 : ")
					.append(entry.getValue()).append("원\n");
		}

		return "productID :'" + productID + '\'' +
				",\n  productName : '" + productName + '\'' +
				",\n  productManagementID : '" + productManagementID + '\'' +
				",\n  coverageByAge\n" + coverageStr.toString() +
				"  exemptionPeriod : " + exemptionPeriod +
				",\n  maxAge: " + maxAge +
				",\n  maxNumberEvent : " + maxNumberEvent +
				",\n  premium : " + premium +
				",\n  reductionPeriod : " + reductionPeriod +
				",\n  reductionRatio: " + reductionRatio +
				",\n  sex=" + sex +
				"\n ================================================";
	}


	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private HashMap<String,String> coverageByAge;
		private int exemptionPeriod;
		public int maxAge;
		public int maxNumberEvent;
		public int premium;
		public String productManagementID;
		public String productName;
		public String productID;
		private int reductionPeriod;
		private int reductionRatio;
		public Sex sex;

		public Builder coverageByAge(HashMap<String,String> coverageByAge) {
			this.coverageByAge = coverageByAge;
			return this;
		}

		public Builder exemptionPeriod(int exemptionPeriod) {
			this.exemptionPeriod = exemptionPeriod;
			return this;
		}

		public Builder maxAge(int maxAge) {
			this.maxAge = maxAge;
			return this;
		}

		public Builder maxNumberEvent(int maxNumberEvent) {
			this.maxNumberEvent = maxNumberEvent;
			return this;
		}

		public Builder premium(int premium) {
			this.premium = premium;
			return this;
		}

		public Builder productManagementID(String productManagementID) {
			this.productManagementID = productManagementID;
			return this;
		}

		public Builder productName(String productName) {
			this.productName = productName;
			return this;
		}

		public Builder productID(String productID){
			this.productID = productID;
			return this;
		}

		public Builder reductionPeriod(int reductionPeriod) {
			this.reductionPeriod = reductionPeriod;
			return this;
		}

		public Builder reductionRatio(int reductionRatio) {
			this.reductionRatio = reductionRatio;
			return this;
		}

		public Builder sex(Sex sex) {
			this.sex = sex;
			return this;
		}

		public InsuranceProduct build() {
			return new InsuranceProduct(this);
		}
	}

}