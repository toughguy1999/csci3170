
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

import java.text.SimpleDateFormat;

public class customer {
    static String date;

    public static void search(int choice, String key) {
        String search = "";
        switch (choice) {
        case 1:
            search = "select * from book  where ISBN = '" + key + "' order by ISBN ASC";
            break;
        case 2:
            search = "select * from book  where title like  '" + key + "' order by ISBN ASC";
            break;
        case 3:
            search = "SELECT DISTINCT b.* FROM book b inner join book_author ba on ba.ISBN =b.ISBN and ba.author_name like '"
                    + key + "' order by ISBN ASC";
            break;
        }

        try {
            Statement s = main.conn.createStatement();

            ResultSet result = s.executeQuery(search);

            if (!result.isBeforeFirst())
                System.out.println("No records found.");
            else
                while (result.next()) {
                    System.out.println("");

                    System.out.println("ISBN: " + result.getString("ISBN"));
                    System.out.println("Book Title: " + result.getString("title"));
                    System.out.println("Unit Price: " + result.getInt("unit_price"));
                    System.out.println("No Of Available: " + result.getInt("no_of_copies"));
                    Statement author = main.conn.createStatement();
                    ResultSet resultAuthor = author.executeQuery("select author_name from book_author where ISBN = '"
                            + result.getString("ISBN") + "' order by author_name asc");
                    System.out.println("Author: ");
                    int num = 1;
                    while (resultAuthor.next()) {
                        System.out.println(num + " :" + resultAuthor.getString("author_name"));
                        num++;
                    }
                    System.out.println("");
                    author.close();
                }
            s.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void order_creation(String customer, String ISBN, int quantity, Date date) {
        if (ISBN.equals("L")) {
            String search = "select * from ordering o join orders os on os.order_id= o.order_id and os.customer_id = '"
                    + customer + "' order by o.order_id ASC";
            try {
                Statement s = main.conn.createStatement();
                ResultSet result = s.executeQuery(search);
                if (!result.isBeforeFirst())
                    System.out.println("No records found.");
                else {
                    System.out.println("ISBN:          Number:");
                    while (result.next()) {
                        System.out.println(result.getString("ISBN") + "  " + result.getInt("quantity"));
                    }
                }

            } catch (SQLException e) {
                System.out.println(e);
            }

        } else {
            int price = checkBookAvaible(ISBN);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringdate = simpleDateFormat.format(date);
            if (price != 0) {
                int order_id = getNewOrderID();
                String insert = "insert into orders values(" + order_id + ",'" + stringdate + "','N'," + price + ",'"
                        + customer + "');";

                String insert2 = "insert into ordering values(" + order_id + ",'" + ISBN + "'," + quantity + ");";
                try {
                    Statement s = main.conn.createStatement();
                    Statement s_2 = main.conn.createStatement();

                    s.executeUpdate(insert);
                    s_2.executeUpdate(insert2);

                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("The Book is not avalible ");
            }

        }
    }


    public static int checkBookAvaible(String ISBN) {
        String insert = "select no_of_copies,unit_price from book where ISBN = '" + ISBN + "'";
        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(insert);
            if (!result.isBeforeFirst())
                System.out.println("Book not found.");
            else {
                result.next();
                if (result.getInt("no_of_copies") > 0)
                    return result.getInt("unit_price");
                else
                    return 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int getNewOrderID() {
        String insert = "select max(order_id) as id from orders ";
        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(insert);
            if (!result.isBeforeFirst())
                System.out.println("Error");
            else {
                result.next();
                return result.getInt("id") + 1;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static void order_altering(String path) {

    }

    public static void order_query(String date) throws IOException {

    }

}
