package main.Employee;

import main.Data.Compensation;
import main.Data.Evaluation;
import main.Data.Event;
import main.List.EventList;
import main.List.EventListImpl;

/**
 * @author �ڼֹ�
 * @version 1.0
 * @created 11-5-2025 ���� 11:25:09
 */
 public class LossAdjuster extends User {

	private final EventList EventList;

	public LossAdjuster(int numOfEmployees, UserType employeeType) {
		super(numOfEmployees, employeeType);
		this.EventList = new EventListImpl();
	}
	public LossAdjuster(String userId, UserType employeeType) {
		super(userId, employeeType);
		this.EventList = new EventListImpl();
	}

	/**
	 * 이벤트ID받고, 심사의 상태를 변경한다.
	 *
	 * @param eventID   심사할 사건의 ID
	 * @param isReceipt 심사 결과 (true: 승인, false: 거절)
	 * @return 심사 성공 여부 (true: 성공, false: 실패 - 해당 사건 ID가 없는 경우)
	 */
	public boolean evaluateCompensation(String eventID, boolean isReceipt) {
		Event targetEvent = this.EventList.searchEvent("event_id", eventID).getFirst();
		if (targetEvent == null)
			return false;
		Evaluation targetEvaluation = targetEvent.getEvaluation();
		targetEvaluation.receiptEvaluation(isReceipt);
		targetEvent.setEvaluation(targetEvaluation);
		if (isReceipt)
			System.out.println("보상 심사가 완료되었습니다.");
		else
			System.out.println("보상 심사가 거절되었습니다.");
		return EventList.update(targetEvent);
	}

	/**
	 * 심사가 완료된 사고들의 보상금을 지급하거나 거부합니다.
	 *
	 * @param eventID 지급 또는 거부할 보상금의 ID
	 * @param isPaid         true면지급, false면 거부
	 * @return 보상금 지급 성공시 true를, 보상금 ID가 유효하지 않거나 업데이트에 실패한 경우 false
	 */
	public boolean payCompensation(String eventID, boolean isPaid) {
		Event targetEvent = this.EventList.searchEvent("event_id", eventID).getFirst();
		Evaluation targetEvaluation = targetEvent.getEvaluation();
		Compensation targetCompensation = targetEvaluation.getCompensation();
		if (targetCompensation == null) return false;
		targetCompensation.receiptCompensation(isPaid);
		targetEvaluation.setCompensation(targetCompensation);
		targetEvent.setEvaluation(targetEvaluation);
		if (isPaid)
			System.out.println("보상 지급이 완료되었습니다.");
		else
			System.out.println("보상 지급이 거부되었습니다.");
		return EventList.update(targetEvent);
	}

	//임시, 더미데이터생성기
	public void genrateDummy(int count) {

		for (int i = 0; i < count; i++) {
			String customerID = "CustomerN" + i;
			String EventID = "EventN" + i;
			String EvaluationID = "EvaluationN" + i;
			String CompensationID = "CompensationN" + i;
			Event e = new Event.Builder(EventID, customerID).build();
			Evaluation ev = new Evaluation.Builder(EvaluationID, e.getEventID(), customerID).build();
			Compensation c = new Compensation.Builder(CompensationID, ev.getEvaluationID(),
					customerID).build();

			ev.setCompensation(c);
			e.setEvaluation(ev);
			EventList.insert(e);
		}
	}

	public EventList getEventList() {
		return this.EventList;
	}
}