package Controller;

//Java utilities
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Other classes
import Model.Booking;

public class ClientNetwork{
	
	private final Socket _socket;
	
	public ClientNetwork(Socket socket) {
		this._socket = socket;
	}
	
	public String add(String userInputs) throws NoSuchElementException, UnknownHostException, IOException{
		try {
		    //Create a PrintWriter to sent LISTALL protocol command to the server via the OutputStream
			PrintWriter out = new PrintWriter(_socket.getOutputStream());
			
			out.println("ADD," + userInputs);
			
			out.flush();
	    }catch (UnknownHostException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
	    }catch (IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;	
       	}

		//Read server's response
		try {
			//Create a Scanner to read the data from the InputStream
			InputStreamReader in = new InputStreamReader(_socket.getInputStream());
			
			Scanner scanner = new Scanner(in);
			
			String result = scanner.nextLine();
			
			return result;
			
		}catch(IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
       	}
	}
	
	//Returns a list of bookings
	public ArrayList<Booking> listAll() throws NoSuchElementException, UnknownHostException, IOException{
	    
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		ArrayList<String> results = new ArrayList<String>();
		
		String[] bookingString;
		//
		//Connecting and retrieving our objects
		//
		//Send LISTALL command to the server
	    //Create a PrintWriter to sent LISTALL protocol command to the server via the OutputStream
		PrintWriter out = new PrintWriter(_socket.getOutputStream());
		
		out.println("LISTALL");
		
		out.flush();
		
		//Read server's response
		
		//Create a Scanner to read the data from the InputStream
		InputStreamReader in = new InputStreamReader(_socket.getInputStream());
		
		Scanner scanner = new Scanner(in);
		
		String line;
		
		int listArrayLength;
		
		listArrayLength = Integer.parseInt(scanner.nextLine());
		
		for (int i = 0; i < listArrayLength; i++) {
			String serverMessage = scanner.nextLine();
			
			results.add(serverMessage);
		}
		
		//doesn't work, loops infinitely
		/*
		while(scanner.hasNextLine()) {
			
			String serverMessage = scanner.nextLine();
			
			results.add(serverMessage);
			
			System.out.println("Receiving from server");

		}
		*/
		
		
	
		if(results.size() > 0) {
	
			for (String s : results) {
				//Split the string based on commas
				bookingString = s.split(",");
				
				//Create new booking
				Booking booking = new Booking(bookingString[0], bookingString[1], bookingString[2], new SimpleDateFormat(bookingString[3]), new SimpleDateFormat(bookingString[4]), new SimpleDateFormat(bookingString[5]), bookingString[6]);
				
				//Push booking to ArrayList
				bookings.add(booking);
			}
		}
		return bookings;
    }
	
	public ArrayList<Booking> listPT(String userInput) throws NoSuchElementException, UnknownHostException, IOException{
	    
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		ArrayList<String> results = new ArrayList<String>();
		
		String[] bookingString;
		//
		//Connecting and retrieving our objects
		//
		
		userInput = "LISTPT," + userInput;
				
		//Send LISTPT command to the server
	    //Create a PrintWriter to sent LIST protocol command to the server via the OutputStream followed by a comma and the PT ID
		PrintWriter out = new PrintWriter(_socket.getOutputStream());
						
		out.println(userInput);
		
		out.flush();
		//Read server's response
		//Create a Scanner to read the data from the InputStream
		InputStreamReader in = new InputStreamReader(_socket.getInputStream());
		
		Scanner scanner = new Scanner(in);
		
		String line;
		
		int listArrayLength = 0;
		
		listArrayLength = Integer.parseInt(scanner.nextLine());
		
		
		
		for (int i = 0; i < listArrayLength; i++) {
			String serverMessage = scanner.nextLine();
			
			results.add(serverMessage);
			
		}
		
		if(results.size() > 0) {
			
			for (String s : results) {
				//Split the string based on commas
				bookingString = s.split(",");
				
				//Create new booking
				Booking booking = new Booking(bookingString[0], bookingString[1], bookingString[2], new SimpleDateFormat(bookingString[3]), new SimpleDateFormat(bookingString[4]), new SimpleDateFormat(bookingString[5]), bookingString[6]);
				
				//Push booking to ArrayList
				bookings.add(booking);
			}
		}
		
		return bookings;
	}
	
