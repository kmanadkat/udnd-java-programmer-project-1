package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
  // Initialize as Singleton Service Class
  private static ReservationService reference = new ReservationService();

  // Rooms list
  Collection<IRoom> rooms;
  Collection<Reservation> reservations;

  // Private Constructor
  private ReservationService() {
    this.rooms = new ArrayList<IRoom>();
    this.reservations = new ArrayList<Reservation>();
  }

  /**
   * Validate & Add New Room to the Collection
   * @param room
   */
  public void addRoom(IRoom room) {
    try {
      // Validate if room id is unique
      for(IRoom existingRoom: rooms) {
        if(existingRoom.getRoomNumber() == room.getRoomNumber()) {
          throw new IllegalArgumentException("Room already exists with number " + room.getRoomNumber());
        }
      }
      rooms.add(room);
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }

  /**
   * Get Room by Room Number
   * @param roomNumber
   * @return IRoom
   */
  public IRoom getARoom(String roomNumber){
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
        System.out.println(newReservation.toString());
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
    System.out.println("No Rooms Available In Date Range\n");

    // Room not available -> Increment Dates by 7 & Show Recommended Rooms
    Date incrementedCheckIn = this.incrementDate(checkInDate, 7);
    Date incrementedCheckOut = this.incrementDate(checkOutDate, 7);
    availableRooms = this.findAvailableRooms(incrementedCheckIn, incrementedCheckOut);
    if(availableRooms.size() > 0){
      SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
      String checkinString = sdf.format(incrementedCheckIn);
      String checkoutString = sdf.format(incrementedCheckOut);

      System.out.println("Recommended Rooms available between " + checkinString + " & " + checkoutString + " :");
      for(IRoom recommendedRoom: availableRooms) {
        System.out.println(recommendedRoom.toString());
      }
    }
    
    return null;
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
        System.out.println(reservation.toString());
      }
    }
  }

  /**
   * Utility Function to Increment Date By Number of Days
   * @param currentDate
   * @param days
   * @return Date
   */
  private Date incrementDate(Date currentDate, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DAY_OF_YEAR, 7);
    return calendar.getTime();
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
      String roomNumber = rm.getRoomNumber();
      boolean roomAvailable = true;
      // Check All Existing Reservations
      for(Reservation rs: reservations) {
        if(rs.getRoom().getRoomNumber() == roomNumber) {
          if(checkInDate.before(rs.getCheckOutDate()) && checkOutDate.after(rs.getCheckInDate())){
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
}
