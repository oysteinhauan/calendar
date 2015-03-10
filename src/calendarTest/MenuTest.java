package calendarTest;

import client.*;
import database.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;


/**
 * Created by oddma_000 on 08.03.2015.
 */

public class MenuTest {

    public static Calendar calendar;
    public static String username;
    public static User user;


    public static void main(String[] args) {

        init();
        run();

    }


    public static void init() {

        Scanner scn = new Scanner(System.in);
        System.out.println("Wilkommen! Bitte schreiben sie Ihren Name!");
        Login login = new Login();
        User user;
        username = "";

        while (scn.hasNext()) {
            try{
                username = scn.nextLine();
                login = new Login(username);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("\nInvalid usrname. Try again plz\n\n");
            }
        }
        System.out.println("\nPlease give me ur passwd!");

        while (scn.hasNext()) {
            String password = scn.next();
            try {
                login.login(password);
                user = User.getUserFromDB(username);
                System.out.println("Welcome to our fantastic calendar, " + user.getFullName());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            }
        }
        calendar = new Calendar(username);
    }

    public static void run() {

        boolean loggedIn = true;

        while (loggedIn) {

            int swValue;
            Database db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");
            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                            "1. opprett / endre avtaler \n" +
                            "2. Vis en gruppekalender\n" +
                            "3. Se en annens private kalender\n" +
                            "4. Endre min brukerinfo\n" +
                            "5. Vis min personlige kalender\n" +
                            "6. Logg ut\n\n"
            );

            swValue = KeyIn.inInt(" Select Option \n");
            Timestamp start, slutt;
            String subject, description;
            int antall;

            switch (swValue) {
                case 1:


                    System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +
                                    "1. Opprett ny avtale \n" +
                                    "2. Endre eksisterende avtale\n" +
                                    "3. Gå tilbake\n\n\n"
                    );
                    swValue = Integer.valueOf((1 + "") + KeyIn.inInt(" Select Option \n"));
                    switch (swValue) {

                        case 11:

                            start = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM)")) + ":00");
                            slutt = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM)")) + ":00");
                            subject = KeyIn.inString("Legg inn subject:");
                            description = KeyIn.inString("Legg inn description:");
                            antall = KeyIn.inInt("Legg inn antall møtedeltakere");
                            System.out.println(username);
                            Appointment appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username);

                            while (appointment.attendingPeople.size() < antall) {

                                String bruker = KeyIn.inString("skriv inn username");
                                appointment.addAttendant(bruker);
                            }
                            break;

                        case 12:

                            boolean bol = true;
                            int idToChange = -1;
                            while (bol) {
                                try {
                                    idToChange = KeyIn.inInt("Skriv inn avtaleID:\n");
                                    Appointment app = Appointment.getAppointment(idToChange);
                                    if (!app.hasRecord(idToChange)) {
                                        System.out.println("id'en finnes ikke, prøv igjen!!");
                                    }
                                    else if (!(Appointment.checkIfOwner(username, app, idToChange))) {
                                        bol = false;
                                    }
                                    else {
                                        System.out.println("du må være eieren av en avtale for å endre på den!! prøv igjen!!!" + idToChange);
                                    }
                                } catch (SQLException e) {
                                    System.out.println("blabla");
                                    e.printStackTrace();
                                } catch (IllegalArgumentException e){
                                    System.out.println("dritt");
                                    e.printStackTrace();
                                }
                            }

                            System.out.println("1. Endre starttid\n" +
                                    "2. Endre sluttid\n" +
                                    "3. legg til deltaker\n" +
                                    "4. fjern deltaker\n" +
                                    "5. endre description\n" +
                                    "6. endre rom\n" +
                                    "7. gå tilbake\n" +
                                    "8. Logg ut\n");

                            int a = Integer.valueOf((12 + "") + KeyIn.inInt("Select option\n"));
                            Appointment appointmentToChange = Appointment.getAppointment(idToChange);

                            switch (a) {

                                case 121:

                                    String newStartTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                                    appointmentToChange.updateAppointmentInDB("start", newStartTime);
                                    continue;

                                case 122:

                                    String newEndTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                                    try {
                                        appointmentToChange.updateAppointmentInDB("slutt", newEndTime);
                                    } catch (IllegalArgumentException e){
                                        System.out.println(e);
                                    }
                                    continue;

                                case 123:

                                    String newAttendant = KeyIn.inString("Hvilken deltaker vil du legge til? skriv inn username");
                                    appointmentToChange.addAttendant(newAttendant);
                                    continue;

                                case 124:

                                    String attendantToRemove = KeyIn.inString("Hvilken deltaker vil du fjerne? skriv inn username");
                                    String sql = "DELETE from userAppointment WHERE username ='" + attendantToRemove +
                                            ", AND appointmentId =" + appointmentToChange.getAppointmentId() + ";";
                                    db.updateQuery(sql);
                                    db.closeConnection();
                                    continue;

                                case 125:

                                    String newDescription = KeyIn.inString("Skriv inn ny description:");
                                    appointmentToChange.updateAppointmentInDB("description", newDescription);
                                    continue;

                                case 126:

                                    System.out.println("denne funksjonen er ikke implementert ennuuu");
                                    continue;

                                case 127:

                                    continue;

                                case 128:
                                    break;
                            }

                            break;

                        case 13:
                            continue;
                    }

                case 2:

                    break;

                case 3:

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    Calendar otherCalendar = new Calendar(otherUser);
                    otherCalendar.viewCalendar();

                    break;

                case 4:

                    System.out.println("du er innlogget som:" + username + "\n");
                    System.out.println("1. Endre Email\n" +
                            "2. Endre passord\n" +
                            "3. Tilbake\n");
                    int option = KeyIn.inInt("hvilken profildata vil du endre\n");
                    int b = Integer.valueOf((4 + "") + option);

                    switch (b){

                        case 41:

                            String newEmail = KeyIn.inString("skriv inn ny email");
                            String sql = "UPDATE  all_s_gruppe40_calendar.user SET email='" + newEmail + "WHERE username ='" + username + "';";
                            db.updateQuery(sql);
                            continue;

                        case 42:

                            String newPassword = KeyIn.inString("skriv inn nytt passord");
                            String sql2 = "UPDATE all_s_gruppe40_calendar.user SET password='" + newPassword + "WHERE username ='" + username + "';";
                            db.updateQuery(sql2);
                            continue;
                    }

                    break;

                case 5:

                    calendar.viewCalendar();
                    System.out.println("1. Gå tilbake");

                    int c = Integer.valueOf((5 + "") + KeyIn.inInt("Select option"));

                    switch (c){

                        case 51:
                            continue;
                    }


                case 6:

                    db.closeConnection();
                    loggedIn = false;
                    break;
            }
        }
    }
}







