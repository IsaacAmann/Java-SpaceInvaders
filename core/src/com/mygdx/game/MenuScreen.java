package com.mygdx.game;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen implements Screen
{
	//Reference to the game object
	final MyGdxGame game;
	
	private int screenWidth;
	private int screenHeight;	
	
	OrthographicCamera camera;

	//Dimensions of the startbutton image
	final static int BUTTON_WIDTH = 200;
	final static int BUTTON_HEIGHT = 100;
	
	//Dimensions of the title image
	final static int TITLE_LENGTH = 300;
	final static int TITLE_HEIGHT = 100;
		
	private float buttonX;
	private float buttonY;

	private float titleX;
	private float titleY;	

	//Texture delarations
	private Texture startButtonImage;
	private Texture titleImage;

	private float titleLength;
	
	public MenuScreen(final MyGdxGame game)
	{
		this.game = game;
		
		screenWidth = game.SCREEN_WIDTH;
		screenHeight = game.SCREEN_HEIGHT;		
		
		//Loading images
		//alienImage = new Texture(Gdx.files.internal("alien.png"));
		startButtonImage = new Texture(Gdx.files.internal("startButton.png"));	
		titleImage = new Texture(Gdx.files.internal("titleImage.png"));	
		//End loading images
		
		//Camera setup
		camera = new OrthographicCamera();
		Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
		camera.setToOrtho(false, screenWidth, screenHeight);
		//End camera setup

		System.out.println(titleLength);
		titleX = screenWidth/2 - TITLE_LENGTH/2;
		titleY = screenHeight - 350;
		buttonX = screenWidth/2 - BUTTON_WIDTH/2;
		buttonY = 250;
	}
	
	private void drawTitle()
	{
		game.batch.draw(titleImage, titleX, titleY);
	}
		
	private void drawStartButton()
	{
		game.batch.draw(startButtonImage, buttonX, buttonY);	
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
		/*	
		System.out.println("boxPostitionX: " + ((screenWidth / 2) - (BUTTON_WIDTH/2) ));
		System.out.println("boxPositionY: " + buttonY);
		System.out.println("Screenwidth: " + screenWidth);
		System.out.println("Screenheight: " + screenHeight);
		System.out.println("Boxwidth: " + BUTTON_WIDTH);
		System.out.println("Screenwidth/2: " + screenWidth/2);
		*/		

		if
		(
			x > buttonX &&
			x < buttonX + BUTTON_WIDTH &&
			screenHeight - y < buttonY + BUTTON_HEIGHT &&
			screenHeight - y > buttonY
		)
		{	
			game.setScreen(new GameScreen(game));

		}
	}
	
	@Override
	public void render(float delta)
	{
		ScreenUtils.clear(0,0,0,1);
		game.batch.begin();
			drawTitle();
			drawStartButton();

			//Draws mouse cords on the screen for debugging
			//game.font.draw(game.batch,"X: " + Gdx.input.getX() +"Y: " + (screenHeight - Gdx.input.getY()), 400, 400);
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
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		camera.setToOrtho(false);
		game.batch.setProjectionMatrix(camera.combined);
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
