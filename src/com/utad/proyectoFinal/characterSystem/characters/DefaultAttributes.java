package com.utad.proyectoFinal.characterSystem.characters;

/**
 * Clase utilitaria que define los valores por defecto para los atributos de los personajes.
 * <p>
 * Esta clase final con constructor privado actúa como un contenedor de constantes
 * que definen los valores predeterminados para salud, ataque, maná y otros atributos
 * utilizados en la creación y gestión de personajes.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public final class DefaultAttributes {
    // Vital attributes
    /**
     * Puntos de salud iniciales por defecto para los personajes.
     */
    public static final Integer HEALTH = 100;

    // Offensive attributes
    /**
     * Valor de ataque base por defecto para los personajes.
     */
    public static final Double ATTACK = 5.0;

    // Upgrade attributes
    /**
     * Cantidad de salud que recupera una poción de salud al ser consumida.
     */
    public static final Integer POTION_HEAL_AMOUNT = 50;
    
    /**
     * Cantidad de salud máxima añadida al personaje al aplicar una mejora de salud.
     */
    public static final Integer UPGRADE_HEALTH_AMOUNT = 25;
    
    /**
     * Cantidad de maná máximo añadido al personaje al aplicar una mejora de maná.
     */
    public static final Integer UPGRADE_MANA_AMOUNT = 20;

    // Mana attributes
    /**
     * Puntos de maná iniciales por defecto para los personajes.
     */
    public static final Integer MANA_POINTS = 50;
    
    /**
     * Puntos de maná máximos por defecto para los personajes.
     */
    public static final Integer MAX_MANA_POINTS = 50;
    
    /**
     * Umbral de puntos de maná por debajo del cual se considera que el personaje tiene poca energía.
     * Esto afecta a varias mecánicas de juego, como la posibilidad de realizar ciertos ataques.
     */
    public static final Integer LOW_MANA_THRESHOLD = 7;

    /**
     * Constructor privado para prevenir la instanciación de esta clase utilitaria.
     */
    private DefaultAttributes() {}
}