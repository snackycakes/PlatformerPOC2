package com.mygdx.game;

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
	
    private Stage stage;
    //private TextureAtlas marioAtlas;
    private Mario mario;
    
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
        
        mario = new Mario();
        mario.setPosition(0, 10);
        
        stage.addActor(mario);
        
        //mario.setMovingRight(true);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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