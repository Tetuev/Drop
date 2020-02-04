package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Drop extends Game { //Game базовый класс libGDX

	SpriteBatch batch; //класс для отображения спрайтов на экране
	BitmapFont font; //класс для отображения текста на экране

	@Override
	public void create() { // метод создающий игровое поле
		batch = new SpriteBatch(); //класс для рисования 2d изображений
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));//устанавливаем экран игры

	}

	@Override
	public void render() {
		super.render();
	}

	@Override //переопределение метода dispose, освобождающего ресурсы
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
	}
}
