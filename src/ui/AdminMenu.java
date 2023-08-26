package ui;

import java.util.Scanner;

import model.Customer;
import service.CustomerService;

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
        // See all rooms
        break;
    
      case 3:
        // See all reservations
        break;
    
      case 4:
        // Add new room
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

  private static void seeAllCustomers() {
    CustomerService customerService = CustomerService.getInstance();
      for (Customer customer : customerService.getAllCustomers()) {
        System.out.println(customer.toString()); 
      }
  }
}
