import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbConnection {
    // connect directly to the application schema so inserts go to the correct DB
    private final String URL = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private final String USER = "root";
    // private final String PASSWORD = "wispY_346126";
    private final String PASSWORD = "-Keion12012004";

    private Connection conn = null;

    // constructor
    public DbConnection(){
        System.out.println("Testing Connection...");
        getConnection();
        System.out.println("Initializing Database");
        initializeDatabase();
    }

    // establishes a connection with the database
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
        }
        return conn;
    }


    // database and table creation statements
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
    PRIMARY KEY (AccountID));
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
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID));
    """;

    String CreateTableService =
    """
    CREATE TABLE IF NOT EXISTS Service (
    ServiceID INT NOT NULL, 
    ServiceName VARCHAR(100), 
    ServiceType VARCHAR(50), 
    DepartmentID INT,
    PRIMARY KEY (ServiceID),
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID));
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
    FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID));
    """;

    String CreateTableDepartment =
    """
    CREATE TABLE IF NOT EXISTS Department (
    DepartmentID INT NOT NULL, 
    DepartmentName VARCHAR(100),
    PRIMARY KEY (DepartmentID));
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
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID));
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
    FOREIGN KEY (CitizenID) REFERENCES Citizen(AccountID));
    """;

    //Scripts that insert data into tables

String InsertDepartments = """
    INSERT INGORE INTO Department (DepartmentID, DepartmentName) VALUES
    (1, 'Department of Engineering and Public Works'),
    (2, 'City General Services Office'),
    (3, 'Department of Public Services'),
    (4, 'Public Recreations Bureau'),
    (5, 'Parks Development Office');
    """;

String InsertServicesDept1 = """
    INSERT IGNORE INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
    (1, 'Road Damage Inspection Request', 'Inspection', 1),
    (2, 'Drainage or Sewerage Cleaning Request', 'Maintenance', 1),
    (3, 'Street Signage Issue Complaint', 'Complaint', 1),
    (4, 'Infrastructure Damage Assessment', 'Inspection', 1);
    """;

String InsertServicesDept2 = """
    INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
    (5, 'City Furniture Repair Request', 'Repair', 2),
    (6, 'Hazardous Waste Disposal Request', 'Cleaning', 2),
    (7, 'City Building Maintenance Request', 'Maintenance', 2);
    """;

String InsertServicesDept3 = """
    INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
    (8, 'Garbage Collection Request', 'Cleaning', 3),
    (9, 'Illegal Dumping Complaint', 'Complaint', 3),
    (10, 'Street Sweeping Request', 'Cleaning', 3),
    (11, 'Public Trash Bin Maintenance Request', 'Maintenance', 3);
    """;

String InsertServicesDept4 = """
    INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
    (12, 'Playground Equipment Maintenance Request', 'Maintenance', 4),
    (13, 'Recreational Program Registration Inquiry', 'Information', 4);
    """;

String InsertServicesDept5 = """
    INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
    (14, 'Park Grounds Maintenance Request', 'Maintenance', 5),
    (15, 'Tree Trimming or Pruning Request', 'Maintenance', 5),
    (16, 'Park Facility Damage Inspection', 'Inspection', 5);
    """;




    // schema and table initializer
    public void initializeDatabase() {
        if (conn == null) {
            System.err.println("Cannot initialize database: connection is null.");
            return;
        }
        try (Statement stmt = conn.createStatement()) {
            // create schema and tables (safe if already present)
            stmt.execute(CreateDatabase);
            System.out.println("CreateDatabase");
            stmt.execute(SelectSchema);
            System.out.println("SelectSchema");
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
            stmt.execute(InsertDepartments);
            System.out.println("InsertDepartments");
            stmt.execute(InsertServicesDept1);
            System.out.println("InsertServicesDept1");
            stmt.execute(InsertServicesDept2);
            System.out.println("InsertServicesDept2");
            stmt.execute(InsertServicesDept3);
            System.out.println("InsertServicesDept3");
            stmt.execute(InsertServicesDept4);
            System.out.println("InsertServicesDept4");
            stmt.execute(InsertServicesDept5);
            System.out.println("InsertServicesDept5");
            System.out.println("Database and tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // methods for user login and registration
    public boolean CitizenRegister(String firstName, String lastName, long contactNbr, String email, String address, String password) {
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

        // attempt to reconnect if conn is null
        if (conn == null) {
            System.err.println("Connection was null; attempting to reconnect...");
            getConnection();
            if (conn == null) {
                System.err.println("Cannot register: DB connection is null after reconnect attempt.");
                return false;
            }
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
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User CitizenLogin(String email, String password){
        String selectCitizenSQL = """
        SELECT * 
        FROM Citizen 
        WHERE Email = ? AND Password = ?
                """;
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

    // gets CitizenLoginName
    public String getCitizenLoginName(String email, String password){
        String getCitizenName = """
        SELECT * 
        FROM Citizen 
        WHERE Email = ? AND Password = ?
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(getCitizenName)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!";
                } else {
                    return "Login failed. Invalid email or password.";
                }
            }
        } catch (SQLException e){
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

    public User StaffLogin(int staffID, String password){
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
                int userID = rs.getInt("StaffID");
                String userLastName = rs.getString("LastName");
                String userFirstName = rs.getString("FirstName");
                int userNbr = rs.getInt("ContactNbr");

                System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!");

                return new User(userID, userLastName, userFirstName, userNbr);
            } else {
                System.out.println("Login failed. Invalid Staff ID or password.");
                return null;
            }
        } catch (SQLException e){
            System.err.println("Error during staff login: " + e.getMessage());
            return null;
        }
    }


    //methods for inserting data into service, departments and service requests
    public boolean CreateService(String serviceName,String serviceType, int departmentID) {
        String insertServiceSQL = """
        INSERT INTO Service (
        ServiceName, 
        ServiceType, 
        DepartmentID) 
        SELECT ?, ?, ? 
        WHERE NOT EXISTS
        (SELECT 1
        FROM Service
        WHERE ServiceName = ?
        AND DepartmentID = ?)
                """;
        try{
             PreparedStatement pstmt = conn.prepareStatement(insertServiceSQL); 

            pstmt.setString(1, serviceName);
            pstmt.setString(2, serviceType);
            pstmt.setInt(3, departmentID);
            pstmt.setString(4, serviceName);
            pstmt.setInt(5, departmentID);

            pstmt.executeUpdate();

            System.out.println("Service creation successful.");
            return true;
        } catch (SQLException e) {
            System.err.println("Service creation error: " + e.getMessage());
            return false;
        }
    }

    public boolean CreateDepartment(int departmentID, String departmentName) {
        String insertDepartmentSQL = """
        INSERT INTO Department (
        DepartmentID, 
        DepartmentName) 
        SELECT ?, ?
        WHERE NOT EXISTS (
        SELECT 1
        FROM Department
        WHERE DepartmentID = ?)
                """;
        try{
            PreparedStatement pstmt = conn.prepareStatement(insertDepartmentSQL); 

            pstmt.setInt(1, departmentID);
            pstmt.setString(2, departmentName);
            pstmt.setInt(3, departmentID);

            pstmt.executeUpdate();

            System.out.println("Department creation successful.");
            return true;
        } catch (SQLException e) {
            System.err.println("Department creation error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Helpers
     */

    private Date parseDate(String dateStr){
        // Assumes dateStr is in ISO format: "yyyy-MM-dd"
        if (dateStr == null) {
            return null;
        }
        try{
            return Date.valueOf(dateStr);
        } catch (IllegalArgumentException e){
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }




    /*
     * Methods used in Transaction 1
     * Filing a service request
     */

    public boolean validateCitizen(int citizenID){
        String validateCitizenSQL = """
        SELECT 1 
        FROM Citizen 
        WHERE AccountID = ?
                """;
        try {
            PreparedStatement pstmt = conn.prepareStatement(validateCitizenSQL);
            pstmt.setInt(1, citizenID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error validating citizen ID: " + e.getMessage());
            return false;
        }
    }

    public boolean validateService(int serviceID){
        String validateServiceSQL = """
        SELECT 1
        FROM Service
        WHERE ServiceID = ?
                """;
        try {
            PreparedStatement pstmt = conn.prepareStatement(validateServiceSQL);
            pstmt.setInt(1, serviceID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error validating service ID: " + e.getMessage());
            return false;
        }
    }
    public boolean FileServiceRequest(int accountID, int staffID, int serviceID, String dateFiled, 
                                    String address, String serviceDesc){
        
        if (!validateCitizen(accountID)){
            System.out.println("Invalid Citizen ID.");
            return false;
        }
        if (!validateService(serviceID)){
            System.out.println("Invalid Service ID.");
            return false;
        }

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

        
        try {
            PreparedStatement pstmt = conn.prepareStatement(insertServiceRequestSQL);

            pstmt.setInt(1, accountID);
            pstmt.setNull(2, Types.INTEGER); // No staff assigned
            pstmt.setInt(3, serviceID);
            pstmt.setString(4, dateFiled);
            pstmt.setString(5, address);
            pstmt.setString(6, serviceDesc);
            pstmt.setString(7, "Pending");

            pstmt.executeUpdate();

            System.out.println("Service request filed.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error filing service request: " + e.getMessage());
            return false;
        }
        
    }
    
        /*
         * Methods used in Transaction 2
         * Updating service request status
         */

    public boolean checkStaffAvailability(int staffID){
        String sql = """
            SELECT Availability 
            FROM Staff
            WHERE StaffID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, staffID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Availability") > 0;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking staff availability: " + e.getMessage());
            return false;
        }
    }


    public boolean validateDepartmentMatch(int staffID, int serviceID){
        String sql = """
            SELECT 1
            FROM Staff s
            JOIN Service v ON s.DepartmentID = v.DepartmentID
            WHERE s.StaffID = ? AND v.ServiceID = ?
            """;

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, staffID);
            pstmt.setInt(2, serviceID);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Error validating department match: " + e.getMessage());
            return false;
        }
    }

    public boolean assignStaffToRequest(int requestID, int staffID){
        
        if (!checkStaffAvailability(staffID)){
            System.out.println("Staff is not available.");
            return false;
        }

        
        int serviceID = getServiceIDFromRequest(requestID);
        if (serviceID == -1){
            System.out.println("Invalid request ID.");
            return false;
        }

        
        if (!validateDepartmentMatch(staffID, serviceID)){
            System.out.println("Staff department does not match service department.");
            return false;
        }

        
        String sql = """
            UPDATE ServiceRequest
            SET StaffID = ?, RequestStatus = 'Assigned'
            WHERE RequestID = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, staffID);
            pstmt.setInt(2, requestID);
            pstmt.executeUpdate();

            decreaseStaffAvailability(staffID);
            return true;

        } catch (SQLException e) {
            System.err.println("Error assigning staff: " + e.getMessage());
            return false;
        }
    }


    public int getServiceIDFromRequest(int requestID){
        String sql = """
            SELECT ServiceID
            FROM ServiceRequest
            WHERE RequestID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("ServiceID");
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Error fetching service ID: " + e.getMessage());
            return -1;
        }
    }

    public void decreaseStaffAvailability(int staffID){
        String sql = """
            UPDATE Staff
            SET Availability = Availability - 1
            WHERE StaffID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, staffID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error decreasing staff availability: " + e.getMessage());
        }
    }


    public void increaseStaffAvailability(int staffID){
        String sql = """
            UPDATE Staff
            SET Availability = Availability + 1
            WHERE StaffID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, staffID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error increasing staff availability: " + e.getMessage());
        }
    }



    public boolean resolveRequest(int requestID, int staffID, String resolutionDate){
        String sql = """
            UPDATE ServiceRequest
            SET RequestStatus = 'Resolved', 
                DateResolved = ?
            WHERE RequestID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resolutionDate);
            pstmt.setInt(2, requestID);
            pstmt.executeUpdate();

            insertAssignmentResolution(requestID, staffID, resolutionDate);
            increaseStaffAvailability(staffID);

            return true;
        } catch (SQLException e) {
            System.err.println("Error resolving request: " + e.getMessage());
            return false;
        }
    }


    public boolean insertAssignmentResolution(int requestID, int staffID, String resolutionDate){
        String sql = """
            INSERT INTO AssignmentResolution (
                RequestID,
                StaffID,
                ResolutionDate
            )
            VALUES (?, ?, ?)
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            pstmt.setInt(2, staffID);
            pstmt.setString(3, resolutionDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting resolution: " + e.getMessage());
            return false;
        }
    }


    /*
     * Methods used in Transaction 3
     * Reopening a Resolved Service Request
     */


    public boolean validateReopenEligibility(int requestID){
        String sql = """
            SELECT RequestStatus
            FROM ServiceRequest
            WHERE RequestID = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("RequestStatus").equals("Resolved");
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error validating reopen eligibility: " + e.getMessage());
            return false;
        }
    }


    public boolean reopenRequest(int requestID, int citizenID, String reason, String reopenDate){
        if (!validateReopenEligibility(requestID)){
            System.out.println("Request cannot be reopened.");
            return false;
        }

        String sql = """
            UPDATE ServiceRequest
            SET RequestStatus = 'Reopened',
                StaffID = NULL
            WHERE RequestID = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            pstmt.executeUpdate();

            recordReopenReason(requestID, citizenID, reason, reopenDate);
            return true;
        } catch (SQLException e) {
            System.err.println("Error reopening request: " + e.getMessage());
            return false;
        }
    }


    public boolean recordReopenReason(int requestID, int citizenID, String reason, String reopenDate){
        String sql = """
            INSERT INTO ReopenRequest (
                RequestID,
                CitizenID,
                Reason,
                ReopenDate
            )
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            pstmt.setInt(2, citizenID);
            pstmt.setString(3, reason);
            pstmt.setString(4, reopenDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting reopen request: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRequestStatus(int requestID, String newStatus) {
        if (conn == null) {
            System.err.println("Cannot update request status: DB connection is null.");
            return false;
        }
        String sql = "UPDATE ServiceRequest SET RequestStatus = ? WHERE RequestID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, requestID);
            int rows = pstmt.executeUpdate();
            System.out.println("updateRequestStatus: rows affected = " + rows);
            if (rows > 0 && "Resolved".equalsIgnoreCase(newStatus)) {
                try (PreparedStatement p2 = conn.prepareStatement("UPDATE ServiceRequest SET DateResolved = CURDATE() WHERE RequestID = ?")) {
                    p2.setInt(1, requestID);
                    p2.executeUpdate();
                }
            }
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating request status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getServiceNames() {
        String sql = "SELECT ServiceName FROM Service ORDER BY ServiceName";
        List<String> serviceNames = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                serviceNames.add(rs.getString("ServiceName"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching service names: " + e.getMessage());
            e.printStackTrace();
        }
        return serviceNames;
    }

    public void InputServiceRequest(int accountID, int serviceID, int departmentID, String address, String serviceDesc) {
        int staffID = findAvailableStaff(departmentID);   // <– NEW LOGIC

        // dateFiled
        String currentDate = LocalDate.now().toString();

        // call the main method
        FileServiceRequest(accountID, staffID, serviceID, currentDate, address, serviceDesc);
    }

    public int findAvailableStaff(int departmentID) {
        String sql = """
            SELECT StaffID
            FROM Staff
            WHERE DepartmentID = ?
            AND Availability > 0
            LIMIT 1
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("StaffID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for available staff: " + e.getMessage());
        }

        return 0; // no available staff → unassigned
    }

    public List<Map<String,Object>> generateServicesRequestedReport(String yearMonth) {
        // yearMonth format: "2025-11"
        String sql = """
            SELECT s.ServiceName, COUNT(*) AS RequestCount
            FROM ServiceRequest sr
            JOIN Service s ON sr.ServiceID = s.ServiceID
            WHERE DATE_FORMAT(sr.DateFiled, '%Y-%m') = ?
            GROUP BY s.ServiceName
            """;
        List<Map<String,Object>> out = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, yearMonth);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> row = new HashMap<>();
                    row.put("ServiceName", rs.getString("ServiceName"));
                    row.put("RequestCount", rs.getInt("RequestCount"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("generateServicesRequestedReport error: " + e.getMessage());
        }
        return out;
    }

    // Services Status Report: count of each status (Pending, Ongoing, Resolved, Re-opened) for a given period (week or month)
    public List<Map<String,Object>> generateServiceStatusReport(String periodType, String periodValue) {
        // periodType: "month" or "week"
        String whereClause;
        if ("week".equalsIgnoreCase(periodType)) {
            // periodValue expected like '2025-11-17' (Monday) -> use YEARWEEK
            whereClause = "AND YEARWEEK(sr.DateFiled, 1) = YEARWEEK(?, 1)";
        } else {
            // default to month: periodValue like '2025-11'
            whereClause = "AND DATE_FORMAT(sr.DateFiled, '%Y-%m') = ?";
        }
        String sql = "SELECT sr.RequestStatus, COUNT(*) AS Cnt FROM ServiceRequest sr WHERE 1=1 " + whereClause + " GROUP BY sr.RequestStatus";
        List<Map<String,Object>> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, periodValue);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> row = new HashMap<>();
                    row.put("RequestStatus", rs.getString("RequestStatus"));
                    row.put("Count", rs.getInt("Cnt"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("generateServiceStatusReport error: " + e.getMessage());
        }
        return out;
    }

    // Monthly Completed Report: number of requests resolved within a given month (yyyy-MM)
    public List<Map<String,Object>> generateMonthlyCompletedReport(String yearMonth) {
        String sql = """
            SELECT COUNT(*) AS CompletedCount
            FROM ServiceRequest
            WHERE DATE_FORMAT(DateResolved, '%Y-%m') = ?
            """;

        List<Map<String,Object>> out = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, yearMonth);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Map<String,Object> row = new HashMap<>();
                    row.put("CompletedCount", rs.getInt("CompletedCount"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("generateMonthlyCompletedReport error: " + e.getMessage());
        }
        return out;
    }

    // Staff Workload Report: number of requests assigned and resolved by a staff member in a period
    public List<Map<String,Object>> generateStaffWorkloadReport(int staffID, String yearMonth) {
        String sqlAssigned = """
            SELECT COUNT(*) AS AssignedCount
            FROM ServiceRequest
            WHERE StaffID = ? AND DATE_FORMAT(DateFiled, '%Y-%m') = ?
            """;
        String sqlResolved = """
            SELECT COUNT(*) AS ResolvedCount
            FROM AssignmentResolution ar
            WHERE ar.StaffID = ? AND DATE_FORMAT(ar.ResolutionDate, '%Y-%m') = ?
            """;
        List<Map<String,Object>> out = new ArrayList<>();
        try (PreparedStatement psA = conn.prepareStatement(sqlAssigned);
            PreparedStatement psR = conn.prepareStatement(sqlResolved)) {
            
            psA.setInt(1, staffID);
            psA.setString(2, yearMonth);

            try (ResultSet rsa = psA.executeQuery()) {
                int assigned = rsa.next() ? rsa.getInt("AssignedCount") : 0;
                psR.setInt(1, staffID);
                psR.setString(2, yearMonth);

                try (ResultSet rsr = psR.executeQuery()) {
                    int resolved = rsr.next() ? rsr.getInt("ResolvedCount") : 0;
                    Map<String,Object> row = new HashMap<>();
                    row.put("StaffID", staffID);
                    row.put("AssignedCount", assigned);
                    row.put("ResolvedCount", resolved);
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("generateStaffWorkloadReport error: " + e.getMessage());
        }
        return out;
    }
}