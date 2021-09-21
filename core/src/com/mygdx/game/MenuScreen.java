package com.mygdx.game;

import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen implements Screen
{
	final MyGdxGame game;
	
	OrthographicCamera camera;
	private ShapeRenderer shape;
	final static int BUTTON_WIDTH = 200;
	final static int BUTTON_HEIGHT = 100;	
	private float buttonX;
	private float buttonY;
	private float titleX;
	private float titleY;
	private BitmapFont titleFont;
	private float titleLength;
	
	public MenuScreen(final MyGdxGame game)
	{
		
		this.game = game;
		titleFont = new BitmapFont(Gdx.files.internal("title.fnt"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		Gdx.graphics.setWindowedMode(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		shape = new ShapeRenderer();
		titleX = game.SCREEN_WIDTH/2;
		titleY = game.SCREEN_HEIGHT - 100;
		buttonX = game.SCREEN_WIDTH/2 - (BUTTON_WIDTH/2);
		buttonY = game.SCREEN_HEIGHT/5;
		titleLength = getStringDrawLength(titleFont, "SpaceInvaders");
		//buttonX = 200;
		//buttonY = 0;
	}

	private float getStringDrawLength(BitmapFont font, String text)
	{
		GlyphLayout glyph = new GlyphLayout(font, text);
		
		return glyph.width;
	}
	
	private void drawTitle()
	{
		//Had to adjust position manually, not sure what is happening with that
		titleFont.draw(game.batch,"Space Invaders", (game.SCREEN_WIDTH - titleLength + 75)/2, titleY);
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
			drawTitle();
			//Draws mouse cords on the screen for debugging
			//game.font.draw(game.batch,"X: " + Gdx.input.getX() +"Y: " + (game.SCREEN_HEIGHT - Gdx.input.getY()), 400, 400);
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
