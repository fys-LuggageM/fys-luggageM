package fys.luggagem;

import fys.luggagem.models.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Enumeration;

/**
 * *
 * Demonstrates use of JDBC in a plain Java Program
 *
 * @author somej
 */
public class MyJDBC {

    private static Data data = MainApp.getData();
    private static final String DB_DEFAULT_DATABASE = "sys";
    private static final String DB_DEFAULT_SERVER_URL = data.getProperties().getProperty("DB_DEFAULT_SERVER_URL");
    private static final String DB_DEFAULT_ACCOUNT = data.getProperties().getProperty("DB_DEFAULT_ACCOUNT");
    private static final String DB_DEFAULT_PASSWORD = data.getProperties().getProperty("DB_DEFAULT_PASSWORD");

    private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_DRIVER_PARAMETERS = "?useSSL=false";

    private Connection connection = null;
    private int registrationNr;
    private int registrationNrDamaged;
    private int registrationNrFound;
    private int registrationNrLost;
    private int luggageRegistrationNr;
    private int changesRegistrationNr;

    // set for verbose logging of all queries
    private boolean verbose = true;

    // remembers the first error message on the connection 
    private String errorMessage = null;

    // constructors
    public MyJDBC() {
        this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName) {
        this(dbName, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName, String account, String password) {
        this(dbName, DB_DEFAULT_SERVER_URL, account, password);
    }

    public MyJDBC(String dbName, String serverURL, String account, String password) {
        try {
            // verify that a proper JDBC driver has been installed and linked
            if (!selectDriver(DB_DRIVER_URL)) {
                return;
            }

            if (password == null) {
                password = "";
            }

            // establish a connection to a named database on a specified server	
            String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
            log("Connecting " + connStr);
            this.connection = DriverManager.getConnection(connStr, account, password);

        } catch (SQLException eSQL) {
            error(eSQL);
            this.close();
        }
    }

    public final void close() {

        if (this.connection == null) {
            // db has been closed earlier already
            return;
        }
        try {
            this.connection.close();
            this.connection = null;
            this.log("Data base has been closed");
        } catch (SQLException eSQL) {
            error(eSQL);
        }
    }

    /**
     * *
     * elects proper loading of the named driver for database connections. This is relevant if there are multiple
     * drivers installed that match the JDBC type
     *
     * @param driverName the name of the driver to be activated.
     * @return indicates whether a suitable driver is available
     */
    private Boolean selectDriver(String driverName) {
        try {
            Class.forName(driverName);
            // Put all non-prefered drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            error(ex);
            return false;
        }
        return true;
    }

    /**
     * *
     * Executes a DDL, DML or DCL query that does not yield a result set
     *
     * @param sql the full sql text of the query.
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeUpdateQuery(String sql) {
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            int n = s.executeUpdate(sql);
            s.close();
            return (n);
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

    /**
     * *
     * Executes an SQL query that yields a ResultSet with the outcome of the query. This outcome may be a single row
     * with a single column in case of a scalar outcome.
     *
     * @param sql the full sql text of the query.
     * @return a ResultSet object that can iterate along all rows
     * @throws SQLException
     */
    public ResultSet executeResultSetQuery(String sql) throws SQLException {
        Statement s = this.connection.createStatement();
        log(sql);
        ResultSet rs = s.executeQuery(sql);
        // cannot close the statement, because that also closes the resultset
        return rs;
    }

    /**
     * *
     * Executes query that is expected to return a single String value
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }

    /**
     * *
     * Executes query that is expected to return a list of String values
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringListQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }

    /**
     * *
     * echoes a message on the system console, if run in verbose mode
     *
     * @param message
     */
    public void log(String message) {
        if (isVerbose()) {
            System.out.println("MyJDBC: " + message);
        }
    }

    /**
     * *
     * echoes an exception and its stack trace on the system console. remembers the message of the first error that
     * occurs for later reference. closes the connection such that no further operations are possible.
     *
     * @param e
     */
    public final void error(Exception e) {
        String msg = "MyJDBC-" + e.getClass().getName() + ": " + e.getMessage();

        // capture the message of the first error of the connection
        if (this.errorMessage == null) {
            this.errorMessage = msg;
        }
        System.out.println(msg);
        e.printStackTrace();

        // if an error occurred, close the connection to prevent further operations
        this.close();
    }

    public static void createDatabase(String dbName) {
        System.out.println("Creating the " + dbName + " database...");

        // use the sys schema for creating another db
        MyJDBC sysJDBC = new MyJDBC("sys");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
        sysJDBC.close();

        // create or truncate Airport table in the Airline database
        System.out.println("Creating the table...");
        MyJDBC myJDBC = new MyJDBC(dbName);
        myJDBC.executeUpdateQuery("CREATE TABLE `account` (\n"
                + "  `Employee_code` int(11) NOT NULL,\n"
                + "  `email` varchar(45) DEFAULT NULL,\n"
                + "  `password` varchar(64) DEFAULT NULL,\n"
                + "  `salt` varchar(64) DEFAULT NULL,\n"
                + "  `user_level` int(11) DEFAULT NULL,\n"
                + "  `active` tinyint(1) DEFAULT '1'\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `account` (`Employee_code`, `email`, `password`, `salt`, `user_level`, `active`) VALUES\n"
                + "(500500, 'pathedude@gmail.com', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 3, 1),\n"
                + "(500501, 'admin@corendol.nl', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 3, 1)");

        myJDBC.executeUpdateQuery("CREATE TABLE `airport` (\n"
                + "  `IATA` varchar(5) NOT NULL,\n"
                + "  `name` varchar(45) DEFAULT NULL,\n"
                + "  `country` varchar(45) DEFAULT NULL,\n"
                + "  `timezone` varchar(45) DEFAULT NULL,\n"
                + "  `city` varchar(45) DEFAULT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `airport` (`IATA`, `name`, `country`, `timezone`, `city`) VALUES\n"
                + "('AMS', 'Amsterdam Schiphol Airport', 'Netherlands', 'GMT+1', 'Amsterdam'),\n"
                + "('RTM', 'Rotterdam Airport', 'Netherlands', 'GMT+1', 'Rotterdam'), \n"
                + "('AYT', 'Antalya Airport', 'Turkey', 'GMT+3', 'Rotterdam'), \n"
                + "('AGP', 'Malaga Airport', 'Spain', 'GMT', 'Malaga'), \n"
                + "('BJV', 'Bodrum Airport', 'Turkey', 'GMT+3', 'Bodrum'), \n"
                + "('IST', 'Istanbul Airport', 'Turkey', 'GMT+3', 'Istanbul'), \n"
                + "('DLM', 'Dalaman Airport', 'Turkey', 'GMT+3', 'Dalaman'), \n"
                + "('ADB', 'Izmir Airport', 'Turkey', 'GMT+3', 'Izmir'), \n"
                + "('GZP', 'Gazipasa-Alanya Airport', 'Turkey', 'GMT+3', 'Gazipasa-Alanya')");

        myJDBC.executeUpdateQuery("CREATE TABLE `changes` (\n"
                + "  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  `Employee_code` int(11) NOT NULL,\n"
                + "  `Luggage_registrationnr` int(11) NOT NULL,\n"
                + "  `changeid` tinyint(1) NOT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `changes` (`date`, `Employee_code`, `Luggage_registrationnr`, `changeid`) VALUES\n"
                + "('2018-01-15 23:47:57', 500500, 1, 0),\n"
                + "('2018-01-15 23:49:15', 500500, 2, 0),\n"
                + "('2018-01-15 23:50:38', 500500, 3, 0),\n"
                + "('2018-01-15 23:53:15', 500500, 4, 0),\n"
                + "('2018-01-15 23:57:49', 500500, 5, 0),\n"
                + "('2018-01-16 00:00:00', 500500, 6, 0),\n"
                + "('2018-01-16 00:04:28', 500500, 7, 0),\n"
                + "('2018-01-16 00:05:24', 500500, 8, 0),\n"
                + "('2018-01-16 00:05:49', 500500, 9, 0),\n"
                + "('2018-01-16 00:07:29', 500500, 10, 0),\n"
                + "('2018-01-16 00:08:28', 500500, 11, 0),\n"
                + "('2018-01-16 00:09:27', 500500, 12, 0)");

        myJDBC.executeUpdateQuery("CREATE TABLE `customer` (\n"
                + "  `customernr` int(11) NOT NULL,\n"
                + "  `first_name` varchar(45) DEFAULT NULL,\n"
                + "  `preposition` varchar(45) DEFAULT NULL,\n"
                + "  `last_name` varchar(45) DEFAULT NULL,\n"
                + "  `adres` varchar(45) DEFAULT NULL,\n"
                + "  `city` varchar(45) DEFAULT NULL,\n"
                + "  `postal_code` varchar(45) DEFAULT NULL,\n"
                + "  `country` varchar(45) DEFAULT NULL,\n"
                + "  `phone` varchar(45) DEFAULT NULL,\n"
                + "  `email` varchar(45) DEFAULT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `customer` (`customernr`, `first_name`, `preposition`, `last_name`, `adres`, `city`, `postal_code`, `country`, `phone`, `email`) VALUES\n"
                + "(1, 'Joris', '', 'Ebbelaar', 'Pleasantpark', 'Assendelft', '0420XD', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl'),\n"
                + "(2, 'Jan', 'van', 'Test', 'Lonelylodge', 'Amserdam', '1234CP', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl'),\n"
                + "(3, 'Loes', 'van', 'Ackema', 'Greasygroove', 'Haarlem', '1532GT', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl')");

        myJDBC.executeUpdateQuery("CREATE TABLE `employee` (\n"
                + "  `code` int(11) NOT NULL,\n"
                + "  `first_name` varchar(45) DEFAULT NULL,\n"
                + "  `preposition` varchar(45) DEFAULT NULL,\n"
                + "  `last_name` varchar(45) DEFAULT NULL,\n"
                + "  `Luchthaven_IATA` varchar(5) NOT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `employee` (`code`, `first_name`, `preposition`, `last_name`, `Luchthaven_IATA`) VALUES\n"
                + "(500500, 'Pathe', NULL, 'Dude', 'AMS'),\n"
                + "(500501, 'Admin', NULL, NULL, 'AMS')");

        myJDBC.executeUpdateQuery("CREATE TABLE `luggage` (\n"
                + "  `registrationnr` int(11) NOT NULL,\n"
                + "  `date` datetime DEFAULT CURRENT_TIMESTAMP,\n"
                + "  `flightnr` varchar(45) DEFAULT NULL,\n"
                + "  `labelnr` varchar(45) DEFAULT NULL,\n"
                + "  `location_found` varchar(45) DEFAULT NULL,\n"
                + "  `destination` varchar(45) DEFAULT NULL,\n"
                + "  `luggage_type` varchar(45) DEFAULT NULL,\n"
                + "  `brand` varchar(45) DEFAULT NULL,\n"
                + "  `primary_color` varchar(45) DEFAULT NULL,\n"
                + "  `secondary_color` varchar(45) DEFAULT NULL,\n"
                + "  `notes` longtext,\n"
                + "  `size` varchar(45) DEFAULT NULL,\n"
                + "  `weight` varchar(45) DEFAULT NULL,\n"
                + "  `case_type` int(11) DEFAULT NULL,\n"
                + "  `customer_firstname` varchar(45) DEFAULT NULL,\n"
                + "  `customer_preposition` varchar(45) DEFAULT NULL,\n"
                + "  `customer_lastname` varchar(45) DEFAULT NULL,\n"
                + "  `case_status` tinyint(1) NOT NULL DEFAULT '1',\n"
                + "  `airport_IATA` varchar(5) DEFAULT NULL,\n"
                + "  `customer_customernr` int(11) DEFAULT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `luggage` (`registrationnr`, `date`, `flightnr`, `labelnr`, `location_found`, `destination`, `luggage_type`, `brand`, `primary_color`, `secondary_color`, `notes`, `size`, `weight`, `case_type`, `customer_firstname`, `customer_preposition`, `customer_lastname`, `case_status`, `airport_IATA`, `customer_customernr`) VALUES\n"
                + "(1, '2018-01-15 23:47:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Overduidelijke schade!', NULL, NULL, 3, 'Joris', '', 'Ebbelaar', 1, 'AMS', 1),\n"
                + "(2, '2018-01-15 23:49:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Geen opmerkingen.', NULL, NULL, 3, 'Jan', 'van', 'Test', 1, 'RTM', 2),\n"
                + "(3, '2018-01-15 23:50:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Schade haast niet zichtbaar.', NULL, NULL, 3, 'Loes', 'van', 'Ackema', 1, 'AMS', 3),\n"
                + "(4, '2018-01-15 23:53:15', '30122', '23145662', 'Toilet', 'Amsterdam', 'Bag', 'Eastpack', 'Black', 'Orange', '', '100x80x20', '5', 1, 'Jan', 'van', 'Test', 1, 'AMS', NULL),\n"
                + "(5, '2018-01-15 23:57:49', '43123', '562716238', 'Belt-03', 'Haarlem', 'Suitcase', 'louis vuitton', 'Black', 'Yellow', '', '50x50x50', '15', 1, 'Loes', 'van', 'Ackema', 1, 'AMS', NULL),\n"
                + "(6, '2018-01-16 00:00:00', '321356', '34256745', NULL, 'Madrid', 'Suitcase', 'samsonite', 'Black', 'Black', 'Rood lint om het handvat.', NULL, NULL, 2, 'Joris', '', 'Ebbelaar', 1, 'AMS', NULL),\n"
                + "(7, '2018-01-16 00:04:28', '32145', '123451523', 'Belt-02', 'Amsterdam', 'Suitcase', 'samsonite', 'Black', 'Black', '', '50x50x50', '25', 1, 'Jan', 'van', 'Straaten', 1, 'AMS', NULL),\n"
                + "(8, '2018-01-16 00:05:24', '', '', 'Belt-01', '', 'Suitcase', 'samsonite', 'Black', 'Black', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),\n"
                + "(9, '2018-01-16 00:05:49', '', '', 'Belt-01', '', 'Bagpack', 'eastpack', 'Black', 'Darkblue', '', '100x50x20', '20', 1, '', '', '', 1, 'RTM', NULL),\n"
                + "(10, '2018-01-16 00:07:29', '12345', '12345678', 'Belt-01', '', 'Bag', 'samsonite', 'Darkbrown', 'Darkbrown', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),\n"
                + "(11, '2018-01-16 00:08:28', '12345', '12345678', 'Belt-01', '', 'Suitcase', 'samsonite', 'Darkbrown', 'Darkbrown', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),\n"
                + "(12, '2018-01-16 00:09:27', '12345', '8674325', NULL, 'Portugal', 'Bagpack', 'samsonite', 'Blue', 'Blue', NULL, NULL, NULL, 2, 'Jan', 'van', 'Test', 1, 'RTM', NULL)");

        myJDBC.executeUpdateQuery("CREATE TABLE `luggage_damaged` (\n"
                + "  `image01` longblob,\n"
                + "  `image02` longblob,\n"
                + "  `image03` longblob,\n"
                + "  `Luggage_registrationnr` int(11) NOT NULL\n"
                + ") ");
        myJDBC.executeUpdateQuery("INSERT INTO `luggage_damaged` (`image01`, `image02`, `image03`, `Luggage_registrationnr`) VALUES\n"
                + "(NULL, NULL, NULL, 1),\n"
                + "(NULL, NULL, NULL, 2),\n"
                + "(NULL, NULL, NULL, 3)");

        myJDBC.executeUpdateQuery("CREATE TABLE `matches` (\n"
                + "  `registrationnr` int(11) NOT NULL,\n"
                + "  `selectedRegNr` int(11) NOT NULL,\n"
                + "  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  `case_status` tinyint(1) NOT NULL DEFAULT '0'\n"
                + ") ");

        myJDBC.executeUpdateQuery("ALTER TABLE `account`\n"
                + "  ADD PRIMARY KEY (`Employee_code`),\n"
                + "  ADD KEY `fk_Account_Medewerker_idx` (`Employee_code`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `airport`\n"
                + "  ADD PRIMARY KEY (`IATA`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `changes`\n"
                + "  ADD PRIMARY KEY (`Employee_code`,`Luggage_registrationnr`),\n"
                + "  ADD KEY `fk_Changes_lost_Medewerker1_idx` (`Employee_code`),\n"
                + "  ADD KEY `fk_Changes_lost_Verloren_bagage1_idx` (`Luggage_registrationnr`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `customer`\n"
                + "  ADD PRIMARY KEY (`customernr`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `employee`\n"
                + "  ADD PRIMARY KEY (`code`),\n"
                + "  ADD KEY `fk_Medewerker_Luchthaven1_idx` (`Luchthaven_IATA`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `luggage`\n"
                + "  ADD PRIMARY KEY (`registrationnr`),\n"
                + "  ADD KEY `fk_airport_IATA_idx` (`airport_IATA`),\n"
                + "  ADD KEY `fk_luggage_customer1_idx` (`customer_customernr`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `luggage_damaged`\n"
                + "  ADD PRIMARY KEY (`Luggage_registrationnr`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `matches`\n"
                + "  ADD PRIMARY KEY (`registrationnr`)");
        myJDBC.executeUpdateQuery("ALTER TABLE `account`\n"
                + "  ADD CONSTRAINT `fk_Account_Medewerker` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION");
        myJDBC.executeUpdateQuery("ALTER TABLE `changes`\n"
                + "  ADD CONSTRAINT `fk_Changes_lost_Medewerker1` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
                + "  ADD CONSTRAINT `fk_Changes_lost_Verloren_bagage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION");
        myJDBC.executeUpdateQuery("ALTER TABLE `employee`\n"
                + "  ADD CONSTRAINT `fk_Medewerker_Luchthaven1` FOREIGN KEY (`Luchthaven_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION");
        myJDBC.executeUpdateQuery("ALTER TABLE `luggage`\n"
                + "  ADD CONSTRAINT `fk_airport_IATA` FOREIGN KEY (`airport_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
                + "  ADD CONSTRAINT `fk_luggage_customer1` FOREIGN KEY (`customer_customernr`) REFERENCES `customer` (`customernr`) ON DELETE NO ACTION ON UPDATE NO ACTION");
        myJDBC.executeUpdateQuery("ALTER TABLE `luggage_damaged`\n"
                + "  ADD CONSTRAINT `fk_Luggage_damaged_Luggage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION");
        myJDBC.executeUpdateQuery("ALTER TABLE `matches`\n"
                + "  ADD CONSTRAINT `FK_matchingNr` FOREIGN KEY (`registrationnr`) REFERENCES `luggage` (`registrationnr`)");
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Connection getConnection() {
        return connection;
    }

    public void getRegistrationnr() {
        System.out.print(registrationNr);
    }

    public int getRegNrDamaged() {
        return registrationNrDamaged;
    }

    public int getRegNrLost() {
        return registrationNrLost;
    }

    public int getRegNrFound() {
        return registrationNrFound;
    }

    public int getLuggageRegistrationNr() {
        return luggageRegistrationNr;
    }

    public void setLuggageRegistrationNr(int luggageRegistrationNr) {
        this.luggageRegistrationNr = luggageRegistrationNr;
    }

    public void newRegnrDamagedLuggage() throws SQLException {
        Connection conn = connection;

        // select query for max registationNr
        String selectMaxRegNR = "SELECT MAX(registrationnr) FROM luggage";

        // insert query to create new registrationNr
        String insertNewRegNR = "INSERT INTO luggage (registrationnr, case_type) VALUES (?, 3)";

//        String INSERT_PICTURE = "insert into test(id, image01, image02, image03) values (?, ?, ?, ?)";
        // prepared statement
        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(selectMaxRegNR);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                registrationNr = result.getInt(1);

                // increment registrationNr by 1 for a new registrationNr
                registrationNr++;
                registrationNrDamaged = registrationNr;

                try {
                    // execute insert query for new registrationNr
                    // TODO: INSERT more luggage details    
                    PreparedStatement ps02 = null;
                    ps02 = conn.prepareStatement(insertNewRegNR);
                    ps02.setInt(1, registrationNr);
                    ps02.executeUpdate();
                    conn.commit();
                } finally {
                }

            }
        } finally {
//            System.out.print(registrationNr);

        }
    }

