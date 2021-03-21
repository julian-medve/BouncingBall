package points;

public interface PointsChangeListener {
	/**
	 * Fired when the number of points has changed.
	 *
	 * @param pointsChangeEvent The event.
	 * @see PointsChangeEvent
	 */
	public void changed(PointsChangeEvent pointsChangeEvent);
}
