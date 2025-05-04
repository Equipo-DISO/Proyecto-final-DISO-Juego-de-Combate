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
     * Crea un item aleatorio (poción, arma o casco).
     * 
     * @return Un item aleatorio
     */
    public static MapObject createRandomItem() {
        // Generar un número aleatorio para decidir el tipo de item
        int itemType = (int)(Math.random() * 3); // 0: poción, 1: arma, 2: casco
        
        switch(itemType) {
            case 0: // Poción
                return createRandomPotion();
                
            case 1: // Arma
                return createRandomWeapon();
                
            case 2: // Casco
                return createRandomHelmet();
                
            default:
                // Por defecto, devolver una poción de vida
                return SimplePotionFactory.createPotion("HEALTH_POTION");
        }
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