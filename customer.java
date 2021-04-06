
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
            int price = checkBookAvaible(ISBN, 1);
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

    public static int checkBookAvaible(String ISBN, int no) {
        String insert = "select no_of_copies,unit_price from book where ISBN = '" + ISBN + "'";
        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(insert);
            if (!result.isBeforeFirst())
                System.out.println("Book not found.");
            else {
                result.next();
                if (result.getInt("no_of_copies") - no > 0)
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

    public static void order_detail(Integer order_id) {

        String insert = "select * from orders where order_id=" + order_id;
        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(insert);
            if (!result.isBeforeFirst())
                System.out.println("Error");
            else {
                while (result.next()) {
                    System.out.println("order_id:" + order_id + " shipping:" + result.getString("shipping_status")
                            + " charge=" + result.getInt("charge") + " customerID=" + result.getString("customer_id"));
                    showAllBookByOrder(order_id);
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void showAllBookByOrder(Integer order_id) {
        String insert = "select * from ordering where order_id=" + order_id;

        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(insert);
            if (!result.isBeforeFirst())
                System.out.println("Error");
            else {
                int number = 1;

                while (result.next()) {
                    System.out.println("book no:" + number + " ISBN = " + result.getString("ISBN") + " quantity = "
                            + result.getInt("quantity"));

                    number = number + 1;
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void order_altering(int order_id, int book_no, String actions, int no) {

        int update_quantity = 0;
        String ISBN = "", shipping = "";
        String getorder = "select og.*,o.shipping_status from ordering og join orders o on o.order_id=og.order_id where og.order_id="
                + order_id;
        try {
            Statement check = main.conn.createStatement();
            ResultSet result = check.executeQuery(getorder);
            if (!result.isBeforeFirst())
                System.out.println("Error");
            else {
                int number = 1;

                while (result.next()) {
                    if (number == book_no) {
                        update_quantity = result.getInt("quantity");
                        ISBN = result.getString("ISBN");
                        shipping = result.getString("shipping_status");
                    }
                    number = number + 1;
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        int price = checkBookAvaible(ISBN, no);
        if (price != 0 && shipping.equals("N")) {
            System.out.println("Update is ok!");

            String update2 = "";
            if (actions.equals("add")) {
                update_quantity += no;
                update2 = "update orders set charge  = charge +" + no * price + " where order_id =" + order_id;

            } else if (actions.equals("remove")) {
                update_quantity -= no;
                update2 = "update orders set charge  = charge -" + no * price + " where order_id =" + order_id;

            }
            String update = "update ordering set quantity =" + update_quantity + " where order_id =" + order_id
                    + " and ISBN='" + ISBN + "';";

            try {
                Statement check = main.conn.createStatement();
                check.executeUpdate(update);
                check.executeUpdate(update2);
                System.out.println("Update done!!");

                check.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            System.out.println("Update charge");
            order_detail(order_id);

        } else {
            System.out.println("Book copies is not enough or book already shipped.");

        }
    }

    public static void order_query(String customer_id, int year) {
        String getorder = "select * from orders  where o_date like '" + year + "%' and customer_id ='" + customer_id
                + "' order by order_id asc;";
        try {
            Statement orders = main.conn.createStatement();
            ResultSet result = orders.executeQuery(getorder);
            if (!result.isBeforeFirst())
                System.out.println("Error");
            else {
                int number = 1;

                while (result.next()) {
                    System.out.println("");

                    System.out.println("Record : " + number);
                    System.out.println("OrderID : " + result.getString("order_id"));
                    System.out.println("OrderDate : " + result.getDate("o_date"));
                    System.out.println("charge : " + result.getInt("charge"));
                    System.out.println("shipping status : " + result.getString("shipping_status"));

                    number = number + 1;
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
