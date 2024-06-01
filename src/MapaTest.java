import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;

public class MapaTest {

    private Player player;
    private JPanel[][] grid;

    @Before
    public void setUp() {
        player = new Player();

        grid = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new JPanel();
                grid[i][j].setBackground(Color.GREEN);
                JLabel label = new JLabel("Basic");
                grid[i][j].add(label);
                BuildingUpgrader.setBuildingLevel(grid[i][j], 1);
            }
        }
    }

    @Test
    public void testCollectTerritoryEarnings() {
        Mapa.collectTerritoryEarnings(player, grid, Mapa.EASY, new JPanel());
        assertEquals(90, player.getMoney()); // Assuming each territory gives 30 money at level 1 and EASY difficulty
        assertEquals(90, player.getWood()); // Assuming each territory gives 30 wood at level 1 and EASY difficulty
        assertEquals(90, player.getStone()); // Assuming each territory gives 30 stone at level 1 and EASY difficulty
        assertEquals(90, player.getGold()); // Assuming each territory gives 30 gold at level 1 and EASY difficulty
        assertEquals(90, player.getArmy()); // Assuming each territory gives 30 army at level 1 and EASY difficulty
    }
}
