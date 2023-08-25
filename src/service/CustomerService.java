package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Customer;

public class CustomerService {
  // Initialize as Singleton Service Class
  private static CustomerService reference = new CustomerService();

  // Email - Customer map for O(1) search
  private Map<String, Customer> customers;

  // Private Constructor
  private CustomerService() {
    this.customers = new HashMap<String, Customer>();
  }

  /**
   * Register new Customers & Store them
   * @param email
   * @param firstName
   * @param lastName
   */
  public void addCustomer(String email, String firstName, String lastName) {
    try {
      // Check For Unique Email id
      if(customers.get(email) != null) {
        throw new IllegalArgumentException("Customer with email " + email + " already exists");
      }
      Customer newCustomer = new Customer(firstName, lastName, email);
      customers.put(email, newCustomer);
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }


  /**
   * Get Existing Customer From Email Id
   * @param customerEmail
   * @return Customer
   */
  public Customer getCustomer(String customerEmail) {
    try {
      Customer customer = customers.get(customerEmail);
      if(customer == null) {
        throw new IllegalArgumentException("Customer with email " + customerEmail + " not found");
      }
      return customer;
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getLocalizedMessage());
      return null;
    }
  }


  /**
   * Get All Existing Customers
   * @return Collection<Customer>
   */
  public Collection<Customer> getAllCustomers() {
    return customers.values();
  }

  /**
   * Get CustomerService Singleton Instance
   * @return
   */
  public static CustomerService getInstance(){
    return reference;
  }
}
