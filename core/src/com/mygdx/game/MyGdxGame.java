package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game
{
	public SpriteBatch batch;
	public BitmapFont font;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;
	
	public void create()
	{
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MenuScreen(this));
	}
	
	public void render()
	{
		super.render();
	}	
	
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}



}
