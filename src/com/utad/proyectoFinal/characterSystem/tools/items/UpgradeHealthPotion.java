package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.mapa.RenderParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Poción que mejora permanentemente los puntos de vida máximos
 */
public class UpgradeHealthPotion extends GenericItem {
    
    public UpgradeHealthPotion() {
        super("Upgrade health potion");
    }
    
    @Override
    public void consume(CombatCharacter character) {
        if (canConsume(character)) {
            // Usa el método específico para mejorar la salud
            character.addHealthUpgrade();
        }
    }
    
    @Override
    public Image getImage() {
        try {
            return ImageIO.read(new File("Files/img/Pergamino.png"));
        } catch (IOException e) {
            System.err.println("Error loading upgrade health potion image: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        }
    }

    @Override
    public RenderParameters getRenderParameters() {
        return new RenderParameters();
    }
} 