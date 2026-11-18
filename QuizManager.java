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
    // QUESTION BANK: 40 OOP questions (Basic OOP, Encapsulation, Inheritance,
    // Polymorphism, Abstraction, Application).
    // Format: new Question(topic, question, CORRECT answer, wrong1, wrong2, wrong3)
    // The A-D order is shuffled every time a question is shown.
    // ---------------------------------------------------------------
    private void loadDefaultQuestions() {
        // Basic OOP Concepts
        addQuestion(new Question("Basic OOP Concepts", "What does OOP stand for?",
                "Object-Oriented Programming", "Object-Oriented Process", "Operational Object Programming", "Organized Operation Program"));
        addQuestion(new Question("Basic OOP Concepts", "What is an object in Java?",
                "An instance of a class", "A type of loop", "A programming language", "A method inside a class"));
        addQuestion(new Question("Basic OOP Concepts", "What is a class in Java?",
                "A blueprint for creating objects", "A variable that stores numbers", "A method that executes a program", "A type of constructor"));
        addQuestion(new Question("Basic OOP Concepts", "Which of the following is an example of an object?",
                "new Car()", "class", "public", "void"));
        addQuestion(new Question("Basic OOP Concepts", "Which keyword is used to create an object in Java?",
                "new", "class", "this", "static"));
        addQuestion(new Question("Basic OOP Concepts", "What are the two common components of a class?",
                "Fields and methods", "Loops and conditions", "Packages and imports", "Comments and operators"));
        addQuestion(new Question("Basic OOP Concepts", "What is a method in Java?",
                "A block of code that performs a specific task", "A blueprint for objects", "A type of variable", "A keyword used to create classes"));
        addQuestion(new Question("Basic OOP Concepts", "What is the purpose of a constructor?",
                "To initialize an object when it is created", "To destroy an object", "To repeat a block of code", "To import another class"));
        addQuestion(new Question("Basic OOP Concepts", "Which statement about constructors is correct?",
                "A constructor has the same name as its class.", "A constructor must always return an integer.", "A constructor must have a return type.", "A constructor cannot have parameters."));
        addQuestion(new Question("Basic OOP Concepts", "Which keyword refers to the current object in Java?",
                "this", "super", "new", "extends"));

        // Encapsulation
        addQuestion(new Question("Encapsulation", "What is encapsulation in OOP?",
                "Bundling data and methods together while controlling access to data", "Creating multiple objects", "Creating a child class", "Hiding all methods from other classes"));
        addQuestion(new Question("Encapsulation", "Which access modifier is commonly used to protect class fields in encapsulation?",
                "private", "public", "protected", "static"));
        addQuestion(new Question("Encapsulation", "What is the main purpose of a getter method?",
                "To retrieve the value of a private field", "To delete a variable", "To create a new object", "To change the class name"));
        addQuestion(new Question("Encapsulation", "What is the main purpose of a setter method?",
                "To modify the value of a field", "To retrieve a private field", "To create a subclass", "To execute a constructor"));
        addQuestion(new Question("Encapsulation", "Which of the following demonstrates encapsulation?",
                "Declaring fields as private and providing public getter and setter methods", "Declaring all fields as public", "Creating multiple constructors", "Using inheritance to extend a class"));
        addQuestion(new Question("Encapsulation", "What is one major benefit of encapsulation?",
                "It protects data from unauthorized or uncontrolled access.", "It makes every variable public.", "It removes the need for methods.", "It allows classes to have multiple parents."));

        // Inheritance
        addQuestion(new Question("Inheritance", "What is inheritance in OOP?",
                "Allowing a class to acquire properties and methods from another class", "Hiding data from users", "Creating multiple constructors", "Converting objects into variables"));
        addQuestion(new Question("Inheritance", "Which keyword is used to inherit a class in Java?",
                "extends", "implements", "this", "super"));
        addQuestion(new Question("Inheritance", "What is the class being inherited from called?",
                "Superclass", "Subclass", "Constructor", "Interface object"));
        addQuestion(new Question("Inheritance", "What is the class that inherits from another class called?",
                "Subclass", "Parent class", "Superclass", "Abstract class"));
        addQuestion(new Question("Inheritance", "Which of the following represents inheritance?",
                "class Dog extends Animal", "class Dog()", "class Dog implements Animal", "class Dog inherits Animal"));
        addQuestion(new Question("Inheritance", "What is the main advantage of inheritance?",
                "It promotes code reusability.", "It prevents code reuse.", "It removes all constructors.", "It makes every field private."));
        addQuestion(new Question("Inheritance", "Which keyword is used to refer to the parent class in Java?",
                "super", "this", "extends", "parent"));
        addQuestion(new Question("Inheritance", "Can a Java class directly extend multiple classes?",
                "No, Java does not support multiple class inheritance.", "Yes, without restrictions", "Yes, but only two classes", "Only if the classes are abstract"));

        // Polymorphism
        addQuestion(new Question("Polymorphism", "What does polymorphism mean in OOP?",
                "One entity can take many forms.", "One class can have only one method.", "A class cannot inherit another class.", "Data must always be private."));
        addQuestion(new Question("Polymorphism", "Which of the following is an example of compile-time polymorphism?",
                "Method overloading", "Method overriding", "Encapsulation", "Inheritance"));
        addQuestion(new Question("Polymorphism", "What is method overloading?",
                "Defining multiple methods with the same name but different parameter lists", "Using the same method in different classes only", "Changing a class into an object", "Hiding a method from another class"));
        addQuestion(new Question("Polymorphism", "What is method overriding?",
                "Redefining an inherited method in a subclass", "Creating two methods with different names", "Declaring a variable as private", "Creating a constructor without parameters"));
        addQuestion(new Question("Polymorphism", "Which type of polymorphism is achieved through method overriding?",
                "Runtime polymorphism", "Compile-time polymorphism", "Static polymorphism only", "Constructor polymorphism"));
        addQuestion(new Question("Polymorphism", "Which annotation is commonly used when overriding a method in Java?",
                "@Override", "@Inherited", "@Overload", "@Polymorphism"));

        // Abstraction
        addQuestion(new Question("Abstraction", "What is abstraction in OOP?",
                "Hiding implementation details and exposing essential features", "Showing every implementation detail", "Making all variables public", "Creating multiple objects from one class"));
        addQuestion(new Question("Abstraction", "Which keyword is used to declare an abstract class in Java?",
                "abstract", "private", "interface", "extends"));
        addQuestion(new Question("Abstraction", "Can an abstract class be instantiated directly?",
                "No, it cannot be instantiated directly.", "Yes, always", "Only when it has no methods", "Only when it has a constructor"));
        addQuestion(new Question("Abstraction", "What is an abstract method?",
                "A method declared without an implementation", "A method with a private constructor", "A method that cannot be inherited", "A method that must always be static"));
        addQuestion(new Question("Abstraction", "Which keyword is used to define an interface in Java?",
                "interface", "abstract", "implements", "extends"));
        addQuestion(new Question("Abstraction", "What keyword does a class use to implement an interface?",
                "implements", "extends", "inherits", "abstract"));

        // Application
        addQuestion(new Question("Application", "A bank account class hides its balance and allows users to access it through methods. Which OOP principle is demonstrated?",
                "Encapsulation", "Inheritance", "Polymorphism", "Abstraction"));
        addQuestion(new Question("Application", "A Car class inherits common properties from a Vehicle class. Which OOP principle is being used?",
                "Inheritance", "Encapsulation", "Polymorphism", "Abstraction"));
        addQuestion(new Question("Application", "A program uses a single draw() method that behaves differently depending on whether the object is a circle or rectangle. Which OOP principle is demonstrated?",
                "Polymorphism", "Encapsulation", "Inheritance", "Abstraction"));
        addQuestion(new Question("Application", "A user drives a car without needing to understand how its engine works internally. Which OOP principle does this best represent?",
                "Abstraction", "Inheritance", "Encapsulation", "Polymorphism"));
    }
}
