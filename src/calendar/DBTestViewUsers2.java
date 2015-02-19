package calendar;

import java.sql.*;

/**
 * Created by oysteinhauan on 19/02/15.
 */
public class DBTestViewUsers2 {

    public static void main(String[] args) {
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }

        String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/oysteibh_calendartest";
        String username = "oysteibh";
        String password = "qwerty";
        Connection connection = null;
        Statement statement = null;
        try {
            System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            System.out.println("Creating Statement...");
            statement = connection.createStatement();
            String sql = "SELECT user_id, firstname, lastname, phone_number, postal_code FROM users";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int user_id  = rs.getInt("user_id");
                int phone_number = rs.getInt("phone_number");
                int postal_code = rs.getInt("postal_code");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                //Display values
                System.out.print("\nID: " + user_id);
                System.out.print("\nFirstname: " + firstname);
                System.out.println("\nLastname: " + lastname);
                System.out.println("\nPostal Code: " + postal_code);
                System.out.print("\nPhone Number: " + phone_number + "\n\n\n");


            }

            rs.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }


}


