package model;

public class FreeRoom extends Room {

  public FreeRoom(int roomNumber, ERoomType roomType) {
    super(roomNumber, 0, roomType);
  }

  @Override
  public String toString() {
    String roomType = "";
    if(this.gERoomType() == ERoomType.SINGLE) {
      roomType = "Single";
    } else {
      roomType = "Double";
    }
    return "Room Number: " + this.getRoomNumber() + " " + roomType + " Bed Room Price: Free";
  }
    
}
