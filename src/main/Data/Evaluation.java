package main.Data;
import java.util.Objects;
import main.Enum.ProcessState;

public class Evaluation {


	private final String evaluationID;
	private final String eventID;
	private final String customerID;
	private ProcessState resultOfEvaluation;
	private Compensation compensation;

	// Private constructor to be used by the Builder
	private Evaluation(Builder builder) {
		this.evaluationID = builder.evaluationID;
		this.eventID = builder.eventID;
		this.customerID = builder.customerID;
		this.compensation = builder.compensation;
		this.resultOfEvaluation = (builder.resultOfEvaluation != null) ? builder.resultOfEvaluation : ProcessState.Awaiting;
	}

	// Getters
	public String getEvaluationID() {
		return evaluationID;
	}

	public String getEventID() {
		return eventID;
	}

	public String getCustomerID() {return customerID;}

	public Compensation getCompensation() {return compensation;}

	public ProcessState getResultOfEvaluation() {
		return resultOfEvaluation;
	}

	// setter
	public void setCompensation(Compensation compensation) {
		this.compensation = compensation;
	}
//m_Compensation


	/**
	 * @param isReceipt 심사 통과하려면 False, 거부하려면 False
	 */
	public void receiptEvaluation(boolean isReceipt){
		if(isReceipt) {
			this.resultOfEvaluation = ProcessState.Completed;
		}
		else {
			this.resultOfEvaluation = ProcessState.Rejected;
		}
	}

	// toString method
	@Override
	public String toString() {
		return "Evaluation{" +
				"evaluationID='" + evaluationID + '\'' +
				", eventID='" + eventID + '\'' +
				", customerID='"+customerID+'\''+
				'}';
	}

	// Builder class
	public static class Builder {
		// Required fields
		private final String eventID;
		private final String customerID;
		private final String evaluationID;
		// Optional fields
		private Compensation compensation;
		private ProcessState resultOfEvaluation;


		public Builder(String evaluationID, String eventID, String customerID) {
			this.eventID = eventID;
			this.customerID = customerID;
			this.evaluationID = evaluationID;
		}

		public Builder compensation(Compensation compensation) {
			this.compensation = compensation;
			return this;
		}
		public Builder resultOfEvaluation(ProcessState state){
			this.resultOfEvaluation = state;
			return this;
		}

		public Evaluation build() {
			return new Evaluation(this);
		}
	}


}