import javax.swing.*;
import java.awt.*;

public class GameResult {

    public static void checkGameResult(JPanel[][] grid, Player player, Opponent opponent) {
        boolean playerWins = true;
        boolean opponentWins = true;

        // Kontrola, zda hráč získal všechna území protivníka
        for (JPanel[] row : grid) {
            for (JPanel cell : row) {
                if (cell.getBackground().equals(Color.RED)) {
                    playerWins = false;
                    break;
                }
            }
        }

        // Kontrola, zda protivník získal všechna území hráče
        for (JPanel[] row : grid) {
            for (JPanel cell : row) {
                if (cell.getBackground().equals(Color.GREEN)) {
                    opponentWins = false;
                    break;
                }
            }
        }

        // Zobrazení výsledku
        if (playerWins) {
            JOptionPane.showMessageDialog(null, "Gratulujeme! Vyhrál jsi hru!");
            System.exit(0); // Konec programu
        } else if (opponentWins) {
            JOptionPane.showMessageDialog(null, "Bohužel, protivník získal všechna tvá území. Prohrál jsi hru.");
            System.exit(0); // Konec programu

        }
    }
}