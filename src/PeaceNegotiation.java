import javax.swing.*;
import java.util.Random;

public class PeaceNegotiation {
    private static final int BASE_PEACE_CHANCE = 1; // základní šance na mír je 1% za každé území

    public static void attemptPeace(Player player, int playerTerritories) {
        int peaceChance = playerTerritories * BASE_PEACE_CHANCE;
        Random rand = new Random();
        int roll = rand.nextInt(100) + 1; // hodnota od 1 do 100

        if (roll <= peaceChance) {
            JOptionPane.showMessageDialog(null, "Mírová smlouva byla úspěšně sjednána!");
            // Logika pro úspěšné uzavření mírové smlouvy
            // Například ukončení hry nebo jiné akce
        } else {
            JOptionPane.showMessageDialog(null, "Mírová smlouva nebyla sjednána. Zkuste to znovu v dalším kole.");
        }
    }
}
