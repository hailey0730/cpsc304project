/**
 * Created by user on 3/24/2017.
 */

import oracle.sql.CHAR;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.lang.String;
import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

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
            String print = "";
            if (count == 0) {
                print = "There is no farmers in " + province + ".";
            }else {
                 print = "There are " + count + " farmers in " + province + ".";
            }
            //System.out.println("There are " + count + " farmers in " + province);

            return print;
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
            if (!rs.next()) {
                print = "There is no " + product + " in " + province + ".";
            } else {
                while (rs.next()) {
                    String name = rs.getString(1).trim();
                    int count = rs.getInt( 2);
                    print += "Farmer " + name + " in " + province + " owns " + count + " " + product + "s." + '\n';
                }
            }


            //System.out.println("There are " + count + " farmers in " + province);
            return print;
        }catch (SQLException e) {
            e.printStackTrace();
            return ("Error:" + e);
        }
    }

    public String getMinMaxPrince(boolean checked) {
        if (checked) {
            try {
                StringBuffer statement = new StringBuffer("select type, min(SELLING_PRICE) from product group by type");
                ResultSet rs = stmt.executeQuery(statement.toString());
                String print="";
                while (rs.next()) {
                    print += "The min price of " + rs.getString(1).trim() + " is " + rs.getInt(2)+ '\n';
                }
                return print;
            }catch (SQLException e) {
                e.printStackTrace();
                return  "Error:" + e;
            }
        } else {
            try {
                StringBuffer statement = new StringBuffer("select type,max(SELLING_PRICE) from product group by type");
                ResultSet rs = stmt.executeQuery(statement.toString());
                String print="";

                while (rs.next()) {
                    print += "The max price of " + rs.getString(1).trim() + " is " + rs.getInt(2) + '\n';
                }
                return print;
            }catch (SQLException e) {
                e.printStackTrace();
                return  "Error:" + e;
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
            String print = "";
            if (!rs.next()) {
                print = "There is no product " + product + " in the database.";
            } else {
                print = "Type Product ID  Price Amount FarmerId" + '\n';
                while (rs.next()){
                    int id= rs.getInt( 1);
                    float price = rs.getFloat(2);
                    int amount = rs.getInt(5);
                    int fid = rs.getInt(6);

                    print += product + "     " +id + "      " + price + "   " + amount + "     " + fid + '\n';
                }
            }

            return print;
        }catch (SQLException e) {
            String print = "Error "+ e;
            return print;
        }
    }

    public  String deleteProduct(String product_ID){

        try {
            StringBuffer statement = new StringBuffer("delete from product where product_id = ");
            statement.append(product_ID);
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(statement.toString());

            if(rs ==1){
                return "product is deleted";
            } else {
                return "";
            }

        } catch (SQLException e){
            e.printStackTrace();
            return "Error: " + e;

        }

    }

    public int getProductCount(){
        try {
            StringBuffer statement = new StringBuffer("select count(product_id)from product");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();

            int count = rs.getInt(1);
            return count;

        } catch (SQLException e){
            e.printStackTrace();
            return 0;

        }
    }

    public ResultSet getProductInfo(int productID) {
        try {
            String statement = new String("select quantity from product where product_id = " + productID);
            Statement stmt = con.createStatement();
            //String state = "select * from product";
            ResultSet rs = stmt.executeQuery(statement);
            rs.next();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet updateProductAmount(int productID, int amount) {

            try {
                ResultSet productInfo = this.getProductInfo(productID);

                int newAmount = productInfo.getInt(1) + amount;
                StringBuffer statement = new StringBuffer("UPDATE product set quantity = ");
                statement.append(newAmount);
                statement.append("where product_id = " + productID);
                Statement stmt = con.createStatement();
                stmt.executeUpdate(statement.toString());
                ResultSet rs = this.getProductInfo(productID);
                rs.next();
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

    }

    public String getProductTypeAnimal(int product_id){
        try {

            StringBuffer statement = new StringBuffer("select * from animal where product_id = ");
            statement.append(product_id);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();
            return "animal";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error!";
        }
    }

    public int getProductID(String farmerID, String productName){
        try {

            StringBuffer statement = new StringBuffer("select from product where farmer_id = ");
            statement.append(farmerID);
            statement.append("and type = ' ");
            statement.append(productName);
            statement.append("'");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getNewTID(){
        try {

            StringBuffer statement = new StringBuffer("select last(transaction_id) from transaction");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String createTransaction(String farmerID, String brokerID, String productName, String units){

        try{
            //INSERT INTO table_name
            //VALUES (value1, value2, value3, ...);
            //transaction_id int not null PRIMARY KEY,
            //trans_date date,
            //animalNumber int not null,
            //cropNumber int not null,
            //farmer_id int not null,
            //broker_id int not null,
            //product_id int not null,

            int product_id = this.getProductID(farmerID, productName);
            String type = this.getProductTypeAnimal(product_id);
            String finalType;
            int transaction_id = this.getNewTID();
            if(type == "Error!"){
                finalType = "crop";
            } else {
                finalType = "animal";
            }
            StringBuffer statement = new StringBuffer("insert into transaction values (");
            statement.append(transaction_id);
            statement.append(",");
            LocalDate localDate = LocalDate.now();
            statement.append(localDate);
            statement.append(",");
            if(finalType == "animal"){
                statement.append(units);
                statement.append(", 0 ,");
                statement.append(farmerID);
                statement.append(",");
                statement.append(brokerID);
                statement.append(",");
                statement.append(product_id);
                statement.append(")");
                Statement stmt = con.createStatement();
                stmt.executeUpdate(statement.toString());


            } else {
                statement.append(", 0 ,");
                statement.append(units);
                statement.append(",");
                statement.append(farmerID);
                statement.append(",");
                statement.append(brokerID);
                statement.append(",");
                statement.append(product_id);
                statement.append(")");
                Statement stmt = con.createStatement();
                stmt.executeUpdate(statement.toString());
            }
            this.updateProductAmount(product_id, Integer.parseInt(units));

            return "Transaction has been added.";
        } catch (SQLException e){
            e.printStackTrace();
            return "Error: " + e;
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
    public String brokerUISearch(String province, String product,  boolean max, boolean min){
        //max is the result of max... if it is selected it maxMin is true, else it is false
        if (max && !min) {
            return this.getMinMaxPrince(false);
        } else if(!max && min) {
            return this.getMinMaxPrince(true);
        } else if (!max && !min) {
            if (province.isEmpty() && product.isEmpty()) {
                return "Error: Empty Input!";
            } else if ((!province.isEmpty()) && product.isEmpty()){
                return this.getNumOfFarmer(province) + '\n' + this.getNamesOfFramersIn(province);
            } else if (province.isEmpty() && (!product.isEmpty())) {
                return this.getProduct(product);
            } else{
                return this.getNamesWith(province,product);
            }
        } else {
            return "Error: Don't check both!";
        }

    }
    public String transactionUICreate(String farmer_id, String broker_id, String productName, String productUnits){

        if(broker_id.isEmpty() || productName.isEmpty()|| farmer_id.isEmpty()|| productUnits.isEmpty()){
            return  "Please input a value in every field";
        }  else {
            try{
                Integer.parseInt(productUnits);
            } catch (NumberFormatException n){
                return "Please enter a numerical value for productUnits";
            }
            return this.createTransaction(farmer_id, broker_id, productName, productUnits);
        }
    }

    public String transactionUISearch(String farmerID, String productID){
        if(farmerID.isEmpty() || productID.isEmpty()){
            return  "Please input both farmer_id and product_id";
        } else {
            int before = this.getProductCount();
            String result = this.deleteProduct(productID);
            int after = this.getProductCount();
            if(result.isEmpty()){
                return "No record has been found";
            } else {
                return result + "Before deletion there were " + before + " products. Now there are " + after + "products.";
            }
        }
    }

    public static void main(String[]args){
        UserInterface ui = new UserInterface();

        System.out.println(ui.getNumOfFarmer("AB"));
        System.out.println(ui.getNamesOfFramersIn("AB"));
        System.out.println(ui.getNamesWith("BC","corn"));
        System.out.println(ui.getProduct("cow"));
        System.out.println(ui.getMinMaxPrince(false));

    }
}
