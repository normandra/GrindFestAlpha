package com.norman.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Norman on 10/30/2015.
 */
public class Background {
    private Vector2 position;
    private Vector2 position2;
    private TextureRegion bg;
    private float velX,startingv;
    private int width;
    private float maxspeed;

    public Background(TextureRegion T, float vel, int width, float maxspeed){
        this.maxspeed = maxspeed;
        this.startingv = vel;
        this.velX = vel;
        this.width = width;
        position = new Vector2(0, 0);
        position2 = new Vector2(width, 0);
        bg = T;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelX(){
        return  velX;
    }

    public void addSpeed() {
        if(velX > -maxspeed)
        velX -= maxspeed/100;

        //System.out.println(velX);
    }

    public void update(float dt){
        if (position.x <= -width){
            position.x = position2.x + width;
        }
        if (position2.x <= -width){
            position2.x = position.x + width;
        }
        position.x += velX;
        position2.x += velX;
    }

    public void updateReady(float dt){

    }

    public void onRestart(){
        position.x = 0;
        position2.x = width;
        velX = startingv;
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getloopPosition() {return position2;}
    public TextureRegion getTexture() { return bg; }

}
