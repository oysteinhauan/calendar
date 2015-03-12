package notification;

import client.User;
import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO notification (message, type, sender, recipient, handled, appointmentId) VALUES('" + getMessage() +"', '"
                + getNotificationType() + "', '" + getSenderUsername() + "', '" + getRecipientUsername() + "', " + handledToString() + ", '" +getAppointmentId()+ "');";

        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
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
                notification.senderUsername = ("sender");
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

    public ArrayList<Notification> getNotificationsForUser(User user){
        try{
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            ArrayList<Integer> notificationIds = new ArrayList<Integer>();

            Database db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "SELECT notificationId FROM notification" +
                    "WHERE recipient = '" + user.getUsername() + "';";
            ResultSet rs = db.readQuery(sql);
            while (rs.next()) {
                notificationIds.add(rs.getInt("notificationId"));
            }
            db.closeConnection();

            for (Integer id: notificationIds){
                notifications.add(Notification.getNotificationFromDB(id));
            }
            return notifications;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public String getMessage() {
        return message;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void handle(){
        handled = true;
    }

    public String handledToString(){
        String handledStr = handled ? "1" : "0";
        return handledStr;
    }
}
