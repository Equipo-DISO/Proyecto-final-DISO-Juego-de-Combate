package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.tools.factory.RandomItemFactory;
import com.utad.proyectoFinal.mapa.MapObject;
import com.utad.proyectoFinal.mapa.RenderParameters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

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
        try {
            return ImageIO.read(new File("Files/img/Cofre.png"));
        } catch (IOException e) {
            System.err.println("Error loading upgrade mana potion image: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        }
    }

    @Override
    public RenderParameters getRenderParameters() {
        return new RenderParameters();
    }
}
