package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BS_Order_query {

	public static void main(String[] args) throws SQLException {
	    	String url = "jdbc:mysql://localhost:3306/booksystem";
	    	String uname = "root";
	        String password = "mwg456123";
	        String order_query = "SELECT *  "
	        		+ " FROM orders \r\n"
	        		+ " WHERE MONTH(o_date) = ? "
	        		+ "AND YEAR(o_date) = ? "
	        		+ "AND shipping_status='Y'"
	        		+ "ORDER BY order_id;";
	        
	       String order_charge_sum = "SELECT \r\n"
	        		+ "	SUM(charge) charge_sum  \r\n"
	        		+ "FROM orders\r\n"
	        		+ "WHERE MONTH(o_date) = ? "
	        		+ "AND YEAR(o_date) = ? "
	        		+ "AND shipping_status='Y'";

	        		
	        		
	        
	        //String update =;
	        
	        String month,year,date_input;
	        String[] temp;
	        char update;
	        		Scanner cc = new Scanner(System.in);
	        		System.out.println("please input the Month for Order Query (e.g. 2005-09)");
	        		date_input = cc.nextLine();
	        		
	        		// split the string into year and month	        	
	        		String delimiter = "-";
	        		temp = date_input.split(delimiter);   
	        		year = temp[0];   
	        		month = temp[1];
	        		
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
	    	
	    	
	    	PreparedStatement OQ1 = con.prepareStatement(order_query);
	    	//execute query dircetly
	    	//Statement statement = con.createStatement();   //for generate a query
	    	// ResultSet result = statement.executeQuery(query); // To get the result (not in preparedstatement)
	    	//create query or update query
	    	OQ1.setString(1, month);
	    	OQ1.setString(2, year);
	    	ResultSet OQ1_result = OQ1.executeQuery();
	    	
	    	PreparedStatement OQ2 = con.prepareStatement(order_charge_sum);
	    	OQ2.setString(1, month);
	    	OQ2.setString(2, year);
	    	ResultSet OQ2_result = OQ2.executeQuery();


			/*//check if the order exist in the table
			 * boolean order_check = result.next();
			 * if(!order_check) { System.out.println("There is no order_id" + order_id
			 * +"exist in the table" ); System.out.println("please input the order ID: 	");
			 * order_id = dd.nextLine(); }
			 */
	    	
	    	

	    				
	    /*	
	    	PreparedStatement preparedstatement2 = con.prepareStatement(query2);
	    	preparedstatement2.setString(1, order_id);
	    	ResultSet result2 = preparedstatement2.executeQuery();
*/
	    	// set parameters	
	    	
	    	//receive a query using integer to lab the field

	    	if(OQ1_result.next() == false) {
				System.out.println("No Order In select month ");
	    	}
	    	else {
	    	int index = 1;
	    	do {
	    		System.out.println("Record: " + index);
	    		String order_id = OQ1_result.getString("order_id");
	    		String customer_id = OQ1_result.getString("customer_id");
	    		String order_date = OQ1_result.getString("o_date");
	    		int order_charge = OQ1_result.getInt("charge");
				System.out.println("order_id: " + order_id);
				System.out.println("customer_id: " + customer_id);
				System.out.println("date: " + order_date);
				System.out.println("charge: " + order_charge);
				System.out.println("\n");
				index += 1 ;
	    	}
	    	while(OQ1_result.next());
	    	
	    	while(OQ2_result.next()) {
	    		int charge_sum = OQ2_result.getInt("charge_sum");
	    		System.out.println("Total charges of the month is  " + charge_sum);

	    	}
	    	

	    }
	    
	    }
	
	    	//get data from specify field
	    	/*while(OQ1_result.next()) {
	    		char shipping_status = OQ1_result.getString("shipping_status").charAt(0)
	;
	    		//int charge = result.getInt("charge");
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
				
				 * System.out.println(shipping_status+"."+charge);
				 			
	    	}
	    	
	    	
	    }*/
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}
