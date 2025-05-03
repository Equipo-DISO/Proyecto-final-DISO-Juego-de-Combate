package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HelmetDecorator extends EquipmentDecorator {
    private BufferedImage helmet;
    
    // Constantes para el posicionamiento relativo del casco
    private static final double HEAD_RELATIVE_Y = -0.28; // Ajuste vertical para la posición de la cabeza
    private static final double HELMET_WIDTH_RATIO = 0.95; // El casco ocupará el 95% del ancho del personaje
    
    public HelmetDecorator(CharacterImage decoratedImage, BufferedImage helmetAvatar) {
        super(decoratedImage);
        this.helmet = helmetAvatar;
    }

    @Override
    public BufferedImage getCompleteImage() {
        BufferedImage baseImage = decoratedImage.getCompleteImage();
        if (helmet == null) {
            return baseImage;
        }
        int characterWidth = baseImage.getWidth();
        int characterHeight = baseImage.getHeight();

        // Original helmet dimensions
        int originalHelmetWidth = helmet.getWidth();
        int originalHelmetHeight = helmet.getHeight();
        // Scaled helmet size
        int newHelmetWidth = (int)(characterWidth * HELMET_WIDTH_RATIO);
        double scaleFactor = (double)newHelmetWidth / originalHelmetWidth;
        int newHelmetHeight = (int)(originalHelmetHeight * scaleFactor);

        // Position relative to base
        int helmetX = (characterWidth / 2) - (newHelmetWidth / 2);
        int helmetY = (int)(characterHeight * HEAD_RELATIVE_Y);

        // Compute bounding box for overflow
        int minX = Math.min(0, helmetX);
        int minY = Math.min(0, helmetY);
        int maxX = Math.max(characterWidth, helmetX + newHelmetWidth);
        int maxY = Math.max(characterHeight, helmetY + newHelmetHeight);
        int resultWidth = maxX - minX;
        int resultHeight = maxY - minY;

        // Create result canvas accommodating overflow
        BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        // Draw base and helmet with offsets
        g.drawImage(baseImage, -minX, -minY, null);
        g.drawImage(helmet, helmetX - minX, helmetY - minY, newHelmetWidth, newHelmetHeight, null);
        g.dispose();
        return result;
    }
}
