package View;

//Java utilities
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Other classes
import Controller.ServerProtocol;
import Model.Booking;

public class ServerView implements Runnable {
	
	private ServerProtocol _prot;
	
	public ServerView(ServerProtocol prot){
		this._prot = prot;
	}
	
	public void run() {
		readUserInput();
	}
	
	public void readUserInput()//Checks users input and calls the required method or shows a help message
    {
		System.out.println("Type in the command you wish to execute. Type help for more details."); 
    	
    	//Create a new scanner
		Scanner input = new Scanner(System.in);
    	
    	String userInput = input.nextLine();
    	
    	//Create an ArrayList to store user inputs
    	String[] userInputs;
    	
    	userInputs = userInput.split(" ");
    	
    	//Uses first token to check if it matches one of the commands of our program
    	switch (userInputs[0]) {
	    	case "ADD":
	    		//Calls method add() and passes the connection & user inputs without the first token
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		//Checking if number of arguments matches what we expect
	    		if(userInputs.length < 6) {
	    			System.out.println("You are missing at least one argument. Refer to the help to know which arguments are needed to add a booking.");
	    			System.out.println();
	    		}else if(userInputs.length > 6) {
	    			System.out.println("You have too many arguments. Refer to the help to know which arguments are needed to add a booking.");
	    			System.out.println();
	    		}else {
	    			try {
	    				String addResult = _prot.add(userInputs);
	    				System.out.println(addResult);
	    				System.out.println();
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured: " + ex);
	    			}
	    		}
	    		break;
	    	case "LISTALL":
	    		//Calls method listAll() and passes the connection
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		try {
	    			showBookings(_prot.listAll(userInputs), userInputs);
	    		}catch(SQLException ex) {
	    			System.out.println("An error occured while retrieving the bookings: " + ex);
	    		}
	    		
	    		break;
	    	case "LISTPT":
	    		//Calls method listPT() and passes the connection & the user inputs without the first token
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		if (userInputs.length == 0) {
	    			System.out.println("Please enter the PT's ID after using the command LISTPT.");
	    		}else {
	    			try {
	    				showBookings(_prot.listPT(userInputs), userInputs);
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured: " + ex);
	    			}
	    		}
	    		break;
	    	case "LISTCLIENT":
	    		//Calls method listClient() and passes the connection & the user inputs without the first token
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		if (userInputs.length == 0) {
	    			System.out.println("Please enter the client's ID after using the command LISTCLIENT.");
	    		}else {
	    			try {
	    				showBookings(_prot.listClient(userInputs), userInputs);
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured: " + ex);
	    			}
	    		}
	    		break;
	    	case "LISTDAY":
	    		//Calls method listDay() and passes the connection & the user inputs without the first token
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		if (userInputs.length == 0) {
	    			System.out.println("Please enter the day after using the command LISTDAY. Date format must be as follow: YYYY:MM:DD");
	    		}else {
	    			try {
	    				showBookings(_prot.listDay(userInputs), userInputs);
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured: " + ex);
	    			}
	    		}
	    		break;
	    	case "UPDATE":
	    		//Calls method update() and passes the connection & the user inputs without the first token
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		//Checking if number of arguments matches what we expect
	    		if(userInputs.length < 7) {
	    			System.out.println("You are missing at least one argument. Refer to the help to know which arguments are needed to add a booking.");
	    			System.out.println();
	    		}else if(userInputs.length > 7) {
	    			System.out.println("You have too many arguments. Refer to the help to know which arguments are needed to add a booking.");
	    			System.out.println();
	    		}else {
	    			try {
	    				String updateResult = _prot.update(userInputs);
	    				System.out.println(updateResult);
	    				System.out.println();
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured: " + ex);
	    			}
	    		}
	    		break;
	    	case "DELETE":
	    		userInputs = Arrays.copyOfRange(userInputs, 1, userInputs.length);
	    		
	    		if (userInputs.length == 0) {
	    			System.out.println("Please enter the booking_ID after using the command DELETE.");
	    		}else if(userInputs.length > 1) {
	    			System.out.println("Please enter a single booking_IDafter using the command DELETE.");
	    		}else {
	    			try {
	    				System.out.println(_prot.delete(userInputs));
	    				System.out.println();
	    			}catch (SQLException ex) {
	    				System.out.println("An error occured while deleting your booking. Error: " + ex);
	    			}
	    		}
	    		break;
	    	case "EXIT":
	    		try {
	    			_prot.closeConnection();
	    		}catch(SQLException ex) {
	    			System.out.println("An error occured while closing the connection to the database: " + ex);
	    			System.out.println("Press enter to quit regardless.");
	    			
	    			Scanner lastWords = new Scanner(System.in);
	    			String quitting = lastWords.nextLine();
	    		}
	    		
	    		System.exit(0);
	    		break;
	    	default:
	    		printHelp(userInputs);
    	}
    	readUserInput();
    }
	
