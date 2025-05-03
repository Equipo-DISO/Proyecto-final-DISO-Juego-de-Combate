package com.utad.proyectoFinal.characterSystem.images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        // Return a copy of the base character image to avoid modification of original
        if (characterImage instanceof BufferedImage) {
            BufferedImage bufferedImage = (BufferedImage) characterImage;
            BufferedImage copy = new BufferedImage(
                bufferedImage.getWidth(), 
                bufferedImage.getHeight(), 
                BufferedImage.TYPE_INT_ARGB
            );
            
            Graphics2D g = copy.createGraphics();
            g.drawImage(characterImage, 0, 0, null);
            g.dispose();
            
            return copy;
        } else {
            // For non-BufferedImage, return the original
            return characterImage;
        }
    }
}