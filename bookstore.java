import java.sql.*;
import java.util.Scanner;


public class bookstore {




		 public static void BS_order_update(){
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
		    	//create a statement  using connection object
		    	
		    	//query
		    	PreparedStatement preparedstatement = main.conn.prepareStatement(query);
		    	preparedstatement.setString(1, order_id);
		    	ResultSet result = preparedstatement.executeQuery();

		    	//query2
		    	PreparedStatement preparedstatement2 = main.conn.prepareStatement(query2);
		    	preparedstatement2.setString(1, order_id);
		    	ResultSet result2 = preparedstatement2.executeQuery();
		    	
				

		    	if(result.next() == false) {
		    			System.out.println("No such Order ID exist");
		        	}
		    		else{
		    			do {
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
		            	    	PreparedStatement update_ss = main.conn.prepareStatement("UPDATE orders SET shipping_status = 'Y' WHERE order_id =?");
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
		    			}while(result.next());
						
		    	}
		    	
		    	
		    }
		    // Handle any errors that may have occurred.
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		 
		 
		 public static void BS_order_query(){
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
		    
		    	//create a statement  using connection object
		    	
		    	
		    	PreparedStatement OQ1 = main.conn.prepareStatement(order_query);
		  
		    	OQ1.setString(1, month);
		    	OQ1.setString(2, year);
		    	ResultSet OQ1_result = OQ1.executeQuery();
		    	
		    	PreparedStatement OQ2 = main.conn.prepareStatement(order_charge_sum);
		    	OQ2.setString(1, month);
		    	OQ2.setString(2, year);
		    	ResultSet OQ2_result = OQ2.executeQuery();

		    	
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
		

		    // Handle any errors that may have occurred.
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
		 }
		 
		 public static void N_mostpopular(){
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
			    
			    	//create a statement  using connection object
			    	
			    	//PreparedStatement N_mostpop = con.prepareStatement(N_query);
			    	Statement N_mostpop = main.conn.createStatement();   //for generate a query
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


