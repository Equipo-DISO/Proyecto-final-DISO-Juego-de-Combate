package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.GameManagement.PushModelObserver;
import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.StatesList;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BaseCharacter implements CombatCharacter, MapObject, PushModelObservable {

    // Contador de personajes (usado para asignar un ID único a cada personaje)
    protected static Integer contadorPersonajes = 0;

    // Atributos de identificación
    protected Integer id;
    protected String name;

    // Objetos de composición
    protected CharacterAttributes attributes;
    protected CharacterEquipment equipment;
    protected CharacterVisualizer visualizer;

    // Estados del personaje
    protected StatesList states;
    protected CharacterState currentState; // Estado actual del personaje

    // Atributos de combate
    protected Boolean retreatSuccess;

    // Atributos de posicionamiento
    protected GenericTile currentPosition;

    // Comportamiento
    protected Boolean esControlado; // Indica si es controlado por IA

    // Lista de observers
    protected final List<PushModelObserver> observers = new ArrayList<>();

    public BaseCharacter(String name) {
        this(name, DefaultAttributes.ATTACK);
    }

    public BaseCharacter(String name, Double baseAttack) {
        this(name, baseAttack, null);
    }

    public BaseCharacter(String name, Double baseAttack, Image baseAvatar) {
        this.name = name;
        this.id = ++BaseCharacter.contadorPersonajes;

        // Inicializar los componentes usando composición
        this.attributes = new CharacterAttributes(
                DefaultAttributes.HEALTH,
                DefaultAttributes.MAX_MANA_POINTS,
                baseAttack);

        this.equipment = new CharacterEquipment();

        if (baseAvatar != null) {
            this.visualizer = new CharacterVisualizer(this.equipment, baseAvatar);
        } else {
            this.visualizer = new CharacterVisualizer(this.equipment);
        }

        // Inicializar estados
        this.states = new StatesList(this);
        this.currentState = states.getIdleState();

        this.esControlado = false;
    }

    // ------- Equals -------

    // Use id to compare characters (id is unique)
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseCharacter other = (BaseCharacter) obj;
        return id.equals(other.id);
    }
    // ------- Delegación a CharacterAttributes -------

    public boolean isAlive() {
        return this.attributes.isAlive();
    }

    public void reduceHealth(Integer damage) {
        this.attributes.reduceHealth(damage);
    }

    public Integer getHealthPoints() {
        return this.attributes.getHealthPoints();
    }

    public void setHealthPoints(Integer healthPoints) {
        this.attributes.setHealthPoints(healthPoints);
    }

    public Integer getMaxHealthPoints() {
        return this.attributes.getMaxHealthPoints();
    }

    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.attributes.setMaxHealthPoints(maxHealthPoints);
    }

    public Double getBaseAttack() {
        return this.attributes.getBaseAttack();
    }

    public Integer getManaPoints() {
        return this.attributes.getManaPoints();
    }

    public void setManaPoints(Integer manaPoints) {
        this.attributes.setManaPoints(manaPoints);
    }

    public void decreaseManaPoints(int amount) {
        this.attributes.decreaseManaPoints(amount);
    }

    public Integer getMaxManaPoints() {
        return this.attributes.getMaxManaPoints();
    }

    public void setMaxManaPoints(Integer maxManaPoints) {
        this.attributes.setMaxManaPoints(maxManaPoints);
    }

    public void gainHealth(Integer healthPoints) {
        this.attributes.gainHealth(healthPoints);
    }

    public boolean isLowEnergy() {
        return this.attributes.isLowEnergy();
    }

    public Integer getHpPotions() {
        return this.attributes.getHpPotions();
    }

    public void useHpPotion() {
        this.attributes.useHpPotion();
    }

    public void increaseManaPoints(Integer manaRecovered) {
        this.attributes.increaseManaPoints(manaRecovered);
    }

    // ------- Implementación de la interfaz CombatCharacter -------

    @Override
    public void addHealthPotion() {
        this.attributes.addHealthPotion();
    }

    @Override
    public void addManaUpgrade() {
        this.attributes.addManaUpgrade();
    }

    @Override
    public void addHealthUpgrade() {
        this.attributes.addHealthUpgrade();
    }

    // ------- Delegación a CharacterEquipment -------

    public BaseWeapon getWeapon() {
        return this.equipment.getWeapon();
    }

    public void setWeapon(BaseWeapon weapon) {
        this.equipment.equipWeapon(weapon);
        this.visualizer.updateCharacterImage();
    }

    public BaseHelmet getHelmet() {
        return this.equipment.getHelmet();
    }

    public void setHelmet(BaseHelmet helmet) {
        this.equipment.equipHelmet(helmet);
        this.visualizer.updateCharacterImage();
    }

    // ------- Delegación a CharacterVisualizer -------

    @Override
    public Image getCompleteImage() {
        return this.visualizer.getCompleteImage();
    }

    @Override
    public Image getImage() {
        return this.visualizer.getImage();
    }

    public void setImage(String path) {
        this.visualizer.setImage(path);
    }

    // ------- Gestión de estados -------

    public void transitionTo(CharacterState newState) {
        this.currentState = newState;
    }

    public void setIdleState() {
        transitionTo(states.getIdleState());
    }

    public void setAttackingState() {
        transitionTo(states.getAttackingState());
    }

    public void setHealState() {
        transitionTo(states.getHealState());
    }

    public void setGainManaState() {
        transitionTo(states.getGainManaState());
    }

    public void setRetreatingState() {
        transitionTo(states.getRetreatingState());
    }

    public void setTiredState() {
        transitionTo(states.getTiredState());
    }

    public void setMovingOnMapState() {
        transitionTo(states.getMovingOnMapState());
    }

    public void setDeadState() {
        transitionTo(states.getDeadState());
    }

    public void setCurrentState(CharacterState state) {
        if (state == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.currentState = state;
    }

    public CharacterState getCurrentState() {
        return currentState;
    }

    public StatesList getStates() {
        return states;
    }

    // ------- Getters y setters de atributos simples -------

    public String getName() {
        return this.name;
    }

    public Boolean isRetreatSuccessful() {
        return retreatSuccess;
    }

    public void setRetreatSuccess(Boolean retreatSuccess) {
        this.retreatSuccess = retreatSuccess;
    }

    public Integer getId() {
        return id;
    }

    public GenericTile getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(GenericTile currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Boolean getEsControlado() {
        return esControlado;
    }

    // ------- Métodos de combate -------

    @Override
    public void attack(CombatCharacter target, AttackStrategy strategy) {
        if (target instanceof BaseCharacter targetBaseCharacter) {
            this.currentState.handleAttack(targetBaseCharacter, strategy);
        } else {
            System.out.println("El objetivo no es un personaje válido.");
        }
    }

    @Override
    public boolean retreat(CombatCharacter opponent) {
        if (opponent instanceof BaseCharacter opponentBaseCharacter) {
            this.currentState.handleRetreat(opponentBaseCharacter);
            return this.retreatSuccess;
        } else {
            System.out.println("El oponente no es un personaje válido.");
            return false;
        }
    }

    @Override
    public void heal() {
        this.currentState.handleHeal();
    }

    @Override
    public void move(GenericTile moveToTile) {
        currentState.handleMove(moveToTile);
    }

    @Override
    public void equipWeapon(BaseWeapon weapon) {
        this.setWeapon(weapon);
    }

    @Override
    public void equipHelmet(BaseHelmet helmet) {
        this.setHelmet(helmet);
    }

    @Override
    public String getCurrentStateName() {
        if (currentState != null) {
            return currentState.getClass().getSimpleName();
        } else {
            return "No State";
        }
    }

    // ------- Observer pattern management methods -------

    public void addObserver(PushModelObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(PushModelObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyDeathObservers() {
        // Clear the tile's occupied object when character dies
        if (this.currentPosition != null) {
            this.currentPosition.setOcupiedObject(null);
        }
        
        for (PushModelObserver observer : observers) {
            observer.characterHasDied(this);
        }
    }
}
