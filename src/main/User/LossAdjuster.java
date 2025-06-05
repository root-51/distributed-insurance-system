package main.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import main.Data.Compensation;
import main.Data.Contract;
import main.Data.Customer;
import main.Data.Evaluation;
import main.Data.Event;
import main.Data.InsuranceProduct;
import main.List.ContractList;
import main.List.ContractListImpl;
import main.List.CustomerList;
import main.List.CustomerListImpl;
import main.List.EventList;
import main.List.EventListImpl;
import main.List.InsuranceProductList;
import main.List.InsuranceProductListImpl;

/**
 * @author �ڼֹ�
 * @version 1.0
 * @created 11-5-2025 ���� 11:25:09
 */
 public class LossAdjuster extends User {

	private final EventList EventList;
	private final CustomerList customerList;
	private final ContractList contractList;
	private final InsuranceProductList insuranceProductList;

	public LossAdjuster(String userId, String userPW, UserType employeeType) {
		super(userId,userPW, employeeType);

		this.EventList = new EventListImpl();
		this.customerList = new CustomerListImpl();
		this.contractList = new ContractListImpl();
		this.insuranceProductList = new InsuranceProductListImpl();
	}

	/**
	 * 이벤트ID받고, 심사의 상태를 변경한다.
	 *
	 * @param eventID   심사할 사건의 ID
	 * @param isReceipt 심사 결과 (true: 승인, false: 거절)
	 * @return 심사 성공 여부 (true: 성공, false: 실패 - 해당 사건 ID가 없는 경우)
	 */
	public boolean evaluateCompensation(String eventID, boolean isReceipt, int predictedCompensationValue) {
		List<Event> eventList = EventList.searchEvent("event_id", eventID);
		if (eventList.isEmpty()) return false;
		Event targetEvent = eventList.getFirst();
		Evaluation targetEvaluation = targetEvent.getEvaluation();
		targetEvaluation.receiptEvaluation(isReceipt);
		targetEvaluation.getCompensation().setCompensationValue(predictedCompensationValue);
		targetEvent.setEvaluation(targetEvaluation);
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
		targetEvent.getEvaluation().getCompensation().receiptCompensation(isPaid);
		//보상액 대로 지급
		targetEvent.getEvaluation().getCompensation().setPaidValue(targetEvent.getEvaluation().getCompensation().getCompensationValue());
		if (targetEvent.getEvaluation() == null
        || targetEvent.getEvaluation().getCompensation() == null) return false;
		return EventList.update(targetEvent);
	}

	public int predictCompensationValue(Contract contract, Event event) {

		InsuranceProduct product = insuranceProductList.searchProductsByKey("product_id",contract.getProductID()).getFirst();
		Customer customer = customerList.search(contract.getCustomerID());

		// Date 타입을 LocalDate로 변환 (날짜 계산의 용이성을 위해)
		LocalDate eventLocalDate = event.getEventDate();
		LocalDate receiptLocalDate = event.getReceiptDate();
		LocalDate enrollmentLocalDate = contract.getContractDate(); // 가입일

		// --- 1. 보상 가능 여부 확인 (사전 조건 검사) ---

		// 1.1. 가입자 연령 확인
		if (customer.getAge() > product.getMaxAge()) {
			System.out.println("예측 불가: 가입자 연령(" + customer.getAge() + "세)이 상품의 최대 가입 연령(" + product.getMaxAge() + "세)을 초과합니다.");
			return -1;
		}

		// 1.2. 성별 확인
		if (customer.getSex() != product.getSex()) {
			System.out.println("예측 불가: 가입자 성별(" + customer.getSex() + ")이 상품의 가입 가능 성별(" + product.getSex() + ")과 일치하지 않습니다.");
			return -1;
		}

		// 1.3. 최대 사고 횟수 확인 (이번 사고를 포함한 청구 가능 횟수)
		// Note: predictCompensationValue는 '예측'이므로, contract.getClaimedAccidentCount()는 현재까지 보상된 횟수입니다.
		// 이번 사고가 보상될 경우 사고 횟수가 1 증가하므로, 이미 최대 횟수를 넘어섰다면 보상 불가.
		// 근데 이걸 기록하는 기능이 Contract에 지금 없음
//		if (contract.getClaimedAccidentCount() >= product.getMaxAccidentCount()) {
//			System.out.println("예측 불가: 이 계약의 최대 사고 횟수(" + product.getMaxAccidentCount() + "회)를 이미 초과했습니다.");
//			return 0;
//		}

		// --- 2. 면책 기간 확인 ---
		// 가입일로부터 사고 발생일까지의 기간이 상품의 초기 면책 기간(exemptionPeriod)보다 짧으면 보상 불가
		long daysSinceEnrollmentToEvent = ChronoUnit.DAYS.between(enrollmentLocalDate, eventLocalDate);
		if (daysSinceEnrollmentToEvent < product.getExemptionPeriod()) {
			System.out.println("예측 불가: 초기 면책 기간(" + product.getExemptionPeriod() + "일) 이내에 사고가 발생했습니다. (가입 후 " + daysSinceEnrollmentToEvent + "일 경과)");
			return -1;
		}

		System.out.println(customer.getAge()+"살");
		System.out.println(product.getCoverageByAge(customer.getAge())+"원");

		// --- 3. 기본 보상액 산정 (나이 기반) ---
		int baseCompensationAmount = Integer.parseInt(product.getCoverageByAge(customer.getAge()));

		// 해당 나이대의 보장 금액이 0이거나 유효하지 않으면 보상 불가
		if (baseCompensationAmount <= 0) {
			System.out.println("예측 불가: 가입자 연령(" + customer.getAge() + "세)에 해당하는 유효한 보장 정보가 없습니다.");
			return 0;
		}

		// --- 4. 감액 비율 적용 (reductionRatio) ---
		// 감액 기간: 14, 감액비율: 16% (주어진 정보)
		// 해석: 사고 접수일로부터 '감액 기간' 이내인 경우 감액비율을 적용한다고 가정.
		double finalCompensation = (double) baseCompensationAmount;

		long daysFromEventToReceipt = ChronoUnit.DAYS.between(eventLocalDate, receiptLocalDate);

		// 사고 발생일과 사고 접수일 사이의 기간이 감액 기간(reductionPeriod) 이내이면 감액 비율 적용
		// (단, reductionPeriod가 0이거나 음수인 경우 감액을 적용하지 않음)
		if (product.getReductionPeriod() > 0 && daysFromEventToReceipt <= product.getReductionPeriod()) {
			System.out.println("감액 기간 이내 (" + daysFromEventToReceipt + "일 경과). 감액 비율 " + (product.getReductionRatio()) + "% 적용.");
			finalCompensation -= (finalCompensation * product.getReductionRatio() / 100);
		} else if (product.getReductionPeriod() == 0) {
			// reductionPeriod가 0이면 감액기간 자체가 없다고 해석, 즉 감액 없음
		} else {
			System.out.println("감액 기간(" + product.getReductionPeriod() + "일) 초과 (" + daysFromEventToReceipt + "일 경과). 감액 없음.");
		}


		// Note: '고객이 청구한 금액(event.getClaimValue())'을 어떻게 반영할지는 약관에 따라 다릅니다.
		// 1. 연령별 보장범위가 '최대 한도'인 경우: 최종 보상액은 '최대 한도'를 넘을 수 없습니다. (현재 구현 방식)
		//    이 경우, 고객이 10000원을 청구해도 30대면 최대 400원에서 시작하여 감액이 적용됩니다.
		// 2. 연령별 보장범위가 '기본액'이고 청구액의 일정 비율을 보상하는 경우:
		//    `finalCompensation = Math.min(event.getClaimValue(), finalCompensation);`
		//    또는 청구액에 대한 특정 로직이 필요합니다.
		//    현재는 연령별 보장범위가 기준이므로, 청구액은 단순히 정보로만 사용하고 최종 보상액에 직접 반영하지 않습니다.


		// 최종 보상액은 int로 반환 (소수점 이하 버림)
		// 보상액보다 청구액이 크면, 보상액을 주게 된다.
		return 	event.getClaimValue() < finalCompensation ? event.getClaimValue() : (int) finalCompensation;
	}

	public EventList getEventList() {
		return this.EventList;
	}
	public CustomerList getCustomerList() {return this.customerList;}
	public ContractList getContractList() {return this.contractList;}
	public InsuranceProductList getInsuranceProductList() {return this.insuranceProductList;}
}