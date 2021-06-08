package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Customer;

/**
 *
 * @author nicho
 */
public interface CustomerDAO {
    
    List<Customer> getAllCustomers();
    Customer getCustomer(int customerId);
    Boolean create(Customer customer);
    Boolean update(Customer customer);
    Boolean delete(Customer customer);
}
