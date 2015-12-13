package com.norman.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.norman.game.GFgame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(GFgame.WIDTH, GFgame.HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new GFgame();
        }
}