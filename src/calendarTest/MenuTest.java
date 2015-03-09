package calendarTest;

import client.Appointment;
import client.Calendar;
import client.Login;

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

            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                            "1. Vis min kalender \n" +
                            "2. Vis min(e) gruppekalender(e)\n" +
                            "3. Se noen andres kalender\n" +
                            "4. Endre min brukerinfo\n" +
                            "5.  \n" +
                            "6. Logg ut"
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

                                    //hvor sendes brukeren nå?
                                    break;

                                case 122:

                                    String newEndTime = KeyIn.inString("skriv inn nytt tidspunkt: (YYYY-MM-DD HH:MM)" + ":00");
                                    try {
                                        appointmentToChange.updateAppointmentInDB("slutt", newEndTime);
                                    } catch (IllegalArgumentException e){
                                        System.out.println(e);
                                    }


                                    break;

                                case 123:

                                    break;

                                case 124:

                                    break;
                                case 125:

                                    break;
                                case 126:

                                    break;
                            }


                            break;

                        case 13:
                            continue;
                    }


                case 2:

                    String groupCalendar = KeyIn.inString("Hvilken gruppekalender vil du se?");

                    //må implementere en generateGroupCalendar i Calendarklasen

                    break;

                case 3:

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    Calendar otherCalendar = new Calendar(otherUser);
                    otherCalendar.viewCalendar();

                    break;

                case 4:

                    break;

                case 5:

                    break;

                case 6:


                    loggedIn = false;
                    break;



            }


        }


    }

}







