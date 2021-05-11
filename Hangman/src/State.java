import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1213301723624825100L;
	private String currentWord;
	private transient String solution;
	private int lives;
	private Set<Character> guesses;
	private transient Set<Character> valid_guesses;
	private boolean victory = false;

	public boolean isVictory() {
		return victory;
	}

	public void setVictory(boolean victory) {
		this.victory = victory;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Set<Character> getGuesses() {
		return guesses;
	}

	public void setGuesses(Set<Character> guesses) {
		this.guesses = guesses;
	}

	public void addGuess(Character c) {
		this.guesses.add(Character.toLowerCase(c));
		if (!valid_guesses.contains(Character.toLowerCase(c)))
			this.lives--;
	}

	public State(String word) {
		this.solution = word;
		this.lives = 3;
		this.guesses = new HashSet<Character>();
		this.valid_guesses = new HashSet<Character>();
		for (int i = 0; i < solution.length(); i++) {
			valid_guesses.add(solution.toLowerCase().charAt(i));
		}
	}

	public void solve() {
		victory = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < solution.length(); i++) {
			if (guesses.contains(solution.toLowerCase().charAt(i)))
				sb.append(solution.charAt(i));
			else {
				sb.append("_");
				victory = false;
			}
		}
		currentWord = sb.toString();
	}

	private String addWhiteSpace(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			sb.append(s.charAt(i));
			sb.append(" ");
		}
		return sb.toString();
	}

	public String toString() {
		return addWhiteSpace(currentWord) + "     lives:" + lives;

	}
}
