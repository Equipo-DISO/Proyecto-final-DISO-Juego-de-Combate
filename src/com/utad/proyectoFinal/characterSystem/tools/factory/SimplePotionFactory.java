package com.utad.proyectoFinal.characterSystem.tools.factory;

import com.utad.proyectoFinal.characterSystem.tools.items.GenericItem;
import com.utad.proyectoFinal.characterSystem.tools.items.HealthPotion;
import com.utad.proyectoFinal.characterSystem.tools.items.UpgradeHealthPotion;
import com.utad.proyectoFinal.characterSystem.tools.items.UpgradeManaPotion;

/**
 * Implementación simple de una fábrica de pociones con métodos estáticos.
 * No requiere instanciación para su uso.
 */
public class SimplePotionFactory {
    
    /**
     * Constructor privado para evitar instanciación.
     * Esta clase solo proporciona métodos estáticos.
     */
    private SimplePotionFactory() {
        // No permite instanciación
    }
    
    /**
     * Crea una nueva poción del tipo especificado.
     * 
     * @param potionType El tipo de poción a crear ("HEALTH_POTION", "UPGRADE_HEALTH_POTION", etc.)
     * @return Una nueva instancia de la poción solicitada
     * @throws IllegalArgumentException si el tipo de poción no es reconocido
     */
    public static GenericItem createPotion(String potionType) {
        // Convertir a mayúsculas para hacer la comparación insensible a mayúsculas/minúsculas
        String type = potionType.toUpperCase();
        
        // Crear la poción según su tipo
        switch (type) {
            case "HEALTH_POTION":
                return new HealthPotion();
                
            case "UPGRADE_HEALTH_POTION":
                return new UpgradeHealthPotion();
                
            case "UPGRADE_MANA_POTION":
                return new UpgradeManaPotion();
                
            default:
                throw new IllegalArgumentException("Tipo de poción desconocido: " + potionType);
        }
    }
    
    /**
     * Verifica si esta fábrica puede crear una poción del tipo especificado.
     * 
     * @param potionType El tipo de poción a verificar
     * @return true si la fábrica puede crear la poción, false en caso contrario
     */
    public static boolean canCreatePotionType(String potionType) {
        String type = potionType.toUpperCase();
        
        return type.equals("HEALTH_POTION") || 
               type.equals("UPGRADE_HEALTH_POTION") || 
               type.equals("UPGRADE_MANA_POTION");
    }
} 