	private void printHelp(String[] userInputs){
		
		if (userInputs.length > 0) {
			for (int i = 0; i < userInputs.length; i++) {
				switch (userInputs[i]) {
					case "ADD":
						System.out.println("ADD creates a new booking. Needs Client_ID, Trainer_ID, Booking_time, Booking_date, Booking_duration and Focus in that order. All arguments must be separated by a single space.");
						System.out.println();
						break;
					case "LISTALL":
						System.out.println("LISTALL prints all bookings. Add -pretty as an argument for a prettier output :)");
						System.out.println();
						break;
					case "LISTPT":
						System.out.println("LISTPT prints all bookings for a specific PT. Type the command followed by a single space and the Trainer_ID. Add -pretty as an argument for a prettier output :)");
						System.out.println();
						break;
					case "LISTCLIENT":
						System.out.println("LISTCLIENT prints all bookings for a specific Client. Type the command followed by a single space and the Client_ID. Add -pretty as an argument for a prettier output :)");
						System.out.println();
						break;
					case "LISTDAY":
						System.out.println("LISTDAY prints all bookings for a specific day. Type the command followed by a single space and the date (yyyy-mm-dd). Add -pretty as an argument for a prettier output :)\"");
						System.out.println();
						break;
					case "UPDATE":
						System.out.println("UPDATE updates an existing Booking. Needs Booking_ID, Client_ID, Trainer_ID, Booking_time, Booking_date, Booking_duration and Focus in that order. All arguments must be separated by a single space. All values will be overwritten, except for the Booking_ID.");
						System.out.println();
						break;
					case "DELETE":
						System.out.println("DELETE deletes an existing Booking. Type the command followed by a single space and the Booking_ID you wish to delete.");
						System.out.println();
					case "EXIT":
						System.out.println("EXIT orders a Sichuan Pork to be delivered to your doorstep. Or quits the program. 50-50 chance, promise.");
						System.out.println();
						break;
					default:
						System.out.println("Commands are as follow:");
						System.out.println("ADD           |Adds a booking based on the details provided");
						System.out.println("LISTALL       |Lists all bookings");
						System.out.println("LISTPT        |Lists all bookings for a given PT");
						System.out.println("LISTCLIENT    |Lists all bookings for a given Client");
						System.out.println("LISTDAY       |Lists all bookings for a given Day");
						System.out.println("UPDATE        |Updates a booking based on the booking's ID and details");
						System.out.println("DELETE        |Deletes a booking based on the booking's ID");
						System.out.println("EXIT          |Surely you can guess that one");
						System.out.println();
						break;
				}
			}
		}else {
			System.out.println("Commands are as follow:");
			System.out.println("ADD           |Adds a booking based on the details provided");
			System.out.println("LISTALL       |Lists all bookings");
			System.out.println("LISTPT        |Lists all bookings for a given PT");
			System.out.println("LISTCLIENT    |Lists all bookings for a given Client");
			System.out.println("LISTDAY       |Lists all bookings for a given Day");
			System.out.println("UPDATE        |Updates a booking based on the booking's ID and details");
			System.out.println("DELETE        |Deletes a booking based on the booking's ID");
			System.out.println("EXIT          |Surely you can guess that one");
			System.out.println();
		}
		
		//Goes back to await user's input
		readUserInput();
	}
	
	
	public void updResult() {
		System.out.println("Your booking has been successfully updated.");
	}
	
	public void delResult() {
		System.out.println("Your booking has been successfully deleted.");
	}
	
	public void delResult(String bookingID) {
		System.out.println("We could not find a booking matching " + bookingID + " in our records. Please ensure you have the correct ID.");
	}
	
	public void delResult(SQLException ex) {
		System.out.println("It seems that there is an error with your input. Please try again. Feel free to show the following message to the nearest developer.");
		System.out.println(ex);
		System.out.println();
	}
	
	public void badInput(Connection con, String field) {
		System.out.println("The value you inserted for " + field + " is not valid.");
	}
	
	public void showBookings(ArrayList<Booking> bookings, String[] userInputs) {
		boolean prettyOutput = false;
		
		//Checks if another token followed the command LISTALL
	    if (userInputs.length > 0) {
			for (int i = 0; i < userInputs.length; i++) {
				
				//If one of the tokens was "-pretty", outputs the results in a pretty table.
				if (userInputs[i].equals("-pretty")){
					
					prettyOutput = true;
				}
				
				if(bookings.size() < 0) {

			    	//No bookings found in the DB.
			    	System.out.println("No bookings registered in the database.");
			    	System.out.println();
				} else if (prettyOutput) { //User wants a pretty output
			    	//String containing the format of our table (cells with 15 characters), followed by the first 3rows.
			    	String rowFormat = "| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %n";
			    	System.out.format("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+%n");
			    	System.out.format("| Booking ID      | Client ID       | Trainer ID      | Booking Time    | Booking Date    | B. Duration     | Focus           |%n");
			    	System.out.format("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+%n");
			    	
			    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
			    	{
			    		String[] bookingDetails = b.getBookingDetails();
			    		System.out.format(rowFormat, bookingDetails[0], bookingDetails[1], bookingDetails[2], bookingDetails[3], bookingDetails[4], bookingDetails[5], bookingDetails[6]);
			    		
			    	}
			    	//Closes the table and skips a line for readability.
			    	System.out.format("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+%n");
			    	System.out.println();
				}else { //User doesn't want a pretty output
			    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
			    	{
			    		String[] bookingDetails = b.getBookingDetails();
			    		System.out.println( bookingDetails[0] + ", " + bookingDetails[1] + ", " + bookingDetails[2] + ", " + bookingDetails[3] + ", " + bookingDetails[4] + ", " + bookingDetails[5] + ", " + bookingDetails[6]);
			    		
			    	}System.out.println();
				}
			}
		}else {//User doesn't want a pretty output
			if (bookings.size() > 0) {
		    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
		    	{
		    		String[] bookingDetails = b.getBookingDetails();
		    		System.out.println( bookingDetails[0] + ", " + bookingDetails[1] + ", " + bookingDetails[2] + ", " + bookingDetails[3] + ", " + bookingDetails[4] + ", " + bookingDetails[5] + ", " + bookingDetails[6]);
		    		
		    	}System.out.println();
		    }
		    else {
		    	//No bookings found in the DB.
		    	System.out.println("No bookings registered in the database.");
		    	System.out.println();
		    }
		}
		
	}
}

