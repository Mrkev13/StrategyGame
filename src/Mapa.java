import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mapa {
    private static final int EASY = 1;
    private static final int MEDIUM = 2;
    private static final int HARD = 3;

    private static int currentDifficulty = MEDIUM; // Výchozí obtížnost

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
                createAndShowGamePanel(currentDifficulty);
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = MEDIUM;
                createAndShowGamePanel(currentDifficulty);
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDifficulty = HARD;
                createAndShowGamePanel(currentDifficulty);
            }
        });

        frame.getContentPane().add(controlPanel, BorderLayout.WEST);
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
    }

    private static void displayTerritoryInfo(JPanel cell, int difficulty) {
        // Otevření dialogového okna s informacemi o území
        JFrame infoFrame = new JFrame("Informace o území");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(300, 200);
        infoFrame.setLocationRelativeTo(cell);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1));

        // Nadpis informace o území
        JLabel titleLabel = new JLabel("Informace o území");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(titleLabel);

        // Obrázek a informace o územích
        if (cell.getBackground().equals(Color.RED)) {
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: 1");
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton attackButton = new JButton("Útok");
            attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Logika pro útok na území
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
                    // Logika pro útok na území
                }
            });
            infoPanel.add(attackButton);
        } else if (cell.getBackground().equals(Color.GREEN)) {
            JLabel buildingLevelLabel = new JLabel("Úroveň budovy: 1");
            buildingLevelLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(buildingLevelLabel);
            JLabel earningLabel = new JLabel("Příjem: 50 peněz, 50 surovin, 10 armády");
            earningLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(earningLabel);
            JLabel defenseLabel = new JLabel("Obrana: " + getTerritoryDefense(difficulty));
            defenseLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(defenseLabel);
            JButton upgradeButton = new JButton("Vylepšit budovu");
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Logika pro vylepšení budovy
                }
            });
            infoPanel.add(upgradeButton);
        }

        infoFrame.add(infoPanel);
        infoFrame.setVisible(true);
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
}
