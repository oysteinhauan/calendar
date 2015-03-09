package client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by oddma_000 on 03.03.2015.
 */
public class CalendarMain {

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


    public static void init(){

        //rett etter loggin må en vise om vedkommende har nye notifikasjoner
        // svare nå eller senere
        //vise kalnder i mellomtid?
        Scanner sc = new Scanner(System.in);

        username = "";


        while (sc.hasNext()) {

            System.out.println("Skriv inn brukernavn:");
            try {

                String user = sc.next();
                Login login = new Login(user);
                System.out.println("Skriv inn passord:");
                String pw = sc.next();
                login.login(pw);
                username = user;


            } catch (IllegalArgumentException e) {
                System.out.println("ugyldig brukernavn eller passord, prøv på nytt");
            }

            break;

        }
        //her blir alle appointments generert som objekter fra databasen
        calendar = new Calendar(username);

        // generer gruppekalendere(n)
        // vise og svare på notifikasjoner.
    }

    public static void run(){

            Scanner sc = new Scanner(System.in);

            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                    "1. Vis min kalender \n" +
                    "2. Vis min(e) gruppekalender(e)\n" +
                    "3. Se noen andres kalender\n" +
                    "4. Endre min brukerinfo\n" +
                    "5.  avtale\n" +
                    "6. Logg ut"
            );

            while(sc.hasNext()){
                int choice1 = sc.nextInt();

                Timestamp start = null, slutt = null;
                String subject = "", description = "";
                ArrayList<String> attendants = null;
                int antall = -1;

                switch (choice1) {

                    case 1:


                        calendar.viewCalendar();

                        System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                                "1. Opprett ny avtale \n" +
                                "2. Endre eksisterende avtale\n" +
                                "3. Gå tilbake"

                        );

                        int choice2 = Integer.valueOf((1 + "") + (sc.nextInt() + "") );

                        switch ( choice2 ) {

                            case 11:

                                System.out.println("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM:SS)");
                                start = Timestamp.valueOf(sc.nextLine());

                                System.out.println("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM:SS)");
                                slutt = Timestamp.valueOf(sc.nextLine());

                                System.out.println("Legg inn avtaleinformasjon: subject");
                                subject = sc.nextLine();

                                System.out.println("Legg inn avtaleinformasjon: description");
                                description = sc.nextLine();

                                System.out.println("Legg inn avtaleinformasjon: antall møtedeltakere");
                                antall = Integer.valueOf(sc.nextLine());

                                System.out.println("Legg inn møtedeltagere: brukernavn1 brukernavn2 osv..");

                                //attendants = new ArrayList<String>();
                                //attendants.add(sc.next());

                                Appointment appointment = Appointment.createAppointment(start,slutt, subject, description, antall);
                                appointment.addAttendant(sc.next());


                                //blabla generer appointment fra denne inputen

                                //legges i en liste med usernames
                                //kall thisAppointment.addAttendent() for hver bruker

                                break;

                            case 12:


                                break;

                            case 13:
                                break;


                    case 2:

                        System.out.println("Denne funksjonealitetn er ikke implementert enda");
                        break;

                    case 3:

                        System.out.println("Hvem sin kalender vil du se?");
                        String otherUser = sc.next();
                        Calendar otherCalendar = new Calendar(otherUser);
                        otherCalendar.viewCalendar();

                        break;

                    case 4:

                        System.out.println("Denne funksjonealitetn er ikke implementert enda");
                        break;

                    case 5:

                        System.out.println("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM:SS)");
                        start = Timestamp.valueOf(sc.nextLine());

                        System.out.println("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM:SS)");
                        slutt = Timestamp.valueOf(sc.nextLine());

                        System.out.println("Legg inn avtaleinformasjon: subject");
                        subject = sc.nextLine();

                        System.out.println("Legg inn avtaleinformasjon: description");
                        description = sc.nextLine();

                        System.out.println("Legg inn avtaleinformasjon: antall møtedeltakere");
                        antall = Integer.valueOf(sc.nextLine());

                        System.out.println("Legg inn møtedeltagere: brukernavn1 brukernavn2 osv..");
                        attendants = new ArrayList<String>();

                        while(sc.hasNext()) {
                            attendants.add(sc.next());
                        }



                        //blabla generer appointment fra denne inputen



                        //legges i en liste med usernames
                        //kall thisAppointment.addAttendent() for hver bruker


                        break;

                    case 6:






                }

                System.out.println("" + start + slutt + subject + description + Integer.valueOf(antall) );

            }



        }
    }


}
