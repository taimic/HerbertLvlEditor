package services;

import java.util.List;
import java.util.Queue;

public interface IServiceSystem<T> {
	/**
	 * Adds the given object, if not added already.
	 * @param object The object to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> add(T object);
	
	/**
	 * Removes the given object, if it was added before.
	 * @param object The object to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> remove(T object);
	
	/**
	 * @param object The object to add
	 * @return Whether or not the given object was added before.
	 */
	boolean contains(T object);
	
	/**
	 * @return The added objects.
	 */
	Queue<T> get();
	
	/**
	 * Removes all the added objects.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> removeAll();

	/**
	 * Adds all given objects, if not added already.
	 * @param objects The objects to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> addAll(List<T> objects);
	
	/**
	 * Removes all given objects, if added before.
	 * @param objects The objects to remove
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> removeAll(List<T> objects);
	
	/**
	 * Disposes the {@see IServiceSystem}.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	IServiceSystem<T> dispose();
	
	/**
	 * @return The current size of the list containing all added objects.
	 */
	int size();
}
