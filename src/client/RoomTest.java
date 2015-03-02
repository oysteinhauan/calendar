package client;

/**
 * Created by Tuva on 27.02.15.
 */
public class RoomTest {

    public static void main(String[] args) {

        Room room = new Room();

        room.getRoom(2);

        System.out.println(room.getSeats());

        Room room1 = new Room(5, 15, "ola");

        room.createRoom(room1);

        room.updateRoom(5, 10, "coolio");

    }
}
