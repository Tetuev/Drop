package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MainMenuScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;


    public MainMenuScreen(final Drop gam) {
        game = gam;

        camera = new OrthographicCamera();// cоздание экземпляра камеры и установка ее параметров
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "DROP GAME", 300, 250); //отображение текста на экране
        game.font.draw(game.batch, "ANY KEY TO START", 300, 200);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game)); //Проверяем было ли прикосновение к экрану и переходим на GameScreen
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
