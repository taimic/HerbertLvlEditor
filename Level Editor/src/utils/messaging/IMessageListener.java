package utils.messaging;


/**
 * This interface provides the methods that have to be implemented for the {@see Messenger}.
 */
public interface IMessageListener {
	/**
	 * This method is called when a message was sent from the {@see Messenger}.
	 * @param message The message defined in {@see Message}
	 * @param data The optional data to sent to the object
	 */
	void onMessage(Message message, Object... data);
}
