package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.ui.combat.CombatInterface;

import java.awt.Image;

/**
 * Define la interfaz pública para los personajes en el sistema de combate.
 * Los sistemas externos deben interactuar con los personajes a través de estos métodos
 * en lugar de manipular directamente los estados.
 * <p>
 * Esta interfaz proporciona métodos para acceder a la información básica del personaje,
 * realizar acciones de combate, gestionar el equipamiento y los estados.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public interface CombatCharacter {
    
    // Basic character information
    /**
     * Obtiene el nombre del personaje.
     * 
     * @return El nombre del personaje
     */
    String getName();
    
    /**
     * Verifica si el personaje está vivo.
     * 
     * @return {@code true} si el personaje está vivo, {@code false} en caso contrario
     */
    boolean isAlive();
    
    /**
     * Obtiene los puntos de salud actuales del personaje.
     * 
     * @return Los puntos de salud actuales
     */
    Integer getHealthPoints();
    
    /**
     * Obtiene los puntos de maná actuales del personaje.
     * 
     * @return Los puntos de maná actuales
     */
    Integer getManaPoints();
    
    /**
     * Obtiene los puntos de salud máximos del personaje.
     * 
     * @return Los puntos de salud máximos
     */
    Integer getMaxHealthPoints();
    
    /**
     * Obtiene los puntos de maná máximos del personaje.
     * 
     * @return Los puntos de maná máximos
     */
    Integer getMaxManaPoints();
    
    // Combat actions
    /**
     * Realiza un ataque contra un objetivo utilizando una estrategia específica.
     * 
     * @param target El personaje objetivo del ataque
     * @param attackStrategy La estrategia de ataque a utilizar
     */
    void attack(CombatCharacter target, AttackStrategy attackStrategy);
    
    /**
     * Intenta retirarse del combate frente a un oponente.
     * 
     * @param opponent El oponente del cual se intenta huir
     * @return {@code true} si la retirada fue exitosa, {@code false} en caso contrario
     */
    boolean retreat(CombatCharacter opponent);
    
    /**
     * Utiliza una poción para recuperar salud.
     */
    void heal();
    
    /**
     * Mueve el personaje a una casilla específica del mapa.
     * 
     * @param moveToTile La casilla de destino
     */
    void move(GenericTile moveToTile);
    
    /**
     * Realiza una acción para recuperar puntos de maná.
     */
    void gainMana();
        
    // Equipment management
    /**
     * Equipa un arma al personaje.
     * 
     * @param weapon El arma a equipar
     */
    void equipWeapon(BaseWeapon weapon);
    
    /**
     * Equipa un casco al personaje.
     * 
     * @param helmet El casco a equipar
     */
    void equipHelmet(BaseHelmet helmet);
    
    /**
     * Obtiene el arma equipada actualmente por el personaje.
     * 
     * @return El arma actual, o {@code null} si no tiene ninguna equipada
     */
    BaseWeapon getWeapon();
    
    /**
     * Obtiene el casco equipado actualmente por el personaje.
     * 
     * @return El casco actual, o {@code null} si no tiene ninguno equipado
     */
    BaseHelmet getHelmet();
    
    /**
     * Obtiene la cantidad de pociones de salud disponibles.
     * 
     * @return El número de pociones de salud
     */
    Integer getHpPotions();

    // Upgrades
    /**
     * Añade una poción de salud al inventario del personaje.
     */
    void addHealthPotion();
    
    /**
     * Aplica una mejora permanente a los puntos de maná máximos del personaje.
     */
    void addManaUpgrade();
    
    /**
     * Aplica una mejora permanente a los puntos de salud máximos del personaje.
     */
    void addHealthUpgrade();

    // State management (might be internal, but useful for tests)
    /**
     * Obtiene el nombre del estado actual del personaje.
     * 
     * @return El nombre del estado actual
     */
    String getCurrentStateName();

    /**
     * Obtiene la imagen completa del personaje con todas las decoraciones aplicadas.
     * 
     * @return La imagen completa del personaje con todas las decoraciones
     */
    Image getCompleteImage();

    // Paths
    /**
     * Obtiene la ruta de la imagen base del personaje.
     * 
     * @return La ruta de la imagen base
     */
    String getBaseImagePath();
    
    /**
     * Establece la ruta de la imagen base del personaje.
     * 
     * @param baseImagePath La nueva ruta de la imagen base
     */
    void setBaseImagePath(String baseImagePath);

    // Feed Logger
    /**
     * Establece la interfaz de combate para registrar las acciones y eventos.
     * 
     * @param combatInterface La interfaz de combate a utilizar para el registro
     */
    void setFeedLogger(CombatInterface combatInterface);
}