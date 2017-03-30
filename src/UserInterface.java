/**
 * Created by user on 3/24/2017.
 */

import oracle.sql.CHAR;

import java.lang.String;
import java.sql.*;

public class UserInterface {
    private Connection con = null;
    private Statement stmt = null;

    public UserInterface() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug", "ora_c2q0b", "a27242149");       // change username, password
            // stmt is a statement object
            stmt = con.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getAllTable() {
        try {
            //String query = "select distinct farmer_id from farmland where province = 'BC'";
            String tableListQuery = "select table_name from user_tables";
            ResultSet rs = stmt.executeQuery(tableListQuery);
            while (rs.next()) {
                String tablename = rs.getString("table_name");
                System.out.println(tablename);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //get all columns in some table
    public void getAllColumns(String tableName) {
        try {
            StringBuffer statement = new StringBuffer("select * from ");
            statement.append(tableName);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            printFamerCol(rs);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printFamerCol(ResultSet rs) {
        try {
            int farmerID;
            String farmerName;
            while(rs.next())
            {
                farmerID = rs.getInt(1);
                farmerName = rs.getString(2);
                System.out.println(farmerID);
                System.out.println(farmerName);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
        }

    }

    //(1 point) Aggregation query: pick one query that requires the use of aggregation
    // (min, max, average, or count are all fine).
    // with input province, get total number of farmers in the given province
    public void getNumOfFarmer(String province) {
        try {
            char[] ch = province.toCharArray();
            StringBuffer statement = new StringBuffer("select count(*) from farmland where province = '");
            for (int i = 0; i < ch.length; i++) {
                statement.append(ch[i]);
            }
            statement.append("'");
            System.out.println(statement.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();
            int count = rs.getInt( 1);
            System.out.println(count);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[]args){
        UserInterface ui = new UserInterface();
        // test getAllColumns with farmer table
        ui.getAllTable();
        // get all columns in table farmer
        ui.getAllColumns("farmer");
        // get the # of farmers in BC
        ui.getNumOfFarmer("BC");

        System.out.println("hello");


    }
}
