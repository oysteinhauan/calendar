package main;

import client.*;
import database.Database;
import notification.Notification;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public CalendarProgram(){
        this.user = null;
    }

    public void init() {

        Scanner scn = new Scanner(System.in);
        System.out.println("Velkommen. Skriv inn brukernavn!\n");
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
                System.out.println("\n\nUgyldig brukernavn. Prøv igjen!\n\n");
            }
        }

        System.out.println("\nSkriv inn ditt hemmelige passord!");

        while (true) {
            String password = scn.next();
            //String password = passwordMasker();
            try {
                login.login(password);
                this.user = User.getUserFromDB(username);
                //clearConsole();
                System.out.println("Velkommen til vår fantastiske kalender, " + this.user.getFullName() + "!\n\n\n\n");
                TimeUnit.SECONDS.sleep(2);
                clearConsole();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            } catch (InterruptedException e) {
                System.out.println("How did you get here???");
            }
        }

    }

    public void run() {

        boolean loggedIn = true;

        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");


        while (loggedIn && db.isConnected()) {

            clearConsole();
            calendar = new Calendar(username, db);

            int swValue;


            this.user.fetchNotifications(db);

            System.out.println(    "\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
            System.out.println(    "\tDu  har  " + user.getNumberOfNewNotifications() + "  ny(e)  varsling(er)");
            System.out.println(    "\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");




            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                            "1. Opprett / Endre avtaler \n" +
                            "2. Se gruppekalender / Gå til gruppemeny\n" +
                            "3. Se en annens private kalender\n" +
                            "4. Endre min brukerinfo\n" +
                            "5. Vis min personlige kalender\n" +
                            "6. Vis/svar på meldinger\n" +
                            "7. Oppdater\n" +
                            "8. Logg ut\n\n"
            );

            if(this.user.isAdmin(db)){
                System.out.println("9. ADMIN");

            }

            swValue = KeyIn.inInt(" Select Option \n");
            Timestamp start, slutt;
            String subject, description;
            int antall;

            switch (swValue) {
                case 1:
                    clearConsole();
                    appNav(db);
                    continue;

                case 2:
                    clearConsole();

                    ArrayList<String> groupNames = Group.getGroupNamesLowerCase(db);
                    String groupname = "";
                    boolean fail = true;
                    while (fail) {
                        groupname = KeyIn.inString("Vennligst skriv inn navnet paa gruppen du vil utforske!" +
                                " Enter for å gå til gruppemeny.").toLowerCase();
                        if(groupNames.contains(groupname)){
                            fail = false;
                            break;
                        }
                        else if(groupname == ""){
                            Group group = null;
                            fail = false;
                            break;
                        }
                        System.out.println("prøv igjen, gruppenavnet finnes ikke!!");
                    }
                        try {
                            int id = Group.getGroupIDFromDB(groupname);
                            Group group = Group.getGroup(id, db);
                            Calendar groupCalendar = new Calendar(group, db);
                            groupCalendar.viewGroupCalendar();

                            String bæsj = KeyIn.inString("Trykk Enter for å gå videre.");
                            groupStuff(group, db);

                            // legge inn gruppelogikk
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ugyldig gruppenavn");
                        } catch (IllegalAccessError e){
                            groupStuff(null, db);
                            break;
                        }



                case 3:
                    clearConsole();

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    if (User.existsCheck(otherUser, db)) {
                        Calendar otherCalendar = new Calendar(otherUser, db);
                        otherCalendar.viewCalendar();
                        String ferdig = KeyIn.inString("" + "Trykk Enter når du er ferdig.");
                    } else {
                        System.out.println("ugyldig brukernavn");
                        continue;
                    }

                    break;

                case 4:
                    clearConsole();

                    editUserInfo(username, db);

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

                    System.out.println("-----------------------------------------");
                    for (Notification notification : this.user.notifications){
                        if (!notification.isHandled()){
                            System.out.println("-----------------------------------------\n");
                            System.out.println(notification.getMessage());
                            System.out.println("-----------------------------------------\n");
                            switch(notification.getNotificationType()){
                                case 1:
                                    this.user.replyToInvite(notification, db);
                                    continue;
                                case 3:
                                    this.user.replyToInvite(notification, db);
                                    continue;
                                default:
                                    System.out.println("1. Se neste notification");


                                    c = Integer.valueOf((5 + "") + KeyIn.inInt("Select option"));

                                    switch (c) {
                                        case 51:
                                            notification.handle(db);
                                            continue;
                                    }
                            }
                        }

                    }

                case 7:
                    continue;

                case 9:

                    if(!this.user.isAdmin(db)){
                        continue;
                    }
                    admin(db);
                    continue;

                case 8:
                    clearConsole();
                    System.out.printf("snx m8s!!!!1!\n\n\n\n");

                    db.closeConnection();
                    loggedIn = false;

                    break;
            }
        }

    }

    public void clearConsole(){
        System.out.println("\n\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n");
    }

    public static void main(String[] args) {
        CalendarProgram cp = new CalendarProgram();
        cp.init();
        cp.run();
    }

    public static void createAppointment(Database db) {
        Timestamp start;
        Timestamp slutt;
        Timestamp timeNow = new Timestamp((new java.util.Date()).getTime());

        while(true) {
            String str = KeyIn.inString("Legg inn avtaleinformasjon: starttidspunkt (YYYY-MM-DD HH:MM)") + ":00";
            if(CalendarProgram.isTimeStampValid(str)){
                start = Timestamp.valueOf(str);
                break;
            }
            System.out.println("Feil format, prøv igjen!");
        }
        if (start.before(timeNow)) {
            throw new IllegalArgumentException("for tidlig!!!");
        }

        while(true){
        String str = KeyIn.inString("Legg inn avtaleinformasjon: slutttidspunkt (YYYY-MM-DD HH:MM)") + ":00";
            if(CalendarProgram.isTimeStampValid(str)){
                slutt = Timestamp.valueOf(str);
                break;
            }
            System.out.println("Feil format, prøv igjen!");
        }
        if (slutt.before(timeNow) || slutt.before(start)) {
            throw new IllegalArgumentException("for TIDLIG SA JEG!!");
        }

        String subject = KeyIn.inString("Legg inn subject:");
        String description = KeyIn.inString("Legg inn description:");
        int antall = KeyIn.inInt("Legg inn antall møtedeltakere");
        System.out.println(username);

        boolean useSystem;
        Appointment appointment;

        while (true) {

            int roomChoice = KeyIn.inInt("1. La systemet finne rom\n" +
                    "2. Avtalen er et annet sted\n");

            if (roomChoice == 1) {
                useSystem = true;
                appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username, useSystem, db);
                break;

            } else if (roomChoice == 2) {
                useSystem = false;
                appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username, useSystem, db);
                break;

            }
            System.out.println("prøv igjen!");
        }

        appointment.addAttendant(username, db);

        while (appointment.invitedUsers.size() + 1 < antall) {

            String bruker = KeyIn.inString("Skriv inn brukernavn som skal inviteres, 'Cancel' for å avbryte");
            if (bruker.compareToIgnoreCase("cancel") == 0){
                break;
            }
            try {

                appointment.inviteAttendant(bruker, db);
                System.out.println(bruker + " ble invitert.");
            } catch (IllegalArgumentException e){
                //dritt
                System.out.println("Prøv igjen.");
            }
        }
    }

    public void groupStuff(Group group, Database db){
        boolean stay = true;

        while (stay) {

            clearConsole();

            System.out.println("1. Vis medlemmer for gruppen\n2. Legg til deltaker i gruppe/subgruppe." +
                    " \n3. Vis grupper\n" +
                    "5. Slett bruker (ADMIN)\n4. Lag ny gruppe\n6. Gå tilbake");

            int value = KeyIn.inInt("Select Option");
            switch (value) {
                case 1:
                    //Se gruppemedlemmer
                    String dritt = KeyIn.inString("Skriv inn gruppen du vil sjekke medlemmer for.");
                    group = Group.getGroup(Group.getGroupIDFromDB(dritt), db);
                    try {
                        int index = 1;
                        for (String str: group.getMembers()){
                            System.out.println();
                            System.out.println(index +". "  + str);
                            index++;
                        }
                        dritt = KeyIn.inString("Enter når du er ferdig.");
                        continue;
                    } catch (NullPointerException e) {
                        System.out.println("Du har ikke valgt noen gruppe." +
                                " Gå tilbake, og skriv gruppenavnet på nytt.");
                        continue;
                    }

                case 4:
                    //Legg til gruppe/subgruppe
                    char ans = KeyIn.inChar("Subgruppe? y/n");

                    if (ans == 'y') {

                        String asdf = KeyIn.inString("Skriv inn gruppen du vil lage subgruppe til");
                        try {
                            group = Group.getGroup(Group.getGroupIDFromDB(asdf));
                            if (group.getGroupname() == null){
                                throw new IllegalAccessError();

                            }
                        } catch (IllegalAccessError e) {
                            System.out.println("Gruppen finnes ikke.");
                            continue;
                        }

                        String gname = KeyIn.inString("Skriv inn navn på subgruppe");
                        try {
                            group.createSubGroup(gname, db);
                        } catch (RuntimeException e) {
                            System.out.println("Gruppen finnes allerede ellernoe");
                            String bæsj = KeyIn.inString("Enter for å fortsette.");

                        }
                        continue;

                    }
                    else{
                        String gname = KeyIn.inString("Skriv navn på ny gruppe.");
                        try {
                            if (gname == "" || gname == " " || gname == "  " || gname == "   " ||
                                    gname.charAt(0) == ' ') {
                                throw new RuntimeException();
                            }
                        } catch (RuntimeException e){
                            System.out.println("Ugyldig gruppenavn. Ikke begynn med space.....\n");
                            dritt = KeyIn.inString("\nEnter for å fortsette.");
                            break;
                        }
                        Group newGroup = new Group(gname);
                        try {
                            newGroup.createGroup(newGroup, db);
                        } catch(RuntimeException e){
                            dritt = KeyIn.inString("Noe gikk adundas. Enter for å fortsette.");
                            e.printStackTrace();
                        }
                        continue;
                    }


                case 3:
                    clearConsole();
                    ResultSet rs = db.readQuery("select * from group_1;");

                    try {
                        while (rs.next()){
                            System.out.println("||");
                            System.out.println("||" + rs.getString("name"));
                            System.out.println("||");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String bæsj = KeyIn.inString("Enter for å fortsette.");
                    continue;

                    //Vis grupper / subgrupper
                case 2:
                    //Legg til deltaker i gruppe
                    try {
                        if(group == null){
                            String asdf = KeyIn.inString("Skriv inn gruppen du vil legge til bruker i");
                            group = Group.getGroup(Group.getGroupIDFromDB(asdf));
                        }
                        String yn = KeyIn.inString("Vil du legge til deg selv eller noen andre i " + group.getGroupname() + "? y/n");
                        if(yn.equals("y") || yn.equals("yes")){
                            group.addMember(user, group.getGroupID(), db);
                            System.out.println("Brukeren ble mest sannsynlig lagt til.");
                            String d = KeyIn.inString("Enter for å fortsette.");

                            //burde catche runtime
                        }
                        else{
                            String usr = KeyIn.inString("Skriv inn brukeren du vil legge til.");
                            group.addMember(User.getUserFromDB(usr), group.getGroupID(), db);
                            System.out.println("Brukeren ble mest sannsynlig lagt til.");
                            String g = KeyIn.inString("Enter for å fortsette.");
                            //burde catche runtime

                        }
                    } catch (RuntimeException e) {
                        System.out.println("Brukeren ble ikke lagt til. Sikkert allerede medlem elns.");
                        String a = KeyIn.inString("Enter for å fortsette.");
                    }
                    continue;


                case 5:
                    if(!user.isAdmin(db)){
                        System.out.println("Du kan ikke slette grupper, du er ikke admin!");
                        dritt = KeyIn.inString("Enter for å fortsette.");

                        continue;
                    }
                    String asdf = KeyIn.inString("Skriv inn navnet på gruppen du vil slette.\n" +
                            "NB: Du sletter alle subgrupper også!");
                    if(asdf == "" || asdf == " " || asdf.charAt(0) == ' '){
                        System.out.println("Du må jo skrive gruppenavn.... Og ikke start med space!!!");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {

                        }
                        break;
                    }
                    try{
                        Group.removeGroupFromDB(asdf, db);
                        System.out.println("Gruppen er slettet");

                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {

                        }

                        continue;
                    } catch(IllegalArgumentException e){
                        System.out.println("Gruppen finnes ikke.");
                        bæsj = KeyIn.inString("Enter for å fortsette.");

                        continue;
                    }


                    //hvis admin slett gruppe / subgruppe
                case 6:
                    stay = false;
                    break;
                    //gå tilbake

                default: continue;

            }
        }
    }

    public void editAppointment(Database db) {


        boolean bol = true;
        boolean skip = false;
        int idToChange = -1;

        while (bol) {
            try {
                System.out.println("Disse er du eier av:");
                ArrayList<Integer> ids = user.getAppointmentsForOwner(user, db);
                for (Integer i : ids){
                    System.out.println(i.toString());
                }
                idToChange = KeyIn.inInt("Skriv inn avtaleID (0 for å gå tilbake):\n");
                if(idToChange == 0){
                    bol = false;
                    skip = true;
                    break;

                }
                Appointment app = Appointment.getAppointment(idToChange, db);
                if (!app.hasRecord(idToChange, db)) {
                    System.out.println("ID'en finnes ikke, prøv igjen!!");
                } else if (!(Appointment.checkIfOwner(this.user.getUsername(), app, idToChange)) && !this.user.isAdmin()) {
                    throw new IllegalArgumentException("Du må være eieren for å endre.");
                }


                bol = false;
                break;

//                } else {
//                    System.out.println("du må være eieren av en avtale for å endre på den!! prøv igjen!!!" + idToChange);
//                }
            } catch (SQLException e) {
                System.out.println("blabla");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Du må være eieren for å kunne endre avtalen");
                e.printStackTrace();

            }
        }
        if (!skip) {

            Appointment appointmentToChange = Appointment.getAppointment(idToChange, db);

            boolean stay = true;

            while (stay) {
                System.out.println("1. Endre tid\n" +
                      //  "2. Endre sluttid\n" +
                        "3. Legg til deltaker\n" +
                        "4. Fjern deltaker\n" +
                        "5. Endre description\n" +
                        "6. Endre rom\n" +
                        "7. Legg til gruppe\n" +
                        "8. Sjekk deltakere\n" +
                        "9. Slett event\n" +
                        "10. Svar på en av de invitertes notifikasjon\n" +
                        "11. Gå tilbake\n");

                int value2 = KeyIn.inInt("Select option.\n ");
                switch (value2) {
                    case 1:
                        String newStartTime;
                        String newEndTime = "";
                        Timestamp newStart= null;
                        Timestamp newEnd = null;
                        while(true) {
                            newStartTime = KeyIn.inString("\nNåværende start: " + appointmentToChange.getStart().toString()
                                    + "\n\nNåværende slutt: " + appointmentToChange.getEnd().toString()
                                    + "\n\nSkriv inn nytt starttidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                            if(!CalendarProgram.isTimeStampValid(newStartTime)){
                                System.out.println("Ugyldig input");
                                break;
                            }
                            newEndTime = KeyIn.inString("Skriv inn nytt sluttidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
                            if(!CalendarProgram.isTimeStampValid(newEndTime)){
                                System.out.println("Ugyldig input");
                                break;
                            }

                            //System.out.println("Feil format, prøv igjen!!");
                            newStart = Timestamp.valueOf(newStartTime);
                            newEnd = Timestamp.valueOf(newEndTime);
                            break;
                        }
                        try {
                            appointmentToChange.updateTimeInDB(newStart, newEnd, db);
                        } catch(IllegalArgumentException e){
                            System.out.println(e);
                        }
                            continue;

                    case 2:
//                    String newEndTime;
//                    while(true) {
//                        newEndTime = KeyIn.inString("\nNåværende start: " + appointmentToChange.getStart().toString()
//                                + "\n\nNåværende slutt: " + appointmentToChange.getEnd().toString()
//                                + "\n\nSkriv inn nytt sluttidspunkt: (YYYY-MM-DD HH:MM)") + ":00";
//                        if(CalendarProgram.isTimeStampValid(newEndTime)){
//                            break;
//                        }
//                        System.out.println("Feil format, prøv igjen");
//                    }
//                    try {
//                        appointmentToChange.updateAppointmentInDB("slutt", newEndTime, db);
//                    } catch (IllegalArgumentException e) {
//                        System.out.println(e);
//                    }
                    continue;
                case 3:
                    String newAttendant = KeyIn.inString("Hvilken deltaker vil du legge til? skriv inn username");
                    appointmentToChange.inviteAttendant(newAttendant, db);
                    continue;
                case 4:
                    String attendantToRemove = KeyIn.inString("Hvilken deltaker vil du fjerne? skriv inn username");
                    appointmentToChange.removeAttendant(attendantToRemove, db);
                    continue;

                    case 5:
                        String newDescription = KeyIn.inString("Skriv inn ny description:");
                        appointmentToChange.updateAppointmentInDB("description", newDescription, db);
                        continue;
                    case 6:
                        //endre romstr
                    case 7:
                        System.out.println("Not yet implemented.");
                        continue;
                    case 8:
                        ArrayList<String> applist = appointmentToChange.getAttendingPeople();
                        int index = 1;
                        for (String usr : applist) {
                            System.out.println((index + "") + ". " + User.getUserFromDB(usr, db).getFullName() + "\n");
                            index++;
                        }
                        continue;
                    case 9:
                        //slett event
                        Appointment.removeAppointmentInDB(appointmentToChange.getAppointmentId(), db);
                        System.out.println("Appointment removed.");
                        stay = false;
                        break;
                    case 10:

                        String username = KeyIn.inString("Skriv inn username til den du vil endre notifikasjon til");

                        int notificationId = -1;

                        //Database db = new Database();
                        //db.connectDb();
                        String sql = "SELECT notification.notificationId FROM notification, appointment WHERE" +
                                " notification.appointmentId = " + String.valueOf(idToChange) + " AND " +
                                "notification.handled = 0 AND appointment.appointmentId = notification.appointmentId;";

                        ResultSet rs = db.readQuery(sql);
                        try {
                            while (rs.next()){
                                notificationId = rs.getInt("notificationId");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        Notification not = Notification.getNotificationFromDB(notificationId, db);
                        User user = User.getUserFromDB(username, db);
                        user.replyToInvite(not, db);

                        break;
                    case 11:
                        stay = false;
                        break;
                    default:
                        continue;

                }
            }
        }
    }



    public void appNav(Database db) {

        System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +
                        "1. Opprett ny avtale \n" +
                        "2. Endre eksisterende avtale\n" +
                        "3. Gå tilbake\n\n\n"
        );

        int value1 = KeyIn.inInt("Select option. \n ");
        boolean stay = true;
        while (stay) {

            switch (value1) {
                case 1:
                    createAppointment(db);
                    stay = false;
                    break;
                case 2:
                    editAppointment(db);
                    stay = false;
                    break;
                case 3:
                    stay = false;
                    break;
                default:
                    System.out.println("Skriv et gyldig valg!!!");
                    continue;

            }

        }
    }

    public void editUserInfo(String username, Database db) {


        //Database db = new Database();
        //db.connectDb();
        boolean stay = true;

        while (stay) {
            System.out.println("du er innlogget som:" + username + "\n");
            System.out.println("1. Endre Email\n" +
                    "2. Endre passord\n" +
                    "3. Slett min bruker\n" +
                    "4. Tilbake\n");
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
                    System.out.println("Not yet implemented.");
                    continue;
                case 4:
                    stay = false;

                    break;
                default:
                    System.out.println("skriv et gyldig valg!!");
                    continue;
            }
        }
    }

    public void viewUser(String username, Database db) {


        if (User.existsCheck(username, db)) {
            Calendar otherCalendar = new Calendar(username, db);
            otherCalendar.viewCalendar();
        } else {
            throw new IllegalArgumentException("ugyldig bruker");

        }
    }
    public void viewGroup(Database db){

        String groupname = KeyIn.inString("Vennligst skriv inn navnet paa gruppen du vil utforske!");
        try {
            int id = Group.getGroupIDFromDB(groupname);
            Group group = Group.getGroup(id);
            Calendar groupCalendar = new Calendar(group, db);
            groupCalendar.viewCalendar();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid groupname. Try again.");
        }

    }

    public void admin(Database db){
        //System.out.println("ADMIN: \n1: Lag ny bruker\n2. Slett bruker\n3. Opprett rom\n4" +
        //        ". Slett rom\n5. Gå tilbake\n6. Gjør bruker til admin\n");

        boolean stay= true;
        while (stay) {

            System.out.println("ADMIN: \n1: Lag ny bruker\n2. Slett bruker\n3. Opprett rom\n4" +
                    ". Slett rom\n5. Gå tilbake\n6. Gjør bruker til admin\n");
            int value = KeyIn.inInt("Select option:\n");



            switch (value) {

                case 1:

                    clearConsole();
                    boolean taken = true;
                    String un = "";
                    while (taken) {
                        un = KeyIn.inString("Skriv inn nytt brukernavn: ");
                        if (User.usernameTaken(un, db)) {
                            System.out.println("Username taken!");
                            break;
                        }
                        taken = false;
                    }
                    String fn = KeyIn.inString("Skriv inn fornavn: ");
                    String en = KeyIn.inString("Skriv inn etternavn: ");
                    String pswd = KeyIn.inString("Skriv inn nytt passord: ");
                    String position = KeyIn.inString("Skriv inn stilling ('None' hvis ingen)");
                    String email = KeyIn.inString("Skriv inn e-post: ");
                    User user = new User(un, pswd, fn, en, email, position);
                    user.addUserToDB(db);
                    String admin = KeyIn.inString("Make user admin? y/n");
                    if(admin.equalsIgnoreCase("y") || admin.equalsIgnoreCase("yes")) {
                        User.setAdmin(un, db);
                    }

                    break;

                case 2:
                    //slett bruker
                    ResultSet rs = db.readQuery("select username from user;");
                    int index = 1;
                    System.out.printf("Liste over brukere:\n\n");
                    try {
                        while(rs.next()){
                            System.out.println(index + ". " + rs.getString("username") + "\n");
                            index++;
                        }
                    } catch (SQLException e) {
                        System.out.println("Database kødd.");
                    }
                    String duser = KeyIn.inString("\n\nSkriv inn brukernavn som skal slettes.\n\n");
                    try{
                        db.updateQuery("delete from user where username = '" + duser +"';");
                        System.out.println("Bruker slettet.");
                    } catch (RuntimeException e){
                        System.out.println("Brukeren finnes ikke.");
                    }
                    continue;

                case 3:
                    rs = db.readQuery("select * from room;");
                    System.out.println("Liste over tilgjengelige rom:\n\n");
                    try {
                        while(rs.next()){
                            System.out.println("Romnr " +  " " +
                                    rs.getString("roomId") + "  " + "Kapasitet: " +
                                    rs.getString("size") + "  Romnavn: " +
                                    rs.getString("roomName") + "\n");

                        }
                    } catch (SQLException e) {
                        System.out.println("Databasekødd.");
                    }

                    int rsize = KeyIn.inInt("Skriv inn ny romstørrelse");
                    String rname = KeyIn.inString("Skriv inn navn på nytt rom.");
                    try {
                        db.updateQuery("insert into room (size, roomName) values ("
                        + rsize + ", '" + rname + "');");
                        System.out.println("\nRommet ble opprettet\n");
                        String dritt = KeyIn.inString("\nEnter for å fortsette");
                    } catch (RuntimeException e) {
                        System.out.println("Dette gikk ikke bra. ");
                    }
                    //oprett rom

                    continue;

                case 4:
                    rs = db.readQuery("select * from room;");
                    System.out.println("Liste over tilgjengelige rom:\n\n");
                    try {
                        while(rs.next()){
                            System.out.println("Romnr " +  " " +
                                    rs.getString("roomId") + "  " + "Kapasitet: " +
                                    rs.getString("size") + "  Romnavn: " +
                                    rs.getString("roomName") + "\n");

                        }
                    } catch (SQLException e) {
                        System.out.println("Databasekødd.");
                    }
                    //slett rom
                    int droom = KeyIn.inInt("Skriv inn romId som skal slettes");
                    try {
                        db.updateQuery("delete from room where roomId = " + droom + ";");
                    } catch(RuntimeException e){
                        System.out.println("Rommet ble slettet. \n");
                        String dritt = KeyIn.inString("\nEnter for å fortsette");
                    }
                    continue;
                case 5:
                    stay = false;
                    break;
                case 6:
                    String usr = KeyIn.inString("Type in username for new admin: ");
                    try{
                        User.setAdmin(usr, db);
                    } catch(RuntimeException e) {
                        System.out.println("User doesnt exist or is already an admin.");
                    }
                    continue;
                default:
                    continue;
            }
        }
    }

    public String passwordMasker() {

        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        char passwordArray[] = console.readPassword();
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        return new String(passwordArray);
    }

    public static boolean isTimeStampValid(String timeStamp) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String tokens [] = timeStamp.split(" ");
        String str1 [] = tokens[0].split("-");
        String str2 [] = tokens[1].split(":");

        if(Integer.valueOf(str1[1]) > 12 || Integer.valueOf(str1[2]) > 31 || Integer.valueOf(str2[0]) > 24 || Integer.valueOf(str2[1]) > 60)
            return false;
        if(Integer.valueOf(str1[1]) < 0 || Integer.valueOf(str1[2]) < 0 || Integer.valueOf(str2[0]) < 0 || Integer.valueOf(str2[1]) < 0)
            return false;
        try{
            format.parse(timeStamp);
            return true;
        }
        catch(ParseException e) {
            return false;
        }

    }
}

