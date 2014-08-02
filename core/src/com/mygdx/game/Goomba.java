package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Goomba extends MarioActor {
	private Animation walking;
	private TextureRegion stomped;
	
	public Goomba() {
		super();

        setHeight(1);
        setWidth(1);
        setCanBeStomped(true);
        setMovementForce(2f);
	}	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (!isDead() && isGrounded()) {
			setMovingLeft(true);
		} else {
			setMovingLeft(false);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!isDead()) {
			setActiveSprite(walking.getKeyFrame(MarioGame.elapsedTime));
		} else {
			setActiveSprite(stomped);
		}
		
		super.draw(batch, parentAlpha);
	}

	@Override
	public void loadAssets() {
		super.loadAssets();
		
		float animationSpeed = 1/7f;
		
		walking = new Animation(animationSpeed, Assets.Textures.GoombaWalking1, Assets.Textures.GoombaWalking2);
		walking.setPlayMode(PlayMode.LOOP_PINGPONG);
		stomped = Assets.Textures.GoombaSquashed;
	}
}
