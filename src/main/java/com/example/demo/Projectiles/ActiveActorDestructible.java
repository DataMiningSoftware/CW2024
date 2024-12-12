package com.example.demo.Projectiles;

import com.example.demo.Destructible;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;
    private boolean alive = true; // Track if the object is alive or destroyed


	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean isAlive() {
        return alive; // Return whether the object is alive
	}
	
	public void setAlive(boolean alive) {
        this.alive = alive; // Set the object's alive status
    }
	
}
