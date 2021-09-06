package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen
{
	final MyGdxGame game;
	
	OrthographicCamera camera;
	private ShapeRenderer shape;
	final static int BUTTON_WIDTH = 200;
	final static int BUTTON_HEIGHT = 150;	
	private float buttonX;
	private float buttonY;

	public MenuScreen(final MyGdxGame game)
	{
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		shape = new ShapeRenderer();
		buttonX = game.SCREEN_WIDTH/2 - BUTTON_WIDTH/2;
		buttonY = game.SCREEN_HEIGHT/4;
	}
	
	private void drawStartButton()
	{
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(0,0,1,0);
			shape.rect(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
		shape.end();
	}

	@Override
	public void render(float delta)
	{
		drawStartButton();
		checkInput();	
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
