
//Java utilities
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

//Other classes
import Controller.ClientNetwork;
import Controller.ServerNetwork;
import Controller.ServerProtocol;
import View.BookingScreen;
import View.ServerView;

//JavaFx
import javafx.application.Application;
import javafx.stage.Stage;

public class Barry_S_Gym extends Application{
    public static void main(String[] args){
    	
    	//If no arguments or -c or -client, start as client.
    	if(args.length == 0 || args[0].equals("-c") || args[0].equals("-client")){ 
			launch();
    	}
    	//if arg == -s or -server, start as a server
    	else if(args[0].equals("-s") || args[0].equals("-server")){
    	    
    	    String dbUrl = "jdbc:mysql://localhost/barry_s_gym";
    	    String uname = "admin";
    	    String pass = "Password1!";
    	    
    	    try {
    	    	//Connect to db
        	    Connection connection = DriverManager.getConnection(dbUrl, uname, pass);
        	    
        	    if (connection != null){
            		System.out.println("Connection to barry_s_gym is successful.");
                }
        	    
        	    //Creates a ServerProtocol to connect to the DB
        	    ServerProtocol prot = new ServerProtocol(connection);
        	    

        	    //Creates a ServerView to handle user input (when used has server)
        	    ServerView view = new ServerView(prot);
        	    
        	    Thread serverThread = new Thread(view);
        	    
        	    serverThread.start();
        	    

        	    //Creates a new Thread when a client try to connect
        	    try {
    	    		ServerSocket serverSocket = new ServerSocket(8888);
    	    		
    	    		System.out.println("Multi-threaded server started at " + new Date());
        	    	
        	    	while(true) {
            	    	
                	    Socket socket = serverSocket.accept();

                	    ServerNetwork serverNetwork = new ServerNetwork(socket, prot);
                	    
                	    Thread thread = new Thread(serverNetwork);
                	    //New thread starts here
                	    thread.start();
        	    	} 
        	    }catch (IOException ex) {
        	    	System.out.println("Socket could not be initialised: " + ex);
        	    }
    	    }catch(SQLException ex) {
    	    	System.out.println("Houston, we got a problem: we can't reach the database, or create an object ServerProtocol, or create an object ServerView. Please send help.");
    	    }
    	} 
    }
    
    @Override
    public void start(Stage stage) {
    	try {
    		//Create a socket to connect to the server
    		Socket socket = new Socket("localhost", 8888);
    		//Create network class
    		ClientNetwork network = new ClientNetwork(socket);
    		
    		
    		//Create BookingScreen class
    		BookingScreen bScreen = new BookingScreen(network);
    		bScreen.showUI(stage);
    		
		} catch (UnknownHostException ex) {
			System.out.println("Error while connecting to the server: " + ex);
			System.exit(0);
		} catch (IOException ex) {
			System.out.println("Error while connecting to the server: " + ex);
			System.exit(0);
		}
    }
}


