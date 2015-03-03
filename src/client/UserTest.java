package client;

/**
 * Created by Henrik on 02.03.2015.
 */
public class UserTest {

    public static void main(String[] args) {
//        User u = new User("oddmrog", "børshht", "Odd Martin", "Rognpåseteller", "oddmrog@rognpåsan.com", "qwert");
//        u.addUserToDB();
//
//        User u = new User();
//        u.getUserFromDB("qwerty");
//        System.out.println(u.toString());

        User u = new User();
        u.getUserFromDB("oysteibh");
        System.out.println(u.toString());
        //u.updateUserInfoInDB("positon", "scrummaster");
        //u.getUserFromDB("henloef");
        //System.out.println(u.toString());
//        u.deleteUserFromDb("qwerty");
//        u.getUserFromDB("qwerty");
//        System.out.println(u.toString());



    }
}
