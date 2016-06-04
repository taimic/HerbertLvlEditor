package services;

public abstract class Service implements IService{
	// Holds whether or not the service is active
	private boolean active;

	// Updates the service
	public abstract void update();

	// Activates the service
	@Override
	public Service activate() {
		active = true;
		return this;
	}

	// Deactivates the service
	@Override
	public Service deactivate() {
		active = false;
		return this;
	}
	
	// Returns whether or not the service is active
	@Override
	public boolean isActive(){
		return this.active;
	}

	// Clears the service from references
	public Service clear(){
		return this;
	}
}
