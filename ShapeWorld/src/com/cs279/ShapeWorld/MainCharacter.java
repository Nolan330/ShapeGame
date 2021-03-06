package com.cs279.ShapeWorld;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import com.cs279.ShapeWorld.Controller.GameEvent;
import com.cs279.ShapeWorld.EnemySprite.EnemyType;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class MainCharacter extends Sprite {
	public enum State {
		STANDING, JUMPING_UP, JUMPING_DOWN, FALLING, WALKING, DOUBLE_JUMP
	}

	@XStreamOmitField
	private Animation animation;
	
	@XStreamOmitField
	private GameEngine ge;

	@XStreamOmitField
	private final int SPRITE_WIDTH = 100;
	@XStreamOmitField
	private final int SPRITE_HEIGHT = 100;

	@XStreamOmitField
	private final int VELOCITY = 4;
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
				SPRITE_WIDTH, SPRITE_HEIGHT);
		animation.setCycleCount(1);
	}

	@Override
	public void update(GameEngine ge) {
		this.ge = ge; //REFACTOR
		if(DEAD) {
			ge.getLevel().setGameOver();
			return;
		}
		if(ge.getLevel().isCompleted()) {
			ge.getLevel().reset(true);
		}
		GameEvent last = ge.getController().getLastEvent() == null ? GameEvent.NONE
				: ge.getController().getLastEvent();
		if(last == GameEvent.SHOOT_RED || last == GameEvent.SHOOT_BLUE)  {
			explodeClosest(last);
			return;
		}
		if(!checkGround()) {
			state = State.FALLING;
		}
		if (state == State.JUMPING_UP) {
			trueY -= 5;
			if ((280 - trueY) > 70) {
				state = State.JUMPING_DOWN;
			}
			node.setTranslateY(trueY);
			if(ge.getController().getLastActionEvent() != GameEvent.DOWN)
				move(ge.getController().getLastActionEvent());
			else
				state = State.JUMPING_DOWN;
		} else if (state == State.DOUBLE_JUMP) {
			trueY -= 4;
			if((280 - trueY) > 140){
				state = State.JUMPING_DOWN;
			}
			node.setTranslateY(trueY);
			if(ge.getController().getLastActionEvent() != GameEvent.DOWN)
				move(ge.getController().getLastActionEvent());
			else if(ge.getController().getLastActionEvent() == GameEvent.DOWN)
				state = State.JUMPING_DOWN;
		} else if (state == State.JUMPING_DOWN) {
			trueY += 3;
			if (trueY > 277) {
				state = State.STANDING;
				trueY = 280;
			}
			node.setTranslateY(trueY);
			move(ge.getController().getLastActionEvent());
		} else if(state == State.FALLING) {
			trueY +=3;
			if(trueY > GameEngine.HEIGHT) 
				ge.getLevel().setGameOver();
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
				jump(State.JUMPING_UP);
				break;
			case DOWN:
				break;
			case DOUBLE_JUMP:
				jump(State.DOUBLE_JUMP);
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
					trueX += VELOCITY;
					ge.getStageCamera().augmentX(VELOCITY);
				} else if(le == GameEvent.LEFT) {
					trueX -= VELOCITY;
					ge.getStageCamera().augmentX(-VELOCITY);
				}
			}
			node.setTranslateX(trueX - ge.getStageCamera().getX());
	}

	public void setStanding() {
		state = State.STANDING;
		node.setViewport(new Rectangle2D(0, 800, 100, 100));
	}
	
	public boolean collision(GameEvent le) {
		for (Sprite s : ge.getLevel().getAllSprites()) {
			if (s.collision(this)) {
				if(s instanceof Checkpoint) {
					ge.getLevel().setCompleted(true);
					s.DEAD = true;
					return false;
				}
				if(s.getXCoord() - 5 > trueX && le == GameEvent.RIGHT)
					return true;
				else if(s.getXCoord() < trueX && le == GameEvent.LEFT)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkGround() {
		for(Sprite s : ge.getLevel().getAllSprites()) {
			if(s instanceof LevelGround) {
				if(trueX < (((LevelGround)s).getEndX()+5) && trueX > (s.getXCoord()-5)) {
					return true;
				}
			}
		}
		return false;
	}

	public void jump(State type) {
		animation.stop();
		node.setViewport(new Rectangle2D(0, 0, 100, 100));
		state = type;
	}


	public String getType() {
		return null;
	}
	
	public void explodeClosest(GameEvent g) {
		Sprite closest = null;
		double closeness = Integer.MAX_VALUE;
		for(Sprite s:ge.getLevel().getAllSprites()) {
			if(Math.abs(s.getXCoord()-trueX) < closeness
						&& s.getNode().isVisible()
						&& ge.getStageCamera().isVisible(s)
						&& !s.DEAD
						&& s instanceof EnemySprite) {
				System.out.println("hi");
				if(GameEvent.SHOOT_BLUE == g && ((EnemySprite) s).getEnemyType() == EnemyType.BLUE) {
					closest = s;
				} else if(g == GameEvent.SHOOT_RED && ((EnemySprite) s).getEnemyType() != EnemyType.BLUE) {
					closest = s;
				}
				closeness = Math.abs(s.getXCoord() - trueX);
			}	
		}
		if(closest != null) {
			BackgroundSprite explosion = new BackgroundSprite("/lightning.gif", closest.trueX, closest.trueY) {
				public void update() {
					return;
				}
			};
			explosion.initialize();
			/*
			 * ConcurrentModificationException being thrown here. Works but needs to be handled.
			 */
			FadeTransition ft = new FadeTransition(Duration.millis(600), explosion.getNode());
			ft.setFromValue(1.0);
			ft.setToValue(0.01);
			ft.setCycleCount(1);
			ge.getLevel().addSprite(explosion);
			ft.play();
			closest.DEAD = true;
			
		}
	}

}
