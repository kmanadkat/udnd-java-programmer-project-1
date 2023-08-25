import java.util.Calendar;
import java.util.Date;

import model.Customer;
import model.ERoomType;
import model.Reservation;
import model.Room;

public class Driver {
  public static void main(String[] args) {
    // Customer
    Customer customer1 = new Customer("Bob", "Nice", "bob.nice@test.com");
    System.out.println(customer1.toString());

    // Test Invalid Customer Email
    // Customer customer2 = new Customer("Bob", "Nice", "bob.nice@test");
    // System.out.println(customer2.toString());

    // Room
    Room room1 = new Room("101", 95.0, ERoomType.SINGLE);
    System.out.println(room1.toString());

    // Reservation
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 7); // Add 7 days to the current date    

    Date checkInDate = new Date();
    Date checkOutDate = calendar.getTime();
    Reservation reservation = new Reservation(customer1, room1, checkInDate, checkOutDate);
    System.out.println(reservation.toString());
  }
}
