import javax.swing.*;

/**
 * Class for handling building upgrades in the forest area of the game.
 */
class BuildingUpgraderForest {

    public static final int MAX_BUILDING_LEVEL = 3;

    public static final int UPGRADE_COST_LEVEL_2 = 400;
    public static final int UPGRADE_COST_LEVEL_3 = 500;

    public static final int UPGRADE_ARMY_LEVEL_2 = 60;
    public static final int UPGRADE_ARMY_LEVEL_3 = 100;

    public static final int UPGRADE_WOOD_LEVEL_2 = 60;
    public static final int UPGRADE_WOOD_LEVEL_3 = 0;

    public static final int UPGRADE_STONE_LEVEL_2 = 60;
    public static final int UPGRADE_STONE_LEVEL_3 = 50;

    public static final int UPGRADE_GOLD_LEVEL_2 = 0;
    public static final int UPGRADE_GOLD_LEVEL_3 = 80;

    /**
     * Upgrades the building for the player.
     *
     * @param player the player who wants to upgrade the building
     * @param cell the cell containing the building to be upgraded
     */
    public static void upgradeBuilding(Player player, JPanel cell, JFrame infoFrame) {
        int currentLevel = getBuildingLevel(cell);
        if (currentLevel < MAX_BUILDING_LEVEL) {
            int upgradeCost = getUpgradeCost(currentLevel);
            int armyCost = getUpgradeArmyCost(currentLevel);
            int woodCost = getUpgradeWoodCost(currentLevel);
            int stoneCost = getUpgradeStoneCost(currentLevel);
            int goldCost = getUpgradeGoldCost(currentLevel);
            if (player.getMoney() >= upgradeCost && player.getArmy() >= armyCost && player.getWood() >= woodCost && player.getStone() >= stoneCost && player.getGold() >= goldCost) {
                player.setMoney(player.getMoney() - upgradeCost);
                player.setArmy(player.getArmy() - armyCost);
                player.setWood(player.getWood() - woodCost);
                player.setStone(player.getStone() - stoneCost);
                player.setGold(player.getGold() - goldCost);
                setBuildingLevel(cell, currentLevel + 1);
                JOptionPane.showMessageDialog(null, "Building successfully upgraded.");
                infoFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Not enough resources to upgrade the building.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Building is already at the maximum level.");
        }
    }

    /**
     * Upgrades the building for the opponent.
     *
     * @param opponent the opponent whose building is to be upgraded
     * @param cell the cell containing the building to be upgraded
     */
    public static void upgradeBuildingOpponent(Opponent opponent, JPanel cell) {
        int currentLevel = getBuildingLevel(cell);
        if (currentLevel < MAX_BUILDING_LEVEL) {
            int upgradeCost = getUpgradeCost(currentLevel);
            int armyCost = getUpgradeArmyCost(currentLevel);
            int woodCost = getUpgradeWoodCost(currentLevel);
            int stoneCost = getUpgradeStoneCost(currentLevel);
            int goldCost = getUpgradeGoldCost(currentLevel);

            if (opponent.getMoney() >= upgradeCost && opponent.getArmy() >= armyCost && opponent.getWood() >= woodCost && opponent.getGold() >= goldCost && opponent.getStone() >= stoneCost) {
                opponent.setMoney(opponent.getMoney() - upgradeCost);
                opponent.setArmy(opponent.getArmy() - armyCost);
                opponent.setWood(opponent.getWood() - woodCost);
                opponent.setGold(opponent.getGold() - goldCost);
                opponent.setStone(opponent.getStone() - stoneCost);
                setBuildingLevel(cell, currentLevel + 1);
                JOptionPane.showMessageDialog(null, "Opponent's building successfully upgraded.");
            }
        }
    }

    /**
     * Retrieves the current level of the building in the specified cell.
     *
     * @param cell the cell containing the building
     * @return the current building level
     */
    public static int getBuildingLevel(JPanel cell) {
        if (cell.getClientProperty("BuildingLevel") != null) {
            return (int) cell.getClientProperty("BuildingLevel");
        } else {
            return 1;
        }
    }

    /**
     * Sets the level of the building in the specified cell.
     *
     * @param cell the cell containing the building
     * @param level the new level to set
     */
    public static void setBuildingLevel(JPanel cell, int level) {
        cell.putClientProperty("BuildingLevel", level);
    }

    /**
     * Retrieves the upgrade cost for the building based on its current level.
     *
     * @param currentLevel the current level of the building
     * @return the cost to upgrade to the next level
     */
    public static int getUpgradeCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_COST_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_COST_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    /**
     * Retrieves the army cost for the building upgrade based on its current level.
     *
     * @param currentLevel the current level of the building
     * @return the army cost to upgrade to the next level
     */
    public static int getUpgradeArmyCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_ARMY_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_ARMY_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    /**
     * Retrieves the stone cost for the building upgrade based on its current level.
     *
     * @param currentLevel the current level of the building
     * @return the stone cost to upgrade to the next level
     */
    public static int getUpgradeStoneCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_STONE_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_STONE_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    /**
     * Retrieves the gold cost for the building upgrade based on its current level.
     *
     * @param currentLevel the current level of the building
     * @return the gold cost to upgrade to the next level
     */
    public static int getUpgradeGoldCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_GOLD_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_GOLD_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    /**
     * Retrieves the wood cost for the building upgrade based on its current level.
     *
     * @param currentLevel the current level of the building
     * @return the wood cost to upgrade to the next level
     */
    public static int getUpgradeWoodCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_WOOD_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_WOOD_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }
}
