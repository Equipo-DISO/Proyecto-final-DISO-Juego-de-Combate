package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponDecorator extends EquipmentDecorator {
    private BufferedImage weapon;
    
    // Constants for weapon positioning
    private static final double WEAPON_RELATIVE_X = 0.6; // Position weapon to the right side
    private static final double WEAPON_RELATIVE_Y = 0.3; // Position weapon at about the middle
    private static final double WEAPON_WIDTH_RATIO = 0.65; // Weapon will be 65% of character width
    
    public WeaponDecorator(CharacterImage decoratedImage, BufferedImage weaponImage) {
        super(decoratedImage);
        this.weapon = weaponImage;
    }

    @Override
    public BufferedImage getCompleteImage() {
        BufferedImage baseImage = decoratedImage.getCompleteImage();
        if (weapon == null) {
            return baseImage;
        }
        // Original dimensions
        int characterWidth = baseImage.getWidth();
        int characterHeight = baseImage.getHeight();
        int originalWeaponWidth = weapon.getWidth();
        int originalWeaponHeight = weapon.getHeight();
        // Scaled weapon size
        int newWeaponWidth = (int)(characterWidth * WEAPON_WIDTH_RATIO);
        double scaleFactor = (double)newWeaponWidth / originalWeaponWidth;
        int newWeaponHeight = (int)(originalWeaponHeight * scaleFactor);
        // Weapon position relative to base
        int weaponX = (int)(characterWidth * WEAPON_RELATIVE_X);
        int weaponY = (int)(characterHeight * WEAPON_RELATIVE_Y);
        // Compute bounding box to allow overflow
        int minX = Math.min(0, weaponX);
        int minY = Math.min(0, weaponY);
        int maxX = Math.max(characterWidth, weaponX + newWeaponWidth);
        int maxY = Math.max(characterHeight, weaponY + newWeaponHeight);
        int resultWidth = maxX - minX;
        int resultHeight = maxY - minY;
        // Create result canvas that fits both base and weapon
        BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        // Draw base image with offset
        g.drawImage(baseImage, -minX, -minY, null);
        // Draw weapon overlay with offset
        g.drawImage(weapon, weaponX - minX, weaponY - minY, newWeaponWidth, newWeaponHeight, null);
        g.dispose();
        return result;
    }
}
