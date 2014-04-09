package com.cs279.ShapeWorld;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import com.cs279.ShapeWorld.Controller.GameEvent;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class MainCharacter extends Sprite {
	public enum State {
		STANDING, JUMPING_UP, JUMPING_DOWN, FALLING, WALKING, ONITEM
	}

	@XStreamOmitField
	private Animation animation;
	
	@XStreamOmitField
	private GameEngine ge;

	@XStreamOmitField
	private final int SPRITE_SIZE = 100;
	@XStreamOmitField
	private final int VELOCITY_X = 4;
	@XStreamOmitField 
	private final int VELOCITY_Y = 3;
	@XStreamOmitField
	private State state = State.WALKING;
	

	public MainCharacter(String imageLocation, int x, int y) {
		super(imageLocation, x, y);

		node.setViewport(new Rectangle2D(0, 0, 100, 100));
 
	}

	private Object readResolve() {
		initialize();
		return this;
	}

	protected void initialize() {
		super.initialize();
		node.setTranslateY(trueY);
		node.setTranslateX(trueX);
		node.setRotationAxis(Rotate.Y_AXIS);

		animation = new SpriteAnimation(node, Duration.millis(500), 8, 1, 0, 0,
				SPRITE_SIZE, SPRITE_SIZE);
		animation.setCycleCount(1);
	}

	@Override
	public void update(GameEngine ge) {
		this.ge = ge; //REFACTOR
		GameEvent last = ge.getController().getLastEvent() == null ? GameEvent.NONE
				: ge.getController().getLastEvent();
		if(!checkGround()) {
			state = State.FALLING;
		}
		if (state == State.JUMPING_UP) {
			trueY -= VELOCITY_X;
			if ((LevelGround.GROUND_LEVEL - trueY) > 70) {
				state = State.JUMPING_DOWN;
			}
			node.setTranslateY(trueY);
			if(ge.getController().getLastActionEvent() != GameEvent.DOWN)
				move(ge.getController().getLastActionEvent());
			else
				state = State.JUMPING_DOWN;
		} else if (state == State.JUMPING_DOWN) {
			trueY += VELOCITY_Y;
			if (trueY > LevelGround.GROUND_LEVEL - 3) {
				state = State.STANDING;
				trueY = LevelGround.GROUND_LEVEL;
			}
			node.setTranslateY(trueY);
			move(ge.getController().getLastActionEvent());
		} else if(state == State.FALLING) {
			trueY += VELOCITY_Y;
			node.setTranslateY(trueY);
			move(ge.getController().getLastActionEvent());
		} else {
			switch (last) {
			case RIGHT:
				animation.play();
				node.setRotate(0);
				move(last);
				break;
			case LEFT:
				node.setRotate(180);
				animation.play();
				move(last);
				break;
			case UP:
				jump();
				break;
			case DOWN:
				break;
			case NONE:
				animation.stop();
				setStanding();
				break;
			default:
				break;
			}
		}
	}
	
	public void move(GameEvent le) {
			if(!collision(le)) {
				if(le == GameEvent.RIGHT) {
					trueX += VELOCITY_X;
					ge.getStageCamera().augmentX(VELOCITY_X);
				} else if(le == GameEvent.LEFT) {
					trueX -= VELOCITY_X;
					ge.getStageCamera().augmentX(-VELOCITY_X);
				}
			}
			node.setTranslateX(trueX - ge.getStageCamera().getX());
	}

	public void setStanding() {
		node.setViewport(new Rectangle2D(0, 800, 100, 100));
	}
	
	public boolean collision(GameEvent le) {
		for (Sprite s : ge.getLevel().getAllSprites()) {
			if (s.collision(this) && state != State.ONITEM) {
				if(s.getXCoord() > trueX && le == GameEvent.RIGHT)
					return true;
				else if(s.getXCoord() < trueX && le == GameEvent.LEFT)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkGround() {
		boolean ground = false;
		for(Sprite s : ge.getLevel().getAllSprites()) {
			if(s.getCollisionType() == Sprite.CollisionType.GROUND) {
				if(trueX < (s.getEndX()+5) && trueX > (s.getXCoord()-5)) {
					ground = true;
				}
			} else if(s.getCollisionType() == Sprite.CollisionType.SOLID) {
				if(s.collision(this) && (trueY + SPRITE_SIZE) < (s.getYCoord())){
					System.out.println("1: " + (trueY + SPRITE_SIZE) + "\n2: " + (s.getYCoord() + s.getHeight()));
					if(s.getXCoord() < trueX && s.getEndX() > trueX) {
						System.out.println(s.getYCoord() + s.getHeight());
						state = State.ONITEM;
						return true;
					}
				}
			} else if(state == State.ONITEM) {
				state = State.JUMPING_DOWN;
				return true;
			}
		}
		return ground;
	}

	public void jump() {
		animation.stop();
		node.setViewport(new Rectangle2D(0, 0, 100, 100));
		state = State.JUMPING_UP;
	}


	public String getType() {
		return null;
	}

}
