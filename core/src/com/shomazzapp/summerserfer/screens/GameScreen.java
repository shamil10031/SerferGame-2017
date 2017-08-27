package com.shomazzapp.summerserfer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shomazzapp.summerserfer.Difficulty;
import com.shomazzapp.summerserfer.GameApp;
import com.shomazzapp.summerserfer.Serfer;
import com.shomazzapp.summerserfer.Sprut;

import java.util.ArrayList;
import java.util.Iterator;

import static com.shomazzapp.summerserfer.Constants.SPRUT_CIRCLE_RADIUS;
import static com.shomazzapp.summerserfer.Constants.SPRUT_CREATING_INTERWAL_TIME;
import static com.shomazzapp.summerserfer.Constants.WORLD_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;


public class GameScreen implements Screen {

    private ArrayList<Sprut> spruts;

    GameApp gameApp;
    int inputMethod;
    private Serfer serfer;
    private ShapeRenderer renderer;
    private FitViewport viewport;

    private long lastSprutCreatedTime;

    public GameScreen(GameApp gameApp, int inputMethod) {
        this.gameApp = gameApp;
        this.inputMethod = inputMethod;
    }

    @Override
    public void show() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        renderer = new ShapeRenderer();
        serfer = new Serfer(renderer, inputMethod);
        spruts = new ArrayList<Sprut>();
        lastSprutCreatedTime = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        System.out.println(serfer.getScore()+"");
        update();
        Gdx.gl.glClearColor(0, 0.8f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1,0,1,1);
        renderer.rectLine(0, 0, 0, WORLD_HEIGHT, 10);
        renderer.rectLine(WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HEIGHT, 10);
        serfer.render(delta);
        renderSpruts(delta);
        renderer.end();
    }

    public void update() {
        if (isTimeToAddSprut()){
            int g = MathUtils.random(0,2);
            switch (g){
                case 0 : createSprut(Difficulty.EASY);
                    break;
                case 1 : createSprut(Difficulty.NORMAL);
                    break;
                case 2 : createSprut(Difficulty.HARD);
                    break;
            }
        }
    }

    public void renderSpruts(float delta) {
        Iterator<Sprut> it = spruts.iterator();
        while (it.hasNext()) {
            Sprut s = it.next();
            if (s.isAlive()) s.render(delta);
            else {
                serfer.incScore(s.getScoreValue());
                it.remove();
            }
        }
        checkAttackingSpruts();
    }

    public boolean isTimeToAddSprut() {
        return (System.currentTimeMillis() - lastSprutCreatedTime) >= SPRUT_CREATING_INTERWAL_TIME;
    }

    public void createSprut(Difficulty difficulty) {
        Vector2 v = new Vector2(Sprut.getRandomX(), Sprut.getRandomY());
        if (!isPositionUnique(v)) {
            while (!isPositionUnique(v)){
                v = new Vector2(Sprut.getRandomX(), Sprut.getRandomY());
            }
        }
        spruts.add(new Sprut(v.x, v.y, renderer, this, difficulty));
        lastSprutCreatedTime = System.currentTimeMillis();
    }

    public ArrayList<Vector2> getSprutsPositions() {
        ArrayList<Vector2> positions = new ArrayList<Vector2>();
        for (Sprut sprut : spruts)
            if (!sprut.isAttacking()) positions.add(sprut.getPosition());
        return positions;
    }

    public boolean isPositionUnique(Vector2 position) {
        boolean bool = true;
        for (Vector2 pos : getSprutsPositions()) {
            if (!((position.x - SPRUT_CIRCLE_RADIUS - SPRUT_CIRCLE_RADIUS * 3 >= pos.x)
                    || (position.x + SPRUT_CIRCLE_RADIUS + SPRUT_CIRCLE_RADIUS * 3 <= pos.x)
                    || (position.y + SPRUT_CIRCLE_RADIUS + SPRUT_CIRCLE_RADIUS * 2 <= pos.y)
                    || (position.y - SPRUT_CIRCLE_RADIUS - SPRUT_CIRCLE_RADIUS * 2 >= pos.y)))
                bool = false;
        }
        return bool;
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
        renderer.dispose();
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
        gameApp.setGameOverScreen();
        gameApp.changeInputMethod();
    }

}
