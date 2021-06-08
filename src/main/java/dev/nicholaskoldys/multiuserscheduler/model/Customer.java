package dev.nicholaskoldys.multiuserscheduler.model;


/**
 *
 * @author nicho
 */
public class Customer {
    
    private int customerId;
    private String customerName;
    private int addressId;
    private Boolean active;
    Address address;
    
    public Customer(String customerName, Address address) {
        
        this.customerName = customerName;
        this.addressId = address.getAddressId();
        this.address = address;
    }
    
    public Customer(int customerId, String customerName, Address address, Boolean active) {        
        
        this(customerName, address);
        this.customerId = customerId;
        this. active = active;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the addressId
     */
    public int getAddressId() {
        return addressId;
    }

    /**
     * @param addressId the addressId to set
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getAddressName(){
        return address.getAddress();
    }
    
    public String getPhone() {
        return address.getPhoneNum();
    }
    
    @Override
    public String toString() {

        return "CustomerName: " + customerName + "\n"
               + "Address: " + address.getAddress() + "\n"
               + "Phone: " + address.getPhoneNum() + "\n";
    }
}