package com.shomazzapp.summerserfer;

public class Game extends com.badlogic.gdx.Game {

	@Override
	public void create() {
		setScreen(new com.shomazzapp.summerserfer.screens.GameScreen(this));
	}

	public void setGameOverScreen(){
		setScreen(new com.shomazzapp.summerserfer.screens.GameOverScreen(this));
	}

	public void setGameScreen(){
		setScreen(new com.shomazzapp.summerserfer.screens.GameScreen(this));
	}

}
