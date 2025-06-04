
package main.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.DAO.DAO;
import main.Data.Event;

public class EventListImpl implements EventList {

	public ArrayList<Event> Events;

	public EventListImpl(){
		Events = new ArrayList<>();
	}

	/**
	 * 
	 * @param eventID
	 */
	public boolean delete(String eventID){
		try (DAO dao = new DAO()){
			dao.executeQuery("DELETE FROM `event` WHERE event_id = ?", eventID);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean insert(Event event) {
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO `event` (event_id, claim_value, documents, event_date, event_description, event_location, event_receipt_date, state_of_evaluation, state_of_compensation, user_id, paid_value)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					event.getEventID(),
					event.getClaimValue(),
					event.getDocuments(),
					event.getEventDate(),
					event.getEventDescription(),
					event.getEventLocation(),
					event.getReceiptDate(),
					0, // state_of_evaluation
					0, // state_of_compensation
					event.getCustomerID(),
					0); // paid_value
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}


//Search
	@Override
	public List<Event> searchEvent(String key, String value) {
		try (DAO dao = new DAO()) {
			return dao.executeQuery("SELECT * FROM event WHERE "+key+" = ?",value).toEvents();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Event> searchEvent(String eventID){
		try (DAO dao = new DAO()) {
			return dao.executeQuery("SELECT * FROM event WHERE event_id = ?",eventID).toEvents();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Event> searchEvent(Map<String, String> queryMap) {
		String sql;
		StringBuilder whereClause = new StringBuilder();
		boolean firstCondition = true;
		for (Map.Entry<String, String> entry : queryMap.entrySet()) {
			String column = entry.getKey();
			String value = entry.getValue();
			if (!firstCondition) {
				whereClause.append(" AND ");
			} else {
				firstCondition = false;
			}
			whereClause.append(column).append(" = '").append(value).append("'");
		}
		sql = "SELECT * FROM event";
		if (whereClause.length() > 0) {
			sql += " WHERE " + whereClause.toString();
		}
		try (DAO dao = new DAO()) {
			return dao.executeQuery(sql).toEvents();
		} catch (Exception e) {
			System.err.println("Error searching events: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

//Update
	@Override
	public boolean update(Event updatedEvent) {
		try (DAO dao = new DAO()){
			dao.executeQuery("UPDATE `event` SET claim_value = ?, documents = ?, event_date = ?, event_description = ?, event_location = ?, event_receipt_date = ?, state_of_evaluation = ?, state_of_compensation = ?, user_id = ?, paid_value = ? WHERE event_id = ?",
					updatedEvent.getClaimValue(),
					updatedEvent.getDocuments(),
					updatedEvent.getEventDate(),
					updatedEvent.getEventDescription(),
					updatedEvent.getEventLocation(),
					updatedEvent.getReceiptDate(),
					updatedEvent.getEvaluation().getResultOfEvaluation().getValue(),
					updatedEvent.getEvaluation().getCompensation().getResultOfPaid().getValue(),
					updatedEvent.getCustomerID(),
					updatedEvent.getEvaluation().getCompensation().getPaidValue(),
					updatedEvent.getEventID());
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public List<Event> getAll(){
		try (DAO dao = new DAO()){
			return dao.executeQuery("SELECT * FROM event").toEvents();
		}catch (Exception e){
			return null;
		}
	}
}//end EventListImpl