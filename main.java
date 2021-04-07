import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {
    static String userName = "root";
    static String password = "Audris05";
    static String url = "jdbc:mysql://localhost:3306/db_3170?useSSL=false"; // username,pwd,and url can be used by other
                                                                            // module
    static Connection conn;
    static Date sys_date;
    static File date = new File("date.txt");

    public static void main(String[] args) throws IOException, ParseException {
        // get system date
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Processing..."); // connect to db
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: java MySQL DB driver not found");
        } catch (SQLException e) {
            System.out.println(e);
        }
        system.get_date();
        System.out.println("Date: " + sys_date);
        // main menu interface
        Scanner sc = new Scanner(System.in);
        while (true) {
            printer.menu();
            int choice = sc.nextInt();

            // system interface
            if (choice == 1) {
                printer.system();
                choice = sc.nextInt();
                // create
                if (choice == 1) {
                    system.create();
                }
                // delete
                else if (choice == 2) {
                    system.delete();
                }
                // insert
                else if (choice == 3) {
                    System.out.println("Please enter the folder path:");
                    system.insert(sc.next());
                }
                // set date
                else if (choice == 4) {
                    System.out.println("Please input the date (YYYYMMDD):");
                    String new_date = sc.next();
                    system.set_date(new_date);
                    sys_date = printer.format_set.parse(new_date);
                    system.get_latest_order();
                    System.out.println("Today is " + printer.format_show.format(sys_date));
                }
            }

            //  customer interface
            else if (choice == 2) {
                printer.customer();
                choice = sc.nextInt();
                if (choice == 1) { // book search
                    printer.book_search();
                    choice = sc.nextInt();
                    if (choice == 1)
                        System.out.print("Input the ISBN: ");
                    if (choice == 2)
                        System.out.print("Input the Book Title: ");
                    if (choice == 3)
                        System.out.print("Input the Author Name: ");
                    customer.search(choice, sc.next());
                    System.out.println("");

                }
                if (choice == 2) { // book search
                    System.out.print("Please enter your customerID?? ");
                    String customer_id = sc.next();
                    printer.order_creation();
                    while (true) {
                        System.out.print("Please enter the book's ISBN: ");
                        String ISBN = sc.next();
                        int quantity = -1;

                        if (!ISBN.equals("L") && !ISBN.equals("F")) {
                            System.out.print("Please enter the quantity of the order: ");
                            quantity = sc.nextInt();
                        }
                        if (ISBN.equals("F"))
                            break;
                        customer.order_creation(customer_id, ISBN, quantity , sys_date);
                    }
                    System.out.println("");

                } 
                if ( choice == 3){
                    System.out.print("Please enter the OrderID that you want to change:");
                    int order_id = sc.nextInt();

                    customer.order_detail(order_id);
                    System.out.println("Which book you want to alter (input book no.):");
                    int book_no = sc.nextInt();
                    System.out.println("input add or remove");
                    String actions = sc.next();
                    System.out.print("input the number: ");
                    int no = sc.nextInt();
                    customer.order_altering(order_id, book_no, actions, no);
                    System.out.print("");

                }
                if ( choice == 4){
                    System.out.print("Please Input Customer ID: ");
                    String customer_id = sc.next();

                  
                    System.out.print("Please Input the Year: ");
                    int year = sc.nextInt();
                   
                    customer.order_query(customer_id, year);
                    System.out.println("");


                }
            }

            //  bookstore interface
            else if (choice == 3) {
            	printer.bookstore();
                choice = sc.nextInt();
                if(choice == 1) {
                	bookstore.BS_order_update();
                }
                else if(choice == 2) {
                	bookstore.BS_order_query();
                }
                else if(choice ==3 ) {
                	bookstore.N_mostpopular();
                }
                else if(choice == 4)
                	continue;
            }

            // show system date
            else if (choice == 4) {
                System.out.println("The System Date is now " + printer.format_show.format(main.sys_date));

            }

            // quit
            else if (choice == 5) {
                break;
            }

            // invalid input
            else {
                System.out.println("Invalid choice!");
            }

        }
        System.out.println("bye");
        System.exit(0);

    }
}
