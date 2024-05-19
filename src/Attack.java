import javax.swing.*;
import java.awt.*;

public class Attack {
    // Metoda pro provedení útoku
    public static void attack(JPanel territoryPanel, Player player, int defense) {
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
    }
}

