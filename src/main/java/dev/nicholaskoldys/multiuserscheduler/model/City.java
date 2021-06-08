package dev.nicholaskoldys.multiuserscheduler.model;

/**
 *
 * @author nicho
 */
public class City {
    
    private int cityId;
    private String city;
    private int countryId;
    private Country country;
    
    
    public City(String city, Country country) {
        
        this.city = city;
        this.country = country;
        this.countryId = country.getCountryId();
    }
    
    
    public City(int cityId, String city, Country country) {
        
        this(city, country);
        this.cityId = cityId;
    }


    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
