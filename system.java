
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class system {

    public static void create() {
        try {
            System.out.println("Processing...");
            Statement s = main.conn.createStatement();
            String createTableSQL1 = "CREATE TABLE book (" + "ISBN CHAR(13) NOT NULL," + "title VARCHAR(100) NOT NULL,"
                    + "unit_price INT(4) NOT NULL," + "no_of_copies INT(4) NOT NULL," + "PRIMARY KEY (ISBN)" + ")";
            String createTableSQL2 = "CREATE TABLE customer (" + "customer_id CHAR(10) NOT NULL,"
                    + "name VARCHAR(50) NOT NULL," + "shipping_address VARCHAR(200) NOT NULL,"
                    + "credit_card_no CHAR(19) NOT NULL," + "PRIMARY KEY (customer_id)" + ")";
            String createTableSQL3 = "CREATE TABLE orders (" + "order_id CHAR(8) NOT NULL," + "o_date DATE NOT NULL,"
                    + "shipping_status CHAR(1) NOT NULL," + "charge INT(4) NOT NULL," + "customer_id CHAR(10) NOT NULL,"
                    + "PRIMARY KEY (order_id)" + ")";
            String createTableSQL4 = "CREATE TABLE ordering (" + "order_id CHAR(8) NOT NULL,"
                    + "ISBN CHAR(13) NOT NULL," + "quantity INT(4) NOT NULL," + "PRIMARY KEY (order_id,ISBN)" + ")";

            String createTableSQL5 = "CREATE TABLE book_author (" + "ISBN CHAR(13) NOT NULL,"
                    + "author_name VARCHAR(200) NOT NULL," + "PRIMARY KEY (ISBN,author_name)" + ")";
            s.executeUpdate(createTableSQL1);
            s.executeUpdate(createTableSQL2);
            s.executeUpdate(createTableSQL3);
            s.executeUpdate(createTableSQL4);
            s.executeUpdate(createTableSQL5);
            s.close();
            System.out.println("Successful!");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void delete() {
        try {

            System.out.println("Processing...");
            Statement s = main.conn.createStatement();
            s.executeUpdate("DROP TABLE book");
            s.executeUpdate("DROP TABLE customer");
            s.executeUpdate("DROP TABLE orders");
            s.executeUpdate("DROP TABLE ordering");
            s.executeUpdate("DROP TABLE book_author");
            s.close();
            System.out.println("Successful!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insert(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        try {
            System.out.println("Processing..."); // connect to db
            Statement s = main.conn.createStatement();

            // insert one by one
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {

                    // get table name
                    String fileName = tempList[i].getName();
                    // create a file reader
                    FileInputStream fis = new FileInputStream(tempList[i]);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;

                    // insert current file

                    if (fileName.equals("book.txt")) {
                        while ((line = br.readLine()) != null) {

                            String[] line_split = line.split("\\|");
                            int line_split2 = Integer.parseInt(line_split[2]);
                            int line_split3 = Integer.parseInt(line_split[3]);

                            s.executeUpdate("INSERT INTO book" + " VALUES" + "(" + "'" + line_split[0] + "'" + "," + "'"
                                    + line_split[1] + "'" + "," + line_split2 + "," + line_split3 + ")");
                        }
                    } else if (fileName.equals("customer.txt")) {
                        while ((line = br.readLine()) != null) {

                            String[] line_split = line.split("\\|");

                            s.executeUpdate("INSERT INTO customer" + " VALUES" + "(" + "'" + line_split[0] + "'" + ","
                                    + "'" + line_split[1] + "'" + "," + "'" + line_split[2] + "'" + "," + "'"
                                    + line_split[3] + "'" + ")");
                        }
                    } else if (fileName.equals("orders.txt")) {
                        while ((line = br.readLine()) != null) {

                            String[] line_split = line.split("\\|");
                            int line_split3 = Integer.parseInt(line_split[3]);
                            Date d = printer.format_set2.parse(line_split[1]);
                            java.sql.Date line_split1 = new java.sql.Date(d.getTime());

                            s.executeUpdate("INSERT INTO orders" + " VALUES" + "(" + "'" + line_split[0] + "'" + ","
                                    + "'" + line_split1 + "'" + "," + "'" + line_split[2] + "'" + "," + line_split3
                                    + "," + "'" + line_split[4] + "'" + ")");
                        }
                    } else if (fileName.equals("ordering.txt")) {
                        while ((line = br.readLine()) != null) {

                            String[] line_split = line.split("\\|");
                            int line_split2 = Integer.parseInt(line_split[2]);

                            s.executeUpdate("INSERT INTO ordering" + " VALUES" + "(" + "'" + line_split[0] + "'" + ","
                                    + "'" + line_split[1] + "'" + "," + line_split2 + ")");
                        }
                    } else if (fileName.equals("book_author.txt")) {
                        while ((line = br.readLine()) != null) {

                            String[] line_split = line.split("\\|");

                            s.executeUpdate("INSERT INTO book_author" + " VALUES" + "(" + "'" + line_split[0] + "'"
                                    + "," + "'" + line_split[1] + "'" + ")");
                        }
                    }
                    br.close();
                }
            }
            s.close();
            System.out.println("Successful!");
        } catch (SQLException | IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void set_date(String date)  {
        try {
            FileWriter fw = new FileWriter(main.date);
            fw.write(date);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void get_date() {
        try {
            if (!main.date.exists()) {
                FileWriter fw = new FileWriter(main.date);
                fw.write("19000101");
                fw.flush();
                fw.close();
                main.sys_date = printer.format_set.parse("19000101");
            } else {
                BufferedReader fr = new BufferedReader(new FileReader(main.date));
                main.sys_date = printer.format_set.parse(fr.readLine());
                fr.close();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void get_latest_order(){
    String query = "select max(o_date) as date from orders ";
    try{
        Statement latest = main.conn.createStatement();
        ResultSet result = latest.executeQuery(query);
        if (!result.isBeforeFirst())
            System.out.println("No order founded");
        else {
            result.next();
            String latest_date = printer.format_show.format(result.getDate("date"));
            System.out.println("Latest date in orders: " + latest_date);
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
}

}
