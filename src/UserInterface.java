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
    public String getNumOfFarmer(String province) {
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
            String number = "There are " + count + " farmers in " + province + ".";
            return number;
        }catch (SQLException e) {
            e.printStackTrace();
            return ("Error:" + e);
        }
    }

    public String getNamesOfFramersIn(String province) {
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
            String print = "Their names are:";
            while (rs.next()){
                String name = rs.getString( 1).trim();
                System.out.println(name);
                print += " " + name;
            }
            return print;
        }catch (SQLException e) {
            String print = "Error "+ e;
            return print;
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
    public String getNamesWith(String province, String product) {
        try {
            char[] pv = province.toCharArray();
            StringBuffer statement = new StringBuffer("select farmer.name,product.quantity from farmer, product, farmland where farmland.province = '");
            for (int i = 0; i < pv.length; i++) {
                statement.append(pv[i]);
            }
            statement.append("'");
            statement.append(" and farmer.farmer_id = product.farmer_id and farmland.farmer_id = product.farmer_id and product.type = '");
            char[] pd = product.toCharArray();
            for (int i = 0; i < pd.length; i++) {
                statement.append(pd[i]);
            }
            statement.append("'");
            System.out.println(statement.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            String print="";
            while (rs.next()) {
                String name = rs.getString(1).trim();
                int count = rs.getInt( 2);
                print += "Farmer " + name + " in " + province + " owns " + count + " " + product + "s." + '\n';
            }

            //System.out.println("There are " + count + " farmers in " + province);
            return print;
        }catch (SQLException e) {
            e.printStackTrace();
            return ("Error:" + e);
        }
    }

    public ResultSet getMinMaxPrince(boolean checked) {
        if (checked) {
            try {
                StringBuffer statement = new StringBuffer("select type, min(SELLING_PRICE) from product group by type");
                ResultSet rs = stmt.executeQuery(statement.toString());
                while (rs.next()) {
                    System.out.println("The min price of " + rs.getString(1).trim() + " is " + rs.getInt(2));
                }
                return rs;
            }catch (SQLException e) {
                e.printStackTrace();
                return  null;
            }
        } else {
            try {
                StringBuffer statement = new StringBuffer("select type,max(SELLING_PRICE) from product group by type");
                ResultSet rs = stmt.executeQuery(statement.toString());
                while (rs.next()) {
                    System.out.println("The min price of " + rs.getString(1).trim() + " is " + rs.getInt(2));
                }
                return rs;
            }catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public String getProduct(String product) {
        try {
            char[] ch = product.toCharArray();
            StringBuffer statement = new StringBuffer("select * from product where type = '");
            for (int i = 0; i < ch.length; i++) {
                statement.append(ch[i]);
            }
            statement.append("'");
            System.out.println(statement.toString());
            ResultSet rs = stmt.executeQuery(statement.toString());
            String print = "Type Product ID  Price Amount FarmerId" + '\n';
            while (rs.next()){
                int id= rs.getInt( 1);
                float price = rs.getFloat(2);
                int amount = rs.getInt(5);
                int fid = rs.getInt(6);

                print += product + "     " +id + "      " + price + "   " + amount + "     " + fid + '\n';
            }
            return print;
        }catch (SQLException e) {
            String print = "Error "+ e;
            return print;
        }
    }


    public String farmerUISearch(String province, String product){
        if (province.isEmpty() && product.isEmpty()) {
            return "Error: Empty Input!";
        } else if ((!province.isEmpty()) && product.isEmpty()){
            return this.getNumOfFarmer(province) + '\n' + this.getNamesOfFramersIn(province);
        } else if (province.isEmpty() && (!product.isEmpty())) {
            return this.getProduct(product);
        } else{
            return this.getNamesWith(province,product);
        }

    }
    public String brokerUISearch(String province, String productID, String price, Boolean max){
        //max is the result of max... if it is selected it maxMin is true, else it is false
        return "results here thanks: " + province + "  " + productID + " " + price + " " + max;
    }
    public String transactionUICreate(String area, String farmName, String productID, String productUnits){
        return "results here thanks: " + area + "  " + farmName + "  " + productID + " " + productUnits;
    }
    public String transactionUISearch(String farmerID, String brokerID, String productID, String productUnits){
        return "results here thanks: " + farmerID + "  " + brokerID + " " + productID + " " + productUnits;
    }

    public static void main(String[]args){
        UserInterface ui = new UserInterface();

        // get the # of farmers in BC
        System.out.println(ui.getNumOfFarmer("BC"));
        System.out.println(ui.getNamesOfFramersIn("BC"));
        System.out.println(ui.getNamesWith("BC","corn"));
        System.out.println(ui.getProduct("cow"));
        ui.getNamesOfFarmerOwnProduct("cow");
        ui.searchByFarmer();
        ui.getMinMaxPrince(true);


    }
}
