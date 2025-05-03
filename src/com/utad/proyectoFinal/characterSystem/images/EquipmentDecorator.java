package com.utad.proyectoFinal.characterSystem.images;

import java.awt.image.BufferedImage;

public abstract class EquipmentDecorator implements CharacterImage {
    protected CharacterImage decoratedImage;

    protected EquipmentDecorator(CharacterImage decoratedImage) {
        this.decoratedImage = decoratedImage;
    }
    
    @Override
    public BufferedImage getCompleteImage() {
        // By default, get the decorated image's complete image
        return decoratedImage.getCompleteImage();
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
        
        // Get the complete image from the base image
        return baseImage.getCompleteImage();
    }
}
