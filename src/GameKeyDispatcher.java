import city.cs.engine.*;
//import helpers.StepAdapter;
import helpers.StepAdapter;
import levels.Level;
import levels.Levels;
//import objects.Bullet;
import objects.Bullet;
import objects.Pad;
import objects.Tile;
import org.jbox2d.common.Vec2;
import points.PointsHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Handle all key events in the game.
 */
public class GameKeyDispatcher extends KeyAdapter {
	private final Pad pad;
	private final World world;

	private int horizontal;
	private boolean cheat = true;
	private Levels levels;

	private static final LinkedList<Integer> konamiKeys = new LinkedList<>(
			Arrays.asList(38, 38, 40, 40, 37, 39, 37, 39, 66, 65)
	);
	private final LinkedList<Integer> lastKeys = new LinkedList<>();

	/**
	 * Set up the event handler.
	 *
	 * @param pad		The main character.
	 * @param world     The world.
	 * @param levels    Levels object.
	 */
	public GameKeyDispatcher(Pad pad, World world, final Levels levels) {
		this.pad = pad;
		this.world = world;
		this.levels = levels;

		// Reapply force on ever step to simulate a continuous force.
		world.addStepListener(new StepAdapter() {
			@Override
			public void preStep(StepEvent stepEvent) {
				if (levels.getInactive()) {
					return;
				}

				GameKeyDispatcher.this.pad.applyForce(new Vec2(horizontal, 0));
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);

		if(levels.getInactive())
			return;

		konamiHandler(e.getKeyCode());

		switch (e.getKeyCode()) {
			// Throw a bullet
			case KeyEvent.VK_SPACE:

				if(levels.getBall() != null)
					return;

				final DynamicBody bullet = new Bullet(world, cheat);
				bullet.setPosition(new Vec2(3.0f, -2.0f));
				bullet.setLinearVelocity(new Vec2(-7.5f, -10f));
				bullet.addCollisionListener(new CollisionListener() {
					@Override
					public void collide(CollisionEvent collisionEvent) {
						Body otherBody = collisionEvent.getOtherBody();
						if(otherBody instanceof Tile && otherBody.getPosition().y == -9.0f){

							levels.setBall(null);
							bullet.destroy();

							levels.getCurrentLevel().setLives(levels.getCurrentLevel().getLives() - 1);

							if(levels.getCurrentLevel().getLives() == 0){
								levels.getCurrentLevel().loseLevel();
							}

							PointsHandler.setPoints(PointsHandler.getPoints());
						}

						// The Second Extra feature
						if(otherBody instanceof Pad  && levels.getCurrentLevel().getRandomExtra() == 2){
							levels.pauseLevel();
						}
					}
				});

				levels.setBall((Bullet) bullet);

				if (!cheat) {
					PointsHandler.removePoints(0.5f);
				}
				break;

			// Apply a horizontal force
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:

				if(levels.getCurrentLevel().getRandomExtra() == 1)
					this.horizontal = 3000;
				else
					this.horizontal = -3000;

				break;

			// Apply a horizontal force
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				if(levels.getCurrentLevel().getRandomExtra() == 1)
					this.horizontal = -3000;
				else
					this.horizontal = 3000;

				break;

			// Add points
			case KeyEvent.VK_P:
				if (cheat) {
					PointsHandler.addPoints(10);
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);

		switch (e.getKeyCode()) {

			// Cancel the force when the left or right key is released.
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_A:
			case KeyEvent.VK_D:
				this.horizontal = 0;
				break;
		}
	}

	private void konamiHandler(int keyCode) {
		lastKeys.add(keyCode);

		// No point in checking again
		if (cheat) {
			return;
		}

		if (lastKeys.size() > konamiKeys.size()) {
			lastKeys.remove();
		}

		if (lastKeys.equals(konamiKeys)) {
			cheat = true;
			world.setGravity(50);
		}
	}
}