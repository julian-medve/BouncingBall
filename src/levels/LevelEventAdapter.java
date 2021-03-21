package levels;

/**
 * An adapter class for LevelEventListener.
 */
public abstract class LevelEventAdapter implements LevelEventListener {
	/**
	 * Fired when the level is completed.
	 */
	@Override
	public void levelComplete() {}

	/**
	 * Fired when the level is lost.
	 */
	@Override
	public void levelLose() {}

	/**
	 * Fired when the level is started (after everything has been created).
	 */
	@Override
	public void levelStart() {}
}
