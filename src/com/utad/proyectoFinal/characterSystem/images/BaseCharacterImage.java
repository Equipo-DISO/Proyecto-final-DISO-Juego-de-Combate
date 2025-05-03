package com.utad.proyectoFinal.characterSystem.images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BaseCharacterImage implements CharacterImage {
    private Image characterImage;
    
    public BaseCharacterImage(String imagePath) {
        try {
            characterImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Create a placeholder image if loading fails
            characterImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        }
    }
    
    public BaseCharacterImage(Image image) {
        this.characterImage = image;
    }
    
    @Override
    public Image getCompleteImage() {
            return characterImage;
    }
    
}