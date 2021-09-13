package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	//globals
	final MyGdxGame game;

	final static int SCREEN_HEIGHT = 800;
	final static int SCREEN_WIDTH = 800;	
	final static float ALIEN_SPEED = 5;
	final static float PROJECTILE_SPEED = 2;
	final static int PLAYER_PROJECTILE_WIDTH = 8;
	final static int PLAYER_PROJECTILE_HEIGHT = 25;
	final static int ALIEN_PROJECTILE_WIDTH = 8;
	final static int ALIEN_PROJECTILE_HEIGHT = 25;
	final static int PLAYER_WIDTH = 60;
	final static int PLAYER_HEIGHT = 60;	
	final static float BACKGROUND_SPEED = 1;	
	final static int ALIEN_SIZE = 20;	
	final static int BOTTOM_INTERFACE_HEIGHT = 40;
	
	private ShapeRenderer shape;	
	BitmapFont font;
	
	//Declaring variables for images
	public static SpriteBatch batch;
	public static Texture img;
	public static Texture alienImage;
	public static Texture shipImage;
	public static Texture alienProjectileImage;
	public static Texture shipProjectileImage;
	public static Texture backgroundImage;
	public static Texture bottomInterfaceImage;	
	public static Texture shipShieldedImage;	
	//end image declarations
	
	//positions for scrolling background image
	private float background1Y = SCREEN_HEIGHT;
	private float background2Y = 0;

	private OrthographicCamera camera;
	//arraylists to store instances of objects
	private static ArrayList<Alien> alienArray = new ArrayList<Alien>();
	private static Iterator<Alien> alienIterator = alienArray.iterator();

	public static ArrayList<AlienProjectile> alienProjectileArray = new ArrayList<AlienProjectile>();

	public static ArrayList<PlayerProjectile> playerProjectileArray = new ArrayList<PlayerProjectile>();
	private Player player1;
	
	//arraylists to store object positions to remove
	private static ArrayList<Integer> alienRemoveList = new ArrayList<Integer>();
	private static ArrayList<Integer> alienProjectileRemoveList = new ArrayList<Integer>();
	private static ArrayList<Integer> playerProjectileRemoveList = new ArrayList<Integer>();

	public GameScreen(final MyGdxGame game) {
		
		this.game = game;
		//batch = new SpriteBatch();
		camera = new OrthographicCamera();
		Gdx.graphics.setWindowedMode(800,800);
		shape = new ShapeRenderer();
		//Loading images / textures
		img = new Texture("badlogic.jpg");
		alienImage = new Texture(Gdx.files.internal("alien.png"));
		shipImage = new Texture(Gdx.files.internal("playerShip.png"));
		shipShieldedImage = new Texture(Gdx.files.internal("playerShipShielded.png"));
		alienProjectileImage = new Texture(Gdx.files.internal("alienProjectile.png"));
		shipProjectileImage = new Texture(Gdx.files.internal("playerProjectile.png"));
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		bottomInterfaceImage = new Texture(Gdx.files.internal("bottomInterface.png"));
		font = new BitmapFont();
		player1 = new Player(game.SCREEN_WIDTH / 2, game.SCREEN_HEIGHT / 3, PLAYER_WIDTH, PLAYER_HEIGHT, 10);
		
		generateAlienGrid(20,10,ALIEN_SIZE,ALIEN_SIZE);
		camera.setToOrtho(false,game.SCREEN_WIDTH,game.SCREEN_HEIGHT);


		//Alien testAlien = new Alien(4,4,4,4,4);	
		//alienArray.add(testAlien);
		
		
	}
	
	//public boolean isColliding(

	//static class to store player inputs 
	static class playerInput
	{
		static boolean up = false;
		static boolean down = false;
		static boolean left = false;
		static boolean right = false;
		static boolean fire = false;
		static boolean shield = false;
	}
	//controls
	private void manageInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.W)) 
		{
			playerInput.up = true;
		}else 
		{
			playerInput.up = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			playerInput.down = true;
		}else
		{
			playerInput.down = false;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			playerInput.left = true;
		}
		else
		{
			playerInput.left = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			playerInput.right = true;
		}
		else
		{
			playerInput.right = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			playerInput.fire = true;
		}
		else
		{
			playerInput.fire = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E))
		{
			playerInput.shield = true;
		}	
		else
		{
			playerInput.shield = false;
		}
			
	}	


	//Rendering / game loop stuff
 	private void gameLoop()
	{
		manageInput();
		int i;
		for(i = 0; i < alienArray.size(); i++)
		{
			alienArray.get(i).update();
		}
		player1.moveFromKeyboard();
		manageProjectiles();
		//removeEntities();
	}	
	
	private boolean isColliding(EntityBox box1, EntityBox box2)
	{
		if
		(
			box1.x < box2.x + box2.width &&
			box1.x + box1.width > box2.x &&
		 	box1.y < box2.y + box2.height &&
			box1.y + box1.height > box2.y
		)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void manageProjectiles()
	{
		AlienProjectile tempAlienProjectile;
		PlayerProjectile tempPlayerProjectile;
		Alien tempAlien;
		Iterator<PlayerProjectile> playerProjectileIterator = playerProjectileArray.iterator();
		Iterator<AlienProjectile> alienProjectileIterator = alienProjectileArray.iterator();
			
		while(alienProjectileIterator.hasNext())
		{
			tempAlienProjectile = alienProjectileIterator.next();
			tempAlienProjectile.update();
			boolean hasCollided = false;
			if(tempAlienProjectile.entityBox.y < 0)
			{
				alienProjectileIterator.remove();
				hasCollided = true;
			}
			if(isColliding(tempAlienProjectile.entityBox, player1.entityBox) && !hasCollided)
			{
				alienProjectileIterator.remove();
				hasCollided = true;
				if(player1.currentState != 2)
					player1.health -= 1;
			}
		}	
		//Throwing error when colliding with two aliens at once		
		while(playerProjectileIterator.hasNext())
		{
			tempPlayerProjectile = playerProjectileIterator.next();
			tempPlayerProjectile.update();
			boolean hasCollided = false;		
			Iterator<Alien> alienIterator = alienArray.iterator();	

			if(tempPlayerProjectile.entityBox.y > game.SCREEN_HEIGHT)
			{
				playerProjectileIterator.remove();
				hasCollided = true;
			}
			while(alienIterator.hasNext() && !hasCollided)
			{
				
				tempAlien = alienIterator.next();
				if(isColliding(tempAlien.entityBox, tempPlayerProjectile.entityBox))
				{
					playerProjectileIterator.remove();
					alienIterator.remove();
					hasCollided = true;
				}	
			}	
		}
		
		/*
		for(i = 0; i < alienProjectileArray.size(); i++)
		{
			AlienProjectile temp = alienProjectileArray.get(i);
			temp.update();
			if(temp.entityBox.y < 0)
			{
				alienProjectileRemoveList.add(i);
			}
		}
		*/
		
		/*
		for(i = 0; i < playerProjectileArray.size(); i++)
		{
			PlayerProjectile temp = playerProjectileArray.get(i);
			temp.update();
			if(temp.entityBox.y > SCREEN_HEIGHT-150)
			{
				playerProjectileRemoveList.add(i);
			}
		}
		*/
	}
		
	private void generateAlienGrid(int rows, int columns, int height, int width)
	{
		int i,g;
		int buffer = 20;
		Alien tempAlien;
		for(i=0; i<rows; i++)
		{
			for(g=0; g<columns; g++)
			{
				tempAlien = new Alien(i*width + (buffer*i),SCREEN_HEIGHT - height - g*height - (buffer*g),width,height, ALIEN_SPEED);
				alienArray.add(tempAlien);
			}
		}
	}

	private void drawBackground()
	{
		game.batch.begin();
			game.batch.draw(backgroundImage, 0, background1Y);
			game.batch.draw(backgroundImage, 0, background2Y);
		game.batch.end();

		background1Y -= BACKGROUND_SPEED;
		background2Y -= BACKGROUND_SPEED;
		if(background1Y <= 0 - game.SCREEN_HEIGHT)
			background1Y = game.SCREEN_HEIGHT;
		if(background2Y <= 0 - game.SCREEN_HEIGHT)
			background2Y = game.SCREEN_HEIGHT;
	}

	@Override
	public void render (float delta) {
		gameLoop();
		ScreenUtils.clear(0, 0, 0, 1);
		drawBackground();
		
		game.batch.begin();
		for(Alien i : alienArray)
		{
			game.batch.draw(alienImage, i.entityBox.x, i.entityBox.y);
			/*
			shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.setColor(0, 1, 0, 0);
				shape.rect(i.entityBox.x, i.entityBox.y, i.entityBox.width, i.entityBox.height);	
			shape.end();	
			*/	
		}
		for(AlienProjectile i : alienProjectileArray)
		{
			game.batch.draw(alienProjectileImage, i.entityBox.x, i.entityBox.y);
		}

		for(PlayerProjectile i : playerProjectileArray)
		{	/*
			shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.setColor(0,0,1,0);	
				shape.rect(i.entityBox.x, i.entityBox.y, i.entityBox.width, i.entityBox.height);
			shape.end();	
			*/
			
			game.batch.draw(shipProjectileImage, i.entityBox.x, i.entityBox.y);	
		}
		//batch.draw(shipImage, player1.entityBox.x, player1.entityBox.y);
		player1.draw(game.batch);
		game.batch.draw(bottomInterfaceImage,0,0);
		game.font.draw(game.batch, "Ship Health: " + player1.health,5,30);
		game.batch.end();
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(0,1,0,0);
			shape.rect(700,2,player1.energy * 98, 36);
		shape.end();
		
		//Old draw player statements
		/*
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(1, 0, 0, 0);
			shape.rect(player1.entityBox.x, player1.entityBox.y, player1.entityBox.width, player1.entityBox.height);
		shape.end();
		*/
		

		//game.batch.draw(img, 0, 0);
		//default test image ^^^
		//batch.end();
	}
	
	@Override
	public void dispose () {
		img.dispose();
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
