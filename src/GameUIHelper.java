import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.text.DateFormat.MEDIUM;

/**
 * Utility class for UI-related methods.
 */
public class GameUIHelper {

    /**
     * Displays information about the territory.
     *
     * @param cell             The cell representing the territory.
     * @param difficulty       The difficulty level.
     * @param playerInfoPanel  The panel displaying player information.
     * @param player           The player.
     * @param grid             The game grid.
     * @param row              The row coordinate.
     * @param col              The column coordinate.
     */
    public static void displayTerritoryInfo(JPanel cell, int difficulty, JPanel playerInfoPanel, Player player, JPanel[][] grid, int row, int col) {
        final Color BROWN = new Color(165, 42, 42);
        JFrame infoFrame = new JFrame("Territory Information");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(500, 200);
        infoFrame.setLocationRelativeTo(cell);

        JPanel infoPanel = new JPanel(new GridLayout(7, 1));

        // Title for territory information
        JLabel titleLabel = new JLabel("Territory Information");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(titleLabel);

        // Find JLabel in JPanel to identify territory type
        JLabel typeLabel = null;
        for (Component comp : cell.getComponents()) {
            if (comp instanceof JLabel) {
                typeLabel = (JLabel) comp;
                break;
            }
        }

        if (typeLabel != null && cell.getBackground().equals(Color.GREEN)) {
            String territoryType = typeLabel.getText();
            switch (territoryType) {
                case "Forest":
                    displayForestInfo(infoPanel, cell, difficulty, infoFrame);
                    break;
                case "River":
                    displayRiverInfo(infoPanel, cell, difficulty, infoFrame);
                    break;
                case "Mountain":
                    displayMountainInfo(infoPanel, cell, difficulty, infoFrame);
                    break;
                case "Basic":
                    displayDefaultInfo(infoPanel, cell, difficulty, infoFrame);
                    break;
            }
        } else if (cell.getBackground().equals(Color.RED)) {
            JLabel buildingLevelLabel = new JLabel("Building Level: " + BuildingUpgrader.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);
            JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JButton attackButton = new JButton("Attack");
            attackButton.addActionListener(e -> {
                // Pass current territory defense value during attack
                Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                // Update player information after attack
                updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                // Close territory information window
                infoFrame.dispose();
            });

            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.WHITE)) {
            JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Attack");
            attackButton.addActionListener(e -> {
                // Pass current territory defense value during attack
                Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                // Update player information after attack
                updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                // Close territory information window
                infoFrame.dispose();
            });
            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(BROWN)) {
            JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Attack");
            attackButton.addActionListener(e -> {
                // Pass current territory defense value during attack
                Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                // Update player information after attack
                updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                // Close territory information window
                infoFrame.dispose();
            });
            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.BLUE)) {
            JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Attack");
            attackButton.addActionListener(e -> {
                // Pass current territory defense value during attack
                Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                // Update player information after attack
                updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                // Close territory information window
                infoFrame.dispose();
            });
            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.GRAY)) {
            JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Attack");
            attackButton.addActionListener(e -> {
                // Pass current territory defense value during attack
                Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                // Update player information after attack
                updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                // Close territory information window
                infoFrame.dispose();
            });
            infoPanel.add(attackButton);
        }

        infoFrame.add(infoPanel);
        infoFrame.setVisible(true);
    }

    /**
     * Displays information about a forest territory.
     *
     * @param infoPanel   The panel displaying the information.
     * @param cell        The cell representing the territory.
     * @param difficulty  The difficulty level.
     * @param infoFrame   The frame displaying territory information
     */
    private static void displayForestInfo(JPanel infoPanel, JPanel cell, int difficulty, JFrame infoFrame) {
        JLabel buildingLevelLabel = new JLabel("Building Level: " + BuildingUpgraderForest.getBuildingLevel(cell));
        buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(buildingLevelLabel);

        int buildingLevel = BuildingUpgraderForest.getBuildingLevel(cell);
        int[] earnings = TerritoryEarning.getTerritoryEarningForest(buildingLevel, difficulty);
        JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, " + earnings[4] + " army");
        earningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(earningLabel);

        JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
        defenseLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(defenseLabel);

        JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderForest.getUpgradeCost(buildingLevel) + " money, " + BuildingUpgraderForest.getUpgradeWoodCost(buildingLevel) + " wood, " + BuildingUpgraderForest.getUpgradeStoneCost(buildingLevel) + " stone, " + BuildingUpgraderForest.getUpgradeGoldCost(buildingLevel) + " gold, " + BuildingUpgraderForest.getUpgradeArmyCost(buildingLevel) + " army");
        upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(upgradeCostLabel);

        int[] nextLevelEarnings = TerritoryEarning.getTerritoryEarningForest(buildingLevel + 1, difficulty);
        JLabel nextLevelEarningLabel = new JLabel("Next Level Income: " + nextLevelEarnings[0] + " money, " + nextLevelEarnings[1] + " wood, " + nextLevelEarnings[2] + " stone, " + nextLevelEarnings[3] + " gold, " + nextLevelEarnings[4] + " army");
        nextLevelEarningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(nextLevelEarningLabel);

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(e -> {
            // Method to upgrade the building
            BuildingUpgraderForest.upgradeBuilding(Mapa.player, cell, infoFrame);
            // Update player information after upgrading
            updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
        });
        infoPanel.add(upgradeButton);
    }


    /**
     * Displays information about a river territory.
     *
     * @param infoPanel   The panel displaying the information.
     * @param cell        The cell representing the territory.
     * @param difficulty  The difficulty level.
     * @param infoFrame   The frame displaying territory information
     */
    private static void displayRiverInfo(JPanel infoPanel, JPanel cell, int difficulty, JFrame infoFrame) {
        JLabel buildingLevelLabel = new JLabel("Building Level: " + BuildingUpgraderRiver.getBuildingLevel(cell));
        buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(buildingLevelLabel);

        int buildingLevel = BuildingUpgraderRiver.getBuildingLevel(cell);
        int[] earnings = TerritoryEarning.getTerritoryEarningRiver(buildingLevel, difficulty);
        JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, " + earnings[4] + " army");
        earningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(earningLabel);

        JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
        defenseLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(defenseLabel);

        JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderRiver.getUpgradeCost(buildingLevel) + " money, " + BuildingUpgraderRiver.getUpgradeWoodCost(buildingLevel) + " wood, " + BuildingUpgraderRiver.getUpgradeStoneCost(buildingLevel) + " stone, " + BuildingUpgraderRiver.getUpgradeGoldCost(buildingLevel) + " gold, " + BuildingUpgraderRiver.getUpgradeArmyCost(buildingLevel) + " army");
        upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(upgradeCostLabel);

        int[] nextLevelEarnings = TerritoryEarning.getTerritoryEarningRiver(buildingLevel + 1, difficulty);
        JLabel nextLevelEarningLabel = new JLabel("Next Level Income: " + nextLevelEarnings[0] + " money, " + nextLevelEarnings[1] + " wood, " + nextLevelEarnings[2] + " stone, " + nextLevelEarnings[3] + " gold, " + nextLevelEarnings[4] + " army");
        nextLevelEarningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(nextLevelEarningLabel);

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(e -> {
            // Method to upgrade the building
            BuildingUpgraderRiver.upgradeBuilding(Mapa.player, cell, infoFrame);
            // Update player information after upgrading
            updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
        });
        infoPanel.add(upgradeButton);
    }


    /**
     * Displays information about a mountain territory.
     *
     * @param infoPanel   The panel displaying the information.
     * @param cell        The cell representing the territory.
     * @param difficulty  The difficulty level.
     * @param infoFrame   The frame displaying territory information
     */
    private static void displayMountainInfo(JPanel infoPanel, JPanel cell, int difficulty, JFrame infoFrame) {
        JLabel buildingLevelLabel = new JLabel("Building Level: " + BuildingUpgraderMountain.getBuildingLevel(cell));
        buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(buildingLevelLabel);

        int buildingLevel = BuildingUpgraderMountain.getBuildingLevel(cell);
        int[] earnings = TerritoryEarning.getTerritoryEarningMountain(buildingLevel, difficulty);
        JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, " + earnings[4] + " army");
        earningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(earningLabel);

        JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
        defenseLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(defenseLabel);

        JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderMountain.getUpgradeCost(buildingLevel) + " money, " + BuildingUpgraderMountain.getUpgradeWoodCost(buildingLevel) + " wood, " + BuildingUpgraderMountain.getUpgradeStoneCost(buildingLevel) + " stone, " + BuildingUpgraderMountain.getUpgradeGoldCost(buildingLevel) + " gold, " + BuildingUpgraderMountain.getUpgradeArmyCost(buildingLevel) + " army");
        upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(upgradeCostLabel);

        int[] nextLevelEarnings = TerritoryEarning.getTerritoryEarningMountain(buildingLevel + 1, difficulty);
        JLabel nextLevelEarningLabel = new JLabel("Next Level Income: " + nextLevelEarnings[0] + " money, " + nextLevelEarnings[1] + " wood, " + nextLevelEarnings[2] + " stone, " + nextLevelEarnings[3] + " gold, " + nextLevelEarnings[4] + " army");
        nextLevelEarningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(nextLevelEarningLabel);

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(e -> {
            // Method to upgrade the building
            BuildingUpgraderMountain.upgradeBuilding(Mapa.player, cell, infoFrame);
            // Update player information after upgrading
            updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
        });
        infoPanel.add(upgradeButton);
    }


    /**
     * Displays default information for other territories.
     *
     * @param infoPanel   The panel displaying the information.
     * @param cell        The cell representing the territory.
     * @param difficulty  The difficulty level.
     * @param infoFrame   The frame displaying territory information
     */
    private static void displayDefaultInfo(JPanel infoPanel, JPanel cell, int difficulty, JFrame infoFrame) {
        JLabel buildingLevelLabel = new JLabel("Building Level: " + BuildingUpgrader.getBuildingLevel(cell));
        buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(buildingLevelLabel);

        int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
        int[] earnings = TerritoryEarning.getTerritoryEarning(buildingLevel, difficulty);
        JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, " + earnings[4] + " army");
        earningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(earningLabel);

        JLabel defenseLabel = new JLabel("Defense: " + getTerritoryDefense(difficulty));
        defenseLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(defenseLabel);

        JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgrader.getUpgradeCost(buildingLevel) + " money, " + BuildingUpgrader.getUpgradeWoodCost(buildingLevel) + " wood, " + BuildingUpgrader.getUpgradeStoneCost(buildingLevel) + " stone, " + BuildingUpgrader.getUpgradeGoldCost(buildingLevel) + " gold, " + BuildingUpgrader.getUpgradeArmyCost(buildingLevel) + " army");
        upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(upgradeCostLabel);

        int[] nextLevelEarnings = TerritoryEarning.getTerritoryEarning(buildingLevel + 1, difficulty);
        JLabel nextLevelEarningLabel = new JLabel("Next Level Income: " + nextLevelEarnings[0] + " money, " + nextLevelEarnings[1] + " wood, " + nextLevelEarnings[2] + " stone, " + nextLevelEarnings[3] + " gold, " + nextLevelEarnings[4] + " army");
        nextLevelEarningLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(nextLevelEarningLabel);

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(e -> {
            // Method to upgrade the building
            BuildingUpgrader.upgradeBuilding(Mapa.player, cell, infoFrame);
            // Update player information after upgrading
            updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
        });
        infoPanel.add(upgradeButton);
    }



    public static int getTerritoryDefense(int difficulty) {
        if (difficulty == Mapa.EASY) {
            return 50;
        } else if (difficulty == MEDIUM) {
            return 75;
        } else if (difficulty == Mapa.HARD) {
            return 100;
        }
        return 0;
    }

    public static void updatePlayerInfoPanel(JPanel playerInfoPanel, Player player) {
        boolean moneyLabelFound = false;
        boolean woodLabelFound = false;
        boolean stoneLabelFound = false;
        boolean goldLabelFound = false;
        boolean armyLabelFound = false;

        Component[] components = playerInfoPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String text = label.getText();

                if (text.startsWith("Money:")) {
                    label.setText("Money: " + player.getMoney());
                    moneyLabelFound = true;
                } else if (text.startsWith("Wood:")) {
                    label.setText("Wood: " + player.getWood());
                    woodLabelFound = true;
                } else if (text.startsWith("Stone:")) {
                    label.setText("Stone: " + player.getStone());
                    stoneLabelFound = true;
                } else if (text.startsWith("Gold:")) {
                    label.setText("Gold: " + player.getGold());
                    goldLabelFound = true;
                } else if (text.startsWith("Army:")) {
                    label.setText("Army: " + player.getArmy());
                    armyLabelFound = true;
                }
            }
        }

        playerInfoPanel.revalidate();
        playerInfoPanel.repaint();
    }
}
