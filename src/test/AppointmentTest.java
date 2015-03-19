package test;

import client.Appointment;
import client.Group;
import database.Database;
import main.CalendarProgram;

import java.sql.Timestamp;
import java.util.Scanner;

/**
 * Created by oddma_000 on 27.02.2015.
 */
public class AppointmentTest {

    public static void main(String[] args) {

       String timeStamp = "2015-10-12 22:00:00";
        System.out.println(CalendarProgram.isTimeStampValid(timeStamp));

    }


}
