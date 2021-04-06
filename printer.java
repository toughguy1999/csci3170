import java.lang.management.MemoryManagerMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class printer {
    static SimpleDateFormat format_show = new SimpleDateFormat("yyyy-MM-dd"); // show date as "yyyy-MM-dd"
    static DateFormat format_set = new SimpleDateFormat("yyyyMMdd"); // transfer "yyyyMMdd" into date
    static SimpleDateFormat format_set2 = new SimpleDateFormat("yyyy-MM-dd"); // transfer "yyyy-MM-dd" into date

    public static void menu() {

        System.out.println("<This is the Book Ordering System.>");
        System.out.println("-------------------------------------------");
        System.out.println("1. System interface");
        System.out.println("2. Customer interface");
        System.out.println("3. Bookstore interface");
        System.out.println("4. Show System Date");
        System.out.println("5. Quit the system......");
        System.out.println("Please enter your choice??  ");

    }

    public static void system() {
        System.out.println("This is the System Interface");
        System.out.println("-------------------------------------------");
        System.out.println("1. Create Table");
        System.out.println("2. Delete Table");
        System.out.println("3. Insert Data");
        System.out.println("4. Set System Date");
        System.out.println("5. Back to main menu");
        System.out.println("Please enter your choice??  ");

    }



    public static void customer() {
        System.out.println("This is the Customer Interface");
        System.out.println("-------------------------------------------");
        System.out.println("1. Book Search");
        System.out.println("2. Order Creation");
        System.out.println("3. Order Altering");
        System.out.println("4. Order Query");
        System.out.println("5. Back to main menu");
        System.out.print("Please enter your choice??  ");
    }

    public static void book_search() {
        System.out.println("What do u want to search");

        System.out.println("1. ISBN");
        System.out.println("2. Book Title");
        System.out.println("3. Author Name");
        System.out.println("4. Exit");

        System.out.print("Your choice?...  ");
    }

    public static void order_creation() {

        System.out.println(">> What books do you want to order??");
        System.out.println(">> Input ISBN and then the quantity.");
        System.out.println(">> You can press \"L\" to see ordered list, or \"F\" to finish ordering.");
    }

    public static void bookstore(){
        System.out.println ("<This is the bookstore interface.>");
        System.out.println("-------------------------------------------");
        System.out.println ("1. Order Update.");
        System.out.println("2. Order Query.");
        System.out.println("3. N most Popular Book Query.");
        System.out.println("4. Back to main menu.\n");
        System.out.println("What is your choice??  ");

    }

    public static void order_altering() {
        System.out.println ("Please enter the OrderID that you want to change:");
         
    }

    public static void order_query() {

    }

}
