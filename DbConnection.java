import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private final String URL = "jdbc:mysql://localhost:3306/CCINFOM_DB";
    private final String USER = "root";
    private final String PASSWORD = "-Keion12012004";
    //private static final String PASSWORD = "[insert password]";
    //private static final String PASSWORD = "[insert password]";

    private Connection conn = null;

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
            System.out.println("Connection established successfully.");
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
    CREATE TABLE IF NOT EXISTS Citizen (AccountID INT PRIMARY KEY AUTO_INCREMENT, 
    FirstName VARCHAR(50), LastName VARCHAR(50), ContactNbr INT, Email VARCHAR(100), 
    Address VARCHAR(250), Password VARCHAR(50));

    (AccountID NOT NULL);
    """;

    String CreateTableStaff = 
    """
    CREATE TABLE IF NOT EXISTS Staff (StaffID INT PRIMARY KEY AUTO_INCREMENT, 
    DepartmentID INT FOREIGN KEY, FirstName VARCHAR(50), LastName VARCHAR(50), 
    ContactNbr INT, Availability VARCHAR(50), Password VARCHAR(50));
    """;

    String CreateTableService = 
    """
    CREATE TABLE IF NOT EXISTS Service (ServiceID INT PRIMARY KEY AUTO_INCREMENT, 
    ServiceName VARCHAR(100), DepartmentID INT FOREIGN KEY);
    """;

    String CreateTableServiceRequest = 
    """
    CREATE TABLE IF NOT EXISTS ServiceRequest (RequestID INT PRIMARY KEY AUTO_INCREMENT, 
    CitizenID INT FOREIGN KEY, StaffID INT FOREIGN KEY, ServiceID INT FOREIGN KEY, 
    DateFiled DATE, Address VARCHAR(250), ServicDesc VARCHAR(999), RequestStatus VARCHAR(10));
    """;

    static String CreateTableDepartment = 
    "CREATE TABLE IF NOT EXISTS Department (DepartmentID INT PRIMARY KEY , DepartmentName VARCHAR(100));";

    //schema and table initializer
    public void initializeDatabase() {
        try{
            conn = getConnection();
            Statement stmt = conn.createStatement(); 

            stmt.execute(CreateDatabase);
            stmt.execute(SelectSchema);
            stmt.execute(CreateTableCitizen);
            stmt.execute(CreateTableStaff);
            stmt.execute(CreateTableService);
            stmt.execute(CreateTableServiceRequest);
            stmt.execute(CreateTableDepartment);

            System.out.println("Database and tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }



    //methods for user login and registration
    public void CitizenRegister(String firstName, String lastName, int contactNbr, String email, String address, String password) {
        String insertCitizenSQL = "INSERT INTO Citizen (FirstName, LastName, ContactNbr, Email, Address, Password) VALUES (?, ?, ?, ?, ?, ?)";
        
        try{
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(insertCitizenSQL);

            //value setters
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, contactNbr);
            pstmt.setString(4, email);
            pstmt.setString(5, address);
            pstmt.setString(6, password);

            pstmt.executeUpdate();
            System.out.println("Registeration successful.");
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    public void CitizenLogin(String email, String password){
        String selectCitizenSQL = "SELECT * FROM Citizen WHERE Email = ? AND Password = ?";
        try{
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(selectCitizenSQL);

            pstmt.setString(1, email);
            pstmt.setString(2, password);


            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!");
            } else {
                System.out.println("Login failed. Invalid email or password.");
            }
        } catch (SQLException e){
            System.err.println("Error during citizen login: " + e.getMessage());
        }
    }

    public void StaffRegister(String firstName, String lastName, int contactNbr, String availability, String password) {
        String insertStaffSQL = "INSERT INTO Staff (FirstName, LastName, ContactNbr, Availability, Password) VALUES (?, ?, ?, ?, ?)";
        try{
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(insertStaffSQL);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, contactNbr);
            pstmt.setString(4, availability);
            pstmt.setString(5, password);

            pstmt.executeUpdate();

            System.out.println("Registration successful.");

        } catch (SQLException e) {
            System.err.println("Registration Error: " + e.getMessage());
        }
    }

    
    public void StaffLogin(int staffID, String password){
        String selectStaffSQL = "SELECT * FROM Staff WHERE StaffID = / AND Password = ?";
        try{
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(selectStaffSQL);

            pstmt.setInt(1, staffID);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + " " + rs.getString("LastName") + "!");
            } else {
                System.out.println("Login failed. Invalid Staff ID or password.");
            }
        } catch (SQLException e){
            System.err.println("Error during staff login: " + e.getMessage());
        }
    }



    //methods for inserting data into service, departments and service requests
    public void FileService(String serviceName, int departmentID) {
        String insertServiceSQL = "INSERT INTO Service (ServiceName, DepartmentID) VALUES (?, ?)";
        try{
            conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(insertServiceSQL); 

            pstmt.setString(1, serviceName);
            pstmt.setInt(2, departmentID);

            pstmt.executeUpdate();

            System.out.println("Service creation successful.");
        } catch (SQLException e) {
            System.err.println("Service creation error: " + e.getMessage());
        }
    }

    public void CreateDepartment(int departmentID, String departmentName) {
        String insertDepartmentSQL = "INSERT INTO Department (DepartmentID, DepartmentName) VALUES (?, ?)";
        try{
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(insertDepartmentSQL); 

            pstmt.setInt(1, departmentID);
            pstmt.setString(2, departmentName);

            pstmt.executeUpdate();

            System.out.println("Department creation successful.");
        } catch (SQLException e) {
            System.err.println("Department creation error: " + e.getMessage());
        }
    }


    public void CreateService(String serviceName, int departmentID) {
        String insertServiceSQL = "INSERT INTO Service (ServiceName, DepartmentID) VALUES (?, ?);";
        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertServiceSQL);

            pstmt.setString(1, serviceName);
            pstmt.setInt(2, departmentID);

            pstmt.executeUpdate();
            System.out.println("Service creation successful.");

        } catch (SQLException e) {
            System.err.println("Service creation error: " + e.getMessage());
        }
    }


    
}