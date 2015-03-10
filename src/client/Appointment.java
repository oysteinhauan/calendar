package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Appointment {


    Timestamp start, end;

    int size;
    ArrayList<String> attendingPeople = new ArrayList<String>();
    Collection<AppointmentListener> appointmentListeners = new ArrayList<AppointmentListener>();
    String subject;
    String description;
    Room room;
    int roomId;
    int appointmentId;
    int attendingGroup;

    public Appointment(){

    }



    public Appointment(Timestamp start, Timestamp end,
                       String subject, String description, int size) {

        this.start = start;
        this.end = end;
        this.subject = subject;
        this.description = description;
        this.size = size;



    }

    public static Appointment createAppointment(Timestamp start, Timestamp end, String subject, String description, int size) {


        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");

        try {
            Appointment appointment = new Appointment(start, end, subject, description, size);
            appointment.findRoom();
            appointment.createAppointmentInDB(appointment, db);
            return appointment;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            db.closeConnection();
        }
        return null;
    }


    @Override
    public String toString() {

        return "Appointment{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                //", room=" + room +
                ", roomId=" + roomId +
                ", appointmentId=" + appointmentId +
                '}';
    }



    public static Appointment getAppointment(int appointmentId){
        Database db = new Database();
        Appointment appointment = new Appointment();
        try {

            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "select * from appointment where appointmentId = " + appointmentId +";";
            String sql2 = "select username from userAppointment where appointmentId = " + appointmentId +";";
            ResultSet rs = db.readQuery(sql);
            ResultSet rs2 = db.readQuery(sql2);
            while (rs.next()){
                appointment.setSubject(rs.getString("subject"));
                appointment.setAppointmentId(appointmentId);
                appointment.setDescription(rs.getString("description"));
                appointment.setStart(rs.getTimestamp("start"));
                appointment.setEnd(rs.getTimestamp("end"));
                appointment.setRoomId(rs.getInt("roomId"));
            }
            rs.close();
            while (rs2.next()){
                appointment.attendingPeople.add(rs2.getString("username"));
            }
            rs2.close();
            db.closeConnection();
            return appointment;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        throw new IllegalArgumentException("Something went haywire!");


    }

    public void createAppointmentInDB(Appointment appointment, Database db) {


        //tar en appointment og legger til i databasen
        String sql = "insert into appointment (start, end, subject, description, roomId) values( '"+ String.valueOf(appointment.getStart()) + "', '" +
                String.valueOf(appointment.getEnd()) + "', '" + appointment.getSubject() + "', '"
                + appointment.getDescription() + "', "
                + (appointment.getRoomId() + "") + ");";

        System.out.println(sql);
        db.updateQuery(sql);
        db.closeConnection();


    }

    public static void removeAppointmentInDB(int appointmentID) {
        Database db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        String sql1 = "DELETE from userAppointment where appointmentId = " + appointmentID + ";";
        String sql2 = "DELETE from appointment where appointmentId = " + appointmentID + ";";
        db.updateQuery(sql1);
        db.updateQuery(sql2);
        db.closeConnection();
    }



    public void updateAppointmentInDB(String columnToUpdate, String updatedInfo){



        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");

        //sjekker om ny slutt ikke er før nåværende start eller omvendt
        if (columnToUpdate == "slutt"){

            String sql = "Select start from appointment where appointmentId ='" + this.appointmentId + "';";
            ResultSet rs = db.readQuery(sql);
            Timestamp currentStart = null;
            try {
                while (rs.next()){
                    currentStart = rs.getTimestamp("start");

            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (Timestamp.valueOf(updatedInfo).before(currentStart)){
                throw new IllegalArgumentException("sluttid må være etter starttid!!");
            }

        }

        if (columnToUpdate  == "start"){

            String sql = "Select slutt from appointment where appointmentId ='" + this.appointmentId + "';";
            ResultSet rs = db.readQuery(sql);
            Timestamp currentEnd = null;

            try {
                while (rs.next()){
                    currentEnd = rs.getTimestamp("start");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (Timestamp.valueOf(updatedInfo).after(currentEnd)){
                throw new IllegalArgumentException("Starttid må være før sluttid!!!!");
            }


        }
        String sql =  "UPDATE appointment SET " + columnToUpdate + "='" + updatedInfo + "' WHERE appointmentId = '" + this.appointmentId + "';";
        db.updateQuery(sql);
        db.closeConnection();


    }



    public void findRoom() {

        /*
        søke gjennom alle rom og avtaler for å finne ledig rom til møtet
        antar all dataen i databasen er ferdig uthentet og generert som objekter
        returnerer rommet som er best egnet plassmessig.
        */

        try {
            Database db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "select roomId, size from room where size >= " + this.size +
                    " and roomId not in (select roomId from appointment where start between '" +
                    start + "' and '" + end + "' or end between '" + start + "' and '" + end + "');";

            ResultSet rs = db.readQuery(sql);
            int actualroom = -1;
            int tempsize = 0;
            int index = 0;

            while (rs.next()) {
                if (index == 0){
                    tempsize = rs.getInt("size");
                    actualroom = rs.getInt("roomId");
                    index++;
                }
                if(rs.getInt("size") < tempsize){
                    actualroom = rs.getInt("roomId");
                }

            }
            this.roomId = actualroom;
            if(this.roomId == -1){
                throw new IllegalAccessError("No available roomz");
            }


            System.out.println(sql);
            db.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void inviteAttendant(String username){

    }


    public void setId(int id) {
        this.appointmentId = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }


    public ArrayList<String> getAttendingPeople() {
        return attendingPeople;
    }

    public void addAttendant(String username) {
        Database db = new Database();

        db.connectDb();
        String sql1 = "select count(*) as no_of_attendants from userAppointment where appointmentId = " + this.appointmentId + ";";
        String sql2 = "select username from userAppointment where username = '" + username + "' and appointmentId = " + this.appointmentId + ";";
        String sql3 = "select size from room, appointment where room.roomId = appointment.roomId" +
                " and appointmentId = " + appointmentId +";";
        ResultSet rs1 = db.readQuery(sql1);
        ResultSet rs2 = db.readQuery(sql2);
        ResultSet rs3 = db.readQuery(sql3);
        int attendants = -1;

        int roomsize = 0;
        try {
            while (rs1.next()) {
                attendants = rs1.getInt("no_of_attendants");
            }
            //rs1.close();
            if(rs2.next()){
                throw new IllegalArgumentException("User is already registered.");
            }
            while(rs3.next()) {
                if (rs3.getInt("size") <= attendants) {
                    throw new IllegalArgumentException("Room is full, you must book a new room if you wish to add attendants.");
                }
            }
            rs2.close();
            rs3.close();
            rs1.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        attendingPeople.add(username);
        db.updateQuery("insert into userAppointment values( '" + username + "', " + this.appointmentId + ");");
        db.closeConnection();
        System.out.println("User added to event.");

    }

    //NOTIFICATION



/*

    public void addAppointmentlistener(AppointmentListener appointmentListener){
        appointmentListeners.add(appointmentListener);
    }

    public void removeAppointmentListeners(AppointmentListener appointmentListener) {
        appointmentListeners.remove(appointmentListener);
    }

    public void fireAppointmentNotification(){
        for (AppointmentListener appointmentListener: appointmentListeners){
            appointmentListener.appointmentNotification(this);
        }
    }
*/

    //GET N' SET

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

    // GROUP: Metoder for å knytte grupper til avtaler

    public void addAttendingGroup(Group group) {
        Database db = new Database();
        db.connectDb();

        //ArrayList<String> members = group.getMembers(group.getGroupID());
        //int no_of_members = members.size();

        String sql1 = "select count(*) as no_of_attendants from userGroup where groupId = " + group.getGroupID() + ";";
        String sql2 = "select groupId from groupAppointment where groupId = '" + group.getGroupID() + "' and appointmentId = " + this.appointmentId + ";";
        String sql3 = "select size from room, appointment where room.roomId = appointment.roomId" +
                " and appointmentId = " + appointmentId +";";
        String sql4 = "select username from userGroup where groupId = " + group.getGroupID();
        ResultSet rs1 = db.readQuery(sql1);
        ResultSet rs2 = db.readQuery(sql2);
        ResultSet rs3 = db.readQuery(sql3);
        ResultSet rs4 = db.readQuery(sql4);


        int attendants = -1;
        boolean alreadyRegistered = false;
        int roomsize = 0;
        try {
            while (rs1.next()) {
                attendants = rs1.getInt("no_of_attendants");
            }
            if(rs2.next()){
                alreadyRegistered = true;
            }
            while(rs3.next()){
                roomsize = rs3.getInt("size");
            }
            while(rs4.next()) {
                String username = rs4.getString("username");
                addAttendant(username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {


            if (attendants >= roomsize /*|| attendingPeople.size() >= attendants*/){
                throw new IllegalArgumentException("Meeting is full or rom is too small.");
            }
            if (attendingGroup == group.getGroupID() || alreadyRegistered) {
                throw new IllegalArgumentException("Group is already partaking in this event.");

                }
            }


            //attendingPeople.add(username);
            attendingGroup = group.getGroupID();
            db.updateQuery("INSERT INTO groupAppointment (groupId, appointmentId) values (" + group.getGroupID() + ", " + this.appointmentId + ");");

            db.closeConnection();
        }



}
