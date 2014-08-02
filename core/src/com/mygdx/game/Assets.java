package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static TextureAtlas marioAtlas;
	public static TextureAtlas enemyAtlas;
	
	public static void load() {
		TextureAtlas marioAtlas = new TextureAtlas(Gdx.files.internal("data/smb_mario_sheet.atlas"));
		
		Assets.Textures.SmallMarioStandingRight = marioAtlas.findRegion("SmallMarioStandingRight");
		Assets.Textures.SmallMarioJumpingRight = marioAtlas.findRegion("SmallMarioJumpingRight");
		Assets.Textures.SmallMarioSlidingRight = marioAtlas.findRegion("SmallMarioSlidingRight");
		Assets.Textures.SmallMarioJumpingLeft = marioAtlas.findRegion("SmallMarioJumpingLeft");
		Assets.Textures.SmallMarioStandingLeft = marioAtlas.findRegion("SmallMarioStandingLeft");
		Assets.Textures.SmallMarioSlidingLeft = marioAtlas.findRegion("SmallMarioSlidingLeft");			
		Assets.Textures.SmallMarioWalkingRight1 = marioAtlas.findRegion("SmallMarioWalkingRight1");
		Assets.Textures.SmallMarioWalkingRight2 = marioAtlas.findRegion("SmallMarioWalkingRight2");
		Assets.Textures.SmallMarioWalkingRight3 = marioAtlas.findRegion("SmallMarioWalkingRight3");		
		Assets.Textures.SmallMarioWalkingLeft1 = marioAtlas.findRegion("SmallMarioWalkingLeft1");
		Assets.Textures.SmallMarioWalkingLeft2 = marioAtlas.findRegion("SmallMarioWalkingLeft2");
		Assets.Textures.SmallMarioWalkingLeft3 = marioAtlas.findRegion("SmallMarioWalkingLeft3");	
		
		TextureAtlas enemyAtlas = new TextureAtlas(Gdx.files.internal("data/smb_enemies_sheet.atlas"));
		
		Assets.Textures.GoombaWalking1 = enemyAtlas.findRegion("GoombaWalking1");
		Assets.Textures.GoombaWalking2 = enemyAtlas.findRegion("GoombaWalking2");
		Assets.Textures.GoombaSquashed = enemyAtlas.findRegion("GoombaSquashed");
	}
	
	public static class Textures {
		public static TextureRegion SmallMarioStandingRight;
		public static TextureRegion SmallMarioJumpingRight;
		public static TextureRegion SmallMarioSlidingRight;		
		public static TextureRegion SmallMarioJumpingLeft;
		public static TextureRegion SmallMarioStandingLeft;
		public static TextureRegion SmallMarioSlidingLeft;		
		public static TextureRegion SmallMarioWalkingRight1;
		public static TextureRegion SmallMarioWalkingRight2;
		public static TextureRegion SmallMarioWalkingRight3;
		public static TextureRegion SmallMarioWalkingLeft1;
		public static TextureRegion SmallMarioWalkingLeft2;
		public static TextureRegion SmallMarioWalkingLeft3;
		public static TextureRegion GoombaWalking1;
		public static TextureRegion GoombaWalking2;
		public static TextureRegion GoombaSquashed;
	}
	
	public static void dispose() {
		marioAtlas.dispose();
	}

}
