package calendarTest;

import client.*;
import database.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by oddma_000 on 08.03.2015.
 */

public class MenuTest {

    public static Calendar calendar;
    public static String username;


    public static void main(String[] args) {

        init();
        run();
        //koble til db
        //lage et menysystem, kjøres fra cmd (kjøre main filen?)
        ////lage nye bruker eller logge inn
        //valg for funksjonalitet som er implementert

    }


    public static void init() {

        //rett etter loggin må en vise om vedkommende har nye notifikasjoner
        // svare nå eller senere



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

                            "1. Vis min kalender og opprett / endre avtaler \n" +
                            "2. Vis en gruppekalender\n" +
                            "3. Se en annens private kalender\n" +
                            "4. Endre min brukerinfo\n" +
                            "5.  \n" +
                            "6. Logg ut\n\n"
            );

            swValue = KeyIn.inInt(" Select Option \n");


            Timestamp start, slutt;
            String subject, description;
            ArrayList<String> attendants = new ArrayList<String>();
            int antall;

            switch (swValue) {

                case 1:



                    calendar.viewCalendar();

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



                            Appointment appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username);

                            while (attendants.size() < antall) {

                                String bruker = KeyIn.inString("skriv inn username");
                                appointment.addAttendant(bruker);

                                // FIKS addAttendant
                                attendants.add(bruker);

                            }
                            //blabla generer appointment fra denne inputen

                            //legges i en liste med usernames
                            //kall thisAppointment.addAttendent() for hver bruker

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


                            // her må det være en sjekk for at kun den som har opprettet avtalen kan endre den.

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
                                    //remove tuple from userAppointment



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

                    /*int groupId = KeyIn.inInt("Hvilken gruppekalender vil du se? skriv inn gruppeId");
                    Group group = Group.getGroup(groupId);

                    Calendar calendar = new Calendar(group);
                    calendar.viewCalendar();*/


                    break;

                case 3:

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    Calendar otherCalendar = new Calendar(otherUser);
                    otherCalendar.viewCalendar();

                    break;

                case 4:
                    //endre brukerinfo
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

                    //hva skjer her

                    break;

                case 6:

                    db.closeConnection();
                    loggedIn = false;
                    break;



            }


        }


    }

}







