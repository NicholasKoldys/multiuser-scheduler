package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Country;

/**
 *
 * @author nicho
 */
public interface CountryDAO {
    
    List<Country> getAllCountries();
    Country getCountry(int countryId);
    Boolean create(Country country);
    Boolean update(Country country);
    Boolean delete(Country country);
}
