/**
standard projectile fired by the player
*/

package com.mygdx.game;

public class PlayerProjectile
{
	private double damage;
	public EntityBox entityBox;
	public float acceleration = 0.5F;
	public float speed = GameScreen.PROJECTILE_SPEED;		

	public PlayerProjectile(float inputX, float inputY, int inputWidth, int inputHeight)
	{
		entityBox = new EntityBox(inputX, inputY, inputWidth, inputHeight);
	}
	
	public void update()
	{
		entityBox.y += speed;
		speed += acceleration;
		
	}



}
