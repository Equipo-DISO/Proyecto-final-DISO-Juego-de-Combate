package com.utad.proyectoFinal.ui.combat;

import java.awt.*;

import javax.swing.*;

public class CombatFeedLine extends JPanel {

    private String icon;
    private String text;

    public CombatFeedLine(String text) {
        this(text, Action.ATACK);
    }
    public CombatFeedLine(String text, Action action) {
        this(text, action.getIcon());
    }
    public CombatFeedLine(String text, String icon) {
        this.icon = icon;
        this.text = text;
        generateLine();
    }

    public void setText(String text) {
        this.text = text;
        generateLine();
    }

    public void setIcon(Action action) {
        setIcon(action.getIcon());
    }
    public void setIcon(String icon) {
        this.icon = icon;
        generateLine();
    }

    public void setNewLine(String text, Action action) {
        setNewLine(text, action.getIcon());
    }
    public void setNewLine(String text, String icon) {
        this.text = text;
        this.icon = icon;
        generateLine();
        
    }

    private void generateLine(){
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(Short.MAX_VALUE, 20));
        setPreferredSize(new Dimension(Short.MAX_VALUE, 20));
        setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
        
        JLabel iconLabel = new JLabel(" " + icon);
        iconLabel.setHorizontalAlignment(SwingConstants.LEFT);
        iconLabel.setPreferredSize(new Dimension(25, 20));
        add(iconLabel, BorderLayout.WEST);

        JLabel textLabel = new JLabel(text);
        textLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(textLabel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
