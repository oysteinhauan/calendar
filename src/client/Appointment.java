package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Appointment {

    java.sql.Date date;
    java.sql.Time time;
    int duration;
    //varighet i minutter
    ArrayList<Person> attendingPeople;
    String subject;
    String description;
    Room room;
    int roomId;
    int appointmentId;


    public Appointment(int id, java.sql.Date date, Time time, int duration, ArrayList<Person> attendingPeople, String subject, String description, Room room, int roomId) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.attendingPeople = attendingPeople;
        this.subject = subject;
        this.description = description;
        this.room = room;
        this.roomId = roomId;
        this.appointmentId = id;
    }

    public void setDateAndTime(int year, int month, int date, int hrs, int min){
        //Må sjekke om det er ledig i møterom osv. først

        this.dateAndTime.setYear(year);
        this.dateAndTime.setMonth(month);
        this.dateAndTime.setDate(date);
        this.dateAndTime.setHours(hrs);
        this.dateAndTime.setMinutes(min);

    }

    public Date getDate(){

        return this.dateAndTime;
    }

    public void setDuration(int duration){
        //Må sjekke om det er ledig i møterom osv. først

        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getId(){

        return appointmentId;
    }

    public void addAttendant(Person attendant){
        //Må være mer sjekk før man kan legge til folk


        attendingPeople.add(attendant);
    }

    public ArrayList getAttendants(){

        return attendingPeople;
    }

    public int findRoomId(){

        return roomId;
    }

    public void createAppointmentInDB(Appointment appointment){


        //tar en appointment og legger til i databasen hvis den ikke finnes fra før.

        try {
            String sql = "insert into appointment values(%d, D%, %d, %s "  + (appointment.getId() + "") + ";";

            Database db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                date = rs.getDate("date");
                time = rs.getTime("time");
                duration = rs.getInt("duration");
                subject = rs.getString("subject");
                description = rs.getString("description");
                roomId = rs.getInt("roomId");


            }

            String userIds = "select username from userAppointment where appointmentId =" + (appointmentId + "") + ";";
            ResultSet rs2 = db.readQuery(userIds);
            while (rs2.next()){

                String username = rs2.getString("username");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

