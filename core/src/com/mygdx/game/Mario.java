package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Mario extends MarioActor {
	
	public Mario() {
		super();

        setHeight(1);
        setWidth(1);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.draw(Assets.Animations.SmallMarioWalkingRight.getKeyFrame(MarioGame.elapsedTime), getX(), getY(), getWidth(), getHeight());
	}
}
