package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
  // Initialize as Singleton Service Class
  private static ReservationService reference = new ReservationService();

  // Rooms Hash Map <roomNumber, Room>
  Collection<IRoom> rooms;
  Collection<Reservation> reservations;

  // Private Constructor
  private ReservationService() {
    this.rooms = new HashSet<IRoom>();
    this.reservations = new ArrayList<Reservation>();
  }

  /**
   * Validate & Add New Room to the Collection
   * @param room
   */
  public void addRoom(IRoom room) {
    // Validate if room id is unique
    for(IRoom existingRoom: rooms) {
      int existingRoomNumber = existingRoom.getRoomNumber();
      int newRoomNumber = room.getRoomNumber();
      if(existingRoomNumber == newRoomNumber) {
        throw new IllegalArgumentException("Room already exists with number " + room.getRoomNumber());
      }
    }
    rooms.add(room);
  }

  /**
   * Get Room by Room Number
   * @param roomNumber
   * @return IRoom
   */
  public IRoom getARoom(int roomNumber){
    for(IRoom existingRoom: rooms) {
      if(existingRoom.getRoomNumber() == roomNumber) {
        return existingRoom;
      }
   }
   return null;
  }

  /**
   * Make Room Reservation for Customer. If Room not available, increment dates by 7 & check
   * @param customer
   * @param room
   * @param checkInDate
   * @param checkOutDate
   * @return Reservation
   */
  public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    // Room available for booking - Book, Print & Return
    Collection<IRoom> availableRooms = this.findRooms(checkInDate, checkOutDate);
    for(IRoom availableRoom: availableRooms) {
      if(availableRoom.getRoomNumber() == room.getRoomNumber()) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
      }
    }
  
    return null;
  }

  /**
   * Find Available Rooms between two dates using private method
   * @param checkInDate
   * @param checkOutDate
   * @return Collection<IRoom>
   */
  public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    Collection<IRoom> availableRooms = this.findAvailableRooms(checkInDate, checkOutDate);
    if(availableRooms.size() > 0) {
      return availableRooms;
    }
    return null;
  }

  /**
   * Find recommended rooms
   * @param checkInDate Original CheckIn Date
   * @param checkOutDate Original Checkout Date
   * @return
   */
  public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
    Date incrementedCheckIn = getRecommendedDate(checkInDate);
    Date incrementedCheckOut = getRecommendedDate(checkOutDate);
    Collection<IRoom> availableRooms = this.findRooms(incrementedCheckIn, incrementedCheckOut);
    return availableRooms;
  }
  
  
  /**
   * Find recommended date from current date
   * @param currentDate
   * @return
   */
  public Date getRecommendedDate(Date currentDate) {
    // Increment Dates by 7 for recommended Rooms
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DAY_OF_YEAR, 7);
    return calendar.getTime();
  }

  /**
   * Get CustomerService Singleton Instance
   * @return ReservationService
   */
  public static ReservationService getInstance(){
    return reference;
  }

  /**
   * Get all Reservations made by Customer
   * @param currentDate
   * @param days
   * @return Collection<Reservation>
   */
  public Collection<Reservation> getCustomersReservation(Customer customer) {
    Collection<Reservation> customerReservations = new ArrayList<Reservation>();
    for (Reservation reservation: reservations) {
      if(reservation.getCustomer().getEmail() == customer.getEmail()){
        customerReservations.add(reservation);
      }
    }
    return customerReservations;
  }


  /**
   * Print All Reservations
   */
  public void printAllReservation() {
    if(reservations.size() > 0){
      System.out.println("All Reservations:");
      for (Reservation reservation : reservations) {
        System.out.println(reservation.toString() + "\n");
      }
    } else {
      System.out.println("No reservations found");
    }
  }
  
  /**
   * Private Find Available Rooms between two dates
   * @param checkInDate
   * @param checkOutDate
   * @return Collection<IRoom>
   */
  private Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
    Collection<IRoom> availableRooms = new ArrayList<IRoom>();
    for(IRoom rm: this.rooms) {
      int roomNumber = rm.getRoomNumber();
      boolean roomAvailable = true;
      // Check All Existing Reservations
      for(Reservation rs: reservations) {
        if(rs.getRoom().getRoomNumber() == roomNumber) {
          //1. required Checkout date is before reserved room checkin date
          if(checkOutDate.before(rs.getCheckInDate())) {
            roomAvailable = true;
          }
          //2. Checkin date after checkout date
          else if(checkInDate.after(rs.getCheckOutDate())){
            roomAvailable = true;
          }
          // Found reservation not satisfying 1. & 2. conditions
          else {
            roomAvailable = false;
            break;
          }
        }
      }
      if(roomAvailable) {
        availableRooms.add(rm);
      }
    }
    return availableRooms;
  }

  public Collection<IRoom> getRooms() {
    return this.rooms;
  }
}
