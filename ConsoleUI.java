/**
 * All the screen drawing: colors (ANSI), logo, risk meter, revolver, art.
 * Flags: set USE_COLOR to false if you see strange characters like [31m,
 *        set USE_ANIMATION to false to remove delays and the spinner.
 */
public class ConsoleUI {
    private static final boolean USE_COLOR = true;
    private static final boolean USE_ANIMATION = true;

    public static final String RESET   = "\u001B[0m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String GRAY    = "\u001B[90m";
    public static final String BOLD    = "\u001B[1m";

    private static final String[] RAINBOW = { RED, YELLOW, GREEN, CYAN, MAGENTA };

    private static final String[] LOGO = {
        "     _                        _        _   _        ",
        "    | | __ ___   ____ _      | | ___  | |_| |_ ___  ",
        " _  | |/ _` \\ \\ / / _` |_____| |/ _ \\ | __| __/ _ \\ ",
        "| |_| | (_| |\\ V / (_| |_____| |  __/ | |_| ||  __/ ",
        " \\___/ \\__,_| \\_/ \\__,_|     |_|\\___|  \\__|\\__\\___| "
    };
    private static final String[] BANG_ART = {
        "   ____    _    _   _  ____  _ ",
        "  | __ )  / \\  | \\ | |/ ___|| |",
        "  |  _ \\ / _ \\ |  \\| | |  _ | |",
        "  | |_) / ___ \\| |\\  | |_| ||_|",
        "  |____/_/   \\_\\_| \\_|\\____|(_)"
    };
    private static final String[] SKULL_ART = {
        "                  .-\"\"\"\"\"-.",
        "                 /  _   _  \\ ",
        "                |  (_) (_)  |",
        "                 \\    ^    / ",
        "                  |  ---  | ",
        "                  '-.___.-' "
    };
    private static final String[] TROPHY_ART = {
        "                 ___________",
        "                '._==_==_=_.'",
        "                .-\\:      /-.",
        "               | (|:.     |) |",
        "                '-|:.     |-'",
        "                  \\::.    /",
        "                   '::. .'",
        "                     ) (",
        "                   _.' '._",
        "                  '-------'"
    };

    // ---------------------------------------------------------- basics
    public static boolean colorEnabled() { return USE_COLOR; }

    public static String paint(String text, String color) {
        return USE_COLOR ? color + text + RESET : text;
    }

    public static void println(String text) { System.out.println(text); }

    public static void print(String text) {
        System.out.print(text);
        System.out.flush();
    }

    public static void pause(int millis) {
        if (!USE_ANIMATION) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void rule() {
        println(paint("=".repeat(58), GRAY));
    }

    private static void printArt(String[] art, String color) {
        for (String line : art) println(paint(line, color));
    }

    private static void dots(int count, int millis) {
        for (int i = 0; i < count; i++) {
            print(". ");
            pause(millis);
        }
    }

    // ---------------------------------------------------------- title + menu
    public static void banner() {
        println("");
        if (PixelArt.enabled()) {
            PixelArt.logo();
        } else {
            for (int i = 0; i < LOGO.length; i++) {
                println("   " + paint(LOGO[i], RAINBOW[i % RAINBOW.length] + BOLD));
            }
        }
        println("");
        println(paint("        Java Quiz  x  Russian Roulette", CYAN + BOLD));
        println(paint("     Batangas State University | OOP 101 | Team 13", GRAY));
        println("");
    }

    private static String menuRow(String key, String label) {
        String plain = "  " + key + "  " + label;
        return paint("  |", MAGENTA) + "  " + paint(key, YELLOW + BOLD) + "  " + label
                + " ".repeat(Math.max(0, 29 - plain.length())) + paint("|", MAGENTA);
    }

    public static void printMenu() {
        println(paint("  .-----------------------------.", MAGENTA));
        println(menuRow("[1]", "Start Game"));
        println(menuRow("[2]", "How to Play"));
        println(menuRow("[3]", "Instructor Info"));
        println(menuRow("[4]", "Exit"));
        println(paint("  '-----------------------------'", MAGENTA));
        println("");
    }

    public static void printHowToPlay() {
        println("");
        println(paint("  .--[ HOW TO PLAY ]--------------------------------------.", CYAN));
        println(paint("  |", CYAN) + "  You start with " + paint("5 lives", RED) + " and " + paint("0 points", GREEN) + ".");
        println(paint("  |", CYAN) + "  A random Java question appears each round.");
        println(paint("  |", CYAN));
        println(paint("  |", CYAN) + "  " + paint("[+] CORRECT", GREEN) + "  -> +10 points, you are SAFE.");
        println(paint("  |", CYAN) + "  " + paint("[x] WRONG  ", RED) + "  -> the trigger is pulled!");
        println(paint("  |", CYAN) + "         click -> empty chamber, you survive.");
        println(paint("  |", CYAN) + "         BANG  -> you lose 1 life, cylinder re-spins.");
        println(paint("  |", CYAN));
        println(paint("  |", CYAN) + "  Watch the " + paint("RISK", YELLOW) + " meter: every empty click makes");
        println(paint("  |", CYAN) + "  the next BANG more likely!");
        println(paint("  |", CYAN) + "  Lose all 5 lives, or clear every question, to finish.");
        println(paint("  '---------------------------------------------------------'", CYAN));
    }

    // ---------------------------------------------------------- HUD
    public static String livesBar(int lives) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Player.STARTING_LIVES; i++) {
            sb.append(i < lives ? paint("<3", RED + BOLD) : paint("--", GRAY));
            if (i < Player.STARTING_LIVES - 1) sb.append(' ');
        }
        return sb.toString();
    }

