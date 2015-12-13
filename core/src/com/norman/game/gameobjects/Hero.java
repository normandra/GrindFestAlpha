package com.norman.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.norman.game.GFgame;
import com.norman.game.gfhelpers.AssetLoader;

/**
 * Created by Norman on 11/15/2015.
 */
public class Hero {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float originalY;
    private float originalX;

    private float elapsedTime, stateTime;
    private Rectangle hitDetect;

    private Animation currentAnimation;
    private Animation runAnimation;
    private Animation attackAnimation;
    private Animation idleAnimation;

    private int WalkState;
    public States currentState;
    public enum States {
        IDLE , RUNNING, ATTACKING
    }
    public Hero(float x, float y){
        this.originalX = x;
        this. originalY = y;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(460,0);

        runAnimation = AssetLoader.heroRun;
        attackAnimation = AssetLoader.heroAttack;
        idleAnimation = AssetLoader.heroIdle;

        currentAnimation = idleAnimation;

        WalkState = 1;
        currentState = States.IDLE;

        hitDetect = new Rectangle();
    }

    public boolean isAttacking (){
        return currentState == States.ATTACKING;
    }

    public void die(){
        velocity.set(0, 0);
    }

    public void update(float delta){

        //attack animation and frame handling
        elapsedTime += delta;

        if(currentAnimation == attackAnimation){
            stateTime += delta;
            if (currentAnimation.isAnimationFinished(stateTime)){
                currentAnimation = runAnimation;
                currentState = States.RUNNING;
            }
        }
        //collision
        if (isAttacking()){
            hitDetect.set(position.x+56, position.y, 25, 80);
        }else {
            hitDetect.set(position.x + 20, position.y, 40, 80);
        }
        //wiggly walk
        if(WalkState == 1){
            if(position.y < 23){
                position.y += 0.3f;
            }else{
                WalkState = 0;
            }
        }else if (position.y > 16){
            position.y += -0.3f;
        }else{
            WalkState = 1;
        }




        //speed handling
        if (velocity.x > 200) {
            velocity.x = 200;
        }
        position.add(velocity.cpy().scl(delta));


        if(position.x > GFgame.WIDTH /8){
            if (velocity.x > 0){
                velocity.sub(acceleration.cpy().scl(delta));
            }
        }else{
            velocity.add(acceleration.cpy().scl(delta));
        }
    }

    public Rectangle getHitDetect(){
        return  hitDetect;
    }

    public void updateReady(float delta) {
        elapsedTime += delta;
        position.y = originalY;
        position.x = originalX;
    }

    public void onRestart(int posx, int posy) {
        currentAnimation = idleAnimation;
        currentState = States.IDLE;
        position.y = posy;
        position.x = posx;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 460;
    }

    public void attack(){
        if(position.x<GFgame.WIDTH/2-200 && currentAnimation != attackAnimation && position.x > 0) {
            elapsedTime = 0;
            AssetLoader.slash.play();
            velocity.x = -150;
            stateTime = 0;
            currentAnimation = attackAnimation;
            currentState = States.ATTACKING;
        }
    }


    public void ready(){
        currentAnimation = idleAnimation;
        currentState = States.IDLE;
    }

    public void start(){
        currentAnimation = runAnimation;
    }
    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public Vector2 getPosition() {
        return position;
    }
}
