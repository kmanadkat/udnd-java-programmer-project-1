package model;

public class Room implements IRoom {
  private String roomNumber;
  private double price;
  private ERoomType roomType;

  public Room(String roomNumber, double price, ERoomType roomType) {
    this.roomNumber = roomNumber;
    this.price = price;
    this.roomType = roomType;
  }
  
  @Override
  public String toString() {
    return "Room Number: " + this.roomNumber + " " + this.getRoomType() + " Bed Room Price: $" + this.price;
  }

  public String getRoomType() {
    if(this.roomType == ERoomType.SINGLE) {
      return "Single";
    }
    return "Double";
  }

  @Override
  public String getRoomNumber() {
    return this.roomNumber;
  }

  @Override
  public Double getRoomPrice() {
    return this.price;
  }

  @Override
  public ERoomType gERoomType() {
    return this.roomType;
  }

  // Assuming all Room Class Objects have price > 0
  @Override
  public boolean isFree() {
    return false;
  }
}
