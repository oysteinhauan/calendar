package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class AppointmentUpdateNotification extends Notification {

    public AppointmentUpdateNotification(String senderUsername, String recieverUsername, int appointmentId){
        setNotificationType();
        setAppointmentId(appointmentId);
        setRecipientUsername(recieverUsername);
        setSenderUsername(senderUsername);
        setMessage();
    }

    @Override
    public void setNotificationType() {
        notificationType = 3;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " has updated the appointment: " + appointment.toString() + "");
    }
}
