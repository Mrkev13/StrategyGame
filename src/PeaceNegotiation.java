import javax.swing.*;
import java.util.Random;

/**
 * Class responsible for handling peace negotiations.
 */
public class PeaceNegotiation {
    private static final int BASE_PEACE_CHANCE = 1; // Base peace chance is 1% per territory

    /**
     * Attempts to negotiate peace.
     *
     * @param player The player attempting peace.
     * @param playerTerritories The number of territories the player owns.
     */
    public static void attemptPeace(Player player, int playerTerritories) {
        int peaceChance = playerTerritories * BASE_PEACE_CHANCE;
        Random rand = new Random();
        int roll = rand.nextInt(100) + 1; // Value from 1 to 100

        if (roll <= peaceChance) {
            JOptionPane.showMessageDialog(null, "The peace treaty was successfully negotiated!");
            JOptionPane.showMessageDialog(null, "The war ended with a peace agreement.");
            JOptionPane.showMessageDialog(null, "However, the enemy side is still arming");
            JOptionPane.showMessageDialog(null, "So beware of another possible war.");
        } else {
            JOptionPane.showMessageDialog(null, "Peace negotiation failed.");
        }
    }
}
