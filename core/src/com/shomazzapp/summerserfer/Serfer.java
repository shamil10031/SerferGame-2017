package com.shomazzapp.summerserfer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.shomazzapp.summerserfer.Constants.INPUT_METHOD_ACCELEROMETR;
import static com.shomazzapp.summerserfer.Constants.INPUT_METHOD_TOUCH;
import static com.shomazzapp.summerserfer.Constants.SERFER_RECT_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.SERFER_RECT_WIDTH;
import static com.shomazzapp.summerserfer.Constants.SERFER_VELOCITY;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;

//TODO: fix bug with touch's pointers

public class Serfer implements IDrowable, IUpdatable {

    private Color color = new Color(0xffffffff);
    private ShapeRenderer renderer;
    private Vector2 position;

    private int maxX = 17;
    private float inputCoff = 0f;
    private int inputMethod;
    private int score = 0;

    public Serfer(ShapeRenderer renderer, int inputMethod) {
        this.renderer = renderer;
        this.inputMethod = inputMethod;
        position = new Vector2(WORLD_WIDTH / 2 - SERFER_RECT_WIDTH / 2, SERFER_RECT_HEIGHT / 2);
    }

    public void move(Vector2 velosity) {
        position.x += velosity.x;
        inputCoff = 0f;
    }

    public void render(float delta) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(position.x, position.y, SERFER_RECT_WIDTH, SERFER_RECT_HEIGHT);
        update(delta);
    }

    public Vector2 getCenter() {
        return new Vector2(position.x + SERFER_RECT_WIDTH / 2, position.y + SERFER_RECT_HEIGHT / 2);
    }

    public void update(float delta) {
        updateInputCoff();
        Vector2 velocity = new Vector2(delta * inputCoff * (float) SERFER_VELOCITY, 0);
        velocity.clamp(0, maxX);
        move(velocity);

        if (position.x < 0) position.x = 0;
        if (position.x >= WORLD_WIDTH - SERFER_RECT_WIDTH)
            position.x = WORLD_WIDTH - SERFER_RECT_WIDTH;
    }

    public void updateInputCoff() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) inputCoff = 1f;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) inputCoff = -1;
        }
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (inputMethod == INPUT_METHOD_ACCELEROMETR) {
                inputCoff = -Gdx.input.getAccelerometerX()
                        / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);
                if (inputCoff > -0.14 && inputCoff < 0.14) inputCoff = 0;
            }
            if (inputMethod == INPUT_METHOD_TOUCH && Gdx.input.isTouched()) {
                if (Gdx.input.getX() >= WORLD_WIDTH/2) inputCoff = 1f;
                else inputCoff = -1f;
            }
        }
    }

    public void incScore (int sc){
        score += sc;
    }

    public int getScore (){
        return score;
    }

}
