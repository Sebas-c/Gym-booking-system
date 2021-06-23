package Controller;

//Java utilities
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//Other classes
import Model.Booking;

public class ServerProtocol {
	
	private Connection _con;
	
	public ServerProtocol(Connection con){
		this._con = con;
	}


	public String add( String[] userInputs) throws SQLException {
		String booking_ID;
		String client_ID;
		String trainer_ID;
		java.sql.Time booking_Time = java.sql.Time.valueOf("00:00:00");
		java.sql.Date booking_Date = java.sql.Date.valueOf("1970-01-01");
		java.sql.Time booking_Duration = java.sql.Time.valueOf("00:00:00");
		String focus;
		
		boolean validTimes = true;
		boolean available = true;
		
		String lastBookingID;
		int lastBookingIDValue;
		
		//Storing the values
		client_ID = userInputs[0];
		trainer_ID = userInputs[1];
		focus = userInputs[5];
		
		
		//Trying to store the time/date values
		try {
			booking_Time = java.sql.Time.valueOf(userInputs[2]);
		}catch (IllegalArgumentException ex) {
			//Used to determine if any value failed the validation
			validTimes = false; 
			String message = "Invalid value for time.";
			return message;
		}
		
		try {
			booking_Date = java.sql.Date.valueOf(userInputs[3]);
		}catch (IllegalArgumentException ex) {
			validTimes = false; //Used to determine if any value failed the validation
			String message = "Invalid value for date.";
			return message;
		}
		
		try {
			booking_Duration = java.sql.Time.valueOf(userInputs[4]);
		}catch (IllegalArgumentException ex) {
			validTimes = false; //Used to determine if any value failed the validation
			String message = "Invalid value for duration.";
			return message;
		}
		
		//Creating a statement to query the server.
		Statement statement = _con.createStatement();
	    
	     
	    String query = "SELECT * FROM Booking"; //Create an SQL Query to retrieve all the bookings.
	    ResultSet resultSet = statement.executeQuery(query); //Store all the bookings retrieved from the DB.
	    
	    
	    //At least one booking exists and time/date values are valid, therefore we can check for double booking
	    if (resultSet != null && validTimes) {
	    	
	    	while (resultSet.next()) {
	    		//If client has a booking at the same date and time	    		
	    		if (resultSet.getString(2).equals(client_ID) && java.sql.Time.valueOf(resultSet.getString(4)).equals(booking_Time) && java.sql.Date.valueOf(resultSet.getString(5)).equals(booking_Date))
	    		{
	    			available = false;
	    			String message = "Client is already booked at the same time and date.";
	    			return message;
	    		}//If trainer has a booking at the same date and time
	    		else if(resultSet.getString(3).equals(trainer_ID) && java.sql.Time.valueOf(resultSet.getString(4)).equals(booking_Time) && java.sql.Date.valueOf(resultSet.getString(5)).equals(booking_Date))
	    		{
	    			available = false;
	    			String message = "Trainer is already booked at the same time and date.";
	    			return message;   			
	    		}
	    	}
	    	
	    	
	    	if(available) { //Both PT and Client are free at the time & date of the appointment
	    		//
		    	//Handling the Booking_ID: Storing it, removing "APP" and casting it to an int
		    	//
		    	resultSet.last(); //Moves to last row of the resultSet
		    	
		    	lastBookingID = resultSet.getString(1); //Retrieves Booking_ID from row
		    	
		    	lastBookingID = lastBookingID.replaceAll("APP", ""); //Removes "APP" from ID
		    	
		    	//Note: next line should be included in a trycatch for best practice but we neglected it because booking_ID is only handled by our program and DB, never a user input.
		    	lastBookingIDValue = Integer.parseInt(lastBookingID); //Parse the String to Int.
		    	
		    	lastBookingIDValue++; //Incrementing the value
		    	
		    	booking_ID = "APP" + String.format("%07d", lastBookingIDValue); //Converting to our desired format and storing in string
		    	
		    	
		    	
		    	//
		    	//Adding the new booking
		    	//
		    	
		    	//Using a try catch here to handle 
		    	try {
			    	//Reusing query for our preparedStatement
			    	query = "INSERT INTO Booking (Booking_ID, Client_ID, Trainer_ID, Booking_Time, Booking_Date, Booking_Duration, Focus) VALUES( ?, ?, ?, ?, ?, ?, ?)";
			    	
			    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
			    	
			    	prepStatement.setString(1, booking_ID);
			    	prepStatement.setString(2, client_ID);
			    	prepStatement.setString(3, trainer_ID);
			    	prepStatement.setTime(4, booking_Time);
			    	prepStatement.setDate(5, booking_Date);
			    	prepStatement.setTime(6, booking_Duration);
			    	prepStatement.setString(7, focus);
			    	
			    	int result = prepStatement.executeUpdate();
			    	
			    	if (result != 0) {
			    		String message = "Booking successfully added.";
		    			return message;
			    	}
		    	}catch (SQLException ex) {
		    		String message = "An error occured while adding your booking. Error: " + ex;
	    			return message;
		    	}
	    	}
	    	
	    }else if (validTimes){ //Time/Date values are valid and all bookings have been deleted from the DB, we want to restart our numbering from APP0000001
	    	booking_ID = "APP0000001";
	    	
	    	try {
		    	//Reusing query for our preparedStatement
		    	query = "INSERT INTO Booking (Booking_ID, Client_ID, Trainer_ID, Booking_Time, Booking_Date, Booking_Duration, Focus) VALUES( ?, ?, ?, ?, ?, ?, ?)";
		    	
		    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
		    	
		    	prepStatement.setString(1, booking_ID);
		    	prepStatement.setString(2, client_ID);
		    	prepStatement.setString(3, trainer_ID);
		    	prepStatement.setTime(4, booking_Time);
		    	prepStatement.setDate(5, booking_Date);
		    	prepStatement.setTime(6, booking_Duration);
		    	prepStatement.setString(7, focus);
		    	
		    	int result = prepStatement.executeUpdate();
		    	
		    	if (result != 0) {
		    		String message = "Booking successfully added.";
	    			return message;
		    	}
	    	}catch (SQLException ex) {
	    		String message = "An error occured while added your booking. Error: " + ex;
    			return message;
	    	}
	    }
	    //In case my spaghetti code messes up, return error.
	    String message = "An error occured while updating your booking. Please ensure the IDs you provided are correct.";
		return message;
	}

