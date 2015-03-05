package client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by oddma_000 on 03.03.2015.
 */
public class CalendarMain {

    boolean loggedIn;
    boolean running;
    String username = "";
    public static void main(String[] args) {

        init();
        run();
        //koble til db
        //lage et menysystem, kjøres fra cmd (kjøre main filen?)
        ////lage nye bruker eller logge inn
        //valg for funksjonalitet som er implementert

    }


    public static void init(){


    }

    public static void run(){

        boolean bol = true;

        while (bol){
        //foreløpig while condition

            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                    "1. Vis min kalender \n" +
                    "2. Vis min(e) gruppekalender(e)\n" +
                    "3. Se noen andres kalender\n" +
                    "4. Endre min brukerinfo\n" +
                    "5. Opprett ny avtale\n"
            );


            Scanner sc = new Scanner(System.in);

            while(sc.hasNext()){
                int a = sc.nextInt();

                Timestamp start = null, slutt = null;
                String subject = "", description = "";
                ArrayList<String> attendants = null;
                int antall = -1;

                switch (a) {

                    /*case 1:

                        Calendar calendar = new Calendar(this.username);
                        calendar.viewCalendar();
                        break;

                    case 2:


                        break;

                    case 3:

                        System.out.println("Hvem sin kalender vil du se?");
                        String username = sc.next();
                        Calendar calendar = new Calendar(username);
                        calendar.viewCalendar();
                        break;*/

                    case 4:


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




                }

                System.out.println("" + start + slutt + subject + description + Integer.valueOf(antall) );

                bol = false;
            }



        }
    }


}
