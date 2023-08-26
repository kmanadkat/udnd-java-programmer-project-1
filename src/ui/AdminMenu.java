package ui;

import java.util.Collection;
import java.util.Scanner;

import api.AdminResource;
import model.Customer;
import model.ERoomType;
import model.IRoom;
import model.Room;

public class AdminMenu {
  public static void showMenuOptions() {
    System.out.println("\nAdmin Menu");
    System.out.println("------------------------------------------------");
    System.out.println("1. See all Customers");
    System.out.println("2. See all Rooms");
    System.out.println("3. See all Reservations");
    System.out.println("4. Add a Room");
    System.out.println("5. Back to Main Menu");
    System.out.println("------------------------------------------------");
    System.out.println("Please select a number for the menu option\n");
  }

  public static boolean initialize(Scanner s, int option) {
    boolean showAdminMenu = true;
    switch (option) {
      case 1:
        seeAllCustomers();
        break;
    
      case 2:
        seeAllRooms();
        break;
    
      case 3:
        seeAllReservations();
        break;
    
      case 4:
        addNewRoom(s);
        break;
      
      case 5:
        // Back to main menu
        showAdminMenu = false;
        break;

      default:
        System.out.println("Invalid input, please try again");
        break;
    }
    return showAdminMenu;
  }

  /**
   * Print All Customers Account
   */
  private static void seeAllCustomers() {
    AdminResource adminResource = AdminResource.getInstance();
    Collection<Customer> customers = adminResource.getAllCustomers();
    if(customers.size() == 0) {
      System.out.println("No customer accounts found");
      return;
    }

    for (Customer customer: customers) {
      System.out.println(customer.toString()); 
    }
  }

  /**
   * See All Rooms
   */
  private static void seeAllRooms() {
    AdminResource adminResource = AdminResource.getInstance();
    Collection<IRoom> rooms = adminResource.getAllRooms();
    if(rooms.size() == 0) {
      System.out.println("No rooms found");
      return;
    }

    for (IRoom room: rooms) {
      System.out.println(room.toString()); 
    }
  }

  /**
   * See All Reservations
   */
  private static void seeAllReservations() {
    AdminResource adminResource = AdminResource.getInstance();
    adminResource.displayAllReservations();
  }

  /**
   * Add New Room
   * @param s Scanner
   */
  private static void addNewRoom(Scanner s) {
    boolean isRoomNumberValid = false;
    int roomNumber = 0;
    while(!isRoomNumberValid) {
      try {
        System.out.println("Enter room number");
        roomNumber = s.nextInt();
        isRoomNumberValid = true;
      } catch (Exception e) {
        System.out.println("Invalid input, please try again");
        s.nextLine();
      }
    }

    double price = 0.0;
    boolean isValidPrice = false;
    while(!isValidPrice){
      try {
        System.out.println("Enter price per night");
        price = s.nextDouble();
        isValidPrice = true;
      } catch (Exception e) {
        System.out.println("Invalid price entered, please try again");
        s.nextLine(); // Consume the leftover input
      }
    }

    ERoomType roomType = null;
    int roomTypeOption = -1;
    boolean isRoomTypeValid = false;
    while(!isRoomTypeValid) {
      try {
        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        roomTypeOption = s.nextInt();      
        if(roomTypeOption == 1){
          roomType = ERoomType.SINGLE;
          isRoomTypeValid = true;
        } else if(roomTypeOption == 2){
          roomType = ERoomType.DOUBLE;
          isRoomTypeValid = true;
        } else{
          System.out.println("Invalid input, please try again");
        }
      } catch (Exception e) {
        System.out.println("Invalid input, please try again");
        s.nextLine(); // Consume the leftover input
      }
    }

    AdminResource adminResource = AdminResource.getInstance();
    try {
      IRoom room = new Room(roomNumber, price, roomType);
      adminResource.addRoom(room);
      System.out.println(room.toString());
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage()+ ", please try again");
      addNewRoom(s);
    }
  }
}
