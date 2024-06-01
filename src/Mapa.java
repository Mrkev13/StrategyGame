import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Main game class that initializes the game.
 */
public class Mapa {

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    public static int currentDifficulty = EASY; // Default difficulty

    public static Player player = new Player(); // Player instance
    private static int opponentMoves = 0; // Number of opponent moves
    private static BuildingUpgrader buildingUpgrader = new BuildingUpgrader(); // BuildingUpgrader instance
    public static JPanel playerInfoPanel; // Panel to display player info
    private static Opponent opponent = new Opponent();
    private static final Set<String> forestCells = new HashSet<>();
    private static final Set<String> mountainCells = new HashSet<>();
    private static final Set<String> goldCells = new HashSet<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window to maximum size
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        controlPanel.add(easyButton);
        controlPanel.add(mediumButton);
        controlPanel.add(hardButton);

        easyButton.addActionListener(e -> {
            currentDifficulty = EASY;
            frame.dispose(); // Close the difficulty selection window
            createAndShowGamePanel(currentDifficulty); // Open new window with game panel
        });

        mediumButton.addActionListener(e -> {
            currentDifficulty = MEDIUM;
            frame.dispose(); // Close the difficulty selection window
            createAndShowGamePanel(currentDifficulty); // Open new window with game panel
        });

        hardButton.addActionListener(e -> {
            currentDifficulty = HARD;
            frame.dispose(); // Close the difficulty selection window
            createAndShowGamePanel(currentDifficulty); // Open new window with game panel
        });

        frame.getContentPane().add(controlPanel, BorderLayout.WEST);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JLabel introLabel = new JLabel("<html><b>Introduction:</b><br>Welcome to the strategic war game! Your goal is to control all territories on the map. Use your strategy to expand your territory and defeat opponents.<br><br><b>Rules:</b><br>1. Each player can only attack adjacent territories.<br>2. Each territory has a certain defense level that you must overcome.<br>3. You can upgrade your buildings to increase their defense and army production.<br>4. The game ends when one player controls all territories or when all players agree to a peace treaty.<br>5. If you lose all your territories, you lose the game.</html>");
        JLabel actionsLabel = new JLabel("<html><b>Tips and Tricks:</b><br>- Strategically upgrade buildings to have stronger defense and a larger army.<br>- Plan your attacks to surprise the enemy and exploit weaknesses.<br>- Try negotiating a peace treaty if you see that you cannot win the battle.<br>- Keep in mind that you can only attack adjacent territories.<br>- Constantly monitor your resources and maintain a balance between attack and defense.</html>");

