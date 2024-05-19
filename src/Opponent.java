import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Opponent {
    private int money;
    private int resources;
    private int army;

    public Opponent() {
        // Nastavení výchozích hodnot
        if (Mapa.currentDifficulty == Mapa.EASY) {
            this.money = 200;
            this.resources = 200;
            this.army = 100;
        } else if (Mapa.currentDifficulty == Mapa.MEDIUM) {
            this.money = 200;
            this.resources = 200;
            this.army = 100;
        } else if (Mapa.currentDifficulty == Mapa.HARD) {
            this.money = 200;
            this.resources = 200;
            this.army = 100;
        }
    }

    //region get set
    public int getMoney() {
        return money;
    }

    public int getResources() {
        return resources;
    }

    public int getArmy() {
        return army;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public void setArmy(int army) {
        this.army = army;
    }
    //endregion

    public void performActions(JPanel[][] grid, Player player) {
        Random rand = new Random();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getBackground().equals(Color.RED)) { // Území protivníka
                    if (rand.nextInt(100) < 10) { // 10% šance na útok
                        attemptAttack(grid, i, j, player, rand);
                    }
                    if (rand.nextInt(100) < 20) { // 20% šance na vylepšení budovy
                        BuildingUpgrader.upgradeBuildingOpponent(this, grid[i][j]);
                    }
                }
            }
        }
    }

    private void attemptAttack(JPanel[][] grid, int row, int col, Player player, Random rand) {
        int attackChance = 50; // základní šance na útok 50%
        int roll = rand.nextInt(100) + 1;

        if (roll <= attackChance) {
            if (row > 0 && (grid[row - 1][col].getBackground().equals(Color.GREEN) || grid[row - 1][col].getBackground().equals(Color.WHITE))) {
                attackTerritory(grid, row - 1, col, player);
            } else if (row < grid.length - 1 && (grid[row + 1][col].getBackground().equals(Color.GREEN) || grid[row + 1][col].getBackground().equals(Color.WHITE))) {
                attackTerritory(grid, row + 1, col, player);
            } else if (col > 0 && (grid[row][col - 1].getBackground().equals(Color.GREEN) || grid[row][col - 1].getBackground().equals(Color.WHITE))) {
                attackTerritory(grid, row, col - 1, player);
            } else if (col < grid[row].length - 1 && (grid[row][col + 1].getBackground().equals(Color.GREEN) || grid[row][col + 1].getBackground().equals(Color.WHITE))) {
                attackTerritory(grid, row, col + 1, player);
            }
        }
    }

    private void attackTerritory(JPanel[][] grid, int row, int col, Player player) {
        grid[row][col].setBackground(Color.RED);
        JOptionPane.showMessageDialog(null, "Protivník napadl a převzal území!");
    }
}
