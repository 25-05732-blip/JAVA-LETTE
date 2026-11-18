import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Pixel art drawn INSIDE the terminal.
 * Every "pixel" is two spaces with an ANSI background color, so it needs no
 * special characters and no images. Sprites are written as small text maps:
 * a letter is a color, a dot is empty space.
 */
public class PixelArt {
    private static final String INDENT = "   ";

    // ---------------------------------------------------------- sprites
    private static final String[] HEART = { ".#.#.", "#####", "#####", ".###.", "..#.." };
    private static final String[] STAR  = { "..#..", ".###.", "#####", ".###.", ".#.#." };
    private static final String[] CHECK = {
        "........G", ".......GG", "G.....GG.", "GG...GG..", ".GG.GG...", "..GGG....", "...G....." };
    private static final String[] CROSS = { "R...R", ".R.R.", "..R..", ".R.R.", "R...R" };
    private static final String[] SKULL = {
        "...WWWWW...", "..WWWWWWW..", ".WWWWWWWWW.", ".WKKWWWKKW.", ".WKKWWWKKW.",
        ".WWWWKWWWW.", "..WWWWWWW..", "...WWWWW...", "...W.W.W..." };
    private static final String[] TROPHY = {
        ".YYYYYYYYY.", "YYYYYYYYYYY", "Y.YYYYYYY.Y", "Y.YYYYYYY.Y", ".YYYYYYYYY.", "..YYYYYYY..",
        "...YYYYY...", "....YYY....", "....YYY....", "...YYYYY...", "..YYYYYYY.." };

    // 3x5 pixel font
    private static final Map<Character, String[]> FONT = new HashMap<>();
    static {
        FONT.put('J', new String[]{ "..#", "..#", "..#", "#.#", ".#." });
        FONT.put('A', new String[]{ ".#.", "#.#", "###", "#.#", "#.#" });
        FONT.put('V', new String[]{ "#.#", "#.#", "#.#", "#.#", ".#." });
        FONT.put('-', new String[]{ "...", "...", "###", "...", "..." });
        FONT.put('L', new String[]{ "#..", "#..", "#..", "#..", "###" });
        FONT.put('E', new String[]{ "###", "#..", "##.", "#..", "###" });
        FONT.put('T', new String[]{ "###", ".#.", ".#.", ".#.", ".#." });
        FONT.put('B', new String[]{ "##.", "#.#", "##.", "#.#", "##." });
        FONT.put('N', new String[]{ "#.#", "###", "###", "#.#", "#.#" });
        FONT.put('G', new String[]{ ".##", "#..", "#.#", "#.#", ".##" });
        FONT.put('M', new String[]{ "#...#", "##.##", "#.#.#", "#...#", "#...#" });
        FONT.put('O', new String[]{ "###", "#.#", "#.#", "#.#", "###" });
        FONT.put('R', new String[]{ "##.", "#.#", "##.", "#.#", "#.#" });
    }

    public static boolean enabled() {
        return ConsoleUI.colorEnabled();
    }

    // ---------------------------------------------------------- drawing
    private static int code(char c) {
        switch (c) {
            case 'R': return 160;  // red
            case 'r': return 196;  // bright red
            case 'O': return 208;  // orange
            case 'Y': return 220;  // yellow
            case 'G': return 40;   // green
            case 'C': return 45;   // cyan
            case 'M': return 171;  // magenta
            case 'W': return 255;  // white
            case 'K': return 234;  // near black
            case 'S': return 244;  // steel
            case 's': return 250;  // light steel
            case 'D': return 238;  // dark gray
            default:  return 15;
        }
    }

    private static String cell(char c) {
        if (c == '.') return "  ";
        return "\u001B[48;5;" + code(c) + "m  \u001B[0m";
    }

    public static void draw(String[] rows, String indent) {
        for (String row : rows) {
            StringBuilder sb = new StringBuilder(indent);
            for (char c : row.toCharArray()) sb.append(cell(c));
            ConsoleUI.println(sb.toString());
        }
    }

    public static void draw(String[] rows) {
        draw(rows, INDENT);
    }

    // ---------------------------------------------------------- builders
    /** Pixel text from the 3x5 font. 'colors' gives one color letter per character (it cycles). */
    public static String[] text(String word, String colors) {
        String[] rows = new String[5];
        Arrays.fill(rows, "");
        for (int i = 0; i < word.length(); i++) {
            String[] glyph = FONT.get(word.charAt(i));
            char color = colors.charAt(i % colors.length());
            for (int r = 0; r < 5; r++) rows[r] += glyph[r].replace('#', color) + ".";
        }
        return rows;
    }

    public static void logo() {
        draw(text("JAVA-", "rOYGC"));
        ConsoleUI.println("");
        draw(text("LETTE", "CGYOr"));
    }

    public static void bang(char color) {
        draw(text("BANG", String.valueOf(color)), "         ");
    }

    /** Full pixel "GAME OVER" title, stacked on two lines and centered in the 58-wide frame. */
    public static void gameOver(char color) {
        draw(text("GAME", String.valueOf(color)), "           ");
        ConsoleUI.println("");
        draw(text("OVER", String.valueOf(color)), "             ");
    }

    public static String[] hearts(int lives, int max) {
        String[] rows = new String[5];
        Arrays.fill(rows, "");
        for (int i = 0; i < max; i++) {
            char c = i < lives ? 'R' : 'D';
            for (int r = 0; r < 5; r++) rows[r] += HEART[r].replace('#', c) + ".";
        }
        return rows;
    }

    public static String[] stars(int filled, int max) {
        String[] rows = new String[5];
        Arrays.fill(rows, "");
        for (int i = 0; i < max; i++) {
            char c = i < filled ? 'Y' : 'D';
            for (int r = 0; r < 5; r++) rows[r] += STAR[r].replace('#', c) + ".";
        }
        return rows;
    }

    /** The revolver cylinder: dark = not fired, green = fired and empty, red = the bullet. */
    public static String[] cylinder(Revolver rev) {
        final int n = 15, c = 7;
        int[][] holes = new int[Revolver.CHAMBERS][2];
        for (int k = 0; k < Revolver.CHAMBERS; k++) {
            double a = -Math.PI / 2 + k * Math.PI / 3;
            holes[k][0] = c + (int) Math.round(4.4 * Math.cos(a));
            holes[k][1] = c + (int) Math.round(4.4 * Math.sin(a));
        }
        String[] rows = new String[n];
        for (int j = 0; j < n; j++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                double d = Math.hypot(i - c, j - c);
                char ch = '.';
                if (d <= 7.4) ch = d > 6.2 ? 's' : 'S';
                if (d <= 1.2) ch = 'D';
                for (int k = 0; k < Revolver.CHAMBERS; k++) {
                    if (Math.hypot(i - holes[k][0], j - holes[k][1]) <= 1.35) {
                        if (rev.wasFired(k)) ch = rev.isBulletAt(k) ? 'r' : 'G';
                        else ch = 'K';
                    }
                }
                sb.append(ch);
            }
            rows[j] = sb.toString();
        }
        return rows;
    }

    // ---------------------------------------------------------- ready-made sprites
    public static String[] check()  { return CHECK; }
    public static String[] cross()  { return CROSS; }
    public static String[] skull()  { return SKULL; }
    public static String[] trophy() { return TROPHY; }
}
