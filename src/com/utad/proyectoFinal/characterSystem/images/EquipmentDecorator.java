package com.utad.proyectoFinal.characterSystem.images;

import java.awt.image.BufferedImage;

public abstract class EquipmentDecorator implements CharacterImage {
    protected CharacterImage decoratedImage;

    protected EquipmentDecorator(CharacterImage decoratedImage) {
        this.decoratedImage = decoratedImage;
    }

    /**
     * Returns the base CharacterImage, unwrapping all decorators
     * @return The base CharacterImage without decorators
     */
    public CharacterImage getBaseImage() {
        CharacterImage image = this.decoratedImage;
        
        while (image instanceof EquipmentDecorator) {
            image = ((EquipmentDecorator) image).decoratedImage;
        }
        
        return image;
    }
    
    /**
     * Returns the original BufferedImage from the base image
     * Useful for accessing the dimensions of the original image
     * @return The original BufferedImage without decorations
     */
    public BufferedImage getOriginalBufferedImage() {
        CharacterImage baseImage = getBaseImage();
        
        // If the base image is a BaseCharacterImage, we can cast and get its BufferedImage
        if (baseImage instanceof BaseCharacterImage) {
            return ((BaseCharacterImage) baseImage).getBufferedImage();
        }
        
        // If not, we return a default image or null
        return null;
    }
}
