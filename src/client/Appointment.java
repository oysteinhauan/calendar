package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Appointment {

    String date;
    String time;
    int duration;
    //varighet i minutter
    ArrayList<User> attendingPeople;
    String subject;
    String description;
    Room room;
    int roomId;
    int appointmentId;



    public Appointment(String date, String time, int duration,
                       String subject, String description) {

        this.date = date;
        this.time = time;
        this.duration = duration;
        this.subject = subject;
        this.description = description;
        this.roomId = 1;


    }

    public static Appointment createAppointment(String date, String time, int duration, String subject, String description) {


        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");

        try {
            Appointment appointment = new Appointment(date, time, duration, subject, description);
            String sql = "SELECT max(appointmentId) FROM appointment";
            ResultSet rs = db.readQuery(sql);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("max(appointmentId)") + 1;
            }

            if (id == -1) {
                throw new IllegalArgumentException("fuck up fra DB ID");
            }

            appointment.setId(id);
            appointment.createAppointmentInDB(appointment, db);
            return appointment;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            db.closeConnection();
        }
        return null;
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

    public void createAppointmentInDB(Appointment appointment, Database db) {


        //tar en appointment og legger til i databasen


        String sql = "insert into appointment values(" + (appointment.getAppointmentId() + "") + ", '" + appointment.getDate() + "', " +
                (appointment.getDuration() + "") + ", '" + appointment.getSubject() + "', '" + appointment.getDescription() + "', "
                + (appointment.getRoomId() + "") + ", '" + getTime() + "');";

        System.out.println(sql);
        db.updateQuery(sql);
        db.closeConnection();


    }

    public void findRoom() {

        /*
        søke gjennom alle rom og avtaler for å finne ledig rom til møtet
        antar all dataen i databasen er ferdig uthentet og generert som objekter
        returnerer en liste med alle ledige rom
        */

        try {
            Database db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql2 = "";
            String sql = "select * from room where size >=" + attendingPeople.size() + "and roomId not in (select roomId from roomOccupation)" +
                    "and   ;";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()) {

                int roomId = rs.getInt("roomId");
                int roomSize = rs.getInt("roomSize");
                String roomName = rs.getString("roomName");

                System.out.println(
                        "room id=" + roomId + "\n" +
                                "room size=" + roomSize + "\n" +
                                "room name=" + roomName + "\n\n\n"
                );


            }

            System.out.println(sql);
            db.updateQuery(sql);
            db.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void setId(int id) {
        this.appointmentId = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public ArrayList<User> getAttendingPeople() {
        return attendingPeople;
    }

    public void addAttendant(User attendant) {
        //Må være mer sjekk før man kan legge til folk
        attendingPeople.add(attendant);
    }

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

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

}
