package View;

//Java utilities
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

//Other classes
import Controller.ClientNetwork;
import Model.Booking;

//JavaFx
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.ReadOnlyStringWrapper;

public class BookingScreen{
	
	private ClientNetwork _network;
	
	public BookingScreen(ClientNetwork network){
		this._network = network;
	}
	
	public void showUI(Stage stage) {
        Label mainLabel = new Label("Welcome to Barry's Gym booking software.");
        
        //Creating our buttons
        Button btnAdd = new Button("Add a booking");
        btnAdd.setMinWidth(250);
        Button btnListAll = new Button("List all bookings");
        btnListAll.setMinWidth(250);
        Button btnListPT = new Button("List by PT");
        btnListPT.setMinWidth(250);
        Button btnListClient = new Button("List by client");
        btnListClient.setMinWidth(250);
        Button btnListDay = new Button("List by day");
        btnListDay.setMinWidth(250);
        Button btnDelete = new Button("Delete a booking");
        btnDelete.setMinWidth(250);
        Button btnUpdate = new Button("Update");
        btnUpdate.setMinWidth(250);
        
        //Creating a button to quit the client
    	Button btnClose = new Button("Close the application");
    	btnClose.setMinWidth(250);
        
        //Assigning events to the buttons (note: btnClose needs the stage to be declared so that event is created at the end of this method)
    	btnAdd.setOnAction(event ->
        {
        	addUI(stage);
        });
        btnListAll.setOnAction(event ->
        {
        	listAllUI(stage);
        });
        btnListPT.setOnAction(event ->
        {
        	listPTUI(stage);
        });
        btnListClient.setOnAction(event ->
        {
        	listClientUI(stage);
        });
        btnListDay.setOnAction(event ->
        {
        	listDayUI(stage);
        });
        btnDelete.setOnAction(event ->
        {
        	deleteUI(stage);
        });
        btnUpdate.setOnAction(event ->
        {
        	updateUI(stage);
        });
        
        //Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 25, 10, 25));
        
        //Adding label to gridPane (col 0, row 0) 
    	gridPane.add(mainLabel, 0, 0);
    	GridPane.setHalignment(mainLabel, javafx.geometry.HPos.CENTER);
    	//Adding btns to gridPane (col 0, row 1)
    	gridPane.add(btnAdd, 0, 1);
    	GridPane.setHalignment(btnAdd, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnListAll, 0, 2);
    	GridPane.setHalignment(btnListAll, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnListPT, 0, 3);
    	GridPane.setHalignment(btnListPT, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnListClient, 0, 4);
    	GridPane.setHalignment(btnListClient, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnListDay, 0, 5);
    	GridPane.setHalignment(btnListDay, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnUpdate, 0, 6);
    	GridPane.setHalignment(btnUpdate, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnDelete, 0, 7);
    	GridPane.setHalignment(btnDelete, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnClose, 0, 8);
    	GridPane.setHalignment(btnClose, javafx.geometry.HPos.CENTER);
        
        
        Scene mainScene = new Scene(new StackPane(gridPane), 430, 350);
        
        stage.setTitle("Barry's Gym booking system");
        
        //Setting action of close button
    	btnClose.setOnAction(event ->{
    		stage.close();
    	});
        
        stage.setScene(mainScene);
        stage.show();
	}
	
	
	

