
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class system {
    static String date ;
    public static void create()
    {
        try {
            Connection conn;
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection (main.url, main.userName, main.password);
            System.out.println ("Database connection established"); // connect to db
            Statement s = conn.createStatement();
            String createTableSQL1 = "CREATE TABLE book ("
                    + "ISBN CHAR(13) NOT NULL,"
                    + "title VARCHAR(100) NOT NULL,"
                    + "unit_price INT(4) NOT NULL,"
                    + "no_of_copies INT(4) NOT NULL,"
                    + "PRIMARY KEY (ISBN)"
                    + ")";
            String createTableSQL2 = "CREATE TABLE customer ("
                    + "customer_id CHAR(10) NOT NULL,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "shipping_address VARCHAR(200) NOT NULL,"
                    + "credit_card_no CHAR(19) NOT NULL,"
                    + "PRIMARY KEY (customer_id)"
                    + ")";
            String createTableSQL3 = "CREATE TABLE orders ("
                    + "order_id CHAR(8) NOT NULL,"
                    + "o_date DATE NOT NULL,"
                    + "shipping_status CHAR(1) NOT NULL,"
                    + "charge INT(4) NOT NULL,"
                    + "customer_id CHAR(10) NOT NULL,"
                    + "PRIMARY KEY (order_id)"
                    + ")";
            String createTableSQL4 = "CREATE TABLE ordering ("
                    + "order_id CHAR(8) NOT NULL,"
                    + "ISBN CHAR(13) NOT NULL,"
                    + "quantity INT(4) NOT NULL,"
                    + "PRIMARY KEY (order_id,ISBN)"
                    + ")";

            String createTableSQL5 = "CREATE TABLE book_author ("
                    + "ISBN CHAR(13) NOT NULL,"
                    + "author_name VARCHAR(200) NOT NULL,"
                    + "PRIMARY KEY (ISBN,author_name)"
                    + ")";
            s.executeUpdate (createTableSQL1);
            s.executeUpdate (createTableSQL2);
            s.executeUpdate (createTableSQL3);
            s.executeUpdate (createTableSQL4);
            s.executeUpdate (createTableSQL5);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println ("[Error]: java MySQL DB driver not found");
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void delete(){
        try {
            Connection conn;
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection (main.url, main.userName, main.password);
            System.out.println ("Database connection established"); // connect to db
            Statement s = conn.createStatement();
            s.executeUpdate ("DROP TABLE book");
            s.executeUpdate ("DROP TABLE customer");
            s.executeUpdate ("DROP TABLE orders");
            s.executeUpdate ("DROP TABLE ordering");
            s.executeUpdate ("DROP TABLE book_author");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println ("[Error]: java MySQL DB driver not found");
        }
        catch (SQLException e) {
            System.out.println(e);
        }


    }
    public static void insert(String path){
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {


                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {

            }
        }
    }
    public static void set_date(String date) throws IOException {
        FileWriter fw = new FileWriter(main.date);
        fw.write(date);
        fw.flush();
        fw.close();
    }
    public static void get_date() throws ParseException, IOException {
        if (!main.date.exists()){
            main.date.createNewFile();
            FileWriter fw = new FileWriter(main.date);
            fw.write("19000101");
            fw.flush();
            fw.close();
            main.sys_date = printer.format_set.parse("19000101");
        }else {
            BufferedReader fr = new BufferedReader(new FileReader(date));
            main.sys_date = printer.format_set.parse(fr.readLine());
            fr.close();
        }
    }
}
