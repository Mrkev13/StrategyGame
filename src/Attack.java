import javax.swing.*;
import java.awt.*;

/**
 * Class for handling attack actions in the game.
 */
public class Attack {

    /**
     * Executes an attack on a specified territory.
     *
     * @param grid the game grid
     * @param territoryPanel the panel representing the territory to attack
     * @param player the player performing the attack
     * @param defense the defense strength of the target territory
     */
    public static void attack(JPanel[][] grid, JPanel territoryPanel, Player player, int defense) {
        int row = -1;
        int col = -1;

        // Find the coordinates of the selected territory
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == territoryPanel) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Check if the selected territory is adjacent to a green territory or a green territory with text
        boolean isAdjacentToGreen = false;
        if (row > 0 && (grid[row - 1][col].getBackground().equals(Color.GREEN) || isGreenWithText(grid[row - 1][col]))) {
            isAdjacentToGreen = true;
        } else if (row < grid.length - 1 && (grid[row + 1][col].getBackground().equals(Color.GREEN) || isGreenWithText(grid[row + 1][col]))) {
            isAdjacentToGreen = true;
        } else if (col > 0 && (grid[row][col - 1].getBackground().equals(Color.GREEN) || isGreenWithText(grid[row][col - 1]))) {
            isAdjacentToGreen = true;
        } else if (col < grid[row].length - 1 && (grid[row][col + 1].getBackground().equals(Color.GREEN) || isGreenWithText(grid[row][col + 1]))) {
            isAdjacentToGreen = true;
        }

        // If the selected territory is adjacent to a green territory, proceed with the attack
        if (isAdjacentToGreen) {
            if (player.getArmy() >= defense) {
                if (territoryPanel.getBackground().equals(Color.WHITE) || territoryPanel.getBackground().equals(Color.RED)) {
                    // Check if the panel already has a label with text
                    JLabel existingLabel = null;
                    for (Component component : territoryPanel.getComponents()) {
                        if (component instanceof JLabel) {
                            existingLabel = (JLabel) component;
                            break;
                        }
                    }

                    // If no existing label is found, create a new one
                    if (existingLabel == null) {
                        JLabel basicLabel = new JLabel("Basic");
                        basicLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        territoryPanel.add(basicLabel);
                    }
                }
                // Subtract defense from the player's army
                player.setArmy(player.getArmy() - defense);

                // Change the territory color to green (successful attack)
                territoryPanel.setBackground(Color.GREEN);

            } else {
                // Display a warning message if the player doesn't have enough army for the attack
                JOptionPane.showMessageDialog(null, "You do not have enough army to attack!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // Display a warning message if the selected territory is not adjacent to a green territory
            JOptionPane.showMessageDialog(null, "Cannot attack this territory, it is either too far or diagonally positioned from a green territory.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Checks if a JPanel is green and contains text.
     *
     * @param panel the JPanel to check
     * @return true if the panel is green and contains text, false otherwise
     */
    private static boolean isGreenWithText(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (panel.getBackground().equals(Color.GREEN) && label.getText() != null && !label.getText().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
