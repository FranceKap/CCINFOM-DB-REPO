
-- Database creation and navigation
CREATE SCHEMA IF NOT EXISTS ccinfom_db;

USE ccinfom_db;


-- Table creation
CREATE TABLE IF NOT EXISTS Citizen (
    AccountID INT NOT NULL AUTO_INCREMENT, 
    FirstName VARCHAR(50), 
    LastName VARCHAR(50), 
    ContactNbr BIGINT, 
    Email VARCHAR(100), 
    Address VARCHAR(250), 
    Password VARCHAR(50),
    PRIMARY KEY (AccountID));
    
    
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
    
    
CREATE TABLE IF NOT EXISTS Service (
    ServiceID INT AUTO_INCREMENT, 
    ServiceName VARCHAR(100), 
    ServiceType VARCHAR(50), 
    DepartmentID INT,
    PRIMARY KEY (ServiceID),
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID));
    
    
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
    
    
CREATE TABLE IF NOT EXISTS Department (
    DepartmentID INT, 
    DepartmentName VARCHAR(100),
    PRIMARY KEY (DepartmentID));
    
    
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
    
    
CREATE TABLE IF NOT EXISTS ReopenRequest (
    ReopenID INT AUTO_INCREMENT,
    RequestID INT NOT NULL,
    CitizenID INT NOT NULL,
    Reason VARCHAR(1000) NOT NULL,
    ReopenDate DATE NOT NULL,
    PRIMARY KEY (ReopenID),
    FOREIGN KEY (RequestID) REFERENCES ServiceRequest(RequestID),
    FOREIGN KEY (CitizenID) REFERENCES Citizen(AccountID));
    
    
    
-- Inserting data into Department and Service tables
INSERT INTO Department (DepartmentID, DepartmentName) VALUES
(1, 'Department of Engineering and Public Works'),
(2, 'City General Services Office'),
(3, 'Department of Public Services'),
(4, 'Public Recreations Bureau'),
(5, 'Parks Development Office');


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
('Road Damage Inspection Request', 'Inspection', 1),
('Drainage or Sewerage Cleaning Request', 'Maintenance', 1),
('Street Signage Issue Complaint', 'Complaint', 1),
('Infrastructure Damage Assessment', 'Inspection', 1);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
('City Furniture Repair Request', 'Repair', 2),
('Hazardous Waste Disposal Request', 'Cleaning', 2),
('City Building Maintenance Request', 'Maintenance', 2);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
('Garbage Collection Request', 'Cleaning', 3),
('Illegal Dumping Complaint', 'Complaint', 3),
('Street Sweeping Request', 'Cleaning', 3),
('Public Trash Bin Maintenance Request', 'Maintenance', 3);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
('Playground Equipment Maintenance Request', 'Maintenance', 4),
('Recreational Program Registration Inquiry', 'Information', 4);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
('Park Grounds Maintenance Request', 'Maintenance', 5),
('Tree Trimming or Pruning Request', 'Maintenance', 5),
('Park Facility Damage Inspection', 'Inspection', 5);

