package com.norman.game.gfhelpers;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Norman on 11/15/2015.
 */
public class AssetLoader {

    private static TextureAtlas mainAtlas;

    public static TextureRegion splash, foreground, middleground, backgrounda,backgroundb, clouds, playbutton,playbuttonpressed,sound,mute;

    public static Preferences prefs;
    public static Animation heroRun;
    public static Animation heroAttack;
    public static Animation heroIdle;
    public static Sound slash,hit;

    public static Animation ogreRun;

    public static BitmapFont font, shadow;

    public static void load(){

        //preferences(saving)
        prefs = Gdx.app.getPreferences("GFgame");

        if(!prefs.contains("highScore")){
            prefs.putInteger("highScore",0);
        }

        mainAtlas = new TextureAtlas(Gdx.files.internal("data/GrindFest.pack"));

        //Buttons-------------------------------------------------------
        playbutton = new TextureRegion(mainAtlas.findRegion("playbutton"));
        playbuttonpressed = new TextureRegion(mainAtlas.findRegion("playbutton pressed"));

        sound = new TextureRegion(mainAtlas.findRegion("mutebutton on"));
        mute = new TextureRegion(mainAtlas.findRegion("mutebutton off"));


        //Hero Animation------------------------------------------------
        heroRun = new Animation(1/15f,
                (mainAtlas.findRegion("MainCharacterRun0000")),
                (mainAtlas.findRegion("MainCharacterRun0001")),
                (mainAtlas.findRegion("MainCharacterRun0002")),
                (mainAtlas.findRegion("MainCharacterRun0003")),
                (mainAtlas.findRegion("MainCharacterRun0004")),
                (mainAtlas.findRegion("MainCharacterRun0005")),
                (mainAtlas.findRegion("MainCharacterRun0006")),
                (mainAtlas.findRegion("MainCharacterRun0007")));
        heroRun.setPlayMode(Animation.PlayMode.LOOP);

        heroAttack = new Animation(1/30f,
                (mainAtlas.findRegion("MainCharacterRun+Attack0000")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0001")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0002")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0003")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0004")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0005")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0006")),
                (mainAtlas.findRegion("MainCharacterRun+Attack0007")));

        heroIdle = new Animation(1/8f,
                (mainAtlas.findRegion("MainCharacterIdle0000")),
                (mainAtlas.findRegion("MainCharacterIdle0001")),
                (mainAtlas.findRegion("MainCharacterIdle0002")),
                (mainAtlas.findRegion("MainCharacterIdle0003")));

        heroIdle.setPlayMode(Animation.PlayMode.LOOP);
        //Ogre Animation-------------------------------------------------
        ogreRun = new Animation(1/15f,
                (mainAtlas.findRegion("GoblinRun0000")),
                (mainAtlas.findRegion("GoblinRun0001")),
                (mainAtlas.findRegion("GoblinRun0002")),
                (mainAtlas.findRegion("GoblinRun0003")),
                (mainAtlas.findRegion("GoblinRun0004")),
                (mainAtlas.findRegion("GoblinRun0005")),
                (mainAtlas.findRegion("GoblinRun0006")),
                (mainAtlas.findRegion("GoblinRun0007")));
        ogreRun.setPlayMode(Animation.PlayMode.LOOP);
        //backgrounds---------------------------------------------------
        splash = new TextureRegion(mainAtlas.findRegion("NewLogo"));
        foreground = new TextureRegion(mainAtlas.findRegion("foreground"));
        middleground = new TextureRegion(mainAtlas.findRegion("middleground"));
        backgrounda = new TextureRegion(mainAtlas.findRegion("Background A"));
        backgroundb = new TextureRegion(mainAtlas.findRegion("Background B"));
        clouds = new TextureRegion(mainAtlas.findRegion("clouds"));



        //font------------------------------
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.25f, .25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.25f, .25f);

        //sound-----------------------------------
        hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
        slash = Gdx.audio.newSound(Gdx.files.internal("data/slash.wav"));

    }


    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose(){
        mainAtlas.dispose();
        font.dispose();
        shadow.dispose();
        slash.dispose();
    }
}
