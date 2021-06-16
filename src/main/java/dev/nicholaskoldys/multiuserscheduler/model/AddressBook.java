
package dev.nicholaskoldys.multiuserscheduler.model;

import java.util.ArrayList;
import java.util.List;

import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.AddressDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CityDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CountryDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CustomerDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model Class
 * 
 * contains lists of all database entities linked to the customer entity
 *
 * @author nicho
 */
public class AddressBook {
    
    private ObservableList<Customer> customersList =
            FXCollections.observableArrayList();
    private ObservableList<Customer> activeCustomersList =
            FXCollections.observableArrayList();
    
    private List<Country> countriesList;
    private List<City> citiesList;
    private List<Address> addressesList;
    
    private final CountryDAOImpl countryData;
    private final CityDAOImpl cityData;
    private final AddressDAOImpl addressData;
    private final CustomerDAOImpl customerData;

    private static final AddressBook instance =
            new AddressBook();
    
    
    /**
     * Sets names of Data Access implementations
     * 
     * Unable to load lists due Implementations requiring list data of
     * its chained entities to provide faster data initialization.
     * 
     * Lists are loaded in the AppointmentCalendar Class - this may change
     * 
     */
    private AddressBook() {
        countryData = CountryDAOImpl.getInstance();
        cityData = CityDAOImpl.getInstance();
        addressData = AddressDAOImpl.getInstance();
        customerData = CustomerDAOImpl.getInstance();
    }

    public static AddressBook getInstance() {
        return instance;
    }
    
    /**
     * Method used as main entry method from controllers AddCustomer method
     * 
     * @param customerName
     * @param address
     * @param address2
     * @param postalCode
     * @param phoneNum
     * @param city
     * @param country
     * @param update
     * @return 
     */
    public Customer addCustomer(String customerName, String address,
            String address2, String postalCode, String phoneNum,
            String city, String country, boolean update) {
        
        Country co = new Country(country);
        addCountry(co);
        co = lookupCountry(country);
        
        City ct = new City(city, co);
        addCity(ct);
        ct = lookupCity(city, co.getCountryId());

        Address adr = new Address(address, address2, ct, postalCode, phoneNum);
        addAddress(adr);
        adr = lookupAddress(address, address2, ct.getCityId(), postalCode, phoneNum);
        
        Customer cus = new Customer(customerName, adr, true);
        
        if(!update) {
            addCustomerInternal(cus);
            cus = lookupCustomer(customerName, adr.getAddressId());
        }
        
        return cus;
    }
    
    /**
     * Method to add customer from other add customer method.
     * 
     * @param customer 
     */
    public void addCustomerInternal(Customer customer) {
        
        if(lookupCustomer(customer.getCustomerName(), customer.getAddressId()) == null) {
            
            customerData.create(customer);
            customer.setCustomerId(customerData.getCustomerId(customer));
            customersList.add(customer);
            activeCustomersList.add(customer);
        }
    }
    
    /**
     * 
     * @param customer
     * @param customerName
     * @param address
     * @param address2
     * @param postalCode
     * @param phoneNum
     * @param city
     * @param country 
     */
    public void updateCustomer(Customer customer, String customerName, String address,
            String address2, String postalCode, String phoneNum,
            String city, String country) {
        
        Customer transitionCustomer = addCustomer(customerName, address, address2,
                postalCode, phoneNum, city, country, true);
        
        transitionCustomer.setCustomerId(customer.getCustomerId());
        
        customerData.update(transitionCustomer);
        
        customersList.remove(customer);
        customersList.add(transitionCustomer);
        
        for(Appointment appointment : AppointmentCalendar.getInstance().lookupAppointments(customer)) {
            appointment.setCustomer(transitionCustomer);
            AppointmentCalendar.getInstance().updateAppointment(appointment);
        }
        
        activeCustomersList.remove(customer);
        activeCustomersList.add(transitionCustomer);
    }
    
    /**
     * 
     * @param customer 
     */
    public void deleteCustomer(Customer customer) {
            
        List<Appointment> appointmentsList = AppointmentCalendar.getInstance().getAllAppointments();
        List<Integer> deleteList = new ArrayList<>();
        
        for(Appointment appointment : appointmentsList) {
            if(appointment.getCustomerId() == customer.getCustomerId()) {
                deleteList.add(appointment.getAppointmentId());
            }
        }
        
        for(Integer app : deleteList) {
            AppointmentCalendar.getInstance().deleteAppointment(
                    AppointmentCalendar.getInstance().lookupAppointment(app));
        }
        
        customerData.delete(customer);
        activeCustomersList.remove(customer);
    }
    
