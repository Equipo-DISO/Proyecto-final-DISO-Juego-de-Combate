package com.utad.proyectoFinal.characterSystem.characters;


import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.StatesList;

import java.awt.image.BufferedImage;

public abstract class BaseCharacter {

    // Contador de personajes (usado para asignar un ID único a cada personaje)
    public static Integer contadorPersonajes = 0;

    // Atributos de identificación
    private Integer id;
    private String name;

    // Atributos de estado y estadísticas
    private Integer healthPoints;
    private Double baseAttack;
    private Double baseDefense;
    private Double baseCounterAttackChance;
    private Double baseCounterAttackDamage;

    // Estados del personaje
    private StatesList states;
    private CharacterState currentState; // Estado actual del personaje

    // Atributos de combate
    private Double retreatChance;
    private Boolean retreatSuccess;


    // Equipamiento del personaje
    // TODO: Implement -> private Arma arma;
    // TODO: Implement -> private Escudo escudo;

    // Inventario y efectos
    // TODO: Implement inventory system (find best approach/pattern) State maybe?
    //    private List<Item> inventario;  // Pociones, vendas, botiquines, etc.
    //    private List<Item> efectosActivos;  // Efectos negativos de las trampas, etc.
    private Integer capacidadMaximaInventario;

    // Atributos de representación gráfica
    private BufferedImage imagen;

    // Atributos de posicionamiento
    // TODO: Implement -> Tile
    //    private Tile ubicacionActual;
    //    private Tile destinoObjetivo;

    // Comportamiento
    private Boolean esControlado;  // Indica si es controlado por IA

    /* Constructor (Solo uno, los hijos se encargan de los Defaults) */
    public BaseCharacter(String name, Double baseAttack, Double baseDefense /*, Arma arma, Escudo escudo*/) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseCounterAttackChance = DefaultAttributes.COUNTERATTACK_PROBABILITY; // Valor por defecto para el ataque de contraataque
        this.baseCounterAttackDamage = DefaultAttributes.COUNTERATTACK_DAMAGE; // Valor por defecto para el multiplicador de contraataque

        this.states = new StatesList(this); // Inicializa el estado del personaje
        this.currentState = states.getIdleState(); // Estado inicial

        // TODO: Implement after creating arma and escudo classes
        // this.arma = arma;
        // this.escudo = escudo;

        this.retreatChance = DefaultAttributes.RETREAT_PROBABILITY;
        this.healthPoints = DefaultAttributes.HEALTH;

//        this.items = new ArrayList<Item>();
//        this.efectos = new ArrayList<Item>();

        this.id = ++BaseCharacter.contadorPersonajes;
        this.imagen = null; // TODO: Placeholder, to be set in the future
        // Todo: Implement -> re-add after Tile class is created
        //this.ubicacionActual = null;
        //this.destinoObjetivo = null;

        this.esControlado = false; // Por defecto, el personaje no es controlado por IA
    }


    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    public void reduceHealth(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return this.name;
    }

    public Double getRetreatChance() {
        return retreatChance;
    }

    public Boolean isRetreatSuccessful() {
        return retreatSuccess;
    }

    public void setRetreatSuccess(Boolean retreatSuccess) {
        this.retreatSuccess = retreatSuccess;
    }

    public void setIdleState() {
        setCurrentState(states.getIdleState());
    }

    public void setCurrentState(CharacterState state) {
        if (state == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.currentState = state;
    }

    public void setDeadState() {
        setCurrentState(states.getDeadState());
    }

    public CharacterState getCurrentState() {
        return currentState;
    }

    public Double getBaseCounterAttackChance() {
        return baseCounterAttackChance;
    }

    public void setBaseCounterAttackChance(Double baseCounterAttackChance) {
        this.baseCounterAttackChance = baseCounterAttackChance;
    }

    public Double getBaseCounterAttackDamage() {
        return baseCounterAttackDamage;
    }

    public Double getBaseAttack() {
        return baseAttack;
    }

    public Double getBaseDefense() {
        return baseDefense;
    }

    public Integer getId() {
        return id;
    }

    public abstract String getSpecialAbility();
}