	public ArrayList<Booking> listAll(String[] userInputs) throws SQLException {
	    //
		//Connecting and retrieving our objects
		//
		
		Statement statement = _con.createStatement();
    	
    	ArrayList<Booking> bookings = new ArrayList<Booking>();//Create an ArrayList to store all the bookings.
	    
	    String query = "SELECT * FROM Booking"; //Create an SQL Query to retrieve all the bookings.
	    ResultSet resultSet = statement.executeQuery(query); //Store all the bookings retrieved from the DB.

	    while(resultSet.next()) //While resultSet is not empty.
	    {
	    	//Creates a new booking with the current row.
	    	Booking booking = new Booking( resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), new SimpleDateFormat(resultSet.getString(4)), new SimpleDateFormat(resultSet.getString(5)), new SimpleDateFormat(resultSet.getString(6)), resultSet.getString(7));
	    	//Push the booking in 
	    	bookings.add(booking);
	    }
	    
	    //Returning ArrayList of bookings.
	    return bookings;
    }
	
	public ArrayList<Booking> listPT(String[] userInputs) throws SQLException {
		
		ArrayList<Booking> bookings = new ArrayList<Booking>();//Create an ArrayList to store all the bookings.
				
		for (String s : userInputs) {
			String query = "SELECT * FROM Booking WHERE Trainer_ID = ?";
			
	    	
	    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
	    	
	    	prepStatement.setString(1, s);
	    	
	    	ResultSet results = prepStatement.executeQuery();
	    	
	    	while(results.next()) //While resultSet is not empty.
		    {
		    	//Creates a new booking with the current row.
		    	Booking booking = new Booking( results.getString(1), results.getString(2), results.getString(3), new SimpleDateFormat(results.getString(4)), new SimpleDateFormat(results.getString(5)), new SimpleDateFormat(results.getString(6)), results.getString(7));
		    	//Push the booking in 
		    	bookings.add(booking);
		    }
		}
		return bookings;
		
	}
	
	public ArrayList<Booking> listClient(String[] userInputs) throws SQLException {
		
		ArrayList<Booking> bookings = new ArrayList<Booking>();//Create an ArrayList to store all the bookings.
				
		for (String s : userInputs) {
			String query = "SELECT * FROM Booking WHERE Client_ID = ?";
	    	
	    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
	    	
	    	prepStatement.setString(1, s);
	    	
	    	ResultSet results = prepStatement.executeQuery();
	    	
	    	while(results.next()) //While resultSet is not empty.
		    {
		    	//Creates a new booking with the current row.
		    	Booking booking = new Booking( results.getString(1), results.getString(2), results.getString(3), new SimpleDateFormat(results.getString(4)), new SimpleDateFormat(results.getString(5)), new SimpleDateFormat(results.getString(6)), results.getString(7));
		    	//Push the booking in 
		    	bookings.add(booking);
		    }
		}
		
		return bookings;
		
	}

	public ArrayList<Booking> listDay(String[] userInputs) throws SQLException {

		ArrayList<Booking> bookings = new ArrayList<Booking>();//Create an ArrayList to store all the bookings.
				
		for (String s : userInputs) {
			String query = "SELECT * FROM Booking WHERE Booking_date = ?";
	    	
	    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
	    	
	    	prepStatement.setString(1, s);
	    	
	    	ResultSet results = prepStatement.executeQuery();
	    	
	    	while(results.next()) //While resultSet is not empty.
		    {
		    	//Creates a new booking with the current row.
		    	Booking booking = new Booking( results.getString(1), results.getString(2), results.getString(3), new SimpleDateFormat(results.getString(4)), new SimpleDateFormat(results.getString(5)), new SimpleDateFormat(results.getString(6)), results.getString(7));
		    	//Push the booking in 
		    	bookings.add(booking);
		    }
		}
		return bookings;
	}
	
	public String update(String[] userInputs) throws SQLException {
		String booking_ID = userInputs[0];
		String client_ID = userInputs[1];
		String trainer_ID = userInputs[2];
		java.sql.Time booking_Time = java.sql.Time.valueOf("00:00:00");
		java.sql.Date booking_Date = java.sql.Date.valueOf("1970-01-01");
		java.sql.Time booking_Duration = java.sql.Time.valueOf("00:00:00");
		String focus = userInputs[6];
		
		Boolean available = true;
		Boolean validTimes = true;
		
		
		//ServerView serverView = new ServerView();
		
		//Trying to store the time/date values
		try {
			booking_Time = java.sql.Time.valueOf(userInputs[3]);
		}catch (IllegalArgumentException ex) {
			validTimes = false; //Used to determine if any value failed the validation
			String message = "Invalid value for time.";
			return message;
		}
		
		try {
			booking_Date = java.sql.Date.valueOf(userInputs[4]);
		}catch (IllegalArgumentException ex) {
			validTimes = false; //Used to determine if any value failed the validation
			String message = "Invalid value for date.";
			return message;
		}
		
		try {
			booking_Duration = java.sql.Time.valueOf(userInputs[5]);
		}catch (IllegalArgumentException ex) {
			validTimes = false; //Used to determine if any value failed the validation
			String message = "Invalid value for duration.";
			return message;
		}
		
		//Creating a statement to query the server.
		Statement statement = _con.createStatement();
	    
	     
	    String query = "SELECT * FROM Booking"; //Create an SQL Query to retrieve all the bookings.
	    ResultSet resultSet = statement.executeQuery(query); //Store all the bookings retrieved from the DB.
	    
	    
	    //At least one booking exists and time/date values are valid, therefore we can check for double booking
	    if (resultSet != null && validTimes) {
	    	
	    	while (resultSet.next()) {
	    		//If client has a booking at the same date and time	    		
	    		if (resultSet.getString(2).equals(client_ID) && java.sql.Time.valueOf(resultSet.getString(4)).equals(booking_Time) && java.sql.Date.valueOf(resultSet.getString(5)).equals(booking_Date))
	    		{
	    			available = false;
	    			String message = "Client is already booked at the same time and date.";
	    			return message;
	    		}//If trainer has a booking at the same date and time
	    		else if(resultSet.getString(3).equals(trainer_ID) && java.sql.Time.valueOf(resultSet.getString(4)).equals(booking_Time) && java.sql.Date.valueOf(resultSet.getString(5)).equals(booking_Date))
	    		{
	    			available = false;
	    			String message = "Trainer is already booked at the same time and date.";
	    			return message;  			
	    		}
	    	}
	    	
	    	
	    	if(available) { //Both PT and Client are free at the time & date of the appointment
	    			    	
		    	//Updating a try catch here to handle 
		    	try {
		    		
		    		String updateQuery = "UPDATE Booking SET Client_ID=?, Trainer_ID=?, Booking_time=?, Booking_date=?, Booking_duration=?, Focus=? WHERE Booking_ID = ?";
			    	
			    	PreparedStatement prepStatement = _con.prepareStatement(updateQuery); //Creating a prepared statement
			    	
			    	//Adding our inputs to the prepared statement
			    	prepStatement.setString(1, client_ID);
			    	prepStatement.setString(2, trainer_ID);
			    	prepStatement.setTime(3, booking_Time);
			    	prepStatement.setDate(4, booking_Date);
			    	prepStatement.setTime(5, booking_Duration);
			    	prepStatement.setString(6, focus);
			    	prepStatement.setString(7, booking_ID);

			    	
			    	int result = prepStatement.executeUpdate();
			    	
			    	if(result != 0) {
			    		String message = "Booking successfully updated.";
		    			return message;
			    	}
			    	else {
			    		String message = "An error occured while updating your booking. Please ensure that the IDs you provided are correct.";
		    			return message;
			    	}
			    	
		    	}catch (SQLException ex) {
		    		String message = "An error occured while updating your booking. Error: " + ex;
	    			return message;
		    	}
	    	}
	    	
	    }else {
	    	String message = "There are no bookings in the database. Please add some if you want to play around with the update function.";
			return message;
	    }
		
	    String message = "An error occured while updating your booking. Please ensure that the IDs you provided are correct.";
		return message;
		
	}
	
	public String delete(String[] userInputs) throws SQLException {
		String booking_ID = userInputs[0];
		
		String query = "DELETE FROM Booking WHERE Booking_ID = ?";
    	
    	PreparedStatement prepStatement = _con.prepareStatement(query); //Creating a prepared statement
    	
    	prepStatement.setString(1, booking_ID); //Adding our input to the prepped statement
    	
    	int result = prepStatement.executeUpdate();
    	
    	if(result != 0) {
    		String message = "Your booking has been successfully deleted.";
    		return message;
    	}
    	else {
    		String message = "An error occured while deleting your booking. Please ensure that the ID provided is correct.";
    		return message;
    	}
    }
	
	public void closeConnection() throws SQLException {
		_con.close();
	}
}

