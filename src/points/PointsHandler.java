package points;

import levels.Levels;

import java.util.ArrayList;

/**
 * Handles points! Static because only one of these is ever needed.
 */
public class PointsHandler {
	private static float totalPoints = 0;
	private static Levels levels;
	private static final ArrayList<PointsChangeListener> listeners = new ArrayList<>();

	/**
	 * Add a number of points.
	 *
	 * @param points Number of points to add.
	 */
	public static void addPoints(float points) {
		setPoints(totalPoints + points);

		if (points < 0) {
			System.out.println("Note: you should use removePoints for removing points.");
		}
	}

	/**
	 * Remove a number of points.
	 *
	 * @param points Number of points to remove.
	 */
	public static void removePoints(float points) {
		setPoints(totalPoints - points);

		if (points < 0) {
			System.out.println("Note: you should use addPoints for adding points.");
		}
	}

	/**
	 * Set the number of points to a specified value.
	 *
	 * @param points New number of points.
	 */
	public static void setPoints(float points) {
		float oldPoints = totalPoints;
		totalPoints = points;

		PointsChangeEvent pointsChangeEvent = new PointsChangeEvent(points, oldPoints);

		// We don't fire the event if the number of points hasn't changed
//		if (pointsChangeEvent.difference == 0) {
//			return;
//		}

		listeners.forEach(listener -> listener.changed(pointsChangeEvent));
	}

	/**
	 * Get the number of points.
	 *
	 * @return The number of points.
	 */
	public static float getPoints() {
		return totalPoints;
	}

	/**
	 * Minus number of lives.
	 *
	 */
	public static void removeLives() {
		levels.getCurrentLevel().setLives(levels.getCurrentLevel().getLives() - 1);
		setPoints(getPoints());
	}


	/**
	 * Convert the current number of points into a string, with one or zero
	 * decimal places.
	 *
	 * @return String representing the number of points.
	 */
	public static String pointsToString() {
		return pointsToString(getPoints());
	}

	/**
	 * Convert a number of points into a string, with one or zero decimal
	 * places.
	 *
	 * @param points The number of points.
	 * @return String representing the number of points.
	 */
	public static String pointsToString(float points) {
		String format = Math.round(points) == points ? ",.0" : ",.1";
		return String.format("%" + format + "f", points);
	}

	public static void setLevels(Levels newLevels){
		levels = newLevels;
	}

	/**
	 * Add a listener to be fired when the number of points changes.
	 *
	 * @param listener The event listener to add.
	 */
	public static void addChangeListener(PointsChangeListener listener) {
		listeners.add(listener);
	}
}
