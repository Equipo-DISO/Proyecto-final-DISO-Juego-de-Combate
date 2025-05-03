package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.mapa.MapObject;

import java.awt.image.BufferedImage;

public class GenericItem implements MapObject {

    private GenericItemType type;

    public GenericItem(GenericItemType type) {
        this.type = type; //para poder instanciar desde la random factory
    }
    public GenericItemType getType() {
        return type;
    }
    @Override
    public BufferedImage getBufferedImage() {
        return null;
    }
}
