package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Mario extends MarioActor {
	
	private boolean isFacingRight = true;
	private boolean isBig = false;
	
	private TextureRegion standingRight;
	private TextureRegion slidingRight;
	private TextureRegion jumpingRight;
	private TextureRegion standingLeft;
	private TextureRegion slidingLeft;
	private TextureRegion jumpingLeft;
	
	private Animation movingRight;
	private Animation movingLeft;
	
	private Animation smallMarioWalkingRight;
	private Animation smallMarioWalkingLeft;
	
	public Mario() {
		super();

        setHeight(1);
        setWidth(1);
        setPawn(true);
        
        setSmallMarioSprites();
	}	

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (isBig) {
			setBumpsTiles(false);
			setBreaksTiles(true);
		} else {
			setBumpsTiles(true);
			setBreaksTiles(false);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if (!isJumping()) {
			if (getVelocity().x == 0) {
				if (isFacingRight) {
					setActiveSprite(standingRight);
				} else {
					setActiveSprite(standingLeft);
				}
			} else 	if (isMovingRight()) {
				isFacingRight = true;
				if (getVelocity().x < 0) {
					setActiveSprite(slidingLeft);
				} else {
					setActiveSprite(movingRight.getKeyFrame(MarioGame.elapsedTime));
				}
			} else if (isMovingLeft()) {
				isFacingRight = false;
				if (getVelocity().x > 0) {
					setActiveSprite(slidingRight);
				} else {
					setActiveSprite(movingLeft.getKeyFrame(MarioGame.elapsedTime));	
				}
			} else if (isFacingRight) {
				setActiveSprite(movingRight.getKeyFrame(MarioGame.elapsedTime));				
			} else {
				setActiveSprite(movingLeft.getKeyFrame(MarioGame.elapsedTime));	
			}
		} else if (isJumping()){
			if (isFacingRight) {
				setActiveSprite(jumpingRight);
			} else {
				setActiveSprite(jumpingLeft);
			}
		}
		
		batch.draw(getActiveSprite(), getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void loadAssets() {
		super.loadAssets();
		
		float animationSpeed = 1/7f;
		
		smallMarioWalkingRight = new Animation(animationSpeed, Assets.Textures.SmallMarioWalkingRight1, Assets.Textures.SmallMarioWalkingRight2, Assets.Textures.SmallMarioWalkingRight3);
		smallMarioWalkingRight.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		smallMarioWalkingLeft = new Animation(animationSpeed, Assets.Textures.SmallMarioWalkingLeft1, Assets.Textures.SmallMarioWalkingLeft2, Assets.Textures.SmallMarioWalkingLeft3);
		smallMarioWalkingLeft.setPlayMode(PlayMode.LOOP_PINGPONG);
	}
	
	private void setSmallMarioSprites() {
		standingRight = Assets.Textures.SmallMarioStandingRight;
		movingRight = smallMarioWalkingRight;
		slidingRight = Assets.Textures.SmallMarioSlidingRight;
		jumpingRight = Assets.Textures.SmallMarioJumpingRight;
		standingLeft = Assets.Textures.SmallMarioStandingLeft;
		movingLeft = smallMarioWalkingLeft;
		slidingLeft = Assets.Textures.SmallMarioSlidingLeft;
		jumpingLeft = Assets.Textures.SmallMarioJumpingLeft;
	}
	
	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}
}
