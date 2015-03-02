package client;

/**
 * Created by Tuva on 27.02.15.
 */
public class RoomTest {

    public static void main(String[] args) {

        Room room = new Room();

        //room.getRoom(2);

        //System.out.println(room.getSeats());

        //Room room1 = new Room(6, 15, "yea");

        //room.createRoom(room1);

        //Room room2 = new Room();

        //room2.updateRoom(5, 10, "coolio");

        //room.updateRoom(6, 8, "sexy");

        Room room3 = new Room(8, "Rongved");

        room.createRoom(room3);

    }
}
