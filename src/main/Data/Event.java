package main.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.Enum.ProcessState;


/**
 * @author �ڼֹ�
 * @version 1.0
 * @created 11-5-2025 ���� 11:25:09
 */
public class Event {
	public String eventID;
	private int claimValue;
	private String documents;
	private LocalDate eventDate;
	private String eventDescription;
	private String eventLocation;
	private LocalDate receiptDate;
	// state_of_evealuation
	// state_of_compensation
	public String customerID;
	// paid_value
	private Evaluation evaluation;

	private Event(Builder builder) {
		this.customerID = builder.customerID;
		this.claimValue = builder.claimValue;
		this.documents = builder.documents;
		this.eventDate = builder.eventDate;
		this.eventDescription = builder.eventDescription;
		this.eventID = builder.eventID;
		this.eventLocation = builder.eventLocation;
		this.receiptDate = builder.receiptDate;
		this.evaluation = builder.evaluation;
	}

	public int getClaimValue() {
		return claimValue;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getDocuments() {
		return documents;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public String getEventID() {
		return eventID;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public LocalDate getReceiptDate() {
		return receiptDate;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}


	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
//m_Evaluation setter



	public static class Builder {
		private final String customerID;
		private final String eventID;
		private int claimValue;
		private String documents;
		private LocalDate eventDate;
		private String eventDescription;
		private String eventLocation;
		private LocalDate receiptDate;
		private Evaluation evaluation;

		public Builder(String eventID,String customerID){
			this.eventID = eventID;
			this.customerID = customerID;
		}
		public Builder claimValue(int claimValue) {
			this.claimValue = claimValue;
			return this;
		}

		public Builder documents(String documents) {
			this.documents = documents;
			return this;
		}

		public Builder eventDate(LocalDate eventDate) {
			this.eventDate = eventDate;
			return this;
		}

		public Builder eventDescription(String eventDescription) {
			this.eventDescription = eventDescription;
			return this;
		}

		public Builder eventLocation(String eventLocation) {
			this.eventLocation = eventLocation;
			return this;
		}

		public Builder receiptDate(LocalDate receiptDate) {
			this.receiptDate = receiptDate;
			return this;
		}
		public Builder evaluation(Evaluation evaluation) {
			this.evaluation = evaluation;
			return this;
		}

		public Event build() {
			return new Event(this);
		}
	}
	@Override
	public String toString() {
		final int labelWidth = 8;
		final int valueWidth = 15;

		String claimValueString = String.valueOf(claimValue);
		String evaluationStateString = (evaluation != null && evaluation.getResultOfEvaluation() != null) ?
				ProcessState.toKoString(evaluation.getResultOfEvaluation()) : "";
		String compensationStateString = (evaluation != null && evaluation.getCompensation() != null && evaluation.getCompensation().getState() != null) ?
				ProcessState.toKoString(evaluation.getCompensation().getState()) : "";


		return String.format(
				" || %-" + getKoreanCount(labelWidth, "사고ID") + "s: %-" + getKoreanCount(valueWidth, eventID) + "s || %-" + getKoreanCount(labelWidth, "청구액") + "s: %-" + getKoreanCount(valueWidth, claimValueString) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "고객ID") + "s: %-" + getKoreanCount(valueWidth, customerID) + "s || %-" + getKoreanCount(labelWidth, "문서") + "s: %-" + getKoreanCount(valueWidth, documents) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "사고날짜") + "s: %-" + getKoreanCount(valueWidth, eventDate.toString()) + "s || %-" + getKoreanCount(labelWidth, "사고내용") + "s: %-" + getKoreanCount(valueWidth, eventDescription) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "사고장소") + "s: %-" + getKoreanCount(valueWidth, eventLocation) + "s || %-" + getKoreanCount(labelWidth, "사고신청일") + "s: %-" + getKoreanCount(valueWidth, receiptDate.toString()) + "s ||\n" +
						" || %-" + getKoreanCount(labelWidth, "심사상태") + "s: %-" + getKoreanCount(valueWidth, evaluationStateString) + "s || %-" + getKoreanCount(labelWidth, "보상상태") + "s: %-" + getKoreanCount(valueWidth, compensationStateString) + "s ||",
				// 각 라인별 레이블과 실제 값
				"사고ID", eventID, "청구액", claimValueString,
				"고객ID", customerID, "문서", documents,
				"사고날짜", eventDate, "사고내용", eventDescription,
				"사고장소", eventLocation, "사고신청일", receiptDate,
				"심사상태", evaluationStateString,
				"보상상태", compensationStateString
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

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (null == o || getClass() != o.getClass()) return false;
		Event event;
		event = (Event) o;
		return claimValue == event.claimValue && Objects.equals(customerID, event.customerID) && Objects.equals(documents, event.documents) && Objects.equals(eventDate, event.eventDate) && Objects.equals(eventDescription, event.eventDescription) && Objects.equals(eventID, event.eventID) && Objects.equals(eventLocation, event.eventLocation) && Objects.equals(receiptDate, event.receiptDate) && Objects.equals(
				evaluation, event.evaluation);
	}

}