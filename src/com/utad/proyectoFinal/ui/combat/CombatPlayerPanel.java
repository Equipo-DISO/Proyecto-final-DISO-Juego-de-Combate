package com.utad.proyectoFinal.ui.combat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.*;
import javax.swing.JSeparator;

import com.utad.proyectoFinal.ui.SimplifiedImage;
import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.ui.InterfacePath;

public class CombatPlayerPanel extends JPanel{
    
    private int alignment = JLabel.LEFT;
    private String name;
    private SimplifiedImage simplifiedImage;
    private List<Image> backpack = new ArrayList();
    private int hp;
    private int hpMax;
    private int mp;
    private int mpMax;

    private JLabel hpBar = new JLabel();
    private JLabel mpBar = new JLabel();

    
    public CombatPlayerPanel(int alignment, String name, SimplifiedImage simplifiedImage, int hp, int hpMax, int mp, int mpMax) {

        this.alignment = alignment;
        this.name = name;
        this.simplifiedImage = simplifiedImage;
        this.hp = hp;
        this.mp = mp;
        this.hpMax = hpMax;
        this.mpMax = mpMax;

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setMinimumSize(new Dimension(230, 500));
        setPreferredSize(new Dimension(230, 500));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //setBackground(java.awt.Color.LIGHT_GRAY);

        // Name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setPreferredSize(new Dimension(200, 30));
        nameLabel.setHorizontalAlignment(alignment);
        nameLabel.setFont(nameLabel.getFont().deriveFont(20f));
        add(nameLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(180, 2)); // Width matches other components

        // Image
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = simplifiedImage.generateJLabel(82, 100);
        imagePanel.add(imageLabel);
        imagePanel.setPreferredSize(new Dimension(82, 115));
        imagePanel.setBorder(new LineBorder(Color.BLACK, 4));

        JPanel inventoryPanel = new JPanel(new GridLayout(2, 6));
        inventoryPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 2, 1, true));
        inventoryPanel.setPreferredSize(new Dimension(82, 115));

        playerPanel.add(imagePanel);
        playerPanel.add(inventoryPanel);
        if (alignment == JLabel.RIGHT) playerPanel.add(imagePanel);

        add(playerPanel);

        // HP
        JPanel hpPanel = new JPanel(new GridLayout(2, 1, 0, 0));

        JPanel hpLabelPanel = new JPanel(new BorderLayout());

        JLabel hpTitleLabel = new JLabel("HP");
        JLabel hpLabel = new JLabel(hp + "/" + hpMax);
        hpLabelPanel.add(hpTitleLabel, BorderLayout.WEST);
        hpLabelPanel.add(hpLabel, BorderLayout.EAST);

        JPanel hpBarPanel;
        if (alignment == JLabel.LEFT) hpBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        else hpBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        
        hpBarPanel.setPreferredSize(new Dimension(204, 10));
        hpBarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        hpBar.setPreferredSize(new Dimension(200, 12));
        hpBar.setOpaque(true);
        hpBar.setBackground(Color.RED);
        hpBarPanel.add(hpBar);

        hpPanel.add(hpLabelPanel);
        hpPanel.add(hpBarPanel);

        add(hpPanel);

        // MP
        JPanel mpPanel = new JPanel(new GridLayout(2, 1, 0, 0));

        JPanel mpLabelPanel = new JPanel(new BorderLayout());

        JLabel mpTitleLabel = new JLabel("MP");
        JLabel mpLabel = new JLabel(mp + "/" + mpMax);
        mpLabelPanel.add(mpTitleLabel, BorderLayout.WEST);
        mpLabelPanel.add(mpLabel, BorderLayout.EAST);

        JPanel mpBarPanel;
        if (alignment == JLabel.LEFT) mpBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        else mpBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        mpBarPanel.setPreferredSize(new Dimension(204, 10));
        mpBarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        mpBar.setPreferredSize(new Dimension(200, 12));
        mpBar.setOpaque(true);
        mpBar.setBackground(Color.BLUE);
        mpBarPanel.add(mpBar);

        mpPanel.add(mpLabelPanel);
        mpPanel.add(mpBarPanel);

        add(mpPanel);

        
        updateValues(hp, mp);
        setVisible(true);
    }

    public void updateValues(int hp, int mp) {
        this.hp = hp;
        this.mp = mp;
        hpBar.repaint();

        updateSliders();
    }

    private void updateSliders(){
        int hpPercentage = (int) ((float) hp / (float) hpMax * 100);
        int mpPercentage = (int) ((float) mp / (float) mpMax * 100);

        hpBar.setPreferredSize(new Dimension(200 * hpPercentage / 100, 12));
        mpBar.setPreferredSize(new Dimension(200 * mpPercentage / 100, 12));
    }
}
