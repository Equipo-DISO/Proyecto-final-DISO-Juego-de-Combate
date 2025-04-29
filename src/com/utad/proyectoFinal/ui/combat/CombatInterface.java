package com.utad.proyectoFinal.ui.combat;

import javax.swing.*;

import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.InterfacePath;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CombatInterface extends JFrame implements Interface {
    private JLabel nombreLabel;
    private SimplifiedImage playerSimplifiedImage = new SimplifiedImage(InterfacePath.PLAYER.getPath(InterfacePath.ColorEnum.GREEN), 92, 110);
    private JLabel playerImage = playerSimplifiedImage.generateJLabel(InterfacePath.PLAYER.getDefWidth(), InterfacePath.PLAYER.getDefHeight());
    private JPanel listaBotsPanel;

    private ArrayList<JPanel> bots = new ArrayList<>();
    private String playerImagePath = InterfacePath.PLAYER.getPath(InterfacePath.ColorEnum.GREEN);
    
    JPanel feedPanel = new JPanel();
    private JScrollPane scrollPane;

    public CombatInterface()                              { this("Juego de Combate", 1000, 500); }
    public CombatInterface(String title)                  { this(title, 1000, 500); }
    public CombatInterface(Integer width, Integer height) { this("Juego de Combate", width, height); }

    public CombatInterface(String title, Integer width, Integer height){

        setTitle(title);
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Player
        JPanel playerPanel = new CombatPlayerPanel(JLabel.LEFT, "Juanito", playerSimplifiedImage, 20, 100, 60, 100);
        add(playerPanel, BorderLayout.WEST);

        // Enemy
        JPanel enemyPanel = new CombatPlayerPanel(JLabel.RIGHT, "Enemigo", playerSimplifiedImage, 80, 100, 50, 60);
        add(enemyPanel, BorderLayout.EAST);

        // Feed
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBorder(BorderFactory.createTitledBorder("Feed"));

        scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(Short.MAX_VALUE, 10));

        add(feedPanel, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(1, 5, 10, 10));
        actionsPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
        actionsPanel.setBackground(Color.LIGHT_GRAY);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] actions = {"Ataque Liguero", "Ataque Potente", "Curarse (x)", "Concentrarse", "Huir"};
        for (int i = 0; i < actions.length; i++) {
            int actionIndex = i;

            JButton actionButton = new JButton(actions[actionIndex]);
            actionButton.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action(actionIndex + 1);
                }
            });
            actionsPanel.add(actionButton);
        }

        add(actionsPanel, BorderLayout.SOUTH);
    }

    public void showInterface(){
        setVisible(true);
    }

    public void waitTillClose(){
        while (isVisible()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public static void main(String[] args) {
        CombatInterface combatInterface = new CombatInterface("Juego de Combate", 1000, 500);
        combatInterface.showInterface();
    }

    private void action(int type){
        switch (type) {
            case 1:
                feedPanel.add(new CombatFeedLine("Ataque Liguero"));
                System.out.println("Ataque Liguero");
                break;
            case 2:
                feedPanel.add(new CombatFeedLine("Ataque Potente"));
                System.out.println("Ataque Potente");
                break;
            case 3:
                feedPanel.add(new CombatFeedLine("Curarse (x)"));
                System.out.println("Curarse (x)");
                break;
            case 4:
                feedPanel.add(new CombatFeedLine("Concentrarse"));
                System.out.println("Concentrarse");
                break;
            case 5:
                feedPanel.add(new CombatFeedLine("Huir"));
                System.out.println("Huir");
                break;
            default:
                System.out.println("Error");
        }

        feedPanel.revalidate();
        feedPanel.repaint();

        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }
}

