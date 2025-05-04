package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.images.BaseCharacterImage;
import com.utad.proyectoFinal.characterSystem.images.CharacterImage;
import com.utad.proyectoFinal.characterSystem.images.EquipmentDecorator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterVisualizer {
    private CharacterImage characterImage;
    private Image baseAvatar;
    private CharacterEquipment equipment;
    private String baseImagePath;

    public CharacterVisualizer(CharacterEquipment equipment) {
        this.equipment = equipment;
        this.baseAvatar = loadDefaultAvatar();
        this.characterImage = new BaseCharacterImage(baseAvatar);
        this.baseImagePath = "Files/img/GreenGuy.png";
        updateCharacterImage();
    }

    public CharacterVisualizer(CharacterEquipment equipment, Image baseAvatar) {
        this.equipment = equipment;
        this.baseAvatar = baseAvatar;
        this.characterImage = new BaseCharacterImage(baseAvatar);
        updateCharacterImage();
    }

    private static Image loadDefaultAvatar() {
        try {
            return ImageIO.read(new File("Files/img/GreenGuy.png"));
        } catch (IOException e) {
            System.err.println("Error loading default avatar: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public void setImage(String path) {
        try {
            this.baseAvatar = ImageIO.read(new File(path));
            this.baseImagePath = path;
        } catch (IOException e) {
            this.baseAvatar = loadDefaultAvatar();
            this.baseImagePath = "Files/img/GreenGuy.png";
        }
        updateCharacterImage();
    }

    public void updateCharacterImage() {
        // Start with the base image
        this.characterImage = new BaseCharacterImage(this.baseAvatar);
        
        // Apply decorators for equipment
        if (equipment.hasHelmet()) {
            this.characterImage = EquipmentDecorator.createFor(this.characterImage, equipment.getHelmet());
        }
        
        if (equipment.hasWeapon()) {
            this.characterImage = EquipmentDecorator.createFor(this.characterImage, equipment.getWeapon());
        }
    }

    public Image getCompleteImage() {
        if (characterImage != null) {
            return characterImage.getCompleteImage();
        }
        return null;
    }

    public Image getImage() {
        return getCompleteImage();
    }

    public String getBaseImagePath() {
        return baseImagePath;
    }

    public void setBaseImagePath(String baseImagePath) {
        this.baseImagePath = baseImagePath;
        setImage(baseImagePath);
    }
} 