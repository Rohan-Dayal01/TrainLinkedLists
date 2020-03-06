/**
 * @author Rohan Dayal
 */
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class defines a station object which maintains a linked-list of Track
 * objects. It has private variables for head of the linked-list, tail of the
 * linked-list, and the cursor of the linked-list. It also has a private 
 * variable numtrax to keep count of the number of tracks in the linked list
 */

public class Station {
	private Track head = null;
	private Track tail = null;
	private Track cursor = null;
	private int numtrax= 0;

	/**
	 * This is the constructor for the Station class used to make a new
	 * Station object.
	 */
	public Station() {
		
	}
	/**
	 * This is a constructor for the Station class used to make a new Station
	 * object with parameters instantiated.
	 * @param h is a Track object which is assigned to the Station's head
	 * @param t is a Track object which is assigned to the Station's tail
	 * @param c is a Track object which is assigned to the Station's cursor
	 */
	public Station(Track h, Track t, Track c) {
		head = h;
		tail = t;
		cursor = c;
	}
	/**
	 * Void method to add a new Track to the Station object
	 * @param newTrack is the Track that is attempted to be added. It will not
	 * be added if a track already exists of the same number
	 * @throws TrackAlreadyExistsException when a boolean method isValidTrack
	 * returns false. isValidTrack will cycle's through the linked list of
	 * Track objects to check that no Track has the same number as newTrack
	 */
	public void addTrack(Track newTrack) throws TrackAlreadyExistsException{
		if(isValidTrack(newTrack)==false) {
			throw new TrackAlreadyExistsException("Track not added as a track "
					+ newTrack.getTrackNum() +" already exists");
		}
		else if(head==null) {
			setHead(newTrack);
			setCursor(newTrack);
			setTail(newTrack);
			numtrax++;
		}
		else if(head.getNext()==null) {//only one track in list so far
			if(head.getTrackNum()>newTrack.getTrackNum()) {
				newTrack.setNext(head);
				head.setPrev(newTrack);
				setTail(head);
				head = newTrack;
				setCursor(newTrack);
				numtrax++;
			}
			else if(head.getTrackNum()<newTrack.getTrackNum()) {
				setTail(newTrack);
				newTrack.setPrev(head);
				head.setNext(newTrack);
				setCursor(newTrack);
				numtrax++;
			}
		}
		else {
			Track ndPtr = head;
			boolean added = false;
			while(ndPtr.getNext()!=null) {
				if(ndPtr.getTrackNum()<newTrack.getTrackNum()&&ndPtr.
						getNext().getTrackNum()>newTrack.getTrackNum()) {
					newTrack.setNext(ndPtr.getNext());
					newTrack.setPrev(ndPtr);
					ndPtr.setNext(newTrack);
					newTrack.getNext().setPrev(newTrack);
					added = true;
					numtrax++;
				}
				ndPtr = ndPtr.getNext();
			}
			if(added==false) {
				newTrack.setPrev(tail);
				tail.setNext(newTrack);
				tail = newTrack;
				numtrax++;
			}
			setCursor(newTrack);
		}
		
	
	}
	/**
	 * isValidTrack is the boolean method which checks to see if a newTrack 
	 * object can be added to the Station. Goes through to check if any track
	 * already exists with the same trackNum. If already exists, returns false.
	 * Else, returns true
	 * @param newTrack is the Track for which we are checking the number as 
	 * being valid or not.
	 * @return boolean true if no Track already exists with same number.
	 * Otherwise, returns boolean false.
	 */
	public boolean isValidTrack(Track newTrack) {
		if(head==null)
			return true;
		Track nodePtr = head;
		while(nodePtr!=null) {
			if(nodePtr.getTrackNum()==newTrack.getTrackNum())
				return false;
			nodePtr = nodePtr.getNext();
		}
		return true;
	}
	/**
	 * Method to remove the Track object that is selected as current from the 
	 * linked-list. 
	 * @return Method will return a reference to the selected Track object that
	 * was removed. If no Track object is selected, method will return null.
	 */
	public Track removeSelectedTrack() {
		if(cursor==null)
			return null;
		else if(cursor.getNext()==null&&cursor.getPrev()==null) {
			Track temp = cursor;
			cursor = null;
			numtrax--;
			return temp;
		}
		else if(cursor.getNext()==null) {
			Track temp = cursor;
			cursor.getPrev().setNext(null);
			cursor = cursor.getPrev();
			tail = cursor;
			numtrax--;
			return temp;
		}
		else if(cursor.getPrev()==null) {
			Track temp = cursor;
			cursor.getNext().setPrev(null);
			cursor = cursor.getNext();
			head = cursor;
			numtrax--;
			return temp;
		}
		else {
			Track temp = cursor;
			cursor.getNext().setPrev(cursor.getPrev());
			cursor.getPrev().setNext(cursor.getNext());
			cursor = cursor.getNext();
			numtrax--;
			return temp;
		}
	}
	/**
	 * Method prints the Track object assigned to cursor variable by calling
	 * Track method toString(). If cursor==null, will print an error message 
	 * instead of calling toString. 
	 */
	public void printSelectedTrack() {
		if(cursor==null) {
			System.out.println("Error, no track selected. Please try again.");
		}
		else {
			System.out.println(cursor.toString());
		}
	}
	/**
	 * Method prints all the Track objects on the current station by cycling
	 * through the linked-list and calling toString() method on each Track.
	 * Constructs a String of all the tracks and then prints that string.
	 */
	public void printAllTracks() {
		String all = "";
		Track ndPtr = head;
		while(ndPtr!=null) {
			all = all+ndPtr.toString();
			ndPtr = ndPtr.getNext();
		}
		System.out.println(all);
	}
	/**
	 * Method allows user to switch Track objects in the linked-list of tracks.
	 * If such a track as the one the user seeks to switch to does not exist 
	 * (i.e. no track with trackNum trackToSelect), method returns false.
	 * If track successfully found, will complete the switch and then return 
	 * true.
	 * @param trackToSelect is an integer value that specifies the trackNum for
	 * the Track object that the user would like to switch to. 
	 * @return boolean true if trackToSelect == trackNum of one of the
	 * Track objects in the linked-list. Return false if trackToSelect!=
	 * trackNum of any of the Track objects
	 */
	public boolean selectTrack(int trackToSelect) {
		Track nodePtr = head;
		while(nodePtr!=null) {
			if(nodePtr.getTrackNum()==trackToSelect) {
				cursor = nodePtr;
				return true;
			}
			nodePtr = nodePtr.getNext();
		}
		return false;
	}
	/**
	 * This method constructs a String representation of the entire Station.
	 * It displays the number of Trains on each Track and the utilization rate
	 * of each track. This time complexity of this method is proportional to 
	 * the number of Tracks, and not dependent on the number of trains, as 
	 * each Track object has a counter for the total number of minutes, which 
	 * is used to compute utilization rate in linear time.
	 * @return a String representation of the Station as str.
	 */
	public String toString() {
		Track ndPtr = head;
		String str="Station ("+numtrax+" track(s))\n";
		while(ndPtr!=null) {
			String addendum = "Track " + ndPtr.getTrackNum() + ": "
		+ ndPtr.getNumTrains() +" trains arriving (" + String.format("%.2f", ndPtr.getUtil())
		+"% utilization rate)\n";
			str = str+addendum;
			ndPtr = ndPtr.getNext();
		}
		return str;
	}
	/**
	 * Setter method for the head Track of this station
	 * @param h is the Track user is setting to be head of the Station linked-
	 * list
	 */
	public void setHead(Track h) {
		head = h;
	}
	/**
	 * Getter method for the head Track of the Station
	 * @return head which is a reference to the Track object that is assigned
	 * to be he head of the Station linked-list.
	 */
	public Track getHead() {
		return head;
	}
	/**
	 * Setter method for the tail Track of this station
	 * @param t is the Track user is setting to be the tail of the Station 
	 * linked-list
	 */
	public void setTail(Track t) {
		tail = t;
	}
	/**
	 * Getter method for the tail Track of the station
	 * @return tail which is reference to the Track object assigned to be 
	 * head of the Station linked-list
	 */
	public Track getTail() {
		return tail;
	}
	/**
	 * Setter method for the cursor Track of this station
	 * @param c is the Track user is setting to be the cursor of the Station
	 * linked-list
	 */
	public void setCursor(Track c) {
		cursor = c;
	}
	/**
	 * Getter method for the cursor Track of the station
	 * @return cursor which is a reference to the Track object assigned 
	 * to be the cursor of the Station linked-list.
	 */
	public Track getCursor() {
		return cursor;
	}
	/**
	 * Main method to run the simulation of Station linked list with the Track
	 * linked-list and the Train linked-list
	 * @param args default for main method construction
	 * @exception NoTrainSelectedException is handled when no Train is assigned
	 * to be the cursor in the selected Track object.
	 * @exception TrackAlreadyExistsException is handled when TA is selected
	 * and the number entered for the new track is already assigned as trackNum
	 * for one of the existing tracks
	 * @exception InputMismatchException for when user enters incorrect value
	 * type for one of the Scanner methods to read in input.
	 */
	public static void main(String[]args) {
		Scanner rod = new Scanner(System.in);
		String entd = "";
		Station theStation = new Station();
		while(!entd.equals("Q")) {
			System.out.println("A. Add new train");
			System.out.println("N. Select next train");
			System.out.println("V. Select previous train");
			System.out.println("R. Remove selected train");
			System.out.println("P. Print selected train");
			System.out.println("TA. Add track");
			System.out.println("TR. Remove selected track");
			System.out.println("TS. Switch selected track");
			System.out.println("TPS. Print selected track");
			System.out.println("TPA. Print all tracks");
			System.out.println("SI. Print Station Information");
			System.out.println("Q. Quit");
			System.out.print("Select one of the above choices.");
			try {
			entd = rod.next();
			entd = entd.toUpperCase();
			if(entd.equals("A")) {
				if(theStation.cursor==null)
					System.out.println("Train not added: there is no track "
							+ "to add this train to!");
				else {
					System.out.println("Enter train number.");
					int num = rod.nextInt();
					System.out.println("Enter train destination.");
					rod.nextLine();
					String dest = rod.nextLine();
					System.out.println("Enter train arrival time.");
					int arrival = rod.nextInt();
					System.out.println("Enter train transfer time (in minutes).");
					int tTime = rod.nextInt();
					Train novel = new Train();
					novel.setNumber(num);
					novel.setDest(dest);
					novel.setArrival(arrival);
					novel.setTransf(tTime);
					theStation.getCursor().addTrain(novel);
					if(theStation.getCursor().getCursor().equals(novel))
						System.out.println("Train " + novel.getNumber() +" to " +novel.getDest()+" has been successfully added to Track " + theStation.getCursor().getTrackNum());
				}
			}
			else if(entd.equals("N")) {
				if(theStation.cursor==null)
					System.out.println("Error: no track to switch trains on");
				else {
					try {
						boolean toTheNext = theStation.getCursor().selectNextTrain();
						if(toTheNext) {
							System.out.println("Train successfully switched to "
									+ "train number " + theStation.getCursor()
									.getCursor().getNumber());}
						else
							System.out.println("No later train. Cannot switch");
					} catch (NoTrainSelectedException e) {
						System.out.println(e.getMessage());
						
					}
				}
			}
			else if(entd.equals("V")) {
				if(theStation.cursor==null)
					System.out.println("Error: no track to switch trains on");
				else {
					try {
						boolean backItUp = theStation.getCursor().selectPrevTrain();
						if(backItUp) {
							System.out.println("Train successfully switched to "
									+ "train number " + theStation.getCursor()
									.getCursor().getNumber());}	
						else 
							System.out.println("No earlier train. Cannot switch.");
							
					}
					catch(NoTrainSelectedException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			else if(entd.equals("R")) {
				if(theStation.cursor==null)
					System.out.println("Error: no track to remove train from");
				else {
					Train x = theStation.getCursor().removeSelectedTrain();
					if(x!=null) {
						System.out.println("Train " + x.getNumber() +" successfully removed from track " + theStation.getCursor().getTrackNum());
					}
					else
						System.out.println("No train selected to be removed.");
				}
			}
			else if(entd.equals("P")) {
				if(theStation.cursor==null) {
					System.out.println("Error: no track to print train from");
				}
				else {
					try {
						theStation.getCursor().printSelectedTrain();
					} catch (NoTrainSelectedException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			else if(entd.equals("TA")) {
				System.out.println("Enter track number");
				int trackNum = rod.nextInt();
				try {
					Track newTrack = new Track();
					newTrack.setTrackNum(trackNum);
					theStation.addTrack(newTrack);
					System.out.println("Track " + trackNum +" successfully added.");
				}
				catch(TrackAlreadyExistsException e) {
					System.out.println(e.getMessage());
				}
			}
			else if(entd.equals("TR")) {
				if(theStation.cursor==null)
					System.out.println("No track to remove.");
				else {
					System.out.println("Closed track " + theStation.removeSelectedTrack().getTrackNum());
				}
			}
			else if(entd.equals("TS")) {
				System.out.println("Enter the track number.");
				int newNum = rod.nextInt();
				boolean switched = theStation.selectTrack(newNum);
				if(switched) {
					System.out.println("Successfully switched to track "+newNum+".");
				}
				else if(switched==false)
					System.out.println("Could not switch tracks. Track "+newNum+" does not exist.");
			}
			else if(entd.equals("TPS")) {
				theStation.printSelectedTrack();
			}
			else if(entd.equals("TPA")) {
				if(theStation.getHead()==null) {
					System.out.println("Cannot print all tracks, as there are no tracks to print!");
				}
				else {
					theStation.printAllTracks();
				}
			}
			else if(entd.equals("SI")) {
				System.out.println(theStation.toString());
			}
			else if(entd.equals("Q")) {
				System.out.println("Program terminating successfully.");
				break;
			}
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter an a correct input type.");
				continue;
			}
		System.out.println();
		}
	}
}
