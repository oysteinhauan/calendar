package main;

import calendarTest.KeyIn;
import client.Appointment;
import client.Calendar;
import client.Login;
import client.User;
import database.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by oysteinhauan on 10/03/15.
 */
public class CalendarProgram {

    public static Calendar calendar;
    public static String username;
    public static User user;

    public void init(){

        Scanner scn = new Scanner(System.in);
        System.out.println("Wilkommen! Bitte schreiben sie Ihren Name!");
        Login login = new Login();
        User user;
        username = "";

        while (scn.hasNext()) {
            try {
                username = scn.nextLine();
                login = new Login(username);
                //clearConsole();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid usrname. Try again plz\n\n");
            }
        }

        System.out.println("\nPlease give me ur passwd!");

        while (scn.hasNext()) {
            String password = scn.next();
            try {
                login.login(password);
                user = User.getUserFromDB(username);
                //clearConsole();
                System.out.println("Welcome to our fantastic calendar, " + user.getFullName() + "\n\n\n\n");
                TimeUnit.SECONDS.sleep(2);
                //clearConsole();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            } catch (InterruptedException e) {
            }
        }
        calendar = new Calendar(username);
    }

    public void run(){

    }

    public static void main(String[] args) {
        CalendarProgram cp = new CalendarProgram();
        cp.init();
        cp.run();
    }

    public static void createAppointment(String username){
        Timestamp start = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM)")) + ":00");
        Timestamp slutt = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM)")) + ":00");
        String subject = KeyIn.inString("Legg inn subject:");
        String description = KeyIn.inString("Legg inn description:");
        int antall = KeyIn.inInt("Legg inn antall møtedeltakere");
        System.out.println(username);
        Appointment appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username);
        appointment.addAttendant(username);

        while (appointment.attendingPeople.size() < antall) {

            String bruker = KeyIn.inString("skriv inn username");
            appointment.addAttendant(bruker);
        }
    }

    public void editAppointment(String username) {


        boolean bol = true;
        int idToChange = -1;

        while (bol) {
            try {
                idToChange = KeyIn.inInt("Skriv inn avtaleID:\n");
                Appointment app = Appointment.getAppointment(idToChange);
                if (!app.hasRecord(idToChange)) {
                    System.out.println("id'en finnes ikke, prøv igjen!!");
                } else if (!(Appointment.checkIfOwner(username, app, idToChange))) {
                    throw new IllegalArgumentException("Du må være eieren for å endre.");
                } else {
                    System.out.println("du må være eieren av en avtale for å endre på den!! prøv igjen!!!" + idToChange);
                }
            } catch (SQLException e) {
                System.out.println("blabla");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Du må være eieren for å kunne endre avtalen");
                e.printStackTrace();
                return;
            }
        }

        Appointment appointmentToChange = Appointment.getAppointment(idToChange);

        boolean stay = true;

        while (stay) {
            System.out.println("1. Endre starttid\n" +
                    "2. Endre sluttid\n" +
                    "3. legg til deltaker\n" +
                    "4. fjern deltaker\n" +
                    "5. endre description\n" +
                    "6. endre rom\n" +
                    "7. gå tilbake\n" +
                    "8. Sjekk deltakere\n" +
                    "9. Slett event" +
                    "10 Logg ut");

            int value = KeyIn.inInt("Select option.\n ");
            switch (value) {
                case 1:
                    String newStartTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                    appointmentToChange.updateAppointmentInDB("start", newStartTime);
                    continue;

                case 2:

                    String newEndTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                    try {
                        appointmentToChange.updateAppointmentInDB("slutt", newEndTime);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }continue;
                case 3:
                    String newAttendant = KeyIn.inString("Hvilken deltaker vil du legge til? skriv inn username");
                    appointmentToChange.addAttendant(newAttendant);
                    continue;
                case 4:
                    String attendantToRemove = KeyIn.inString("Hvilken deltaker vil du fjerne? skriv inn username");
                    appointmentToChange.removeAttendant(attendantToRemove);
                    continue;

                case 5:
                    String newDescription = KeyIn.inString("Skriv inn ny description:");
                    appointmentToChange.updateAppointmentInDB("description", newDescription);
                    continue;
                case 6:
                    //endre romstr
                case 7:
                    continue;
                case 8:
                    ArrayList<String> applist = appointmentToChange.getAttendingPeople();
                    int index = 1;
                    for (String usr: applist){
                        System.out.println((index + "") + ". " + User.getUserFromDB(usr).getFullName() + "\n");
                        index++;
                    }
                    continue;
                case 9:
                    //slett event
                    Appointment.removeAppointmentInDB(appointmentToChange.getAppointmentId());
                    break;
                case 10:
                    break;

            }
        }
    }

    public void appNav(String username){

        int value = KeyIn.inInt("Select option. \n ");

        switch (value){
            case 1:
                createAppointment(username);
            case 2:
                editAppointment(username);
            case 3:
                break;
            default:

        }

    }

    public void editUserInfo(String username){

        Database db = new Database();
        db.connectDb();

        while (true) {
            System.out.println("du er innlogget som:" + username + "\n");
            System.out.println("1. Endre Email\n" +
                    "2. Endre passord\n" +
                    "3. Tilbake\n");
            int option = KeyIn.inInt("hvilken profildata vil du endre\n");

            switch (option) {

                case 1:
                    String newEmail = KeyIn.inString("skriv inn ny email");
                    String sql = "UPDATE  all_s_gruppe40_calendar.user SET email='" + newEmail + "' WHERE username ='" + username + "';";
                    db.updateQuery(sql);
                    continue;
                case 2:
                    String newPassword = KeyIn.inString("skriv inn nytt passord");
                    String sql2 = "UPDATE all_s_gruppe40_calendar.user SET password='" + newPassword + "' WHERE username ='" + username + "';";
                    db.updateQuery(sql2);
                    continue;
                case 3:
                    break;
                default:
                    System.out.println("skriv et gyldig valg!!");
                    continue;
            }
        }
    }
}
