package ui;

import java.util.Scanner;

import api.HotelResource;

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
        // Find & Reserve a room
        break;
    
      case 2:
        // See my reservations
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

  /**
   * Create Customer Workflow
   * @param s - Scanner
   */
  private static void createCustomerAccount(Scanner s) {
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
      }
    }
  }
}
