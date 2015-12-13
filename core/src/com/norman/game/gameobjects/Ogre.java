package com.norman.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.norman.game.gameworld.GameWorld;
import com.norman.game.gfhelpers.AssetLoader;

/**
 * Created by Norman on 11/16/2015.
 */
public class Ogre {
    private Animation currentAnimation;
    private Animation runAnimation;
    private float elapsedTime, deadTime;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Rectangle hitDetect;
    private float Rotation;
    private boolean dead, remove;
    private boolean stop;

    public Ogre(int posx,int posy){
        position = new Vector2(posx,posy);
        velocity = new Vector2(0,0);
        runAnimation = AssetLoader.ogreRun;
        acceleration = new Vector2(0,0);
        currentAnimation = runAnimation;
        hitDetect = new Rectangle();
        dead = false;
        stop = false;
        remove = false;
        acceleration.x = 200;
    }

    public void update(float delta) {
        if (dead) {
            deadTime += delta;
            Rotation += 600 * delta;
            hitDetect.set(500, 500, 1, 1);
            if (position.y > 300) {
                remove = true;
            }
        } else {
            hitDetect.set(position.x + 29, position.y, 10, 80);
        }
        elapsedTime += delta;
        //speed handling
        if (stop) {

        } else {
            position.add(velocity.cpy().scl(delta));
             if(velocity.x > -260f){
            velocity.sub(acceleration.cpy().scl(delta));
        }
           // System.out.println(velocity.x);
        }
    }

    public boolean shouldRemove() { return remove; }


    public boolean collides (Hero hero) {
        if ( position.x < hero.getPosition().x + 57) {
            return (Intersector.overlaps(hero.getHitDetect(), hitDetect));
        }
        return false;
    }

    public Rectangle getHitDetect(){
        return  hitDetect;
    }

    public void die(){
        velocity.set(MathUtils.random(-400,400),300);
        dead = true;
    }

    public boolean getStatus(){ return dead;}

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }


    public float getElapsedTime() {
        return elapsedTime;
    }

    public float getRotationsize(){
        return deadTime * 5;
    }

    
/*    public void onRestart(int x, int y){
        velocity.setZero();
        position.set(x,y);
        dead = false;
    }*/

    public Vector2 getPosition() {
        return position;
    }
    public float getRotation() { return Rotation; }
}
