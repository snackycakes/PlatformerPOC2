package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MarioStage extends Stage {
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tileRenderer;
	protected float gravity = -4f;
	protected float gravityAccl = -0.3f;
	protected float frictionGround = 0.06f;
	protected float frictionAir = 0.00f;

	public MarioStage(Viewport viewport, TiledMap tiledMap, OrthogonalTiledMapRenderer tileRenderer) {
		super(viewport);
		this.tiledMap = tiledMap;
		this.tileRenderer = tileRenderer;
	}

	@Override
	public void draw() {
		update();
		
		getCamera().update();		
		tileRenderer.render();
		
		super.draw();
	}
	
	public void update() {
		for (Actor actor : this.getActors()) {
			if (actor instanceof MarioActor) {
				updateActor((MarioActor)actor);
			}
		}
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
		
		actor.update();
		
		// check and resolve tile collisions
		//resolveTileCollisionsNew(actor);
		
		actor.commitDesiredPosition();
	}
	
	public Vector2 getTileIndex(HitBox hitBox) {
		int xPos = (int)(hitBox.getPositionX() + hitBox.getSizeX()) / 2;
		int yPos = (int)(hitBox.getPositionY() + hitBox.getSizeY()) / 2;
		return new Vector2(xPos, yPos);
	}
	
/*
	public ArrayList<Cell> getSurroundingTiles (MarioActor actor) {
		ArrayList<TileContainer> returnValue = new ArrayList<TileContainer>();
		
		HitBox hitBox = actor.createHitBox();
		
		OrderedPair hitBoxTileIndex =  getTileIndexe(hitBox);
		int hitBoxTileWidth = (int)Math.ceil((double)hitBox.getSizeX() / tileSize.getPosX());
		int hitBoxTileHeight = (int)Math.ceil((double)hitBox.getSizeY() / tileSize.getPosY());
		int xPos, yPos;		
		
		yPos = hitBoxTileIndex.getPosY() + hitBoxTileHeight;
		for (xPos = hitBoxTileIndex.getPosX(); xPos < hitBoxTileIndex.getPosX() + hitBoxTileWidth; xPos++) {
			if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.LOWER, hitBox, tiles[xPos][yPos])); }
		}
		yPos = hitBoxTileIndex.getPosY() - 1;
		for (xPos = hitBoxTileIndex.getPosX(); xPos < hitBoxTileIndex.getPosX() + hitBoxTileWidth; xPos++) {
			if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.UPPER, hitBox, tiles[xPos][yPos])); }
		}
		xPos = hitBoxTileIndex.getPosX() - 1;
		for (yPos = hitBoxTileIndex.getPosY(); yPos < hitBoxTileIndex.getPosY() + hitBoxTileHeight; yPos++) {
			if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.LEFT, hitBox, tiles[xPos][yPos])); }
		}
		xPos = hitBoxTileIndex.getPosX() + hitBoxTileWidth;
		for (yPos = hitBoxTileIndex.getPosY(); yPos < hitBoxTileIndex.getPosY() + hitBoxTileHeight; yPos++) {
			if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.RIGHT, hitBox, tiles[xPos][yPos])); }
		}
		
		yPos = hitBoxTileIndex.getPosY() - 1;
		xPos = hitBoxTileIndex.getPosX() - 1;
		if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.DIAGUPPERLEFT, hitBox, tiles[xPos][yPos])); }
		
		yPos = hitBoxTileIndex.getPosY() - 1;
		xPos = hitBoxTileIndex.getPosX() + hitBoxTileWidth;
		if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.DIAGUPPERRIGHT, hitBox, tiles[xPos][yPos])); }
		
		yPos = hitBoxTileIndex.getPosY() + hitBoxTileHeight;
		xPos = hitBoxTileIndex.getPosX() - 1;
		if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.DIAGLOWERLEFT, hitBox, tiles[xPos][yPos])); }
		
		yPos = hitBoxTileIndex.getPosY() + hitBoxTileHeight;
		xPos = hitBoxTileIndex.getPosX() + hitBoxTileWidth;
		if (xPos >= 0 && yPos >= 0 && tiles[xPos][yPos] != null) { returnValue.add(new TileContainer(PositionType.DIAGLOWERRIGHT, hitBox, tiles[xPos][yPos])); }
		
		return returnValue;
	}
*/
	
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
}
