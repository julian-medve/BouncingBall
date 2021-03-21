import city.cs.engine.UserView;
import city.cs.engine.World;
import levels.Levels;
import objects.Pad;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends UserView {
	private final Pad pad;
	private Levels levels;

	private static final Image backgroundImage = Toolkit.getDefaultToolkit().getImage("assets/image/background.png");
	private static final Image closeButton = Toolkit.getDefaultToolkit().getImage("assets/image/close.png");
	private static final Image pauseButton = Toolkit.getDefaultToolkit().getImage("assets/image/pause.png");

	private static final Image muenButton = Toolkit.getDefaultToolkit().getImage("assets/image/Text_menu.png");
	private static final Image newButton = Toolkit.getDefaultToolkit().getImage("assets/image/Text_new_game.png");
	private static final Image loadButton = Toolkit.getDefaultToolkit().getImage("assets/image/Text_load_game.png");
	private static final Image scoreButton = Toolkit.getDefaultToolkit().getImage("assets/image/Text_high_score.png");

	private boolean menuClicked = false;

	public GameView(World world, int i, int i2, Pad pad) {
		super(world, i, i2);

		this.pad = pad;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if (e.getButton() != 1) {
					return;
				}

				System.out.println(e.getX() + " : " + e.getY());

				if(e.getX() > 30 && e.getX() < 30 + 160){
					if(e.getY() > 5 && e.getY() < 45){
						menuClicked = true;
						return;
					}


					if(menuClicked){
						// New Game
						if(e.getY() > 45 && e.getY() < 85){
							menuClicked = false;
							levels.start();
						}

						// Load Game
						if(e.getY() > 85 && e.getY() < 125){
							menuClicked = false;
							System.out.println("Loads Game");
							levels.loadGame();
						}

						// High Score
						if(e.getY() > 125 && e.getY() < 165){
							levels.showHighScoreDialog();
							menuClicked = false;
						}
					}
				}

				// Close button
				if (e.getX() > 625 && e.getX() < 645 && e.getY() > 5 && e.getY() < 25) {
					System.exit(0);
				}

				// Pause Button
				if (e.getX() > 600 && e.getX() < 620 && e.getY() > 5 && e.getY() < 25) {
					levels.pauseLevel();
					levels.saveLevel();
				}

				if(levels.getInactive() && levels.getCurrentLevel().getRandomExtra() == 2){
					levels.resumeLevel(new Vec2(e.getX(), e.getY()));
				}
			}
		});
	}

	@Override
	protected void paintBackground(Graphics2D graphics2D) {
		Vec2 padPosition = pad.getPosition();

		// Calls to Math.max and Math.min to ensure that the image doesn't go off screen
		graphics2D.drawImage(backgroundImage, 0, 50, this);
	}

	@Override
	protected void paintForeground(Graphics2D graphics2D) {
		graphics2D.drawImage(muenButton, 30, 5, 160, 40, this);

		if(menuClicked){
			graphics2D.drawImage(newButton, 30, 45, 160, 40, this);
			graphics2D.drawImage(loadButton, 30, 85, 160, 40, this);
			graphics2D.drawImage(scoreButton, 30, 125, 160, 40, this);
		}

		graphics2D.drawImage(pauseButton, 600, 5, 20, 20, this);
		graphics2D.drawImage(closeButton, 625, 5, 20, 20, this);
	}

	public void setGameLevels(Levels levels){
		this.levels = levels;
	}
}
