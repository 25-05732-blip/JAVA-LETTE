/**
 * Base class (Inheritance): shared attributes of every user.
 * Subclasses must implement displayInfo() (Polymorphism).
 */
public abstract class User {
    private static int nextId = 1;

    private final String name;
    private final int id;

    public User(String name) {
        this.name = name;
        this.id = nextId++;
    }

    public String getName() { return name; }
    public int getId()      { return id; }

    public abstract void displayInfo();
}
