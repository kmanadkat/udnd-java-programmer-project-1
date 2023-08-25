package api;

import java.util.Collection;
import java.util.List;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
    // Initialize as Singleton Service Class
  private static AdminResource reference = new AdminResource();

  private CustomerService customerService;
  private ReservationService reservationService;

  // Private Constructor
  private AdminResource() {
    customerService = CustomerService.getInstance();
    reservationService = ReservationService.getInstance();
  }

  /**
   * Get Customer with Email Id
   * @param email
   * @return Customer
   */
  public Customer getCustomer(String email) {
    Collection<Customer> registeredCustomers = customerService.getAllCustomers();
    for (Customer customer : registeredCustomers) {
      if(customer.getEmail() == email){
        return customer;
      }
    }
    System.out.println("Customer Not Found");
    return null;
  }

  /**
   * Register Rooms
   * @param rooms
   */
  public void addRoom(List<IRoom> rooms){
    for (IRoom room : rooms) {
      reservationService.addRoom(room);
    }
  }

  /**
   * Get All Rooms
   * @return
   */
  public Collection<IRoom> getAllRooms(){
    return reservationService.getRooms();
  }

  /**
   * Get All Customers
   * @return
   */
  public Collection<Customer> getAllCustomers(){
    return customerService.getAllCustomers();
  }

  /**
   * Print All Reservations
   */
  public void displayAllReservations(){
    reservationService.printAllReservation();
  }

  /**
   * Get CustomerService Singleton Instance
   * @return
   */
  public static AdminResource getInstance(){
    return reference;
  }  
}
