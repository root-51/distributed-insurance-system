package main.Data;
import java.util.Objects;
import main.Enum.ProcessState;

public class Evaluation {


	private final String eventID;
	private ProcessState resultOfEvaluation;
	private Compensation compensation;

	// Private constructor to be used by the Builder
	private Evaluation(Builder builder) {
		this.eventID = builder.eventID;
		this.compensation = builder.compensation;
		this.resultOfEvaluation = (builder.resultOfEvaluation != null) ? builder.resultOfEvaluation : ProcessState.Awaiting;
	}

	// Getters
	public String getEventID() {
		return eventID;
	}

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


	// Builder class
	public static class Builder {
		// Required fields
		private final String eventID;
		private Compensation compensation;
		private ProcessState resultOfEvaluation;


		public Builder(String eventID, Compensation compensation) {
			this.eventID = eventID;
			this.compensation = compensation;
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