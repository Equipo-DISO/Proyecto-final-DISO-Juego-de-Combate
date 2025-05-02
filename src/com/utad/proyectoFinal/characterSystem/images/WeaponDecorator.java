package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponDecorator extends EquipmentDecorator {
    private BufferedImage weapon;
    
    // Constants for weapon positioning
    private static final double WEAPON_RELATIVE_X = 0.8; // Position to the right side of the character
    private static final double WEAPON_RELATIVE_Y = 0.4; // Position in the middle-right of the character
    private static final double WEAPON_WIDTH_RATIO = 0.6; // The weapon will be 60% of the character width
    
    public WeaponDecorator(CharacterImage decoratedImage, BufferedImage weaponAvatar) {
        super(decoratedImage);
        this.weapon = weaponAvatar;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        // First render the decorated image (character with any previous decorations)
        super.decoratedImage.render(g, x, y);
        
        if (weapon != null) {
            // Get the original image to know its dimensions
            BufferedImage originalImage = getOriginalBufferedImage();
            
            // If we can't get the original image, use default values
            int characterWidth = originalImage != null ? originalImage.getWidth() : 82;
            int characterHeight = originalImage != null ? originalImage.getHeight() : 100;
            
            // Get original weapon dimensions
            int originalWeaponWidth = weapon.getWidth();
            int originalWeaponHeight = weapon.getHeight();
            
            // Calculate new weapon width based on character width
            int newWeaponWidth = (int)(characterWidth * WEAPON_WIDTH_RATIO);
            
            // Maintain weapon proportions
            double scaleFactor = (double)newWeaponWidth / originalWeaponWidth;
            int newWeaponHeight = (int)(originalWeaponHeight * scaleFactor);
            
            // Calculate position to place weapon in the character's hand
            int weaponX = x + (int)(characterWidth * WEAPON_RELATIVE_X) - (newWeaponWidth / 2);
            int weaponY = y + (int)(characterHeight * WEAPON_RELATIVE_Y);
            
            // Draw the scaled weapon
            g.drawImage(weapon, weaponX, weaponY, newWeaponWidth, newWeaponHeight, null);
        }
    }
}
