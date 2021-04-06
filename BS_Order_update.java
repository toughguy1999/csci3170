package JDBC;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;



public class BS_Order_update {
	public static void main(String[] args) throws SQLException {
    	String url = "jdbc:mysql://localhost:3306/booksystem";
    	String uname = "root";
        String password = "";
        String query = "select shipping_status,charge \r\n"
        		+ "from orders\r\n"
        		+ "where order_id = ?";
        
        String query2 = "SELECT \r\n"
        		+ "	SUM(quantity)  orderTotal\r\n"
        		+ "FROM\r\n"
        		+ "	ordering\r\n"
        		+ "WHERE\r\n"
        		+ "	order_id = ?";
                
        String order_id;
        char update;
        		Scanner dd = new Scanner(System.in);
        		System.out.println("please input the order ID: 	");
        		order_id = dd.nextLine();
        		

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
    	
    	//query
    	PreparedStatement preparedstatement = con.prepareStatement(query);
    	preparedstatement.setString(1, order_id);
    	ResultSet result = preparedstatement.executeQuery();

    	//query2
    	PreparedStatement preparedstatement2 = con.prepareStatement(query2);
    	preparedstatement2.setString(1, order_id);
    	ResultSet result2 = preparedstatement2.executeQuery();
    	
		
    	while(result.next()) {
    		//convert string to char
    		char shipping_status = result.getString("shipping_status").charAt(0);    		
    		
    		if(shipping_status == 'Y') {
				System.out.println("No update is allowed.");
    		}
    		else {
    	    	 //int ordertotal = result2.getInt(1);
    			//move the cursor to first row
    			result2.next(); 
    	    	 int ordertotal = result2.getInt("orderTotal");
    	    	if(ordertotal >=1 ) {
    				System.out.println("Are you sure to update the shipping status?(Yes= Y / No = N)");
            		while(true) {
                		update = dd.next().charAt(0);

            		if(update == 'Y') {
            	    	PreparedStatement update_ss = con.prepareStatement("UPDATE orders SET shipping_status = 'Y' WHERE order_id =?");
            	    	update_ss.setString(1, order_id);
            	    	//To manipulate data you actually need executeUpdate() rather than executeQuery().!
            	    	update_ss.executeUpdate(); 
        				System.out.println("Updated shipping status!");
        				break;} 
        				else if(update == 'N') {
        				break;	}//go back to BS interface
        				else {
            				System.out.println("Incorrect input!");
        				}
        					
            		}
    	    				}
    	    	else {
    				System.out.println("there is no book in the order");
    				break; //go back to BS interface
    	    	}

    		}
				
    	}
    	if(!result.next()) {
			System.out.println("No such Order ID exist");
    	}
    	
    }
    // Handle any errors that may have occurred.
    catch (SQLException e) {
        e.printStackTrace();
    }
}
}