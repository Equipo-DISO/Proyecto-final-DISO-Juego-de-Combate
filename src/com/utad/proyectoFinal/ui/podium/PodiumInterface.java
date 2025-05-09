package com.utad.proyectoFinal.ui.podium;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.SimplifiedImage;
import com.utad.proyectoFinal.ui.combat.Action;

public class PodiumInterface extends JFrame implements Interface{

    JPanel killPanel;

    public PodiumInterface(Integer playersLeft, Integer playersTotal, String killer){
        this(playersLeft, playersTotal, killer, new LinkedList<String>());
    }
    public PodiumInterface(Integer playersLeft, Integer playersTotal, String killer, LinkedList<String> kills) {
        System.out.println(killer);
        setTitle("Juego de Combate");
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setResizable(false);

        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Juego de Combate - Fin de Partida", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(30f));
        title.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // Player Position
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Position (Big)
        JPanel tP1 = new JPanel(new BorderLayout());
        tP1.setPreferredSize(new Dimension(140, 100));
        tP1.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        JLabel p1 = new JLabel("#" + playersLeft);
        p1.setFont(p1.getFont().deriveFont(80f));

        // Total players (Small)
        JPanel tP2 = new JPanel(new BorderLayout());
        tP2.setPreferredSize(new Dimension(30, 64));    // BOTTOM: [...] , 72));

        JLabel p2 = new JLabel("/" + playersTotal);
        p2.setFont(p2.getFont().deriveFont(20f));
        
        // Add all
        tP1.add(p1, BorderLayout.NORTH);
        tP2.add(p2, BorderLayout.NORTH);                // BOTTOM: [...] .SOUTH);

        topPanel.add(tP1);
        topPanel.add(tP2);

        mainPanel.add(topPanel, BorderLayout.WEST);

        // Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        JLabel info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        info.setBorder(BorderFactory.createEmptyBorder(60, 10, 0, 10));

        JLabel killerLabel = new JLabel();
        if (playersLeft == 1) info.setText("VICTORIA");
        else{
            info.setText("Has sido eliminado por:");
            killerLabel = new JLabel(Action.ATACK.getIcon() + "  " + killer, SwingConstants.LEFT);
            killerLabel.setPreferredSize(new Dimension(200, 50));
            killerLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            killerLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        }
        centerPanel.add(info);
        centerPanel.add(killerLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Exit button
        JButton exitButton = new JButton("Salir");
        exitButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        exitButton.setFocusable(false);

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(null);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quit(); // Restart game
            }
        });

        bottomPanel.add(exitButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Side panel
        killPanel = new JPanel(new GridLayout(9, 1));
        killPanel.setPreferredSize(new Dimension(212, Short.MAX_VALUE));
        killPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        JLabel killsTitle = new JLabel("Has acabado con:", SwingConstants.LEFT);
        killsTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        killsTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 15));
        killPanel.add(killsTitle);

        if (!kills.isEmpty()) for (String kill : kills) {
            JLabel killLabel = new JLabel("· " + kill, SwingConstants.LEFT);
            killLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            killLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            killPanel.add(killLabel);
        }
        else{
            JLabel killLabel = new JLabel("... nadie, ¿en serio?", SwingConstants.LEFT);
            killLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            killLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            killPanel.add(killLabel);
        }

        add(killPanel, BorderLayout.EAST);
    }

    public void quit() {
        dispose();
        System.exit(0);
    }

    public void updateKillList(LinkedList<String> kills) {

        killPanel.removeAll();

        JLabel killsTitle = new JLabel("Has acabado con:", SwingConstants.LEFT);
        killsTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        killsTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 15));
        killPanel.add(killsTitle);

        if (!kills.isEmpty()) for (String kill : kills) {
            JLabel killLabel = new JLabel("· " + kill, SwingConstants.LEFT);
            killLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            killLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            killPanel.add(killLabel);
        }
        else{
            JLabel killLabel = new JLabel("... nadie, ¿en serio?", SwingConstants.LEFT);
            killLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            killLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            killPanel.add(killLabel);
        }

        killPanel.revalidate();
        killPanel.repaint();
    }


    @Override
    public void showInterface() {
        setVisible(true);
    }
    @Override
    public void hideInterface() {
        setVisible(false);
    }

    
    /*

    TESTING

    public static void main(String[] args) {
        LinkedList<String> killList = new LinkedList<String>();
        String[] names = {"Pepe", "Juan", "Paco", "Luis", "Javier", "AntonioLuisitoLuisitoLuisitoLuisitoLuisito", "Manuel"};
        for (int i = 0; i < 7; i++){
            killList.add(names[i]);
        }

        PodiumInterface startScreen = new PodiumInterface(1, 8, null, killList);
        startScreen.setVisible(true);
        startScreen.setResizable(false); // Hacer la ventana no redimensionable
    }
    */
}
