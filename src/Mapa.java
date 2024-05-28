import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Mapa {

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    public static int currentDifficulty = 1; // Výchozí obtížnost

    private static Player player = new Player(); // Hráč
    private static int opponentMoves = 0; // Počet tahů protihráče
    private static BuildingUpgrader buildingUpgrader = new BuildingUpgrader(); // Instance třídy BuildingUpgrader
    private static JPanel playerInfoPanel; // Panel pro zobrazení informací o hráči
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

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Nastavení okna na maximální velikost
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 1));

        JButton easyButton = new JButton("Lehká");
        JButton mediumButton = new JButton("Střední");
        JButton hardButton = new JButton("Těžká");

        controlPanel.add(easyButton);
        controlPanel.add(mediumButton);
        controlPanel.add(hardButton);

        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = EASY;
                frame.dispose(); // Zavřít okno s volbou obtížnosti
                createAndShowGamePanel(currentDifficulty); // Otevřít nové okno s herním panelem
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = MEDIUM;
                frame.dispose(); // Zavřít okno s volbou obtížnosti
                createAndShowGamePanel(currentDifficulty); // Otevřít nové okno s herním panelem
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = HARD;
                frame.dispose(); // Zavřít okno s volbou obtížnosti
                createAndShowGamePanel(currentDifficulty); // Otevřít nové okno s herním panelem
            }
        });

        frame.getContentPane().add(controlPanel, BorderLayout.WEST);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JLabel introLabel = new JLabel("<html><b>Úvod:</b><br>Vítejte ve strategické válečné hře! Vaším cílem je ovládnout všechna území na mapě. Použijte svou strategii k rozšíření svého území a poražení protivníků.<br><br><b>Pravidla:</b><br>1. Každý hráč může útočit pouze na sousední území.<br>2. Každé území má určitou úroveň obrany, kterou musíte překonat.<br>3. Můžete vylepšovat své budovy pro zvýšení jejich obrany a produkce armády.<br>4. Hra končí, když jeden hráč ovládne všechna území nebo když všichni hráči uzavřou mírovou dohodu.<br>5. Pokud ztratíte všechna svá území, prohráváte hru.</html>");
        JLabel actionsLabel = new JLabel("<html><b>Rady a tipy:</b><br>- Vylepšujte budovy strategicky, abyste měli silnější obranu a větší armádu.<br>- Plánujte své útoky tak, abyste nepřítele překvapili a využili slabých míst.<br>- Zkuste sjednat mírovou smlouvu, pokud vidíte, že nemůžete vyhrát boj.<br>- Mějte na paměti, že můžete zaútočit pouze na sousední území.<br>- Neustále sledujte své zdroje a udržujte rovnováhu mezi útokem a obranou.</html>");

        mainPanel.add(introLabel);
        mainPanel.add(actionsLabel);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void createAndShowGamePanel(int difficulty) {
        final Color BROWN = new Color(165, 42, 42);

        JFrame frame = new JFrame("Hra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pro herní pole
        JPanel gamePanel = new JPanel(new GridLayout(10, 10));
        gamePanel.setPreferredSize(new Dimension(1000, 1000)); // Zvětšíme rozměry herního pole
        JPanel[][] grid = new JPanel[10][10]; // Přidání mřížky pro uchovávání referencí na buňky
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel cell = new JPanel();
                JLabel forest = new JLabel("Les");
                forest.setFont(new Font("Arial", Font.BOLD, 20));
                JLabel mountain = new JLabel("Hory");
                mountain.setFont(new Font("Arial", Font.BOLD, 20));
                JLabel river = new JLabel("Řeka");
                river.setFont(new Font("Arial", Font.BOLD, 20));

                cell.setPreferredSize(new Dimension(100, 100)); // Zvětšíme rozměry čtverečků

                // Obrázek a informace o územích
                int row = i;
                int col = j;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            displayTerritoryInfo(cell, difficulty, playerInfoPanel, player, grid, col, row);
                        }
                    }
                });

                if (isPlayerTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.GREEN);
                } else if (isOpponentTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.RED);
                } else if (isForestTerritory(i,j,difficulty)){
                    cell.setBackground(BROWN);
                    cell.add(forest);
                } else if (isRiverTerritory(i,j,difficulty)) {
                    cell.setBackground(Color.BLUE);
                    cell.add(river);
                } else if (isMountainTerritory(i,j,difficulty)) {
                    cell.setBackground(Color.GRAY);
                    cell.add(mountain);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // zvýraznění čtverců
                gamePanel.add(cell);
                grid[i][j] = cell; // Přidání panelu do mřížky
            }
        }
        // Rozložení okna
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gamePanel, BorderLayout.WEST);

        frame.pack();
        frame.setVisible(true);

        // Panel pro zobrazení informací o hráči
        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(6, 1));
        JLabel moneyLabel = new JLabel("Peníze: " + player.getMoney());
        JLabel woodLabel = new JLabel("Wood: " + player.getWood());
        JLabel stoneLabel = new JLabel("Stone: " + player.getStone());
        JLabel goldLabel = new JLabel("Gold: " + player.getGold());
        JLabel armyLabel = new JLabel("Armáda: " + player.getArmy());
        playerInfoPanel.add(armyLabel);
        JLabel opponentMovesLabel = new JLabel("Tahy protihráče: " + opponentMoves);
        JButton endTurnButton = new JButton("Ukončit kolo");
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endTurn(grid, opponent, player);
            }
        });

        playerInfoPanel.add(moneyLabel);
        playerInfoPanel.add(woodLabel);
        playerInfoPanel.add(stoneLabel);
        playerInfoPanel.add(goldLabel);
        playerInfoPanel.add(armyLabel);
        playerInfoPanel.add(opponentMovesLabel);
        playerInfoPanel.add(endTurnButton);

        JButton negotiatePeaceButton = new JButton("Sjednat mír");
        negotiatePeaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int playerTerritories = countPlayerTerritories(grid);
                PeaceNegotiation.attemptPeace(player, playerTerritories);
            }
        });
        playerInfoPanel.add(negotiatePeaceButton);

        contentPane.add(playerInfoPanel, BorderLayout.EAST);

        // Přepnutí na celou obrazovku
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Metoda pro počítání hráčových území
    private static int countPlayerTerritories(JPanel[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getBackground().equals(Color.GREEN)) {
                    count++;
                }
            }
        }
        return count;
    }

    // Metoda pro aktualizaci informací o hráči v panelu
    private static void updatePlayerInfoPanel(JPanel playerInfoPanel, Player player) {
        // Aktualizace textu v labelu pro peníze, suroviny a armádu
        JLabel moneyLabel = (JLabel) Mapa.playerInfoPanel.getComponent(0);
        moneyLabel.setText("Money: " + player.getMoney());

        JLabel woodLabel = (JLabel) Mapa.playerInfoPanel.getComponent(1);
        woodLabel.setText("Wood: " + player.getWood());

        JLabel stoneLabel = (JLabel) Mapa.playerInfoPanel.getComponent(2);
        woodLabel.setText("Stone: " + player.getStone());

        JLabel goldLabel = (JLabel) Mapa.playerInfoPanel.getComponent(3);
        goldLabel.setText("Gold: " + player.getGold());

        JLabel armyLabel = (JLabel) Mapa.playerInfoPanel.getComponent(4);
        armyLabel.setText("Army: " + player.getArmy());

    }


    private static void displayTerritoryInfo(JPanel cell, int difficulty, JPanel playerInfoPanel, Player player, JPanel[][] grid, int row, int col) {
        final Color BROWN = new Color(165, 42, 42);
        JLabel forest = new JLabel("Les");
        forest.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel mountain = new JLabel("Hory");
        mountain.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel river = new JLabel("Řeka");
        river.setFont(new Font("Arial", Font.BOLD, 20));
           JFrame infoFrame = new JFrame("Informace o území");
           infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           infoFrame.setSize(500, 200);
           infoFrame.setLocationRelativeTo(cell);

           JPanel infoPanel = new JPanel(new GridLayout(6, 1));

           // Nadpis informace o území
           JLabel titleLabel = new JLabel("Informace o území");
           titleLabel.setHorizontalAlignment(JLabel.CENTER);
           infoPanel.add(titleLabel);

           // Obrázek a informace o územích
        if (cell.getBackground().equals(Color.RED)) {
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgrader.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Při útoku předávajte aktuální hodnotu obrany území
                    Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                    // Aktualizace informací o hráči po provedení útoku
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });

            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.WHITE)) {
               JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
               defenseLabel.setHorizontalAlignment(JLabel.CENTER);
               infoPanel.add(defenseLabel);
               JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Při útoku předávajte aktuální hodnotu obrany území
                    Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                    // Aktualizace informací o hráči po provedení útoku
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
               infoPanel.add(attackButton);
        }  else if (cell.getBackground().equals(BROWN)) {
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Při útoku předávajte aktuální hodnotu obrany území
                    Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                    // Aktualizace informací o hráči po provedení útoku
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(attackButton);
        }  else if (cell.getBackground().equals(Color.BLUE)) {
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Při útoku předávajte aktuální hodnotu obrany území
                    Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                    // Aktualizace informací o hráči po provedení útoku
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(attackButton);
        }  else if (cell.getBackground().equals(Color.GRAY)) {
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Při útoku předávajte aktuální hodnotu obrany území
                    Attack.attack(grid, cell, Mapa.player, getTerritoryDefense(difficulty));
                    // Aktualizace informací o hráči po provedení útoku
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(attackButton);

        } else if (cell.getBackground().equals(Color.GREEN) && forest.getText().equals("Les")) {

            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgraderForest.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);

            int buildingLevel = BuildingUpgraderForest.getBuildingLevel(cell);
            int[] earnings = getTerritoryEarningForest(buildingLevel, difficulty);
            JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, "  + earnings[4] + " army");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);

            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderForest.getUpgradeCost(BuildingUpgraderForest.getBuildingLevel(cell))+ " money, " + BuildingUpgraderForest.getUpgradeWoodCost(BuildingUpgraderForest.getBuildingLevel(cell)) + " wood, " + BuildingUpgraderForest.getUpgradeStoneCost(BuildingUpgraderForest.getBuildingLevel(cell)) + " stone, " + BuildingUpgraderForest.getUpgradeGoldCost(BuildingUpgraderForest.getBuildingLevel(cell)) + " gold, " + BuildingUpgraderForest.getUpgradeArmyCost(BuildingUpgraderForest.getBuildingLevel(cell)) + " army");
            upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(upgradeCostLabel);

            JButton upgradeButton = new JButton("Vylepšit");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Metoda pro vylepšení budovy
                    buildingUpgrader.upgradeBuilding(Mapa.player, cell);
                    // Aktualizace informací o hráči po provedení vylepšení
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(upgradeButton);
        } else if (cell.getBackground().equals(Color.BLUE) && forest.getText().equals("Řeka")) {
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgraderRiver.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);

            int buildingLevel = BuildingUpgraderRiver.getBuildingLevel(cell);
            int[] earnings = getTerritoryEarningRiver(buildingLevel, difficulty);
            JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, "  + earnings[4] + " army");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);

            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderRiver.getUpgradeCost(BuildingUpgraderRiver.getBuildingLevel(cell))+ " money, " + BuildingUpgraderRiver.getUpgradeWoodCost(BuildingUpgraderRiver.getBuildingLevel(cell)) + " wood, " + BuildingUpgraderRiver.getUpgradeStoneCost(BuildingUpgraderRiver.getBuildingLevel(cell)) + " stone, " + BuildingUpgraderRiver.getUpgradeGoldCost(BuildingUpgraderRiver.getBuildingLevel(cell)) + " gold, " + BuildingUpgraderRiver.getUpgradeArmyCost(BuildingUpgraderRiver.getBuildingLevel(cell)) + " army");
            upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(upgradeCostLabel);

            JButton upgradeButton = new JButton("Vylepšit");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Metoda pro vylepšení budovy
                    buildingUpgrader.upgradeBuilding(Mapa.player, cell);
                    // Aktualizace informací o hráči po provedení vylepšení
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(upgradeButton);

        } else if (cell.getBackground().equals(Color.GRAY) && forest.getText().equals("Hory")){
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgraderMountain.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);

            int buildingLevel = BuildingUpgraderMountain.getBuildingLevel(cell);
            int[] earnings = getTerritoryEarningMountain(buildingLevel, difficulty);
            JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, "  + earnings[4] + " army");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);

            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgraderMountain.getUpgradeCost(BuildingUpgraderMountain.getBuildingLevel(cell))+ " money, " + BuildingUpgraderMountain.getUpgradeWoodCost(BuildingUpgraderMountain.getBuildingLevel(cell)) + " wood, " + BuildingUpgraderMountain.getUpgradeStoneCost(BuildingUpgraderMountain.getBuildingLevel(cell)) + " stone, " + BuildingUpgraderMountain.getUpgradeGoldCost(BuildingUpgraderMountain.getBuildingLevel(cell)) + " gold, " + BuildingUpgraderMountain.getUpgradeArmyCost(BuildingUpgraderMountain.getBuildingLevel(cell)) + " army");
            upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(upgradeCostLabel);

            JButton upgradeButton = new JButton("Vylepšit");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Metoda pro vylepšení budovy
                    buildingUpgrader.upgradeBuilding(Mapa.player, cell);
                    // Aktualizace informací o hráči po provedení vylepšení
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(upgradeButton);
        }
        else if (cell.getBackground().equals(Color.GREEN)) {
            // Pokud je to hráčovo území, zobrazí se úroveň budovy, příjem, obrana a tlačítko na vylepšení
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgrader.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);

            int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
            int[] earnings = getTerritoryEarning(buildingLevel, difficulty);
            JLabel earningLabel = new JLabel("Income: " + earnings[0] + " money, " + earnings[1] + " wood, " + earnings[2] + " stone, " + earnings[3] + " gold, "  + earnings[4] + " army");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);

            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JLabel upgradeCostLabel = new JLabel("Upgrade Cost: " + BuildingUpgrader.getUpgradeCost(BuildingUpgrader.getBuildingLevel(cell))+ " money, " + BuildingUpgrader.getUpgradeWoodCost(BuildingUpgrader.getBuildingLevel(cell)) + " wood, " + BuildingUpgrader.getUpgradeStoneCost(BuildingUpgrader.getBuildingLevel(cell)) + " stone, " + BuildingUpgrader.getUpgradeGoldCost(BuildingUpgrader.getBuildingLevel(cell)) + " gold, " + BuildingUpgrader.getUpgradeArmyCost(BuildingUpgrader.getBuildingLevel(cell)) + " army");
            upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(upgradeCostLabel);

            JButton upgradeButton = new JButton("Vylepšit");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Metoda pro vylepšení budovy
                    buildingUpgrader.upgradeBuilding(Mapa.player, cell);
                    // Aktualizace informací o hráči po provedení vylepšení
                    updatePlayerInfoPanel(Mapa.playerInfoPanel, Mapa.player);
                    // Zavření okna s informacemi o území
                    infoFrame.dispose();
                }
            });
            infoPanel.add(upgradeButton);
        }
        infoFrame.add(infoPanel);
        infoFrame.setVisible(true);
    }

    private static int[] getTerritoryEarning(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == EASY) {
                earnings = new int[]{30, 0, 0,0,10}; // Úroveň 1 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{25, 0, 0,0,5}; // Úroveň 1 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{20, 0, 0,0,5}; // Úroveň 1 na těžké obtížnosti
            }
        } else if (buildingLevel == 2) {
            if (difficulty == EASY) {
                earnings = new int[]{35, 0, 0,0,15}; // Úroveň 2 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{30, 0, 0,0,10}; // Úroveň 2 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{25, 0, 0,0,10}; // Úroveň 2 na těžké obtížnosti
            }
        } else if (buildingLevel == 3) {
            if (difficulty == EASY) {
                earnings = new int[]{40, 0, 0,0,20}; // Úroveň 3 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{35, 0, 0,0,15}; // Úroveň 3 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{30, 0, 0,0,15}; // Úroveň 3 na těžké obtížnosti
            }
        }
        return earnings;
    }

    private static int[] getTerritoryEarningForest(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 20, 0,0,0}; // Úroveň 1 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 15, 0,0,0}; // Úroveň 1 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 10, 0,0,0}; // Úroveň 1 na těžké obtížnosti
            }
        } else if (buildingLevel == 2) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 25, 0,0,0}; // Úroveň 2 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 20, 0,0,0}; // Úroveň 2 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 15, 0,0,0}; // Úroveň 2 na těžké obtížnosti
            }
        } else if (buildingLevel == 3) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 30, 0,0,0}; // Úroveň 3 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 25, 0,0,0}; // Úroveň 3 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 20, 0,0,0}; // Úroveň 3 na těžké obtížnosti
            }
        }
        return earnings;
    }

    private static int[] getTerritoryEarningRiver(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 0, 0,15,0}; // Úroveň 1 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 0, 0,10,0}; // Úroveň 1 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 0, 0,5,0}; // Úroveň 1 na těžké obtížnosti
            }
        } else if (buildingLevel == 2) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 0, 0,20,0}; // Úroveň 2 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 0, 0,15,0}; // Úroveň 2 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 0, 0,10,0}; // Úroveň 2 na těžké obtížnosti
            }
        } else if (buildingLevel == 3) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 0, 0,25,0}; // Úroveň 3 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 0, 0,20,0}; // Úroveň 3 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0, 0, 0,15,0}; // Úroveň 3 na těžké obtížnosti
            }
        }
        return earnings;
    }

    private static int[] getTerritoryEarningMountain(int buildingLevel, int difficulty) {
        int[] earnings = new int[5];
        if (buildingLevel == 1) {
            if (difficulty == EASY) {
                earnings = new int[]{0, 0,20, 0,0}; // Úroveň 1 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0,0, 15, 0,0}; // Úroveň 1 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0,0, 10, 0,0}; // Úroveň 1 na těžké obtížnosti
            }
        } else if (buildingLevel == 2) {
            if (difficulty == EASY) {
                earnings = new int[]{0,0, 25, 0,0}; // Úroveň 2 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 0,20, 0,0}; // Úroveň 2 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0,0, 15, 0,0}; // Úroveň 2 na těžké obtížnosti
            }
        } else if (buildingLevel == 3) {
            if (difficulty == EASY) {
                earnings = new int[]{0,0, 30, 0,0}; // Úroveň 3 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{0, 0,25, 0,0}; // Úroveň 3 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{0,0, 20,0}; // Úroveň 3 na těžké obtížnosti
            }
        }
        return earnings;
    }


    private static int getTerritoryDefense(int difficulty) {
        if (difficulty == EASY) {
            return 50;
        } else if (difficulty == MEDIUM) {
            return 75;
        } else if (difficulty == HARD) {
            return 100;
        }
        return 0;
    }

    private static boolean isOpponentTerritory(int i, int j, int difficulty) {
        if (difficulty == EASY) {
            return (i == 0 && (j == 5 || j == 4));
        } else if (difficulty == MEDIUM) {
            return (i == 0 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == HARD) {
            return (i == 0 && (j == 4 || j == 5 || j == 6 || j == 3));
        }
        return false;
    }

    private static boolean isPlayerTerritory(int i, int j, int difficulty) {
        if (difficulty == EASY) {
            return (i == 9 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == MEDIUM) {
            return (i == 9 && (j == 4 || j == 5 || j == 6 || j == 3));
        } else if (difficulty == HARD) {
            return (i == 9 && (j == 4 || j == 5));
        }
        return false;
    }

    private static boolean isForestTerritory(int i, int j, int difficulty) {
        Random rm = new Random();


        if (forestCells.isEmpty() && (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD)) {
            while (forestCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                forestCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD) {
            return forestCells.contains(i + "," + j);
        } else {
            return false;
        }
    }
    private static boolean isMountainTerritory(int i, int j, int difficulty){
        Random rm = new Random();
        if (mountainCells.isEmpty() && (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD)) {
            while (mountainCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                mountainCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD) {
            return mountainCells.contains(i + "," + j);
        } else {
            return false;
        }
    }
    private static boolean isRiverTerritory(int i, int j, int difficulty){
        Random rm = new Random();
        if (goldCells.isEmpty() && (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD)) {
            while (goldCells.size() < 5) {
                int randI = rm.nextInt(8) + 1;
                int randJ = rm.nextInt(10);
                goldCells.add(randI + "," + randJ);
            }
        }

        if (difficulty == EASY || difficulty == MEDIUM || difficulty == HARD) {
            return goldCells.contains(i + "," + j);
        } else {
            return false;
        }
    }



    // Metoda pro přičtení všech peněz, surovin a armády za všechna hráčova území a aktualizaci informací o hráči v panelu
    private static void collectTerritoryEarnings(Player player, JPanel[][] grid) {
        int totalMoney = 0;
        int totalArmy = 0;
        int totalWood = 0;
        int totalStone = 0;
        int totalGold = 0;

        JLabel forest = new JLabel("Les");
        forest.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel mountain = new JLabel("Hory");
        mountain.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel river = new JLabel("Řeka");
        river.setFont(new Font("Arial", Font.BOLD, 20));

        // Projít všechna hráčova území a přičíst příjem
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                JPanel cell = grid[i][j];
                if (cell.getBackground().equals(Color.GREEN) && forest.getText().equals("Les")){
                    int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
                    int[] earnings = getTerritoryEarningForest(buildingLevel, currentDifficulty);
                    totalMoney += earnings[0];
                    totalWood += earnings[1];
                    totalStone += earnings[2];
                    totalGold += earnings[3];
                    totalArmy += earnings[4];
                } else if (cell.getBackground().equals(Color.GREEN) && mountain.getText().equals("Hory") ) {
                    int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
                    int[] earnings = getTerritoryEarningMountain(buildingLevel, currentDifficulty);
                    totalMoney += earnings[0];
                    totalWood += earnings[1];
                    totalStone += earnings[2];
                    totalGold += earnings[3];
                    totalArmy += earnings[4];
                } else if (cell.getBackground().equals(Color.BLUE) && river.getText().equals("Řeka")) {
                    int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
                    int[] earnings = getTerritoryEarningRiver(buildingLevel, currentDifficulty);
                    totalMoney += earnings[0];
                    totalWood += earnings[1];
                    totalStone += earnings[2];
                    totalGold += earnings[3];
                    totalArmy += earnings[4];
                }else if (cell.getBackground().equals(Color.GREEN)) { // Pokud je to hráčovo území
                    int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
                    int[] earnings = getTerritoryEarning(buildingLevel, currentDifficulty);
                    totalMoney += earnings[0];
                    totalWood += earnings[1];
                    totalStone += earnings[2];
                    totalGold += earnings[3];
                    totalArmy += earnings[4];
                }
            }
        }

        // Přičtení příjmu k aktuálním hodnotám hráče
        player.setMoney(player.getMoney() + totalMoney);
        player.setWood(player.getWood() + totalWood);
        player.setStone(player.getStone() + totalStone);
        player.setGold(player.getGold() + totalGold);
        player.setArmy(player.getArmy() + totalArmy);


        // Aktualizace informací o hráči v panelu
        updatePlayerInfoPanel(playerInfoPanel, player);
    }


    // Metoda pro ukončení kola
    private static void endTurn(JPanel[][] grid, Opponent opponent, Player player) {
        collectTerritoryEarnings(player, grid);
        opponent.performActions(grid, player);
        opponentMoves++;
        updatePlayerInfoPanel(playerInfoPanel, player);
        GameResult.checkGameResult(grid, player, opponent);
    }


}
 /*

*/