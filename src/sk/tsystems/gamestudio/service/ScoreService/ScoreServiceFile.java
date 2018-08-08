package sk.tsystems.gamestudio.service.ScoreService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import sk.tsystems.gamestudio.entity.Score;


public class ScoreServiceFile implements ScoreService {
	private String game;
	
	private List<Score> scores = new ArrayList<>();
	
	public ScoreServiceFile(String game) {
		this.game = game;
	}
	
	/* (non-Javadoc)
	 * @see puzzle.score.ScoreService#addScore(puzzle.score.Score)
	 */
	@Override
	public void addScore(Score score) {
		scores.add(score);
		save();
	}
	
	/* (non-Javadoc)
	 * @see puzzle.score.ScoreService#getBestScores()
	 */
	@Override
	public List<Score> getBestScores(String gameName) {
		load();
		scores.sort(Comparator.comparing(Score::getPoints).reversed());
		return scores;
	}
	
	private String getFileName() {
		return game + ".bin";
	}
	
	private void save() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFileName()))) {
			oos.writeObject(scores);
		} catch (IOException e) {
			System.err.println("Cannot save score!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void load() {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFileName()))) {
			scores = (List<Score>)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Cannot load score!");
		}
	}
}
