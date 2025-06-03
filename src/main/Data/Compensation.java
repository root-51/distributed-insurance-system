package main.Data;
import java.util.Objects;
import main.Enum.ProcessState;

public class Compensation {

	/**
	 * Class 다이어그램과 다른점 몇가지
	 * 1. resultOFPaid와 PaidState를 합침
	 */


	// Example Fields
	private final String compensationID;
	private final String evaluationID; // Link to an Evaluation
	private final String customerID;   // Link to a Customer
	private ProcessState resultOfPaid;
	private int amountOfPaid;
	// Builder Pattern

	private Compensation(Builder builder) {
		this.compensationID = builder.compensationID;
		this.evaluationID = builder.evaluationID;
		this.customerID = builder.customerID;
		this.amountOfPaid = builder.claimsPaid;
		this.resultOfPaid = ProcessState.Awaiting;
	}

	// Getters
	public String getCompensationID() {
		return compensationID;
	}

	public String getEvaluationID() {
		return evaluationID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public ProcessState getState() {
		return resultOfPaid;
	}

	public int getAmountOfPaid() {
		return amountOfPaid;
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

	public void setAmountOfPaid(int paid){
		this.amountOfPaid = paid;
  }

	public static class Builder {
		private final String evaluationID;
		private final String customerID;
		private final String compensationID;
		private ProcessState paidState;
		private int claimsPaid;


		public Builder(String compensationID,String evaluationID, String customerID) {
			this.evaluationID = evaluationID;
			this.customerID = customerID;
			this.compensationID = compensationID;
			this.paidState = ProcessState.Awaiting;
			this.claimsPaid = 0;

		}

		public Builder paidState(ProcessState paidState) {
			this.paidState = paidState;
			return this;
		}

		public Builder claimsPaid(int claimsPaid) {
			this.claimsPaid = claimsPaid;
			return this;
		}


		public Compensation build() {
			return new Compensation(this);
		}
	}


	@Override
	public String toString() {
		return "Compensation{" +
				"compensationID='" + compensationID + '\'' +
				", evaluationID='" + evaluationID + '\'' +
				", customerID='" + customerID + '\'' +
				", paidState=" + resultOfPaid +
				", claimsPaid=" + amountOfPaid +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Compensation that = (Compensation) o;
		return amountOfPaid == that.amountOfPaid && Objects.equals(compensationID, that.compensationID) && Objects.equals(evaluationID, that.evaluationID) && Objects.equals(customerID, that.customerID) && resultOfPaid
				== that.resultOfPaid;
	}

}