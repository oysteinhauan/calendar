package client;

/**
 * Created by Henrik on 02.03.2015.
 */
public class UserTest {

    public static void main(String[] args) {
        User u = new User("henloef", "karsk", "Henrik", "Løfaldli", "henloef@spacex.com", "CEO");
        u.addUserToDB();
    }
}
