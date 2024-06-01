import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Class representing an opponent in the game.
 */
public class Opponent {
    private int money;
    private int army;
    private int stone;
    private int gold;
    private int wood;

    /**
     * Constructor for Opponent. Initializes resources based on the current difficulty.
     */
    public Opponent() {
        if (Mapa.currentDifficulty == Mapa.EASY) {
            this.money = 1000;
            this.army = 200;
            this.stone = 0;
            this.gold = 0;
            this.wood = 0;
        } else if (Mapa.currentDifficulty == Mapa.MEDIUM) {
            this.money = 1000;
            this.army = 200;
            this.stone = 0;
            this.gold = 0;
            this.wood = 0;
        } else if (Mapa.currentDifficulty == Mapa.HARD) {
            this.money = 1000;
            this.army = 200;
            this.stone = 0;
            this.gold = 0;
            this.wood = 0;
        }
    }

    //region get set
    public int getMoney() {
        return money;
    }

    public int getArmy() {
        return army;
    }

    public int getWood() {
        return wood;
    }

    public int getGold() {
        return gold;
    }

    public int getStone() {
        return stone;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setArmy(int army) {
        this.army = army;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }
    //endregion

    /**
     * Performs actions for the opponent such as attacking or upgrading buildings.
     * @param grid the game grid
     * @param player the player to be attacked
     */
    public void performActions(JPanel[][] grid, Player player) {
        Random rand = new Random();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getBackground().equals(Color.RED)) { // Opponent's territory
                    if (rand.nextInt(100) < 20) { // 20% chance to attack
                        attemptAttack(grid, i, j, player, rand);
                    }
                    if (rand.nextInt(100) < 40) { // 40% chance to upgrade building
                        BuildingUpgrader.upgradeBuildingOpponent(this, grid[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Attempts to attack adjacent territories.
     * @param grid the game grid
     * @param row the row index of the opponent's territory
     * @param col the column index of the opponent's territory
     * @param player the player to be attacked
     * @param rand random number generator
     */
    private void attemptAttack(JPanel[][] grid, int row, int col, Player player, Random rand) {
        final Color BROWN = new Color(165, 42, 42);
        int attackChance = 55; // basic attack chance 55%
        int roll = rand.nextInt(100) + 1;

        if (roll <= attackChance) {
            if (row > 0 && isTargetable(grid[row - 1][col])) {
                attackTerritory(grid, row - 1, col, player);
            } else if (row < grid.length - 1 && isTargetable(grid[row + 1][col])) {
                attackTerritory(grid, row + 1, col, player);
            } else if (col > 0 && isTargetable(grid[row][col - 1])) {
                attackTerritory(grid, row, col - 1, player);
            } else if (col < grid[row].length - 1 && isTargetable(grid[row][col + 1])) {
                attackTerritory(grid, row, col + 1, player);
            }
        }
    }

    /**
     * Checks if a cell is targetable for an attack.
     * @param cell the cell to check
     * @return true if the cell is targetable, false otherwise
     */
    private boolean isTargetable(Component cell) {
        final Color BROWN = new Color(165, 42, 42);
        Color bg = cell.getBackground();
        return bg.equals(Color.GREEN) || bg.equals(Color.WHITE) || bg.equals(BROWN) || bg.equals(Color.BLUE) || bg.equals(Color.GRAY);
    }

    /**
     * Executes an attack on a specified territory.
     * @param grid the game grid
     * @param row the row index of the target territory
     * @param col the column index of the target territory
     * @param player the player to be attacked
     */
    private void attackTerritory(JPanel[][] grid, int row, int col, Player player) {
        if (grid[row][col].getBackground().equals(Color.GREEN)) {
            JOptionPane.showMessageDialog(null, "Opponent attacked and took over your territory!");
        } else {
            JOptionPane.showMessageDialog(null, "Opponent attacked and took over a territory!");
        }
        grid[row][col].setBackground(Color.RED);
    }
}
