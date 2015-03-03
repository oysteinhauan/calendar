package client;

import java.sql.Timestamp;
import java.util.Scanner;

/**
 * Created by oddma_000 on 27.02.2015.
 */
public class AppointmentTest {

    public static void main(String[] args) {

        //kjører et enkelt program  i consollen som lar deg legge til en ny appointment.

        System.out.println("skriv inn ny appointment:");
        Scanner sc = new Scanner(System.in);
        String subject = "", description = "";
        Timestamp start = null, end = null;

        int duration = -1;
        while (sc.hasNext()){

            start = Timestamp.valueOf(sc.nextLine());
            end = Timestamp.valueOf(sc.nextLine());

            subject = sc.nextLine();
            description = sc.nextLine();
            break;



        }

        Appointment test = Appointment.createAppointment(start, end, subject, description, 6);
        test.toString();

    }

}
