import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;

public class GameResultTest {

    private JPanel[][] grid;
    private Player player;
    private Opponent opponent;

    @Before
    public void setUp() {
        player = new Player();
        opponent = new Opponent();

        grid = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new JPanel();
                grid[i][j].setBackground(Color.GREEN); // Initially, all territories are controlled by the player
            }
        }
    }

    @Test
    public void testCheckGameResultPlayerWins() {
        boolean result = GameResult.checkGameResult(grid, player, opponent);
        assertTrue(result); // Player should win since all territories are green
    }

    @Test
    public void testCheckGameResultOpponentWins() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setBackground(Color.RED); // Opponent controls all territories
            }
        }
        boolean result = GameResult.checkGameResult(grid, player, opponent);
        assertFalse(result); // Opponent should win since all territories are red
    }
}
