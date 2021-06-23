package Controller;

//Java utilities
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Other classes
import Model.Booking;

public class ServerNetwork implements Runnable{
	private final Socket _socket;
	private final ServerProtocol _prot;
	
	public ServerNetwork(Socket socket, ServerProtocol prot) {
		this._socket = socket;
		this._prot = prot;
	}
	
	public void run() {
		
		
		
		InputStreamReader in;
		try {
			in = new InputStreamReader(_socket.getInputStream());
			//Create a Scanner to read the data from the InputStream
			
			while(true) {
				Scanner sc = new Scanner(in);
				
				String clientMessage = sc.nextLine();
				
				String[] clientTokens = clientMessage.split(",");
				
				if(clientTokens != null) {
			
					switch (clientTokens[0]) {
				    	case "ADD":
				    		//Calls method add() and passes the connection & user inputs without the first token
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Checking if number of arguments matches what we expect
				    		if(clientTokens.length < 6) {
				    			//Return error message
				    			try {
					    			PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
					    			out.println("An input is missing.");
				    				out.flush();
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}else if(clientTokens.length > 6) {
				    			//Return error message
				    			try {
					    			PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
					    			out.println("Too many inputs.");
				    				out.flush();
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}else {
				    			try {
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println(_prot.add(clientTokens));
				    				out.flush();
				    			}catch (SQLException ex) {
				    				//Returns error
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println("An error occured when a client tried to add a booking: " + ex);
				    				out.flush();
				    				System.out.println("An error occured when a client tried to add a booking: " + ex);
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}
				    		break;
				    	case "LISTALL":
				    		//Calls method listAll() and passes the connection
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Returns
								try {
									
									PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
									
									//Sending size of the ArrayList we're about to send
									out.println(_prot.listAll(clientTokens).size());
									
									out.flush();
									
									//Loop through all bookings
									for(Booking booking: _prot.listAll(clientTokens)) {
										//Stores all the booking details in an array
										String[] bookingDetails = booking.getBookingDetails();
										
										//Creates a string to store all details
										String allDetails = "";
										
										//Adds each detail and a comma to separate them
										for(String s : bookingDetails) {
											allDetails = allDetails + s.toString() + ",";
										}
										
										//Sends to client
										out.println(allDetails);
									}
									
									out.flush();
									
								}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);		
						       	}catch (SQLException ex) {
						       		//Returning 0 (size of the array we're supposed to send) to avoid having client loop indefinitely
						       		PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
									out.println("0");
									out.flush();
									
									System.out.println("An error occured while a client tried to list a booking: " + ex);
							}
								
				    		break;
				    	case "LISTPT":
				    		//Calls method listPT() and passes the connection & the user inputs without the first token
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Returns
			    			try {
			    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								
								//Sending size of the ArrayList we're about to send
								out.println(_prot.listPT(clientTokens).size());
								
								out.flush();
								
								//Loop through all bookings
								for(Booking booking: _prot.listPT(clientTokens)) {
									//Stores all the booking details in an array
									String[] bookingDetails = booking.getBookingDetails();
									
									//Creates a string to store all details
									String allDetails = "";
									
									//Adds each detail and a comma to separate them
									for(String s : bookingDetails) {
										allDetails = allDetails + s.toString() + ",";
									}
									//Sends to client
									out.println(allDetails);
								}
								
								out.flush();
			    			}catch (SQLException ex) {
			    				//Returning 0 (size of the array we're supposed to send) to avoid having client loop indefinitely
					       		PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								out.println("0");
								out.flush();
								
								System.out.println("An error occured while a client tried to list a booking: " + ex);
			    			}
				    		break;
				    	case "LISTCLIENT":
				    		//Calls method listClient() and passes the connection & the user inputs without the first token
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Returns
			    			try {
			    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								
								//Sending size of the ArrayList we're about to send
								out.println(_prot.listClient(clientTokens).size());
								
								out.flush();
								
								//Loop through all bookings
								for(Booking booking: _prot.listClient(clientTokens)) {
									//Stores all the booking details in an array
									String[] bookingDetails = booking.getBookingDetails();
									
									//Creates a string to store all details
									String allDetails = "";
									
									//Adds each detail and a comma to separate them
									for(String s : bookingDetails) {
										allDetails = allDetails + s.toString() + ",";
									}
									//Sends to client
									out.println(allDetails);
								}
								
								out.flush();
			    			}catch (SQLException ex) {
			    				//Returning 0 (size of the array we're supposed to send) to avoid having client loop indefinitely
					       		PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								out.println("0");
								out.flush();
								
								System.out.println("An error occured while a client tried to list a booking: " + ex);
			    			}
				    		break;
				    	case "LISTDAY":
				    		//Calls method listDay() and passes the connection & the user inputs without the first token
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Returns
			    			try {
			    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								
								//Sending size of the ArrayList we're about to send
			    				
								out.println(_prot.listDay(clientTokens).size());
								
								out.flush();
								
								//Loop through all bookings
								for(Booking booking: _prot.listDay(clientTokens)) {
									//Stores all the booking details in an array
									String[] bookingDetails = booking.getBookingDetails();
									
									//Creates a string to store all details
									String allDetails = "";
									
									//Adds each detail and a comma to separate them
									for(String s : bookingDetails) {
										allDetails = allDetails + s.toString() + ",";
									}
									//Sends to client
									out.println(allDetails);
								}
								
								out.flush();
			    			}catch (SQLException ex) {
			    				//Returning 0 (size of the array we're supposed to send) to avoid having client loop indefinitely
					       		PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
								out.println("0");
								out.flush();
								
								System.out.println("An error occured while a client tried to list a booking: " + ex);
			    			}
				    		break;
				    	case "UPDATE":
				    		//Calls method add() and passes the connection & user inputs without the first token
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		//Checking if number of arguments matches what we expect
				    		if(clientTokens.length < 6) {
				    			//Return error message
				    			try {
					    			PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
					    			out.println("An input is missing.");
				    				out.flush();
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}else if(clientTokens.length > 6) {
				    			//Return error message
				    			try {
					    			PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
					    			out.println("Too many inputs.");
				    				out.flush();
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}else {
				    			try {
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println(_prot.update(clientTokens));
				    				out.flush();
				    			}catch (SQLException ex) {
				    				//Returns error
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println("An error occured. Barry's Gym apologises for the inconvenience.");
				    				System.out.println("An error occured while serving a client.");
				    			}catch(IOException ex) {
						    		System.out.println("Error sending bookings to client: " + ex);	
				    			}
				    		}
				    		break;
				    	case "DELETE":
				    		clientTokens = Arrays.copyOfRange(clientTokens, 1, clientTokens.length);
				    		
				    		if(clientTokens.length == 1) {
				    			try {
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println(_prot.delete(clientTokens));
				    				out.flush();
				    			}catch (SQLException ex) {
				    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				    				out.println("An error occured. Barry's Gym apologises for the inconvenience.");
				    				System.out.println("An error occured: " + ex);
				    			}
				    		}else {
			    				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
			    				out.println("Please provide a Booking_ID for us to be able to delete it.");
			    				out.flush();
				    		}
				    		break;
				    	default:
				    		//Returns error
				    		PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
		    				out.println("An error occured. Barry's Gym apologises for the inconvenience.");
		    				System.out.println("An error occured while serving a client.");
			    	}
				}
			}
		} catch (IOException ex) {
			System.out.println("An error occured while reading a client's input: " + ex);
		} catch (NoSuchElementException ex) {
			System.out.println("A client disconnected");
		}
	}
}

