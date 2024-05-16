import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mapa {

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    public static int currentDifficulty = MEDIUM; // Výchozí obtížnost

    private static Player player = new Player(); // Hráč
    private static int opponentMoves = 0; // Počet tahů protihráče
    private static BuildingUpgrader buildingUpgrader = new BuildingUpgrader(); // Instance třídy BuildingUpgrader
    private static JPanel playerInfoPanel; // Panel pro zobrazení informací o hráči

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

        JLabel introLabel = new JLabel("<html><b>Úvod:</b><br>Vítejte v naší hře! Cílem hry je ... [popis cíle hry]<br><br><b>Pravidla:</b><br>1. ... [pravidlo 1]<br>2. ... [pravidlo 2]<br>3. ... [pravidlo 3]</html>");
        JLabel actionsLabel = new JLabel("<html><b>Rady a typy:</b><br>- Můžete vylepšit kolik budov chcete.<br>- Můžete zautocit maximalne na 5 neutralnich a nepratelskych policek.<br>- Můžete zkusit sjednat mirovou smlouvu (čím více máte polic na mapě, tím větší máte šanci na úspěch).</html>");

        mainPanel.add(introLabel);
        mainPanel.add(actionsLabel);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void createAndShowGamePanel(int difficulty) {
        JFrame frame = new JFrame("Hra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pro herní pole
        JPanel gamePanel = new JPanel(new GridLayout(10, 10));
        gamePanel.setPreferredSize(new Dimension(1000, 1000)); // Zvětšíme rozměry herního pole
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(100, 100)); // Zvětšíme rozměry čtverečků

                // Obrázek a informace o územích
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            displayTerritoryInfo(cell, difficulty);
                        }
                    }
                });

                if (isPlayerTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.GREEN); // území hráče
                } else if (isOpponentTerritory(i, j, difficulty)) {
                    cell.setBackground(Color.RED); // území protihráče
                } else {
                    cell.setBackground(Color.WHITE); // neutrální území
                }
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // zvýraznění čtverců
                gamePanel.add(cell);
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
        JLabel resourcesLabel = new JLabel("Suroviny: " + player.getResources());
        JLabel armyLabel = new JLabel("Armáda: " + player.getArmy());
        playerInfoPanel.add(armyLabel);
        JLabel opponentMovesLabel = new JLabel("Tahy protihráče: " + opponentMoves);
        JButton endTurnButton = new JButton("Ukončit kolo");
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endTurn();
            }
        });

        playerInfoPanel.add(moneyLabel);
        playerInfoPanel.add(resourcesLabel);
        playerInfoPanel.add(armyLabel);
        playerInfoPanel.add(opponentMovesLabel);
        playerInfoPanel.add(endTurnButton);

        contentPane.add(playerInfoPanel, BorderLayout.EAST);

        // Přepnutí na celou obrazovku
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Metoda pro aktualizaci informací o hráči v panelu
    private static void updatePlayerInfoPanel(JPanel playerInfoPanel, Player player) {
        // Aktualizace textu v labelu pro peníze, suroviny a armádu
        JLabel moneyLabel = (JLabel) Mapa.playerInfoPanel.getComponent(0);
        moneyLabel.setText("Peníze: " + player.getMoney());

        JLabel resourcesLabel = (JLabel) Mapa.playerInfoPanel.getComponent(1);
        resourcesLabel.setText("Suroviny: " + player.getResources());

        JLabel armyLabel = (JLabel) Mapa.playerInfoPanel.getComponent(2);
        armyLabel.setText("Armáda: " + player.getArmy());

        // Aktualizace textu v labelu pro tahy protihráče
        JLabel opponentMovesLabel = (JLabel) Mapa.playerInfoPanel.getComponent(3);
        opponentMovesLabel.setText("Tahy protihráče: " + opponentMoves);
    }


    private static void displayTerritoryInfo(JPanel cell, int difficulty) {
           JFrame infoFrame = new JFrame("Informace o území");
           infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           infoFrame.setSize(300, 200);
           infoFrame.setLocationRelativeTo(cell);

           JPanel infoPanel = new JPanel(new GridLayout(6, 1));

           // Nadpis informace o území
           JLabel titleLabel = new JLabel("Informace o území");
           titleLabel.setHorizontalAlignment(JLabel.CENTER);
           infoPanel.add(titleLabel);

           // Obrázek a informace o územích
           if (cell.getBackground().equals(Color.RED)) {
               JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
               defenseLabel.setHorizontalAlignment(JLabel.CENTER);
               infoPanel.add(defenseLabel);
               JButton attackButton = new JButton("Útok");
               attackButton.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       // Při útoku předávajte aktuální hodnotu obrany území
                       Attack.attack(cell, player, getTerritoryDefense(difficulty));
                       // Aktualizace informací o hráči po provedení útoku
                       updatePlayerInfoPanel(playerInfoPanel, player);
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
                       Attack.attack(cell, player, getTerritoryDefense(difficulty));
                       // Aktualizace informací o hráči po provedení útoku
                       updatePlayerInfoPanel(playerInfoPanel, player);
                       // Zavření okna s informacemi o území
                       infoFrame.dispose();
                   }
               });
               infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.GREEN)) {
            // Pokud je to hráčovo území, zobrazí se úroveň budovy, příjem, obrana a tlačítko na vylepšení
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: " + BuildingUpgrader.getBuildingLevel(cell));
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);

            int buildingLevel = BuildingUpgrader.getBuildingLevel(cell);
            int[] earnings = getTerritoryEarning(buildingLevel, difficulty);
            JLabel earningLabel = new JLabel("Příjem: " + earnings[0] + " peněz, " + earnings[1] + " surovin, " + earnings[2] + " armády");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);

            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);

            JLabel upgradeCostLabel = new JLabel("Cena vylepšení: " + BuildingUpgrader.getUpgradeCost(BuildingUpgrader.getBuildingLevel(cell))+ " peněz, " + BuildingUpgrader.getUpgradeResourceCost(BuildingUpgrader.getBuildingLevel(cell)) + " surovin, " + BuildingUpgrader.getUpgradeArmyCost(BuildingUpgrader.getBuildingLevel(cell)) + " armády");
            upgradeCostLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(upgradeCostLabel);

            JButton upgradeButton = new JButton("Vylepšit");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Metoda pro vylepšení budovy
                    buildingUpgrader.upgradeBuilding(player, cell);
                    // Aktualizace informací o hráči po provedení vylepšení
                    updatePlayerInfoPanel(playerInfoPanel, player);
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
        int[] earnings = new int[3];
        if (buildingLevel == 1) {
            if (difficulty == EASY) {
                earnings = new int[]{50, 50, 10}; // Úroveň 1 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{25, 25, 5}; // Úroveň 1 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{25, 25, 5}; // Úroveň 1 na těžké obtížnosti
            }
        } else if (buildingLevel == 2) {
            if (difficulty == EASY) {
                earnings = new int[]{60, 60, 20}; // Úroveň 2 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{30, 30, 15}; // Úroveň 2 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{30, 30, 15}; // Úroveň 2 na těžké obtížnosti
            }
        } else if (buildingLevel == 3) {
            if (difficulty == EASY) {
                earnings = new int[]{75, 75, 30}; // Úroveň 3 na lehké obtížnosti
            } else if (difficulty == MEDIUM) {
                earnings = new int[]{40, 40, 25}; // Úroveň 3 na střední obtížnosti
            } else if (difficulty == HARD) {
                earnings = new int[]{40, 40, 25}; // Úroveň 3 na těžké obtížnosti
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

//   public static boolean isAdjacent(int attackerRow, int attackerCol, int defenderRow, int defenderCol) {
//       // Kontrola, zda jsou políčka vedle sebe
//       return Math.abs(attackerRow - defenderRow) + Math.abs(attackerCol - defenderCol) == 1;
//   }


//   private static int[] getCellCoordinates(JPanel cell) {
//       // Implementace získání souřadnic buňky z panelu (například pomocí layout manageru nebo mapování)
//       return new int[]{0, 0}; // Příklad návratových hodnot
//   }


    // Metoda pro ukončení kola
    private static void endTurn() {

    }
}
