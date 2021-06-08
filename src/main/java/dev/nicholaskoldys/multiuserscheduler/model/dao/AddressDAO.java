package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Address;

/**
 *
 * @author nicho
 */
public interface AddressDAO {
    
    List<Address> getAllAddresses();
    Address getAddress(int addressId);
    Boolean create(Address address);
    Boolean update(Address address);
    Boolean delete(Address address);
}
