
import java.sql.*;
import java.io.File;
public class system {
    static String date ;
    public static void create(String schema)
    {
        try {
            Connection conn;
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection (main.url, main.userName, main.password);
            System.out.println ("Database connection established"); // connect to db
            Statement s = conn.createStatement();
            s.executeUpdate ("CREATE TABLE "+ schema);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println ("[Error]: java MySQL DB driver not found");
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void delete(String table){
        try {
            Connection conn;
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection (main.url, main.userName, main.password);
            System.out.println ("Database connection established"); // connect to db
            Statement s = conn.createStatement();
            s.executeUpdate ("DROP TABLE "+ table);
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
                files.add(tempList[i].toString());

                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {

            }
        }
    }
    public static void set_date(Date){

    }
}
