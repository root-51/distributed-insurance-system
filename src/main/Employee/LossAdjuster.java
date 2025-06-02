package main.Employee;

import main.DAO.DAO;
import main.DAO.ResultSetWrapper;
import main.Data.*;
import main.List.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * 이벤트ID받고, 심사의 상태를 변경한다.
	 *
	 * @param eventID   심사할 사건의 ID
	 * @param isReceipt 심사 결과 (true: 승인, false: 거절)
	 * @return 심사 성공 여부 (true: 성공, false: 실패 - 해당 사건 ID가 없는 경우)
	 */
	public boolean evaluateCompensation(String eventID, boolean isReceipt) {
		Event targetEvent = this.EventList.searchEvent("id", eventID).getFirst();
		if (targetEvent == null)
			return false;
		Evaluation targetEvaluation = targetEvent.getEvaluation();
		targetEvaluation.receiptEvaluation(isReceipt);
		if (isReceipt)
			System.out.println("보상 심사가 완료되었습니다.");
		else
			System.out.println("보상 심사가 거절되었습니다.");
		return EventList.updateEvaluation(targetEvaluation);
	}

	/**
	 * 심사가 완료된 사고들의 보상금을 지급하거나 거부합니다.
	 *
	 * @param compensationID 지급 또는 거부할 보상금의 ID
	 * @param isPaid         true면지급, false면 거부
	 * @return 보상금 지급 성공시 true를, 보상금 ID가 유효하지 않거나 업데이트에 실패한 경우 false
	 */
	public boolean payCompensation(String compensationID, boolean isPaid) {
		Compensation targetCompensation = this.EventList.searchEvent("compensation_id", compensationID)
				.getFirst().getEvaluation().getCompensation();
		if (targetCompensation == null) return false;
		targetCompensation.receiptCompensation(isPaid);
		if (isPaid)
			System.out.println("보상 지급이 완료되었습니다.");
		else
			System.out.println("보상 지급이 거부되었습니다.");
		return EventList.updateCompensation(targetCompensation);
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

	public List<Contract> getContractByCustomerID(String customerID) {
		try(DAO dao = new DAO()){
			ResultSetWrapper wrapper = dao.executeQuery("SELECT * FROM contract WHERE customer_id = ?",customerID);
			return wrapper.toContracts();
		}catch(Exception e){
			return null;
		}
	}

	public Customer getCustomer(String customerID) {
		try(DAO dao = new DAO()){
			ResultSetWrapper wrapper = dao.executeQuery("SELECT * FROM customer WHERE customer_id = ?",customerID);
			return  wrapper.toCustomer().get(0);
		}catch(Exception e){
			return null;
		}
	}

	public List<InsuranceProduct> getProductsByContractID(List<Contract> contractList) {
		InsuranceProductList productList = new InsuranceProductListImpl();

		try(DAO dao = new DAO()){

			ArrayList<String> productIDs = new ArrayList<>();
			for (int i=0;i<contractList.size();i++) {
				productIDs.add(contractList.get(i).getProductID());
			}

			String placeholder = productIDs.stream().map(id->"?").collect(Collectors.joining(", "));
			Object[] params = productIDs.toArray();
			try{
				ResultSetWrapper wrapper = dao.executeQuery("SELECT * FROM insurance_procut WHERE product_id in ("+ placeholder + ")",params);
				return wrapper.toInsuranceProduct();
			}catch(Exception e){
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}
}