import java.util.Scanner;

/**
 * Runs the full game flow: menu -> name -> rounds -> results -> play again.
 * Connects Revolver, Player, QuizManager and the console screens.
 */
public class GameController {
    private static final int POINTS_PER_CORRECT = 10;
    private static final int MAX_NAME_LENGTH = 12;

    private final Revolver revolver = new Revolver();
    private final QuizManager quiz = new QuizManager();
    private final Instructor instructor = new Instructor("Ms. Fatima Marie P. Agdon", quiz);
    private final Scanner scanner = new Scanner(System.in);

    private Player player;
    private boolean isRunning;

    // ------------------------------------------------------------
    // Main menu loop
    // ------------------------------------------------------------
    public void run() {
        isRunning = true;
        while (isRunning) {
            ConsoleUI.banner();
            ConsoleUI.printMenu();
            int choice = readMenuChoice(1, 4);
            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    ConsoleUI.printHowToPlay();
                    waitForEnter();
                    break;
                case 3:
                    instructor.displayInfo();   // polymorphism: Instructor's version
                    waitForEnter();
                    break;
                default:
                    isRunning = false;
                    break;
            }
        }
        ConsoleUI.println("");
        ConsoleUI.println(ConsoleUI.paint("  Thanks for playing Java-lette! Goodbye.", ConsoleUI.CYAN));
    }

    // ------------------------------------------------------------
    // One full game session (with replay)
    // ------------------------------------------------------------
    private void startGame() {
        String name = askPlayerName();
        boolean playAgain = true;

        while (playAgain) {
            player = new Player(name);
            quiz.resetQuestions();
            revolver.spin();
            ConsoleUI.printInit(player, revolver);
            waitForEnter();

            int round = 1;
            while (player.isAlive() && quiz.hasMoreQuestions()) {
                playRound(round);
                round++;
            }

            showFinalResult();
            playAgain = askPlayAgain();
        }
    }

    // ------------------------------------------------------------
    // One round: question -> answer -> (trigger pull if wrong)
    // ------------------------------------------------------------
    private void playRound(int round) {
        Question q = quiz.getRandomQuestion();

        ConsoleUI.printRoundHeader(round, quiz.getQuestionCount(), player, revolver);
        ConsoleUI.printQuestion(q);

        int choice = readAnswer();

        if (quiz.checkAnswer(q, choice)) {
            player.addPoints(POINTS_PER_CORRECT);
            player.incrementCorrect();
            ConsoleUI.printCorrect(POINTS_PER_CORRECT);
        } else {
            player.incrementWrong();
            ConsoleUI.printWrong(q);
            ConsoleUI.printRiskWarning(revolver);
            ConsoleUI.println("");
            System.out.print(ConsoleUI.paint("  Press ENTER to pull the trigger...", ConsoleUI.RED));
            scanner.nextLine();
            pullTheTrigger();
        }

        if (player.isAlive() && quiz.hasMoreQuestions()) {
            waitForEnter();
        }
    }

    private void pullTheTrigger() {
        ConsoleUI.printSuspense();

        boolean hit = revolver.pullTrigger();
        ConsoleUI.printRevolver(revolver);
        ConsoleUI.pause(300);

        if (hit) {
            ConsoleUI.printBang();
            player.loseLife();
            ConsoleUI.println("  " + ConsoleUI.paint("[!!] HIT! You lose 1 life.", ConsoleUI.RED + ConsoleUI.BOLD));
            ConsoleUI.println("  Lives: " + ConsoleUI.livesBar(player.getLives()));
            if (player.isAlive()) {
                revolver.spin();
                ConsoleUI.println("");
                ConsoleUI.printSpin();
            }
        } else {
            ConsoleUI.printClick();
        }
    }

    // ------------------------------------------------------------
    // Final score and rank
    // ------------------------------------------------------------
    private void showFinalResult() {
        if (player.isAlive()) {
            ConsoleUI.printCleared();
        } else {
            ConsoleUI.printGameOver();
        }
        ConsoleUI.println("");
        player.displayInfo();               // polymorphism: Player's version
        ConsoleUI.println("");
        ConsoleUI.printRank(getRank(player.getScore()), getStars(player.getScore()));
        ConsoleUI.println("");
    }

    private int getStars(int score) {
        if (score < 50)  return 1;
        if (score < 100) return 2;
        if (score < 150) return 3;
        return 4;
    }

    private String getRank(int score) {
        if (score < 50)  return "Rookie Coder";
        if (score < 100) return "Junior Developer";
        if (score < 150) return "Senior Developer";
        return "Java Master";
    }

    // ------------------------------------------------------------
    // Input helpers (with exception handling)
    // ------------------------------------------------------------
    private int readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("  Enter choice: ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice < min || choice > max) {
                    throw new IllegalArgumentException("out of range");
                }
                return choice;
            } catch (NumberFormatException e) {
                ConsoleUI.println(ConsoleUI.paint("  [!] Invalid input! Please enter a number from "
                        + min + " to " + max + ".", ConsoleUI.RED));
            } catch (IllegalArgumentException e) {
                ConsoleUI.println(ConsoleUI.paint("  [!] Choice out of range! Pick "
                        + min + " to " + max + ".", ConsoleUI.RED));
            }
        }
    }

    /** Returns 0-3 for answers A-D. Keeps asking until the input is valid. */
    private int readAnswer() {
        while (true) {
            System.out.print("  Your answer (A-D): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                if (input.length() != 1 || "ABCD".indexOf(input.charAt(0)) < 0) {
                    throw new IllegalArgumentException("Please choose A, B, C, or D.");
                }
                return input.charAt(0) - 'A';
            } catch (IllegalArgumentException e) {
                ConsoleUI.println(ConsoleUI.paint("  [!] Invalid input! " + e.getMessage(), ConsoleUI.RED));
            }
        }
    }

    private String askPlayerName() {
        while (true) {
            System.out.print("  Enter your name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                ConsoleUI.println(ConsoleUI.paint("  [!] Invalid input! Name cannot be empty.", ConsoleUI.RED));
            } else if (name.length() > MAX_NAME_LENGTH) {
                ConsoleUI.println(ConsoleUI.paint("  [!] Name is too long (max "
                        + MAX_NAME_LENGTH + " characters).", ConsoleUI.RED));
            } else {
                ConsoleUI.println("");
                ConsoleUI.println("  Welcome, " + ConsoleUI.paint(name, ConsoleUI.YELLOW)
                        + "! Good luck... you'll need it.");
                return name;
            }
        }
    }

    private boolean askPlayAgain() {
        while (true) {
            System.out.print("  Play again? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            ConsoleUI.println(ConsoleUI.paint("  [!] Invalid input! Please enter Y or N.", ConsoleUI.RED));
        }
    }

    private void waitForEnter() {
        System.out.print(ConsoleUI.paint("  Press ENTER to continue...", ConsoleUI.GRAY));
        scanner.nextLine();
    }
}
