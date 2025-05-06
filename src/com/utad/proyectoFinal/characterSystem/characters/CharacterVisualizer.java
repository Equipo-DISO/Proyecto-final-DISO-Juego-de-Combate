package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.images.BaseCharacterImage;
import com.utad.proyectoFinal.characterSystem.images.CharacterImage;
import com.utad.proyectoFinal.characterSystem.images.EquipmentDecorator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Clase que gestiona la representación visual de un personaje.
 * <p>
 * Esta clase se encarga de componer la imagen completa de un personaje,
 * incluyendo su avatar base y las decoraciones visuales del equipamiento.
 * Implementa el patrón Decorator para añadir dinámicamente elementos visuales
 * como cascos y armas a la imagen base del personaje.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class CharacterVisualizer {
    private CharacterImage characterImage;
    private Image baseAvatar;
    private CharacterEquipment equipment;
    private String baseImagePath;

    /**
     * Constructor que inicializa el visualizador con el equipamiento del personaje.
     * Utiliza una imagen por defecto como avatar base.
     * 
     * @param equipment El equipamiento del personaje
     */
    public CharacterVisualizer(CharacterEquipment equipment) {
        this.equipment = equipment;
        this.baseAvatar = loadDefaultAvatar();
        this.characterImage = new BaseCharacterImage(baseAvatar);
        this.baseImagePath = "Files/img/GreenGuy.png";
        updateCharacterImage();
    }

    /**
     * Constructor que inicializa el visualizador con el equipamiento y una imagen base personalizada.
     * 
     * @param equipment El equipamiento del personaje
     * @param baseAvatar La imagen base a utilizar
     */
    public CharacterVisualizer(CharacterEquipment equipment, Image baseAvatar) {
        this.equipment = equipment;
        this.baseAvatar = baseAvatar;
        this.characterImage = new BaseCharacterImage(baseAvatar);
        updateCharacterImage();
    }

    /**
     * Carga la imagen de avatar por defecto desde el sistema de archivos.
     * Si no puede cargarla, crea una imagen en blanco como respaldo.
     * 
     * @return La imagen cargada o una imagen en blanco si hay error
     */
    private static Image loadDefaultAvatar() {
        try {
            return ImageIO.read(new File("Files/img/GreenGuy.png"));
        } catch (IOException e) {
            System.err.println("Error loading default avatar: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }
    }

    /**
     * Establece una nueva imagen base para el personaje a partir de una ruta de archivo.
     * 
     * @param path Ruta del archivo de imagen
     */
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

    /**
     * Actualiza la imagen completa del personaje aplicando las decoraciones
     * correspondientes al equipamiento actual (casco y arma).
     * Utiliza el patrón Decorator para componer la imagen resultante.
     */
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

    /**
     * Obtiene la imagen completa del personaje con todas las decoraciones aplicadas.
     * 
     * @return La imagen completa del personaje, o {@code null} si no hay imagen
     */
    public Image getCompleteImage() {
        if (characterImage != null) {
            return characterImage.getCompleteImage();
        }
        return null;
    }

    /**
     * Método alternativo para obtener la imagen completa del personaje.
     * 
     * @return La imagen completa del personaje
     */
    public Image getImage() {
        return getCompleteImage();
    }

    /**
     * Obtiene la ruta de la imagen base del personaje.
     * 
     * @return La ruta de la imagen base
     */
    public String getBaseImagePath() {
        return baseImagePath;
    }

    /**
     * Establece la ruta de la imagen base del personaje y actualiza la imagen.
     * 
     * @param baseImagePath La nueva ruta de la imagen base
     */
    public void setBaseImagePath(String baseImagePath) {
        this.baseImagePath = baseImagePath;
        setImage(baseImagePath);
    }
} 