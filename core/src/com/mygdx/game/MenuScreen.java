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
	
	OrthographicCamera camera;

	//Dimensions of the startbutton image
	final static int BUTTON_WIDTH = 200;
	final static int BUTTON_HEIGHT = 100;
	
	private float buttonX;
	private float buttonY;

	private float titleX;
	private float titleY;	

	//creating seperate objects for each line of text
	//rendering bugs out otherwise
	private BitmapFont titleFont;
	private BitmapFont startFont;

	//Texture delarations
	private Texture startButtonImage;

	private float titleLength;
	
	public MenuScreen(final MyGdxGame game)
	{
		this.game = game;
		
		titleFont = new BitmapFont(Gdx.files.internal("title.fnt"));
		startFont = new BitmapFont(Gdx.files.internal("title.fnt"));		
		
		//Loading images
		//alienImage = new Texture(Gdx.files.internal("alien.png"));
		startButtonImage = new Texture(Gdx.files.internal("startButton.png"));		
		//End loading images

		camera = new OrthographicCamera();
		//Gdx.graphics.setWindowedMode(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

		titleX = game.SCREEN_WIDTH/2;
		titleY = game.SCREEN_HEIGHT - 100;
		buttonX = game.SCREEN_WIDTH/2 - BUTTON_WIDTH/2;
		buttonY = game.SCREEN_HEIGHT/5;
		titleLength = getStringDrawLength(titleFont, "SpaceInvaders");
	}

	private float getStringDrawLength(BitmapFont font, String text)
	{
		GlyphLayout glyph = new GlyphLayout(font, text);
		
		return glyph.width;
	}
	
	private void drawTitle()
	{
		//Had to adjust position manually, not sure what is happening with that
		titleFont.draw(game.batch,"Space Invaders", (game.SCREEN_WIDTH - titleLength)/2, titleY);
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
		System.out.println("boxPostitionX: " + ((game.SCREEN_WIDTH / 2) - (BUTTON_WIDTH/2) ));
		System.out.println("boxPositionY: " + buttonY);
		System.out.println("Screenwidth: " + game.SCREEN_WIDTH);
		System.out.println("Screenheight: " + game.SCREEN_HEIGHT);
		System.out.println("Boxwidth: " + BUTTON_WIDTH);
		System.out.println("Screenwidth/2: " + game.SCREEN_WIDTH/2);
		if
		(
			x > buttonX &&
			x < buttonX + BUTTON_WIDTH &&
			game.SCREEN_HEIGHT - y > buttonY + BUTTON_HEIGHT &&
			game.SCREEN_HEIGHT - y < buttonY
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
