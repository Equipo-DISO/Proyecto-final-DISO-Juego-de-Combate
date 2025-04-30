package com.utad.proyectoFinal.ui.combat;

import javax.swing.*;
import javax.swing.border.TitledBorder;

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
    private boolean feedUpdated = false;

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

            if (feedUpdated) slideBottom(0);
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
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        combatInterface.showInterface();

    }

    private void action(int type){

        CombatFeedLine feedLine = new CombatFeedLine("");

        switch (type) {
            case 1:
                feedLine.setNewLine("Ataque Liguero", Action.ATACK);
                break;
            case 2:
                feedLine.setNewLine("Ataque Potente", Action.ATACK);
                break;
            case 3:
                feedLine.setNewLine("Curarse (x)", Action.HEAL);
                break;
            case 4:
                feedLine.setNewLine("Concentrarse", Action.CONCENTRATE);
                break;
            case 5:
                feedLine.setNewLine("Huir", Action.RUN);
                break;
            default:
                System.out.println("Error");
        }

        feedPanel.add(feedLine);
        feedPanel.revalidate();
        feedPanel.repaint();

        feedUpdated = true;
    }

    public void slideBottom(int value) {
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        feedPanel.revalidate();
        feedPanel.repaint();

        System.out.println("scroll");
        feedUpdated = false;
    }

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
}

