import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private final String URL = "";      //rightclick an SQL connection and click "Copy JDBC Connection String to Clickboard"
    private final String USER = "root";
    private final String PASSWORD = ""; //whatever password you have
    //private static final String PASSWORD = "[insert password]";
    //private static final String PASSWORD = "FonupaK518_";

    private Connection conn = null;

    //this is contrsutctor
    public DbConnection(){
        System.out.println("Testing Connection...");
        getConnection();
        System.out.println("Initializing Database"); //TODO placeholders
        initializeDatabase();
    }

    //establishes a connection with the database
    public Connection getConnection(){
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                System.err.println("MySQL JDBC Driver not found on classpath: " + cnfe.getMessage());
                return null;
            }

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // make sure autocommit is enabled (so inserts are committed immediately)
            conn.setAutoCommit(true);
            System.out.println("Connection established successfully (autocommit=" + conn.getAutoCommit() + ").");
        } catch (SQLException e) {
            System.err.println("Failed to establish connection: " + e.getMessage());
            e.printStackTrace();
            // System.getLogger(DbConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        }
        return conn;
    }


    //database and table creation statements
    String CreateDatabase = "CREATE SCHEMA IF NOT EXISTS ccinfom_db";
    String SelectSchema = "USE ccinfom_db";

    String CreateTableCitizen = 
    """
    CREATE TABLE IF NOT EXISTS Citizen (
    AccountID INT NOT NULL AUTO_INCREMENT, 
    FirstName VARCHAR(50), 
    LastName VARCHAR(50), 
    ContactNbr BIGINT, 
    Email VARCHAR(100), 
    Address VARCHAR(250), 
    Password VARCHAR(50),
    PRIMARY KEY (AccountID))
    """;

    String CreateTableStaff = 
    """
    CREATE TABLE IF NOT EXISTS Staff (
    StaffID INT AUTO_INCREMENT, 
    DepartmentID INT NOT NULL, 
    FirstName VARCHAR(50), 
    LastName VARCHAR(50), 
    ContactNbr BIGINT, 
    Availability VARCHAR(50), 
    Password VARCHAR(50),
    PRIMARY KEY (StaffID),
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID))
    """;

    String CreateTableService = 
    """
    CREATE TABLE IF NOT EXISTS Service (
    ServiceID INT AUTO_INCREMENT, 
    ServiceName VARCHAR(100), 
    ServiceType VARCHAR(50), 
    DepartmentID INT,
    PRIMARY KEY (ServiceID),
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID))
    """;

    String CreateTableServiceRequest = 
    """
    CREATE TABLE IF NOT EXISTS ServiceRequest (
    RequestID INT AUTO_INCREMENT, 
    AccountID INT NOT NULL, 
    StaffID INT, 
    ServiceID INT NOT NULL, 
    DateFiled DATE, 
    Address VARCHAR(250), 
    ServiceDesc VARCHAR(999), 
    RequestStatus VARCHAR(10), 
    DateResolved DATE,
    PRIMARY KEY (RequestID),
    FOREIGN KEY (AccountID) REFERENCES Citizen(AccountID),
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID))
    """;

    String CreateTableDepartment = 
    """
    CREATE TABLE IF NOT EXISTS Department (
    DepartmentID INT, 
    DepartmentName VARCHAR(100),
    PRIMARY KEY (DepartmentID))
    """;

    String CreateTableAssignmentResolution = """
    CREATE TABLE IF NOT EXISTS AssignmentResolution (
    ResolutionID INT AUTO_INCREMENT,
    RequestID INT NOT NULL,
    StaffID INT NOT NULL,
    ResolutionNote VARCHAR(1000),
    ResolutionDate DATE NOT NULL,
    StatusAfterAction VARCHAR(20),
    PRIMARY KEY (ResolutionID),
    FOREIGN KEY (RequestID) REFERENCES ServiceRequest(RequestID),
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID))
    """;

    String CreateTableReopenRequest = """
    CREATE TABLE IF NOT EXISTS ReopenRequest (
    ReopenID INT AUTO_INCREMENT,
    RequestID INT NOT NULL,
    CitizenID INT NOT NULL,
    Reason VARCHAR(1000) NOT NULL,
    ReopenDate DATE NOT NULL,
    PRIMARY KEY (ReopenID),
    FOREIGN KEY (RequestID) REFERENCES ServiceRequest(RequestID),
    FOREIGN KEY (CitizenID) REFERENCES Citizen(AccountID))
    """;

    //schema and table initializer
    //TODO Kuya Drei told me to make it private
    public void initializeDatabase() {
        if (conn == null) {
            System.err.println("Cannot initialize database: connection is null.");
            return;
        }
        try (Statement stmt = conn.createStatement()) {
            // CreateDatabase is harmless if DB exists; we connect directly to ccinfom_db via URL,
            // so no need to run "USE ccinfom_db". Keep create schema so first-run still works.
            stmt.execute(CreateDatabase);
            stmt.execute(CreateTableCitizen);
            System.out.println("CreateTableCitizen");
            stmt.execute(CreateTableDepartment);
            System.out.println("CreateTableDepartment");
            stmt.execute(CreateTableService);
            System.out.println("CreateTableService");
            stmt.execute(CreateTableStaff);
            System.out.println("CreateTableStaff");
            stmt.execute(CreateTableServiceRequest);
            System.out.println("CreateTableServiceRequest");
            stmt.execute(CreateTableAssignmentResolution);
            System.out.println("CreateTableAssignmentResolution");
            stmt.execute(CreateTableReopenRequest);
            System.out.println("CreateTableReopenRequest");
            System.out.println("Database and tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }



    //methods for user login and registration
    public void CitizenRegister(String firstName, String lastName, long contactNbr, String email, String address, String password) {
        String insertCitizenSQL = """
        INSERT INTO Citizen (
        FirstName, 
        LastName, 
        ContactNbr, 
        Email, 
        Address, 
        Password) 
        VALUES (?, ?, ?, ?, ?, ?)                
                """;
        
        if (conn == null) {
            System.err.println("Cannot register: DB connection is null.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(insertCitizenSQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setLong(3, contactNbr);
            pstmt.setString(4, email);
            pstmt.setString(5, address);
            pstmt.setString(6, password);
            int rows = pstmt.executeUpdate();
            System.out.println("Registration: rows affected = " + rows);
            if (rows == 0) {
                System.err.println("Insert returned 0 rows. Check table/schema permissions and that 'USE ccinfom_db' applied.");
            }
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User CitizenLogin(String email, String password){
        String selectCitizenSQL = """
        SELECT * 
        FROM Citizen 
        WHERE Email = ? AND Password = ?
                """;
        // use try-with-resources to ensure PreparedStatement and ResultSet are closed
        try (PreparedStatement pstmt = conn.prepareStatement(selectCitizenSQL)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userID = rs.getInt("AccountID");
                    String userLastName = rs.getString("LastName");
                    String userFirstName = rs.getString("FirstName");
                    long userNbr = rs.getLong("ContactNbr");
                    System.out.println("Login successful. Welcome, " + userFirstName + " " + userLastName + "!");
                    return new User(userID, userLastName, userFirstName, userNbr);
                } else {
                    System.out.println("Login failed. Invalid email or password.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during citizen login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //gets CitizenLoginName
    //i know it's inefficient and I copied it off of the function above but shhhhhh
    public String getCitizenLoginName(String email, String password){
        String getCitizenName = """
        SELECT * 
        FROM Citizen 
        WHERE Email = ? AND Password = ?
                """;
        try{
            PreparedStatement pstmt = conn.prepareStatement(getCitizenName);

            pstmt.setString(1, email);
            pstmt.setString(2, password);


            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                
                //System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!");
                return "Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!";
            }
            else{
                return "Login failed. Invalid email or password.";
            }
            //surely it won't cause an invalid login error
        } catch (SQLException e){
            //System.err.println("Error during citizen login: " + e.getMessage());
            return "Error during citizen login: " + e.getMessage();
        }
    }

    public void StaffRegister(String firstName, String lastName, long contactNbr, String availability, String password) {
        String insertStaffSQL = """
        INSERT INTO Staff (
        FirstName, 
        LastName, 
        ContactNbr, 
        Availability, 
        Password) 
        VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(insertStaffSQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setLong(3, contactNbr);
            pstmt.setString(4, availability);
            pstmt.setString(5, password);
            int rows = pstmt.executeUpdate();
            System.out.println("Staff registration: rows affected = " + rows);
        } catch (SQLException e) {
            System.err.println("Registration Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public boolean StaffLogin(int staffID, String password){
        String selectStaffSQL = """
        SELECT * 
        FROM Staff 
        WHERE StaffID = ? AND Password = ?
                """;
        try{
            PreparedStatement pstmt = conn.prepareStatement(selectStaffSQL);

            pstmt.setInt(1, staffID);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!");
                return true;
            } else {
                System.out.println("Login failed. Invalid Staff ID or password.");
                return false;
            }
        } catch (SQLException e){
            System.err.println("Error during staff login: " + e.getMessage());
            return false;
        }
    }



    //methods for inserting data into service, departments and service requests
    public void CreateService(String serviceName,String serviceType, int departmentID) {
        String insertServiceSQL = """
        INSERT INTO Service (
        ServiceName, 
        ServiceType, 
        DepartmentID) 
        VALUES (?, ?, ?)
                """;
        try{
             PreparedStatement pstmt = conn.prepareStatement(insertServiceSQL); 

            pstmt.setString(1, serviceName);
            pstmt.setString(2, serviceType);
            pstmt.setInt(3, departmentID);

            pstmt.executeUpdate();

            System.out.println("Service creation successful.");
        } catch (SQLException e) {
            System.err.println("Service creation error: " + e.getMessage());
        }
    }

    public void CreateDepartment(int departmentID, String departmentName) {
        String insertDepartmentSQL = """
        INSERT INTO Department (
        DepartmentID, 
        DepartmentName) 
        VALUES (?, ?)
                """;
        try{
            PreparedStatement pstmt = conn.prepareStatement(insertDepartmentSQL); 

            pstmt.setInt(1, departmentID);
            pstmt.setString(2, departmentName);

            pstmt.executeUpdate();

            System.out.println("Department creation successful.");
        } catch (SQLException e) {
            System.err.println("Department creation error: " + e.getMessage());
        }
    }


    public void FileServiceRequest(int accountID, int staffID, int serviceID, String dateFiled, 
                                    String address, String serviceDesc){
        String insertServiceRequestSQL = """
        INSERT INTO ServiceRequest (
        CitizenID, 
        StaffID, 
        ServiceID, 
        DateFiled, 
        Address, 
        ServiceDesc, 
        RequestStatus) 
        VALUES (?, ?, ?, ?, ?, ?, ?)
                """;


        try (PreparedStatement pstmt = conn.prepareStatement(insertServiceRequestSQL)) {
            pstmt.setInt(1, accountID);
            pstmt.setInt(2, staffID);
            pstmt.setInt(3, serviceID);
            pstmt.setString(4, dateFiled);
            pstmt.setString(5, address);
            pstmt.setString(6, serviceDesc);
            pstmt.setString(7, "Pending");
            int rows = pstmt.executeUpdate();
            System.out.println("Service request filed. rows = " + rows);
        } catch (SQLException e) {
            System.err.println("Error filing service request: " + e.getMessage());
            e.printStackTrace();
        }
    
    
    }

    


    
}