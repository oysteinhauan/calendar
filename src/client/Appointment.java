package client;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Appointment {

    Date dateAndTime;
    int duration;
    //varighet i minutter
    ArrayList<Person> attendingPeople;
    String subject;
    String description;
    Room room;

    public Appointment() {

    }

    public void setDateAndTime(int year, int month, int date, int hrs, int min){
        //Må sjekke om det er ledig i møterom osv. først

        this.dateAndTime.setYear(year);
        this.dateAndTime.setMonth(month);
        this.dateAndTime.setDate(date);
        this.dateAndTime.setHours(hrs);
        this.dateAndTime.setMinutes(min);

    }

    public Date getDateAndTime(){
        return this.dateAndTime;
    }

    public void setDuration(int duration){
        //Må sjekke om det er ledig i møterom osv. først

        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public void addAttendant(Person attendant){
        //Må være mer sjekk før man kan legge til folk


        attendingPeople.add(attendant);
    }

    public ArrayList getAttendants(){
        return attendingPeople;
    }

    public Room findRoom(){

        return room;
    }
}
