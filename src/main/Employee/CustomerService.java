package main.Employee;

import main.List.EventList;
import main.List.EventListImpl;

import java.util.ArrayList;

public class CustomerService extends User{
    private final EventList eventList;
    public CustomerService(String userID, String userPW, UserType userType) {
        super(userID,userPW, userType);
        this.eventList = new EventListImpl();
    }
    public boolean createEvent(Event event) {
        return eventList.insert(event);
    }
    public boolean searchEvent(){
        return false;
    }
    public boolean updateEvent(){
        return false;
    }
    public boolean deleteEvent(){
        return false;
    }

    public boolean searchPremium(){
        return false;
    }
    public boolean payPremium(){
        return false;
    }
}
