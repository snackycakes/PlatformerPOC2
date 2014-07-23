package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
