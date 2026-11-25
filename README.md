# 🔫 JAVA-LETTE — Java Quiz × Russian Roulette

**Java-lette** is a Java console game that blends a Java OOP quiz with the suspense of Russian Roulette. Answer correctly and you are safe. Answer wrong and the trigger is pulled. Lose all 5 lives and it's game over.

---

## 💭 Overview

Many beginner students find learning Java dry and unengaging. **Java-lette** turns practice into play: every question reinforces a core OOP concept, and the game format makes mistakes feel like part of the fun instead of a setback.

You get a random OOP question each round. A correct answer earns **+10 points** and keeps you safe. A wrong answer means the revolver's trigger is pulled. If the bullet fires, you lose a life and the cylinder re-spins. If it clicks, you survive, but the risk of the next BANG goes up.

**Features:**

- 🎯 40 Java OOP questions in **random order**, no repeats, with shuffled A–D choices
- 🔫 6-chamber revolver with real bullet mechanics (spin, trigger pull, re-spin after a hit)
- 📈 **Risk meter** that shows the chance the next trigger pull hits
- ❤️ 5 lives, +10 points per correct answer, and a performance rank
- 🎨 Color-coded terminal text
- 👾 **Pixel art drawn inside the terminal** (title logo, hearts, revolver cylinder, BANG, skull, trophy, star rank)
- 🛡️ Input validation with `try-catch`, so bad input never crashes the game
- 👩‍🏫 Instructor Info screen that shows the question bank topics and size
- ⚙️ Plain JDK, no external libraries required

---

## 🎲 Inspiration

The dark, high-stakes "survive the gamble" idea behind Java-lette is inspired by **Buckshot Roulette**, an indie game by **Mike Klubnika** that turns Russian Roulette into a survival game.

- Reference: https://buckshotroulette.com/online

Java-lette is a non-commercial, educational class project. It is a quiz game with its own rules, code and pixel art, and it is not affiliated with or endorsed by Buckshot Roulette or its creator.

---

## 🎮 How the Game Works

```
Start
  ↓
Enter Player Name
  ↓
Initialize Game (5 lives, score = 0)
  ↓
Spin Revolver → bullet placed in a random chamber
  ↓
Receive a random Java OOP question
  ↓
Submit Answer
  ├─ Correct   → +10 points → Safe → Next round
  └─ Incorrect → Pull Trigger
        ├─ Empty click → Survive → Next round
        └─ Hit → Lose 1 life
              ├─ Lives left        → Re-spin & continue
              └─ No lives remaining → Game Over
  ↓
(All questions answered → Game Cleared)
  ↓
Final score & performance rank
  ↓
Play again?  Yes → new game   No → Exit
```

**Performance rank** (based on final score):

| Score | Rank |
|---|---|
| below 50 | Rookie Coder |
| 50 – 99 | Junior Developer |
| 100 – 149 | Senior Developer |
| 150 and above | Java Master |

---

## 📦 OOP Concepts Applied

### 💊 Encapsulation

All fields are `private` and are read or changed only through methods, so the game state can't be changed by accident.

```java
// Player.java
public class Player extends User {
    public static final int STARTING_LIVES = 5;

    private int lives;
    private int score;

    public void addPoints(int points) { score += points; }

    public void loseLife() {
        if (lives > 0) lives--;
        timesShot++;
    }

    public boolean isAlive() { return lives > 0; }
}
```

The revolver's bullet position is hidden the same way. The rest of the program can only call `spin()`, `pullTrigger()` and `isBulletHere()`.

### 💖 Abstraction

`User` is an **abstract class**. It declares what every user must do (`displayInfo()`) without saying how. Complex mechanics like spinning the chamber, randomizing questions and validating answers are hidden behind simple calls like `startGame()`, `getRandomQuestion()` and `checkAnswer()`.

```java
// User.java
public abstract class User {
    private final String name;
    private final int id;

    public User(String name) { ... }

    public String getName() { return name; }
    public int getId()      { return id; }

    public abstract void displayInfo();
}
```

### 🔗 Inheritance

