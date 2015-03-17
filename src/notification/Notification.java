package notification;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Henrik on 09.03.2015.
 */
public abstract class Notification {



    String senderUsername;
    String recipientUsername;
    int notificationId;
    int appointmentId;
    String message;
    int notificationType;
    boolean handled = false;
    Database db;
    String sql;
    String sql2;

//NOTIFICATION TYPE
//1 <=> Invite
//2 <=> ReplyFromInvitedUser
//3 <=> AppointmenUpdate
//4 <=> AttendanceCanceled

    public void createNotificationInDB(){
        this.db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO notification (message, type, sender, recipient, handled, appointmentId) VALUES('" + getMessage() +"', '"
                + getNotificationType() + "', '" + getSenderUsername() + "', '" + getRecipientUsername() + "', " + handledToString() + ", '" + getAppointmentId() + "');";

        this.db.connectDb("all_s_gruppe40", "qwerty");
        this.db.updateQuery(sql);
        this.db.closeConnection();
    }

    public void createNotificationInDB(Database db){
        //this.db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO notification (message, type, sender, recipient, handled, appointmentId) VALUES('" + getMessage() +"', '"
                + getNotificationType() + "', '" + getSenderUsername() + "', '" + getRecipientUsername() + "', " + handledToString() + ", '" + getAppointmentId() + "');";

        //this.db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        //this.db.closeConnection();
    }

    public static Notification getNotificationFromDB(int notificationId, Database db){
        //henter ut informasjonen om en varsling fra databasen, basert på varsleidentifikatoren som skrives

        //Database db = new Database();
        Notification notification = new Notification() {

            @Override
            public void setNotificationType() {

            }

            @Override
            public void setMessage() {

            }
        };
        try {
            //db = new Database("all_s_gruppe40_calendar");
            //db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "SELECT * FROM notification WHERE notificationId='" + notificationId + "';";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                notification.notificationId = notificationId;
                notification.senderUsername = rs.getString("sender");
                notification.recipientUsername = rs.getString("recipient");
                notification.appointmentId = rs.getInt("appointmentId");
                notification.message =rs.getString("message");
                notification.notificationType= rs.getInt("type");
                notification.handled = rs.getBoolean("handled");
            }
            //db.closeConnection();
            rs.close();

        } catch (SQLException e){
        }
        return notification;
    }

    public static Notification getNotificationFromDB(int notificationId){
        //henter ut informasjonen om en varsling fra databasen, basert på varsleidentifikatoren som skrives

        Database db = new Database();
        Notification notification = new Notification() {

            @Override
            public void setNotificationType() {

            }

            @Override
            public void setMessage() {

            }
        };
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "SELECT * FROM notification WHERE notificationId='" + notificationId + "';";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                notification.notificationId = notificationId;
                notification.senderUsername = rs.getString("sender");
                notification.recipientUsername = rs.getString("recipient");
                notification.appointmentId = rs.getInt("appointmentId");
                notification.message =rs.getString("message");
                notification.notificationType= rs.getInt("type");
                notification.handled = rs.getBoolean("handled");
            }
            db.closeConnection();
            rs.close();

        } catch (SQLException e){
        }
        return notification;
    }




    abstract public void setNotificationType();
    public int getNotificationType() {
        return notificationType;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    abstract public void setMessage();

    public boolean isHandled(){
        return handled;
    }

    public String getMessage() {
        return message;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void handle(Database db){
        handled = true;

        //skriv inn hvilken kolonne som skal få sin informasjon oppdatert, og hva den nye informasjonen skal være
        //db = new Database("all_s_gruppe40_calendar");
        sql = "UPDATE notification SET handled = 1 WHERE notificationId = '" + notificationId + "';";
        //db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        //db.closeConnection();
        }


    public String handledToString(){
        String handledStr = handled ? "1" : "0";
        return handledStr;
    }

    public static void editNotificationInDb(int notificationId){

    }
}
