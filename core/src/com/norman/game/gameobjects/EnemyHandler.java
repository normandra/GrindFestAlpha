package com.norman.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.norman.game.gameworld.GameWorld;

/**
 * Created by Norman on 11/16/2015.
 */
public class EnemyHandler {
    // check if there is an enemy on the world
    // if no enemy generate a random one out side the screen


    public Ogre ogre;



    public void update (float delta){
        updateEnemy(delta);
    }

    public void updateReady(float delta){

    }

    public void createEnemy(){
       // ogre = new Ogre(20,220,21);
        System.out.println("ogreinit");
    }

    public void updateEnemy(float delta){
        ogre.update(delta);
    }

    public void killEnemy(){
        ogre.die();
    }
}
