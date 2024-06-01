import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;

public class AttackTest {

    private Player player;
    private JPanel[][] grid;
    private JPanel targetCell;

    @Before
    public void setUp() {
        player = new Player();
        player.setArmy(100);

        grid = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new JPanel();
            }
        }

        grid[1][1].setBackground(Color.GREEN); // Player territory
        targetCell = grid[1][2];
        targetCell.setBackground(Color.WHITE); // Target territory
    }

    @Test
    public void testAttackSuccess() {
        Attack.attack(grid, targetCell, player, 50);
        assertEquals(Color.GREEN, targetCell.getBackground());
        assertEquals("Basic", ((JLabel) targetCell.getComponent(0)).getText());
        assertEquals(50, player.getArmy());
    }

    @Test
    public void testAttackFailure() {
        Attack.attack(grid, targetCell, player, 150);
        assertEquals(Color.WHITE, targetCell.getBackground());
        assertEquals(100, player.getArmy());
    }
}
