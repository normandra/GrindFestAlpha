package com.norman.game;


import com.badlogic.gdx.Game;
import com.norman.game.gfhelpers.AssetLoader;
import com.norman.game.screens.SplashScreen;

public class GFgame extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITTLE = "put tittle here";
	
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();

	}
}