	private void addUI(Stage mainStage) {
		//Creating Labels
		Label labelBooking = new Label("Add a new booking");
		Label labelClientID = new Label("Enter a Client_ID");
		Label labelTrainerID = new Label("Enter a Trainer_ID");
		Label labelTime = new Label("Enter a time");
		Label labelDate = new Label("Enter a date");
		Label labelDuration = new Label("Enter a duration");
		Label labelFocus = new Label("Enter a Focus");


		//Creating Buttons
		Button btnAddBooking = new Button("Submit your booking");
		Button btnBackToMain = new Button("Go back to main menu");
		
		//Creating TextFields
		TextField textFieldClientID = new TextField();
		TextField textFieldTrainerID = new TextField();
		//TextField textFieldTime = new TextField();
		//TextField textFieldDuration = new TextField();
		TextField textFieldFocus = new TextField();

		//Creating DatePickers
		DatePicker timePicker = new DatePicker();
		
		//Creating Combo Boxes for hours
		ObservableList<String> hours = FXCollections.observableArrayList();
		for (Integer i = 0; i < 24; i++) {
			if(i < 10) {
				hours.add("0" + i);
			}else {
				hours.add(i.toString());
			}
		}
		ComboBox<String> comboBoxHoursTime = new ComboBox<String>(hours);
		ComboBox<String> comboBoxHoursDuration = new ComboBox<String>(hours);
		ObservableList<String> minutes = FXCollections.observableArrayList();
		for (Integer i = 0; i < 60; i++) {
			if(i < 10) {
				minutes.add("0" + i);
			}else {
				minutes.add(i.toString());
			}
		}
		ComboBox<String> comboBoxMinutesTime = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxMinutesDuration = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxSecondsTime = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxSecondsDuration = new ComboBox<String>(minutes);
		
		
		//Creating Hbox for times
		HBox hBoxTime = new HBox(comboBoxHoursTime, comboBoxMinutesTime, comboBoxSecondsTime);
		HBox hBoxDuration = new HBox(comboBoxHoursDuration, comboBoxMinutesDuration, comboBoxSecondsDuration);
		hBoxTime.setAlignment(Pos.BASELINE_CENTER);
		hBoxDuration.setAlignment(Pos.BASELINE_CENTER);
		
		//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 25, 10, 25));
    	
        //Adding label to gridPane (col 0, row 0) 
    	gridPane.add(labelBooking, 0, 0);
    	GridPane.setHalignment(labelBooking, javafx.geometry.HPos.CENTER);
    	//Adding elements to gridPane (col 0, row 1)
    	gridPane.add(labelClientID, 0, 1);
    	GridPane.setHalignment(labelClientID, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldClientID, 0, 2);
    	GridPane.setHalignment(textFieldClientID, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelTrainerID, 0, 3);
    	GridPane.setHalignment(labelTrainerID, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldTrainerID, 0, 4);
    	GridPane.setHalignment(textFieldTrainerID, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelTime, 0, 5);
    	GridPane.setHalignment(labelTime, javafx.geometry.HPos.CENTER);
    	gridPane.add(hBoxTime, 0, 6);
    	GridPane.setHalignment(hBoxTime, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelDate, 0, 7);
    	GridPane.setHalignment(labelDate, javafx.geometry.HPos.CENTER);
    	gridPane.add(timePicker, 0, 8);
    	GridPane.setHalignment(timePicker, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelDuration, 0, 9);
    	GridPane.setHalignment(labelDuration, javafx.geometry.HPos.CENTER);
    	gridPane.add(hBoxDuration, 0, 10);
    	GridPane.setHalignment(hBoxDuration, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelFocus, 0, 11);
    	GridPane.setHalignment(labelFocus, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldFocus, 0, 12);
    	GridPane.setHalignment(textFieldFocus, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnAddBooking, 0, 13);
    	GridPane.setHalignment(btnAddBooking, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnBackToMain, 0, 14);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	
    	//Scene
    	Scene addScene = new Scene(new StackPane(gridPane), 480, 600);
    	
    	//Stage
    	Stage addStage = new Stage();
    	addStage.setTitle("Add bookings");
    	addStage.setScene(addScene);
    	addStage.initOwner(mainStage);
    	addStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	addStage.setX(mainStage.getX()+ 50);
    	addStage.setY(mainStage.getY()+ 50);
    	
    	//Assigning an event to the buttons
    	btnBackToMain.setOnAction(event ->
        {
        	addStage.close();
        });
    	
    	btnAddBooking.setOnAction(event ->
    	{
    		try {
    			//Retrieving date as LocalDate, then converting to String
    			LocalDate datePicked;    			
    			String dateString;
    			String format = "yyyy-MM-dd"; 
    			
    			datePicked = timePicker.getValue();
    			dateString = datePicked.format(DateTimeFormatter.ofPattern(format));
    			
    			//Retrieving time and formatting it to String
    			String time = comboBoxHoursTime.getValue() + ":" + comboBoxMinutesTime.getValue() + ":" + comboBoxSecondsTime.getValue();
    			
    			//Retrieving duration and formatting it to String
    			String duration = comboBoxHoursDuration.getValue() + ":" + comboBoxMinutesDuration.getValue() + ":" + comboBoxSecondsDuration.getValue();
    			        		
        		String newBooking = textFieldClientID.getText() + "," + textFieldTrainerID.getText() + "," + time + "," + dateString + "," + duration + "," + textFieldFocus.getText();
        		String result = _network.add(newBooking);
				showResultWindow(result, addStage);
				
			}catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}catch(NullPointerException ex) {
				//If server can't be reached. Show error window.
				Label errorLabel = new Label("Please make sure all fields have an input.");
				Label errorCodeLabel = new Label(ex.toString());
				
				//Creating a grid and setting its design constraints
		    	GridPane gridPaneError = new GridPane();
		    	ColumnConstraints column1Error = new ColumnConstraints();
		    	column1Error.setPercentWidth(100);
		    	gridPaneError.getColumnConstraints().addAll(column1Error);
		    	gridPaneError.setVgap(10);
		    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
		    	row1.setPercentHeight(25);
		    	row2.setPercentHeight(50);
		    	row3.setPercentHeight(25);
		    	gridPaneError.getRowConstraints().addAll(row1, row2, row3);
		    	
		    	//Creating a button to go back to the main menu
		    	Button btnClose = new Button("Close");
		    	
		    	//Adding labels to gridPane (col 0, row 0 and row 1) 
		    	gridPaneError.add(errorLabel, 0, 0);
		    	GridPane.setHalignment(errorLabel, javafx.geometry.HPos.CENTER);
		    	gridPaneError.add(errorCodeLabel, 0, 1);
		    	GridPane.setHalignment(errorCodeLabel, javafx.geometry.HPos.CENTER);
		    	//Adding btn to gridPane (col 0, row 2)
		    	gridPaneError.add(btnClose, 0, 2);
		    	GridPane.setHalignment(btnClose, javafx.geometry.HPos.CENTER);
		    	
		    	//Scene
		    	Scene errorScene = new Scene(new StackPane(gridPaneError), 320, 180);
				
				Stage errorStage = new Stage();
		    	errorStage.setTitle("Error");
		    	errorStage.setScene(errorScene);
		    	errorStage.initOwner(mainStage);
		    	errorStage.initModality(Modality.APPLICATION_MODAL);
		    	
		    	//Offset position
		    	errorStage.setX(mainStage.getX()+ 50);
		    	errorStage.setY(mainStage.getY()+ 50);
		    	
		    	//Assigning an event to the button BackToMain
		    	btnClose.setOnAction(eventClose ->
		        {
		        	errorStage.close();
		        });
				
		    	errorStage.showAndWait();
			}
    	});
    	
