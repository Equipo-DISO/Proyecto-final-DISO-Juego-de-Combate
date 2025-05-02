package com.utad.proyectoFinal.characterSystem.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponDecorator extends EquipmentDecorator {
    private BufferedImage weapon;

    public WeaponDecorator(CharacterImage decoratedImage, BufferedImage weaponAvatar) {
        super(decoratedImage);
        this.weapon = weaponAvatar;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        super.decoratedImage.render(g, x, y);
        g.drawImage(weapon, x, y, null);
    }
}
