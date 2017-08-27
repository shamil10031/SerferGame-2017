package com.shomazzapp.summerserfer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shomazzapp.summerserfer.GameApp;

import static com.shomazzapp.summerserfer.Constants.WORLD_HEIGHT;
import static com.shomazzapp.summerserfer.Constants.WORLD_WIDTH;

public class GameOverScreen implements Screen {

    GameApp gameApp;
    ShapeRenderer renderer;
    FitViewport viewport;
    BitmapFont font;
    SpriteBatch batch;

    public GameOverScreen (GameApp gameApp){
        this.gameApp = gameApp;
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
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        font.setColor(1,1,1,1);
        final GlyphLayout gameOverLayout = new GlyphLayout(font, "Game over");
        font.getData().setScale(5,5);
        font.draw(batch, "Game over", WORLD_WIDTH/2, WORLD_HEIGHT/2 + gameOverLayout.height / 2, 0, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) gameApp.setGameScreen();
        if (Gdx.input.isTouched()) gameApp.setGameScreen();
    }

    @Override
    public void resize(int width, int height) {
        font.getData().setScale(Math.min(width, height) / 480f);
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
        renderer.dispose();
    }
}
