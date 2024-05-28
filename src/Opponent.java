import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Opponent {
    private int money;
    private int army;
    private int stone;
    private int gold;
    private int wood;

    public Opponent() {
        // Nastavení výchozích hodnot
        if (Mapa.currentDifficulty == Mapa.EASY) {
            this.money = 200;
            this.army = 100;
            this.stone = 0;
            this.gold = 0;
            this.wood = 0;
        } else if (Mapa.currentDifficulty == Mapa.MEDIUM) {
            this.money = 200;
            this.army = 100;
            this.stone = 0;
            this.gold = 0;
            this.wood = 0;
        } else if (Mapa.currentDifficulty == Mapa.HARD) {
            this.money = 200;
            this.army = 100;
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
    public void performActions(JPanel[][] grid, Player player) {
        Random rand = new Random();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getBackground().equals(Color.RED)) { // Území protivníka
                    if (rand.nextInt(100) < 30) { // 10% šance na útok
                        attemptAttack(grid, i, j, player, rand);
                    }
                    if (rand.nextInt(100) < 40) { // 20% šance na vylepšení budovy
                        BuildingUpgrader.upgradeBuildingOpponent(this, grid[i][j]);
                    }
                }
            }
        }
    }

    private void attemptAttack(JPanel[][] grid, int row, int col, Player player, Random rand) {
        final Color BROWN = new Color(165, 42, 42);
        int attackChance = 60; // základní šance na útok 50%
        int roll = rand.nextInt(100) + 1;

        if (roll <= attackChance) {
            if (row > 0 && (grid[row - 1][col].getBackground().equals(Color.GREEN) || grid[row - 1][col].getBackground().equals(Color.WHITE) || grid[row - 1][col].getBackground().equals(BROWN) || grid[row - 1][col].getBackground().equals(Color.BLUE) || grid[row - 1][col].getBackground().equals(Color.GRAY))) {
                attackTerritory(grid, row - 1, col, player);
            } else if (row < grid.length - 1 && (grid[row + 1][col].getBackground().equals(Color.GREEN) || grid[row + 1][col].getBackground().equals(Color.WHITE) || grid[row + 1][col].getBackground().equals(BROWN) || grid[row + 1][col].getBackground().equals(Color.BLUE) || grid[row + 1][col].getBackground().equals(Color.GRAY))) {
                attackTerritory(grid, row + 1, col, player);
            } else if (col > 0 && (grid[row][col - 1].getBackground().equals(Color.GREEN) || grid[row][col - 1].getBackground().equals(Color.WHITE) || grid[row][col - 1].getBackground().equals(BROWN)) || grid[row][col - 1].getBackground().equals(Color.BLUE) || grid[row][col - 1].getBackground().equals(Color.GRAY)) {
                attackTerritory(grid, row, col - 1, player);
            } else if (col < grid[row].length - 1 && (grid[row][col + 1].getBackground().equals(Color.GREEN) || grid[row][col + 1].getBackground().equals(Color.WHITE) || grid[row][col + 1].getBackground().equals(BROWN) || grid[row][col + 1].getBackground().equals(Color.GRAY) || grid[row][col + 1].getBackground().equals(Color.GRAY))) {
                attackTerritory(grid, row, col + 1, player);
            }
        }
    }

    private void attackTerritory(JPanel[][] grid, int row, int col, Player player) {
        grid[row][col].setBackground(Color.RED);
        JOptionPane.showMessageDialog(null, "Protivník napadl a převzal území!");
    }
}
