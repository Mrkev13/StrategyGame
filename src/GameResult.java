import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for checking and displaying the game result.
 */
public class GameResult {

    /**
     * Checks the game result and displays the appropriate message.
     *
     * @param grid     The game grid.
     * @param player   The player.
     * @param opponent The opponent.
     * @return
     */
    public static boolean checkGameResult(JPanel[][] grid, Player player, Opponent opponent) {
        boolean playerWins = true;
        boolean opponentWins = true;

        // Check if the player has acquired all opponent's territories
        for (JPanel[] row : grid) {
            for (JPanel cell : row) {
                if (cell.getBackground().equals(Color.RED)) {
                    playerWins = false;
                    break;
                }
            }
        }

        // Check if the opponent has acquired all player's territories
        for (JPanel[] row : grid) {
            for (JPanel cell : row) {
                if (cell.getBackground().equals(Color.GREEN)) {
                    opponentWins = false;
                    break;
                }
            }
        }

        // Display the result
        if (playerWins) {
            JOptionPane.showMessageDialog(null, "Congratulations! You won the game!");
            System.exit(0); // End the program
        } else if (opponentWins) {
            JOptionPane.showMessageDialog(null, "Unfortunately, the opponent has acquired all your territories. You lost the game.");
            System.exit(0); // End the program
        }
        return playerWins;
    }
}
