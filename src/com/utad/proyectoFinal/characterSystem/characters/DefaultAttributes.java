package com.utad.proyectoFinal.characterSystem.characters;

public final class DefaultAttributes {
    // Vital attributes
    public static final Integer HEALTH = 100;

    // Offensive attributes
    public static final Double ATTACK = 5.0;
    public static final Double COUNTERATTACK_DAMAGE = 50.0;

    // Defensive attributes
    public static final Double DEFENSE = 0.0;

    // Action probabilities
    public static final Double RETREAT_PROBABILITY = 20.0;
    public static final Double COUNTERATTACK_PROBABILITY = 10.0;

    // Inventory limits
    public static final Integer INITIAL_ITEMS = 0;
    public static final Integer MAX_ITEMS = 5;

    // Mana attributes
    public static final Integer MANA_POINTS = 50;
    public static final Integer MAX_MANA_POINTS = 50;
    // Umbral de maná por debajo del cual el personaje está cansado
    public static final Integer LOW_MANA_THRESHOLD = 7;

    // Private constructor to prevent instantiation
    private DefaultAttributes() {}
}