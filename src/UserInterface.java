/**
 * Created by user on 3/24/2017.
 */

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
            String tableListQuery = "select table_name from user_tables order by table_name";
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

            //print all columns in farmers;
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
        }

    }
    // with input province, get all farmer's name in this area
    // or get total number of farmers in the given province
    public void getFarmersName() {


    }

    public static void main(String[]args){
        UserInterface ui = new UserInterface();
        ui.getAllColumns("farmer");
        ui.getAllTable();
        System.out.println("hello");

    }
}
