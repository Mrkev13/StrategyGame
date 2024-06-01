/**
 * Utility class for calculating earnings from territories.
 */
public class TerritoryEarning {

    /**
     * Gets the earnings from a territory based on building level and difficulty.
     *
     * @param buildingLevel The level of the building.
     * @param difficulty    The difficulty level.
     * @return An array of earnings [money, wood, stone, gold, army].
     */
    public static int[] getTerritoryEarning(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{30, 0, 0, 0, 10}; // Level 1 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{25, 0, 0, 0, 5}; // Level 1 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{20, 0, 0, 0, 5}; // Level 1 on hard difficulty
            }
        } else if (buildingLevel == 2) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{35, 0, 0, 0, 15}; // Level 2 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{30, 0, 0, 0, 10}; // Level 2 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{25, 0, 0, 0, 10}; // Level 2 on hard difficulty
            }
        } else if (buildingLevel == 3) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{45, 0, 0, 0, 35}; // Level 3 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{40, 0, 0, 0, 20}; // Level 3 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{30, 0, 0, 0, 15}; // Level 3 on hard difficulty
            }
        }
        return earnings;
    }

    /**
     * Gets the earnings from a forest territory based on building level and difficulty.
     *
     * @param buildingLevel The level of the building.
     * @param difficulty    The difficulty level.
     * @return An array of earnings [money, wood, stone, gold, army].
     */
    public static int[] getTerritoryEarningForest(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 20, 0, 0, 0}; // Level 1 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 15, 0, 0, 0}; // Level 1 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 10, 0, 0, 0}; // Level 1 on hard difficulty
            }
        } else if (buildingLevel == 2) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 25, 0, 0, 0}; // Level 2 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 20, 0, 0, 0}; // Level 2 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 15, 0, 0, 0}; // Level 2 on hard difficulty
            }
        } else if (buildingLevel == 3) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 35, 0, 0, 0}; // Level 3 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 30, 0, 0, 0}; // Level 3 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 25, 0, 0, 0}; // Level 3 on hard difficulty
            }
        }
        return earnings;
    }

    /**
     * Gets the earnings from a river territory based on building level and difficulty.
     *
     * @param buildingLevel The level of the building.
     * @param difficulty    The difficulty level.
     * @return An array of earnings [money, wood, stone, gold, army].
     */
    public static int[] getTerritoryEarningRiver(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 0, 15, 0}; // Level 1 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 0, 10, 0}; // Level 1 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 0, 5, 0}; // Level 1 on hard difficulty
            }
        } else if (buildingLevel == 2) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 0, 20, 0}; // Level 2 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 0, 15, 0}; // Level 2 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 0, 10, 0}; // Level 2 on hard difficulty
            }
        } else if (buildingLevel == 3) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 0, 30, 0}; // Level 3 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 0, 25, 0}; // Level 3 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 0, 20, 0}; // Level 3 on hard difficulty
            }
        }
        return earnings;
    }

    /**
     * Gets the earnings from a mountain territory based on building level and difficulty.
     *
     * @param buildingLevel The level of the building.
     * @param difficulty    The difficulty level.
     * @return An array of earnings [money, wood, stone, gold, army].
     */
    public static int[] getTerritoryEarningMountain(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 20, 0, 0}; // Level 1 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 15, 0, 0}; // Level 1 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 10, 0, 0}; // Level 1 on hard difficulty
            }
        } else if (buildingLevel == 2) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 25, 0, 0}; // Level 2 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 20, 0, 0}; // Level 2 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 15, 0, 0}; // Level 2 on hard difficulty
            }
        } else if (buildingLevel == 3) {
            if (difficulty == Mapa.EASY) {
                earnings = new int[]{0, 0, 35, 0, 0}; // Level 3 on easy difficulty
            } else if (difficulty == Mapa.MEDIUM) {
                earnings = new int[]{0, 0, 30, 0, 0}; // Level 3 on medium difficulty
            } else if (difficulty == Mapa.HARD) {
                earnings = new int[]{0, 0, 25, 0, 0}; // Level 3 on hard difficulty
            }
        }
        return earnings;
    }
}
