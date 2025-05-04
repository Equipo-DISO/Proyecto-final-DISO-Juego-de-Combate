package com.utad.proyectoFinal.characterSystem.images;

import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import java.awt.Image;

public abstract class EquipmentDecorator implements CharacterImage {
    protected CharacterImage decoratedImage;

    protected EquipmentDecorator(CharacterImage decoratedImage) {
        this.decoratedImage = decoratedImage;
    }
    
    @Override
    public Image getCompleteImage() {
        // By default, get the decorated image's complete image
        return decoratedImage.getCompleteImage();
    }

    /**
     * Returns the base CharacterImage, unwrapping all decorators
     * @return The base CharacterImage without decorators
     */
    public CharacterImage getBaseImage() {
        CharacterImage image = this.decoratedImage;
        
        while (image instanceof EquipmentDecorator decorator) {
            image = decorator.decoratedImage;
        }
        
        return image;
    }
    
    /**
     * Returns the original Image from the base image
     * Useful for accessing the dimensions of the original image
     * @return The original Image without decorations
     */
    public Image getOriginalImage() {
        CharacterImage baseImage = getBaseImage();
        
        // Get the complete image from the base image
        return baseImage.getCompleteImage();
    }
    
    /**
     * Factory method to create the appropriate equipment decorator
     * This helps decouple specific decorator implementations
     * @param base The base image to decorate
     * @param equipment The equipment to apply (helmet, weapon, etc.)
     * @return A decorated CharacterImage
     */
    public static CharacterImage createFor(CharacterImage base, Object equipment) {
        if (equipment == null) {
            return base;
        }
        
        // Handle different equipment types
        if (equipment instanceof BaseHelmet helmet) {
            Image avatar = helmet.getAvatar();
            if (avatar != null) {
                return new HelmetDecorator(base, avatar);
            }
        } else if (equipment instanceof BaseWeapon weapon) {
            Image avatar = weapon.getAvatar();
            if (avatar != null) {
                return new WeaponDecorator(base, avatar);
            }
        }
        
        // If no decorator could be applied, return the base image
        return base;
    }
}
