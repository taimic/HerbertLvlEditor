package services;

public interface IService {	
	// Activates the service
	public Service activate();
	
	// Deactivates the service
	public Service deactivate();
	
	// Returns whether or not the service is active
	public boolean isActive();
}
