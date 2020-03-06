/**
 * @author Rohan Dayal
 * This class represents the Train object. Each Train object has private
 * variables for next Train as next, previous Train as prev, train number as
 * trainNumber, destination, arrivalTime as an int, transferTime as an int in 
 * minutes. Additional variables also created for convenience as departureTime,
 * and arrival time as a String represented by strTime
 *
 */
public class Train {
	private Train next=null;
	private Train prev=null;
	private int trainNumber;
	private String destination;
	private int arrivalTime;
	private int transferTime;
	private String strTime;
	private String departureTime;
	/**
	 * Empty constructor for creating a new Train object
	 */
	public Train() {
		
	}
	/**
	 * Additional Constructor for creating a new Train object. Not required
	 * but just for convenience
	 * @param nextTrain is Train object assigned for next
	 * @param prevTrain is Train object assigned for prev
	 * @param stime is String representation of arrival time. Parsed to Integer
	 * in constructor to get assignment for arrivalTime 
	 * @param dest is String for the destination of Train
	 * @param number is int which trainNumber is assigned to
	 * @param transfer is the number of minutes the train spends at station
	 * and is represented by transferTime.
	 */
	public Train(Train nextTrain, Train prevTrain, String stime, String dest,
			int number, int transfer) {
		next = nextTrain;
		prev = prevTrain;
		strTime=stime;
		trainNumber = number;
		destination = dest;
		arrivalTime = Integer.parseInt(stime);
		transferTime = transfer;
		
	}
	/**
	 * toString() method which includes identifying Train information such as
	 * trainNumber, destination, arrivalTime (strTime if in String form), and
	 * departureTime, which is called with getDeparture() method
	 * @return a String representation of the Train object toString() is called
	 * on.
	 */
	public String toString() {
		
		String s = String.format("Train Number: %d \nTrain Destination: %s\n"
				+ "Arrival Time: %s\nDeparture Time: %s", trainNumber, 
				destination, strTime, getDeparture());
		return s;
	}
	/**
	 * Method to get the total number of minutes in the day by the point where
	 * train is leaving the station. Has to parse arrivalTime to a String
	 * so that we can determine number of hours and total number of minutes.
	 * Finally, adds the transferTime. Final return value will be the departure
	 * time of the train only in minutes (ex: 1000am is 600 minutes)
	 * @return
	 */
	public int totalMins() {
		String t = String.valueOf(arrivalTime);
		int mins = 0;
		if(t.length()==3) {
			mins+=(Integer.parseInt(t.substring(0,1))*60) + Integer.parseInt(t.substring(1));
		}
		else if(t.length()==4) {
			mins+=(Integer.parseInt(t.substring(0,2))*60) + Integer.parseInt(t.substring(2));
		}
		else  {
			mins+=Integer.parseInt(t.substring(0));
		}
		
		return mins+transferTime;
	}
	/**
	 * Setter method for the next train following this current calling 
	 * Train object.
	 * @param nextTrain is the Train object we are assigning to next
	 */
	public void setNext(Train nextTrain) {
		this.next=nextTrain;
	}
	/**
	 * Setter method for the previous train prior to this current calling
	 * Train object
	 * @param prevTrain is the Train object we are assigning to prev
	 */
	public void setPrev(Train prevTrain) {
		this.prev = prevTrain;
	}
	/**
	 * Getter method for the next Train after this current calling Train
	 * @return a reference to the next Train following current calling Train. 
	 */
	public Train getNext() {
		return this.next;
	}
	/**
	 * Getter method for the previous Train prior to this calling Train.
	 * @return a reference to the previous Train prior to this current calling
	 * Train
	 */
	public Train getPrev() {
		return this.prev;
	}
	/**
	 * equals method to check if one Train is equal to the other. Specification
	 * required for checking only if the train numbers are equal.
	 * @return boolean true if and only if obj is an instanceof Train and obj
	 * has the same trainNumber as this current calling Train object. Otherwise
	 * will return false.
	 * @param Object o is what we are comparing to this current Train
	 */
	public boolean equals(Object o) {
		if((o instanceof Train)==false)
			return false;
		
		else {
			Train obj = (Train)o;
			if(obj.getNumber()==this.getNumber())
				return true;
			else
				return false;
		}
	}
	/**
	 * Setter method for assigning value to trainNumber.
	 * @param num is the number we are assigning to trainNumber for calling 
	 * Train object
	 */
	public void setNumber(int num) {
		this.trainNumber = num;
	}
	/**
	 * Getter method for the trainNumber of this calling Train object
	 * @return trainNumber which is an int that gives the train number of the 
	 * train
	 */
	public int getNumber() {
		return this.trainNumber;
	}
	/**
	 * Setter method for the destination of this current Train object.
	 * @param dest is a String which we are making destination equal to
	 */
	public void setDest(String dest) {
		this.destination = dest;
	}
	/**
	 * Getter method for destination of the calling Train object
	 * @return a String destination which is the destination of the current
	 * calling Train
	 */
	public String getDest() {
		return this.destination;
	}
	/**
	 * Setter method for arrivalTime of the Train. After assigning int value
	 * to arrivalTime, calls setArrString() to assign value to strTime.
	 * @param arr is the integer which is being arrivalTime is being assigned 
	 * to
	 */
	public void setArrival(int arr) {
		this.arrivalTime = arr;
		setArrString();
	}
	/**
	 * Getter method for the arrivalTime variable of the calling Train object
	 * @return arrivalTime variable of the calling Train object
	 */
	public int getArrival() {
		return this.arrivalTime;
	}
	/**
	 * Setter method for the transferTime variable of the calling Train object
	 * @param tran is an integer that is being assigned to transferTime 
	 * of calling Train object
	 */
	public void setTransf(int tran) {
		this.transferTime = tran;
	}
	/**
	 * Getter method for the transferTime variable of the calling Train object
	 * @return transferTime value of the calling Train object.
	 */
	public int getTransf() {
		return this.transferTime;
	}
	/**
	 * Getter method for the strTime which is the arrival time of the calling
	 * Train object as a string.
	 * @return strTime which is a String representation of the arrival time of 
	 * the train
	 */
	public String getStrArr() {
		return strTime;
	}
	/**
	 * Setter method for the strTime which utilizes the totalMins() to easily
	 * convert
	 */
	public void setArrString() {
		String part1="";
		String part2="";
		int tempTime = totalMins() - transferTime;
		if(tempTime/60>=10)
			part1 = String.valueOf(tempTime/60);
		else if(tempTime/60<10)
			part1 = "0"+String.valueOf(tempTime/60);
		if(tempTime%60>=10)
			part2=String.valueOf(tempTime%60);
		else if(tempTime%60<10)
			part2 = "0"+String.valueOf(tempTime%60);
		strTime = part1+part2;
	}
	/**
	 * Setter method for departure time of the train. Utilizes totalMins()
	 * in order to get time of day when Train departs in minutes and then
	 * converts to standard time formatting as a String.
	 */
	public void setDeparture() {
		String part1="";
		String part2="";
		if(totalMins()/60>=10)
			part1 = String.valueOf(totalMins()/60);
		else if(totalMins()/60<10)
			part1 = "0"+String.valueOf(totalMins()/60);
		if(totalMins()%60>=10)
			part2=String.valueOf(totalMins()%60);
		else if(totalMins()%60<10)
			part2 = "0"+String.valueOf(totalMins()%60);
		
		departureTime = part1+ part2;
	}
	/**
	 * Getter method for the departureTime variable of the calling Train object
	 * @return String departureTime which is String representation of time when
	 * train departs.
	 */
	public String getDeparture() {
		setDeparture();
		return departureTime;
	}
	
}
