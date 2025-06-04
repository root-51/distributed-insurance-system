package main.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	public List<Event> searchEvent(String key, String value);
	public List<Event> searchEvent(Map<String,String> queryMap);
	public List<Event> searchEvent(String eventID);


	//update
	public boolean update(Event event);
	public List<Event> getAll();

}