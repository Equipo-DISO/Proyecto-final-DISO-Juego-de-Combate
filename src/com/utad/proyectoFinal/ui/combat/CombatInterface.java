package com.utad.proyectoFinal.ui.combat;

import javax.swing.*;
import javax.swing.border.Border;

import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.InterfacePath;
import com.utad.proyectoFinal.ui.SimplifiedImage;
import com.utad.proyectoFinal.ui.InterfacePath.ColorEnum;

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
    
    public CombatInterface()                              { this("Juego de Combate", 1000, 500); }
    public CombatInterface(String title)                  { this(title, 1000, 500); }
    public CombatInterface(Integer width, Integer height) { this("Juego de Combate", width, height); }

    public CombatInterface(String title, Integer width, Integer height){

        setTitle(title);
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel playerPanel = new CombatPlayerPanel("Juanito", playerSimplifiedImage, 100, 100);
        add(playerPanel, BorderLayout.WEST);
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
}

