package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
  private final Customer customer;
  private final IRoom room;
  private final Date checkInDate;
  private final Date checkOutDate;

  public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    this.customer = customer;
    this.room = room;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
  }

  @Override
  public final String toString() {
    String roomType = "Double";
    if(this.room.gERoomType() == ERoomType.SINGLE) {
      roomType = "Single";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
    String checkinString = sdf.format(this.checkInDate);
    String checkoutString = sdf.format(this.checkOutDate);
    return this.customer.getFirstName() + " " + this.customer.getLastName() + 
           "\nRoom: " + this.room.getRoomNumber() + " - " + roomType + " bed" + 
           "\nPrice: $" + this.room.getRoomPrice() + " price per night" + 
           "\nCheckin Date: " + checkinString + 
           "\nCheckout Date: " + checkoutString;
  }

  // Getters
  public Customer getCustomer() {
    return customer;
  }
  public IRoom getRoom() {
    return room;
  }
  public Date getCheckInDate() {
    return checkInDate;
  }
  public Date getCheckOutDate() {
    return checkOutDate;
  }
}
