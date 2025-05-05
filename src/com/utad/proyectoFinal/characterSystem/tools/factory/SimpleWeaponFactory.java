package com.utad.proyectoFinal.characterSystem.tools.factory;

import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.items.WeaponType;

/**
 * Fábrica simple para crear armas con métodos estáticos.
 * No requiere instanciación para su uso.
 */
public class SimpleWeaponFactory {
    
    /**
     * Constructor privado para evitar instanciación.
     * Esta clase solo proporciona métodos estáticos.
     */
    private SimpleWeaponFactory() {
        // No permite instanciación
    }
    
    /**
     * Crea una nueva arma del tipo especificado.
     * 
     * @param weaponType El tipo de arma a crear (como String)
     * @return Una nueva instancia del arma
     * @throws IllegalArgumentException si el tipo de arma no es válido
     */
    public static BaseWeapon createWeapon(String weaponType) {
        // Convertir a mayúsculas para normalizar
        String type = weaponType.toUpperCase();
        
        // Crear el arma según el tipo
        switch (type) {
            case "STICK":
                return new BaseWeapon(WeaponType.STICK);
            case "KNIFE":
                return new BaseWeapon(WeaponType.KNIFE);
            case "AXE":
                return new BaseWeapon(WeaponType.AXE);
            case "SWORD":
                return new BaseWeapon(WeaponType.SWORD);
            case "SPEAR":
                return new BaseWeapon(WeaponType.SPEAR);
            default:
                throw new IllegalArgumentException("Tipo de arma desconocido: " + weaponType);
        }
    }
    
    /**
     * Crea una nueva arma usando directamente el enum WeaponType.
     * 
     * @param weaponType El tipo de arma a crear
     * @return Una nueva instancia del arma
     */
    public static BaseWeapon createWeapon(WeaponType weaponType) {
        return new BaseWeapon(weaponType);
    }
    
    /**
     * Verifica si esta fábrica puede crear un arma del tipo especificado.
     * 
     * @param weaponType El tipo de arma a verificar (como String)
     * @return true si la fábrica puede crear el arma, false en caso contrario
     */
    public static boolean canCreateWeaponType(String weaponType) {
        String type = weaponType.toUpperCase();
        
        return type.equals("STICK") || 
               type.equals("KNIFE") || 
               type.equals("AXE") ||
               type.equals("SWORD") ||
               type.equals("SPEAR");
    }
} 