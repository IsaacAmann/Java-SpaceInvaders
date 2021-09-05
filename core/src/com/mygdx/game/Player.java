package com.mygdx.game;
import com.badlogic.gdx.utils.TimeUtils;

public class Player
{
	public static final int STARTING_HEALTH = 5;
	public float speed;
	public EntityBox entityBox;
	public int health;	
	public float energy;
	public float energyRechargeRate = 0.0005F;
	public float standardProjectileEnergyCost = 0.1F;

       //public static final int  PROJECTILE_SIZE = 5;	
	
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
}
