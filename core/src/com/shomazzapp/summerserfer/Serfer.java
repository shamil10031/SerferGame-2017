package com.shomazzapp.summerserfer;

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

public class Serfer implements IDrowable, IUpdatable {

    private Color color = new Color(0xffffffff);
    private ShapeRenderer renderer;
    private Vector2 position;

    private int maxX = 17;

    private int inputMethod = INPUT_METHOD_ACCELEROMETR;

    public Serfer(ShapeRenderer renderer) {
        this.renderer = renderer;
        position = new Vector2(WORLD_WIDTH / 2 - SERFER_RECT_WIDTH / 2, SERFER_RECT_HEIGHT / 2);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void move(float x) {
        if (x > maxX) x = maxX;
        if (x < -maxX) x = -maxX;
        position.x += x;
        System.out.println(" " + x);
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
        float accelerometerInput = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) accelerometerInput = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) accelerometerInput = -1;
        if (inputMethod == INPUT_METHOD_ACCELEROMETR)
            //TODO : make accelerometr more sensetive and add speed limit
            accelerometerInput = -Gdx.input.getAccelerometerX()
                    / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);
        if (inputMethod == INPUT_METHOD_TOUCH){}

        move(delta * accelerometerInput * (float) SERFER_VELOCITY);

        if (position.x < 0) position.x = 0;
        if (position.x >= WORLD_WIDTH - SERFER_RECT_WIDTH)
            position.x = WORLD_WIDTH - SERFER_RECT_WIDTH;
    }

}
