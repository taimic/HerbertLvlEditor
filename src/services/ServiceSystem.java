package services;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ServiceSystem<T> extends Service implements IServiceSystem<T>{
	/**
	 * All added objects.
	 */
	protected Queue<T> objects = new ConcurrentLinkedQueue<>();
	/**
	 * The last added object.
	 */
	protected T lastAddedObject;

	/**
	 * Adds the given object, if not added already.
	 * @param object The object to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> add(T object) {
		if(!this.contains(object)) {
			this.objects.add(object);
			this.lastAddedObject = object;
		}
		return this;
	}

	/**
	 * Removes the given object, if it was added before.
	 * @param object The object to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> remove(T object) {
		if(this.contains(object)) this.objects.remove(object);
		return null;
	}

	/**
	 * @param object The object to add
	 * @return Whether or not the given object was added before.
	 */
	@Override
	public boolean contains(T object) {
		return this.objects.contains(object);
	}
	
	/**
	 * @return The object that was added last.
	 */
	public T getLastAddedObject(){
		return this.lastAddedObject;
	}

	/**
	 * @return The added objects.
	 */
	@Override
	public Queue<T> get() {
		return this.objects;
	}

	/**
	 * Removes all the added objects.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> removeAll() {
		this.objects.clear();
		return this;
	}

	/**
	 * Adds all given objects, if not added already.
	 * @param objects The objects to add
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> addAll(List<T> objects) {
		for(T object : objects){
			this.add(object);
		}
		return this;
	}
	
	/**
	 * Removes all given objects, if added before.
	 * @param objects The objects to remove
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> removeAll(List<T> objects) {
		objects.removeAll(objects);
		return this;
	}

	/**
	 * Disposes the {@see IServiceSystem}.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	@Override
	public IServiceSystem<T> dispose() {
		this.removeAll();
		return this;
	}
	
	/**
	 * @return The current size of the list containing all added objects.
	 */
	@Override
	public int size(){
		return this.objects.size();
	}
	
	/*******************
	 * Service Methods *
	 ******************/
	
	/**
	 * Disposes the {@see IServiceSystem}.
	 */
	@Override
	public Service clear(){
		this.dispose();
		return this;
	}
}
