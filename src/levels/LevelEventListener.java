package levels;

/**
 * An interface for creating classes to listener for level events.
 */
public interface LevelEventListener {
	/**
	 * Fired when the level is completed.
	 */
	public void levelComplete();

	/**
	 * Fired when the level is losed.
	 */
	public void levelLose();

	/**
	 * Fired when the level is started (after everything has been created).
	 */
	public void levelStart();
}
