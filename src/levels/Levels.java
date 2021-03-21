package levels;

import city.cs.engine.UserView;
import levels.levels.*;
import objects.Brick;
import objects.Bullet;
import objects.Pad;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import points.HighScore;
import points.PointsHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class Levels {
	private final ArrayList<Level> levels = new ArrayList<>();
	private boolean isActive = false;
	private Pad pad;
	private final UserView view;
	private final JLabel levelLabel;
	private Bullet ball;
	private static final String fileGameSave = "GameSave";

	private int latestIndex;

	/**
	 * Sets up the levels.
	 *
	 * @param pad        The Pad object.
	 * @param view       The view to draw the level to.
	 * @param levelLabel The label to display on completion of levels and the game.
	 */
	public Levels(Pad pad, final UserView view, final JLabel levelLabel) {
		this.view = view;
		this.levelLabel = levelLabel;
		this.pad = pad;

		initLevelsData();
	}

	private void initLevelsData(){
		levels.clear();
		levels.add(new Level1(pad, levelLabel));
		levels.add(new Level2(pad, levelLabel));
		levels.add(new Level3(pad, levelLabel));
		levels.add(new Level4(pad, levelLabel));
		levels.add(new Level5(pad, levelLabel));
	}
	/**
	 * Starts the game. Draws specified level (usually 0) to the specified world.
	 *
	 * @param index The index of the level to start at. You probably want 0.
	 */
	public void start(final int index) {
		final Level level = levels.get(index);
		level.init();

		level.drawTo(view.getWorld());

		isActive = true;
		latestIndex = index;
		level.setLives(3);
		levelLabel.setVisible(false);
		PointsHandler.setPoints(0);

		level.addEventListener(new LevelEventAdapter() {
			@Override
			public void levelComplete() {
				level.destroy();
				setBall(null);

				isActive = false;

				levelLabel.setText("Click mouse to next level.");
				levelLabel.setVisible(true);

				if (index == levels.size() - 1) {
					levelLabel.setText("You won the game!");
					showInputNameDialog();
					return;
				}

				view.addMouseListener(new MouseAdapter() {
					private boolean called = false;

					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);

						if (called) {
							return;
						}

						called = true;

						levelLabel.setVisible(false);

						start(index + 1);
					}
				});
			}

			@Override
			public void levelLose() {
				level.destroy();
				setBall(null);
				isActive = false;

				levelLabel.setVisible(true);

//				if (index == levels.size() - 1) {
					levelLabel.setText("You Lose the Game!");
					showInputNameDialog();
					return;
//				}


//				view.addMouseListener(new MouseAdapter() {
//					private boolean called = false;
//
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						super.mouseClicked(e);
//
//						if (called) {
//							return;
//						}
//
//						called = true;
//
//						levelLabel.setVisible(false);
//
//						start(index);
//					}
//				});
			}
		});
	}

	/**
	 * Starts the game at level 0.
	 */
	public void start() {
		initLevelsData();
		start(0);
	}

	/**
	 * Is there a level being played right now?
	 *
	 * @return True if level not being played.
	 */
	public boolean getInactive() {
		return !isActive;
	}

	/**
	 * Get the level being played right now.
	 *
	 * @return The current level.
	 */
	public Level getCurrentLevel() {
		return levels.get(latestIndex);
	}

	public void showInputNameDialog(){
		Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");

		JDialog nameDialog = new JDialog();
		nameDialog.setLayout(new BorderLayout());

		JLabel labelName = new JLabel("Input your name");
		labelName.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));

		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField textName = new JTextField();
		textName.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));

		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));

		nameDialog.setSize(240, 180);
		nameDialog.add(labelName, BorderLayout.NORTH);
		nameDialog.add(textName, BorderLayout.CENTER);
		nameDialog.add(btnSave, BorderLayout.SOUTH);

		nameDialog.show();

		// Save user name and score
		btnSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				HighScore.saveHighScore(textName.getText(), PointsHandler.getPoints());
				nameDialog.hide();
			}
		});
	}

	public void showHighScoreDialog(){
		Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");
		JDialog dialog = new JDialog();
		dialog.setLayout(new BorderLayout());
		dialog.setSize(360, 540);

		JLabel labelTop = new JLabel("High Scores");
		labelTop.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));
		labelTop.setHorizontalAlignment(SwingConstants.CENTER);
		dialog.add(labelTop);

		final DefaultListModel scores = new DefaultListModel();

		HighScore.getHighScore().forEach((name, score) -> {
			scores.addElement(name + " " + score);
		});

		JList listScores = new JList(scores);
		listScores.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 18));
		listScores.setSize(360, 540);
		dialog.setTitle("High Scores");
		dialog.add(listScores, BorderLayout.CENTER);

		dialog.show();
	}

	public void pauseLevel(){
		isActive = false;
		pad.getWorld().stop();
	}

	public void resumeLevel(Vec2 vec2){
		if(vec2 != null){
			Vec2 relativeCenter = new Vec2((vec2.x - 650 / 2) / 40 , (750 / 2 - vec2.y) / 40);
			Vec2 posBall = ball.getPosition();
			Vec2 delta =  new Vec2(relativeCenter.x - posBall.x, relativeCenter.y - posBall.y);

			Vec2 newVelocity = new Vec2(delta.x / delta.y * 20, 20);
			ball.setLinearVelocity(newVelocity);
		}

		isActive = true;
		pad.getWorld().start();
	}

	public void setBall(Bullet bullet){
		this.ball = bullet;
	}

	public void saveLevel(){
		int lives = getCurrentLevel().getLives();
		float scores = PointsHandler.getPoints();
		ArrayList<Brick> bricks = getCurrentLevel().getBricks();

		try (FileWriter fw = new FileWriter(fileGameSave, false)) {
			fw.write(getCurrentLevel().getRound() + "\n");
			fw.write(lives + "\n");
			fw.write(scores + "\n");
			fw.write(bricks.size() + "\n");

			for(int i = 0; i < bricks.size(); i++){
				Vec2 pos = bricks.get(i).getPosition();
				fw.write(String.format("%s %s %s\n", pos.x, pos.y, bricks.get(i).getLives()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resumeLevel(null);
	}

	public void loadGame(){

		try (BufferedReader reader = new BufferedReader(new FileReader(fileGameSave))) {

			int round = Integer.parseInt(reader.readLine());
			float lives = Float.parseFloat(reader.readLine());
			float scores = Float.parseFloat(reader.readLine());

			int size = Integer.parseInt(reader.readLine());

			Vec3[] bricks = new Vec3[size];

			String data = reader.readLine();
			int index = 0;
			while(data != null){
				String[] tempData = data.split(" ");
				Float x = Float.parseFloat(tempData[0]);
				Float y = Float.parseFloat(tempData[1]);
				Float z = Float.parseFloat(tempData[2]);
				bricks[index++] = new Vec3(x, y, z);
				data = reader.readLine();
			}

			Level levelSaved = new Level(bricks, pad, new Vec2(0, 0), round);
			levels.set(round - 1, levelSaved);
			start(round -1);
			getCurrentLevel().setLives((int)lives);
			PointsHandler.setPoints(scores);
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
			} else {
				e.printStackTrace();
			}
		}
	}

	public Bullet getBall(){
		return ball;
	}
}