    /**
     * 
     * @param customerName
     * @param addressId
     * @return 
     */
    public Customer lookupCustomer(String customerName, int addressId){

        for(Customer customer : activeCustomersList) {

            if(customer.getCustomerName().equals(customerName)
                    && customer.getAddressId() == addressId) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 
     * @param customerId
     * @return 
     */
    public Customer lookupCustomer(int customerId){

        for(Customer customer : customersList) {
            if(customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 
     * @param customerName
     * @return 
     */
    public ObservableList<Customer> lookupCustomers(String customerName) {
        
       ObservableList<Customer> lookupCustomerList =
               FXCollections.observableArrayList();
        
        for (Customer customer : activeCustomersList) {
            if (customer.getCustomerName().contains(customerName)) {
                lookupCustomerList.add(customer);
            }
        }
        
        return lookupCustomerList;
    }

    /**
     * 
     */
    public Boolean loadAllCustomers() {

        customersList = FXCollections.observableArrayList(customerData.getAllCustomers());
        List<Customer> activeList = new ArrayList<>();
        
        for(Customer customer : customersList) {
            if(customer.getActive() != false) {
                activeList.add(customer);
            }
        }
        
        activeCustomersList = FXCollections.observableArrayList(activeList);
        return (customersList != null);
    }
    
    public ObservableList<Customer> getAllActiveCustomers() {

        return activeCustomersList;
    }

    /**
     * Address CRUD operations customers use
     * 
     * @param addressesList 
     */
    public void setAddressesList(List<Address> addressesList) {
        this.addressesList = addressesList;
    }

    public List<Address> getAddressesList() {
       return addressesList;
    }

    public void addAddress(Address address) {
        
        if(lookupAddress(address.getAddress(), address.getAddress2(),
                address.getCityId(), address.getPostalCode(),
                address.getPhoneNum()) == null) {
            
            addressData.create(address);
            address.setAddressId(addressData.getAddressId(address));
            addressesList.add(address);
        }
    }

    public Address lookupAddress(int addressId) {

        for(Address ad : addressesList){
            if(ad.getAddressId() == addressId){
                return ad;
            }
        }
        return null;
    }

    public Address lookupAddress(String address, String address2, int cityId,
            String postalCode, String phone){

        for(Address ad : addressesList) {
            if(ad.getAddress().equals(address) 
                    && ad.getAddress2().equals(address2)
                    && ad.getCityId() == cityId
                    && ad.getPostalCode().equals(postalCode)
                    && ad.getPhoneNum().equals(phone)) {
                return ad;
            }
        }
        return null;
    }

    /**
     * City CRUD operations Addresses use
     * 
     * @param citiesList 
     */
    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public List<City> getCitiesList() {
       return citiesList;
    }

    public void addCity(City city) {
        
        if(lookupCity(city.getCity(), city.getCountryId()) == null) {
            
            cityData.create(city);
            city.setCityId(cityData.getCityId(city));
            citiesList.add(city);
        }
    }

    public City lookupCity(int cityId) {

        for(City city : citiesList){
            if(city.getCityId() == cityId){
                return city;
            }
        }
        return null;
    }

    public City lookupCity(String city, int countryId){

        for(City ct : citiesList) {
            if(ct.getCity().equals(city) 
                    && ct.getCountryId() == countryId) {
                return ct;
            }
        }
        return null;
    }

    /**
     * Country CRUD operations Cities use
     * 
     * @param countriesList 
     */
    public void setCountriesList(List<Country> countriesList) {
        this.countriesList = countriesList;
    }

    public List<Country> getCountriesList() {
       return countriesList;
    }

    public void addCountry(Country country) {
        
        if(lookupCountry(country.getCountry()) == null) {
            
            countryData.create(country);
            country.setCountryId(countryData.getCountryId(country));
            countriesList.add(country);
        }
    }

    public Country lookupCountry(int countryId) {

        for(Country country : countriesList){
            if(country.getCountryId() == countryId){
                return country;
            }
        }
        return null;
    }

    public Country lookupCountry(String country){

        for(Country co : countriesList) {
            if(co.getCountry().equals(country)) {
                return co;
            }
        }
        return null;
    }
}
