package com.mygdx.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
	
	public HitBox(float posX, float posY, float sizeX, float sizeY) {
		this.position.x = posX;
		this.position.y = posY;
		this.size.x = sizeX;
		this.size.y = sizeY;
		init();
	}
	
	private void init() {		
		this.boundingBox = new Rectangle(this.position.x, this.position.y, this.size.x, this.size.y);
	}

	public Rectangle checkCollision(HitBox hitBox) {	
		Rectangle returnValue = new Rectangle();		

		if (!Intersector.intersectRectangles(boundingBox, hitBox.boundingBox, returnValue)) {
			returnValue = null;
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
