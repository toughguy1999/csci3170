import java.lang.management.MemoryManagerMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class printer {
    static  SimpleDateFormat format_show = new SimpleDateFormat("yyyy-MM-dd");
    static  DateFormat format_set = new SimpleDateFormat("yyyyMMdd");
    public static void menu(){

        System.out.println("The System Date is now " + format_show.format(main.sys_date));
        System.out.println("<This is the Book Ordering System.>");
        System.out.println("-------------------------------------------");
        System.out.println("1. System interface");
        System.out.println("2. Customer interface");
        System.out.println("3. Bookstore interface");
        System.out.println("4. Show System Date");
        System.out.println("5. Quit the system......");
        System.out.println("Please enter your choice??  ");

    }
    public static void system(){
        System.out.println("This is the System Interface");
        System.out.println("-------------------------------------------");
        System.out.println("1. Create Table");
        System.out.println("2. Delete Table");
        System.out.println("3. Insert Data");
        System.out.println("4. Set System Date");
        System.out.println("5. Back to main menu");
        System.out.println("Please enter your choice??  ");

    }
    public static void bookstore(){

    }
    public static void customer(){

    }
    public static void book_search(){

    }
}