    /** Risk meter: how likely the NEXT trigger pull is to hit. */
    public static String riskBar(Revolver r) {
        int pct = r.riskPercent();
        int filled = Math.min(6, Math.max(1, Math.round(6f / r.chambersLeft())));
        String color = pct >= 50 ? RED : (pct >= 25 ? YELLOW : GREEN);
        return "[" + paint("#".repeat(filled), color) + paint("-".repeat(6 - filled), GRAY) + "] "
                + paint(pct + "%", color + BOLD);
    }

    public static void printRoundHeader(int round, int total, Player player, Revolver revolver) {
        String title = " .--[ ROUND " + round + "/" + total + " ]";
        println("");
        println(paint(title + "-".repeat(Math.max(3, 57 - title.length())) + ".", MAGENTA));
        String lives = paint("LIVES ", CYAN) + (PixelArt.enabled()
                ? paint(player.getLives() + "/" + Player.STARTING_LIVES, RED + BOLD) : livesBar(player.getLives()));
        println(paint(" |", MAGENTA) + "  " + lives
                + "        " + paint("SCORE ", CYAN) + paint(String.valueOf(player.getScore()), GREEN + BOLD));
        println(paint(" |", MAGENTA) + "  " + paint("RISK  ", CYAN) + riskBar(revolver)
                + paint("   (chance the next pull hits)", GRAY));
        println(paint(" '" + "-".repeat(55) + "'", MAGENTA));
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.hearts(player.getLives(), Player.STARTING_LIVES), "    ");
        }
    }

    private static String topicColor(String topic) {
        switch (topic) {
            case "Syntax":             return CYAN;
            case "Data Types":         return YELLOW;
            case "Control Structures": return GREEN;
            default:                   return MAGENTA;
        }
    }

    public static void printQuestion(Question q) {
        println("");
        println("  " + paint("[ " + q.getTopic().toUpperCase() + " ]", topicColor(q.getTopic()) + BOLD));
        println("  " + paint(q.getText(), BOLD));
        println("");
        String[] options = q.getOptions();
        for (int i = 0; i < options.length; i++) {
            char letter = (char) ('A' + i);
            println("     " + paint(letter + ")", YELLOW + BOLD) + "  " + options[i]);
        }
        println("");
    }

    // ---------------------------------------------------------- results of an answer
    public static void printCorrect(int points) {
        println("");
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.check(), "     ");
            println("  " + paint("CORRECT!", GREEN + BOLD) + "  +" + points + " points. You are safe this round.");
            return;
        }
        println(paint("     \\o/", GREEN) + "   " + paint("CORRECT!", GREEN + BOLD) + "  +" + points + " points.");
        println(paint("      |", GREEN) + "    You are safe this round.");
        println(paint("     / \\", GREEN));
    }

    public static void printWrong(Question q) {
        println("");
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.cross(), "       ");
        }
        println("  " + paint("[X] WRONG!", RED + BOLD) + "  The correct answer was "
                + paint(q.getCorrectLetter() + ") " + q.getCorrectAnswer(), GREEN));
    }

    public static void printRiskWarning(Revolver r) {
        println("  " + paint("[!] DANGER:", YELLOW + BOLD) + " 1 in " + r.chambersLeft()
                + " chance of BANG (" + r.riskPercent() + "%)  " + riskBar(r));
    }

    public static void printSuspense() {
        println("");
        print(paint("  The revolver is raised", GRAY));
        dots(3, 450);
        println("");
        print(paint("  Finger on the trigger", GRAY));
        dots(3, 550);
        println("");
        pause(300);
    }

    public static void printSpin() {
        String[] frames = { "|", "/", "-", "\\" };
        if (USE_ANIMATION) {
            for (int i = 0; i < 14; i++) {
                print("\r  Spinning the cylinder  [" + frames[i % 4] + "]   ");
                pause(80);
            }
        }
        print("\r  Spinning the cylinder  " + paint("[OK]", GREEN) + "   \n");
    }

    public static void printInit(Player player, Revolver revolver) {
        println("");
        println(paint("  .--[ INITIALIZING GAME ]-----------------------------.", CYAN));
        println(paint("  |", CYAN) + "  Lives: " + livesBar(player.getLives()) + "     Score: " + player.getScore());
        println(paint("  '------------------------------------------------------'", CYAN));
        printSpin();
        printRevolver(revolver);
        println("  One bullet is hidden in one of six chambers.");
    }

    /** ( ) not fired yet   (o) fired, empty   (*) the bullet */
    public static void printRevolver(Revolver r) {
        println("");
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.cylinder(r), "           ");
            println(paint("        dark = not fired   green = empty   red = BULLET", GRAY));
        } else {
            println("               .-------.");
            println("             " + chamber(r, 0) + "  " + chamber(r, 1));
            println("           " + chamber(r, 5) + "   " + paint("[+]", YELLOW + BOLD) + "   " + chamber(r, 2));
            println("             " + chamber(r, 4) + "  " + chamber(r, 3));
            println("               '-------'");
        }
        println(paint("        chambers fired: " + r.getFiredCount() + "/" + Revolver.CHAMBERS, GRAY));
        println("");
    }

    private static String chamber(Revolver r, int i) {
        if (r.wasFired(i)) {
            return r.isBulletAt(i) ? paint("(*)", RED + BOLD) : paint("(o)", GREEN);
        }
        return paint("( )", GRAY);
    }

    public static void printClick() {
        println(paint("       . . . click!", CYAN + BOLD));
        println("  " + paint("[ OK ]", GREEN + BOLD) + " Empty chamber. You survive!");
    }

    public static void printBang() {
        println("");
        if (PixelArt.enabled()) {
            if (USE_ANIMATION) {                     // quick yellow flash, then red
                PixelArt.bang('Y');
                pause(160);
                print("\u001B[5A");
            }
            PixelArt.bang('r');
        } else {
            printArt(BANG_ART, RED + BOLD);
        }
        println("");
    }

    // ---------------------------------------------------------- endings
    public static void printGameOver() {
        println("");
        rule();
        println("");
        if (PixelArt.enabled()) {
            if (USE_ANIMATION) {                     // quick yellow flash, then red
                PixelArt.gameOver('Y');
                pause(250);
                print("\u001B[11A");
            }
            PixelArt.gameOver('r');
            println("");
            PixelArt.draw(PixelArt.skull(), "             ");
        } else {
            println(paint("                  G A M E   O V E R", RED + BOLD));
            printArt(SKULL_ART, GRAY);
        }
        println("");
        println("                 You ran out of lives.");
        rule();
    }

    public static void printCleared() {
        println("");
        rule();
        println(paint("         ALL QUESTIONS CLEARED - YOU SURVIVED!", YELLOW + BOLD));
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.trophy(), "             ");
        } else {
            printArt(TROPHY_ART, YELLOW);
        }
        rule();
    }

    public static void printRank(String rank, int stars) {
        if (PixelArt.enabled()) {
            PixelArt.draw(PixelArt.stars(stars, 4), "      ");
        }
        println("   RANK  " + paint("[" + "*".repeat(stars) + "-".repeat(4 - stars) + "]", YELLOW + BOLD)
                + "  " + paint(rank.toUpperCase(), YELLOW + BOLD));
        println(paint("   Rookie Coder < Junior Developer < Senior Developer < Java Master", GRAY));
    }
}
