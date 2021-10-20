package com.company;
//To compile, make sure on same folder level as Main.java
//Compile with javac Main.java
//back up to the src level
//Execute with java -classpath ".;sqlite-jdbc-3.36.0.3.jar" com.company.Main
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    //gets database based on name of database
    private static Connection getConnection() throws SQLException {
        Connection con;
        //Database path -- if its a new database, it will be created in the project folder
        con = DriverManager.getConnection("jdbc:sqlite:TurboAutoService.db");
        return con;
    }

    //function to build the database
    private static void buildDatabase(boolean DBExists) throws SQLException {
        Connection con;
        Statement state, state2;
        ResultSet res;
        PreparedStatement prep;

        if (!DBExists) {
            DBExists=true;
            con=getConnection();

            //Check to create mechanics table
            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='MECHANICS_TABLE'");
            if(!res.next()) {
                System.out.println("Building the MECHANICS_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE MECHANICS_TABLE(" +
                        "TUID INTEGER," +
                        "MECHANIC_NAME VARCHAR(60)," +
                        "HOURLY_PAYRATE DOUBLE," +
                        "PRIMARY KEY(TUID)" +
                        ");");
                //Add a couple of records using parameters
                System.out.println("Add record 1 to MECHANICS_TABLE table");
                prep = con.prepareStatement("INSERT INTO MECHANICS_TABLE Values(?,?,?);");
                prep.setInt(1, 1);
                prep.setString(2,"Steve");
                prep.setDouble(3,9.00);
                prep.execute();

                System.out.println("Add record 2 to MECHANICS_TABLE table");
                prep = con.prepareStatement("INSERT INTO MECHANICS_TABLE Values (?,?,?);");
                prep.setInt(1, 2);
                prep.setString(2, "Sue");
                prep.setDouble(3, 10.00);
                prep.execute();
            }

            //Check to create customer table
            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='CUSTOMER_TABLE'");
            if(!res.next()) {
                System.out.println("Building the CUSTOMER_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE CUSTOMER_TABLE(" +
                        "TUID INTEGER," +
                        "NAME VARCHAR(60)," +
                        "PRIMARY KEY(TUID)" +
                        ");");
            }

            //Check to create vehicles table
            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='VEHICLES_TABLE'");
            if(!res.next()) {
                System.out.println("Building the VEHICLES_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE VEHICLES_TABLE(" +
                        "TUID INTEGER," +
                        "CUSTOMER_TUID INTEGER," +
                        "VEHICLE_DESCRIPTION VARCHAR(60)," +
                        "PRIMARY KEY(TUID)" +
                        ");");
            }

            //Check to create services table
            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SERVICES_TABLE'");
            if(!res.next()) {
                System.out.println("Building the SERVICES_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE SERVICES_TABLE(" +
                        "TUID INTEGER," +
                        "SERVICE_NAME VARCHAR(60)," +
                        "SERVICE_TIME INTEGER," +
                        "PRIMARY KEY(TUID)" +
                        ");");
                //Add a couple of records using parameters
                System.out.println("Add record 1 to SERVICES_TABLE table");
                prep = con.prepareStatement("INSERT INTO SERVICES_TABLE Values(?,?,?);");
                prep.setInt(1, 1);
                prep.setString(2,"Oil Change");
                prep.setInt(3,30);
                prep.execute();

                System.out.println("Add record 2 to SERVICES_TABLE table");
                prep = con.prepareStatement("INSERT INTO SERVICES_TABLE Values (?,?,?);");
                prep.setInt(1, 2);
                prep.setString(2, "Tire Replacement");
                prep.setInt(3, 60);
                prep.execute();

                System.out.println("Add record 3 to SERVICES_TABLE table");
                prep = con.prepareStatement("INSERT INTO SERVICES_TABLE Values (?,?,?);");
                prep.setInt(1, 3);
                prep.setString(2, "Brakes");
                prep.setInt(3, 180);
                prep.execute();

                System.out.println("Add record 4 to SERVICES_TABLE table");
                prep = con.prepareStatement("INSERT INTO SERVICES_TABLE Values (?,?,?);");
                prep.setInt(1, 4);
                prep.setString(2, "Transmission Filter Replacement");
                prep.setInt(3, 120);
                prep.execute();

                System.out.println("Add record 5 to SERVICES_TABLE table");
                prep = con.prepareStatement("INSERT INTO SERVICES_TABLE Values (?,?,?);");
                prep.setInt(1, 5);
                prep.setString(2, "Cooling System Cleaning");
                prep.setInt(3, 240);
                prep.execute();
            }

            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='BAYS_TABLE'");
            if(!res.next()) {
                System.out.println("Building the BAYS_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE BAYS_TABLE(" +
                        "TUID INTEGER," +
                        "MECHANIC_TUID INTEGER," +
                        "PRIMARY KEY(TUID)" +
                        ");");
                //Add a couple of records using parameters
                System.out.println("Add record 1 to BAYS_TABLE table");
                prep = con.prepareStatement("INSERT INTO BAYS_TABLE Values(?,?);");
                prep.setInt(1, 1);
                prep.setInt(2,2);
                prep.execute();

                System.out.println("Add record 2 to BAYS_TABLE table");
                prep = con.prepareStatement("INSERT INTO BAYS_TABLE Values(?,?);");
                prep.setInt(1, 2);
                prep.setInt(2,1);
                prep.execute();
            }

            //Check to create customer table
            state = con.createStatement();
            res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SCHEDULE_TABLE'");
            if(!res.next()) {
                System.out.println("Building the SCHEDULE_TABLE table");
                state2 = con.createStatement();
                state2.executeUpdate("CREATE TABLE SCHEDULE_TABLE(" +
                        "TUID INTEGER," +
                        "VEHICLE_TUID INTEGER," +
                        "SERVICES_TUID INTEGER," +
                        "BAYS_TUID INTEGER," +
                        "APPOINTMENT_STARTTIME STRING," +
                        "APPOINTMENT_ENDTIME STRING," +
                        "PRIMARY KEY(TUID)" +
                        ");");
            }
            con.close();
        }
    }

    //function to load file from system
    public static void loadFile(Scanner sc) throws SQLException {
        Connection con = getConnection();
        PreparedStatement prep;
        ResultSet res;
        Statement state = con.createStatement();
        boolean loaded=false;
        int custIndex;
        int vehIndex;
        int schedIndex;
        res = state.executeQuery("SELECT IFNULL(MAX(TUID),0) FROM CUSTOMER_TABLE;"); //get next customer TUID
        custIndex = res.getInt(1)+1;
        res = state.executeQuery("SELECT IFNULL(MAX(TUID),0) FROM VEHICLES_TABLE;"); //get next vehicle TUId
        vehIndex = res.getInt(1)+1;
        res = state.executeQuery("SELECT IFNULL(MAX(TUID),0) FROM SCHEDULE_TABLE;"); //get next scheduler TUID
        schedIndex = res.getInt(1)+1;

        while(!loaded) {
            System.out.print("\nPlease enter the queue document name:\t");
            String filename = sc.nextLine();
            File f = new File(filename);
            try {
                Scanner fs = new Scanner(f);
                while(fs.hasNext()) {
                    state = con.createStatement();
                    String[] lineData = fs.nextLine().split("\t");
                    switch(lineData[0]) {
                        case "C": //new customer
                            //Prevent double loading of data by checking if name already in system
                            if (state.executeQuery("SELECT COUNT(NAME) FROM CUSTOMER_TABLE WHERE NAME='" + lineData[1] + "';").getInt(1) == 0) {
                                prep = con.prepareStatement("INSERT INTO CUSTOMER_TABLE Values(?,?);");
                                prep.setInt(1, custIndex);
                                prep.setString(2, lineData[1]);
                                prep.execute();
                                custIndex++;
                            }
                            break;
                        case "V": //new vehicle
                            //make sure vehicle not already in system
                            boolean notExist = state.executeQuery("SELECT COUNT(TUID) FROM VEHICLES_TABLE " +
                                    "WHERE CUSTOMER_TUID=(SELECT TUID FROM CUSTOMER_TABLE WHERE NAME=\""+lineData[1]+"\") " +
                                    "AND VEHICLE_DESCRIPTION=\""+lineData[2]+"\"").getInt(1)==0;
                            //Prevent double loading of data by checking if vehicle already in system
                            if (notExist) {
                                prep = con.prepareStatement("INSERT INTO VEHICLES_TABLE Values(?,?,?)");
                                prep.setInt(1, vehIndex);
                                prep.setInt(2, state.executeQuery("SELECT TUID FROM CUSTOMER_TABLE WHERE NAME='" + lineData[1] + "';").getInt(1));
                                prep.setString(3, lineData[2]);
                                prep.execute();
                                vehIndex++;
                            }
                            break;
                        case "S": //new schedule point
                            prep = con.prepareStatement("INSERT INTO SCHEDULE_TABLE Values(?,?,?,?,?,?)");
                            //scheduler counter
                            prep.setInt(1, schedIndex);
                            //put tuid in query
                            prep.setInt(2, con.createStatement().executeQuery("SELECT TUID FROM VEHICLES_TABLE " +
                                    "WHERE CUSTOMER_TUID=(SELECT TUID FROM CUSTOMER_TABLE WHERE NAME=\'"+lineData[1]+"\') " +
                                    "AND VEHICLE_DESCRIPTION=\'"+lineData[2]+"\';").getInt(1));
                            //get tuid of service to get length
                            prep.setInt(3, con.createStatement().executeQuery("SELECT TUID FROM SERVICES_TABLE WHERE SERVICE_NAME='" + lineData[3] + "';").getInt(1));
                            //get length of service
                            int mins = con.createStatement().executeQuery("SELECT SERVICE_TIME FROM SERVICES_TABLE WHERE SERVICE_NAME='"+lineData[3]+"';").getInt(1);
                            res = con.createStatement().executeQuery("SELECT TUID, " + //get tuid of vehicle for other bay checking purposes
                                    "DATE('now'), " + //get current date
                                    "strftime('%w', DATE('now')) " + //Get weekday
                                    "FROM VEHICLES_TABLE WHERE VEHICLE_DESCRIPTION=\""+lineData[2]+"\"");
                            TimeResult tr = calcEarliest(con, mins, res.getInt(1), res.getInt(3)+res.getString(2));
                            prep.setInt(4,tr.bay); //put bay
                            prep.setString(5, tr.starttime.toString()); //put start time
                            prep.setString(6, tr.endtime.toString()); //put end time
                            prep.execute();
                            schedIndex++;
                            break;
                        default:
                            System.out.println("Invalid input. Please try again.");
                            break;
                    }
                state.close();
                }
                loaded=true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
        con.close();
    }

    //calculates earliest time that a job can be served (FIFO)
    public static TimeResult calcEarliest(Connection con, int minutes, int vehicleTUID, String currentDate) throws SQLException {
        Statement state = con.createStatement();
        ResultSet res;
        TimeResult tr;
        boolean invalid = true;
        //create timeresult for checking
        tr = new TimeResult(LocalDate.parse(currentDate.substring(1)), Integer.parseInt(currentDate.charAt(0)+""), minutes);
        tr.setDateFromToday(LocalDate.parse(currentDate.substring(1)),Integer.parseInt(currentDate.charAt(0)+""));
        while(invalid) {
            for (int i = 0; i < 2; i++) { //to check both bays to reduce redundant code
                res = state.executeQuery(createSQLScheduler(tr, false, vehicleTUID));
                if (res.getInt(1) != 0) { //flip bay if none found
                    if (tr.bay == 1)
                        tr.setBay(2);
                    else
                        tr.setBay(1);
                } else { //if open spot
                    res = state.executeQuery(createSQLScheduler(tr, true, vehicleTUID)); //make sure car is not already in other bay
                    if (res.getInt(1) == 0) {
                        return tr;
                    }
                }
            }
            //get new startTime to work off of on both bays
            res = state.executeQuery("SELECT MIN(APPOINTMENT_ENDTIME) FROM SCHEDULE_TABLE " +
                    "WHERE DATETIME(APPOINTMENT_STARTTIME) < DATETIME('" + tr.endtime.toString() + "') " +
                    "AND DATETIME(APPOINTMENT_ENDTIME) > DATETIME('" + tr.starttime.toString() + "') " +
                    ";");
            tr.updateStart(LocalDateTime.parse(res.getString(1)));
        }
        return tr;
    }


    //Creates SQL statement for checking whether an appointment is already scheduled at that time
    public static String createSQLScheduler(TimeResult tr, boolean otherBay, int vehicleTUID) {
        int bayCheck = tr.bay;
        String ob = "";
        if (otherBay) {
            if (bayCheck == 1)
                bayCheck = 2;
            else
                bayCheck = 1;
            ob+=" AND VEHICLE_TUID="+vehicleTUID;
        }
        return "SELECT IFNULL(COUNT(TUID),0) FROM SCHEDULE_TABLE " +
                "WHERE DATETIME(APPOINTMENT_STARTTIME) < DATETIME('" + tr.endtime.toString() + "') " +
                "AND DATETIME(APPOINTMENT_ENDTIME) > DATETIME('" + tr.starttime.toString() + "') " +
                "AND BAYS_TUID=" + bayCheck + ob + ";";
    }

    //Prints the main menu
    public static void printMenu() {
        System.out.println("**********************************************************************************************************");
        System.out.println("Welcome to the Turbo Auto Service Management Software! Please Select a menu option to continue");
        System.out.println("**********************************************************************************************************");
        System.out.println("\t1:\tLoad file of customer appointments");
        System.out.println("\t2:\tCalculate pay for mechanics");
        System.out.println("\t3:\tPrint schedules");
        System.out.println("\t9:\tQuit program");
        System.out.println("**********************************************************************************************************");
    }

    //Reports salaries for all employees
    public static void calculatePay(Connection con) throws SQLException {
        System.out.println("**********************************************************************************************************");
        System.out.println("SALARY REPORT");
        System.out.println("**********************************************************************************************************");
        ResultSet rs = con.createStatement().executeQuery("SELECT MECHANIC_TUID, MECHANIC_NAME, AmountToPay " +
                "FROM (SELECT MECHANIC_TUID, SUM(payforJob) as AmountToPay " +
                "FROM (SELECT MECHANIC_TUID, MECHANICS_TABLE.HOURLY_PAYRATE*(SERVICE_TIME/60.0) as payforJob " +
                "FROM SCHEDULE_TABLE " +
                "JOIN BAYS_TABLE on BAYS_TABLE.TUID=SCHEDULE_TABLE.BAYS_TUID " +
                "JOIN MECHANICS_TABLE on MECHANICS_TABLE.TUID=BAYS_TABLE.MECHANIC_TUID " +
                "JOIN SERVICES_TABLE on SERVICES_TABLE.TUID=SCHEDULE_TABLE.SERVICES_TUID " +
                ") as mechData " +
                "group by MECHANIC_TUID " +
                ") as groupedData " +
                "join MECHANICS_TABLE on MECHANICS_TABLE.TUID=groupedData.MECHANIC_TUID");
        while (rs.next()) {
            System.out.println("ID:\t" + rs.getInt(1) + "\tMechanic:\t" + String.format("%1$10s",rs.getString(2))+"\tPay:\t$"+rs.getDouble(3));
        }
        con.close();
    }

    //function to print out a schedule for a selected mechanic
    public static void printSchedule(Scanner sc, Connection con) throws SQLException {
        int id = -1;
        while (true) {
            System.out.println("Which mechanic would you like the schedule for?");
            ResultSet rs = con.createStatement().executeQuery("SELECT TUID, MECHANIC_NAME FROM MECHANICS_TABLE"); //get all mechanics
            while (rs.next()) { //print out all mechanics
                System.out.println("ID:\t" + rs.getInt(1) + "\tMechanic:\t" + rs.getString(2));
            }
            System.out.println("Enter 0 to cancel operation."); //in case mechanic screen was entered by accident
            try {
                id=Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                id = -1;
            }
            if (id==0) {
                return;
            }
            if (con.createStatement().executeQuery("SELECT COUNT(TUID) FROM MECHANICS_TABLE WHERE TUID=" + id + ";").getInt(1) > 0) {
                break;
            } else {
                System.out.println("Not a valid ID. Please try again.");
            }
        }
        //Create printing statement here
        ResultSet schedule = con.createStatement().executeQuery("select MECHANIC_NAME, BAYS_TABLE.TUID BAY, NAME, VEHICLE_DESCRIPTION, SERVICE_NAME, APPOINTMENT_STARTTIME, APPOINTMENT_ENDTIME from SCHEDULE_TABLE " +
                "join BAYS_TABLE on BAYS_TUID=BAYS_TABLE.TUID " +
                "join MECHANICS_TABLE on MECHANIC_TUID=MECHANICS_TABLE.TUID " +
                "join VEHICLES_TABLE on VEHICLE_TUID=VEHICLES_TABLE.TUID " +
                "join CUSTOMER_TABLE on CUSTOMER_TUID=CUSTOMER_TABLE.TUID " +
                "join SERVICES_TABLE on SERVICES_TUID=SERVICES_TABLE.TUID " +
                "where MECHANICS_TABLE.TUID="+id); //query to get all relevant info
        System.out.println("**********************************************************************************************************************************************************************************************************");
        System.out.printf("%1$15s%2$10s%3$20s%4$30s%5$50s%6$20s%7$20s\n","Mechanic Name", "Bay", "Customer Name", "Vehicle", "Service", "Start Time", "End Time");
        System.out.println("**********************************************************************************************************************************************************************************************************");
        while(schedule.next()) { //print out scheduled jobs
            System.out.printf("%1$15s%2$10s%3$20s%4$30s%5$50s%6$20s%7$20s\n", schedule.getString(1), String.valueOf(schedule.getInt(2)), schedule.getString(3), schedule.getString(4), schedule.getString(5), schedule.getString(6), schedule.getString(7));
        }
    }
    public static void main(String[] args) {
	// write your code here
        Scanner sc = new Scanner(System.in); //create input scanner for usage
        try {
            getConnection(); //create database connection
            buildDatabase(false); //build the database if it doesn't exist
        } catch (Exception e) {
            e.printStackTrace();
        }
        int input = 0; //create user input varaible for allowing it to be used continually for every usage in the while
        while (input!=9) { //9 is quit
            printMenu();
            try {
                input = Integer.parseInt(sc.nextLine().trim()); //get user input, trim in case of whitespace
            } catch (Exception e) {
                input = 0; //ensure input does not break
                System.out.print("Invalid input. Please try again.");
            }
            switch(input) {
                case 1: //1 to load an input file
                    try {
                        loadFile(sc);
                    } catch (Exception e) {
                        System.out.println("Invalid file. Please try again.");
                    }
                    break;
                case 2: //2 to report salaries
                    try {
                        calculatePay(getConnection());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3: //3 to print a mechanic's schedule
                    try {
                        printSchedule(sc, getConnection());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 9: //9 to quit
                    System.out.println("\tQuitting...");
                    break;
                default:
            }
        }
        sc.close();
    }
}
//class for appointments to allow easier handling of scheduler
class TimeResult {
    LocalDate date;
    LocalDateTime starttime;
    LocalDateTime endtime;
    int bay; //to allow for checking if a vehicle is in the other bay
    int weekday;
    int minutes;

    //Constructor
    public TimeResult(LocalDate date, int weekday, int minutes) {
        setDateFromToday(date, weekday); //ensure starts on monday
        starttime=LocalDateTime.of(this.date, LocalTime.parse("08:00:00"));
        bay = 1;
        this.weekday=1;
        this.minutes=minutes;
        updateTimes();
    }

    //automatically sets the date to the upcoming Monday (1)
    public void setDateFromToday(LocalDate date, int weekday) {
        int daysToAdd;
        if(weekday==0) {
            daysToAdd=1;
        } else {
            daysToAdd=8-weekday;
        }
        this.date=date.plusDays(daysToAdd);
    }

    //setter for startTime
    public void updateStart(LocalDateTime newTime) {
        starttime = newTime;
        updateTimes();
    }

    //Setter for bay
    public void setBay(int bay) {
        this.bay=bay;
    }

    //accounts for lunch times and end of day to set endTime
    public void updateTimes() {
        if(starttime.getHour()==12) { //if start time is during lunch automatically go to 1pm
            starttime=LocalDateTime.of(date,LocalTime.parse("13:00:00"));
        }
        this.endtime=this.starttime.plusMinutes(this.minutes);
        if (starttime.getHour() < 12 && endtime.getHour()>=12) { //add hour if job goes into lunch
            this.endtime.plusMinutes(60);
        }
        if (endtime.getHour() > 17 || (endtime.getHour()==17 &&endtime.getMinute()!=0)) { //if end of work day, go to next available day
            weekday++;
            if (weekday == 6) { //if the next day is saturday, go to Monday
                weekday = 1;
                date = date.plusDays(2);
            }
            date = date.plusDays(1);
            starttime=LocalDateTime.of(date, LocalTime.parse("08:00:00")); //reset to 8am on next day
            updateTimes(); //to update endTimes
        }
    }
}