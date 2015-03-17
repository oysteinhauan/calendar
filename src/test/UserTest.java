package test;

import client.User;

import java.util.Scanner;

/**
 * Created by Henrik on 02.03.2015.
 */
public class UserTest {

    public static void main(String[] args) {

        User user = new User();
        Scanner scn = new Scanner(System.in);
        System.out.println("Type in username:\n");
        while (scn.hasNext()){
            String usr = scn.nextLine();
            //if(!User.usernameTaken(usr)){
                System.out.println("Username available!\n");
                user.setUsername(usr);
                break;
            //} else {
               // System.out.println("Username taken! Try again:\n");
           // }
        }
        System.out.println("Please type firstname: \n");
        while (scn.hasNext()){
            try {
                String first = scn.nextLine();
                System.out.println("Please type lastname: \n");
                String last = scn.nextLine();
                System.out.println("Please choose a password: \n");
                String pswd = scn.nextLine();
                System.out.println("Please state your position. Type 'None' if you don't have any.\n");
                String pos = scn.nextLine();
                System.out.println("Please type in your e-mail. (examplename@exampleserver.com");
                String mail = scn.nextLine();

                user.setFirstname(first);
                user.setLastname(last);
                user.setPassword(pswd);
                user.setPosition(pos);
                user.setEmail(mail);
                user.addUserToDB();
                System.out.println("User added succesfully!");
                break;

            } catch (Exception e) {
                System.out.println("Something went to shit. >:( Try again.");
            }
        }
        System.out.println("User must be added to at least one group. \nPlz type groupIds for groups. \nWhen finished press enter.");
        while (scn.hasNext()){

        }
        System.out.printf("Do you want to delete some n00bz?\n");
        while(scn.hasNext()){
            String usr = scn.nextLine();
            user.deleteUserFromDb(usr);

        }


    }
}
