package com.mygdx.game;

import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;


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
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		Gdx.graphics.setWindowedMode(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		shape = new ShapeRenderer();
		buttonX = game.SCREEN_WIDTH/2 - (BUTTON_WIDTH/2);
		buttonY = game.SCREEN_HEIGHT/5;
		//buttonX = 200;
		//buttonY = 0;
	}
	
	private void drawStartButton()
	{
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(0,0,1,0);
			shape.rect(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
		shape.end();
	}
	
	private void checkInput()
	{
		if(Gdx.input.isTouched())
		{
			checkStartButton(Gdx.input.getX(),Gdx.input.getY());
		}
	}	
	
	private void checkStartButton(int x, int y)
	{	
		System.out.println("boxPostition: " + ((game.SCREEN_WIDTH / 2) - (BUTTON_WIDTH/2) ));
		System.out.println("Screenwidth: " + game.SCREEN_WIDTH);
		System.out.println("Screenheight: " + game.SCREEN_HEIGHT);
		System.out.println("Boxwidth: " + BUTTON_WIDTH);
		System.out.println("Screenwidth/2: " + game.SCREEN_WIDTH/2);
		if
		(
			x > buttonX &&
			x < buttonX + BUTTON_WIDTH &&
			game.SCREEN_HEIGHT - y > buttonY &&
			game.SCREEN_HEIGHT - y < buttonY + BUTTON_HEIGHT
		)
		{	
			game.setScreen(new GameScreen(game));

		}
	}
	
	@Override
	public void render(float delta)
	{
		ScreenUtils.clear(0,0,0,1);
		drawStartButton();
		game.batch.begin();
			game.font.draw(game.batch,"X: " + Gdx.input.getX() +"Y: " + (game.SCREEN_HEIGHT - Gdx.input.getY()), 400, 400);
		game.batch.end();
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
