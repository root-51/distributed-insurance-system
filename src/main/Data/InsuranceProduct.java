package main.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

	@Override
	public String toString() {
		// --- 여기의 labelWidth 및 valueWidth를 조정하세요 ---
		// 각 레이블이 차지할 '목표' 너비 (getKoreanCount에 전달됨)
		final int labelWidth = 14; // "상품관리자ID" (7글자 한글 포함) 정렬을 위한 목표 너비
		// 각 값이 차지할 '목표' 너비 (getKoreanCount에 전달됨)
		final int valueWidth = 25; // 상품ID, 이름 등 긴 값을 포함하기 위한 목표 너비

		StringBuilder formattedOutput = new StringBuilder();
		formattedOutput.append("\n=== 보험 상품 정보 ===\n"); // 시작 헤더

		// 1. 기본 정보
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "상품ID") + "s: %s\n", "상품ID", productID));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "상품이름") + "s: %s\n", "상품이름", productName));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "상품관리자ID") + "s: %s\n", "상품관리자ID", productManagementID));
		formattedOutput.append("\n"); // 줄바꿈으로 섹션 구분

		// 2. 연령별 보장범위 (정렬된 형태로 출력)
		formattedOutput.append(" 연령별 보장범위:\n");
		// 키셋을 정렬하여 순서대로 출력 (0대, 10대, 20대...)
		coverageByAge.entrySet().stream()
				.sorted(HashMap.Entry.comparingByKey())
				.forEach(entry -> {
					String ageKey = entry.getKey() + "대";
					String value = entry.getValue() + "원";
					// 연령대와 금액도 getKoreanCount에 맞춰 정렬
					formattedOutput.append(String.format("  %-" + getKoreanCount(5, ageKey) + "s: %s\n", ageKey, value));
				});
		formattedOutput.append("\n"); // 줄바꿈으로 섹션 구분


		// 3. 기타 상세 정보
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "면책기간(초기)") + "s: %s일\n", "면책기간(초기)", exemptionPeriod));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "최대가입연령") + "s: %s세\n", "최대가입연령", maxAge));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "최대사고횟수") + "s: %s회\n", "최대사고횟수", maxNumberEvent));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "보험료") + "s: %s원\n", "보험료", (int)premium)); // 정수로 출력
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "면책기간(감액)") + "s: %s일\n", "면책기간(감액)", reductionPeriod));
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "감액비율") + "s: %s%%\n", "감액비율", (int)(reductionRatio * 100))); // 퍼센트로 출력
		formattedOutput.append(String.format(" %-" + getKoreanCount(labelWidth, "성별") + "s: %s\n", "성별", sex));

		formattedOutput.append("========================================\n"); // 끝나는 푸터

		return formattedOutput.toString();
	}
	public int getKoreanCount(int width, String str) {
		if (str == null || str.isEmpty()) {
			return Math.max(1, width); // MissingFormatWidthException 방지를 위해 최소 1 또는 원래 width 반환
		}
		Matcher matcher = Pattern.compile("[\\uAC00-\\uD7A3]").matcher(str);
		int koreanCount = 0;
		while (matcher.find()) {
			koreanCount++;
		}
		int calculatedWidth = width - koreanCount / 3; // 사용자 요청 로직
		return Math.max(1, calculatedWidth); // 계산된 너비가 음수/0이 되는 것을 방지
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