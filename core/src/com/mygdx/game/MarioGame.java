package com.mygdx.game;

import java.awt.event.KeyEvent;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MarioGame implements ApplicationListener, InputProcessor {
	
	public static float deltaTime = 0;
    public static float elapsedTime = 0;
    
    private MarioStage stage;
    private Mario playerMario;
    
    @Override
    public void create() {
    	Assets.load();    		

		// create an orthographic camera, shows us 16 x 15 units of the world
    	OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 15);
		camera.update();    	  	
    	
		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
		TiledMap tiledMap = new TmxMapLoader().load("data/smb_stage1.tmx");
		OrthogonalTiledMapRenderer tileRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);
		tileRenderer.setView(camera);	
		
    	stage = new MarioStage(new FitViewport(16, 15, camera), tiledMap, tileRenderer);    	
        
    	playerMario = new Mario();
    	playerMario.setPosition(0, 10);
        
        stage.addActor(playerMario);
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {
    	
		// clear the screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		MarioGame.deltaTime = Math.min(3/60f, Gdx.graphics.getDeltaTime());
		MarioGame.elapsedTime += MarioGame.deltaTime;
		
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    	 stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			break;

		case Keys.DOWN:
			break;

		case Keys.LEFT:
			playerMario.setMovingLeft(true);
			break;

		case Keys.RIGHT:
			playerMario.setMovingRight(true);
			break;

		case Keys.SPACE:
      			playerMario.setJumping(true);
			break;

		case Keys.CONTROL_LEFT:
			break;
		
		case Keys.G:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				stage.setGravity(stage.getGravity() - .1f);
			else
				stage.setGravity(stage.getGravity() + .1f);
			break;
			
		case Keys.H:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				stage.setGravityAccl(stage.getGravityAccl() - .01f);
			else
				stage.setGravityAccl(stage.getGravityAccl() + .01f);
			break;
			
		case Keys.F:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				stage.setFrictionGround(stage.getFrictionGround() - .01f);
			else
				stage.setFrictionGround(stage.getFrictionGround() + .01f);
			break;
			
		case Keys.A:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				stage.setFrictionAir(stage.getFrictionAir() - .01f);
			else
				stage.setFrictionAir(stage.getFrictionAir() + .01f);
			break;
			
		case Keys.J:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setJumpForce(playerMario.getJumpForce() - .1f);
			else
				playerMario.setJumpForce(playerMario.getJumpForce() + .1f);
			break;
			
		case Keys.U:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setJumpInitialForce(playerMario.getJumpInitialForce() - .1f);
			else
				playerMario.setJumpInitialForce(playerMario.getJumpInitialForce() + .1f);
			break;
			
		case Keys.K:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setJumpAcceleration(playerMario.getJumpAcceleration() - .1f);
			else
				playerMario.setJumpAcceleration(playerMario.getJumpAcceleration() + .1f);
			break;
			
		case Keys.L:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setJumpMaxTime(playerMario.getJumpMaxTime() - 1f);
			else
				playerMario.setJumpMaxTime(playerMario.getJumpMaxTime() + 1f);
			break;
			
		case Keys.M:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setMovementForce(playerMario.getMovementForce() - 1f);
			else
				playerMario.setMovementForce(playerMario.getMovementForce() + 1f);
			break;
			
		case Keys.N:
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
				playerMario.setMovementAccl(playerMario.getMovementAccl() - 1f);
			else
				playerMario.setMovementAccl(playerMario.getMovementAccl() + 1f);
			break;
	}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			break;

		case Keys.DOWN:
			break;

		case Keys.LEFT:
			playerMario.setMovingLeft(false);
			break;

		case Keys.RIGHT:
			playerMario.setMovingRight(false);
			break;

		case Keys.SPACE:
			playerMario.setJumping(false);
			break;
			
		case Keys.CONTROL_LEFT:
			break;
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}