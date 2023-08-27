package api;

import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
  // Initialize as Singleton Service Class
  private static HotelResource reference = new HotelResource();

  private CustomerService customerService;
  private ReservationService reservationService;

  // Private Constructor
  private HotelResource() {
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
      if(customer.getEmail().equals(email)){
        return customer;
      }
    }
    return null;
  }

  /**
   * Register New Customer
   * @param email
   * @param firstName
   * @param lastName
   */
  public void createACustomer(String email, String firstName, String lastName) {
    customerService.addCustomer(email, firstName, lastName);
  }

  /**
   * Get Room by Room Number
   * @param roomNumber
   * @return IRoom
   */
  public IRoom getRoom(int roomNumber) {
    return reservationService.getARoom(roomNumber);
  }

  /**
   * Book Room After Fetching Customer from Email id
   * @param customerEmail
   * @param room
   * @param checkInDate
   * @param CheckOutDate
   * @return Reservation
   */
  public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate){
    Customer customer = this.getCustomer(customerEmail);
    if(customer != null) {
      return reservationService.reserveARoom(customer, room, checkInDate, CheckOutDate);
    }
    return null;
  }


  /**
   * Get Customer Reservations After Fetching Customer From Email id
   * @param customerEmail
   * @return
   */
  public Collection<Reservation> getCustomersReservations(String customerEmail){
    Customer customer = this.getCustomer(customerEmail);
    if(customer != null) {
      return reservationService.getCustomersReservation(customer);
    }
    return null;
  }

  /**
   * Find Rooms in Checkin & Checkout date range
   * @param checkIn
   * @param checkOut
   * @return
   */
  public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
    return reservationService.findRooms(checkIn, checkOut);
  }

  /**
   * Fetch List of Recommended Rooms
   * @param checkIn
   * @param checkOut
   * @return
   */
  public Collection<IRoom> findRecommendedRooms(Date checkIn, Date checkOut) {
    return reservationService.findRecommendedRooms(checkIn, checkOut);
  }

  /**
   * Fetch Recommended Date
   * @param date
   * @return
   */
  public Date getRecommendedDate(Date date){
    return reservationService.getRecommendedDate(date);
  }

  /**
   * Get CustomerService Singleton Instance
   * @return
   */
  public static HotelResource getInstance(){
    return reference;
  }  
}
