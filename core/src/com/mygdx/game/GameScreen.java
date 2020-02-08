package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen { //реализуем интерфейс Screen(содержит методы show/hide при потере фокуса

    final Drop game;
    OrthographicCamera camera;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGatchered;
    String dropString;



    public GameScreen (final Drop gam) {//конструктор , в который передаем объект Drop
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);// установка камеры просмотра игры

        touchPos = new Vector3();// Cоздание объекта при касании по экрану

        dropImage = new Texture("drop4.png");//загрузка текстур
        bucketImage = new Texture("vase5.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));//загрузка звука
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("seapalace.mp3"));

        rainMusic.setLooping(true);//зацикливание музыки
        rainMusic.play();

        bucket = new Rectangle();//cоздание объекта ведра
        bucket.x = 800 / 2 - 64 / 2;// задание начального положения ведра
        bucket.y = 20;
        bucket.width = 64;// задание размеров ведра
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
        spawnRaindrop(); // порождаем первую каплю

    }

    private void spawnRaindrop(){ // метод создания капли
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);// добавление в массив
        lastDropTime = TimeUtils.nanoTime();// запись текущего времени создания капли в наносекундах
    }

    @Override
    public void render (float delta) { // метод рисующий экран игры
        Gdx.gl.glClearColor(0.4f, 0, 0, 1);// очистка поля игры и закраска цветом
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch spriteBatch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgroundgame1.jpg")); //задний фон экрана меню
        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.end();

        if (dropsGatchered > 5){
            SpriteBatch spriteBatch2 = new SpriteBatch();
            Texture texture2 = new Texture(Gdx.files.internal("backgroundgame2.jpg")); //задний фон экрана меню
            spriteBatch2.begin();
            spriteBatch2.draw(texture2, 0, 0);
            spriteBatch2.end();

            if (dropsGatchered > 15) {
                SpriteBatch spriteBatch3 = new SpriteBatch();
                Texture texture3 = new Texture(Gdx.files.internal("backgroundgame3.jpg")); //задний фон экрана меню
                spriteBatch3.begin();
                spriteBatch3.draw(texture3, 0, 0);
                spriteBatch3.end();
            }

        }

        camera.update();//обновление камеры 1 раз за кадр

        game.batch.setProjectionMatrix(camera.combined);//cообщаем методу SpriteBatch что нужно использовать систему координат камеры
        game.batch.begin();
        game.font.draw(game.batch, "DROPS COUNT: " + dropsGatchered, 25, 450);
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        //game.batch.draw(lightImage, light.x, light.y);
        for (Rectangle raindrop: raindrops){//отображение капель на экране
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        if(Gdx.input.isTouched()){// метод опрашиваюший есть ли прикосновение к экрану или нажатие кнопки мыши
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);// преобразование касания координат касания экрана к координатам камеры
            camera.unproject(touchPos);// метод unproject преобразует вектор координат нажатия в вектор камеры
            bucket.x = (int) (touchPos.x -64 / 2);// изменение координат ведра в рамки координат прикосновения
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 300 * Gdx.graphics.getDeltaTime();// передвижение ведра влево/вправо при нажатии клавиш
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 300 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;// проверка того что ведро остается в пределах экрана
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();//проверка времени которое прошло после создания капли и при необходимости создавать еще каплю

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();// движение капель с заданной скоростью
            if (raindrop.y + 64 < 0) iter.remove();// удаление капли из массива при достижении края экрана
            if (raindrop.overlaps(bucket)){// метод overlaps определяет пересечение прямоугольника
                dropsGatchered++;//добавление счетчика капель
                dropSound.play();// звук и удаление упавшей капли
                iter.remove();
            }

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
    public void dispose() {// метод освобождения ресурсов , не будут обработаны сборщиком мусора
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public void show() {
        rainMusic.play();
    } //метод проигрывания музыки
}
