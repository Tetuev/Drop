package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


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
        Gdx.gl.glClearColor(0, 0.4f, 0, 1); //очистка экрана
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        SpriteBatch spriteBatch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgroundmenu.jpg")); //задний фон экрана меню
        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.end();

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "DROP GAME", 370, 250); //отображение текста на экране
        game.font.draw(game.batch, "ANY KEY TO START", 350, 200);
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
