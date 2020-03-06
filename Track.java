/**
 * @author Rohan Dayal
 * This class represent the Track object which contains a doubly linked-list of train
 * objects to be kept track of.
 * Track has private variables for head Train, tail Train, cursor Train, next
 * Track, previous Track, double utilRate for the utilization rate of the Track
 * compared to length of the day, totalMins for the number of minutes in a day
 * that trains are on this Track. trackNum is an ID to distinguish between
 * Track objects when they are implemented together as a linked-list. numTrains
 * keeps count of the number of trains on the Track throughout the day.
 */
public class Track {
	private Train head = null;
	private Train tail = null;
	private Train cursor = null;
	private Track next;
	private Track prev;
	private double utilRate=0;
	private int totalMins=0;
	private int trackNum=0;
	private int numTrains=0;
	/**
	 * Empty constructor to make a new Track object.
	 */
	public Track() {
		
	}
	/**
	 * Constructor to make new Track object
	 * @param h is Train assigned to be head of train linked-list
	 * @param t is Train assigned to be tail of train linked-list
	 * @param c is Train assigned as cursor of train linked-list
	 * @param n is Track assigned as next Track after current 
	 * @param p is Track assigned as prev Track before current
	 * @param num is int assigned to be trackNum of the Track
	 */
	public Track(Train h, Train t, Train c, Track n, Track p, int num) {
		head = h;
		tail = t;
		cursor = c;
		next = n;
		prev = p;
		trackNum = num;
	}
	/**
	 * Void method to add new Train to the linked-list of trains on this track
	 * @param newTrain is the new train we try to add to the linked-list
	 * Train will not be added if the arrival time entered is in invalid time
	 * format. Will also check that no train is already scheduled for the track
	 * at this time, and that no train has already been entered with the given
	 * trainNumber
	 */
	public void addTrain(Train newTrain) {
		String tempTime = String.valueOf(newTrain.getArrival());
		tempTime = "000" +tempTime;
		tempTime = tempTime.substring(tempTime.length()-2, tempTime.length());
		if(Integer.parseInt(tempTime)>59||Integer.parseInt(tempTime)<0)//checks to ensure 
			System.out.println("Invalid train time entered. Try again.");
		else if(newTrain.getArrival()<0||newTrain.getArrival()>=2400)
			System.out.println("Invalid train time entered. Try again.");
		else if(!validNumber(newTrain))
			System.out.println("Train already entered with given number.");
		else if(!noOverlap(newTrain))//uses total mins calculation. NOT WORKING
			System.out.println("Train not scheduled. Train already scheduled for"
					+ " this time on Track " + trackNum);
		else if(head==null) {//no Trains on this Track yet
			setHead(newTrain);//head = newTrain
			setCursor(newTrain);//cursor =newTrain 
			setTail(newTrain);
			totalMins+=newTrain.getTransf();
			numTrains++;
		}
		else if(head.getNext()==null) {//Means only one element in list so far
			if(head.getArrival()<newTrain.getArrival()) {
				newTrain.setPrev(head);
				head.setNext(newTrain);
				cursor = newTrain;
				totalMins+=newTrain.getTransf();
				setTail(newTrain);
				numTrains++;
			}
			else {
				newTrain.setNext(head);
				head.setPrev(newTrain);
				setTail(head);
				head = newTrain;
				cursor = newTrain;
				totalMins+=newTrain.getTransf();
				numTrains++;
			}
		}
		//don't need to check if cursor==null bc adding by time not next to cursor
		else {//means more than 1 element in list
			Train ndPtr = head;
			boolean entered = false;
			while(ndPtr.getNext()!=null) {
				if(ndPtr.getArrival()<newTrain.getArrival()&&ndPtr.getNext().
						getArrival()>newTrain.getArrival()) {
					newTrain.setPrev(ndPtr);
					newTrain.setNext(ndPtr.getNext());
					ndPtr.setNext(newTrain);
					newTrain.getNext().setPrev(newTrain);
					cursor = newTrain;
					entered=true;
					numTrains++;
					break;
				}
				ndPtr = ndPtr.getNext();
			}
			if(entered==false) {//adding to the end
				if(newTrain.getArrival()>ndPtr.getArrival()) {
					newTrain.setPrev(tail);
					tail.setNext(newTrain);
					tail = newTrain;
					cursor = newTrain;
					numTrains++;
				}
				else {//adding to beginning
					newTrain.setNext(head);
					head.setPrev(newTrain);
					head = newTrain;
					cursor = newTrain;
					numTrains++;
				}
			}
			totalMins+=newTrain.getTransf();
		}
		utilRate=(totalMins)/1440.0;
	}
	/**
	 * noOverlap returns boolean true or false depending on whether newTrain
	 * would be on the track at the same time as an already existing train in
	 * the linked-list.
	 * @param newTrain is Train object we are checking to see if overlaps with
	 * already existing trains
	 * @return boolean true if no Train already in linked-list will be on track
	 * at the same time as newTrain. Otherwise, return false
	 */
	public boolean noOverlap(Train newTrain) {
		if(head==null)
			return true;
		Train nodePtr = head;
		if(nodePtr.getNext()==null) {//checks for when at head
			if(!(newTrain.getArrival()>Integer.parseInt(nodePtr.getDeparture())
					)&&!(Integer.parseInt(newTrain.getDeparture())<nodePtr.
							getArrival()))
				return false;
			else
				return true;
		}
		while(nodePtr!=null) {
			if(nodePtr.getNext()==null) {
				if(!(newTrain.getArrival()>Integer.parseInt(nodePtr.getDeparture())
						)&&!(Integer.parseInt(newTrain.getDeparture())<nodePtr.
								getArrival()))
					return false;
			}
			else
				if(!(newTrain.getArrival()>Integer.parseInt(nodePtr.getDeparture()))
						&&!(Integer.parseInt(newTrain.getDeparture())>nodePtr.getArrival()))
					return false;
			nodePtr = nodePtr.getNext();
		}
		return true;
	}
	/**
	 * Method returns whether the newTrain has a trainNumber that can be used
	 * by checking if any other Trains on the list have the same number.
	 * @param newTrain is the Train object for which we are checking the number
	 * @return boolean true if no other Train in list that has the same train
	 * number. Otherwise, return false 
	 */
	private boolean validNumber(Train newTrain) {
		if(head==null)
			return true;
		Train current = head;
		while(current!=null) {
			if(current.getNumber()==newTrain.getNumber())
				return false;
			current = current.getNext();
		}
		return true;
	}
	/**
	 * Void method that prints the train value at cursor by calling the Train
	 * toString() method and then printing it
	 * @throws NoTrainSelectedException if cursor==null, meaning that no Train
	 * is made to be the cursor
	 */
	public void printSelectedTrain() throws NoTrainSelectedException{
		if(cursor==null) {
			throw new NoTrainSelectedException("No train selected, cannot print.");
		}
		else
			System.out.println(cursor.toString());
	}
	/**
	 * Method that removes the Train object that is selected as cursor.
	 * Method returns a reference to the Train object that is removed, and if
	 * cursor==null, it returns null.
	 * @return a reference to the Train object that is being removed. Also 
	 * conducts corresponding computations to ensure numTrains and totalMins
	 * are adjusted. If no Train is selected for cursor, returns null
	 */
	public Train removeSelectedTrain() {
		if(cursor==null)//case where no selected train
			return null;
		else if(cursor.getNext()==null&&cursor.getPrev()==null) {//case where removed is the only element in linked list
			Train temp = cursor;
			cursor = null;	
			totalMins-=temp.getTransf();
			utilRate = totalMins/1440;
			numTrains--;
			head=null;
			tail=null;
			return temp;//cursor is POINTING to a train. Is not actual train
		}
		else if(cursor.getNext()==null) {//case where removing last item in the list
			cursor.getPrev().setNext(null);
			Train temp = cursor;
			cursor = cursor.getPrev();
			setTail(cursor);
			totalMins-=temp.getTransf();
			utilRate = totalMins/1440;
			tail = cursor;
			numTrains--;
			return temp;
		}
		else if(cursor.getPrev()==null) {//case where first element is being removed
			cursor.getNext().setPrev(null);
			Train temp = cursor;
			cursor = cursor.getNext();
			head = cursor;
			totalMins-=temp.getTransf();
			utilRate = totalMins/1440;
			numTrains--;
			return temp;
		}
		else {//just randomly in the middle
			cursor.getPrev().setNext(cursor.getNext());
			cursor.getNext().setPrev(cursor.getPrev());
			Train temp = cursor;
			cursor = cursor.getNext();
			totalMins-=temp.getTransf();
			utilRate = totalMins/1440;
			numTrains--;
			return temp;
		}
			
	}
	/**
	 * selectNextTrain() is boolean method that will move the cursor
	 * to the next Train in the Track's linked-list of Trains. If no train is
	 * next, will not change the cursor and will return false. Otherwise, will
	 * change the cursor and return true
	 * @return boolean true if there is a next train that the cursor can be 
	 * moved to. Otherwise, returns false if there is no cursor to move to.
	 * @throws NoTrainSelectedException when cursor==null and no train is 
	 * currently selected
	 */
	public boolean selectNextTrain() throws NoTrainSelectedException{
		if(cursor==null)
			throw new NoTrainSelectedException("Cannot select next train with"
					+ " no train selected for current.");
		else if(cursor.getNext()==null)
			return false;
		else {
			cursor = cursor.getNext();
			return true;
		}
	}
	/**
	 * selectPrevTrain() is a boolean method that returns true if the cursor
	 * is successfully switched to the previous train, and false otherwise
	 * @return A boolean true if successfully changed Train to previous one.
	 * Otherwise, return false if no previous Train to change to
	 * @throws NoTrainSelectedException if cursor==null and no Train is 
	 * currently selected.
	 */
	public boolean selectPrevTrain() throws NoTrainSelectedException{
		if(cursor==null)
			throw new NoTrainSelectedException("Cannot select previous train"
					+ " with no train selected as current.");
		else if(cursor.getPrev()==null)
			return false;
		else {
			cursor = cursor.getPrev();
			return true;
		}
	}
	/**
	 * @return a String representation of the Track with all the information
	 * for each Train displayed, as well as the Track utilization rate.
	 */
	public String toString() {
			String table="Track "+trackNum;
			computeUtil();
			table = table+ String.format(" (%.2f %s utilization rate)\n", getUtil(), "%");
			String addendum =String.format("%-10s%-30s%-20s%-20s%-40s\n",
					"Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time");
			table = table+ addendum +"-----------------------------------------"
					+ "--------------------------------------------\n";
			if(head==null)
				return table;
			Train ndPtr = head;
			String select = " ";
			/*if(ndPtr==cursor)
				select = "*";
			addendum = String.format("%-10s%-30d%-20s%-20s%-40s\n",
					select, ndPtr.getNumber(), ndPtr.getDest(), ndPtr.getStrArr
					(), ndPtr.getDeparture());
			table = table+addendum;*/
			while(ndPtr!=null) {
				if(ndPtr==cursor)
					select = "*";
				addendum = String.format("%-10s%-30d%-20s%-20s%-40s\n",
						select, ndPtr.getNumber(), ndPtr.getDest(), ndPtr.getStrArr(), ndPtr.getDeparture());
				table = table+addendum;
				ndPtr = ndPtr.getNext();
				select = " ";
			}
			return table;
		
	}
	/**
	 * Getter method for numTrains on Track linked list
	 * @return numTrains variable from track
	 */
	public int getNumTrains() {
		return numTrains;
	}
	/**
	 * Void method that sets the head train as hd
	 * @param hd is the Train we are setting the head to
	 */
	public void setHead(Train hd) {
		head = hd;
	}
	/**
	 * Getter method for head. Returns head Train reference
	 * @return head Train value for this Track
	 */
	public Train getHead() {
		return head;
	}
	/**
	 * Setter method for tail to Train tl
	 * @param tl is the Train we are setting tail to
	 */
	public void setTail(Train tl) {
		tail = tl;
	}
	/**
	 * Getter method for tail. Returns reference to tail Train
	 * @return tail, which is a reference to the tail Train
	 */
	public Train getTail() {
		return tail;
	}
	/**
	 * Setter method for the cursor. Assigns Train value to cursor
	 * @param cur is the Train we want to assign to cursor.
	 */
	public void setCursor(Train cur) {
		cursor = cur;
	}
	/**
	 * Getter method for the cursor which returns reference to the cursor
	 * Train.
	 * @return cursor, a reference to the Train object that cursor points to.
	 */
	public Train getCursor() {
		return cursor;
	}
	/**
	 * Setter method for next Track following this current Track
	 * @param n is the Track n we are assigning next to.
	 */
	public void setNext(Track n) {
		next = n;
	}
	/**
	 * Getter method for the next Track from this current Track
	 * @return a reference to the next track with variable next
	 */
	public Track getNext() {
		return next;
	}
	/**
	 * Setter method for previous Track
	 * @param p is the Track value we are assigning as the previous Track
	 */
	public void setPrev(Track p) {
		prev = p;
	}
	/**
	 * Getter method for the previous track
	 * @return prev which is a reference to the Track assigned as previous to 
	 * current Track
	 */
	public Track getPrev() {
		return prev;
	}
	/**
	 * Getter method for the trackNum variable that each Track object has
	 * @return trackNum, which is the ID to label each Track
	 */
	public int getTrackNum() {
		return trackNum;
	}
	/**
	 * Setter method for the trackNum. Assigns trackNum the int number
	 * @param number is the number we are assigning to be the trackNum
	 * of this Track
	 */
	public void setTrackNum(int number) {
		trackNum=number;
	}
	/**
	 * Void method that updates the value of utilRate by doing totalMins/1440.0
	 */
	public void computeUtil() {
		utilRate = totalMins/1440.0;
	}
	/**
	 * Getter method for the utilRate variable. Calls computeUtil()
	 * to ensure most recent value for utilRate is returned.
	 * @return utilRate *100 so that rate is returned as a percent
	 */
	public double getUtil() {
		computeUtil();
		return utilRate*100.0;
	}
	
}
