import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main{
    static String userName = "root";
    static String password = "123456";
    static String url = "jdbc:mysql://localhost/test"; // username,pwd,and url can be used by other module
    static Date sys_date;
    static File date = new File("date.txt");
    public static void main (String[] args) throws IOException, ParseException {
        // get system date
        system.get_date();

        // main menu interface
        Scanner sc = new Scanner(System.in);
        while(true) {
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
                else if (choice == 2){
                    system.delete();
                }
                // insert
                else if (choice == 3){
                    System.out.println("Please enter the folder path:");
                    system.insert(sc.next());
                }
                // set date
                else if (choice == 4){
                    System.out.println("Please input the date (YYYYMMDD):");
                    String new_date = sc.next();
                    system.set_date(new_date);
                    sys_date = printer.format_set.parse(new_date);
                    System.out.println("Latest date in orders: 00");
                    System.out.println("Today is " + printer.format_show.format(sys_date));
                }
            }

            //  customer interface
            else if (choice == 2) {

            }

            //  bookstore interface
            else if (choice == 3) {

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
