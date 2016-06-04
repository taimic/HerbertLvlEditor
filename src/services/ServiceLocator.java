package services;

import java.util.ArrayList;
import java.util.List;

public final class ServiceLocator {
	// Holds all services
	private static List<Service> services = new ArrayList<Service>();
	
	// Updates all services
	public static void update(){
		for (Service service : services) {
			if(service.isActive()) service.update();
		}
	}
	
	// Adds the given service
	public static void addService(Service service){
		if(!services.contains(service)) services.add(service);
	}
	
	// Removes the given service
	public static void removeService(Service service){
		if(services.contains(service)) services.remove(service);		
	}
	
	// Returns the service with the given class
	public static <T extends Service> T getService(Class<T> type){
		for (Service service : services) {
			if(service.getClass().isAssignableFrom(type)) return type.cast(service);
		}
		return null;
	}
	
	public static void clear(){
		for (Service service : services) {
			service.clear();
		}
	}
}
