package com.norman.game.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.norman.game.TweenAccessors.Value;
import com.norman.game.TweenAccessors.ValueAccessor;
import com.norman.game.gameobjects.Background;
import com.norman.game.gameobjects.Hero;
import com.norman.game.gameobjects.Ogre;
import com.norman.game.gfhelpers.AssetLoader;
import com.norman.game.gfhelpers.InputHandler;
import com.norman.game.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Norman on 11/15/2015.
 */
public class GameRenderer {

    private OrthographicCamera cam;

    private GameWorld myWorld;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private Hero hero;
    private ArrayList<Ogre> enemies;

    private Background clouds, backgrounda, backgroundb, foreground, middleground;

    //tween
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private List<SimpleButton> menuButtons;
    private Color transitionColor;

    public GameRenderer(GameWorld world,OrthographicCamera cam){
        this.cam = cam;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();


        myWorld = world;

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        setupTweens();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
    }

    private void initGameObjects() {
        hero = myWorld.getHero();
        clouds = myWorld.getClouds();
        backgrounda = myWorld.getBackgrounda();
        backgroundb = myWorld.getBackgroundb();
        middleground = myWorld.getMiddleground();
        foreground = myWorld.getForeground();


        enemies = myWorld.getEnemies();
    }


    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawBackground(){
        //MIDDLE - A - B - CLOUDS

        batcher.draw(clouds.getTexture(), clouds.getPosition().x, clouds.getPosition().y);
        batcher.draw(clouds.getTexture(), clouds.getloopPosition().x, clouds.getloopPosition().y);

        batcher.draw(backgroundb.getTexture(), backgroundb.getPosition().x, backgroundb.getPosition().y);
        batcher.draw(backgroundb.getTexture(), backgroundb.getloopPosition().x, backgroundb.getloopPosition().y);

        batcher.draw(backgrounda.getTexture(), backgrounda.getPosition().x, backgrounda.getPosition().y);
        batcher.draw(backgrounda.getTexture(), backgrounda.getloopPosition().x, backgrounda.getloopPosition().y);

        batcher.draw(middleground.getTexture(), middleground.getPosition().x, middleground.getPosition().y);
        batcher.draw(middleground.getTexture(), middleground.getloopPosition().x, middleground.getloopPosition().y);

    }

    private void drawForeground(){
        batcher.draw(foreground.getTexture(), foreground.getPosition().x, foreground.getPosition().y);
        batcher.draw(foreground.getTexture(), foreground.getloopPosition().x, foreground.getloopPosition().y);
    }

    private void drawMenuUI() {


        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawEndUI() {
        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

        if(myWorld.isGameOver()){

            AssetLoader.font.draw(batcher, "Final Score: " + myWorld.getScore(), cam.viewportWidth/2- 110,  170);

            AssetLoader.font.draw(batcher, "Best: " + AssetLoader.getHighScore(), cam.viewportWidth/2+50,  170);

        }else{
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "NEW HIGHSCORE! " + AssetLoader.getHighScore(), cam.viewportWidth/2, 170);
            // Draw text
            AssetLoader.font.draw(batcher, "NEW HIGHSCORE! " + AssetLoader.getHighScore(), cam.viewportWidth/2,  170);

        }

    }

    private void drawScore(){
        // Convert integer into String
        String life = myWorld.getScore() + "";

        /*// Draw shadow first
        AssetLoader.shadow.draw(batcher, "" + myWorld.getHp(), (5)
                - (3 * life.length()), 6);
        // Draw text
        AssetLoader.font.draw(batcher, "" + myWorld.getHp(), (5)
                - (3 * life.length() - 1), 5);*/

        // Draw shadow first
        AssetLoader.shadow.draw(batcher, " " + myWorld.getScore(), (cam.viewportWidth/2)
                - (3 * life.length()), 200);
        // Draw text
        AssetLoader.font.draw(batcher, " " + myWorld.getScore(), (cam.viewportWidth/2)
                - (3 * life.length() - 1), 200);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

    private void drawHero(){
        batcher.draw(hero.getCurrentAnimation().getKeyFrame(hero.getElapsedTime()),hero.getPosition().x,hero.getPosition().y);
    }

    private void drawEnemy(){
       /* if(ogre.getStatus()){
            batcher.draw(ogre.getCurrentAnimation().getKeyFrame(ogre.getElapsedTime()),ogre.getPosition().x,ogre.getPosition().y,80/2,80/2,80,80,1,1,ogre.getRotation());
        }else {
            batcher.draw(ogre.getCurrentAnimation().getKeyFrame(ogre.getElapsedTime()), ogre.getPosition().x, ogre.getPosition().y);
        }*/

        for(int i = 0; i <enemies.size(); i++){

            if(enemies.get(i).getStatus()){
                batcher.draw(enemies.get(i).getCurrentAnimation().getKeyFrame(enemies.get(i).getElapsedTime()),
                        enemies.get(i).getPosition().x, enemies.get(i).getPosition().y, 80 / 2, 80 / 2, 80, 80, enemies.get(i).getRotationsize(), enemies.get(i).getRotationsize(), enemies.get(i).getRotation());
            }else {
                batcher.draw(enemies.get(i).getCurrentAnimation().getKeyFrame(enemies.get(i).getElapsedTime()), enemies.get(i).getPosition().x, enemies.get(i).getPosition().y);
            }
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Begin SpriteBatch
        batcher.begin();
        batcher.enableBlending();
        drawBackground();

        if (myWorld.isRunning()) {
            drawHero();
            drawEnemy();
            drawScore();
        } else if (myWorld.isReady()) {
            drawHero();
            drawScore();
        } else if (myWorld.isMenu()) {
            drawHero();
            drawMenuUI();

        } else if (myWorld.isGameOver() || myWorld.isHighscore()) {
            drawHero();
            drawEnemy();
            drawEndUI();
        }

        drawForeground();


        // End SpriteBatch
        batcher.end();


        //collision check
       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hero.getHitDetect().x, hero.getHitDetect().y, hero.getHitDetect().width, hero.getHitDetect().height);
        for(int i = 0; i <enemies.size(); i++){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(enemies.get(i).getHitDetect().x, enemies.get(i).getHitDetect().y, enemies.get(i).getHitDetect().width, enemies.get(i).getHitDetect().height);

        }

        shapeRenderer.end();*/


        drawTransition(delta);


    }

}
