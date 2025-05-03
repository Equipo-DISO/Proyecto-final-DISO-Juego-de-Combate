package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.mapa.MapObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Chest implements MapObject {

    private final List<MapObject> contenido;

    public Chest(RandomItemFactory factory) {
        this.contenido = new ArrayList<>();
        initializeChest(factory);
    }

    public List<MapObject> abrir() {
        return contenido;
    }

    private void initializeChest(RandomItemFactory factory) {
        while (contenido.size() < 2) {
            MapObject item = factory.giveRandomObject();

            // Evitar cofres dentro de cofres
            if (!(item instanceof Chest)) {
                contenido.add(item);
            }
        }
    }

    @Override
    public Image getImage() {
        return null;
    }
}
