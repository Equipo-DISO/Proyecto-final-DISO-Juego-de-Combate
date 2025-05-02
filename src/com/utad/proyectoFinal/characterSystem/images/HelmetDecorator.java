package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HelmetDecorator extends EquipmentDecorator {
    private BufferedImage helmet;

    public HelmetDecorator(CharacterImage decoratedImage, BufferedImage helmetAvatar) {
        super(decoratedImage);
        this.helmet = helmetAvatar;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        super.decoratedImage.render(g, x, y);
        g.drawImage(helmet, x, y, null);
    }
}
