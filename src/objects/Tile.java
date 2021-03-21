package objects;

import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 * Used by the Bullet class to see whether bullets should be destroyed when
 * they hit stuff.
 */
public class Tile extends StaticBody {
	private boolean killBullets = false;

	public Tile(World world, Shape shape) {
		super(world, shape);
	}

	/**
	 * Returns true if bullets should be destroyed when they hit the tile.
	 *
	 * @return True if bullets should be destroyed when they hit the tile.
	 */
	public boolean getKillBullets() {
		return killBullets;
	}

	/**
	 * Set the value of killBullets.
	 *
	 * @param killBullets If true, bullets will be destroyed when they hit this tile.
	 */
	public void setKillBullets(boolean killBullets) {
		this.killBullets = killBullets;
	}
}
