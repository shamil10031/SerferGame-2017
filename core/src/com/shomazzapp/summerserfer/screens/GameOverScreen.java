package com.shomazzapp.summerserfer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shomazzapp.summerserfer.Game;

import static com.shomazzapp.summerserfer.Constants.WORLD_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;

public class GameOverScreen implements Screen {

    Game game;
    ShapeRenderer renderer;
    FitViewport viewport;
    BitmapFont font;
    SpriteBatch batch;

    public GameOverScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "GAME OVER", viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) game.setGameScreen();
        if (Gdx.input.isTouched()) game.setGameScreen();
    }

    @Override
    public void resize(int width, int height) {
        font.getData().setScale(Math.min(width, height) / 480f);
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
        renderer.dispose();
    }
}
