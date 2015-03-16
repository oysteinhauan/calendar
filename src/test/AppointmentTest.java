package test;

import client.Appointment;
import client.Group;

import java.sql.Timestamp;
import java.util.Scanner;

/**
 * Created by oddma_000 on 27.02.2015.
 */
public class AppointmentTest {

    public static void main(String[] args) {

        //kjrer et enkelt program  i consollen som lar deg legge til en ny appointment.

        /*System.out.println("skriv inn ny appointment:");
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




        Appointment test = Appointment.createAppointment(start, end, subject, description, 20);
        test.toString();*/

        Appointment test = new Appointment();


        //test.addAttendant("henloef");
        //test.addAttendant("oddmrog");
        //test.addAttendant("tuvaeri");
        Appointment a = test.getAppointment(2);
        System.out.println(a);

//        Group g = Group.getGroup(3);
//        Appointment a = Appointment.getAppointment(7);
//
//        a.addAttendingGroup(g);

        //a.addAttendingGroup(g);


        //test.addAttendant("tuvaeri");

        //Appointment app = Appointment.createAppointment(Timestamp.valueOf("1122-12-12 12:12:00"), Timestamp.valueOf("1133-12-12 12:12:00"), "asfsadf", "asfsdaf", 16, "oysteibh");
        //System.out.println(app.getAppointmentId());
        //app.addAttendant("tuvaeri");







    }

}
