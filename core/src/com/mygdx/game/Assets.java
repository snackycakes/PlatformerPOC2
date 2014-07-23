package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static TextureAtlas marioAtlas = new TextureAtlas(Gdx.files.internal("data/smb_mario_sheet.atlas"));
	
	public static void load() {
		TextureAtlas marioAtlas = new TextureAtlas(Gdx.files.internal("data/smb_mario_sheet.atlas"));
		
		Assets.Textures.SmallMarioStandingRight = marioAtlas.findRegion("SmallMarioStandingRight");
		
	}
	
	public static class Textures {
		public static TextureRegion SmallMarioStandingRight;
	}
	
	public static void dispose() {
		marioAtlas.dispose();
	}

}
