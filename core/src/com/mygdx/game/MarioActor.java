package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MarioActor extends Actor {
	private Vector2 desiredPosition = new Vector2();
	private Vector2 velocity = new Vector2();
	private boolean desiredPositionAdjusted = false;
	private HashMap<String, Force> appliedForces = new HashMap<String, Force>();
	private TextureRegion activeSprite;
	
	private boolean grounded = false;
	private boolean isPawn = false;
	private boolean isVisible = true;
	private boolean isInView = true;

	private float movementForce = 6f;
	private float movementAccl = .36f;

	private float jumpForce = 9f;
	private float jumpInitialForce = 8.4f;
	private float jumpAcceleration = 6f;
	private float jumpMaxTime = .5f;
	private float jumpElapsedTime = 0f;
	private float jumpMovementCoefficent = .4f;
	
	private boolean applyJumpForce = false;	
	private boolean isMovingRight = false;
	private boolean isMovingLeft = false;
	private boolean isJumping = false;
	
	public MarioActor() {
		super();
		loadAssets();
	}
	
	public void applyForce(String forceName, Force force) {
		appliedForces.put(forceName, force);
	}
	
	public void applyForce(String forceName, float maxVelocityX, float maxVelocityY, float acclX, float acclY) {
		appliedForces.put(forceName, new Force(maxVelocityX, maxVelocityY, acclX, acclY));
	}
	
	public float getDesiredPositionX() {
		return desiredPosition.x;
	}
	
	public void setDesiredPositionX(float xPos) {
		desiredPositionAdjusted = true;
		this.desiredPosition.x = xPos;
	}
	
	public void setDesiredPosition(float xPos, float yPos) {
		desiredPositionAdjusted = true;
		this.desiredPosition.x = xPos;
		this.desiredPosition.y = yPos;
	}
	
	public float getDesiredPositionY() {
		return desiredPosition.y;
	}
	
	public void setDesiredPositionY(float yPos) {
		desiredPositionAdjusted = true;
		this.desiredPosition.y = yPos;
	}
	
	public boolean isGrounded() {
		return grounded;
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
		
		if (!applyJumpForce && grounded) {
			isJumping = false;	
		}
	}
	
	public void commitDesiredPosition() {
		this.setPosition(desiredPosition.x, desiredPosition.y);
	}
	
	public boolean isMovingRight() {
		return isMovingRight;
	}

	public void setMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}

	public boolean isMovingLeft() {
		return isMovingLeft;
	}

	public void setMovingLeft(boolean isMovingLeft) {
		this.isMovingLeft = isMovingLeft;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		if (isJumping && grounded) {
			this.isJumping = true;
			this.applyJumpForce = true;
			this.grounded = false;
			this.jumpElapsedTime = 0;
		} else if (!isJumping) {
			this.applyJumpForce = false;
			this.jumpElapsedTime = 0;
		}
	}
		
	@Override
	public void draw(Batch batch, float parentAlpha) {
	}
	
	public void update() {
		float movementCoefficent = 1;
		
		if (!grounded) {
			movementCoefficent = jumpMovementCoefficent;
		}
		
		if (isMovingRight) {
			applyForce("movement", movementForce, 0, movementAccl * movementCoefficent, 0);
		}
		
		if (isMovingLeft) {
			applyForce("movement", -movementForce, 0, -movementAccl * movementCoefficent, 0);
		}
		
		if (applyJumpForce) {
			if (jumpElapsedTime == 0) {
				applyForce("jump", 0, jumpForce, 0, jumpInitialForce);
			} else {
				applyForce("jump", 0, jumpForce, 0, jumpAcceleration);
			}
			jumpElapsedTime += Gdx.graphics.getDeltaTime();
			if (jumpElapsedTime >= jumpMaxTime) {
				applyJumpForce = false;
				jumpElapsedTime = 0;			
			}
		}
		
		Vector2 maxVelocity = new Vector2 (0, 0);
		Vector2 minVelocity = new Vector2 (0, 0);
		
		for (Force force : appliedForces.values()) {
			maxVelocity.x = Math.max(force.getMaxVelocityX(), maxVelocity.x);
			maxVelocity.y = Math.max(force.getMaxVelocityY(), maxVelocity.y);
			minVelocity.x = Math.min(force.getMaxVelocityX(), minVelocity.x);
			minVelocity.y = Math.min(force.getMaxVelocityY(), minVelocity.y);
		}
		
		for (Force force : appliedForces.values()) {
			if (force.getMaxVelocityX() > 0) {
				velocity.x = Math.min(velocity.x + force.getAccelerationX(), maxVelocity.x);
			} else if (force.getMaxVelocityX() < 0) {
				velocity.x = Math.max(velocity.x + force.getAccelerationX(), minVelocity.x);
			}
			
			if (force.getMaxVelocityY() > 0) {
				velocity.y = Math.min(velocity.y + force.getAccelerationY(), maxVelocity.y);
			} else if (force.getMaxVelocityY() < 0) {
				velocity.y = Math.max(velocity.y + force.getAccelerationY(), minVelocity.y);
			}	
		}
		
		appliedForces.clear();
		
		this.desiredPosition.set(getX(), getY());
		this.desiredPosition.add(velocity.cpy().scl(MarioGame.deltaTime));
	}
	
	public HitBox createHitBox() {
		return new HitBox(desiredPosition, new Vector2(getWidth(), getHeight()));
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public boolean isPawn() {
		return isPawn;
	}

	public void setPawn(boolean isPawn) {
		this.isPawn = isPawn;
	}
	
	public void setVelocityY(float speedY) {
		velocity.y = speedY;
		applyJumpForce = false;
		jumpElapsedTime = 0;
	}
	
	public void setVelocityX(float speedX) {
		velocity.x = speedX;
	}
	
	public Vector2 getDesiredPosition() {
		return desiredPosition;
	}

	public void setDesiredPosition(Vector2 desiredPosition) {
		this.desiredPosition = desiredPosition;
	}

	public boolean isDesiredPositionAdjusted() {
		return desiredPositionAdjusted;
	}

	public void setDesiredPositionAdjusted(boolean desiredPositionAdjusted) {
		this.desiredPositionAdjusted = desiredPositionAdjusted;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isInView() {
		return isInView;
	}

	public void setInView(boolean isInView) {
		this.isInView = isInView;
	}

	public float getMovementForce() {
		return movementForce;
	}

	public void setMovementForce(float movementForce) {
		this.movementForce = movementForce;
	}

	public float getMovementAccl() {
		return movementAccl;
	}

	public void setMovementAccl(float movementAccl) {
		this.movementAccl = movementAccl;
	}

	public float getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(float jumpForce) {
		this.jumpForce = jumpForce;
	}

	public float getJumpInitialForce() {
		return jumpInitialForce;
	}

	public void setJumpInitialForce(float jumpInitialForce) {
		this.jumpInitialForce = jumpInitialForce;
	}

	public float getJumpAcceleration() {
		return jumpAcceleration;
	}

	public void setJumpAcceleration(float jumpAcceleration) {
		this.jumpAcceleration = jumpAcceleration;
	}
	
	public float getJumpMaxTime() {
		return(jumpMaxTime);
	}

	public void setJumpMaxTime(float jumpMaxTime) {
		this.jumpMaxTime = jumpMaxTime;
	}

	public float getJumpMovementCoefficent() {
		return jumpMovementCoefficent;
	}

	public void setJumpMovementCoefficent(float jumpMovementCoefficent) {
		this.jumpMovementCoefficent = jumpMovementCoefficent;
	}
	
	public void loadAssets() {

	}

	public TextureRegion getActiveSprite() {
		return activeSprite;
	}

	public void setActiveSprite(TextureRegion activeSprite) {
		this.activeSprite = activeSprite;
	}
}
