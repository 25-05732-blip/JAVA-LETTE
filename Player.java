/**
 * The student playing the game. Tracks lives and score.
 * Encapsulation: all fields are private and changed only through methods.
 */
public class Player extends User {
    public static final int STARTING_LIVES = 5;

    private int lives;
    private int score;
    private int correct;
    private int wrong;
    private int timesShot;

    public Player(String name) {
        super(name);
        this.lives = STARTING_LIVES;
        this.score = 0;
    }

    public void addPoints(int points) { score += points; }
    public void incrementCorrect()    { correct++; }
    public void incrementWrong()      { wrong++; }

    public void loseLife() {
        if (lives > 0) lives--;
        timesShot++;
    }

    public boolean isAlive() { return lives > 0; }

    public int getLives()     { return lives; }
    public int getScore()     { return score; }
    public int getCorrect()   { return correct; }
    public int getWrong()     { return wrong; }
    public int getTimesShot() { return timesShot; }

    private int accuracy() {
        int rounds = correct + wrong;
        return rounds == 0 ? 0 : Math.round(100f * correct / rounds);
    }

    public String getStats() {
        return "Lives: " + lives + " | Score: " + score;
    }

    @Override
    public void displayInfo() {
        ConsoleUI.println("   +--------------- FINAL RESULTS ---------------+");
        ConsoleUI.println(String.format("   |  Player          : %-24s |", getName()));
        ConsoleUI.println(String.format("   |  Rounds Played   : %-24d |", correct + wrong));
        ConsoleUI.println(String.format("   |  Correct Answers : %-24d |", correct));
        ConsoleUI.println(String.format("   |  Wrong Answers   : %-24d |", wrong));
        ConsoleUI.println(String.format("   |  Times Shot      : %-24d |", timesShot));
        ConsoleUI.println(String.format("   |  Accuracy        : %-24s |", accuracy() + "%"));
        ConsoleUI.println(String.format("   |  Final Score     : %-24d |", score));
        ConsoleUI.println("   +---------------------------------------------+");
    }
}
