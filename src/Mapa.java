import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mapa {
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

        // Panel pro herní pole
        JPanel gamePanel = new JPanel(new GridLayout(10, 10));
        gamePanel.setPreferredSize(new Dimension(1000, 1000)); // Zvětšíme rozměry herního pole
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(100, 100)); // Zvětšíme rozměry čtverečků
                if ((i == 0 && (j == 4 || j == 5 || j == 6 || j == 3))) {
                    cell.setBackground(Color.RED); // území hráče
                } else if ((i == 9 && (j == 4 || j == 5 || j == 6 || j == 3))) {
                    cell.setBackground(Color.GREEN); // území protihráče
                } else {
                    cell.setBackground(Color.WHITE); // neutrální území
                }
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // zvýraznění čtverců
                gamePanel.add(cell);
            }
        }

        // Panel pro informace o území
        JPanel infoPanel = new JPanel(new GridBagLayout()); // Použijeme GridBagLayout pro lepší umístění prvků
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Nastavíme okraje mezi prvky

        // Informace o území
        JLabel infoLabel = new JLabel("Informace o území");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(infoLabel, gbc);

        JTextArea infoTextArea = new JTextArea(10, 20);
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0; // Zvýšíme váhu vertikálního roztahu
        infoPanel.add(scrollPane, gbc);

        // Mezera mezi sekcemi informací
        JPanel spacerPanel1 = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1; // Váha vertikálního roztahu na minimum
        infoPanel.add(spacerPanel1, gbc);

        // Celkové peníze, suroviny a armáda
        JLabel resourceLabel = new JLabel("Celkové peníze, suroviny a armáda");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.0; // Resetujeme váhu vertikálního roztahu
        infoPanel.add(resourceLabel, gbc);

        JTextArea resourceTextArea = new JTextArea(3, 20);
        resourceTextArea.setEditable(false);
        JScrollPane resourceScrollPane = new JScrollPane(resourceTextArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0; // Zvýšíme váhu vertikálního roztahu
        infoPanel.add(resourceScrollPane, gbc);

        // Mezera mezi sekcemi informací
        JPanel spacerPanel2 = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1; // Váha vertikálního roztahu na minimum
        infoPanel.add(spacerPanel2, gbc);

        // Tahy protihráče
        JLabel opponentLabel = new JLabel("Tahy protihráče");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 0.0; // Resetujeme váhu vertikálního roztahu
        infoPanel.add(opponentLabel, gbc);

        JTextArea opponentTextArea = new JTextArea(3, 20);
        opponentTextArea.setEditable(false);
        JScrollPane opponentScrollPane = new JScrollPane(opponentTextArea);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0; // Zvýšíme váhu vertikálního roztahu
        infoPanel.add(opponentScrollPane, gbc);

        // Mezera mezi sekcemi informací
        JPanel spacerPanel3 = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1; // Váha vertikálního roztahu na minimum
        infoPanel.add(spacerPanel3, gbc);

        // Rady a typy
        JLabel tipsLabel = new JLabel("Rady a typy");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weighty = 0.0; // Resetujeme váhu vertikálního roztahu
        infoPanel.add(tipsLabel, gbc);

        JTextArea tipsTextArea = new JTextArea(5, 20);
        tipsTextArea.setEditable(false);
        JScrollPane tipsScrollPane = new JScrollPane(tipsTextArea);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0; // Zvýšíme váhu vertikálního roztahu
        infoPanel.add(tipsScrollPane, gbc);

        // Rozložení okna
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gamePanel, BorderLayout.WEST);
        contentPane.add(infoPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
