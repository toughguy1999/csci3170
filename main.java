import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main{
    static String userName = "root";
    static String password = "123456";
    static String url = "jdbc:mysql://localhost/test"; // username,pwd,and url can be used by other module
    public static void main (String[] args) throws IOException {
        // main menu interface
        while(true) {
            printer.menu();
            int choice = 0;
            choice = (int) System.in.read();

            if (choice == 1) {
                // system interface
                printer.system();
                choice = (int) System.in.read();
                if (choice == 1) {
                    // create
                    BufferedReader schema = new BufferedReader(new InputStreamReader(System.in));
                    system.create(schema.readLine());
                }
                else if (choice == 2){
                    // delete
                    BufferedReader table = new BufferedReader(new InputStreamReader(System.in));
                    system.delete(table.readLine());
                }
                else if (choice == 3){
                    // insert
                    BufferedReader path = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Please enter the folder path:");
                    system.insert(path.readLine());
                }
                else if (choice == 4){
                    // set date
                    BufferedReader date = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Please input the date (YYYYMMDD):");



                }

            } else if (choice == 2) {

            } else if (choice == 3) {

            } else if (choice == 4) {

            } else if (choice == 5) {
                System.out.println("bye");
                System.exit(0);
            } else {
                System.out.println("Invalid choice");
            }
        }


    }
}