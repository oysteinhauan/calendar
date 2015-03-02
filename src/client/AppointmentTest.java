package client;

import java.util.Scanner;

/**
 * Created by oddma_000 on 27.02.2015.
 */
public class AppointmentTest {

    public static void main(String[] args) {

        //Appointment appointment = Appointment.createAppointment("2015-01-01", "12:00", 90, "test", "test");
        //appointment.createAppointmentInDB();
        //System.out.println(appointment.toString());

        System.out.println("skriv inn ny appointment:");
        Scanner sc = new Scanner(System.in);
        String date = "", time = "", subject = "", descrition = "";
        int duration = -1;
        while (sc.hasNext()){

            date = sc.next();
            time = sc.next();
            duration = sc.nextInt();
            subject = sc.next();
            descrition = sc.next();
            break;



        }

        Appointment test = Appointment.createAppointment(date, time, duration,  subject, descrition);
        test.toString();

    }

}
