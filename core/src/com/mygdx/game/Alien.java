/**
standard enemy class
*/

package com.mygdx.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

public class Alien
{
	private boolean direction = true;
	public float speed;	
	public EntityBox entityBox;	
	private Random rand = new Random();
	public int fireChance = 1000;
	
	public Alien(float inputX, float inputY, int inputWidth, int inputHeight, float speed)
	{
		entityBox = new EntityBox(inputX, inputY, inputWidth, inputHeight);
		this.speed = speed;
		
	}
	
	public void fireProjectile()
	{
		AlienProjectile tempProjectile = new AlienProjectile(entityBox.x, entityBox.y, entityBox.width/2, entityBox.height/2, MyGdxGame.PROJECTILE_SPEED);
		MyGdxGame.alienProjectileArray.add(tempProjectile);			
	}
	
	public void alienMovement()
	{
		if(direction == true && entityBox.x <= MyGdxGame.SCREEN_WIDTH)
		{
			entityBox.x += speed;
		}else 
		{
			direction = false;
		}
		
		if(direction == false && entityBox.x >= 0) 
		{
			entityBox.x -= speed;
		}else
		{
			direction = true;
		}
		if(rand.nextInt(fireChance) == fireChance / 2)
		{	
			fireProjectile();
		}
	}
	
	public void update()
	{
		alienMovement();		
	}
	
	
}
