import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;

public class CountPlayerTerritoriesTest {

    private JPanel[][] grid;

    @Before
    public void setUp() {
        grid = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new JPanel();
            }
        }
        grid[0][0].setBackground(Color.GREEN);
        grid[0][1].setBackground(Color.GREEN);
        grid[1][0].setBackground(Color.GREEN);
    }

    @Test
    public void testCountPlayerTerritories() {
        int count = Mapa.countPlayerTerritories(grid);
        assertEquals(3, count);
    }
}
