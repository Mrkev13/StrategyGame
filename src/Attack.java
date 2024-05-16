import javax.swing.*;
import java.awt.*;

public class Attack {
    public static void attack(JPanel territoryPanel, Player player, int defense, int attackerRow, int attackerCol, int defenderRow, int defenderCol) {
        // Kontrola, zda je pole vedle hráčovy oblasti
        if (isAdjacent(attackerRow, attackerCol, defenderRow, defenderCol)) {
            // Pokud hráč má dostatečnou armádu na provedení útoku
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
            // Zobrazení upozornění, že útok není možný
            JOptionPane.showMessageDialog(null, "Můžete útočit pouze na sousedící pole ve vaší oblasti!", "Upozornění", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static boolean isAdjacent(int attackerRow, int attackerCol, int defenderRow, int defenderCol) {
        // Kontrola, zda jsou políčka vedle sebe
        return Math.abs(attackerRow - defenderRow) + Math.abs(attackerCol - defenderCol) == 1;
    }
}