`Player` and `Instructor` both **extend** the abstract `User` class, so they reuse the shared `name` and `id` and add their own features.

```java
public class Player extends User { ... }       // tracks lives, score, accuracy
public class Instructor extends User { ... }   // shows question bank information
```

### ✨ Polymorphism

`displayInfo()` is declared once in `User` and **implemented differently** in each subclass:

- `Player.displayInfo()` prints the final results table (score, accuracy, times shot).
- `Instructor.displayInfo()` prints the question count and the lesson topics.

```java
// Instructor.java
@Override
public void displayInfo() {
    ConsoleUI.println("  Instructor     : " + getName() + " (ID " + getId() + ")");
    ConsoleUI.println("  Questions      : " + quizManager.getQuestionCount() + " in the bank");
    ConsoleUI.println("  Lesson topics  : " + String.join(", ", quizManager.getTopics()));
}
```

### 🛡️ Exception Handling

`try-catch` blocks handle text where a number is expected, out-of-range menu choices, and answers other than A–D. The game asks again instead of crashing.

```java
// GameController.java
try {
    if (input.length() != 1 || "ABCD".indexOf(input.charAt(0)) < 0) {
        throw new IllegalArgumentException("Please choose A, B, C, or D.");
    }
    return input.charAt(0) - 'A';
} catch (IllegalArgumentException e) {
    ConsoleUI.println("  [!] Invalid input! " + e.getMessage());
}
```

### 🗂️ Arrays / Collections

- `ArrayList<Question>` holds the question bank (`questionList`) and the pool of questions not used yet (`remaining`), so no question repeats in a game.
- A `boolean[]` tracks which revolver chambers have been fired.

```java
// QuizManager.java
public Question getRandomQuestion() {
    if (remaining.isEmpty()) return null;
    Question q = remaining.remove(random.nextInt(remaining.size()));
    q.shuffleOptions();
    return q;
}
```

---

## 🛠️ Program Structure

```
JAVA-LETTE/
├─ README.md
├─ Main.java
├─ GameController.java
├─ Revolver.java
├─ Player.java
├─ User.java
├─ Instructor.java
├─ Question.java
├─ QuizManager.java
├─ ConsoleUI.java
└─ PixelArt.java
```

### Main Roles

| Class | Role |
|---|---|
| **Main** | Entry point. Starts the `GameController`. |
| **GameController** | Runs the full game flow: menu, name entry, rounds, final results, play again. Connects the revolver, player and quiz. |
| **Revolver** | 6-chamber revolver. `spin()` places the bullet, `pullTrigger()` returns true if it fires, `riskPercent()` gives the chance of a hit. |
| **Player** *(extends User)* | The student playing. Tracks lives, score, correct and wrong answers, and times shot. |
| **User** *(abstract)* | Base class with the shared `name` and `id`. Declares the abstract `displayInfo()`. |
| **Instructor** *(extends User)* | Instructor role. Shows the question bank size and lesson topics. |
| **Question** | One quiz item. Stores topic, text, correct answer and shuffled choices. |
| **QuizManager** | Holds the 40-question bank, picks random questions without repeats, validates answers. |
| **ConsoleUI** | Draws the colored screens, menus, HUD and messages. |
| **PixelArt** | Draws pixel-art sprites and a 3×5 pixel font in the terminal using colored cells. |

---

## 🖥️ How to Run the Program

**Prerequisites:**

