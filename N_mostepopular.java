package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class N_mostepopular {

	public static void main(String[] args) throws SQLException {
    	String url = "jdbc:mysql://localhost:3306/booksystem";
    	String uname = "root";
        String password = "";
        String N_query = "SELECT book.ISBN, book.Title, book.no_of_copies, sum(ordering.quantity) "
        		+ "FROM book INNER "
        		+ "JOIN ordering "
        		+ "ON book.ISBN=ordering.ISBN "
        		+ "group by ISBN";	

                
        //String update =;
        
        int N;
        char update;
        String[] ISBN = new String[1000];
        String[] Title = new String[1000];
        int[] sum = new int[1000];
        		Scanner aa = new Scanner(System.in);
        		System.out.println("Please input the N popular books number :	");
        		N = aa.nextInt();
        		

    try {
    	Class.forName("com.mysql.jdbc.Driver");
    }catch (ClassNotFoundException e) {
    	//TODO Auto generate catch block;
        e.printStackTrace(); 
    }



	 try {
	    	//make connection
	    	Connection con = DriverManager.getConnection(url,uname,password );
	    	//create a statement  using connection object
	    	
	    	//PreparedStatement N_mostpop = con.prepareStatement(N_query);
	    	Statement N_mostpop = con.createStatement();   //for generate a query
	    	ResultSet N_result = N_mostpop.executeQuery(N_query); // To get the result (not in preparedstatement)
	    	
	    
	    	
	    int count =0;
	    			
	    	//get data from specify field
		System.out.println("ISBN" +  "\t\t" + "Title" +  "\t\t" +  "Copies");

	  	if(N_result.next() == false) {
		System.out.println("No book order");
	}
	
			else {
				do{
	    		ISBN[count] = N_result.getString("ISBN");
	    		Title[count] = N_result.getString("Title");
	    		sum[count] = N_result.getInt("sum(ordering.quantity)");
	    		//int charge = result.getInt("charge");
				//System.out.println(ISBN[count] + "\t" + Title[count] + "\t" + sum[count]);
	    		count ++;

	    	}while(N_result.next());
	    	if(N >= count) {
	    		for(int i =0;i<count;i++) {
		    		System.out.println(ISBN[i] + "\t" + Title[i] + "\t" + sum[i]);
		    	}
	    		
	    	}
	    	else {
	    	for(int i =0;i<N;i++) {
	    		if(sum[i]== sum[i+1] && i > 0 ) {
	    			N ++;
	    		}
	    		System.out.println(ISBN[i] + "\t" + Title[i] + "\t" + sum[i]);
	    	}
	    	}
	 }
	 }
	    		

	    		
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	 }
}