    public void newRegnrLostLuggage() throws SQLException {
        Connection conn = connection;

        // select query for max registationNr
        String selectMaxRegNR = "SELECT MAX(registrationnr) FROM luggage";

        // insert query to create new registrationNr
        String insertNewRegNR = "INSERT INTO luggage (registrationnr, case_type) VALUES (?, 2)";

//        String INSERT_PICTURE = "insert into test(id, image01, image02, image03) values (?, ?, ?, ?)";
        // prepared statement
        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(selectMaxRegNR);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                registrationNr = result.getInt(1);

                // increment registrationNr by 1 for a new registrationNr
                registrationNr++;
                luggageRegistrationNr = registrationNr;

                try {
                    // execute insert query for new registrationNr
                    // TODO: INSERT more luggage details    
                    PreparedStatement ps02 = null;
                    ps02 = conn.prepareStatement(insertNewRegNR);
                    ps02.setInt(1, registrationNr);
                    ps02.executeUpdate();
                    conn.commit();
                } finally {
                }

            }
        } finally {

        }
    }

    public void newRegnrFoundLuggage() throws SQLException {
        Connection conn = connection;

        // select query for max registationNr
        String selectMaxRegNR = "SELECT MAX(registrationnr) FROM luggage";

        // insert query to create new registrationNr
        String insertNewRegNR = "INSERT INTO luggage (registrationnr, case_type) VALUES (?, 1)";
        String insertNewRegNR2 = "INSERT INTO changes (Luggage_registrationnr, Employee_code, changeid) VALUES (?, ?, 0)";

//        String INSERT_PICTURE = "insert into test(id, image01, image02, image03) values (?, ?, ?, ?)";
        // prepared statement
        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(selectMaxRegNR);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                registrationNr = result.getInt(1);

                // increment registrationNr by 1 for a new registrationNr
                registrationNr++;
                luggageRegistrationNr = registrationNr;

                try {
                    // execute insert query for new registrationNr
                    // TODO: INSERT more luggage details    
                    PreparedStatement ps02 = null;
                    ps02 = conn.prepareStatement(insertNewRegNR);
                    ps02.setInt(1, registrationNr);
                    ps02.executeUpdate();
                    conn.commit();

                    PreparedStatement ps03 = null;
                    ps03 = conn.prepareStatement(insertNewRegNR2);
                    ps03.setInt(1, registrationNr);
                    ps03.setInt(2, data.getEmployeeNr());
                    ps03.executeUpdate();
                    conn.commit();
                } finally {
                }

            }
        } finally {

        }
    }

    public void getNewEmptyFoundLuggageNr() throws SQLException {
        Connection conn = connection;

        // select query for max registationNr
        String selectMaxRegNR = "SELECT MAX(registrationnr) FROM luggage";

        // insert query to create new registrationNr
        String insertNewRegNR = "INSERT INTO luggage (registrationnr, case_type) VALUES (?, 1)";

//        String INSERT_PICTURE = "insert into test(id, image01, image02, image03) values (?, ?, ?, ?)";
        // prepared statement
        PreparedStatement ps = null;
        try {
            // set autocommit false
            conn.setAutoCommit(false);

            // add query to prepared statement
            ps = conn.prepareStatement(selectMaxRegNR);

            // execute prepared statement
            ResultSet result = ps.executeQuery();

            // get results from the query
            if (result.next()) {
                registrationNr = result.getInt(1);

                // increment registrationNr by 1 for a new registrationNr
                registrationNr++;
                luggageRegistrationNr = registrationNr;
            }
        } finally {

        }
    }
}
