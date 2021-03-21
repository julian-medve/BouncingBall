import city.cs.engine.UserView;
import city.cs.engine.World;
import levels.Levels;
import objects.Pad;
import org.jbox2d.common.Vec2;
import points.HighScore;
import points.PointsHandler;

import javax.swing.*;
import java.awt.*;

public class BouncingBall {
    public static void main(String[] args) {
        World world = new World();
        world.setGravity(1);
        Pad pad = new Pad(world);

        // Create the view
        UserView view = new GameView(world, 650, 750, pad);
        view.setView(new Vec2(0, 0), 40);
        view.setLayout(new BorderLayout());


        // Display points
        final String pointsText = "Points: %s, Lives: %s";

        String text = String.format(pointsText, 0, 3);
        final JLabel pointsLabel = new JLabel(text);

        Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");

        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsLabel.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));
        view.add(pointsLabel, BorderLayout.NORTH);

        // Label for "Level Complete", "Game Complete" and start instructions.
        JLabel levelLabel = new JLabel("Click mouse to next level.");
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setVisible(false);
        levelLabel.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 48));

        view.add(levelLabel, BorderLayout.CENTER);

        // Start the game
        Levels levels = new Levels(pad, view, levelLabel);
        levels.start();
        pad.addLevelsData(levels);

        // Set Levels to the GameView
        ((GameView) view).setGameLevels(levels);

        // Add Event Listener for top label
        PointsHandler.addChangeListener(pointsChangeEvent -> {
            String pointsStr = PointsHandler.pointsToString();
            String newText = String.format(pointsText, pointsStr, levels.getCurrentLevel().getLives());
            pointsLabel.setText(newText);
        });

        // Set up the boring stuff.
        JFrame frame = new JFrame("Game");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(view);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        // Position window in middle of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = frame.getSize();
        Point frameLocation = new Point(dim.width / 2 - windowSize.width / 2,
                dim.height / 2 - windowSize.height / 2);
        frame.setLocation(frameLocation);

        // Set up Draggable
        Draggable.makeDraggable(frame, view, frameLocation);

        frame.addKeyListener(new GameKeyDispatcher(pad, world, levels));

        world.start();
    }
}
