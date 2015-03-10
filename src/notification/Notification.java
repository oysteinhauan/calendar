package notification;

import database.Database;

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
        sql = "INSERT INTO notification (message, type, sender, recipient, handled) VALUES('" + getMessage() +"', '"
                + getNotificationType() + "', '" + getSenderUsername() + "', '" + getRecipientUsername() + "', " + handledToString() + ");";

        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
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
