# ⌖ Java-lette

A Java-based application that integrates the suspense of Russian Roulette with interactive Java programming quizzes, transforming the learning experience into an engaging, playful activity that motivates students, reinforces core Java and object-oriented concepts, and helps learners build knowledge and confidence through immediate feedback and active participation.

> **Note:** Java-lette is a school project developed for OOP 101 (Object-Oriented Programming) in the Computer Science Department.

## Features

- **Interactive Java Quiz System** – displays a curated set of progressive questions covering Java fundamentals like syntax, data types, control structures, and OOP concepts with instant feedback on every answer
- **Virtual Russian Roulette Mechanics** – implements a 6-chamber revolver using OOP; incorrect answers trigger a trigger pull. If the bullet chamber fires, the player loses a life (contains 5 lives each round); otherwise, they survive and continue playing
- **Score and Progress Tracking** – automatically awards points for correct answers, tracks remaining lives, and displays running totals so players can monitor their performance throughout the game
- **User-Friendly Text-Based Interface** – provides clear prompts, visual cues, and simple navigation, no prior technical experience needed; works seamlessly in any Java-supported console environment
- **Extensible Question Bank** – organizes quiz topics in a modular format, allowing instructors or users to easily add, remove, or update questions to match specific lessons, difficulty levels, or new topics

## Target Users

| User | How they will use the system |
| --- | --- |
| **Beginner Java Students** | Play the game to practice and reinforce core Java concepts, syntax, and OOP principles in an engaging, low-pressure way; answer quiz questions to progress and see immediate feedback on their knowledge |
| **Programming Instructors** | Use the application as a supplementary learning tool in class or lab sessions; review student performance trends; add or edit quiz questions to match their lesson plan and current topics |
| **Self-Learners and Reviewer** | Run the program independently to test their understanding, prepare for quizzes or exams, and identify knowledge gaps, turning study sessions into an enjoyable, self-paced activity |

## Getting Started

Follow the instructions below to get a local copy of **Java-lette** up and running.

```bash
git clone [https://github.com/25-05732-blip/JAVA-LETTE](https://github.com/25-05732-blip/JAVA-LETTE)
javac *.java
java JavaLette

```

> Replace `JavaLette` with the name of your class that contains the `main` method if it is different.

## Java and OOP Concepts Used

| Concept | Where it is used |
| --- | --- |
| Classes and Objects | Core entities are defined as classes: `Revolver`, `Player`, `QuizQuestion`, `GameController`. Objects are instantiated to represent the game's revolver, the current player, each quiz item, and the main game flow forming the entire application structure. |
| Methods | Each class contains purpose-built methods for its behavior: `spinChamber()` and `pullTrigger()` for the revolver; `addPoints()` and `loseLife()` for the player; `getRandomQuestion()` for quizzes. Methods encapsulate actions so logic is organized and reusable. |
| Encapsulation | Internal state such as the revolver's loaded chamber position, player lives, and score is kept private with restricted direct access. State is only read or modified through dedicated methods, protecting data and preventing unintended changes. |
| Inheritance | A base `User` class holds shared attributes (name, ID); subclasses `StudentPlayer` and `Instructor` extend it to add unique features; `StudentPlayer` tracks score/lives, while `Instructor` gains permission to manage or update the question bank. |
| Polymorphism | The `displayInfo()` method is defined in the base `User` class and implemented differently in each subclass: it shows game stats and progress for `StudentPlayer`, while showing question count or lesson topic info for `Instructor`. |
| Abstraction | Complex internal mechanics like spinning the chamber, randomizing questions, and validating answers are hidden behind simple method calls like `startGame()` or `checkAnswer()`. Users interact with clear prompts without needing to understand the underlying code. |
| Exception Handling | `try-catch` blocks handle invalid user input such as entering text where a response is expected, or out-of-range menu choices preventing crashes and prompting the user to re-enter valid input smoothly. |
| Arrays / Data Handling | Arrays or collections store the quiz question bank, track used questions, and manage high-score records. They enable easy iteration, random selection, and organized management of the game's content and persistent data. |

## Proposed Classes

