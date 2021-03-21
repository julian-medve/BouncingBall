package points;

/**
 * Event fired when the number of points is changed.
 */
public class PointsChangeEvent {
	// The new number of points
	public final float points;

	// The old number of points
	public final float oldPoints;

	// Points gained. Can be negative. This will never be zero!
	public final float difference;

	// True if points gained is greater than zero.
	public final boolean good;

	/**
	 * Set up the event with final variables so that they can't be changed.
	 *
	 * @param points    The new number of points.
	 * @param oldPoints The previous number of points.
	 */
	public PointsChangeEvent(float points, float oldPoints) {
		this.points = points;
		this.oldPoints = oldPoints;
		this.difference = points - oldPoints;
		this.good = this.difference > 0;
	}
}
