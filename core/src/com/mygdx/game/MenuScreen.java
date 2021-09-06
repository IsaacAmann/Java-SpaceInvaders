package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen
{
	final MyGdxGame game;
	
	OrthographicCamera camera;

	public MenuScreen(final MyGdxGame game)
	{
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
	}
	
	@Override
	public void render(float delta)
	{

	}

	@Override
	public void dispose()
	{
		
	}
	
	@Override
	public void resize(int width, int height)
	{

	}
	
	@Override
	public void show()
	{

	}

	@Override
	public void hide()
	{

	}
	
	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{
	
	}







	
}
