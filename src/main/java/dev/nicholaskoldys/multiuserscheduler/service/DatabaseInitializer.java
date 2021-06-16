package dev.nicholaskoldys.multiuserscheduler.service;

import dev.nicholaskoldys.multiuserscheduler.model.*;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.*;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;

import static java.lang.Math.floor;

public class DatabaseInitializer {

    private final String USER_TABLE = "user";
    private final String USER_ID = "userId";
    private final String USER_NAME = "userName";
    private final String USER_PASSWORD = "password";
    private final String USER_ACTIVE = "active";

    private final String COUNTRY_TABLE = "country";
    private final String COUNTRY_ID = "countryId";
    private final String COUNTRY_NAME = "country";

    private final String CITY_TABLE = "city";
    private final String CITY_ID= "cityId";
    private final String CITY_NAME = "city";

    private final String ADDRESS_TABLE = "address";
    private final String ADDRESS_ID = "addressId";
    private final String ADDRESS_NAME = "address";
    private final String ADDRESS2_NAME = "address2";
    private final String ADDRESS_PHONE = "phone";
    private final String ADDRESS_POSTALCODE = "postalCode";

    private final String CUSTOMER_TABLE = "customer";
    private final String CUSTOMER_ID = "customerId";
    private final String CUSTOMER_NAME = "customerName";
    private final String CUSTOMER_ACTIVE = "active";

    private final String APPOINTMENT_TABLE = "appointment";
    private final String APPOINTMENT_ID = "appointmentId";
    private final String APPOINTMENT_TITLE = "title";
    private final String APPOINTMENT_DESCRIPTION = "description";
    private final String APPOINTMENT_LOCATION = "location";
    private final String APPOINTMENT_CONTACT = "contact";
    private final String APPOINTMENT_TYPE = "type";
    private final String APPOINTMENT_URL = "url";
    private final String APPOINTMENT_STARTTIME = "start";
    private final String APPOINTMENT_ENDTIME = "end";

    private final String CREATE_DATE = "createDate";
    private final String CREATE_BY = "createdBy";
    private final String LAST_UPDATE = "lastUpdate";
    private final String LAST_UPDATE_BY = "lastUpdateBy";

    public static DatabaseInitializer instance;

    private DatabaseInitializer() {
        createStructure();
        initSchema();
        fillInData();
    }

    private static DatabaseInitializer getInstance() {
        if(instance == null) {
            instance = new DatabaseInitializer();
        }
        return instance;
    }

    public static void initializeDatabase() {
        DatabaseInitializer.getInstance();
    }

