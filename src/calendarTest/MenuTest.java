package calendarTest;

import client.*;
import database.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by oddma_000 on 08.03.2015.
 */

public class MenuTest {

    public static Calendar calendar;
    public static String username;
    public static User user;


    public static void main(String[] args) {
        Database db;

        //init(db);
        run();

    }

    public final static void clearConsole() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }


    public static void init(Database db) {

        Scanner scn = new Scanner(System.in);
        System.out.println("Wilkommen! Bitte schreiben sie Ihren Name!");
        Login login = new Login();
        User user;
        username = "";

        while (scn.hasNext()) {
            try {
                username = scn.nextLine();
                login = new Login(username);
                clearConsole();
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
                clearConsole();

                System.out.println("Welcome to our fantastic calendar, " + user.getFullName() + "\n\n\n\n");
                TimeUnit.SECONDS.sleep(2);
                clearConsole();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            } catch (InterruptedException e) {

            }
        }
        calendar = new Calendar(username, db);
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
                    clearConsole();


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
                            Appointment appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username, true);
                            appointment.addAttendant(username);

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
                                    } else if (!(Appointment.checkIfOwner(username, app, idToChange))) {
                                        bol = false;
                                    } else {
                                        System.out.println("du må være eieren av en avtale for å endre på den!! prøv igjen!!!" + idToChange);
                                    }
                                } catch (SQLException e) {
                                    System.out.println("blabla");
                                    e.printStackTrace();
                                } catch (IllegalArgumentException e) {
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
                                    "8. Logg ut\n" +
                                    "9. Sjekk deltakere");

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
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e);
                                    }
                                    continue;

                                case 123:

//                                    String newAttendant = KeyIn.inString("Hvilken deltaker vil du legge til? skriv inn username");
//                                    if (User.existsCheck(newAttendant)){
//                                        appointmentToChange.addAttendant(newAttendant);
//                                    } else {
//                                        System.out.println("brukeren eksisterer ikke!");
//                                    }
//                                    continue;

                                case 124:

                                   /* String attendantToRemove = KeyIn.inString("Hvilken deltaker vil du fjerne? skriv inn username");
                                    if (User.existsCheck(attendantToRemove)) {
                                        String sql = "DELETE from userAppointment WHERE username ='" + attendantToRemove +
                                                ", AND appointmentId =" + appointmentToChange.getAppointmentId() + ";";

                                        db.updateQuery(sql);
                                        db.closeConnection();
                                    }*/ if (true){
                                        System.out.println("brukeren eksisterer ikke!");
                                        continue;
                                    }


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

                                case 129:
                                    ArrayList<String> applist = appointmentToChange.getAttendingPeople();
                                    int index = 1;
                                    for (String usr: applist){
                                        System.out.println((index + "") + ". " + User.getUserFromDB(usr).getFullName() + "\n");
                                        index++;

                                    }
                                    continue;
                                }

                            break;

                        case 13:
                            continue;
                    }

                case 2:
                    clearConsole();

                    String groupname = KeyIn.inString("Vennligst skriv inn navnet paa gruppen du vil utforske!");
                    try {
                        int id = Group.getGroupIDFromDB(groupname);
                        Group group = Group.getGroup(id);
                       // Calendar groupCalendar = new Calendar(group);
                      //  groupCalendar.viewCalendar();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid groupname. Try again.");
                    }
                    break;

                case 3:
                    clearConsole();

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    //if (User.existsCheck(otherUser)) {
                        //Calendar otherCalendar = new Calendar(otherUser);
                        //otherCalendar.viewCalendar();
                    //} //else {
                        System.out.println("ugyldig brukernavn");
                        continue;




                case 4:
                    clearConsole();

                    System.out.println("du er innlogget som:" + username + "\n");
                    System.out.println("1. Endre Email\n" +
                            "2. Endre passord\n" +
                            "3. Tilbake\n");
                    int option = KeyIn.inInt("hvilken profildata vil du endre\n");
                    int b = Integer.valueOf((4 + "") + option);

                    switch (b) {

                        case 41:

                            String newEmail = KeyIn.inString("skriv inn ny email");
                            String sql = "UPDATE  all_s_gruppe40_calendar.user SET email='" + newEmail + "' WHERE username ='" + username + "';";
                            db.updateQuery(sql);
                            continue;

                        case 42:

                            String newPassword = KeyIn.inString("skriv inn nytt passord");
                            String sql2 = "UPDATE all_s_gruppe40_calendar.user SET password='" + newPassword + "' WHERE username ='" + username + "';";
                            db.updateQuery(sql2);
                            continue;
                    }

                    break;

                case 5:
                    clearConsole();

                    calendar.viewCalendar();
                    System.out.println("1. Gå tilbake");

                    int c = Integer.valueOf((5 + "") + KeyIn.inInt("Select option"));

                    switch (c) {

                        case 51:
                            continue;
                    }


                case 6:
                    clearConsole();
                    System.out.printf("snx m8s!!!!1!");

                    db.closeConnection();
                    loggedIn = false;

                    break;
            }
        }
    }
}







