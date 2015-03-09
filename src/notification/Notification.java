package notification;

/**
 * Created by Henrik on 09.03.2015.
 */
public abstract class Notification {

    String senderUsername;
    String recieverUsername;
    int notificationId;
    int appointmentId;
    String message;
    int notificationType;

//NOTIFICATION TYPE
//1 <=> Invite
//2 <=> ReplyFromInvitedUser
//3 <=> AppointmenUpdate
//4 <=> AttendanceCanceled

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

    public void setRecieverUsername(String recieverUsername) {
        this.recieverUsername = recieverUsername;
    }

    public String getRecieverUsername() {
        return recieverUsername;
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
}
