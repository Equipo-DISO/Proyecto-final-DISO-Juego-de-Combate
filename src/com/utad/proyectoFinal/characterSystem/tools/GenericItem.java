package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.mapa.MapObject;

import java.awt.*;

public class GenericItem implements MapObject, Consumible {

    private GenericItemType type;

    public GenericItem(GenericItemType type) {
        this.type = type; //para poder instanciar desde la random factory
    }
    public GenericItemType getType() {
        return type;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
