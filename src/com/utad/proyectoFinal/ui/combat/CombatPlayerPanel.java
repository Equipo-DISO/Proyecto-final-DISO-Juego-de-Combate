package com.utad.proyectoFinal.ui.combat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.JSeparator;

import com.utad.proyectoFinal.ui.SimplifiedImage;
import com.utad.proyectoFinal.ui.InterfacePath;

public class CombatPlayerPanel extends JPanel{

    private String name;
    private SimplifiedImage simplifiedImage;
    private List<Image> backpack = new ArrayList();
    private int hp;
    private int mp;
    private int alignment = JLabel.LEFT;

    public CombatPlayerPanel(String name, SimplifiedImage simplifiedImage, int hp, int mp) {

        this.name = name;
        this.simplifiedImage = simplifiedImage;
        this.hp = hp;
        this.mp = mp;

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setMinimumSize(new Dimension(200, 500));
        setPreferredSize(new Dimension(200, 500));
        setBackground(java.awt.Color.LIGHT_GRAY);

        // Name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setPreferredSize(new Dimension(180, 30));
        nameLabel.setHorizontalAlignment(alignment);
        nameLabel.setFont(nameLabel.getFont().deriveFont(20f));
        add(nameLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(180, 2)); // Width matches other components

        // Image
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = simplifiedImage.generateJLabel(82, 100);
        imagePanel.add(imageLabel);
        imagePanel.setPreferredSize(new Dimension(82, 115));
        imagePanel.setBorder(new LineBorder(Color.BLACK, 4));
        add(imagePanel);

        //JLabel a = new JLabel(simplifiedImage.generateImageIcon(), SwingConstants.SOUTH);
        setVisible(true);
    }
}
