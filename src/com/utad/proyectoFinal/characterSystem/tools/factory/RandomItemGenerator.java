package com.utad.proyectoFinal.characterSystem.tools.factory;

import com.utad.proyectoFinal.characterSystem.tools.items.*;
import com.utad.proyectoFinal.mapa.MapObject;

/**
 * Generador de items aleatorios con métodos estáticos.
 * Proporciona métodos para crear diferentes tipos de items aleatorios.
 */
public class RandomItemGenerator {

    /**
     * Constructor privado para evitar instanciación.
     * Esta clase solo proporciona métodos estáticos.
     */
    private RandomItemGenerator() {
        // No permite instanciación
    }
    
    /**
     * Crea un item aleatorio (poción, arma, casco o cofre).
     * 
     * @return Un item aleatorio
     */
    public static MapObject createRandomItem() {
        // Probabilidad ajustada para hacer los cofres menos comunes (10%)
        double randomValue = Math.random();
        
        if (randomValue < 0.30) {
            // 30% probabilidad de poción
            return createRandomPotion();
        } else if (randomValue < 0.60) {
            // 30% probabilidad de arma
            return createRandomWeapon();
        } else if (randomValue < 0.90) {
            // 30% probabilidad de casco
            return createRandomHelmet();
        } else {
            // 10% probabilidad de cofre
            return createChest();
        }
    }
    
    /**
     * Crea un cofre que contiene items aleatorios.
     * 
     * @return Un cofre con items aleatorios
     */
    public static Chest createChest() {
        return new Chest(new RandomItemFactory());
    }
    
    /**
     * Crea una poción aleatoria.
     * 
     * @return Una poción aleatoria
     */
    public static GenericItem createRandomPotion() {
        String[] potionTypes = {"HEALTH_POTION", "UPGRADE_HEALTH_POTION", "UPGRADE_MANA_POTION"};
        int potionIndex = (int)(Math.random() * potionTypes.length);
        return SimplePotionFactory.createPotion(potionTypes[potionIndex]);
    }
    
    /**
     * Crea un arma aleatoria.
     * 
     * @return Un arma aleatoria
     */
    public static BaseWeapon createRandomWeapon() {
        WeaponType[] weaponTypes = WeaponType.values();
        int weaponIndex = (int)(Math.random() * weaponTypes.length);
        return SimpleWeaponFactory.createWeapon(weaponTypes[weaponIndex]);
    }
    
    /**
     * Crea un casco aleatorio.
     * 
     * @return Un casco aleatorio
     */
    public static BaseHelmet createRandomHelmet() {
        HelmetType[] helmetTypes = HelmetType.values();
        int helmetIndex = (int)(Math.random() * helmetTypes.length);
        return SimpleHelmetFactory.createHelmet(helmetTypes[helmetIndex]);
    }
} 