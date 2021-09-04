package com.mygdx.game;


public class AlienProjectile
{
	public float speed;	
	public EntityBox entityBox;

	public AlienProjectile(float x, float y, int width, int height, float speed)
	{	
		entityBox = new EntityBox(x, y, width, height);
		this.speed = speed;
	}
	
	public void update()
	{
		entityBox.y -= this.speed;
			
	}
	
	public void destroy()
	{

	}
}
