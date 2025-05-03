package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.mapa.MapObject;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseWeapon implements MapObject {

    private String name;
    private Double damage;
    private Double criticalChance; // probabilidad de asestar un golpe crítico -> %
    private Double criticalDamage; // daño adicional al asestar un golpe crítico -> %
    private Integer durability;
    private WeaponType type;
    private String imagePath;

    public BaseWeapon(WeaponType type) {
        this.type = type;
        this.name = type.getName();
        this.damage = type.getDamage();
        this.criticalChance = type.getCriticalChance();
        this.criticalDamage = type.getCriticalDamage();
        this.durability = type.getDurability();
        this.imagePath = type.getImagePath();
    }

    public String getName() {
        return name;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double auxDamage) {
        this.damage = auxDamage; // por si asestan golpes críticos
    }

    public Double getCriticalChance() {
        return criticalChance;
    }

    public Double getCriticalDamage() {
        return criticalDamage;
    }

    public WeaponType getType() {
        return type;
    }

    public Integer getDurability() {
        return durability;
    }

    public void decreaseDurability(int i) {
        this.durability -= i;
    }

    // --- Metodo para fumar ---
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
}
