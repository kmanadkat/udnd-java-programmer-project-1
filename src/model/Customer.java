package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
  private String firstName;
  private String lastName;
  private String email;

  public Customer(String firstName, String lastName, String email) {
    try {
      // Validate Email
      String emailRegex = "^(.+)@(.+).com$";
      Pattern pattern = Pattern.compile(emailRegex);
      if(!pattern.matcher(email).matches()) {
        throw new IllegalArgumentException("IllegalArgumentException: Invalid Email Id");
      }

      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }

  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "First Name: " + this.firstName + "Last Name: " + this.lastName + " Email: " + this.email;
  }

  @Override
  public boolean equals(Object customer) {
    if (this == customer) return true;
    if (!(customer instanceof Customer)) return false;
    Customer cm = (Customer) customer;
    return firstName.equals(cm.firstName) && lastName.equals(cm.lastName) && email.equals(cm.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, email);
  }
}
