package com.mygdx.game;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public class HitBox {
	protected Rectangle boundingBox;
	protected Vector2 boxOffset = new Vector2();
	protected Vector2 size = new Vector2();
	
	/*
	private void init() {		
		this.boundingBox = new Rectangle(this.position.getPosX(), this.position.getPosY(), this.sizeX, this.sizeY);
	}
	*/
	
	public HitBox(Vector2 boxOffset, Vector2 size) {
		this.size = size;
		this.boxOffset = boxOffset;
	}

	/*
	public Vector2 checkCollision(HitBox hitBox) {
		OrderedPair returnValue = null;		
		Rectangle intersection = this.boundingBox.intersection(hitBox.boundingBox);
		if (intersection.getSize().height > 0 && intersection.getSize().width > 0)
		{
			returnValue = new OrderedPair();
			returnValue.setPosX(intersection.getSize().width);
			returnValue.setPosY(intersection.getSize().height);
		}
		return returnValue;
	}
	*/
	
	public float getSizeX() {
		return size.x;
	}

	public float getSizeY() {
		return size.y;
	}

	public Vector2 getBoxOffset() {
		return boxOffset;
	}

	public void setBoxOffset(Vector2 boxOffset) {
		this.boxOffset = boxOffset;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}
}
