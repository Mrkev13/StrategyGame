import javax.swing.*;
import java.awt.*;

public class Attack {
    // Metoda pro provedení útoku
    public static void attack(JPanel[][] grid, JPanel territoryPanel, Player player, int defense) {
        int row = -1;
        int col = -1;
        // Najít souřadnice vybraného políčka
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == territoryPanel) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Kontrola, zda je vybrané políčko vedle zeleného políčka
        boolean isAdjacentToGreen = false;
        if (row > 0 && grid[row - 1][col].getBackground().equals(Color.GREEN)) {
            isAdjacentToGreen = true;
        } else if (row < grid.length - 1 && grid[row + 1][col].getBackground().equals(Color.GREEN)) {
            isAdjacentToGreen = true;
        } else if (col > 0 && grid[row][col - 1].getBackground().equals(Color.GREEN)) {
            isAdjacentToGreen = true;
        } else if (col < grid[row].length - 1 && grid[row][col + 1].getBackground().equals(Color.GREEN)) {
            isAdjacentToGreen = true;
        }

        // Pokud je vybrané políčko vedle zeleného políčka, můžete provést útok
        if (isAdjacentToGreen) {
            if (player.getArmy() >= defense) {
                // Odebrat obranu z armády hráče
                player.setArmy(player.getArmy() - defense);

                // Změna barvy území na zelenou (úspěšný útok)
                territoryPanel.setBackground(Color.GREEN);
            } else {
                // Zobrazení upozornění o nedostatečné armádě pro útok
                JOptionPane.showMessageDialog(null, "Nemáte dostatečnou armádu pro útok!", "Upozornění", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // Pokud není vybrané políčko vedle zeleného políčka, zobrazte upozornění
            JOptionPane.showMessageDialog(null, "Nelze útočit na toto políčko, není vedle zeleného políčka.", "Upozornění", JOptionPane.WARNING_MESSAGE);
        }
    }
}
