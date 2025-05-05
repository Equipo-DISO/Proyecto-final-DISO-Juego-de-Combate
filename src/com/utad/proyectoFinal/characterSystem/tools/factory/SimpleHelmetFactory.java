package com.utad.proyectoFinal.characterSystem.tools.factory;

import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.HelmetType;

/**
 * Fábrica simple para crear cascos con métodos estáticos.
 * No requiere instanciación para su uso.
 */
public class SimpleHelmetFactory {
    
    /**
     * Constructor privado para evitar instanciación.
     * Esta clase solo proporciona métodos estáticos.
     */
    private SimpleHelmetFactory() {
        // No permite instanciación
    }
    
    /**
     * Crea un nuevo casco del tipo especificado.
     * 
     * @param helmetType El tipo de casco a crear (puede ser el nombre o directamente el enum)
     * @return Una nueva instancia del casco
     * @throws IllegalArgumentException si el tipo de casco no es válido
     */
    public static BaseHelmet createHelmet(String helmetType) {
        // Convertir a mayúsculas para normalizar
        String type = helmetType.toUpperCase();
        
        // Crear el casco según el tipo
        switch (type) {
            case "NORMAL_HELMET":
                return new BaseHelmet(HelmetType.NORMAL_HELMET);
            case "SIMPLE_HELMET":
                return new BaseHelmet(HelmetType.SIMPLE_HELMET);
            case "DEMON_HELMET":
                return new BaseHelmet(HelmetType.DEMON_HELMET);
            default:
                throw new IllegalArgumentException("Tipo de casco desconocido: " + helmetType);
        }
    }
    
    /**
     * Crea un nuevo casco usando directamente el enum HelmetType.
     * 
     * @param helmetType El tipo de casco a crear
     * @return Una nueva instancia del casco
     */
    public static BaseHelmet createHelmet(HelmetType helmetType) {
        return new BaseHelmet(helmetType);
    }
    
    /**
     * Verifica si esta fábrica puede crear un casco del tipo especificado.
     * 
     * @param helmetType El tipo de casco a verificar (como String)
     * @return true si la fábrica puede crear el casco, false en caso contrario
     */
    public static boolean canCreateHelmetType(String helmetType) {
        String type = helmetType.toUpperCase();
        
        return type.equals("NORMAL_HELMET") || 
               type.equals("SIMPLE_HELMET") || 
               type.equals("DEMON_HELMET");
    }
} 