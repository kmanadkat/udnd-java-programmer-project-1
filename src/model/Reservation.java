package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
  private Customer customer;
  private IRoom room;
  private Date checkInDate;
  private Date checkOutDate;

  public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    this.customer = customer;
    this.room = room;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
  }

  @Override
  public String toString() {
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
}
