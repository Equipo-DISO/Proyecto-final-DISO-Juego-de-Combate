package com.utad.proyectoFinal.ui.combat;

import java.awt.*;

import javax.swing.*;

public class CombatFeedLine extends JPanel {
    public CombatFeedLine(String text) {
        setLayout(new BorderLayout());
        
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setFont(label.getFont().deriveFont(12f));
        
        add(label, BorderLayout.CENTER);

        setPreferredSize(new Dimension(Short.MAX_VALUE, 15));
    }
}
