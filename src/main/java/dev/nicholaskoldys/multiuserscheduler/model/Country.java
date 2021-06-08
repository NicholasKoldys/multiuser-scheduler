/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nicholaskoldys.multiuserscheduler.model;

/**
 *
 * @author nicho
 */
public class Country {
    
    private int countryId;
    private String country;
    
    
    public Country(String country) {
        
        this.countryId = 0;
        this.country = country;
    }
    
    
    public Country(int countryId, String country) {
        
        this(country);
        this.countryId = countryId;
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
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return country;
    }
}
