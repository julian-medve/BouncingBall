package levels;

import city.cs.engine.Body;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import objects.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import points.PointsHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Level {
	private Vec3[] bricksStart;
	private final Pad pad;
	private final Vec2 startPosition;
	private final int randomPeriod = 30000;
	private int lives = 3;
	private int round = -1;

	// -1 means Random Extra was not happened
	// 0 means Random Extra happened
	// 1 means anti direction of the pad
	// 2 means manual direction of ball
	private int randomExtra = -1;
	private int allBricks;

	private int remainingBricks;
	private final ArrayList<LevelEventListener> listeners = new ArrayList<>();

	private final ArrayList<Body> bodyArray = new ArrayList<>();

	/**
	 * Create level with initial level data.
	 * @param bricksStart    	Array of Vec2 objects for enemy start positions.
	 * @param pad          	  	The pad.
	 * @param startPosition   	The start position of the player.
	 */
	public Level(Vec3[] bricksStart, Pad pad, Vec2 startPosition, int round) {
		this.bricksStart = bricksStart;
		this.pad = pad;
		this.startPosition = startPosition;
		this.round = round;
		init();
	}

	public void init(){
		randomExtra = -1;
		allBricks = bricksStart.length;
		remainingBricks = bricksStart.length;
//		for(int i = 0; i < listeners.size(); i++ )
//			listeners.remove(i);

		List<StaticBody> tempBodies = pad.getWorld().getStaticBodies();
		for(int i = 0; i < tempBodies.size(); i++){
			if(tempBodies.get(i) instanceof Brick)
				tempBodies.get(i).destroy();
		}
	}

	/**
	 * Draw the level to the world.
	 *
	 * @param world The world to draw the level to.
	 */
	public void drawTo(World world) {

		bodyArray.clear();

		// Build the walls
		bodyArray.addAll(Arrays.asList(
				WallGenerator.generateWall(world, 7f)
		));

		bodyArray.addAll(Arrays.asList(
				WallGenerator.generateWall(world, -7f)
		));

		bodyArray.addAll(Arrays.asList(
				WallGenerator.generateCeil(world, 8.5f)
		));

		bodyArray.addAll(Arrays.asList(
				WallGenerator.generateFloor(world, -9.0f)
		));


		// Draw Bricks for each level
		Arrays.stream(bricksStart).forEach(brickPosition -> {
			Brick brick = new Brick(world, pad, (int)brickPosition.z);
			brick.setPosition(new Vec2(brickPosition.x, brickPosition.y));

			brick.addCollisionListener(collisionEvent -> {
				Body otherBody = collisionEvent.getOtherBody();

				if (otherBody instanceof Bullet) {
					if (brick.getLives() == 1) {
						bodyArray.remove(brick);
						brick.destroy();
						PointsHandler.addPoints(5);

						remainingBricks--;

						if(getRound() >= 3 && remainingBricks < allBricks / 2 && randomExtra == -1) {
							randomExtra = new Random(2).nextInt();
							Timer timer = new Timer();
							timer.schedule(new RandomTimer(), randomPeriod, 1000);
						}
					} else if (brick.getLives() != -1){
						brick.setLives(brick.getLives() - 1);
					}

					if (remainingBricks == 0) {
						listeners.forEach(LevelEventListener::levelComplete);
					}

					playBrickSound();
				}
			});

			bodyArray.add(brick);

			if(brick.getLives() == -1)
				remainingBricks--;
		});

		// Position Player
		restorePlayer();

		listeners.forEach(LevelEventListener::levelStart);
	}

	public void loseLevel(){
		listeners.forEach(LevelEventListener::levelLose);
	}

	/**
	 * Remove the level from the world.
	 */
	public void destroy() {
		bodyArray.forEach(Body::destroy);
	}

	/**
	 * Moves the player back to the start point.
	 */
	public void restorePlayer() {
		pad.setLinearVelocity(new Vec2(0, 0));
		pad.setPosition(new Vec2(0, -8.5f));
	}

	/**
	 * Adds an event listener.
	 *
	 * @param levelEventListener The listener to add.
	 */
	public void addEventListener(LevelEventListener levelEventListener) {
		listeners.add(levelEventListener);
	}

	/**
	 * Removes an event listener.
	 *
	 * @param levelEventListener The exact listener to remove.
	 */
	public void removeEventListener(LevelEventListener levelEventListener) {
		listeners.remove(levelEventListener);
	}

	public int getRound(){
		return round;
	}

	public int getLives(){
		return lives;
	}

	public void setLives(int lives){
		this.lives = lives;
	}

	private void playBrickSound(){
		String soundName = "assets/sound/brick_break.wav";
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRandomExtra(){
		return randomExtra;
	}

	class RandomTimer extends TimerTask {
		public void run() {
			randomExtra = 0;
			cancel();
		}
	}

	public ArrayList<Brick> getBricks(){
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		bodyArray.forEach(body->{
			if(body instanceof  Brick)
				bricks.add((Brick)body);
		});
		return bricks;
	}

	public void setBricks(Vec3[] bricks){
		this.bricksStart = bricks;
	}
}
