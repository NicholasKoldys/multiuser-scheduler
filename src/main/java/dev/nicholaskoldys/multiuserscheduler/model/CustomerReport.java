package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.Year;

/**
 *
 * @author nicho
 */
public class CustomerReport extends Report {

    private int count;
    private Year year;
    private String customer;

    
    public CustomerReport(int count, Year year, String customer) {
        
        this.count = count;
        this.year = year;
        this.customer = customer;
    }
    
    @Override
    public void setCount(int count) {
        
        this.count = count;
    }

    @Override
    public int getCount() {
        
        return count;
    }

    /**
     * @return the year
     */
    public Year getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
