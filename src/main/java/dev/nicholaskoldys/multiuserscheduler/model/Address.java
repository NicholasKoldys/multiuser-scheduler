package dev.nicholaskoldys.multiuserscheduler.model;

/**
 *
 * @author nicho
 */
public class Address {
    
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phoneNum;
    private City city;
    
    public Address(String address, String address2, City city, String postalCode, String phoneNum) {
        
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.cityId = city.getCityId();
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.addressId = 0;
    }
    
    public Address(int addressId, String address, String address2, City city, String postalCode, String phoneNum) {
        
        this(address, address2, city, postalCode, phoneNum);
        this.addressId = addressId;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
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
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }
}
