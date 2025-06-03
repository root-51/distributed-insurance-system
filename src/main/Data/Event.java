package main.Data;
import java.util.Date;
import java.util.Objects;


/**
 * @author �ڼֹ�
 * @version 1.0
 * @created 11-5-2025 ���� 11:25:09
 */
public class Event {

	private int claimValue;
	public String customerID;
	private String documents;
	private Date eventDate;
	private String eventDescription;
	public String eventID;
	private String eventLocation;
	private Date receiptDate;
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
		return "Event{" +
				"claimValue=" + claimValue +
				", customerID=" + customerID +
				", documents='" + documents + '\'' +
				", eventDate=" + eventDate +
				", eventDescription=" + eventDescription +
				", eventID=" + eventID +
				", eventLocation='" + eventLocation + '\'' +
				", receiptDate=" + receiptDate +
				", m_Evaluation=" + evaluation +
				'}';
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