    	addStage.showAndWait();
	}

	//Creates a new window to display a table containing all bookings
	private void listAllUI(Stage mainStage) {
		//Creating an ArrayList to store our bookings
		ArrayList<Booking> bookings;
		
		//Try fetching results from server and displaying them
		try {
			bookings =_network.listAll();

	    	//Label
	    	Label l2 = new Label("List of all bookings");
	    	
	    	//Table
	    	TableView<Booking> tableView = new TableView<Booking>();
	    	tableView.setEditable(false);
	    	
	    	//Columns
	    	//First column: Booking_ID
	    	TableColumn<Booking, String> bookingIDCol = new TableColumn<Booking, String>("Booking_ID");
	    	bookingIDCol.setMinWidth(150);
	    	bookingIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_bookingID"));
	    	
	    	//Second column: Client_ID
	    	TableColumn<Booking, String> clientIDCol = new TableColumn<Booking, String>("Client_ID");
	    	clientIDCol.setMinWidth(150);
	    	clientIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_clientID"));
	    	
	    	//Third column: Trainer_ID
	    	TableColumn<Booking, String> trainerIDCol = new TableColumn<Booking, String>("Trainer_ID");
	    	trainerIDCol.setMinWidth(150);
	    	trainerIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_trainerID"));
	    	
	    	//Fourth column: Booking_Time
	    	TableColumn<Booking, String> timeCol = new TableColumn<Booking, String>("Booking_Time");
	    	timeCol.setMinWidth(150);
	    	//Next line somehow gets the value to be displayed correctly.
	    	//Basically gets the object related to the cell (type booking), calls get_time and converts it to a String.
	    	timeCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_time().format(new Date())));
	    	
	    	//Fifth column: Booking_Date
	    	TableColumn<Booking, String> dateCol = new TableColumn<Booking, String>("Booking_Date");
	    	dateCol.setMinWidth(150);
	    	dateCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_date().format(new Date())));
	    	
	    	//Sixth column: Booking_Duration
	    	TableColumn<Booking, String> durationCol = new TableColumn<Booking, String>("Booking_Duration");
	    	durationCol.setMinWidth(150);
	    	durationCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_duration().format(new Date())));
	    	
	    	//Seventh column: Focus
	    	TableColumn<Booking, String> focusCol = new TableColumn<Booking, String>("Focus");
	    	focusCol.setMinWidth(150);
	    	focusCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_focus"));
	    	
	    	//Adding all columns to the table
	    	tableView.getColumns().addAll(bookingIDCol, clientIDCol, trainerIDCol, timeCol, dateCol, durationCol, focusCol);
	    	
	    	//Creating a button to go back to the main menu
	    	Button btnBackToMain = new Button("Go back to main menu");
	    	
	    	//Creating a grid and setting its design constraints
	    	GridPane gridPane = new GridPane();
	    	ColumnConstraints column1 = new ColumnConstraints();
	        column1.setPercentWidth(100);
	        gridPane.getColumnConstraints().addAll(column1);
	        gridPane.setVgap(10);
	    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
	    	row1.setPercentHeight(5);
	    	row2.setPercentHeight(85);
	    	row3.setPercentHeight(10);
	    	gridPane.getRowConstraints().addAll(row1, row2, row3);
	    	gridPane.setPadding(new Insets(10, 10, 10, 10));
	    	
	        //Adding label to gridPane (col 0, row 0) 
	    	gridPane.add(l2, 0, 0);
	    	GridPane.setHalignment(l2, javafx.geometry.HPos.CENTER);
	    	//Adding table to gridPane (col 0, row 1)
	    	gridPane.add(tableView, 0, 1);
	    	//Adding btn to gridPane (col 0, row 2)
	    	gridPane.add(btnBackToMain, 0, 2);
	    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
	
	    	//Populating the table	    	
	    	if (bookings.size() > 0) {
		    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
		    	{
		    		String[] bookingDetails = b.getBookingDetails();
		    		tableView.getItems().addAll(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));
		    	}
		    }
		    else {
		    	//No bookings found in the DB.
		    	//Adding placeholder instead of table
		    	tableView.setPlaceholder(new Label("No bookings stored in the database."));
		    }
	    	
	    	//Scene
	    	Scene listAllScene = new Scene(new StackPane(gridPane), 1280, 720);
	    	
	    	//Stage
	    	Stage listAllStage = new Stage();
	    	listAllStage.setTitle("All bookings");
	    	listAllStage.setScene(listAllScene);
	    	listAllStage.initOwner(mainStage);
	    	listAllStage.initModality(Modality.APPLICATION_MODAL);
	    	
	    	//Offset position
	    	listAllStage.setX(mainStage.getX()+ 50);
	    	listAllStage.setY(mainStage.getY()+ 50);
	    	
	    	//Assigning an event to the button BackToMain
	    	btnBackToMain.setOnAction(event ->
	        {
	        	listAllStage.close();
	        });
	    	listAllStage.showAndWait();
		} catch(NoSuchElementException ex) {
			showErrorWindow(ex, mainStage);
		} catch(UnknownHostException ex) {
			showErrorWindow(ex, mainStage);
		} catch(IOException ex) {
			showErrorWindow(ex, mainStage);
		}
	}
	
	private void listPTUI(Stage mainStage) {		
    	//Label
    	Label trainerLabel = new Label("Enter a TrainerID");
    	
    	//Table
    	TableView<Booking> tableView = new TableView<Booking>();
    	tableView.setEditable(true);
    	tableView.setPlaceholder(new Label("Use the textfield and button to search for bookings"));
    	
    	//
    	//Columns
    	//
    	
    	//First column: Booking_ID
    	TableColumn<Booking, String> bookingIDCol = new TableColumn<Booking, String>("Booking_ID");
    	bookingIDCol.setMinWidth(150);
    	bookingIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_bookingID"));
    	
    	//Second column: Client_ID
    	TableColumn<Booking, String> clientIDCol = new TableColumn<Booking, String>("Client_ID");
    	clientIDCol.setMinWidth(150);
    	clientIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_clientID"));
    	
    	//Third column: Trainer_ID
    	TableColumn<Booking, String> trainerIDCol = new TableColumn<Booking, String>("Trainer_ID");
    	trainerIDCol.setMinWidth(150);
    	trainerIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_trainerID"));
    	
    	//Fourth column: Booking_Time
    	TableColumn<Booking, String> timeCol = new TableColumn<Booking, String>("Booking_Time");
    	timeCol.setMinWidth(150);
    	//Next line somehow gets the value to be displayed correctly.
    	//Basically gets the object related to the cell (type booking), calls get_time and converts it to a String.
    	timeCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_time().format(new Date())));
    	
    	//Fifth column: Booking_Date
    	TableColumn<Booking, String> dateCol = new TableColumn<Booking, String>("Booking_Date");
    	dateCol.setMinWidth(150);
    	dateCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_date().format(new Date())));
    	
    	//Sixth column: Booking_Duration
    	TableColumn<Booking, String> durationCol = new TableColumn<Booking, String>("Booking_Duration");
    	durationCol.setMinWidth(150);
    	durationCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_duration().format(new Date())));
    	
    	//Seventh column: Focus
    	TableColumn<Booking, String> focusCol = new TableColumn<Booking, String>("Focus");
    	focusCol.setMinWidth(150);
    	focusCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_focus"));
    	
    	//Adding all columns to the table
    	tableView.getColumns().addAll(bookingIDCol, clientIDCol, trainerIDCol, timeCol, dateCol, durationCol, focusCol);
    	
    	
    	ObservableList<Booking> data = FXCollections.observableArrayList();
    	tableView.setItems(data);
    	
    	//
    	//Buttons
    	//
    	
    	//Creating a button to send request to server
    	Button btnListPT = new Button("List bookings by PT");
    	//Creating a button to go back to the main menu
    	Button btnBackToMain = new Button("Go back to main menu");
    	
    	
    	//
    	//Textfield
    	//
    	TextField trainerIDField = new TextField("Enter the Trainer_ID here");
    	
    	//
    	//GridPane
    	//
    	
    	//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column3 = new ColumnConstraints();
        column1.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setMinWidth(720);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
    	row1.setPercentHeight(5);
    	row2.setPercentHeight(85);
    	row3.setPercentHeight(10);
    	gridPane.getRowConstraints().addAll(row1, row2, row3);
    	gridPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    	gridPane.setPadding(new Insets(10, 10, 10, 10));
    	
    	
        //Adding label to gridPane (col 0, row 0, colSpan 1, rowSpan 1) 
    	gridPane.add(trainerLabel, 0, 0);
    	GridPane.setHalignment(trainerLabel, javafx.geometry.HPos.CENTER);
    	//Add textfield to gridPane(col 1, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(trainerIDField, 1, 0);
    	GridPane.setHalignment(trainerIDField, javafx.geometry.HPos.CENTER);
    	//Add btnListPT to gridPane(col 2, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(btnListPT, 2, 0);
    	GridPane.setHalignment(btnListPT, javafx.geometry.HPos.CENTER);
    	//Adding table to gridPane (col 0, row 1)
    	gridPane.add(tableView, 0, 1, 3, 1);
    	//Adding btn to gridPane (col 0, row 2)
    	gridPane.add(btnBackToMain, 0, 2, 3, 1);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	
    	
    	//Scene
    	Scene listTrainerScene = new Scene(new StackPane(gridPane), 1280, 720);
    	
    	//Stage
    	Stage listTrainerStage = new Stage();
    	listTrainerStage.setTitle("Bookings by Trainer_ID");
    	listTrainerStage.setScene(listTrainerScene);
    	listTrainerStage.initOwner(mainStage);
    	listTrainerStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	listTrainerStage.setX(mainStage.getX()+ 50);
    	listTrainerStage.setY(mainStage.getY()+ 50);
    	
    	//Adjust size of gridPane to window
    	gridPane.prefWidthProperty().bind(mainStage.widthProperty());
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	listTrainerStage.close();
        });
    	

    	btnListPT.setOnAction(event -> {

    		try {

    			ArrayList<Booking> bookings;
    			String trainerID;
    			
    			trainerID = trainerIDField.getText();
    			//Input validation should be added here
				bookings =_network.listPT(trainerID);
				
				data.removeAll(data);

		    	//Populating the table	    	
		    	if (bookings.size() > 0) {
			    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
			    	{
			    		String[] bookingDetails = b.getBookingDetails();
			    		tableView.getItems().addAll(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));
			    		//data.add(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));

			    	}
			    	tableView.refresh();
			    	trainerIDField.clear();
			    }
			    else {
			    	//No bookings found in the DB.
			    	//Adding placeholder instead of table
			    	tableView.setPlaceholder(new Label("No bookings matching this ID in the database."));
			    	tableView.refresh();
			    	trainerIDField.clear();
			    }
			} catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}
    	});
    	

    	listTrainerStage.showAndWait();	
	}

	private void listClientUI(Stage mainStage) {
		//Label
    	Label clientLabel = new Label("Enter a Client_ID");
    	
    	//Table
    	TableView<Booking> tableView = new TableView<Booking>();
    	tableView.setEditable(true);
    	tableView.setPlaceholder(new Label("Use the textfield and button to search for bookings"));
    	
    	//
    	//Columns
    	//
    	
    	//First column: Booking_ID
    	TableColumn<Booking, String> bookingIDCol = new TableColumn<Booking, String>("Booking_ID");
    	bookingIDCol.setMinWidth(150);
    	bookingIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_bookingID"));
    	
    	//Second column: Client_ID
    	TableColumn<Booking, String> clientIDCol = new TableColumn<Booking, String>("Client_ID");
    	clientIDCol.setMinWidth(150);
    	clientIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_clientID"));
    	
    	//Third column: Trainer_ID
    	TableColumn<Booking, String> trainerIDCol = new TableColumn<Booking, String>("Trainer_ID");
    	trainerIDCol.setMinWidth(150);
    	trainerIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_trainerID"));
    	
    	//Fourth column: Booking_Time
    	TableColumn<Booking, String> timeCol = new TableColumn<Booking, String>("Booking_Time");
    	timeCol.setMinWidth(150);
    	//Next line somehow gets the value to be displayed correctly.
    	//Basically gets the object related to the cell (type booking), calls get_time and converts it to a String.
    	timeCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_time().format(new Date())));
    	
    	//Fifth column: Booking_Date
    	TableColumn<Booking, String> dateCol = new TableColumn<Booking, String>("Booking_Date");
    	dateCol.setMinWidth(150);
    	dateCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_date().format(new Date())));
    	
    	//Sixth column: Booking_Duration
    	TableColumn<Booking, String> durationCol = new TableColumn<Booking, String>("Booking_Duration");
    	durationCol.setMinWidth(150);
    	durationCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_duration().format(new Date())));
    	
    	//Seventh column: Focus
    	TableColumn<Booking, String> focusCol = new TableColumn<Booking, String>("Focus");
    	focusCol.setMinWidth(150);
    	focusCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_focus"));
    	
    	//Adding all columns to the table
    	tableView.getColumns().addAll(bookingIDCol, clientIDCol, trainerIDCol, timeCol, dateCol, durationCol, focusCol);
    	
    	
    	ObservableList<Booking> data = FXCollections.observableArrayList();
    	tableView.setItems(data);
    	
    	//
    	//Buttons
    	//
    	
    	//Creating a button to send request to server
    	Button btnListPT = new Button("List bookings by client");
    	//Creating a button to go back to the main menu
    	Button btnBackToMain = new Button("Go back to main menu");
    	
    	
    	//
    	//Textfield
    	//
    	TextField clientIDField = new TextField("Enter the Client_ID here");
    	
    	//
    	//GridPane
    	//
    	
    	//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column3 = new ColumnConstraints();
        column1.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setMinWidth(720);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
    	row1.setPercentHeight(5);
    	row2.setPercentHeight(85);
    	row3.setPercentHeight(10);
    	gridPane.getRowConstraints().addAll(row1, row2, row3);
    	gridPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    	gridPane.setPadding(new Insets(10, 10, 10, 10));
    	
    	
        //Adding label to gridPane (col 0, row 0, colSpan 1, rowSpan 1) 
    	gridPane.add(clientLabel, 0, 0);
    	GridPane.setHalignment(clientLabel, javafx.geometry.HPos.CENTER);
    	//Add textfield to gridPane(col 1, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(clientIDField, 1, 0);
    	GridPane.setHalignment(clientIDField, javafx.geometry.HPos.CENTER);
    	//Add btnListPT to gridPane(col 2, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(btnListPT, 2, 0);
    	GridPane.setHalignment(btnListPT, javafx.geometry.HPos.CENTER);
    	//Adding table to gridPane (col 0, row 1)
    	gridPane.add(tableView, 0, 1, 3, 1);
    	//Adding btn to gridPane (col 0, row 2)
    	gridPane.add(btnBackToMain, 0, 2, 3, 1);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	
    	
    	//Scene
    	Scene listClientScene = new Scene(new StackPane(gridPane), 1280, 720);
    	
    	//Stage
    	Stage listClientStage = new Stage();
    	listClientStage.setTitle("Bookings by Client_ID");
    	listClientStage.setScene(listClientScene);
    	listClientStage.initOwner(mainStage);
    	listClientStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	listClientStage.setX(mainStage.getX()+ 50);
    	listClientStage.setY(mainStage.getY()+ 50);
    	
    	//Adjust size of gridPane to window
    	gridPane.prefWidthProperty().bind(mainStage.widthProperty());
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	listClientStage.close();
        });
    	

    	btnListPT.setOnAction(event -> {

    		try {

    			ArrayList<Booking> bookings;
    			String clientID;
    			
    			clientID = clientIDField.getText();
    			//Input validation should be added here
				bookings =_network.listClient(clientID);
				
				data.removeAll(data);

		    	//Populating the table	    	
		    	if (bookings.size() > 0) {
			    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
			    	{
			    		String[] bookingDetails = b.getBookingDetails();
			    		tableView.getItems().addAll(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));
			    		//data.add(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));

			    	}
			    	tableView.refresh();
			    	clientIDField.clear();
			    }
			    else {
			    	//No bookings found in the DB.
			    	//Adding placeholder instead of table
			    	tableView.setPlaceholder(new Label("No bookings matching this ID in the database."));
			    	tableView.refresh();
			    	clientIDField.clear();
			    }
			} catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}
    	});
    	

    	listClientStage.showAndWait();
	}

	private void listDayUI(Stage mainStage) {
		//Label
    	Label dayLabel = new Label("Enter a day");
    	
    	//Table
    	TableView<Booking> tableView = new TableView<Booking>();
    	tableView.setEditable(true);
    	tableView.setPlaceholder(new Label("Use the textfield and button to search for bookings"));
    	
    	//
    	//Columns
    	//
    	
    	//First column: Booking_ID
    	TableColumn<Booking, String> bookingIDCol = new TableColumn<Booking, String>("Booking_ID");
    	bookingIDCol.setMinWidth(150);
    	bookingIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_bookingID"));
    	
    	//Second column: Client_ID
    	TableColumn<Booking, String> clientIDCol = new TableColumn<Booking, String>("Client_ID");
    	clientIDCol.setMinWidth(150);
    	clientIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_clientID"));
    	
    	//Third column: Trainer_ID
    	TableColumn<Booking, String> trainerIDCol = new TableColumn<Booking, String>("Trainer_ID");
    	trainerIDCol.setMinWidth(150);
    	trainerIDCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_trainerID"));
    	
    	//Fourth column: Booking_Time
    	TableColumn<Booking, String> timeCol = new TableColumn<Booking, String>("Booking_Time");
    	timeCol.setMinWidth(150);
    	//Next line somehow gets the value to be displayed correctly.
    	//Basically gets the object related to the cell (type booking), calls get_time and converts it to a String.
    	timeCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_time().format(new Date())));
    	
    	//Fifth column: Booking_Date
    	TableColumn<Booking, String> dateCol = new TableColumn<Booking, String>("Booking_Date");
    	dateCol.setMinWidth(150);
    	dateCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_date().format(new Date())));
    	
    	//Sixth column: Booking_Duration
    	TableColumn<Booking, String> durationCol = new TableColumn<Booking, String>("Booking_Duration");
    	durationCol.setMinWidth(150);
    	durationCol.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().get_duration().format(new Date())));
    	
    	//Seventh column: Focus
    	TableColumn<Booking, String> focusCol = new TableColumn<Booking, String>("Focus");
    	focusCol.setMinWidth(150);
    	focusCol.setCellValueFactory(new PropertyValueFactory<Booking, String>("_focus"));
    	
    	//Adding all columns to the table
    	tableView.getColumns().addAll(bookingIDCol, clientIDCol, trainerIDCol, timeCol, dateCol, durationCol, focusCol);
    	
    	
    	ObservableList<Booking> data = FXCollections.observableArrayList();
    	tableView.setItems(data);
    	
    	//
    	//Buttons
    	//
    	
    	//Creating a button to send request to server
    	Button btnListPT = new Button("List bookings by day");
    	//Creating a button to go back to the main menu
    	Button btnBackToMain = new Button("Go back to main menu");
    	
    	
    	//
    	//Textfield
    	//
    	DatePicker datePicker = new DatePicker();
    	
    	//
    	//GridPane
    	//
    	
    	//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(75);
        ColumnConstraints column3 = new ColumnConstraints();
        column1.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setMinWidth(720);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
    	row1.setPercentHeight(5);
    	row2.setPercentHeight(85);
    	row3.setPercentHeight(10);
    	gridPane.getRowConstraints().addAll(row1, row2, row3);
    	gridPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    	gridPane.setPadding(new Insets(10, 10, 10, 10));
    	
    	
        //Adding label to gridPane (col 0, row 0, colSpan 1, rowSpan 1) 
    	gridPane.add(dayLabel, 0, 0);
    	GridPane.setHalignment(dayLabel, javafx.geometry.HPos.CENTER);
    	//Add textfield to gridPane(col 1, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(datePicker, 1, 0);
    	GridPane.setHalignment(datePicker, javafx.geometry.HPos.CENTER);
    	//Add btnListPT to gridPane(col 2, row 0, colSpan 1, rowSpan 1)
    	gridPane.add(btnListPT, 2, 0);
    	GridPane.setHalignment(btnListPT, javafx.geometry.HPos.CENTER);
    	//Adding table to gridPane (col 0, row 1)
    	gridPane.add(tableView, 0, 1, 3, 1);
    	//Adding btn to gridPane (col 0, row 2)
    	gridPane.add(btnBackToMain, 0, 2, 3, 1);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	
    	
    	//Scene
    	Scene listDayScene = new Scene(new StackPane(gridPane), 1280, 720);
    	
    	//Stage
    	Stage listDayStage = new Stage();
    	listDayStage.setTitle("Bookings by Day");
    	listDayStage.setScene(listDayScene);
    	listDayStage.initOwner(mainStage);
    	listDayStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	listDayStage.setX(mainStage.getX()+ 50);
    	listDayStage.setY(mainStage.getY()+ 50);
    	
    	//Adjust size of gridPane to window
    	gridPane.prefWidthProperty().bind(mainStage.widthProperty());
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	listDayStage.close();
        });
    	

    	btnListPT.setOnAction(event -> {

    		try {

    			ArrayList<Booking> bookings;
    			
    			//Retriving date as LocalDate, then converting to String
    			LocalDate datePicked;    			
    			String dateString;
    			String format = "yyyy-MM-dd"; 
    			
    			datePicked = datePicker.getValue();
    			dateString = datePicked.format(DateTimeFormatter.ofPattern(format));
    			
    			//Input validation should be added here
				bookings =_network.listDay(dateString);
				
				data.removeAll(data);

		    	//Populating the table	    	
		    	if (bookings.size() > 0) {
			    	for (Booking b : bookings) //Looping through all bookings retrieved and outputting them.
			    	{
			    		String[] bookingDetails = b.getBookingDetails();
			    		tableView.getItems().addAll(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));
			    		//data.add(new Booking(bookingDetails[0], bookingDetails[1], bookingDetails[2], new SimpleDateFormat(bookingDetails[3]), new SimpleDateFormat(bookingDetails[4]), new SimpleDateFormat(bookingDetails[5]), bookingDetails[6] ));

			    	}
			    	tableView.refresh();
			    }
			    else {
			    	//No bookings found in the DB.
			    	//Adding placeholder instead of table
			    	tableView.setPlaceholder(new Label("No bookings matching this ID in the database."));
			    	tableView.refresh();
			    }
			} catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}catch(NullPointerException ex) {
				tableView.setPlaceholder(new Label("Please select a date."));
		    	tableView.refresh();
			}
    	});
    	

    	listDayStage.showAndWait();
	}

	private void updateUI(Stage mainStage) { //NEEDS TO BE FINISHED
		//Creating Labels
		Label labelBooking = new Label("Update a booking");
		Label labelBookingID = new Label("Enter a Booking_ID");
		Label labelClientID = new Label("Enter a Client_ID");
		Label labelTrainerID = new Label("Enter a Trainer_ID");
		Label labelTime = new Label("Enter a time");
		Label labelDate = new Label("Enter a date");
		Label labelDuration = new Label("Enter a duration");
		Label labelFocus = new Label("Enter a Focus");


		//Creating Buttons
		Button btnUpdateBooking = new Button("Submit your booking");
		Button btnBackToMain = new Button("Go back to main menu");
		
		//Creating TextFields
		TextField textFieldBookingID = new TextField();
		TextField textFieldClientID = new TextField();
		TextField textFieldTrainerID = new TextField();
		TextField textFieldFocus = new TextField();

		//Creating DatePickers
		DatePicker timePicker = new DatePicker();
		
		//Creating Combo Boxes for hours
		ObservableList<String> hours = FXCollections.observableArrayList();
		for (Integer i = 0; i < 24; i++) {
			if(i < 10) {
				hours.add("0" + i);
			}else {
				hours.add(i.toString());
			}
		}
		ComboBox<String> comboBoxHoursTime = new ComboBox<String>(hours);
		ComboBox<String> comboBoxHoursDuration = new ComboBox<String>(hours);
		
		ObservableList<String> minutes = FXCollections.observableArrayList();
		for (Integer i = 0; i < 60; i++) {
			if(i < 10) {
				minutes.add("0" + i);
			}else {
				minutes.add(i.toString());
			}
		}
		ComboBox<String> comboBoxMinutesTime = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxMinutesDuration = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxSecondsTime = new ComboBox<String>(minutes);
		ComboBox<String> comboBoxSecondsDuration = new ComboBox<String>(minutes);
		
		
		//Creating Hbox for times
		HBox hBoxTime = new HBox(comboBoxHoursTime, comboBoxMinutesTime, comboBoxSecondsTime);
		HBox hBoxDuration = new HBox(comboBoxHoursDuration, comboBoxMinutesDuration, comboBoxSecondsDuration);
		hBoxTime.setAlignment(Pos.BASELINE_CENTER);
		hBoxDuration.setAlignment(Pos.BASELINE_CENTER);
		
		//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 25, 10, 25));
    	
        //Adding label to gridPane (col 0, row 0) 
    	gridPane.add(labelBooking, 0, 0);
    	GridPane.setHalignment(labelBooking, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelBookingID, 0, 1);
    	GridPane.setHalignment(labelBookingID, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldBookingID, 0, 2);
    	GridPane.setHalignment(textFieldBookingID, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelClientID, 0, 3);
    	GridPane.setHalignment(labelClientID, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldClientID, 0, 4);
    	GridPane.setHalignment(textFieldClientID, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelTrainerID, 0, 5);
    	GridPane.setHalignment(labelTrainerID, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldTrainerID, 0, 6);
    	GridPane.setHalignment(textFieldTrainerID, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelTime, 0, 7);
    	GridPane.setHalignment(labelTime, javafx.geometry.HPos.CENTER);
    	gridPane.add(hBoxTime, 0, 8);
    	GridPane.setHalignment(hBoxTime, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelDate, 0, 9);
    	GridPane.setHalignment(labelDate, javafx.geometry.HPos.CENTER);
    	gridPane.add(timePicker, 0, 10);
    	GridPane.setHalignment(timePicker, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelDuration, 0, 11);
    	GridPane.setHalignment(labelDuration, javafx.geometry.HPos.CENTER);
    	gridPane.add(hBoxDuration, 0, 12);
    	GridPane.setHalignment(hBoxDuration, javafx.geometry.HPos.CENTER);
    	gridPane.add(labelFocus, 0, 13);
    	GridPane.setHalignment(labelFocus, javafx.geometry.HPos.CENTER);
    	gridPane.add(textFieldFocus, 0, 14);
    	GridPane.setHalignment(textFieldFocus, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnUpdateBooking, 0, 15);
    	GridPane.setHalignment(btnUpdateBooking, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnBackToMain, 0, 16);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	
    	//Scene
    	Scene updateScene = new Scene(new StackPane(gridPane), 480, 600);
    	
    	//Stage
    	Stage updateStage = new Stage();
    	updateStage.setTitle("Update bookings");
    	updateStage.setScene(updateScene);
    	updateStage.initOwner(mainStage);
    	updateStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	updateStage.setX(mainStage.getX()+ 50);
    	updateStage.setY(mainStage.getY()+ 50);
    	
    	//Assigning an event to the buttons
    	btnBackToMain.setOnAction(event ->
        {
        	updateStage.close();
        });
    	
    	btnUpdateBooking.setOnAction(event ->
    	{
    		try {
    			//Retrieving date as LocalDate, then converting to String
    			LocalDate datePicked;    			
    			String dateString;
    			String format = "yyyy-MM-dd"; 
    			
    			datePicked = timePicker.getValue();
    			dateString = datePicked.format(DateTimeFormatter.ofPattern(format));
    			
    			//Retrieving time and formatting it to String
    			String time = comboBoxHoursTime.getValue() + ":" + comboBoxMinutesTime.getValue() + ":" + comboBoxSecondsTime.getValue();
    			
    			//Retrieving duration and formatting it to String
    			String duration = comboBoxHoursDuration.getValue() + ":" + comboBoxMinutesDuration.getValue() + ":" + comboBoxSecondsDuration.getValue();
    			        		
        		String newBooking = textFieldBookingID.getText() + "," + textFieldClientID.getText() + "," + textFieldTrainerID.getText() + "," + time + "," + dateString + "," + duration + "," + textFieldFocus.getText();
        		
        		String result = _network.update(newBooking);
				showResultWindow(result, updateStage);
				
			}catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}catch(NullPointerException ex) {
				//If server can't be reached. Show error window.
				Label errorLabel = new Label("Please make sure all fields have an input.");
				Label errorCodeLabel = new Label(ex.toString());
				
				//Creating a grid and setting its design constraints
		    	GridPane gridPaneError = new GridPane();
		    	ColumnConstraints column1Error = new ColumnConstraints();
		    	column1Error.setPercentWidth(100);
		    	gridPaneError.getColumnConstraints().addAll(column1Error);
		    	gridPaneError.setVgap(10);
		    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
		    	row1.setPercentHeight(25);
		    	row2.setPercentHeight(50);
		    	row3.setPercentHeight(25);
		    	gridPaneError.getRowConstraints().addAll(row1, row2, row3);
		    	
		    	//Creating a button to go back to the main menu
		    	Button btnClose = new Button("Close");
		    	
		    	//Adding labels to gridPane (col 0, row 0 and row 1) 
		    	gridPaneError.add(errorLabel, 0, 0);
		    	GridPane.setHalignment(errorLabel, javafx.geometry.HPos.CENTER);
		    	gridPaneError.add(errorCodeLabel, 0, 1);
		    	GridPane.setHalignment(errorCodeLabel, javafx.geometry.HPos.CENTER);
		    	//Adding btn to gridPane (col 0, row 2)
		    	gridPaneError.add(btnClose, 0, 2);
		    	GridPane.setHalignment(btnClose, javafx.geometry.HPos.CENTER);
		    	
		    	//Scene
		    	Scene errorScene = new Scene(new StackPane(gridPaneError), 320, 180);
				
				Stage errorStage = new Stage();
				errorStage.setTitle("Error");
				errorStage.setScene(errorScene);
				errorStage.initOwner(mainStage);
				errorStage.initModality(Modality.APPLICATION_MODAL);
		    	
		    	//Offset position
				errorStage.setX(mainStage.getX()+ 50);
				errorStage.setY(mainStage.getY()+ 50);
		    	
		    	//Assigning an event to the button BackToMain
		    	btnClose.setOnAction(eventClose ->
		        {
		        	errorStage.close();
		        });
				
		    	errorStage.showAndWait();
			}
    	});
    	
    	updateStage.showAndWait();
	}
	
	private void deleteUI(Stage mainStage) {
		//If server can't be reached. Show error window.
		Label deleteLabel = new Label("Enter the ID of the booking you wish to delete.");
		
		//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints(), row4 = new RowConstraints();
    	row1.setPercentHeight(25);
    	row2.setPercentHeight(25);
    	row3.setPercentHeight(25);
    	row3.setPercentHeight(25);
    	gridPane.getRowConstraints().addAll(row1, row2, row3, row4);
    	gridPane.setPadding(new Insets(10, 25, 10, 25));
    	
    	//Creating a button to go back to the main menu
    	Button btnDelete = new Button("Delete");
    	btnDelete.setMinWidth(75);
    	Button btnBackToMain = new Button("Go back to main menu");
    	btnBackToMain.setMinWidth(75);
    	
    	//Creating TextField
		TextField textFieldBookingID = new TextField();
    	
    	//Adding labels to gridPane (col 0, row 0) 
    	gridPane.add(deleteLabel, 0, 0);
    	GridPane.setHalignment(deleteLabel, javafx.geometry.HPos.CENTER);
    	//Adding TextField to gridPane (col 0, row 1)
    	gridPane.add(textFieldBookingID, 0, 1);
    	GridPane.setHalignment(textFieldBookingID, javafx.geometry.HPos.CENTER);
    	//Adding btn to gridPane (col 0, row 2 & 3)
    	gridPane.add(btnDelete, 0, 2);
    	GridPane.setHalignment(btnDelete, javafx.geometry.HPos.CENTER);
    	gridPane.add(btnBackToMain, 0, 3);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	//Scene
    	Scene deleteScene = new Scene(new StackPane(gridPane), 400, 200);
		
		Stage deleteStage = new Stage();
		deleteStage.setTitle("Delete bookings");
		deleteStage.setScene(deleteScene);
		deleteStage.initOwner(mainStage);
    	deleteStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	deleteStage.setX(mainStage.getX()+ 50);
    	deleteStage.setY(mainStage.getY()+ 50);
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	deleteStage.close();
        });
    	
    	btnDelete.setOnAction(event ->{
    		try {
    			//Retrieving date as LocalDate, then converting to String
    			
        		String result = _network.delete(textFieldBookingID.getText());
				showResultWindow(result, deleteStage);
				
			}catch(NoSuchElementException ex) {
				showErrorWindow(ex, mainStage);
			} catch(UnknownHostException ex) {
				showErrorWindow(ex, mainStage);
			} catch(IOException ex) {
				showErrorWindow(ex, mainStage);
			}
    	});
		
    	deleteStage.showAndWait();
	}
	
	//Creates a modal window that display a result message and a button to return to the main menu
	private void showResultWindow(String message, Stage mainStage){
		//If server can't be reached. Show error window.
		Label messageLabel = new Label(message);
		
		
		//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
    	row1.setPercentHeight(25);
    	row2.setPercentHeight(50);
    	row3.setPercentHeight(25);
    	gridPane.getRowConstraints().addAll(row1, row2, row3);
    	
    	//Creating a button to go back to the main menu
    	Button btnBackToMain = new Button("Go back to main menu");
    	
    	//Adding labels to gridPane (col 0, row 0) 
    	gridPane.add(messageLabel, 0, 0);
    	GridPane.setHalignment(messageLabel, javafx.geometry.HPos.CENTER);
    	//Adding btn to gridPane (col 0, row 1)
    	gridPane.add(btnBackToMain, 0, 1);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	//Scene
    	Scene errorScene = new Scene(new StackPane(gridPane), 600, 180);
		
		Stage errorStage = new Stage();
		if (message.equals("Booking succesfully added.") || message.equals("Booking successfully updated.") || message.equals("Your booking has been successfully deleted.")) {
	    	errorStage.setTitle("Success");
		}else {
			errorStage.setTitle("Error");
		}
    	errorStage.setScene(errorScene);
    	errorStage.initOwner(mainStage);
    	errorStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	errorStage.setX(mainStage.getX()+ 50);
    	errorStage.setY(mainStage.getY()+ 50);
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	errorStage.close();
        });
		
    	errorStage.showAndWait();
	}
	
	//Creates a modal window that display an error message and a button to return to the main menu
	private void showErrorWindow(Exception ex, Stage mainStage){
		//If server can't be reached. Show error window.
		Label errorLabel = new Label("An error occured while retrieving data from the server. Server might be offline.");
		Label errorCodeLabel = new Label(ex.toString());
		
		//Creating a grid and setting its design constraints
    	GridPane gridPane = new GridPane();
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.setVgap(10);
    	RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
    	row1.setPercentHeight(25);
    	row2.setPercentHeight(50);
    	row3.setPercentHeight(25);
    	gridPane.getRowConstraints().addAll(row1, row2, row3);
    	
    	//Creating a button to go back to the main menu
    	Button btnBackToMain = new Button("Go back to main menu");
    	
    	//Adding labels to gridPane (col 0, row 0 and row 1) 
    	gridPane.add(errorLabel, 0, 0);
    	GridPane.setHalignment(errorLabel, javafx.geometry.HPos.CENTER);
    	gridPane.add(errorCodeLabel, 0, 1);
    	GridPane.setHalignment(errorCodeLabel, javafx.geometry.HPos.CENTER);
    	//Adding btn to gridPane (col 0, row 2)
    	gridPane.add(btnBackToMain, 0, 2);
    	GridPane.setHalignment(btnBackToMain, javafx.geometry.HPos.CENTER);
    	
    	//Scene
    	Scene errorScene = new Scene(new StackPane(gridPane), 600, 120);
		
		Stage errorStage = new Stage();
    	errorStage.setTitle("Error");
    	errorStage.setScene(errorScene);
    	errorStage.initOwner(mainStage);
    	errorStage.initModality(Modality.APPLICATION_MODAL);
    	
    	//Offset position
    	errorStage.setX(mainStage.getX()+ 50);
    	errorStage.setY(mainStage.getY()+ 50);
    	
    	//Assigning an event to the button BackToMain
    	btnBackToMain.setOnAction(event ->
        {
        	errorStage.close();
        });
		
    	errorStage.showAndWait();
	}
}