	public ArrayList<Booking> listClient(String userInput) throws NoSuchElementException, UnknownHostException, IOException{
	    
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		ArrayList<String> results = new ArrayList<String>();
		
		String[] bookingString;
		//
		//Connecting and retrieving our objects
		//
		
		userInput = "LISTCLIENT," + userInput;
				
		//Send LISTPT command to the server
	    //Create a PrintWriter to sent LIST protocol command to the server via the OutputStream followed by a comma and the PT ID
		PrintWriter out = new PrintWriter(_socket.getOutputStream());
						
		out.println(userInput);
		
		out.flush();
		//Read server's response
		//Create a Scanner to read the data from the InputStream
		InputStreamReader in = new InputStreamReader(_socket.getInputStream());
		
		Scanner scanner = new Scanner(in);
		
		String line;
		
		int listArrayLength = 0;
		
		listArrayLength = Integer.parseInt(scanner.nextLine());
		
		
		
		for (int i = 0; i < listArrayLength; i++) {
			String serverMessage = scanner.nextLine();
			
			results.add(serverMessage);
			
		}
		
		if(results.size() > 0) {
			
			for (String s : results) {
				//Split the string based on commas
				bookingString = s.split(",");
				
				//Create new booking
				Booking booking = new Booking(bookingString[0], bookingString[1], bookingString[2], new SimpleDateFormat(bookingString[3]), new SimpleDateFormat(bookingString[4]), new SimpleDateFormat(bookingString[5]), bookingString[6]);
				
				//Push booking to ArrayList
				bookings.add(booking);
			}
		}
		
		return bookings;
	}
	
	
	public ArrayList<Booking> listDay(String userInput) throws NoSuchElementException, UnknownHostException, IOException{
	    
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		ArrayList<String> results = new ArrayList<String>();
		
		String[] bookingString;
		//
		//Connecting and retrieving our objects
		//
		
		userInput = "LISTDAY," + userInput;
				
		//Send LISTPT command to the server
	    //Create a PrintWriter to sent LIST protocol command to the server via the OutputStream followed by a comma and the PT ID
		PrintWriter out = new PrintWriter(_socket.getOutputStream());
						
		out.println(userInput);
		
		out.flush();
		//Read server's response
		//Create a Scanner to read the data from the InputStream
		InputStreamReader in = new InputStreamReader(_socket.getInputStream());
		
		Scanner scanner = new Scanner(in);
		
		String line;
		
		int listArrayLength = 0;
		
		listArrayLength = Integer.parseInt(scanner.nextLine());
		
		for (int i = 0; i < listArrayLength; i++) {
			String serverMessage = scanner.nextLine();
			
			results.add(serverMessage);
		}
		
		if(results.size() > 0) {
			
			for (String s : results) {
				//Split the string based on commas
				bookingString = s.split(",");
				
				//Create new booking
				Booking booking = new Booking(bookingString[0], bookingString[1], bookingString[2], new SimpleDateFormat(bookingString[3]), new SimpleDateFormat(bookingString[4]), new SimpleDateFormat(bookingString[5]), bookingString[6]);
				
				//Push booking to ArrayList
				bookings.add(booking);
			}
		}
		
		return bookings;
	}
	
	public String update(String userInputs) throws NoSuchElementException, UnknownHostException, IOException{
		try {
		    //Create a PrintWriter to sent LISTALL protocol command to the server via the OutputStream
			PrintWriter out = new PrintWriter(_socket.getOutputStream());
			
			out.println("UPDATE," + userInputs);
			
			out.flush();
	    }catch (UnknownHostException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
	    }catch (IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;	
       	}

		//Read server's response
		try {
			//Create a Scanner to read the data from the InputStream
			InputStreamReader in = new InputStreamReader(_socket.getInputStream());
			
			Scanner scanner = new Scanner(in);
			
			String result = scanner.nextLine();
			
			return result;
			
		}catch(IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
       	}
	}
	
	public String delete(String userInputs) throws NoSuchElementException, UnknownHostException, IOException{
		try {
		    //Create a PrintWriter to sent LISTALL protocol command to the server via the OutputStream
			PrintWriter out = new PrintWriter(_socket.getOutputStream());
			
			out.println("DELETE," + userInputs);
			
			out.flush();
	    }catch (UnknownHostException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
	    }catch (IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;	
       	}

		//Read server's response
		try {
			//Create a Scanner to read the data from the InputStream
			InputStreamReader in = new InputStreamReader(_socket.getInputStream());
			
			Scanner scanner = new Scanner(in);
			
			String result = scanner.nextLine();
			
			return result;
			
		}catch(IOException ex) {
    		String errorMessage = "An error occured: " + ex;
    		return errorMessage;
       	}
	}
}