        mainPanel.add(introLabel);
        mainPanel.add(actionsLabel);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void createAndShowGamePanel(int difficulty) {
        final Color BROWN = new Color(165, 42, 42);

        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel for game grid
        JPanel gamePanel = new JPanel(new GridLayout(10, 10));
        gamePanel.setPreferredSize(new Dimension(1000, 1000)); // Increase the size of the game grid
        JPanel[][] grid = new JPanel[10][10]; // Grid to store cell references
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel cell = new JPanel();
                JLabel forest = new JLabel("Forest");
                forest.setFont(new Font("Arial", Font.BOLD, 20));
                JLabel mountain = new JLabel("Mountain");
                mountain.setFont(new Font("Arial", Font.BOLD, 20));
                JLabel river = new JLabel("River");
                river.setFont(new Font("Arial", Font.BOLD, 20));
                JLabel basic = new JLabel("Basic");
                basic.setFont(new Font("Arial", Font.BOLD, 20));

                cell.setPreferredSize(new Dimension(100, 100)); // Increase the size of the cells

                // Territory info on click
                int row = i;
                int col = j;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            GameUIHelper.displayTerritoryInfo(cell, difficulty, playerInfoPanel, player, grid, col, row);
                        }
                    }
                });

                if (Territory.isPlayerTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.GREEN);
                    cell.add(basic);
                } else if (Territory.isOpponentTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.RED);
                } else if (Territory.isForestTerritory(i, j, difficulty)) {
                    cell.setBackground(BROWN);
                    cell.add(forest);
                } else if (Territory.isRiverTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.BLUE);
                    cell.add(river);
                } else if (Territory.isMountainTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.GRAY);
                    cell.add(mountain);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Highlight the cells
                gamePanel.add(cell);
                grid[i][j] = cell; // Add panel to the grid
            }
        }
        // Window layout
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gamePanel, BorderLayout.WEST);

        frame.pack();
        frame.setVisible(true);

        // Panel to display player info
        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(8, 1));
        JLabel moneyLabel = new JLabel("Money: " + player.getMoney());
        JLabel woodLabel = new JLabel("Wood: " + player.getWood());
        JLabel stoneLabel = new JLabel("Stone: " + player.getStone());
        JLabel goldLabel = new JLabel("Gold: " + player.getGold());
        JLabel armyLabel = new JLabel("Army: " + player.getArmy());
        playerInfoPanel.add(armyLabel);
        JLabel opponentMovesLabel = new JLabel("Opponent moves: " + opponentMoves);
        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(e -> endTurn(grid, opponent, player));

        playerInfoPanel.add(moneyLabel);
        playerInfoPanel.add(woodLabel);
        playerInfoPanel.add(stoneLabel);
        playerInfoPanel.add(goldLabel);
        playerInfoPanel.add(armyLabel);
        playerInfoPanel.add(opponentMovesLabel);
        playerInfoPanel.add(endTurnButton);

        JButton negotiatePeaceButton = new JButton("Negotiate Peace");
        negotiatePeaceButton.addActionListener(e -> {
            int playerTerritories = countPlayerTerritories(grid);
            PeaceNegotiation.attemptPeace(player, playerTerritories);
        });
        playerInfoPanel.add(negotiatePeaceButton);

        contentPane.add(playerInfoPanel, BorderLayout.EAST);

        // Switch to full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Counts the number of player territories.
     *
     * @param grid the game grid
     * @return the number of player territories
     */
    private static int countPlayerTerritories(JPanel[][] grid) {
        int count = 0;
        for (JPanel[] panels : grid) {
            for (JPanel panel : panels) {
                if (panel.getBackground().equals(Color.GREEN)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Collects all money, resources, and army from all player territories and updates the player info panel.
     *
     * @param player          the player
     * @param grid            the game grid
     * @param currentDifficulty the current game difficulty
     * @param playerInfoPanel the player info panel
     */
    public static void collectTerritoryEarnings(Player player, JPanel[][] grid, int currentDifficulty, JPanel playerInfoPanel) {
        int totalMoney = 0;
        int totalArmy = 0;
        int totalWood = 0;
        int totalStone = 0;
        int totalGold = 0;

        // Iterate through all player territories and add earnings
        for (JPanel[] panels : grid) {
            for (JPanel cell : panels) {

                // Check if JPanel contains components
                if (cell.getComponentCount() > 0 && cell.getComponent(0) instanceof JLabel) {
                    String territoryType = ((JLabel) cell.getComponent(0)).getText(); // Assume JLabel is the first component

                    if (cell.getBackground().equals(Color.GREEN)) {
                        int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
                        int[] earnings;

                        switch (territoryType) {
                            case "Forest":
                                earnings = TerritoryEarning.getTerritoryEarningForest(buildingLevel, currentDifficulty);
                                break;
                            case "Mountain":
                                earnings = TerritoryEarning.getTerritoryEarningMountain(buildingLevel, currentDifficulty);
                                break;
                            case "River":
                                earnings = TerritoryEarning.getTerritoryEarningRiver(buildingLevel, currentDifficulty);
                                break;
                            default:
                                earnings = TerritoryEarning.getTerritoryEarning(buildingLevel, currentDifficulty);
                                break;
                        }

                        totalMoney += earnings[0];
                        totalWood += earnings[1];
                        totalStone += earnings[2];
                        totalGold += earnings[3];
                        totalArmy += earnings[4];
                    }
                }
            }
        }

        // Add earnings to current player values
        player.setMoney(player.getMoney() + totalMoney);
        player.setWood(player.getWood() + totalWood);
        player.setStone(player.getStone() + totalStone);
        player.setGold(player.getGold() + totalGold);
        player.setArmy(player.getArmy() + totalArmy);

        // Update player info panel
        GameUIHelper.updatePlayerInfoPanel(playerInfoPanel, player);
    }

    /**
     * Ends the turn.
     *
     * @param grid     the game grid
     * @param opponent the opponent
     * @param player   the player
     */
    public static void endTurn(JPanel[][] grid, Opponent opponent, Player player) {
        collectTerritoryEarnings(player, grid, currentDifficulty, playerInfoPanel);
        opponent.performActions(grid, player);
        opponentMoves++;
        GameUIHelper.updatePlayerInfoPanel(playerInfoPanel, player);
        GameResult.checkGameResult(grid, player, opponent);
    }
}
