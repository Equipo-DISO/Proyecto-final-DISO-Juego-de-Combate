package com.utad.proyectoFinal.ui.combat;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.CombatActionType;
import com.utad.proyectoFinal.gameManagement.CombatManager;
import com.utad.proyectoFinal.mapa.MapController;
import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.InterfacePath;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CombatInterface extends JFrame implements Interface {
    private JLabel nombreLabel;

    private String playerImagePath = InterfacePath.PLAYER.getPath(InterfacePath.ColorEnum.GREEN);
    
    JPanel feedPanel = new JPanel();
    private JScrollPane scrollPane;
    private boolean feedUpdated = false;

    private CombatCharacter player;
    private CombatCharacter enemy;

    private CombatPlayerPanel playerPanel;
    private CombatPlayerPanel enemyPanel;
    
    public CombatInterface(CombatCharacter player, CombatCharacter enemy){
        this(player, enemy, "Juego de Combate - Pelea en curso");
    }
    public CombatInterface(CombatCharacter player, CombatCharacter enemy, String title){
        this(player, enemy, title, 1000, 500);
    }
    public CombatInterface(CombatCharacter player, CombatCharacter enemy, String title, Integer width, Integer height){

        if (!(player instanceof Bot)) {
            this.player = player;
            this.enemy = enemy;
        }
        else {
            this.player = enemy;
            this.enemy = player;
        }
        
        setTitle(title);
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // Player on the left side
        playerPanel = new CombatPlayerPanel(this.player, JLabel.LEFT);
        add(playerPanel, BorderLayout.WEST);

        // Enemy on the right side
        enemyPanel = new CombatPlayerPanel(this.enemy, JLabel.RIGHT);
        add(enemyPanel, BorderLayout.EAST);

        // Feed
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Feed");
        titledBorder.setTitleFont(new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 14));
        feedPanel.setBorder(titledBorder);

        scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(1, 5, 10, 10));
        actionsPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
        actionsPanel.setBackground(Color.LIGHT_GRAY);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] actions = {"Ataque Ligero", "Ataque Potente", "Curarse (" + this.player.getHpPotions() + ")", "Concentrarse", "Huir"};
        for (int i = 0; i < actions.length; i++) {
            int actionIndex = i;

            JButton actionButton = new JButton(actions[actionIndex]);
            actionButton.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action(actionIndex);

                    if (actionIndex == 2) {
                        actionButton.setText("Curarse (" + CombatInterface.this.player.getHpPotions() + ")");
                    }
                }
            });
            actionsPanel.add(actionButton);
        }

        add(actionsPanel, BorderLayout.SOUTH);
        
        // Add a window listener to clean up resources when the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanupResources();
            }
        });
    }

    // INTERFACE FUNCTIONS
    public void showInterface(){
        setVisible(true);
    }
    
    public void hideInterface() {
        cleanupResources();
        setVisible(false);
        dispose();
    }
    
    // Cleanup resources when the window is closed
    private void cleanupResources() {
        // Clear any temporary references
        this.player.setFeedLogger(null);
        this.enemy.setFeedLogger(null);
        
        // Clear feed panel
        feedPanel.removeAll();
        feedPanel.revalidate();
        feedPanel.repaint();
        
        // Release resources
        MapController.setDisableMap(false);
    }

    // ACTION BUTTONS FUNCTION
    private void action(int type) {

        if (player.isAlive() && enemy.isAlive()){
            CombatActionType actionType;
            switch (type) {
                case 0:
                    actionType = CombatActionType.LIGHT_ATTACK;
                    break;
                case 1:
                    actionType = CombatActionType.HEAVY_ATTACK;
                    break;
                case 2:
                    actionType = CombatActionType.HEAL;
                    break;
                case 3:
                    actionType = CombatActionType.GAIN_MANA;
                    break;
                case 4:
                    actionType = CombatActionType.RETREAT;
                    break;
                default:
                    // Handle unexpected type, perhaps log an error or default
                    System.err.println("Unexpected action type in CombatInterface: " + type);
                    actionType = CombatActionType.LIGHT_ATTACK; // Defaulting to light attack
                    break;
            }

            // Use CombatManager to handle player action
            CombatManager combatManager = CombatManager.getInstance();
            boolean battleEnded = combatManager.handlePlayerAction(this.player, this.enemy, actionType, this);
            
            // Update UI
            updatePanels();
            feedPanel.revalidate();
            feedPanel.repaint();
            this.feedUpdated = true;
            
            // If battle ended, hide combat interface
            if (battleEnded) {
                hideInterface();
            }
            else handleBotTurn(); // fix exception creo
        }
        else hideInterface();
    }
    
    // Bot turn handling
    private void handleBotTurn() {
        // Add delay to make the bot's turn visible
        Timer timer = new Timer(500, e -> {
            // Use CombatManager to handle bot turn
            CombatManager combatManager = CombatManager.getInstance();
            boolean battleEnded = combatManager.handleBotTurn(this.player, this.enemy, this);
            
            // Update UI
            updatePanels();
            feedPanel.revalidate();
            feedPanel.repaint();
            this.feedUpdated = true;
            
            // If battle ended, hide combat interface
            if (battleEnded) {
                hideInterface();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void updatePanels(){
        playerPanel.getInventoryImages();
        playerPanel.setInventory();
        playerPanel.revalidate();
        playerPanel.repaint();
        
        enemyPanel.getInventoryImages();
        enemyPanel.setInventory();
        enemyPanel.revalidate();
        enemyPanel.repaint();

        playerPanel.updateValues(this.player.getHealthPoints(), this.player.getManaPoints());
        enemyPanel.updateValues(this.enemy.getHealthPoints(), this.enemy.getManaPoints());

        revalidate();
        repaint();
    }

    // FEED FUNCTIONS 
    public void addFeedLine(String text, Action action) {
        addFeedLine(text, action.getIcon());
    }
    public void addFeedLine(String text, String icon) {
        CombatFeedLine feedLine = new CombatFeedLine(text, icon);
        addFeedLine(feedLine);
    }
    public void addFeedLine(CombatFeedLine line) {
        feedPanel.add(line);
        feedPanel.revalidate();
        feedPanel.repaint();
        
        // Desplazar automáticamente al final del contenido
        slideBottom();
    }

    public Boolean getFeedUpdated(){
        return this.feedUpdated;
    }

    public void slideBottom() {
        // Asegurar que los componentes estén actualizados antes de desplazarse
        feedPanel.revalidate();
        feedPanel.repaint();
        
        // Desplazarse al final del panel (usando SwingUtilities.invokeLater para garantizar que ocurra después de la actualización)
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
        
        this.feedUpdated = false;
    }

    public String getPlayerImagePath() {
        return playerImagePath;
    }

    public void setPlayerImagePath(String playerImagePath) {
        this.playerImagePath = playerImagePath;
    }

    public JLabel getNombreLabel() {
        return nombreLabel;
    }

    public void setNombreLabel(JLabel nombreLabel) {
        this.nombreLabel = nombreLabel;
    }
}

