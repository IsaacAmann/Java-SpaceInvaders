package com.mygdx.game;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player
{
	final static int PLAYER_WIDTH = 60;
	final static int PLAYER_HEIGHT = 60;
	final static int SHIELDED_HEIGHT = 90;	
	final static float SHIELD_ENERGY_COST = 0.1F;

	public static final int STARTING_HEALTH = 5;
	public float speed;
	public EntityBox entityBox;
	public int health;	
	public float energy;
	public float energyRechargeRate = 0.005F;
	public float standardProjectileEnergyCost = 0.1F;
	public int currentState = 1;
       //public static final int  PROJECTILE_SIZE = 5;	
	//Textures
	


	
	//Time handled in milli seconds
	public long timeLastFired;
	public long FIRE_COOLDOWN = 200;	
	
	public Player(float x, float y, int width, int height, float speed) 
	{
		entityBox = new EntityBox(x, y, width, height);
		this.speed = speed;
		timeLastFired = TimeUtils.millis();	
		health = STARTING_HEALTH;
		energy = 1.0F;	
	}
	
	//Acts like update function, place anything to run each frame here
	public void moveFromKeyboard()
	{
		if(MyGdxGame.playerInput.up && entityBox.y < MyGdxGame.SCREEN_HEIGHT / 3)
			entityBox.y += this.speed;
		if(MyGdxGame.playerInput.down && entityBox.y > 0 + MyGdxGame.BOTTOM_INTERFACE_HEIGHT)
			entityBox.y -= this.speed;
		if(MyGdxGame.playerInput.left && entityBox.x > 0)
			entityBox.x -= this.speed;
		if(MyGdxGame.playerInput.right && entityBox.x < MyGdxGame.SCREEN_WIDTH - entityBox.width)
			entityBox.x += this.speed;
		if(MyGdxGame.playerInput.fire)
			fire(entityBox.x, entityBox.y);
		if(MyGdxGame.playerInput.shield && energy - SHIELD_ENERGY_COST > 0)
		{
			currentState = 2;
		}
		else
		{
			currentState = 1;
		}
		stateBehavior();
		manageEnergy();
	}
	//alters the energy variable based on the recharge rate
	public void manageEnergy()
	{
		if(energy < 1.0F)
		{
			if(energy + energyRechargeRate < 1.0F)
			{
				energy += energyRechargeRate;
			}
			else
			{
				energy = 1.0F;
			}
		}	
	}
	public void fire(float x, float y)
	{
		if(TimeUtils.timeSinceMillis(timeLastFired) >= FIRE_COOLDOWN && energy - standardProjectileEnergyCost >= 0)
		{
			PlayerProjectile temp = new PlayerProjectile(x, y, MyGdxGame.PLAYER_PROJECTILE_WIDTH, MyGdxGame.PLAYER_PROJECTILE_HEIGHT);
			MyGdxGame.playerProjectileArray.add(temp); 
			timeLastFired = TimeUtils.millis();
			energy -= standardProjectileEnergyCost;
		}
	}

	public void stateBehavior()
	{
		switch(currentState)
		{
			case 1:
				entityBox.width = PLAYER_WIDTH;
				entityBox.height = PLAYER_HEIGHT;
			break;
			//shielded state
			case 2:
				entityBox.width = PLAYER_WIDTH;
				entityBox.height = SHIELDED_HEIGHT;
				energy -= SHIELD_ENERGY_COST;
			break;
			
			default:
				entityBox.width = PLAYER_WIDTH;
				entityBox.height = PLAYER_HEIGHT;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		switch(currentState)
		{
			//case for drawing regular ship sprite
			case 1: 
				batch.draw(MyGdxGame.shipImage, entityBox.x, entityBox.y);
			break;
			//case for when shield is active
			case 2:
				batch.draw(MyGdxGame.shipShieldedImage, entityBox.x, entityBox.y);
			break;			

			default:
				batch.draw(MyGdxGame.shipImage, entityBox.x, entityBox.y);

		}
	}	
}
