package com.shomazzapp.summerserfer;


public class GameApp extends com.badlogic.gdx.Game {

	public int inputMethod = Constants.INPUT_METHOD_ACCELEROMETR;

	@Override
	public void create() {
		setScreen(new com.shomazzapp.summerserfer.screens.GameOverScreen(this));//GameScreen(this, inputMethod));
	}

	public void setGameOverScreen(){
		setScreen(new com.shomazzapp.summerserfer.screens.GameOverScreen(this));
	}

	public void setGameScreen(){
		setScreen(new com.shomazzapp.summerserfer.screens.GameScreen(this, inputMethod));
	}

	public void changeInputMethod(){
		if (inputMethod == Constants.INPUT_METHOD_TOUCH) inputMethod = Constants.INPUT_METHOD_ACCELEROMETR;
		else inputMethod = Constants.INPUT_METHOD_TOUCH;
	}

}
