import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;

public class BuildingUpgraderTest {

    private Player player;
    private JPanel cell;

    @Before
    public void setUp() {
        player = new Player();
        player.setMoney(1000);
        player.setArmy(500);
        player.setWood(200);
        player.setStone(100);
        player.setGold(50);

        cell = new JPanel();
        BuildingUpgrader.setBuildingLevel(cell, 1);
    }

    @Test
    public void testUpgradeBuildingSuccess() {
        JFrame infoFrame = new JFrame();
        BuildingUpgrader.upgradeBuilding(player, cell, infoFrame);
        assertEquals(2, BuildingUpgrader.getBuildingLevel(cell));
        assertEquals(700, player.getMoney());
        assertEquals(450, player.getArmy());
        assertEquals(170, player.getWood());
        assertEquals(70, player.getStone());
        assertEquals(50, player.getGold());
    }

    @Test
    public void testUpgradeBuildingFailureDueToResources() {
        player.setMoney(100);
        JFrame infoFrame = new JFrame();
        BuildingUpgrader.upgradeBuilding(player, cell, infoFrame);
        assertEquals(1, BuildingUpgrader.getBuildingLevel(cell));
        assertEquals(100, player.getMoney());
    }
}
