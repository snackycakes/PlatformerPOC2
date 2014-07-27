package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class MarioStaticTile extends StaticTiledMapTile {
	
	private boolean isBumped = false;
	private boolean isAscending = false;
	private float bumpVelocity = 42f;
	private float bumpMaxOffset = 6f;

	public MarioStaticTile(TextureRegion textureRegion) {
		super(textureRegion);
	}

	public MarioStaticTile(StaticTiledMapTile copy) {
		super(copy);
	}
	
	public void bump() {
		if (!isBumped) {
			isBumped = true;
			isAscending = true;		
		}
	}
	
	public void update() {
		if (isAscending) {
			setOffsetY(Math.min(getOffsetY() + (bumpVelocity * MarioGame.deltaTime), bumpMaxOffset));
			if (getOffsetY() == bumpMaxOffset) {
				isAscending = false;
			}
		} else if (isBumped) {
			setOffsetY(Math.max(getOffsetY() - (bumpVelocity * MarioGame.deltaTime), 0));	
			if (getOffsetY() == 0) {
				isBumped = false;
			}
		}
	}
	
	public boolean needsUpdates() {
		return isBumped;
	}

	public boolean isBumped() {
		return isBumped;
	}

	public void setBumping(boolean isBumped) {
		this.isBumped = isBumped;
	}

	public boolean isAscending() {
		return isAscending;
	}

	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}
	
	
}
