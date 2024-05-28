import javax.swing.*;

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



    public static void upgradeBuilding(Player player, JPanel cell) {
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
                JOptionPane.showMessageDialog(null, "Budova byla úspěšně vylepšena.");
            } else {
                JOptionPane.showMessageDialog(null, "Nemáte dostatek zdrojů na vylepšení budovy.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Budova je již na maximální úrovni.");
        }
    }

    public static void upgradeBuildingOpponent(Opponent opponent, JPanel cell) {
        int currentLevel = getBuildingLevel(cell);
        if (currentLevel < MAX_BUILDING_LEVEL) {
            // Předpokládejme, že cena pro vylepšení protihráčovy budovy je stejná jako pro hráči
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
                JOptionPane.showMessageDialog(null, "Budova protihráče byla úspěšně vylepšena.");
            }
        }
    }

    public static int getBuildingLevel(JPanel cell) {
        // Získání úrovně budovy z buňky
        if (cell.getClientProperty("BuildingLevel") != null) {
            return (int) cell.getClientProperty("BuildingLevel");
        } else {
            return 1;
        }
    }

    public static void setBuildingLevel(JPanel cell, int level) {
        // Nastavení úrovně budovy do buňky
        cell.putClientProperty("BuildingLevel", level);
    }

    public static int getUpgradeCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_COST_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_COST_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    public static int getUpgradeArmyCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_ARMY_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_ARMY_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    public static int getUpgradeStoneCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_STONE_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_STONE_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

    public static int getUpgradeGoldCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_GOLD_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_GOLD_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }

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
