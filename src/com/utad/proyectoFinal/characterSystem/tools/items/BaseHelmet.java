package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.mapa.MapObject;
import com.utad.proyectoFinal.mapa.RenderParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseHelmet implements MapObject, Consumable {

    private String name;
    private Double defense;
    private HelmetType type;
    private Integer durability;
    private String imagePath;

    // Constructor dependiendo del Tipo de casco
    public BaseHelmet(HelmetType type) {
        this.type = type;
        this.name = type.getName();
        this.defense = type.getDefense();
        this.durability = type.getDurability();
        this.imagePath = type.getImagePath();
    }

    public String getName() {
        return name;
    }

    public Double getDefense() {
        return defense;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefense(Double defense) {
        this.defense = defense;
    }

    public HelmetType getType() {
        return type;
    }

    public void decreaseDurability() {
        this.durability--;
    }

    public Integer getDurability() {
        return this.durability;
    }

    // --- Metodo para obtener el Avatar ---
    public Image getAvatar() {
        Image avatar = null;

        try {
            File file = new File(this.imagePath);
            if (file.exists()) {
                avatar = ImageIO.read(file);
            } else {
                throw new IOException("No placeholder images found");
            }

        } catch (IOException e) {
            System.err.println("Error loading weapon image: " + e.getMessage());
            e.printStackTrace();

            // Create a simple placeholder image
            avatar = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }

        return avatar;
    }

    @Override
    public Image getImage() {
        return getAvatar();
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean consume(CombatCharacter character) {
        // Usa el método específico para mejorar la defensa
        character.equipHelmet(this);
        return true;
    }

    @Override
    public RenderParameters getRenderParameters() {
        return new RenderParameters(-10, 0, 0.8, 0.8);
    }
}
