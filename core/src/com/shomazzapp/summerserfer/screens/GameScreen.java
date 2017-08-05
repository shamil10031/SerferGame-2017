package com.shomazzapp.summerserfer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shomazzapp.summerserfer.Game;
import com.shomazzapp.summerserfer.Serfer;
import com.shomazzapp.summerserfer.Sprut;

import java.util.ArrayList;
import java.util.Iterator;

import static com.shomazzapp.summerserfer.Constants.SPRUT_CREATING_INTERWAL_TIME;
import static com.shomazzapp.summerserfer.Constants.WORLD_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;


public class GameScreen implements Screen {

    private ArrayList<Sprut> spruts;

    Game game;
    private SpriteBatch batch;
    private Serfer serfer;
    private ShapeRenderer renderer;
    private FitViewport viewport;

    private long lastSprutCreatedTime;

    public GameScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        serfer = new Serfer(renderer);
        spruts = new ArrayList<Sprut>();
        createSprut();
    }

    @Override
    public void render(float delta) {
        if (isTimeToAddSprut()) createSprut();
        Gdx.gl.glClearColor(0, 0.8f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.rectLine(0, 0, 0, WORLD_HEIGHT, 10);
        renderer.rectLine(WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HEIGHT, 10);
        serfer.render(delta);
        renderSpruts(delta);
        renderer.end();
    }

    public void renderSpruts(float delta) {
        /*for (Sprut s : spruts) {
            if (s.isAlive()) s.render(delta);
            else spruts.remove(s);
        }*/
        Iterator<Sprut> it = spruts.iterator();
        while (it.hasNext()) {
            Sprut s = it.next();
            if (s.isAlive()) s.render(delta);
            else it.remove();
        }
        checkAttackingSpruts();
    }

    public boolean isTimeToAddSprut() {
        return (System.currentTimeMillis() - lastSprutCreatedTime) >= SPRUT_CREATING_INTERWAL_TIME;
    }

    public void createSprut() {
        //TODO: chek coordinates of other spruts
        spruts.add(new Sprut(Sprut.getRandomX(), Sprut.getRandomY(), renderer, this));
        lastSprutCreatedTime = System.currentTimeMillis();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public Serfer getSerfer() {
        return serfer;
    }

    public void checkAttackingSpruts() {
        for (Sprut s : spruts) {
            if (s.isAttacking()) {
                if (s.isSerferCaught()) gameOver();
            }
        }
    }

    public void gameOver() {
        game.setGameOverScreen();
    }

}
