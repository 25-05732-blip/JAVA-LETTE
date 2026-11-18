import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Holds the question bank and serves questions in RANDOM order.
 * A question is never repeated until the game is reset.
 */
public class QuizManager {
    private final ArrayList<Question> questionList = new ArrayList<>();
    private final ArrayList<Question> remaining = new ArrayList<>();
    private final Random random = new Random();

    public QuizManager() {
        loadDefaultQuestions();
        resetQuestions();
    }

    /** Puts every question back in the pool (called at the start of each game). */
    public void resetQuestions() {
        remaining.clear();
        remaining.addAll(questionList);
    }

    public boolean hasMoreQuestions() { return !remaining.isEmpty(); }

    /** Picks a random unused question and shuffles its A-D choices. */
    public Question getRandomQuestion() {
        if (remaining.isEmpty()) return null;
        Question q = remaining.remove(random.nextInt(remaining.size()));
        q.shuffleOptions();
        return q;
    }

    public boolean checkAnswer(Question q, int choiceIndex) {
        return q.isCorrect(choiceIndex);
    }

    public void addQuestion(Question q) {
        questionList.add(q);
        remaining.add(q);
    }

    public int getQuestionCount() { return questionList.size(); }

    public Set<String> getTopics() {
        Set<String> topics = new LinkedHashSet<>();
        for (Question q : questionList) topics.add(q.getTopic());
        return topics;
    }

    // ---------------------------------------------------------------
    // QUESTION BANK: add, remove or edit questions here.
    // Format: new Question(topic, question, CORRECT answer, wrong1, wrong2, wrong3)
    // ---------------------------------------------------------------
    private void loadDefaultQuestions() {
        // Syntax
        addQuestion(new Question("Syntax", "Which is the correct entry point of a Java program?",
                "public static void main(String[] args)", "public void main(String args)",
                "static public int main()", "void main(String[] args)"));
        addQuestion(new Question("Syntax", "Which symbol ends a Java statement?",
                ";", ":", ".", "#"));
        addQuestion(new Question("Syntax", "Which statement prints text to the console?",
                "System.out.println(\"Hi\");", "print(\"Hi\");", "console.log(\"Hi\");", "echo \"Hi\";"));
        addQuestion(new Question("Syntax", "Which symbol starts a single-line comment?",
                "//", "#", "<!--", "**"));
        addQuestion(new Question("Syntax", "Which is the correct way to declare a class named Car?",
                "public class Car { }", "class public Car { }", "public Car class { }", "new class Car { }"));

        // Data Types
        addQuestion(new Question("Data Types", "Which data type stores true or false?",
                "boolean", "int", "String", "char"));
        addQuestion(new Question("Data Types", "Which data type stores whole numbers?",
                "int", "double", "boolean", "String"));
        addQuestion(new Question("Data Types", "Which data type best stores 3.14159?",
                "double", "int", "char", "boolean"));
        addQuestion(new Question("Data Types", "Which data type stores a single character like 'A'?",
                "char", "String", "int", "boolean"));
        addQuestion(new Question("Data Types", "Which is a valid way to declare a String?",
                "String name = \"Ana\";", "string name = 'Ana';", "String name = 'Ana';", "str name = \"Ana\";"));

        // Control Structures
        addQuestion(new Question("Control Structures", "Which loop always runs at least once?",
                "do-while", "while", "for", "for-each"));
        addQuestion(new Question("Control Structures", "Which keyword exits a loop immediately?",
                "break", "continue", "return;", "stop"));
        addQuestion(new Question("Control Structures", "How many times does for(int i=0;i<3;i++) run?",
                "3", "2", "4", "0"));
        addQuestion(new Question("Control Structures", "Which switch label handles all other cases?",
                "default", "else", "otherwise", "catch"));
        addQuestion(new Question("Control Structures", "Which keyword skips to the next loop iteration?",
                "continue", "break", "skip", "next"));

        // OOP
        addQuestion(new Question("OOP", "Which principle hides data using private fields?",
                "Encapsulation", "Polymorphism", "Inheritance", "Abstraction"));
        addQuestion(new Question("OOP", "Which keyword inherits from a class?",
                "extends", "implements", "inherits", "super"));
        addQuestion(new Question("OOP", "Which keyword creates an object?",
                "new", "create", "make", "object"));
        addQuestion(new Question("OOP", "Same method name, different behavior per subclass is called?",
                "Polymorphism", "Encapsulation", "Abstraction", "Composition"));
        addQuestion(new Question("OOP", "A constructor has the same name as the...",
                "class", "package", "object", "main method"));
        addQuestion(new Question("OOP", "Which keyword refers to the current object?",
                "this", "self", "me", "current"));
    }
}
