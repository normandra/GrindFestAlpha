package com.norman.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.norman.game.GFgame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GFgame.WIDTH;
		config.height = GFgame.HEIGHT;
		config.title = GFgame.TITTLE;
		new LwjglApplication(new GFgame(), config);
	}
}
