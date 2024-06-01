import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Utility class for handling territory-related methods.
 */
public class Territory {
    private static final Set<String> forestCells = new HashSet<>();
    private static final Set<String> mountainCells = new HashSet<>();
    private static final Set<String> goldCells = new HashSet<>();

    /**
     * Checks if the given coordinates belong to opponent territory.
     *
     * @param i          The row coordinate.
     * @param j          The column coordinate.
     * @param difficulty The difficulty level.
     * @return true if it's opponent territory, false otherwise.
     */
    public static boolean isOpponentTerritory(int i, int j, int difficulty) {
        if (difficulty == Mapa.EASY) {
            return (i == 0 && (j == 5 || j == 4));
        } else if (difficulty == Mapa.MEDIUM) {
            return (i == 0 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == Mapa.HARD) {
            return (i == 0 && (j == 4 || j == 5 || j == 6 || j == 3));
        }
        return false;
    }

    /**
     * Checks if the given coordinates belong to player territory.
     *
     * @param i          The row coordinate.
     * @param j          The column coordinate.
     * @param difficulty The difficulty level.
     * @return true if it's player territory, false otherwise.
     */
    public static boolean isPlayerTerritory(int i, int j, int difficulty) {
        if (difficulty == Mapa.EASY) {
            return (i == 9 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == Mapa.MEDIUM) {
            return (i == 9 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == Mapa.HARD) {
            return (i == 9 && (j == 4 || j == 5));
        }
        return false;
    }

    /**
     * Checks if the given coordinates belong to forest territory.
     *
     * @param i          The row coordinate.
     * @param j          The column coordinate.
     * @param difficulty The difficulty level.
     * @return true if it's forest territory, false otherwise.
     */
    public static boolean isForestTerritory(int i, int j, int difficulty) {
        Random rm = new Random();

        if (forestCells.isEmpty() && (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD)) {
            while (forestCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                forestCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD) {
            return forestCells.contains(i + "," + j);
        } else {
            return false;
        }
    }

    /**
     * Checks if the given coordinates belong to mountain territory.
     *
     * @param i          The row coordinate.
     * @param j          The column coordinate.
     * @param difficulty The difficulty level.
     * @return true if it's mountain territory, false otherwise.
     */
    public static boolean isMountainTerritory(int i, int j, int difficulty) {
        Random rm = new Random();

        if (mountainCells.isEmpty() && (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD)) {
            while (mountainCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                mountainCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD) {
            return mountainCells.contains(i + "," + j);
        } else {
            return false;
        }
    }

    /**
     * Checks if the given coordinates belong to river territory.
     *
     * @param i          The row coordinate.
     * @param j          The column coordinate.
     * @param difficulty The difficulty level.
     * @return true if it's river territory, false otherwise.
     */
    public static boolean isRiverTerritory(int i, int j, int difficulty) {
        Random rm = new Random();

        if (goldCells.isEmpty() && (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD)) {
            while (goldCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                goldCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == Mapa.EASY || difficulty == Mapa.MEDIUM || difficulty == Mapa.HARD) {
            return goldCells.contains(i + "," + j);
        } else {
            return false;
        }
    }
}
