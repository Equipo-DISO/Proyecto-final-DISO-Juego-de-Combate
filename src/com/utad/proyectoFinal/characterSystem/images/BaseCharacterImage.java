package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BaseCharacterImage implements CharacterImage {
    private BufferedImage avatar;

    public BaseCharacterImage(BufferedImage avatar) {
        this.avatar = avatar;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(avatar, x, y, null);
    }
}