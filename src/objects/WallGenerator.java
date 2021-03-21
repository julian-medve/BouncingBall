package objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * As you can only have one image per Body, this is hacky.
 */
public class WallGenerator {
	private WallGenerator() {}

	private static final BodyImage wall_vertical = new BodyImage("assets/image/Wall_vertical.png");
	private static final BodyImage wall_ceil = new BodyImage("assets/image/Wall_ceil.png");
	private static final BodyImage wall_floor = new BodyImage("assets/image/Wall_floor.png");

	private static final Shape shape_vertical 	= new BoxShape(0.25f, 0.5f);
	private static final Shape shape_horizontal = new BoxShape(0.5f, 0.5f);
	private static final Shape shape_bottom = new BoxShape(7f, 0.5f);

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the wall on.
	 * @param x     The x position of the wall.
	 * @return      An array of Tiles that make the wall.
	 */
	public static Tile[] generateWall(World world, float x) {
		Tile[] tiles = new Tile[16];

		for (int i = 0; i < 16; i++) {
			Tile tile = new Tile(world, shape_vertical);
			tile.setKillBullets(false);
			tile.setPosition(new Vec2(x, 7.5f - i));
			tile.addImage(wall_vertical);
			tiles[i] = tile;

			SolidFixture fixture = new SolidFixture(tile, shape_vertical);
			fixture.setFriction(0);
		}

		return tiles;
	}

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the ceil on.
	 * @param y     The y position of the ceil.
	 * @return      An array of Tiles that make the ceil.
	 */
	public static Tile[] generateCeil(World world, float y) {
		Tile[] tiles = new Tile[14];

		for (int i = 0; i < 14; i++) {
			Tile tile = new Tile(world, shape_horizontal);
			tile.setKillBullets(false);
			tile.setPosition(new Vec2(6.5f - i , y));
			tile.addImage(wall_ceil);
			tiles[i] = tile;

			SolidFixture fixture = new SolidFixture(tile, shape_horizontal);
			fixture.setFriction(0);
		}

		return tiles;
	}

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the ceil on.
	 * @param y     The y position of the ceil.
	 * @return      An array of Tiles that make the ceil.
	 */
	public static Tile[] generateFloor(World world, float y) {
		Tile[] tiles = new Tile[1];

		Tile tile = new Tile(world, shape_bottom);
		tile.setKillBullets(true);
		tile.setPosition(new Vec2(0 , y));
		tile.setName("floor");
		tile.addImage(wall_floor);
		tiles[0] = tile;

		SolidFixture fixture = new SolidFixture(tile, shape_bottom);
		fixture.setFriction(0);

		return tiles;
	}
}
