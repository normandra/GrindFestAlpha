package com.norman.game.gameworld;

import com.badlogic.gdx.math.MathUtils;
import com.norman.game.GFgame;
import com.norman.game.gameobjects.Background;
import com.norman.game.gameobjects.Hero;
import com.norman.game.gameobjects.Ogre;
import com.norman.game.gfhelpers.AssetLoader;
import com.norman.game.ui.SimpleButton;

import java.util.ArrayList;

/**
 * Created by Norman on 11/15/2015.
 */
public class GameWorld {
    private Hero hero;

    private ArrayList<Ogre> enemies;


    private Background clouds, backgrounda, backgroundb, middleground, foreground;

    private GameRenderer renderer;
    private GameState currentState;
    private boolean stop;

    private int score;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public Background getClouds() {
        return clouds;
    }

    public Background getBackgrounda() {
        return backgrounda;
    }

    public Background getBackgroundb() {
        return backgroundb;
    }

    public Background getMiddleground() {
        return middleground;
    }

    public Background getForeground() {
        return foreground;
    }


    public GameWorld() {
        currentState = GameState.MENU;
        hero = new Hero(20, 21);
        clouds = new Background(AssetLoader.clouds, -0.1f,400,1);
        backgroundb = new Background(AssetLoader.backgroundb, -0.3f,800,3);
        backgrounda = new Background(AssetLoader.backgrounda, -0.6f,800,6);
        middleground = new Background(AssetLoader.middleground, -1f,800,10);
        foreground = new Background(AssetLoader.foreground, -1.5f,800,15);




        //enemy array
        enemies = new ArrayList<Ogre>();

    }

    public void spawn() {
        enemies.add(new Ogre(MathUtils.random(400, 1800), 21));
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }
        if (stop != true) {
            hero.update(delta);
            backgrounda.update(delta);
            backgroundb.update(delta);
            middleground.update(delta);
            foreground.update(delta);
            clouds.update(delta);
        }
        if (enemies.size() < 9) {
            spawn();
        }



        for (int i = 0; i < enemies.size(); i++) {
            if (stop == false)
                enemies.get(i).update(delta);
            if (enemies.get(i).collides(hero) && stop == false) {
                //if (middleground.getVelX() == 4)
                if (hero.isAttacking()) {
                    score++;
                    clouds.addSpeed();
                    backgroundb.addSpeed();
                    backgrounda.addSpeed();
                    middleground.addSpeed();
                    foreground.addSpeed();
                    AssetLoader.hit.play();
                    enemies.get(i).die();
                } else {
                    hero.die();
                    stop = true;
                    renderer.prepareTransition(255, 255, 255, 1f);
                    currentState = GameState.GAMEOVER;

                    if(score > AssetLoader.getHighScore()){
                        AssetLoader.setHighScore(score);
                        currentState = GameState.HIGHSCORE;
                    }
                }
            }

            /*if(hero.isAttacking()){
                enemies.get(i).die();
                score++;
                AssetLoader.hit.play();
                clouds.addSpeed();
                backgroundb.addSpeed();
                backgrounda.addSpeed();
                middleground.addSpeed();
                foreground.addSpeed();

            }*/
            if (enemies.get(i).shouldRemove()) {
                enemies.remove(i);
                i--;
            }
        }




        /*if (ogre.collides(hero)){
            if(hero.isAttacking()){
                AssetLoader.hit.play();
                ogre.die();
            }else{
                hero.die();
                ogre.stop();
                renderer.prepareTransition(255,255,255,1f);

            }
        }*/

    }

    public void update(float delta) {


        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        hero.updateReady(delta);
        clouds.updateReady(delta);
        backgrounda.updateReady(delta);
        backgroundb.updateReady(delta);
        middleground.updateReady(delta);

    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isHighscore() { return currentState == GameState.HIGHSCORE; }

    public void start() {

        currentState = GameState.RUNNING;
        hero.start();
        for (int i = 0; i < enemies.size(); i++) {

        }
    }

    public void ready() {

        currentState = GameState.READY;
        hero.ready();
        renderer.prepareTransition(0, 0, 0, 1f);

    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public void restart() {
        currentState = GameState.READY;
        stop = false;
        hero.onRestart(20, 21);
        enemies.clear();
        score = 0;

        //BACKGROUND SHITS
        clouds.onRestart();
        backgrounda.onRestart();
        backgroundb.onRestart();
        middleground.onRestart();
        foreground.onRestart();

        renderer.prepareTransition(255, 255, 255, 1f);
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList getEnemies() {
        return enemies;
    }

    public boolean getStop() {
        return stop;
    }

    public int getScore() {
        return score;
    }


}

