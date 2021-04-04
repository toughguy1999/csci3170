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

            if (choice == 1) {
                // system interface
                printer.system();
                choice = sc.nextInt();
                if (choice == 1) {
                    // create
                    system.create();
                }
                else if (choice == 2){
                    // delete
                    system.delete();
                }
                else if (choice == 3){
                    // insert
                    System.out.println("Please enter the folder path:");
                    system.insert(sc.next());
                }
                else if (choice == 4){
                    // set date
                    System.out.println("Please input the date (YYYYMMDD):");
                    String new_date = sc.next();
                    system.set_date(new_date);
                    sys_date = printer.format_set.parse(new_date);
                    System.out.println("Latest date in orders: 00");
                    System.out.println("Today is " + printer.format_show.format(sys_date));
                }

            } else if (choice == 2) {

            } else if (choice == 3) {

            } else if (choice == 4) {

            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }

        }
        System.out.println("bye");
        System.exit(0);


    }
}