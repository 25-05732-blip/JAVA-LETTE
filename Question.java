import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** One multiple-choice quiz item. Answer choices are shuffled every time. */
public class Question {
    private final String topic;
    private final String text;
    private final String correctAnswer;
    private final List<String> options = new ArrayList<>();
    private int correctIndex;

    public Question(String topic, String text, String correctAnswer, String... wrongAnswers) {
        this.topic = topic;
        this.text = text;
        this.correctAnswer = correctAnswer;
        options.add(correctAnswer);
        Collections.addAll(options, wrongAnswers);
        shuffleOptions();
    }

    /** Randomizes the order of A-D and remembers where the right one landed. */
    public void shuffleOptions() {
        Collections.shuffle(options);
        correctIndex = options.indexOf(correctAnswer);
    }

    public boolean isCorrect(int choiceIndex) { return choiceIndex == correctIndex; }

    public String getTopic()         { return topic; }
    public String getText()          { return text; }
    public String[] getOptions()     { return options.toArray(new String[0]); }
    public String getCorrectAnswer() { return correctAnswer; }
    public char getCorrectLetter()   { return (char) ('A' + correctIndex); }
}
