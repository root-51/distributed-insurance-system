
package main.List;
import java.util.ArrayList;
import main.DAO.DAO;
import main.Data.Compensation;
import main.Data.Evaluation;
import main.Data.Event;
import main.Enum.ProcessState;

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
	public ArrayList<Event> searchEvent(String key, String value) {
		try (DAO dao = new DAO()) {
			String sql="";
			switch (key){
				case "user_id":
					sql = "SELECT * FROM `event` WHERE user_id = ?";
					break;
				case "event_id":
					sql = "SELECT * FROM `event` WHERE event_id = ?";
					break;
			}
			ArrayList<Event> events =(ArrayList<Event>) dao.executeQuery(sql, value).toEvents();
			return events;
		} catch (Exception e) {
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
					updatedEvent.getEvaluation().getCompensation().getAmountOfPaid(),
					updatedEvent.getEventID());
			return true;
		}catch(Exception e){
			return false;
		}
	}
}//end EventListImpl