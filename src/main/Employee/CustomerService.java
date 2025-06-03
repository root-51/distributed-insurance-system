package main.Employee;

import main.Data.Event;
import main.List.EventList;
import main.List.EventListImpl;

import java.util.ArrayList;

public class CustomerService extends User {
    private final EventList eventList;
    public CustomerService(String userID, String userPW, UserType userType) {
        super(userID,userPW, userType);
        this.eventList = new EventListImpl();
    }
    public boolean createEvent(Event event) {
        return eventList.insert(event);
    }
    public ArrayList<Event> getMyEvents(String userID) {
        try{ return eventList.searchEvent("user_id", userID); }
        catch (Exception e) { return null; }

    }
    public Event getMyEvent(String eventID) {
        try{ return eventList.searchEvent("event_id", eventID).get(0);}
        catch (Exception e) { return null; }
    }
    public boolean updateEvent(Event updatedEvent) {
        return eventList.update(updatedEvent);
    }
    public boolean deleteEvent(String eventID) {
        return eventList.delete(eventID);
    }

    public boolean searchPremium() {
        return false;
    }

    public boolean payPremium() {
        return false;
    }
}
