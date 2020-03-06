/**
 * @author Rohan Dayal
 * Class defines the NoTrainSelectedException and extends Exception class.
 * Should be thrown when trying to perform operation on cursor in Track
 * class, but no Train assigned to cursor
 *
 */
public class NoTrainSelectedException extends Exception{
	/**
	 * Constructor for NoTrainSelectedException object.
	 * @param message is the message to be printed when the exception is caught
	 * and thrown by the program
	 */
	public NoTrainSelectedException(String message) {
		super(message);
	}
}
