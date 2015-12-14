package com.norman.game.gfhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.norman.game.GFgame;
import com.norman.game.gameobjects.Hero;
import com.norman.game.gameworld.GameWorld;
import com.norman.game.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;


public class InputHandler implements InputProcessor {
	
	private Hero hero;
	private GameWorld myWorld;

	private List<SimpleButton> menuButtons;
	private SimpleButton playButton;

    private float scaleFactorX;
    private float scaleFactorY;


	// Ask for a reference to the char class when InputHandler is created.
    public InputHandler(GameWorld myWorld, float scaleFactorX,
                        float scaleFactorY) {

        this.myWorld = myWorld;
        hero = myWorld.getHero();

		//buttons
		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(GFgame.WIDTH/4-25,GFgame.HEIGHT/4-40,50,30, AssetLoader.playbutton, AssetLoader.playbuttonpressed);
		menuButtons.add(playButton);

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        
    }

	@Override
	public boolean keyDown(int keycode) {
		// space button on pc desktop
		if(keycode == Input.Keys.SPACE){
			if (myWorld.isMenu()) {
				myWorld.ready();
			} else if (myWorld.isReady()) {
				myWorld.start();
			}
			else if (myWorld.isRunning()) {
				hero.attack();


			}

		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println(screenX + " " + screenY);
		//Gdx.app.log("location1 ",screenX + " " + screenY);
		screenX = scaleX(screenX);
        screenY = scaleY(screenY);
		//Gdx.app.log("location ",screenX + " " + screenY);

        if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX,screenY);
        } else if (myWorld.isReady()) {
            myWorld.start();
        }
        else if (myWorld.isRunning()) {
                hero.attack();


        }
        if (myWorld.isGameOver() || myWorld.isHighscore()) {
            // Reset all variables, go to GameState.READ
			playButton.isTouchDown(screenX, screenY);
        }
		return true;// true to enable input handler
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if(myWorld.isMenu()){
			if(playButton.isTouchUp(screenX, screenY)){
				myWorld.ready();
				return true;
			}
		}

		if(myWorld.isGameOver()||myWorld.isHighscore()){
			if(playButton.isTouchUp(screenX,screenY)){
				myWorld.restart();
				return true;
			}
		}

        return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
    private int scaleX(int screenX) {
        return (int) (screenX * scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (240 - (screenY * scaleFactorY));
    }

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}


}
