package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Force {

	Vector2 initVelocity = new Vector2();
	Vector2 maxVelocity = new Vector2();
	Vector2 acceleration = new Vector2();

	public Force() {
		setForce(0, 0, 0 ,0);
	}

	public Force(float maxVelocityX, float maxVelocityY) {
		setForce(maxVelocityX, maxVelocityY, maxVelocityX, maxVelocityY);
	}
	
	public Force(float maxVelocityX, float maxVelocityY, float acclX, float acclY) {
		setForce(maxVelocityX, maxVelocityY, acclX, acclY);
	}
	
	public void setForce(float maxVelocityX, float maxVelocityY, float acclX, float acclY) {
		this.maxVelocity.x = maxVelocityX;
		this.maxVelocity.y = maxVelocityY;
		this.acceleration.x = acclX;
		this.acceleration.y = acclY;
	}

	public void setMaxVelocity(float maxVelocityX, float maxVelocityY) {
		this.maxVelocity.x = maxVelocityX;
		this.maxVelocity.y = maxVelocityY;		
	}

	public void addToMaxVelocityX(float maxVelocityX) {
		this.maxVelocity.add(maxVelocityX, 0);
	}

	public void addToMaxVelocityY(float maxVelocityY) {
		this.maxVelocity.add(0, maxVelocityY);
	}

	public float getMaxVelocityX() {
		return maxVelocity.x;
	}

	public void setMaxVelocityX(float maxVelocityX) {
		this.maxVelocity.x = maxVelocityX;
	}

	public float getMaxVelocityY() {
		return maxVelocity.y;
	}

	public void setMaxVelocityY(float maxVelocityY) {
		this.maxVelocity.y = maxVelocityY;
	}
	
	public void setAcceleration(float acclX, float acclY) {
		this.acceleration.x = acclX;
		this.acceleration.y = acclY;	
	}
	
	public float getAccelerationX() {
		return acceleration.x;
	}

	public void setAccelerationX(float acclX) {
		this.acceleration.x = acclX;
	}
	
	public float getAccelerationY() {
		return acceleration.y;
	}

	public void setAccelerationY(float acclY) {
		this.acceleration.y = acclY;
	}
	
	public void setInitVelocity(float initVelocityX, float initVelocityY) {
		this.initVelocity.x = initVelocityX;
		this.initVelocity.y = initVelocityY;		
	}

	public void copy(Force force) {		
		this.maxVelocity.x = force.maxVelocity.x;
		this.maxVelocity.y = force.maxVelocity.y;
		this.acceleration.x = force.acceleration.x;
		this.acceleration.y = force.acceleration.y;
	}
	
	public void add(Force force) {
		this.maxVelocity.add(force.maxVelocity);
		this.acceleration.add(force.acceleration);
	}
}
