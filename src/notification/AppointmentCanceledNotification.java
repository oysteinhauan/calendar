package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class AppointmentCanceledNotification extends Notification{

    public AppointmentCanceledNotification(String recieverUsername, String senderUsername, int appointmentId){
        setNotificationType();
        setAppointmentId(appointmentId);
        setRecipientUsername(recieverUsername);
        setSenderUsername(senderUsername);
        setMessage();
    }

    @Override
    public void setNotificationType() {
        notificationId = 4;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " har avlyst avtalen du skulle, eller var invitert til å, delta på: " + appointment.toString()+ ".");
    }
}
