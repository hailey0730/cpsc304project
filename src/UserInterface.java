/**
 * Created by user on 3/24/2017.
 */



import java.lang.String;
import java.sql.*;

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
            String print="Error: There is no " +  product + " in " + province;

            while (rs.next()) {
                print="";
                String name = rs.getString(1).trim();
                int count = rs.getInt( 2);
                print += "Farmer " + name + " in " + province + " owns " + count + " " + product + "s." + '\n';
            }


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
                    print += "The min price of " + rs.getString(1).trim() + " is " + rs.getFloat(2)+ '\n';
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
                    print += "The max price of " + rs.getString(1).trim() + " is " + rs.getFloat(2) + '\n';
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
            }else {
                print = "Type Product ID  Price Amount FarmerId" + '\n';
                int id= rs.getInt( 1);
                float price = rs.getFloat(2);
                int amount = rs.getInt(5);
                int fid = rs.getInt(6);
                print += product + "     " +id + "      " + price + "    " + amount + "       " + fid + '\n';
                while (rs.next()){
                    id= rs.getInt( 1);
                    price = rs.getFloat(2);
                    amount = rs.getInt(5);
                    fid = rs.getInt(6);

                    print += product + "     " +id + "      " + price + "    " + amount + "       " + fid + '\n';
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

//    public ResultSet getProductInfo(int productID) {
//        try {
//            String statement = new String("select quantity from product where product_id = " + productID);
//            Statement stmt = con.createStatement();
//            //String state = "select * from product";
//            ResultSet rs = stmt.executeQuery(statement);
//            rs.next();
//            return rs;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public String updateProductAmount(int productID, int amount) {

            try {
                StringBuffer statement = new StringBuffer("SELECT product.* from product where product_ID =");
                statement.append(productID);
                statement.append(" FOR UPDATE");
                System.out.println(statement.toString());
                ResultSet rs = stmt.executeQuery(statement.toString());
                int quantity=0;
                int new_quantity=0;
                if (rs.next()) {
                    quantity = rs.getInt(5);
                    new_quantity = quantity - amount;
                    rs.updateInt(5,new_quantity);
                }
                String print="The Amount of " + productID + "was updated from " + quantity + " to " + new_quantity;
                return print;

            } catch (SQLException e) {
                e.printStackTrace();
                return "Invalid operation for read only resultset: updateInt";
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

            StringBuffer statement = new StringBuffer("select product_id from product where farmer_id = ");
            statement.append(farmerID);
            statement.append(" and type = '");
            statement.append(productName);
            statement.append("'");
            Statement stmt = con.createStatement();
            System.out.println(statement.toString());
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

            StringBuffer statement = new StringBuffer("select max(transaction_id) from transaction");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement.toString());
            rs.next();
            return rs.getInt(1) + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String createTransaction(String farmerID, String brokerID, String productName, String units){

        try{
            int product_id = this.getProductID(farmerID, productName);
            String type = this.getProductTypeAnimal(product_id);
            String finalType;
            int transaction_id = this.getNewTID();
            StringBuffer statement = new StringBuffer("insert into transaction values (");
            statement.append(transaction_id);
            statement.append(",");
            LocalDate localDate = LocalDate.now();
            statement.append(localDate);
            statement.append(",");
            statement.append(units);
            statement.append(",");
            statement.append(farmerID);
            statement.append(",");
            statement.append(brokerID);
            statement.append(",");
            statement.append(product_id);
            statement.append(")");
            System.out.println(statement.toString());
            stmt.executeQuery(statement.toString());


            String print = "Transaction has been added." + '\n';
            print += "Transaction_id: " +transaction_id + " DATE: " + localDate + " Amount: " + units + " BrokerID: " + brokerID
                    + " FarmerID: " + farmerID + " ProductID " + product_id;
            print += this.updateProductAmount(product_id, Integer.parseInt(units));
            return  print ;
        } catch (SQLException e){
            e.printStackTrace();
            return "Error: Transaction is declined" + e;
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
            return "";
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
//        System.out.println(ui.getNamesWith("BC","cow"));
//        System.out.println(ui.getNamesOfFramersIn("AB"));
//        System.out.println(ui.getProduct("cow"));
        System.out.println(ui.getMinMaxPrince(true));
        System.out.println(ui.getMinMaxPrince(false));
        System.out.println(ui.getProductID("1","corn"));
//        System.out.println(ui.createTransaction("1","301","cow","200"));

    }
}
