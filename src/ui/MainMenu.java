package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class MainMenu {
  public static void showMenuOptions() {
    System.out.println("\nWelcome to the Hotel Reservation Application");
    System.out.println("------------------------------------------------");
    System.out.println("1. Find and reserve a room");
    System.out.println("2. See my reservations");
    System.out.println("3. Create an Account");
    System.out.println("4. Admin");
    System.out.println("5. Exit");
    System.out.println("------------------------------------------------");
    System.out.println("Please select a number for the menu option\n");
  }
  
  public static boolean initialize(Scanner s, int option) {
    boolean showMenu = true;
    switch (option) {
      case 1:
        createRoomReservation(s);
        break;
    
      case 2:
        showCustomerReservations(s);
        break;
    
      case 3:
        createCustomerAccount(s);
        break;
    
      case 4:
        goToAdminMenu(s);
        break;
      
      case 5:
        showMenu = false;
        break;

      default:
        System.out.println("Invalid input, please try again");
        break;
    }
    return showMenu;
  }

  private static void createRoomReservation(Scanner s){
    Date today = new Date();
    // Get Checkin Date
    boolean isCheckInDateValid = false;
    Date checkInDate = new Date();
    while(!isCheckInDateValid) {
      checkInDate = getDateUserInput(s, "Enter CheckIn Date mm/dd/yyyy example 02/01/2023");
      if(checkInDate.after(today)) {
        isCheckInDateValid = true;
      } else {
        System.out.println("Checkin Date cannot be in the past");
      }
    }

    // Get Checkout Date
    boolean isCheckOutDateValid = false;
    Date checkOutDate = new Date();
    while(!isCheckOutDateValid){
      checkOutDate = getDateUserInput(s, "Enter CheckOut Date mm/dd/yyyy example 02/15/2023");
      if(checkOutDate.after(checkInDate)) {
        isCheckOutDateValid = true;
      } else {
        System.out.println("Checkout Date cannot be before Checkin date");
      }
    }

    // Find Rooms
    HotelResource resourse = HotelResource.getInstance();
    Collection<IRoom> rooms = resourse.findARoom(checkInDate, checkOutDate);
    // Rooms Available
    if(rooms != null && rooms.size() > 0) {
      bookAvailableRoom(s, rooms, checkInDate, checkOutDate);
    }
    
    // Rooms Not Available, Show Recommended rooms - increment by 7 days
    else {
      System.out.println("No rooms available between checkin & checkout dates");
      rooms = resourse.findRecommendedRooms(checkInDate, checkOutDate);
      if(rooms != null && rooms.size() > 0){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        Date newCheckinDate = resourse.getRecommendedDate(checkInDate);
        Date newCheckoutDate = resourse.getRecommendedDate(checkOutDate);
        String checkinString = sdf.format(newCheckinDate);
        String checkoutString = sdf.format(newCheckoutDate);

        System.out.println("Recommended Rooms available between " + checkinString + " & " + checkoutString + " :");
        bookAvailableRoom(s, rooms, newCheckinDate, newCheckoutDate);
      }
    }
  }

  private static void bookAvailableRoom(Scanner s, Collection<IRoom> rooms, Date checkInDate, Date checkOutDate) {
    // Print all rooms
    for (IRoom room : rooms) {
      System.out.println(room.toString());
    }
    // Seek Booking Preference
    boolean wantToBook = getYesNoInput(s, "\nWould you like to book a room? y/n");
    // Don't want to book - exit
    if(!wantToBook) {
      return;
    }
    boolean hasAccount = getYesNoInput(s, "\nDo you have an account with us? y/n");
    // Not Having Account - Help Creating Account
    String emailId = "";
    if(!hasAccount){
      emailId = createCustomerAccount(s);
    } else {
      // Get Valid Email Id
      boolean isValidEmailId = false;
      while(!isValidEmailId) {
        try {
          System.out.println("Enter email id:");
          emailId = s.next();
          Customer customer = HotelResource.getInstance().getCustomer(emailId);
          if(customer == null){
            System.out.println("No accounts found with email: " + emailId + ", please create account & try again");
            return;
          }
          isValidEmailId = true;
        } catch (Exception e) {
          System.out.println(e.getLocalizedMessage());
          System.out.println("Please try again");
          s.nextLine(); // Consume the leftover input
        }
      }
    }

    // Ask for Room Number
    boolean isRoomNumberValid = false;
    int roomNumber = 0;
    while(!isRoomNumberValid) {
      try {
        System.out.println("What room number would you like to reserve?");
        roomNumber = s.nextInt();
        IRoom room = HotelResource.getInstance().getRoom(roomNumber);
        if(room != null) {
          Reservation reservation = HotelResource.getInstance().bookARoom(emailId, room, checkInDate, checkOutDate);
          isRoomNumberValid = true;
          if(reservation == null) {
            System.out.println("Room is not available for the checkin checkout dates, please try again"); // should never happen - only available rooms shown to user
            isRoomNumberValid = false;
          } else {
            // Print Reservation - FINALLY!
            System.out.println(reservation.toString());
            return;
          }
        } else {
          System.out.println("Room number invalid, please try again");
        }
      } catch (Exception e) {
        System.out.println("Invalid input, Please try again");
        s.nextLine();
      }
    }
  }

  private static boolean getYesNoInput(Scanner s, String message) {
    // Check Account Existence
    boolean isValidResponse = false;
    boolean boolValue = false;
    while(!isValidResponse){
      try {
        System.out.println(message);
        String response = s.next();
        if(response.equals("y") || response.equals("n")) {
          boolValue = response.equals("y");
          isValidResponse = true;
        } else {
          throw new Exception("Invalid input");
        }
      } catch (Exception e) {
        System.out.println("Invalid input, please try again");
        s.nextLine();
      }
    }
    return boolValue;
  }
  
  private static Date getDateUserInput(Scanner s, String message) {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    Date inputDate = null;
    boolean validInputDate = false;
    while (!validInputDate) {
      System.out.println(message);
      String inputDateStr = s.next();
      try {
        inputDate = dateFormatter.parse(inputDateStr);
        validInputDate = true;
      } catch (ParseException ex) {
        System.out.println("Invalid date format, please use dd/mm/yyyy");
        s.nextLine();  // Consume the leftover input
      }
    }
    return inputDate;
  }

  /**
   * Show All Customer Reservations
   * @param s Scanner
   */
  private static void showCustomerReservations(Scanner s) {
    HotelResource resource = HotelResource.getInstance();
    boolean isValidEmail = false;
    String emailId = "";
    while(!isValidEmail){
      try {
        System.out.println("Enter your email id");
        emailId = s.next();
        Customer customer = resource.getCustomer(emailId);
        if(customer == null){
          System.out.println("Account with email entered is not found.");
          return;
        }
        Collection<Reservation> reservations = resource.getCustomersReservations(emailId);
        if(reservations.size() == 0) {
          System.out.println("No reservations found");
          return;
        } 
        for (Reservation reservation : reservations) {
          System.out.println(reservation.toString() + "\n");
        }
        isValidEmail = true;
      } catch (Exception e) {
        System.out.println("Error occured, please try again");
        s.nextLine();  // Consume the leftover input
      }
    }
  }

  /**
   * Create Customer Workflow
   * @param s - Scanner
   */
  private static String createCustomerAccount(Scanner s) {
    HotelResource hotelResource = HotelResource.getInstance();
    System.out.println("Enter first name:");
    String firstName = s.next();
    
    System.out.println("Enter last name:");
    String lastName = s.next();

    String emailId = "";
    boolean isValidEmailId = false;
    while(!isValidEmailId) {
      System.out.println("Enter email id:");
      emailId = s.next();
      try {
        hotelResource.createACustomer(emailId, firstName, lastName);
        isValidEmailId = true;
        break;
      } catch (Exception e) {
        System.out.println(e.getLocalizedMessage());
        System.out.println("Please try again");
        s.nextLine(); // Consume the leftover input
      }
    }
    return emailId;
  }

  /**
   * Switch To Admin Menu Context
   * @param s - Scanner
   */
  private static void goToAdminMenu(Scanner s) {
    boolean showAdminMenu = true;
    while(showAdminMenu) {
      AdminMenu.showMenuOptions();
      try {
        int option = s.nextInt();
        showAdminMenu = AdminMenu.initialize(s, option);
      } catch (Exception e) {
        System.out.println("Invalid input, please try again");
        s.nextLine(); // Consume the leftover input
      }
    }
  }
}
