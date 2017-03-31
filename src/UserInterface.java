/**
 * Created by user on 3/24/2017.
 */

import oracle.sql.CHAR;

import java.io.*;
import java.lang.String;
import java.sql.*;
import java.util.Scanner;

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

//            stmt.executeQuery("insert into farmer\n" +
//                    "values(001, 'Ed Knorr')");
            ResultSet rs = stmt.executeQuery(statement.toString());
            printFarmerCol(rs);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printFarmerCol(ResultSet rs) {
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
            //System.out.println("There are " + count + " farmers in " + province);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getNamesOfFramersIn(String province) {
        try {
            char[] ch = province.toCharArray();
            StringBuffer statement = new StringBuffer("select farmer.name from farmland, farmer where farmer.farmer_id = farmland.farmer_id and province = '");
            for (int i = 0; i < ch.length; i++) {
                statement.append(ch[i]);
            }
            statement.append("'");
            System.out.println(statement.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            while (rs.next()){
                String name = rs.getString( 1);
                System.out.println(name);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getNamesOfFarmerOwnProduct(String productName) {
        try {
            char[] ch = productName.toCharArray();
            StringBuffer statement = new StringBuffer("select name from farmer where exists(select * from product where type = 'chicken' and farmer.farmer_id = product.farmer_id)");
//            for (int i = 0; i < ch.length; i++) {
//                statement.append(ch[i]);
//            }
//            statement.append("'");
            System.out.println(statement.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            while (rs.next()){
                String name = rs.getString( 1);
                System.out.println(name);
            }



        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet searchByProvince() {
        try {
            Statement stmt = con.createStatement();
            StringBuffer statement = new StringBuffer("select distinct province from farmland");
            ResultSet rs = stmt.executeQuery(statement.toString());
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            return rs;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResultSet searchByFarmer() {
        try {
            Statement stmt = con.createStatement();
            StringBuffer statement = new StringBuffer("select name from farmer");
            ResultSet rs = stmt.executeQuery(statement.toString());
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            return rs;

        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void importSQL(Connection conn, InputStream in) throws SQLException
    {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        try
        {
            st = stmt;
            while (s.hasNext())
            {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/"))
                {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0)
                {
                    st.execute(line);
                }
            }
        }
        finally
        {
            if (st != null) st.close();
        }
    }
    public String farmerUISearch(String province, String productID){
        return "results here thanks";
    }
    public String brokerUISearch(String province, String product, String price, Boolean maxMin){
        return "results here thanks";
    }
    public String transactionUICreate(String area, String product){
        return "results here thanks";
    }
    public String transactionUISearch(String farmerID, String brokerID, String productID, String productUnits){
        return "results here thanks";
    }
    public static void main(String[]args){
        UserInterface ui = new UserInterface();
        // test getAllColumns with farmer table
//        File file = new File("./SQLscript/project.sql");
//        try {
//            FileInputStream in = new FileInputStream(file);
//            ui.importSQL(ui.con,in);
//        } catch (FileNotFoundException a) {
//
//        } catch (SQLException e) {
//
//        }
        // get all columns in table farmer
        ui.getAllColumns("farmer");
        // get the # of farmers in BC
        ui.getNumOfFarmer("BC");
        ui.getNamesOfFramersIn("BC");
        ui.getNamesOfFarmerOwnProduct("cow");
        ui.searchByProvince();
        ui.searchByFarmer();

    }
}
