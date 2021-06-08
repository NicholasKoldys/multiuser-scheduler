package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;

import dev.nicholaskoldys.multiuserscheduler.model.City;

/**
 *
 * @author nicho
 */
public interface CityDAO {
    
    List<City> getAllCities();
    City getCity(int cityId);
    Boolean create(City city);
    Boolean update(City city);
    Boolean delete(City city);
}
