package main.Data;
import java.util.Objects;
import main.Enum.ProcessState;

public class Compensation {

	/**
	 * Class 다이어그램과 다른점 몇가지
	 * 1. resultOFPaid와 PaidState를 합침
	 */


	// Example Fields
	private final String EventID;
	private ProcessState resultOfPaid;
	private int paidValue;
	private int compensationValue;
	// Builder Pattern

	private Compensation(Builder builder) {
		this.EventID = builder.eventID;
		this.compensationValue = builder.compensationValue;
		this.paidValue = builder.paidValue;
		this.resultOfPaid = (builder.resultOfPaid != null) ? builder.resultOfPaid : ProcessState.Awaiting;
	}

	//getter
	public ProcessState getState() {
		return resultOfPaid;
	}
	public String getEventID() {return EventID;}
	public int getCompensationValue() {return compensationValue;}
	public int getPaidValue() {
		return paidValue;
	}
	public ProcessState getResultOfPaid(){
		return resultOfPaid;
	}

	// setter?
	public void receiptCompensation(boolean isReceipt){
		if(isReceipt) {
			this.resultOfPaid = ProcessState.Completed;
		}
		else {
			this.resultOfPaid = ProcessState.Rejected;
		}
	}

	public void setPaidValue(int paid){
		this.paidValue = paid;
  }
	public void setCompensationValue(int compensationValue){
		this.compensationValue = compensationValue;
	}

	public static class Builder {
		private final String eventID;
		private int compensationValue;
		private int paidValue;
		private ProcessState resultOfPaid;


		public Builder(String eventID) {
			this.eventID = eventID;
			this.resultOfPaid = ProcessState.Awaiting;
			this.paidValue = 0;
			this.compensationValue = 0;
		}

		public Builder resultOfPaid(ProcessState resultOfPaid) {
			this.resultOfPaid = resultOfPaid;
			return this;
		}

		public Builder paidValue(int paidValue) {
			this.paidValue = paidValue;
			return this;
		}

		public Builder compensationValue(int compensationValue) {
			this.compensationValue = compensationValue;
			return this;
		}


		public Compensation build() {
			return new Compensation(this);
		}
	}

}