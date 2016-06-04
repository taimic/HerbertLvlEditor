package services.systems;

import java.util.ArrayList;
import java.util.List;

import services.ServiceSystem;
import entity.Entity;

public class EntitySystem extends ServiceSystem<Entity>{
	/**
	 * Activates the given entity.
	 * @param entity The entity to activate
	 * @return The {@see IServiceSystem} for chaining.
	 */
	public EntitySystem activateEntity(Entity entity){
		entity.activate();
		return this;
	}
	
	/**
	 * Deactivates the entity.
	 * @param entity The entity to deactivate
	 * @return The {@see IServiceSystem} for chaining.
	 */
	public EntitySystem deactivateEntity(Entity entity){
		entity.deactivate();
		return this;
	}
	
	/**
	 * Activates all entities.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	public EntitySystem activateEntities(){
		for (Entity entity : this.objects) {
			activateEntity(entity);
		}
		return this;
	}

	/**
	 * Deactivates all entities.
	 * @return The {@see IServiceSystem} for chaining.
	 */
	public EntitySystem deactivateEntities(){
		for (Entity entity : this.objects) {
			deactivateEntity(entity);
		}
		return this;
	}
	
	/**
	 * @param state The state to look for, i.e. active or inactive
	 * @return The entities with the given state.
	 */
	public List<Entity> getEntitiesWithState(boolean state){
		List<Entity> returnValue = new ArrayList<Entity>();
		
		for (Entity entity : this.objects) {
			if(entity.isActive() == state) returnValue.add(entity);
		}
		
		return returnValue;
	}
	
	/********************
	 * Service Methods	*
	 ********************/
	
	/**
	 * Updates all active entities.
	 */
	@Override
	public void update() {
		for (Entity entity : this.objects) {
			if(entity.isActive()) entity.update();
		}
	}
}
