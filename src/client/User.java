package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by oysteinhauan on 24/02/15.
 */
public class User implements AppointmentListener{

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String position;
    database.Database db;
    String sql;
    private ArrayList<Appointment> appointmentNotifications = new ArrayList<Appointment>();

    public User(){

    }

    public User(String username, String password, String firstname,
                String lastname, String email, String position) {
        setUsername(username);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPosition(position);

    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                '}';
    }


    public void addUserToDB(){
        db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO user VALUES( '" + getUsername() + "', '" + getPassword() + "', '" + getFirstname() + "', '"
                + getLastname() + "', '" + getPosition() + "', '" + getEmail() + "');";

        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
    }

    public ArrayList<Appointment> getAppointmentsForUser(User user){

        try {
            ArrayList<Integer> appIdList = new ArrayList<Integer>();
            ArrayList<Appointment> appList = new ArrayList<Appointment>();

            Database db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "select appointment.appointmentId, start from userAppointment, appointment " +
                    "where username = '" + user.getUsername() + "' and appointment.appointmentId = userAppointment.appointmentId " +
                            "order by start;";
            ResultSet rs = db.readQuery(sql);
            while (rs.next()) {
                appIdList.add(rs.getInt("appointmentId"));
            }
            db.closeConnection();

            for (Integer id: appIdList){
                appList.add(Appointment.getAppointment(id));
            }
            return appList;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public User getUserFromDB(String username){
        //henter ut informasjonen om en bruker fra databasen, basert på burkernavnet som skrives
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = "SELECT * FROM user WHERE username='" + username + "';";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                setUsername(username);
                this.firstname = rs.getString("firstname");
                this.lastname = rs.getString("lastname");
                this.position = rs.getString("position");
                this.email =rs.getString("email");
            }
            db.closeConnection();
            rs.close();

        } catch (SQLException e){
        }
        return this;
    }

    public static boolean usernameTaken(String username){
        Database db = new Database();
        db.connectDb();
        String sql = "select username from user where username = '" + username +"';";
        ResultSet rs = db.readQuery(sql);
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            db.closeConnection();
        }
        return false;

    }

    public void updateUserInfoInDB(String columnToUpdate, String updatedInfo){
        //skriv inn hvilken kolonne som skal få sin informasjon oppdatert, og hva den nye informasjonen skal være
        db = new Database("all_s_gruppe40_calendar");
        sql = "UPDATE user SET " + columnToUpdate + "='" + updatedInfo + "' WHERE username = '" + username + "';";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName(){
        return "" + getFirstname() + " " + getLastname();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }





    public void deleteUserFromDb(String username) {
        db = new Database("all_s_gruppe40_calendar");
        sql = "DELETE FROM user WHERE username='" + username + "';";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
    }

//NOTIFICATION
    @Override
    public void appointmentNotification(Appointment appointment) {
        appointmentNotifications.add(appointment);
    }

    public int getNumberOfNotifications(){
        return appointmentNotifications.size();
    }

    public void removeAppointmentNotification(Appointment appointment){
        appointmentNotifications.remove(appointment);
    }

    public boolean replyToNotification(Appointment appointment){
        Scanner sc = new Scanner(System.in);
        String appointmentInfo = appointment.toString();
        char replyFromUser;
        boolean replied = false;
        Boolean replyToAppointer = null;
        do{
            System.out.println("You have bee invited to attend the appointment" + appointmentInfo + "." );
            System.out.println("RSVP (y/n)");
            replyFromUser = sc.findInLine(".").charAt(0);

            switch (replyFromUser){
                case 'y':
                    replied = true;
                    replyToAppointer = true;
                    break;
                case 'n':
                    replied = true;
                    replyToAppointer = false;
                    break;
                default:
                    System.out.println("Invalid answer, please reply 'y' or 'n'.");
            }
        }  while (!replied);
        removeAppointmentNotification(appointment);
        return replyToAppointer;
    }
}
