package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TileContainer.PositionType;

public class MarioStage extends Stage {
	private final static float CAMERASCROLLX = 13f;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tileRenderer;
	private float gravity = -9f;
	private float gravityAccl = -1f;
	private float frictionGround = 0.2f;
	private float frictionAir = 0.00f;
	private Mario pawn;
	private ArrayList<MarioStaticTile> updatedTiles = new ArrayList<MarioStaticTile>();

	public MarioStage(Viewport viewport, TiledMap tiledMap, OrthogonalTiledMapRenderer tileRenderer) {
		super(viewport);
		this.tiledMap = tiledMap;
		this.tileRenderer = tileRenderer;
	}
	
	@Override
	public void act() {
		// update Actors
		for (Actor actor : this.getActors()) {
			if (actor instanceof MarioActor) {
				updateActor((MarioActor)actor);
			}
		}
		
		// check for tiles that have been manipulated by collisions nnd need updates
	    Iterator<MarioStaticTile> iterator = updatedTiles.iterator();
	    while (iterator.hasNext()) {
	    	MarioStaticTile tile = iterator.next();
	    	tile.update();
	    	if (!tile.needsUpdates()) {
	    		iterator.remove();
	    	}
	    }	
	}

	@Override
	public void draw() {		
		float pawnOffset = pawn.getX() - getCamera().position.x;
		if (pawnOffset > 0) {
			getCamera().position.x += pawnOffset;
		}
		
		getCamera().update();		
		tileRenderer.render();
		tileRenderer.setView((OrthographicCamera)getCamera());
		
		super.draw();
	}
	
	public void updateActor(MarioActor actor) {
		// gravity logic
		actor.applyForce("gravity", new Force(0, gravity, 0, gravityAccl));
		float friction = 0;
		if (actor.isGrounded()) {
			friction = frictionGround;
		} else {
			friction = frictionAir;
		}
		
		//friction logic
		if (actor.getVelocity().x >= 0f) {
			if (actor.getVelocity().x - friction >= 0f) {
				actor.applyForce("friction", new Force(-friction, 0, -friction, 0));
			} else {
				actor.applyForce("friction", new Force(-actor.getVelocity().x, 0, -actor.getVelocity().x, 0));
				//mob.getVelocity().setValueX(0f);
			}
		} else{
			if (actor.getVelocity().x + friction <= 0f) {
				actor.applyForce("friction", new Force(friction, 0, friction, 0));
			} else {
				actor.applyForce("friction", new Force(-actor.getVelocity().x, 0, -actor.getVelocity().x, 0));
				//mob.getVelocity().setValueX(0f);
			}
		}
		
		actor.act(MarioGame.deltaTime);
		
		// check and resolve tile collisions
		resolveTileCollisions(actor);		
		actor.commitDesiredPosition();
	}
	
	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getGravityAccl() {
		return gravityAccl;
	}

	public void setGravityAccl(float gravityAccl) {
		this.gravityAccl = gravityAccl;
	}

	public float getFrictionGround() {
		return frictionGround;
	}

	public void setFrictionGround(float frictionGround) {
		this.frictionGround = frictionGround;
	}

	public float getFrictionAir() {
		return frictionAir;
	}

	public void setFrictionAir(float frictionAir) {
		this.frictionAir = frictionAir;
	}

	public Vector2 getTileIndex(HitBox hitBox) {
		int xPos = (int)(hitBox.getPositionX() + hitBox.getSizeX() / 2);
		int yPos = (int)(hitBox.getPositionY() + hitBox.getSizeY() / 2);
		return new Vector2(xPos, yPos);
	}
	

