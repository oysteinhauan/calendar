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

    String date;
    String time;
    int duration;
    //ArrayList<Person> attendingPeople;
    String subject;
    String description;
    //Room room;
    int roomId;
    int appointmentId;


    public Appointment(int id, String date, String time, int duration,
                       String subject, String description, int roomId) {

        this.date = date;
        this.time = time;
        this.duration = duration;
        //this.attendingPeople = attendingPeople;
        this.subject = subject;
        this.description = description;
        //this.room = room;
        this.roomId = roomId;
        this.appointmentId = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

  /* public ArrayList<Person> getAttendingPeople() {
        return attendingPeople;
    }

    public void setAttendingPeople(ArrayList<Person> attendingPeople) {
        this.attendingPeople = attendingPeople;
    }*/

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   /* public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }*/

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration=" + duration +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                //", room=" + room +
                ", roomId=" + roomId +
                ", appointmentId=" + appointmentId +
                '}';
    }

    public void createAppointmentInDB(){


        //tar en appointment og legger til i databasen



        Database db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");


        String sql = "insert into appointment values(" + (getAppointmentId() + "") + ", '"  + getDate() + "', " +
                (getDuration() + "") + ", '" + getSubject() + "', '" + getDescription() + "', " + (getRoomId() + "") + ", '" + getTime() +"');";
        System.out.println(sql);
        db.updateQuery(sql);




    }

}

