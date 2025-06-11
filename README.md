# EMS-HR-Project
The intent of this project is to create a simple but efficient HR system designed to streamline various HR tasks such as employee performance assessment, management of employee records, payroll details, etc. Considering that certain tasks required generation reports, this would be achieved by using Oracle APEX, a low-code platform by Oracle. The data to be used would be obtained from an Oracle Autonomous Database.

The system is a native Java GUI application, created using the swing package.

## Requirements
1. Java JDK, version 20+
2. An Oracle Cloud Infrastructure (OCI) account
3. Oracle Database Driver, preferably ojdbc10 or higher (should be compartible with the Autonomous Database version in use)
4. An Oracle APEX account

## Project Structure
```tree
EMS/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── DB/
│   │   │           │   ├── Database.java         # contains code for creating the database
│   │   │           │   └── database.sql         # reference to the database created
│   │   │           ├── HR/
│   │   │           │   ├── Attendance.java       # records employee arrival time
│   │   │           │   ├── Auth.java            # login credentials to the application are entered
│   │   │           │   ├── Leaves.java          # recording leave requests
│   │   │           │   ├── ModifyLeaves.java    # editing leave requests as needed
│   │   │           │   └── ViewLeaves.java      # tabular view of pending leave requests
│   │   │           ├── Staff/
│   │   │           │   ├── EmployeeEntry.java       # entry of employees on contract-basis
│   │   │           │   ├── SingleViewEmployees.java # view each employee record and modify
│   │   │           │   ├── SingleViewTemp.java      # view each Temporary staff record and modify
│   │   │           │   ├── TempStaffEntry.java      # entry of interns and attaches
│   │   │           │   ├── ViewEmployees.java       # tabular view of all employees
│   │   │           │   └── ViewTempStaff.java       # tabular view of all interns and attaches
│   │   │           ├── app.properties           # environment variables
│   │   │           ├── Home.java                # initial page after login
│   │   │           ├── LoadEnv.java             # loading env. variables from app.properties
│   │   │           └── Standard.java            # setting unified display across all pages
│   ├── test/
│   │   └── java/
├── target/
│   └── classes/
└── pom.xml
```        

## Steps
1. Clone this repository, under the 'Oracle-ATP' branch, which is the default branch.
```git
git clone https://github.com/Muturi-002/EMS-HR-Project.git
```
2. Read this blog post on the provided link to set up your Autonomous Database (ADB), and how to connect to it from your local machine. [Click me.](https://medium.com/@martinmnjoroge03/connecting-to-an-oracle-autonomous-database-using-java-6de3013138a1)
3. Navigate to the EMS/ directory and build the Maven package successfully.
``` bash
mvn clean install
```
4. Under the src/main/java/org/example directory, compile the java file containing the code for creating the database and run
```java 
javac -cp "path/to/ojdbc10.jar" *.java -d EMS/target/classes //compiles all java files
java -cp "path/to/compiled-Database.java:path/to/ojdbc.jar" DB/Database.java
```
5. If you have hard-coded your login credentials:
run the Auth.java file.
``` java
java -cp "path/to/ojdbc10.jar" HR/Auth.java
```
6. Enter your data.