| Class | Purpose | Main Attributes | Main Methods |
| --- | --- | --- | --- |
| `Revolver` | Implements the Russian Roulette mechanism; manages chambers, bullet position, and trigger pulls | `CHAMBERS = 6` (`final int`), `bulletPosition` (`int`), `currentPosition` (`int`) | `spin()`, `pullTrigger()`, `isBulletHere()` |
| `Player` | Stores player identity, lives, and score; tracks game progress | `playerName` (`String`), `lives` (`int`), `score` (`int`) | `addPoints(int points)`, `loseLife()`, `isAlive()`, `getStats()` |
| `QuizManager` | Holds Java questions, selects them randomly, and validates answers | `questionList` (array/list of questions), `correctAnswers` (matching array) | `getRandomQuestion()`, `checkAnswer(input, correct)`, `addQuestion(q, a)` |
| `GameController` | Runs the full game flow, connects revolver, player, and quiz; handles rounds and end conditions | `revolver` (`Revolver`), `player` (`Player`), `quiz` (`QuizManager`), `isRunning` (`boolean`) | `startGame()`, `playRound()`, `showFinalResult()` |


## Program Flow

1. **Start** → Enter Player Name
2. **Initialize Game** → 5 lives, score = 0
3. **Spin Revolver** → Bullet placed in random chamber
4. **Receive Java Quiz Question** → Submit Answer
5. **Validate Answer:**
* **Correct** → `+10` Points → Safe → Next Round
* **Incorrect** → Pull Trigger:
* **Empty Click** → Survive → Next Round
* **Hit** → Lose 1 Life:
* **Lives Left** → Spin & Continue
* **No Lives Remaining** → Game Over






6. **Display Final Score & Performance Rank**
7. **Play Again?**
* **Yes** → Start New Game
* **No** → Exit



## Expected Output

```text
========================================================
        ⌖  J A V A - L E T T E  ⌖
     Java Quiz  x  Russian Roulette
========================================================

  [1] Start Game
  [2] How to Play
  [3] Exit

  Enter choice: abc
  ✘ Invalid input! Please enter a number from 1 to 3.

  Enter choice: 2

--------------------------------------------------------
                     HOW TO PLAY
--------------------------------------------------------
  • You start with 5 lives (♥) and 0 points.
  • A Java question appears each round.
  • Correct answer  -> +10 points, you are SAFE.
  • Wrong answer    -> the trigger is pulled!
        - Click!  -> You survive.
        - BANG!   -> You lose 1 life.
  • Lose all 5 lives and the game is over.
--------------------------------------------------------
  Press ENTER to go back...

  Enter choice: 1

  Enter your name: Jasmine

  Welcome, Jasmine! Good luck... you'll need it.

========================================================
  Initializing game...
  Lives: ♥ ♥ ♥ ♥ ♥        Score: 0
  Spinning the revolver... *click-click-click-click*
  The bullet is now hidden in one of 6 chambers.
========================================================

--------------------------------------------------------
  ROUND 1                 Lives: ♥ ♥ ♥ ♥ ♥   Score: 0
--------------------------------------------------------
  [Topic: Syntax]
  Which of the following is the correct entry point
  of a Java program?

    A. public void main(String[] args)
    B. public static void main(String[] args)
    C. static public main(String args)
    D. public static int main(String[] args)

  Your answer (A-D): B

  ✔ CORRECT! +10 points. You are safe this round.

--------------------------------------------------------
  ROUND 2                 Lives: ♥ ♥ ♥ ♥ ♥   Score: 10
--------------------------------------------------------
  [Topic: Data Types]
  Which data type is used to store a true/false value?

    A. int
    B. String
    C. boolean
    D. char

  Your answer (A-D): E
  ✘ Invalid input! Please choose A, B, C, or D.

  Your answer (A-D): A

  ✘ WRONG! The correct answer was C. boolean.
  The revolver is raised... the trigger is pulled...

      . . . click!

  ♥ You survived! The chamber was empty.

--------------------------------------------------------
  ROUND 3                 Lives: ♥ ♥ ♥ ♥ ♥   Score: 10
--------------------------------------------------------
  [Topic: Control Structures]
  Which loop is guaranteed to execute at least once?

    A. for
    B. while
    C. do-while
    D. for-each

  Your answer (A-D): A

  ✘ WRONG! The correct answer was C. do-while.
  The revolver is raised... the trigger is pulled...

      . . . B A N G !

  ☠ You were hit! You lose 1 life.
  Lives remaining: ♥ ♥ ♥ ♥ ♡
  Re-spinning the revolver... *click-click-click*

--------------------------------------------------------
  ROUND 4                 Lives: ♥ ♥ ♥ ♥ ♡   Score: 10
--------------------------------------------------------
  [Topic: OOP]
  Which OOP principle hides internal data using
  private access modifiers?

    A. Inheritance
    B. Polymorphism
    C. Encapsulation
    D. Abstraction

  Your answer (A-D): C

  ✔ CORRECT! +10 points. You are safe this round.

          [ ... Rounds 5 to 14 omitted ... ]

--------------------------------------------------------
  ROUND 15                Lives: ♥ ♡ ♡ ♡ ♡   Score: 70
--------------------------------------------------------
  [Topic: OOP]
  Which keyword is used to inherit from a class?

    A. implements
    B. extends
    C. inherits
    D. super

  Your answer (A-D): A

  ✘ WRONG! The correct answer was B. extends.
  The revolver is raised... the trigger is pulled...

      . . . B A N G !

  ☠ You were hit! You lose 1 life.
  Lives remaining: ♡ ♡ ♡ ♡ ♡

========================================================
                    G A M E   O V E R
========================================================
  You have run out of lives, Jasmine.

  ----------------- FINAL RESULTS ----------------------
   Player            : Jasmine
   Rounds Played     : 15
   Correct Answers   : 7
   Wrong Answers     : 8
   Times Shot        : 5
   Final Score       : 70
   Performance Rank  : ★★★ Junior Developer ★★★
  ------------------------------------------------------

   Rank Guide:
     0 - 30   : Rookie Coder
     40 - 80  : Junior Developer
     90 - 130 : Senior Developer
     140+     : Java Master

  Keep practicing, you're getting there!

========================================================
  Play again? (Y/N): maybe
  ✘ Invalid input! Please enter Y or N.

  Play again? (Y/N): N

  Thanks for playing Java-lette, Jasmine! Goodbye. ♠
========================================================

```

