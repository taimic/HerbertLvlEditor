package utils.messaging;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class provides the required implementation for sending messages from one class to another.
 */
public class Messenger {	
	/**
	 * All the transitions, i.e. all messages with all objects to send the message to.
	 */
	private static Map<Message, Queue<IMessageListener>> transitions = new HashMap<>();
	
	/**
	 * The {@see Queue} containing all the messages that still need to be sent.
	 */
	private static final Map<Message, Object[]> queue = Collections.synchronizedMap(new LinkedHashMap<Message, Object[]>());
	
	/**
	 * Holds whether or not a message can be sent.
	 */
	private static boolean canSend = true;
	
	/**
	 * Sends the given message to all objects that have registered before hand.
	 * @param message The message to send
	 * @param data The optional data to send to the object
	 */
	public static void sendMessage(Message message, Object... data){
		if(transitions.containsKey(message)) fire(message, data);
	}

	/**
	 * Fires the message by calling the corresponding method of the {@see IMessageListener}.
	 * @param message The message to fire
	 * @param data The optional data to send to the object
	 */
	@SafeVarargs
	private static void fire(Message message, Object... data){
		// Add message to queue, if another message is current being processed
		if(!canSend) {
			queue.put(message, data);
			return;
		}
		canSend = false;
		
		// Get all listeners of the current message
		Queue<IMessageListener> listeners = transitions.get(message);
		
		// Notify listeners
		for(IMessageListener listener : listeners){
			listener.onMessage(message, data);
		}

		// Allow sending more messages
		canSend = true;
	}
	
	/**
	 * Registers a new listener for the given message.
	 * @param listener The listener to register
	 * @param messages The messages to register for
	 */
	public static void register(IMessageListener listener, Message... messages){
		for(Message message : messages){
			if(!transitions.containsKey(message)) transitions.put(message, new ConcurrentLinkedQueue<IMessageListener>());
			transitions.get(message).add(listener);			
		}
	}
	
	/**
	 * Unregisters the given listener from the given message.
	 * @param listener The listener to unregister
	 * @param messages The messages to unregister from
	 */
	public static void unregister(IMessageListener listener, Message... messages){
		for(Message message : messages){
			if(!transitions.containsKey(message)) return;
			transitions.get(message).remove(listener);		
		}
	}
	
	/**
	 * @param message1 The first message
	 * @param message2 The second message
	 * @return Whether or not the messages are equal.
	 */
	public static boolean is(Message message1, Message message2){
		return message1.name().equals(message2.name());
	}
	
	/**
	 * Updates the {@see Messenger} by sending all messages in the queue.
	 */
	public static void update(){
		if(queue.size() > 0){
			for(Entry<Message, Object[]> entry : queue.entrySet()){
				if(canSend) {
					fire(entry.getKey(), entry.getValue());
					queue.remove(entry.getKey());
				}
			}
		}
	}
	
	/**
	 * Converts the given message to the corresponding state. This only works, if the states are named exactly the same way as the messages.
	 * @param message The message to get the state from
	 * @return The {@see IState} that was found.
	 */
	public static <T extends Enum<T>> T getStateFromMessage(Class<T> enumClass, Message message){
		return Enum.valueOf(enumClass, message.getValue());
	}
}
