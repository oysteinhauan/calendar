package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class InviteNotification extends Notification {

    public InviteNotification(String recieverUsername, String senderUsername, int appointmentId ){
        setNotificationType();
        setSenderUsername(senderUsername);
        setRecipientUsername(recieverUsername);
        setAppointmentId(appointmentId);
        setMessage();
    }

    @Override
    public void setNotificationType() {
        notificationType = 1;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " har invitert deg til å delta på: " + appointment.toString() + "");
    }
}
