import java.util.Calendar;
import java.util.Date;

import model.Customer;
import model.ERoomType;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.ReservationService;

public class Driver {
  public static void main(String[] args) {
    // Customer
    Customer customer1 = new Customer("Bob", "Nice", "bob.nice@test.com");
    // System.out.println(customer1.toString());

    // Test Invalid Customer Email
    // Customer customer2 = new Customer("Bob", "Nice", "bob.nice@test");
    // System.out.println(customer2.toString());

    // Room
    Room room1 = new Room("101", 95.0, ERoomType.SINGLE);
    // System.out.println(room1.toString());

    // Reservation
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 7); // Add 7 days to the current date    

    Date checkInDate = new Date();
    Date checkOutDate = calendar.getTime();
    Reservation reservation = new Reservation(customer1, room1, checkInDate, checkOutDate);
    // System.out.println(reservation.toString());

    // CustomerService
    CustomerService customerService = CustomerService.getInstance();
    customerService.addCustomer("jack@test.com", "Jack", "Jill");
    customerService.addCustomer("johny@test.com", "Jhony", "James");
    customerService.addCustomer("johny@test.com", "Jhony", "James");
    // System.out.println(customerService.getCustomer("jack@test.com"));
    // System.out.println(customerService.getCustomer("jack@abc.com"));
    // System.out.println(customerService.getAllCustomers());

    // ReservationService
    ReservationService reservationService = ReservationService.getInstance();
    Room room2 = new Room("102", 45, ERoomType.SINGLE);
    Room room3 = new Room("103", 102, ERoomType.DOUBLE);
    reservationService.addRoom(room1);
    reservationService.addRoom(room2);
    reservationService.addRoom(room3);

    System.out.println(reservationService.getARoom("101"));
    System.out.println(reservationService.getARoom("102"));
    System.out.println(reservationService.getARoom("103"));

    System.out.println("::::::After Adding All Rooms::::::\n");
    reservationService.reserveARoom(customer1, room1, checkInDate, checkOutDate);
    reservationService.reserveARoom(customer1, room2, checkInDate, checkOutDate);
    reservationService.printAllReservation();
    
    System.out.println("::::::After Reserving Two Rooms::::::\n");
    reservationService.findRooms(checkInDate, checkOutDate);

    reservationService.reserveARoom(customer1, room3, checkInDate, checkOutDate);
    reservationService.findRooms(checkInDate, checkOutDate);
  }
}
