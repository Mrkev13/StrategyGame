import javax.swing.*;

class BuildingUpgrader {

    public static final int MAX_BUILDING_LEVEL = 3;
    public static final int UPGRADE_COST_LEVEL_2 = 100;
    public static final int UPGRADE_COST_LEVEL_3 = 150;
    public static final int UPGRADE_ARMY_LEVEL_2 = 50;
    public static final int UPGRADE_ARMY_LEVEL_3 = 75;
    public static final int UPGRADE_RESOURCE_LEVEL_2 = 100;
    public static final int UPGRADE_RESOURCE_LEVEL_3 = 150;

    public static void upgradeBuilding(Player player, JPanel cell) {
        int currentLevel = getBuildingLevel(cell);
        if (currentLevel < MAX_BUILDING_LEVEL) {
            int upgradeCost = getUpgradeCost(currentLevel);
            int armyCost = getUpgradeArmyCost(currentLevel);
            int resourceCost = getUpgradeResourceCost(currentLevel);
            if (player.getMoney() >= upgradeCost && player.getArmy() >= armyCost && player.getResources() >= resourceCost) {
                player.setMoney(player.getMoney() - upgradeCost);
                player.setArmy(player.getArmy() - armyCost);
                player.setResources(player.getResources() - resourceCost);
                setBuildingLevel(cell, currentLevel + 1);
                JOptionPane.showMessageDialog(null, "Budova byla úspěšně vylepšena.");
            } else {
                JOptionPane.showMessageDialog(null, "Nemáte dostatek zdrojů na vylepšení budovy.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Budova je již na maximální úrovni.");
        }
    }


    public static int getBuildingLevel(JPanel cell) {
        // Získání úrovně budovy z buňky
        if (cell.getClientProperty("BuildingLevel") != null) {
            return (int) cell.getClientProperty("BuildingLevel");
        } else {
            // Pokud není nastavena žádná úroveň, vraťte výchozí hodnotu (například 1)
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

    public static int getUpgradeResourceCost(int currentLevel) {
        if (currentLevel == 1) {
            return UPGRADE_RESOURCE_LEVEL_2;
        } else if (currentLevel == 2) {
            return UPGRADE_RESOURCE_LEVEL_3;
        } else {
            return 0; // Max level
        }
    }
}