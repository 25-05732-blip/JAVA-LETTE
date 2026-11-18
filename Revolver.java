import java.util.Arrays;
import java.util.Random;

/**
 * 6-chamber revolver. The bullet is placed in a random chamber by spin().
 * Each trigger pull checks the current chamber, then moves to the next one.
 */
public class Revolver {
    public static final int CHAMBERS = 6;

    private int bulletPosition;
    private int currentPosition;
    private final boolean[] fired = new boolean[CHAMBERS];
    private final Random random = new Random();

    public Revolver() {
        spin();
    }

    /** Randomly places the bullet and resets the cylinder. */
    public void spin() {
        bulletPosition = random.nextInt(CHAMBERS);
        currentPosition = 0;
        Arrays.fill(fired, false);
    }

    /** Checks the chamber that is currently lined up with the barrel. */
    public boolean isBulletHere() {
        return currentPosition == bulletPosition;
    }

    /** Pulls the trigger. Returns true if the bullet fires. */
    public boolean pullTrigger() {
        boolean hit = isBulletHere();
        fired[currentPosition] = true;
        currentPosition = (currentPosition + 1) % CHAMBERS;
        return hit;
    }

    /** How many chambers have not been fired since the last spin. */
    public int chambersLeft() {
        return Math.max(1, CHAMBERS - getFiredCount());
    }

    /** Chance (in %) that the next trigger pull hits the bullet. */
    public int riskPercent() {
        return Math.round(100f / chambersLeft());
    }

    // Read-only helpers used only to draw the cylinder on screen
    public boolean wasFired(int chamber)   { return fired[chamber]; }
    public boolean isBulletAt(int chamber) { return chamber == bulletPosition; }

    public int getFiredCount() {
        int count = 0;
        for (boolean f : fired) if (f) count++;
        return count;
    }
}
