import javax.swing.*;
import java.awt.*;

public class Attack {
    // Metoda pro provedení útoku
    public static void attack(JPanel territoryPanel, int army, int defense) {
        // Pokud hráč má dostatečnou armádu na provedení útoku
        if (army >= defense) {
            // Odebrat obranu z armády hráče
            army -= defense;

            // Změna barvy území na zelenou (úspěšný útok)
            territoryPanel.setBackground(Color.GREEN);
        } else {
            // Změna barvy území na červenou (neúspěšný útok)
            territoryPanel.setBackground(Color.RED);
            // Zobrazení upozornění o nedostatečné armádě pro útok
            JOptionPane.showMessageDialog(null, "Nemáte dostatečnou armádu pro útok!", "Upozornění", JOptionPane.WARNING_MESSAGE);
        }
    }
}
