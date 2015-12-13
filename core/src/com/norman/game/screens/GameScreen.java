package com.norman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.norman.game.GFgame;
import com.norman.game.gameworld.GameRenderer;
import com.norman.game.gameworld.GameWorld;
import com.norman.game.gfhelpers.InputHandler;

/**
 * Created by Norman on 11/15/2015.
 */
public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private OrthographicCamera cam;
    private Viewport viewport;

    public GameScreen() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GFgame.WIDTH/2,GFgame.HEIGHT/2);

        //Gdx.app.log("width", " "+Gdx.graphics.getWidth());

        world = new GameWorld();
        renderer = new GameRenderer(world,cam,viewport);
        world.setRenderer(renderer);

        Gdx.input.setInputProcessor(new InputHandler(world, cam.viewportWidth / Gdx.graphics.getWidth(), cam.viewportHeight / Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