	public ArrayList<TileContainer> getSurroundingTiles (MarioActor actor) {
		ArrayList<TileContainer> returnValue = new ArrayList<TileContainer>();
		
		HitBox hitBox = actor.createHitBox();		
		Vector2 hitBoxTileIndex =  getTileIndex(hitBox);
		
		int hitBoxTileWidth = (int)hitBox.getSizeX();
		int hitBoxTileHeight = (int)hitBox.getSizeY();
		int xPos, yPos;
		
		TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get("Collision");
		
		yPos = (int)hitBoxTileIndex.y - 1;
		for (xPos = (int)hitBoxTileIndex.x; xPos < hitBoxTileIndex.x + hitBoxTileWidth; xPos++) {
			if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.LOWER, layer.getCell(xPos, yPos), xPos, yPos)); }
		}
		yPos = (int)hitBoxTileIndex.y + hitBoxTileHeight;
		for (xPos = (int)hitBoxTileIndex.x; xPos < hitBoxTileIndex.x + hitBoxTileWidth; xPos++) {
			if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.UPPER, layer.getCell(xPos, yPos), xPos, yPos)); }
		}
		
		xPos = (int)hitBoxTileIndex.x - 1;
		for (yPos = (int)hitBoxTileIndex.y; yPos < hitBoxTileIndex.y + hitBoxTileHeight; yPos++) {
			if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.LEFT, layer.getCell(xPos, yPos), xPos, yPos)); }
		}
		xPos = (int)hitBoxTileIndex.x + hitBoxTileWidth;
		for (yPos = (int)hitBoxTileIndex.y; yPos < hitBoxTileIndex.y + hitBoxTileHeight; yPos++) {
			if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.RIGHT, layer.getCell(xPos, yPos), xPos, yPos)); }
		}
		
		yPos = (int)hitBoxTileIndex.y + hitBoxTileHeight;
		xPos = (int)hitBoxTileIndex.x - 1;
		if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.DIAGUPPERLEFT, layer.getCell(xPos, yPos), xPos, yPos)); }
		
		yPos = (int)hitBoxTileIndex.y + hitBoxTileHeight;
		xPos = (int)hitBoxTileIndex.x + hitBoxTileWidth;
		if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.DIAGUPPERRIGHT, layer.getCell(xPos, yPos), xPos, yPos)); }
		
		yPos = (int)hitBoxTileIndex.y - 1;
		xPos = (int)hitBoxTileIndex.x - 1;
		if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.DIAGLOWERLEFT, layer.getCell(xPos, yPos), xPos, yPos)); }
		
		yPos = (int)hitBoxTileIndex.y - 1;
		xPos = (int)hitBoxTileIndex.x + hitBoxTileWidth;
		if (xPos >= 0 && yPos >= 0 && layer.getCell(xPos, yPos) != null) { returnValue.add(new TileContainer(PositionType.DIAGLOWERRIGHT, layer.getCell(xPos, yPos), xPos, yPos)); }

		return returnValue;
	}
	
	public void resolveTileCollisions(MarioActor actor) {
		ArrayList<TileContainer> surroundingTiles = getSurroundingTiles(actor);
		boolean isMobGrounded = false;
		
		for (TileContainer tileContainer : surroundingTiles) {	
			Cell cell = tileContainer.getTileCell();
			TiledMapTile tile = cell.getTile();
			HitBox tileHitBox = new HitBox(tileContainer.getxIndex(), tileContainer.getyIndex(), 1, 1);

			PositionType resolvePosition;
			
			Rectangle collisionDepth = tileHitBox.checkCollision(actor.createHitBox());
			
			if (collisionDepth != null) {
				resolvePosition = tileContainer.getRelativePosition();
				
				// resolve a diagonal collision to either left, right, up, or down.
				if ((resolvePosition == PositionType.DIAGLOWERLEFT || resolvePosition == PositionType.DIAGLOWERRIGHT || resolvePosition == PositionType.DIAGUPPERLEFT || resolvePosition == PositionType.DIAGUPPERRIGHT)) {
					// check previous hit box location to determine desired collision for this update
					
					if (resolvePosition == PositionType.DIAGLOWERLEFT || resolvePosition == PositionType.DIAGLOWERRIGHT) {
						if ((actor.getY() < tileHitBox.getPositionY() + tileHitBox.getSizeY())) {
							if (resolvePosition == PositionType.DIAGLOWERLEFT) {
								resolvePosition = PositionType.LEFT;
							} else {
								resolvePosition = PositionType.RIGHT;
							}
						} else {
							resolvePosition = PositionType.LOWER;
						}
					} else {
						if (collisionDepth.getWidth() <= collisionDepth.getHeight()) {
							if (resolvePosition == PositionType.DIAGUPPERLEFT) {
								resolvePosition = PositionType.LEFT;
							} else {
								resolvePosition = PositionType.RIGHT;
							}
						} else {
							resolvePosition = PositionType.UPPER;
						}
					}
				}
				
				switch (resolvePosition) {
					case LOWER:
						actor.setDesiredPositionY(actor.getDesiredPositionY() + collisionDepth.getHeight());
						isMobGrounded = true;
						break;
					case UPPER:
						actor.setDesiredPositionY(actor.getDesiredPositionY() - collisionDepth.getHeight());
						actor.setVelocityY(0);
						
						if (!(tile instanceof MarioStaticTile) || !((MarioStaticTile)tile).isBumped()) {
							
						}
						MarioStaticTile bumpedTile = new MarioStaticTile(tile.getTextureRegion());
						bumpedTile.bump();
						updatedTiles.add(bumpedTile);
						tileContainer.getTileCell().setTile(bumpedTile);
						
						break;
					case LEFT:
						actor.setDesiredPositionX(actor.getDesiredPositionX() + collisionDepth.getWidth());
						actor.setVelocityX(0);
						break;
					case RIGHT:
						actor.setDesiredPositionX(actor.getDesiredPositionX() - collisionDepth.getWidth());
						actor.setVelocityX(0);
						break;
					default:
						break;					
				}
				
				//actor.collisionUpdate(tileContainer);
			}
		}
		
		actor.setGrounded(isMobGrounded);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		tiledMap.dispose();
		tileRenderer.dispose();
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public void setTileMap(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}

	public OrthogonalTiledMapRenderer getTileRenderer() {
		return tileRenderer;
	}

	public void setTileRenderer(OrthogonalTiledMapRenderer tileRenderer) {
		this.tileRenderer = tileRenderer;
	}

	public Mario getPawn() {
		return pawn;
	}

	public void setPawn(Mario pawn) {
		this.pawn = pawn;
	}
}
