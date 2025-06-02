package main.List;
import java.util.ArrayList;
import java.util.List;

import main.Data.Compensation;
import main.Data.Evaluation;
import main.Data.Event;

/**
 * @author �ڼֹ�
 * @version 1.0
 * @created 11-5-2025 ���� 11:25:09
 */
public interface EventList {


	public boolean delete(String eventID);
	public boolean insert(Event event);

	//search
	public ArrayList<Event> searchEvent(String key, String value);

	//update
	public boolean update(Event event);

	boolean updateEvaluation(Evaluation targetEvaluation);
	boolean updateCompensation(Compensation targetCompensation);

	List<Event> searchCompensation(String state, String awaiting);

	List<Event> searchEvaluation(String state, String awaiting);

//	public ArrayList<Event> searchCompensation(String state, String awaiting);
}