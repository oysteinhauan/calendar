package calendarTest;

import client.Appointment;
import client.Calendar;
import client.Group;
import client.Login;
import database.Database;

import java.sql.Timestamp;
import java.util.ArrayList;


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


            username = KeyIn.inString("Skriv inn brukernavn\n\n");

            try {

                Login login = new Login(username);
                String password = KeyIn.inString("skriv inn passord\n\n");
                login.login(password);



            } catch (IllegalArgumentException e) {
                System.out.println("ugyldig brukernavn eller passord, prøv på nytt\n\n");
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

            swValue = KeyIn.inInt(" Select Option ");


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

                    swValue = Integer.valueOf((1 + "") + KeyIn.inInt(" Select Option "));


                    switch (swValue) {

                        case 11:


                            start = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM")) + ":00");


                            slutt = Timestamp.valueOf((KeyIn.inString("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM")) + ":00");


                            subject = KeyIn.inString("Legg inn subject:");


                            description = KeyIn.inString("Legg inn description:");


                            antall = KeyIn.inInt("Legg inn antall møtedeltakere");


                            Appointment appointment = Appointment.createAppointment(start, slutt, subject, description, antall);

                            while (attendants.size() < antall) {

                                String bruker = KeyIn.inString("skriv in username");
                                appointment.addAttendant(bruker);

                                // FIKS addAttendant
                                attendants.add(bruker);

                            }
                            //blabla generer appointment fra denne inputen

                            //legges i en liste med usernames
                            //kall thisAppointment.addAttendent() for hver bruker

                            break;

                        case 12:

                            int idToChange = KeyIn.inInt("Skriv inn avtaleID:");

                            System.out.println("1. Endre starttid" +
                                    "2. Endre sluttid" +
                                    "3. legg til deltaker" +
                                    "4. fjern deltaker" +
                                    "5. endre description" +
                                    "6. endre rom" +
                                    "7. gå tilbake" +
                                    "8. Logg ut");

                            int a = Integer.valueOf((12 + "") + KeyIn.inInt("Select option"));

                            Appointment appointmentToChange = Appointment.getAppointment(idToChange);


                            switch (a) {

                                case 121:

                                    String newStartTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)" + ":00");
                                    appointmentToChange.updateAppointmentInDB("start", newStartTime);


                                    continue;

                                case 122:

                                    String newEndTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)" + ":00");
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







