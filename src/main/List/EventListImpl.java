
package main.List;
import java.util.ArrayList;
import java.util.List;

import main.DAO.DAO;
import main.DAO.ResultSetWrapper;
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
			dao.executeQuery("DELETE FROM Event WHERE EventID = ?", eventID);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean insert(Event event) {
		try (DAO dao = new DAO()){
			dao.executeQuery("INSERT INTO Event (event_id, claim_value, documents, event_date, event_description, event_location, event_receipt_date, state_of_evaluation, state_of_compensation, user_id, paid_value)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					event.getEventID(),
					event.getClaimValue(),
					event.getDocuments(),
					event.getEventDate(),
					event.getEventDescription(),
					event.getEventLocation(),
					event.getReceiptDate(),
					event.getEvaluation().getResultOfEvaluation().getValue(),
					event.getEvaluation().getCompensation().getResultOfPaid().getValue(),
					event.getCustomerID(),
					event.getEvaluation().getCompensation().getAmountOfPaid());
			return true;
		}catch(Exception e){
			return false;
		}
	}


	//Search
	@Override
	public ArrayList<Event> searchEvent(String key, String value) {
		try (DAO dao = new DAO()) {
			return (ArrayList<Event>) dao.executeQuery("SELECT * FROM Event WHERE ? = ?", key, value)
					.toEvents();
		} catch (Exception e) {
			return null;
		}
	}


	//Update
	@Override
	public boolean update(Event updatedEvent) {
		try (DAO dao = new DAO()){
			dao.executeQuery("UPDATE Event SET claim_value = ?, documents = ?, event_date = ?, event_description = ?, event_location = ?, event_receipt_date = ?, state_of_evaluation = ?, state_of_compensation = ?, user_id = ?, paid_value = ? WHERE event_id = ?",
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

	public boolean updateEvaluation(Evaluation targetEvaluation){
		try(DAO dao = new DAO()){
			dao.executeQuery("UPDATE event set state_of_evaluation = ? where event_id = ?",targetEvaluation.getResultOfEvaluation(),targetEvaluation.getEvaluationID());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean updateCompensation(Compensation targetCompensation){
		try(DAO dao = new DAO()){
			dao.executeQuery("UPDATE event set state_of_compensation = ? where evaluation_id = ?",targetCompensation.getResultOfPaid(),targetCompensation.getEvaluationID());
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public List<Event> searchCompensation(String state, String awaiting) {
		try(DAO dao = new DAO()){
			ResultSetWrapper wrapper = dao.executeQuery("SELECT * FROM event where result_of_evaluation in (?,?)",state,awaiting);
			return wrapper.toEvents();
		}catch(Exception e){
			return null;
		}
	}

	public List<Event> searchEvaluation(String state, String awaiting) {
		try(DAO dao = new DAO()){
			ResultSetWrapper wrapper = dao.executeQuery("SELECT * FROM event where result_of_compensation in (?,?)",state,awaiting);
			return wrapper.toEvents();
		}catch (Exception e){
			return null;
		}
	}

//	public ArrayList<Event> searchCompensation(String state, String awaiting){
//
//	}
}//end EventListImpl