- JDK 17 or newer (tested with JDK 21). Download it from [Adoptium](https://adoptium.net/temurin/releases/).
- A terminal that supports ANSI colors, such as the **VS Code terminal** or **Windows Terminal**.

### Option 1: VS Code

1. Install the **Extension Pack for Java** (by Microsoft).
2. Open this folder in VS Code (**File → Open Folder**).
3. Open `Main.java` and click **Run** above `main`.
4. Click inside the **Terminal** panel and type your answers there.

### Option 2: Command line

From the project folder:

```
javac *.java
java Main
```

**Controls:** type your choice and press **ENTER**. Menu choices are `1`–`4`, answers are `A`–`D`, and `Y`/`N` for play again.

> **Tip:** If you see strange characters like `[48;5;160m`, your console doesn't support ANSI colors. In `ConsoleUI.java`, set `USE_COLOR` to `false` for a plain ASCII version. Set `USE_ANIMATION` to `false` to remove the delays and spinner.

---

## 👾 Sample Output

```
  Enter choice: abc
  [!] Invalid input! Please enter a number from 1 to 4.
  Enter choice: 1
  Enter your name: Jasmine

 .--[ ROUND 2/40 ]---------------------------------------.
 |  LIVES <3 <3 <3 <3 <3        SCORE 10
 |  RISK  [#-----] 17%   (chance the next pull hits)
 '-------------------------------------------------------'

  [ INHERITANCE ]
  Which keyword is used to inherit a class in Java?

     A)  implements
     B)  this
     C)  extends
     D)  super

  Your answer (A-D): A

  [X] WRONG!  The correct answer was C) extends
  [!] DANGER: 1 in 6 chance of BANG (17%)  [#-----] 17%

  Press ENTER to pull the trigger...

  The revolver is raised. . .
  Finger on the trigger. . .
```

<!--
Add your screenshots to a "static" folder, then delete the comment markers and edit the file names:

![Title and Menu](static/Title.png)
![Gameplay](static/Gameplay.png)
![Game Over](static/GameOver.png)
-->

---

## ⚙️ Customize It

| Want to change | Where |
|---|---|
| Add, remove or edit questions | `QuizManager.java` → `loadDefaultQuestions()` (format: `new Question(topic, question, CORRECT, wrong1, wrong2, wrong3)`) |
| Points per correct answer | `GameController.java` → `POINTS_PER_CORRECT` |
| Starting lives | `Player.java` → `STARTING_LIVES` |
| Rank cutoffs | `GameController.java` → `getRank()` and `getStars()` |
| Colors and animation | `ConsoleUI.java` → `USE_COLOR`, `USE_ANIMATION` |

---

## 🚧 Project Limitations

1. Scores, progress and game history are kept only in memory and are lost when the program closes.
2. The question bank has 40 introductory OOP questions only. It doesn't cover advanced Java topics or frameworks.
3. Single-player only.
4. Questions can only be added or edited in the source code, not inside the running app.
5. The interface is a text-based terminal. The colors and pixel art use ANSI color codes, so a terminal that supports them is needed for the full look. There is no GUI.

---

## ✨ Authors & Credits

**Team 13 — The Clique** (CS-2102)

| Name | SR-Code | Role | GitHub |
|---|---|---|---|
| **Ma. Mikaella Andrea R. Cesa** | 25-03885 | Quiz system data handling: question bank, random selection, answer validation, input handling | Andrea Cesa |
| **Jasmine Gaile M. Lajara** | 25-05732 | Game flow and interface: `GameController`, console screens, scoring display, rank, integration, documentation | Yumeko |
| **Reign C. Marquez** | 25-09651 | Core game and OOP structure: `Revolver`, `Player`, OOP principles, testing | 25-09651 |

Thank you to our professor, **Ms. Fatima Marie P. Agdon, MSCS**, for guiding us through OOP 101.

### 📖 Question Source

The quiz questions are based on the Java tutorials of **GeeksforGeeks**. Thank you for the free learning resources that made this project possible.

- https://www.geeksforgeeks.org/java/java/

---

## 🔭 Future Enhancements

- Save high scores to a file
- A larger question bank with difficulty levels
- Add or edit questions from inside the app
- Multiplayer mode

## 📚 References

- GeeksforGeeks, Java tutorials (source of the quiz questions): https://www.geeksforgeeks.org/java/java/
- Buckshot Roulette by Mike Klubnika (inspiration for the game concept): https://buckshotroulette.com/online
- Java 17+ standard library
- Object-oriented design principles: Abstraction, Encapsulation, Inheritance, Polymorphism

---

## 💖 Thank You for Playing!

Thanks for checking out **Java-lette**. We hope learning OOP felt a little more exciting, and a little more dangerous. 🔫

> 📌 Repository: https://github.com/25-05732-blip/JAVA-LETTE
