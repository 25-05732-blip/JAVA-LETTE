import java.util.Set;

/**
 * The instructor role. Shows how the question bank is set up.
 * Same method name as Player.displayInfo(), different behavior (Polymorphism).
 */
public class Instructor extends User {
    private final QuizManager quizManager;

    public Instructor(String name, QuizManager quizManager) {
        super(name);
        this.quizManager = quizManager;
    }

    @Override
    public void displayInfo() {
        Set<String> topics = quizManager.getTopics();
        ConsoleUI.println("");
        ConsoleUI.println(ConsoleUI.paint("  INSTRUCTOR INFO", ConsoleUI.CYAN));
        ConsoleUI.println("  Instructor     : " + getName() + " (ID " + getId() + ")");
        ConsoleUI.println("  Questions      : " + quizManager.getQuestionCount() + " in the bank");
        ConsoleUI.println("  Lesson topics  : " + String.join(", ", topics));
        ConsoleUI.println("  To add questions, edit QuizManager.loadDefaultQuestions().");
    }
}
