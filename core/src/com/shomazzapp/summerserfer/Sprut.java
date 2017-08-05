package com.shomazzapp.summerserfer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static com.shomazzapp.summerserfer.Constants.SERFER_RECT_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.SERFER_RECT_WIDTH;
import static com.shomazzapp.summerserfer.Constants.SPRUT_CIRCLE_RADIUS;
import static com.shomazzapp.summerserfer.Constants.SPRUT_MAX_WAITING_TIME;
import static com.shomazzapp.summerserfer.Constants.SPRUT_MIN_WAITING_TIME;
import static com.shomazzapp.summerserfer.Constants.SPRUT_SWIM_DISTANCE;
import static com.shomazzapp.summerserfer.Constants.SPRUT_VELOCITY;
import static com.shomazzapp.summerserfer.Constants.WORLD_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;

public class Sprut implements IUpdatable, IDrowable {

    float geometryCoff;
    private static final int heightBelowWorld = 700;
    private float createdX;
    private long createdTime;
    private int waitingTime;

    private boolean attacking = false;

    private ShapeRenderer renderer;
    private com.shomazzapp.summerserfer.screens.GameScreen game;

    private Vector2 sprutPosition;
    private Vector2 velocity;
    private Vector2 destination;

    public Sprut(float x, float y, ShapeRenderer renderer, com.shomazzapp.summerserfer.screens.GameScreen game) {
        this.renderer = renderer;
        this.game = game;
        this.createdX = x;
        sprutPosition = new Vector2(x, y);
        init();
    }

    public void init() {
        this.createdTime = System.currentTimeMillis();
        velocity = new Vector2();
        waitingTime = MathUtils.random(SPRUT_MIN_WAITING_TIME, SPRUT_MAX_WAITING_TIME);

    }

    public void attack() {
        geometryCoff = (sprutPosition.y - game.getSerfer().getCenter().y)
                / (game.getSerfer().getCenter().y + heightBelowWorld);
        attacking = true;
        destination = new Vector2(
                game.getSerfer().getCenter().x + getDestinationPadding().x,
                game.getSerfer().getCenter().y + getDestinationPadding().y);
    }

    public void swim() {
        // TODO: asynchronize swim mowement
        long elapsedNanos = TimeUtils.nanoTime() - createdTime;
        float elapsedSeconds = MathUtils.nanoToSec * elapsedNanos;
        float cycles = elapsedSeconds / 2;
        float cyclePosition = cycles % 1;
        sprutPosition.x = createdX + SPRUT_SWIM_DISTANCE * MathUtils.sin(MathUtils.PI2 * cyclePosition);
    }

    @Override
    public void update(float delta) {
        if (!attacking) {
            swim();
            if (isAttackTime())
                attack();
        } else {
            //TODO: choose sprut's attack tactik
    /*        destination = new Vector2(
                    game.getSerfer().getCenter().x + getDestinationPadding().x,
                    game.getSerfer().getCenter().y + getDestinationPadding().y);*/
            Vector2 followVector = new Vector2(destination.x - sprutPosition.x, destination.y - sprutPosition.y);
            velocity.x = SPRUT_VELOCITY * followVector.x;
            velocity.y = SPRUT_VELOCITY * followVector.y;

            sprutPosition.x += delta * velocity.x;
            sprutPosition.y += delta * velocity.y;

        }
    }

    public boolean isAttacking(){return attacking;}

    public boolean isSerferCaught(){
        return (((sprutPosition.x + SPRUT_CIRCLE_RADIUS >= game.getSerfer().getCenter().x - SERFER_RECT_WIDTH / 2)
        && (sprutPosition.x - SPRUT_CIRCLE_RADIUS <= game.getSerfer().getCenter().x + SERFER_RECT_WIDTH / 2))
                &&(sprutPosition.y + SPRUT_CIRCLE_RADIUS <= game.getSerfer().getCenter().y + SERFER_RECT_HEIGHT / 2));
    }

    public Vector2 getDestinationPadding() {
        return new Vector2((game.getSerfer().getCenter().x - sprutPosition.x) / geometryCoff, -heightBelowWorld);
    }

    public boolean isAttackTime() {
        return System.currentTimeMillis() - createdTime >= waitingTime;
    }

    @Override
    public void render(float delta) {
        update(delta);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1f, 0, 0, 1f);
        renderer.circle(sprutPosition.x, sprutPosition.y, SPRUT_CIRCLE_RADIUS);
    }

    public static float getRandomX() {
        return MathUtils.random(SPRUT_CIRCLE_RADIUS + SPRUT_SWIM_DISTANCE,
                WORLD_WIDTH - SPRUT_CIRCLE_RADIUS - SPRUT_SWIM_DISTANCE);
    }

    public static float getRandomY() {
        return MathUtils.random(WORLD_HEIGHT / 2 + SPRUT_CIRCLE_RADIUS, WORLD_HEIGHT - SPRUT_CIRCLE_RADIUS * 4);
    }

    public boolean isAlive (){
        return sprutPosition.y >= 0;
    }

}
