package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HelmetDecorator extends EquipmentDecorator {
    private BufferedImage helmet;
    
    // Constantes para el posicionamiento relativo del casco
    private static final double HEAD_RELATIVE_Y = -0.28; // Ajuste vertical para la posición de la cabeza
    private static final double HELMET_WIDTH_RATIO = 0.95; // El casco ocupará el 90% del ancho del personaje
    
    public HelmetDecorator(CharacterImage decoratedImage, BufferedImage helmetAvatar) {
        super(decoratedImage);
        this.helmet = helmetAvatar;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        // Primero renderiza el personaje base
        super.decoratedImage.render(g, x, y);
        
        if (helmet != null) {
            // Obtener la imagen original para conocer sus dimensiones
            BufferedImage originalImage = getOriginalBufferedImage();
            
            // Si no podemos obtener la imagen original, usamos un valor por defecto
            int characterWidth = originalImage != null ? originalImage.getWidth() : 82;
            int characterHeight = originalImage != null ? originalImage.getHeight() : 100;
            
            // Obtener dimensiones originales del casco
            int originalHelmetWidth = helmet.getWidth();
            int originalHelmetHeight = helmet.getHeight();
            
            // Calcular nuevo ancho del casco basado en el ancho del personaje
            int newHelmetWidth = (int)(characterWidth * HELMET_WIDTH_RATIO);
            
            // Mantener proporción del casco
            double scaleFactor = (double)newHelmetWidth / originalHelmetWidth;
            int newHelmetHeight = (int)(originalHelmetHeight * scaleFactor);
            
            // Calcular posición para centrar el casco en la cabeza del personaje
            int helmetX = x + (characterWidth / 2) - (newHelmetWidth / 2);
            int helmetY = y + (int)(characterHeight * HEAD_RELATIVE_Y);
            
            // Dibujar el casco escalado
            g.drawImage(helmet, helmetX, helmetY, newHelmetWidth, newHelmetHeight, null);
        }
    }
}
