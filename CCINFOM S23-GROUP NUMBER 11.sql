
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
    ServiceID INT NOT NULL, 
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
    DepartmentID INT NOT NULL, 
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
INSERT IGNORE INTO Department (DepartmentID, DepartmentName) VALUES
(1, 'Department of Engineering and Public Works'),
(2, 'City General Services Office'),
(3, 'Department of Public Services'),
(4, 'Public Recreations Bureau'),
(5, 'Parks Development Office');


INSERT IGNORE INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(1, 'Road Damage Inspection Request', 'Inspection', 1),
(2, 'Drainage or Sewerage Cleaning Request', 'Maintenance', 1),
(3, 'Street Signage Issue Complaint', 'Complaint', 1),
(4, 'Infrastructure Damage Assessment', 'Inspection', 1);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(5, 'City Furniture Repair Request', 'Repair', 2),
(6, 'Hazardous Waste Disposal Request', 'Cleaning', 2),
(7, 'City Building Maintenance Request', 'Maintenance', 2);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(8, 'Garbage Collection Request', 'Cleaning', 3),
(9, 'Illegal Dumping Complaint', 'Complaint', 3),
(10, 'Street Sweeping Request', 'Cleaning', 3),
(11, 'Public Trash Bin Maintenance Request', 'Maintenance', 3);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
(12, 'Playground Equipment Maintenance Request', 'Maintenance', 4),
(13, 'Recreational Program Registration Inquiry', 'Information', 4);


INSERT INTO Service (ServiceName, ServiceType, DepartmentID) VALUES
(14, 'Park Grounds Maintenance Request', 'Maintenance', 5),
(15, 'Tree Trimming or Pruning Request', 'Maintenance', 5),
(16, 'Park Facility Damage Inspection', 'Inspection', 5);


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
    
    
CREATE TABLE IF NOT EXISTS Department (
    DepartmentID INT NOT NULL, 
    DepartmentName VARCHAR(100),
    PRIMARY KEY (DepartmentID));
    
    
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
    ServiceID INT NOT NULL, 
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
INSERT IGNORE INTO Department (DepartmentID, DepartmentName) VALUES
(1, 'Department of Engineering and Public Works'),
(2, 'City General Services Office'),
(3, 'Department of Public Services'),
(4, 'Public Recreations Bureau'),
(5, 'Parks Development Office');


INSERT IGNORE INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(1, 'Road Damage Inspection Request', 'Inspection', 1),
(2, 'Drainage or Sewerage Cleaning Request', 'Maintenance', 1),
(3, 'Street Signage Issue Complaint', 'Complaint', 1),
(4, 'Infrastructure Damage Assessment', 'Inspection', 1);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(5, 'City Furniture Repair Request', 'Repair', 2),
(6, 'Hazardous Waste Disposal Request', 'Cleaning', 2),
(7, 'City Building Maintenance Request', 'Maintenance', 2);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(8, 'Garbage Collection Request', 'Cleaning', 3),
(9, 'Illegal Dumping Complaint', 'Complaint', 3),
(10, 'Street Sweeping Request', 'Cleaning', 3),
(11, 'Public Trash Bin Maintenance Request', 'Maintenance', 3);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(12, 'Playground Equipment Maintenance Request', 'Maintenance', 4),
(13, 'Recreational Program Registration Inquiry', 'Information', 4);


INSERT INTO Service (ServiceID, ServiceName, ServiceType, DepartmentID) VALUES
(14, 'Park Grounds Maintenance Request', 'Maintenance', 5),
(15, 'Tree Trimming or Pruning Request', 'Maintenance', 5),
(16, 'Park Facility Damage Inspection', 'Inspection', 5);

