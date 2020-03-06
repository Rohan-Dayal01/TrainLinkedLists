/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework2;


/**
 * Class defines the TrackAlreadyExistsException and extends Exception.
 * Should be thrown when trying to add a new Track to the Station's linked-list
 * but a Track already exists of the same number.
 *
 */
public class TrackAlreadyExistsException extends Exception{
	/**
	 * Constructor for the TrackAlreadyExistsException object
	 * @param message is the message to be printed when a 
	 * TrackAlreadyExistsException object is caught and handled.
	 */
	public TrackAlreadyExistsException(String message) {
		super(message);
	}
}