## Limitations

1. Data such as scores, player progress, and game history are stored temporarily in memory and are not saved to a file or database; all information is lost when the program terminates.
2. The question bank is preloaded with Java fundamentals only; it does not cover advanced Java topics, frameworks, or higher-level programming concepts beyond basic syntax and introductory OOP.
3. The application is designed for single-player use only; it does not support multiplayer, competitive, or turn-based game modes.
4. While the question bank is structured to be extensible in code, no in-application interface is provided for adding, editing, or managing questions during runtime — all updates require modifying the source code directly.
5. The application is limited to a text-based console interface; it does not include a graphical user interface (GUI) or advanced visual design elements.

## Development Schedule

| Week | Activity | Expected Output |
| --- | --- | --- |
| Week 1 | Planning and requirements | Project proposal |
| Week 2 | System and class design | Class/UML diagram |
| Week 3 | Initial coding | Basic classes |
| Week 4–5 | Feature development | Working features |
| Week 6 | OOP implementation | Completed OOP features |
| Week 7 | Testing and debugging | Tested application |
| Week 8 | Documentation and presentation | Final project |

## Team

**Team The Clique** (Team 13, CS - 2102) — OOP 101, 1st Semester, AY 2026-2027

* **Cesa, Ma. Mikaella Andrea R.** (SR-Code: `25-03885` | GitHub: `Andrea Cesa`) – Quiz System Data Handling
* **Lajara, Jasmine Gaile M.** (SR-Code: `25-05732` | GitHub: `Yumeko`) – Game Flow and Interface
* **Marquez, Reign C.** (SR-Code: `25-09651` | GitHub: `25-09651`) – Core Game and OOP Structure

**Instructor:** Ms. Fatima Marie P. Agdon, MSCS

**Repository:** [JAVA-LETTE](https://github.com/25-05732-blip/JAVA-LETTE)

```
