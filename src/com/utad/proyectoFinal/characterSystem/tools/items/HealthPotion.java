package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Poción de salud que restaura puntos de vida
 */
public class HealthPotion extends GenericItem {
    
    public HealthPotion() {
        super("Health potion");
    }
    
    @Override
    public boolean consume(CombatCharacter character) {
        if (canConsume(character)) {
            // Usa el método heal() de CombatCharacter
            character.addHealthPotion();
            return true;
        }
        return false;
    }
    
    @Override
    public Image getImage() {
        try {
            return ImageIO.read(new File("Files/img/Pocion.png"));
        } catch (IOException e) {
            System.err.println("Error loading health potion image: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        }
    }
} 