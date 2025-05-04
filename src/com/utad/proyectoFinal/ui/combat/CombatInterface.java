package com.utad.proyectoFinal.ui.combat;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.InterfacePath;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CombatInterface extends JFrame implements Interface {
    private JLabel nombreLabel;

    private ArrayList<JPanel> bots = new ArrayList<>();
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

        this.player = player;
        this.enemy = enemy;

        setTitle(title);
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // Player
        playerPanel = new CombatPlayerPanel(player, JLabel.LEFT);
        add(playerPanel, BorderLayout.WEST);

        // Enemy
        enemyPanel = new CombatPlayerPanel(enemy, JLabel.RIGHT);
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

        String[] actions = {"Ataque Liguero", "Ataque Potente", "Curarse (" + player.getHpPotions() + ")", "Concentrarse", "Huir"};
        for (int i = 0; i < actions.length; i++) {
            int actionIndex = i;

            JButton actionButton = new JButton(actions[actionIndex]);
            actionButton.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action(actionIndex);

                    if (actionIndex == 2) {
                        actionButton.setText("Curarse (" + player.getHpPotions() + ")");
                    }
                }
            });
            actionsPanel.add(actionButton);
        }

        add(actionsPanel, BorderLayout.SOUTH);
    }

    // INTERFACE FUNCTIONS
    public void showInterface(){
        setVisible(true);
    }
    public void hideInterface() {
        setVisible(false);
    }
    public List<String> getData() {
        List<String> dataList = new ArrayList<>();
        dataList.add(playerImagePath);
        dataList.add(nombreLabel.getText());

        for (JPanel bot : this.bots) {
            JLabel botNameLabel = (JLabel) bot.getComponent(1);
            String botName = botNameLabel.getText();
            dataList.add(botName);
        }

        return dataList;
    }

    // ACTION BUTTONS FUNCTION
    private void action(int type){

        CombatFeedLine feedLine = new CombatFeedLine("");
        player.setFeedLogger(this);
        enemy.setFeedLogger(this);

        switch (type) {
            case 0:
                feedLine.setNewLine("Ataque Liguero", Action.ATACK);
                player.attack(enemy, new LightAttackStrategy());
                break;
            case 1:
                feedLine.setNewLine("Ataque Potente", Action.ATACK);
                player.attack(enemy, new HeavyAttackStrategy());
                break;
            case 2:
                feedLine.setNewLine("Curarse (" + player.getHpPotions() + ")", Action.HEAL);
                player.heal();
                break;
            case 3:
                feedLine.setNewLine("Concentrarse", Action.CONCENTRATE);
                player.gainMana();
                break;
            case 4:
                feedLine.setNewLine("Huir", Action.RUN);
                if (player.retreat(enemy)) {
                    feedLine.setNewLine("Retirado", Action.RUN);
                }   
                break;
            default:
                System.out.println("Error");
        }

        updatePanels();

        feedPanel.add(feedLine);
        feedPanel.revalidate();
        feedPanel.repaint();

        feedUpdated = true;
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

        playerPanel.updateValues(player.getHealthPoints(), player.getManaPoints());
        enemyPanel.updateValues(enemy.getHealthPoints(), enemy.getManaPoints());

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
    }

    // ON MAIN FUNCTIONS
    public void waitTillClose(){
        while (isVisible()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (feedUpdated) slideBottom();
        }
    }
    private void slideBottom() {
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        feedPanel.revalidate();
        feedPanel.repaint();

        System.out.println("scroll");
        feedUpdated = false;
    }
}

