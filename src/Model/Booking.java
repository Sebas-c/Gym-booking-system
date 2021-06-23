package Model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
	private String _bookingID;
	private String _clientID;
	private String _trainerID;
	private SimpleDateFormat _time = new SimpleDateFormat ("hh:mm:ss");
	private SimpleDateFormat _date = new SimpleDateFormat ("yyyy.MM.dd");
	private SimpleDateFormat _duration = new SimpleDateFormat ("hh:mm:ss");
	private String _focus;
	
	public Booking(String _bookingID, String _clientID, String _trainerId, SimpleDateFormat _time, SimpleDateFormat _date, SimpleDateFormat _duration, String _focus) {
		super();
		this._bookingID = _bookingID;
		this._clientID = _clientID;
		this._trainerID = _trainerId;
		this._time = _time;
		this._date = _date;
		this._duration = _duration;
		this._focus = _focus;
	}
	
	public String[] getBookingDetails() {
		String[] allDetails = {_bookingID, _clientID, _trainerID, _time.format(new Date()), _date.format(new Date()), _duration.format(new Date()), _focus};
		return allDetails;
	}
	
	public String get_bookingID() {
		return _bookingID;
	}

	public String get_clientID() {
		return _clientID;
	}

	public String get_trainerID() {
		return _trainerID;
	}

	public SimpleDateFormat get_time() {
		return _time;
	}

	public SimpleDateFormat get_date() {
		return _date;
	}

	public SimpleDateFormat get_duration() {
		return _duration;
	}

	public String get_focus() {
		return _focus;
	}
}

