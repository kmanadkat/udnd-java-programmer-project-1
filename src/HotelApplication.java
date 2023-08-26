import java.util.Scanner;

import ui.MainMenu;

public class HotelApplication {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    boolean showMenu = true;

    while(showMenu) {
      MainMenu.showMenuOptions();
      try {
        int option = s.nextInt();
        showMenu = MainMenu.initialize(s, option);
      } catch (Exception e) {
        System.out.println(e.getLocalizedMessage());
        System.out.println("Invalid input, please try again");
        s.nextLine(); // Consume the leftover input
      }
    }
  }
}
