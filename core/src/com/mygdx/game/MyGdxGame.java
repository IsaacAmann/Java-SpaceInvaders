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

public class MyGdxGame extends ApplicationAdapter {
	//globals
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

	private ShapeRenderer shape;	
	//Declaring variables for images
	private SpriteBatch batch;
	private Texture img;
	private Texture alienImage;
	private Texture shipImage;
	private Texture alienProjectileImage;
	private Texture shipProjectileImage;
	private Texture backgroundImage;
	private float background1Y = SCREEN_HEIGHT;
	private float background2Y = 0;
	//end image declaration

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

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		shape = new ShapeRenderer();
		//Loading images / textures
		img = new Texture("badlogic.jpg");
		alienImage = new Texture(Gdx.files.internal("alien.png"));
		shipImage = new Texture(Gdx.files.internal("playerShip.png"));
		alienProjectileImage = new Texture(Gdx.files.internal("alienProjectile.png"));
		shipProjectileImage = new Texture(Gdx.files.internal("playerProjectile.png"));
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		player1 = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 3, PLAYER_WIDTH, PLAYER_HEIGHT, 10);

		generateAlienGrid(20,10,ALIEN_SIZE,ALIEN_SIZE);
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);


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
			}
		}	
		//Throwing error when colliding with two aliens at once		
		while(playerProjectileIterator.hasNext())
		{
			tempPlayerProjectile = playerProjectileIterator.next();
			tempPlayerProjectile.update();
			boolean hasCollided = false;		
			Iterator<Alien> alienIterator = alienArray.iterator();	

			if(tempPlayerProjectile.entityBox.y > SCREEN_HEIGHT)
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
	
	//function that destroys objects marked for removal

	//This is still broken, integer stored in remove array is off by one. Size of arraylist changes
	//Use Iterator class
	private void removeEntities()
	{
		int i;
		for(i = 0; i < alienProjectileRemoveList.size(); i++)
		{
			alienProjectileArray.remove(alienProjectileRemoveList.get(i));
		}
		alienProjectileRemoveList.clear();
	
		for(i = 0; i < playerProjectileRemoveList.size(); i++)
		{
			int positionToRemove = playerProjectileRemoveList.get(i);
			//playerProjectileArray.remove(playerProjectileRemoveList.get(i));
			playerProjectileArray.remove(positionToRemove);
		}	
		playerProjectileRemoveList.clear();	
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
		batch.begin();
			batch.draw(backgroundImage, 0, background1Y);
			batch.draw(backgroundImage, 0, background2Y);
		batch.end();

		background1Y -= BACKGROUND_SPEED;
		background2Y -= BACKGROUND_SPEED;
		if(background1Y <= 0 - SCREEN_HEIGHT)
			background1Y = SCREEN_HEIGHT;
		if(background2Y <= 0 - SCREEN_HEIGHT)
			background2Y = SCREEN_HEIGHT;
	}

	@Override
	public void render () {
		gameLoop();
		ScreenUtils.clear(0, 0, 0, 1);
		drawBackground();
		
		batch.begin();
		for(Alien i : alienArray)
		{
			batch.draw(alienImage, i.entityBox.x, i.entityBox.y);
			/*
			shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.setColor(0, 1, 0, 0);
				shape.rect(i.entityBox.x, i.entityBox.y, i.entityBox.width, i.entityBox.height);	
			shape.end();	
			*/	
		}
		for(AlienProjectile i : alienProjectileArray)
		{
			batch.draw(alienProjectileImage, i.entityBox.x, i.entityBox.y);
		}

		for(PlayerProjectile i : playerProjectileArray)
		{	/*
			shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.setColor(0,0,1,0);	
				shape.rect(i.entityBox.x, i.entityBox.y, i.entityBox.width, i.entityBox.height);
			shape.end();	
			*/
			
			batch.draw(shipProjectileImage, i.entityBox.x, i.entityBox.y);	
		}
		batch.draw(shipImage, player1.entityBox.x, player1.entityBox.y);
		batch.end();
		
		//Old draw player statements
		/*
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(1, 0, 0, 0);
			shape.rect(player1.entityBox.x, player1.entityBox.y, player1.entityBox.width, player1.entityBox.height);
		shape.end();
		*/
		

		//batch.draw(img, 0, 0);
		//default test image ^^^
		//batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
}
