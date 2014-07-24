package com.mygdx.game;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public class HitBox {
	protected Rectangle boundingBox;
	protected Vector2 position = new Vector2();
	protected Vector2 size = new Vector2();
	
	public HitBox(Vector2 containerPosition, Vector2 offset, Vector2 size) {
		this.size = size;
		this.position.x = containerPosition.x + offset.x;
		this.position.y = containerPosition.y + offset.y;
		init();
	}
	
	public HitBox(Vector2 position, Vector2 size) {
		this.size = size;
		this.position = position;
		init();
	}
	
	private void init() {		
		this.boundingBox = new Rectangle((int)this.position.x, (int)this.position.y, (int)this.size.x, (int)this.size.y);
	}

	public Vector2 checkCollision(HitBox hitBox) {
		Vector2 returnValue = null;		
		Rectangle intersection = this.boundingBox.intersection(hitBox.boundingBox);
		if (intersection.getSize().height > 0 && intersection.getSize().width > 0)
		{
			returnValue = new Vector2();
			returnValue.x = intersection.getSize().width;
			returnValue.y = intersection.getSize().height;
		}
		return returnValue;
	}
	
	public float getSizeX() {
		return size.x;
	}

	public float getSizeY() {
		return size.y;
	}
	
	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}
}