    private void createStructure() {

        if(!isDatabaseStructureCreated()) {
            try {
                createSchema();
                createUserTable();
                createCountryTable();
                createCityTable();
                createAddressTable();
                createCustomerTable();
                createAppointmentTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createAppointmentTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + APPOINTMENT_TABLE + " ("
                                + APPOINTMENT_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + CUSTOMER_ID + " " + "INT" + ", "
                                + USER_ID + " " + "INT" + ", "
                                + APPOINTMENT_TITLE + " " + "VARCHAR(255)" + ", "
                                + APPOINTMENT_DESCRIPTION + " " + "CLOB(255)" + ", "
                                + APPOINTMENT_LOCATION + " " + "CLOB(255)" + ", "
                                + APPOINTMENT_CONTACT + " " + "CLOB(255)" + ", "
                                + APPOINTMENT_TYPE + " " + "CLOB(255)" + ", "
                                + APPOINTMENT_URL + " " + "CLOB(255)" + ", "
                                + APPOINTMENT_STARTTIME + " " + "TIMESTAMP" + ", "
                                + APPOINTMENT_ENDTIME + " " + "TIMESTAMP" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + APPOINTMENT_ID + ")" + ", "
                                + "FOREIGN KEY" + " " + "(" + CUSTOMER_ID + ")"
                                + " REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")" + ", "
                                + "FOREIGN KEY" + " " + "(" + USER_ID + ")"
                                + " REFERENCES " + USER_TABLE + "(" + USER_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create appointment");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createCustomerTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + CUSTOMER_TABLE + " ("
                                + CUSTOMER_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + CUSTOMER_NAME + " " + "VARCHAR(45)" + ", "
                                + ADDRESS_ID + " " + "INT" + ", "
                                + CUSTOMER_ACTIVE + " " + "TINYINT" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + CUSTOMER_ID + ")" + ", "
                                + "FOREIGN KEY" + " " + "(" + ADDRESS_ID + ")"
                                + " REFERENCES " + ADDRESS_TABLE + "(" + ADDRESS_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create customer");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createAddressTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + ADDRESS_TABLE + " ("
                                + ADDRESS_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + ADDRESS_NAME + " " + "VARCHAR(30)" + ", "
                                + ADDRESS2_NAME + " " + "VARCHAR(30)" + ", "
                                + CITY_ID + " " + "INT" + ", "
                                + ADDRESS_POSTALCODE + " " + "VARCHAR(10)" + ", "
                                + ADDRESS_PHONE + " " + "VARCHAR(20)" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + ADDRESS_ID + ")" + ", "
                                + "FOREIGN KEY" + " " + "(" + CITY_ID + ")"
                                + " REFERENCES " + CITY_TABLE + "(" + CITY_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create address");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createCityTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + CITY_TABLE + " ("
                                + CITY_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + CITY_NAME + " " + "VARCHAR(50)" + ", "
                                + COUNTRY_ID + " " + "INT" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + CITY_ID + ")" + ", "
                                + "FOREIGN KEY" + " " + "(" + COUNTRY_ID + ")"
                                + " REFERENCES " + COUNTRY_TABLE + "(" + COUNTRY_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create city");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createCountryTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + COUNTRY_TABLE + " ("
                                + COUNTRY_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + COUNTRY_NAME + " " + "VARCHAR(50)" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + COUNTRY_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create country");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createUserTable() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE TABLE IF NOT EXISTS schema_schedules." + USER_TABLE + " ("
                                + USER_ID + " " + "INT" + " "
                                + "GENERATED BY DEFAULT AS "
                                + "IDENTITY(START WITH 1, INCREMENT BY 1)" + ", "
                                + USER_NAME + " " + "VARCHAR(50)" + ", "
                                + USER_PASSWORD + " " + "VARCHAR(50)" + ", "
                                + USER_ACTIVE + " " + "TINYINT" + ", "
                                + CREATE_DATE + " " + "DATETIME" + ", "
                                + CREATE_BY + " " + "VARCHAR(40)" + ", "
                                + LAST_UPDATE + " " + "TIMESTAMP" + ", "
                                + LAST_UPDATE_BY + " " + "VARCHAR(40)" + ", "
                                + "PRIMARY KEY" + " " + "(" + USER_ID + ")"
                                + ");"
                )
        ) {
            createStatement.execute();
            System.out.println("Create user");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createSchema() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "CREATE SCHEMA SCHEMA_SCHEDULES AUTHORIZATION DBA;"
                )
        ) {
            createStatement.execute();
            System.out.println("Create schema");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initSchema() {
        try (PreparedStatement createStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "SET SCHEMA SCHEMA_SCHEDULES;"
                )
        ) {
            createStatement.execute();
            System.out.println("Init schema");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fillInData() {

        if(!isDataFilled()) {
            try {

                UserDAOImpl.getInstance().create(new User("test", "secret", true));
                User user1 = UserDAOImpl.getInstance().getUser(1);

                InputStream countryInput = getClass().getClassLoader().getResourceAsStream("countryList.txt");
                ReadFromFileUtil.sendInputStreamToFunc(countryInput, new Consumer<String>() {
                    @Override
                    public void accept(String country) {
                        CountryDAOImpl.getInstance().create(new Country(country));
                        //AddressBook.getInstance().addCountry(
                    }
                });

                CityDAOImpl.getInstance().create("Nashville", 233);
                City city1 = CityDAOImpl.getInstance().getCity(1, true);
                CityDAOImpl.getInstance().create("Chicago",233);
                City city2 = CityDAOImpl.getInstance().getCity(2, true);
                CityDAOImpl.getInstance().create("Seattle", 233);
                City city3 = CityDAOImpl.getInstance().getCity(3, true);

                Address adr1 = createQuickAddress("1234 Acorn Dr.", "", city1,
                        "12345", "615-123-4567", 1);
                Address adr2 = createQuickAddress("1 Rainbow Rd.", "", city2,
                        "56789", "637-123-4567", 2);
                Address adr3 = createQuickAddress("22 South Dr.", "Apt.3-rm.22",
                        city3, "54321", "987-123-4567", 3);

                Customer cus1 = createQuickCustomer("Nick K", adr1, 1);
                Customer cus2 = createQuickCustomer("Kevin T", adr2, 2);
                Customer cus3 = createQuickCustomer("Sandy Luu", adr3, 3);


                LocalDateTime baseTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));
                List<LocalDateTime> startDTList = new ArrayList<>();
                /*List<LocalDateTime> endTimeList = new ArrayList<>();*/

                for(int hour = 0; hour < 9; hour++) {

                    //Don't exceed 5:00PM - Workers shouldn't work this late.
                    if(baseTime.getHour() + hour == 17) {
                        break;
                    }

                    //Increment minutes every 15.
                    for(int min = 0; min < 60; min++) {

                        if(min % 15 == 0) {
                            startDTList.add(
                                    LocalDateTime.of(
                                            baseTime.toLocalDate(),
                                            baseTime.toLocalTime().plusMinutes((hour * 60) + min))
                            );
                        }
                    }
                }

                int sepRatio = (int) floor(startDTList.size()/10);

                System.out.println(sepRatio);

                for(int i = 0; i < 10; i++) {

                    AppointmentDAOImpl.getInstance().create(
                            new Appointment(
                                    cus1,
                                    user1,
                                    "Physical Exam " + i,
                                    "This is a Desc.",
                                    "Nashville, Downtown Office",
                                    "Dr. Feelgud",
                                    "Check-up",
                                    cus1.getCustomerName(),
                                    startDTList.get(i*sepRatio),
                                    startDTList.get(i*sepRatio).plusMinutes(30)
                            )
                    );
                    AppointmentDAOImpl.getInstance().create(
                            new Appointment(
                                    cus2,
                                    user1,
                                    "Psych. Health " + i,
                                    "This is a Desc.",
                                    "Chicago, Downtown Office",
                                    "Dr. Mac",
                                    "Mental Therapy",
                                    cus2.getCustomerName(),
                                    startDTList.get(i*sepRatio).plusDays(1),
                                    startDTList.get(i*sepRatio).plusDays(1).plusMinutes(30)
                            )
                    );
                    AppointmentDAOImpl.getInstance().create(
                            new Appointment(
                                    cus3,
                                    user1,
                                    "Dental Check " + i,
                                    "This is a Desc.",
                                    "Seattle, Downtown Office",
                                    "Dr. Hoover",
                                    "Dentist",
                                    cus3.getCustomerName(),
                                    startDTList.get(i*sepRatio).plusDays(2),
                                    startDTList.get(i*sepRatio).plusDays(2).plusMinutes(30)
                            )
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDatabaseStructureCreated() {

        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "SELECT * FROM INFORMATION_SCHEMA.TABLES "
                                + "WHERE TABLE_SCHEMA LIKE 'SCHEMA_SCHEDULES'"
                )
        ) {
            ResultSet results = selectStatement.executeQuery();
            int counter = 0;
            String checkedTables = "";
            while(results.next()) {
                checkedTables += results.getString(3) + ".. ";
                counter++;
            }

            System.out.println("Tables correctly set: " + checkedTables);

            if(counter == 6) {
                return true;
            } else {
                System.out.println("Table counter: " + counter);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private boolean isDataFilled() {

        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection()
                .prepareStatement(
                        "SELECT COUNT(*) FROM SCHEMA_SCHEDULES.appointment"
                )
        ) {
            ResultSet results = selectStatement.executeQuery();
            results.next();
            System.out.println(results.getLong(1));

            if(results.getLong(1) > 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private Customer createQuickCustomer(String name, Address adr, int currentTotalCountOf) {
        CustomerDAOImpl.getInstance().create(new Customer(name, adr,true));
        return CustomerDAOImpl.getInstance().getCustomer(currentTotalCountOf, true);
    }

    private Address createQuickAddress(String adr, String adr2, City city, String post, String phnum, int currentTotalCountOf) {
        AddressDAOImpl.getInstance().create(new Address(adr, adr2, city, post, phnum));
        return AddressDAOImpl.getInstance().getAddress(currentTotalCountOf, true);
    }
}