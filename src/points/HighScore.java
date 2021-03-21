package points;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to handle high scores.
 */
public class HighScore {
	private static final String file = "highscore";
	private static Map<String, String> highScores = new HashMap<String, String>();

	public static Map<String, String> getHighScore() {

		highScores = new HashMap<>();

		// Autocloses in Java 7 (both resources)

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String highScore = reader.readLine();
			while(highScore != null){
				String name = highScore.split(":")[0];
				String score = highScore.split(":")[1];
				highScores.put(name, score);
				highScore = reader.readLine();
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("High score file not found. Probably not a problem.");
			} else {
				e.printStackTrace();
			}
		}

		return highScores;
	}

	public static void saveHighScore(String name, float highScore) {
		try (FileWriter fw = new FileWriter(file, true)) {
			fw.append(name + ":" + highScore + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
