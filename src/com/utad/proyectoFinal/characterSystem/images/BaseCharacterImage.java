package com.utad.proyectoFinal.characterSystem.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaseCharacterImage implements CharacterImage {
    private BufferedImage characterImage;
    
    public BaseCharacterImage(String imagePath) {
        try {
            characterImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Create a placeholder image if loading fails
            characterImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        }
    }
    
    public BaseCharacterImage(BufferedImage image) {
        this.characterImage = image;
    }
    
    @Override
    public BufferedImage getCompleteImage() {
        // Return a copy of the base character image to avoid modification of original
        BufferedImage copy = new BufferedImage(
            characterImage.getWidth(), 
            characterImage.getHeight(), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g = copy.createGraphics();
        g.drawImage(characterImage, 0, 0, null);
        g.dispose();
        
        return copy;
    }
}