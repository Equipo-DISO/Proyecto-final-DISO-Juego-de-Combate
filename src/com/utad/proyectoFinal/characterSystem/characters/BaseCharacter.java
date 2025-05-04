package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.gameManagement.PushModelObserver;
import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.StatesList;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.images.BaseCharacterImage;
import com.utad.proyectoFinal.characterSystem.images.CharacterImage;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.images.*; // Importa los decoradores
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapObject;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseCharacter implements CombatCharacter, MapObject, PushModelObservable {

    // Contador de personajes (usado para asignar un ID único a cada personaje)
    public static Integer contadorPersonajes = 0;

    // Atributos de identificación
    private Integer id;
    private String name;

    // Atributos de estado y estadísticas
    private Integer healthPoints;
    private Integer maxHealthPoints;
    private Double baseAttack;
    private Integer manaPoints;
    private Integer maxManaPoints;
    private Integer hpPotions; // Cantidad de pociones de salud

    // Estados del personaje
    private StatesList states;
    private CharacterState currentState; // Estado actual del personaje

    // Atributos de combate
    private Boolean retreatSuccess;

    // Equipamiento del personaje
    protected BaseWeapon weapon; // Arma equipada
    protected BaseHelmet helmet; // Casco equipado

    // Sistema de decoradores de imagen
    protected CharacterImage characterImage;
    protected Image baseAvatar; // Guarda la imagen base original

    // Atributos de posicionamiento
    private GenericTile currentPosition;

    // Comportamiento
    private Boolean esControlado; // Indica si es controlado por IA

    // Lista de observers
    private final List<PushModelObserver> observers = new ArrayList<>();

    public BaseCharacter(String name) {
        this(name, DefaultAttributes.ATTACK);
    }

    public BaseCharacter(String name, Double baseAttack) {
        this(name, baseAttack, loadDefaultAvatar());
    }

    public BaseCharacter(String name, Double baseAttack, Image baseAvatar) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.manaPoints = DefaultAttributes.MANA_POINTS; // Valor por defecto para los puntos de maná
        this.maxManaPoints = DefaultAttributes.MAX_MANA_POINTS; // Valor por defecto
        this.hpPotions = 0; // Inicialmente no tiene pociones de salud

        this.states = new StatesList(this); // Inicializa el estado del personaje
        this.currentState = states.getIdleState(); // Estado inicial

        this.weapon = null;
        this.helmet = null;

        this.maxHealthPoints = DefaultAttributes.HEALTH;
        this.healthPoints = this.maxHealthPoints;

        this.id = ++BaseCharacter.contadorPersonajes;

        this.baseAvatar = baseAvatar;
        this.characterImage = new BaseCharacterImage(baseAvatar);

        this.esControlado = false; // Por defecto, el personaje no es controlado por IA
    }

    private static Image loadDefaultAvatar() {
        try {
            return ImageIO.read(new File("Files/img/GreenGuy.png"));
        } catch (IOException e) {
            System.err.println("Error loading default avatar: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }
    }

    // Método privado para actualizar la imagen decorada
    private void updateCharacterImage() {
        // Start with the base image
        this.characterImage = new BaseCharacterImage(this.baseAvatar);
        
        // Apply decorators in a defined order using the factory method
        // This decouples BaseCharacter from specific decorator implementations
        this.characterImage = EquipmentDecorator.createFor(this.characterImage, this.helmet);
        this.characterImage = EquipmentDecorator.createFor(this.characterImage, this.weapon);
    }
    
    // Setter para el casco que actualiza el decorador
    public void setHelmet(BaseHelmet helmet) {
        this.helmet = helmet;
        updateCharacterImage(); // Reconstruye la imagen decorada
    }

    // Getter para el casco
    public BaseHelmet getHelmet() {
        return helmet;
    }

    @Override
    public void addHealthPotion() {
        this.hpPotions++;
    }

    @Override
    public void addManaUpgrade() {
        this.manaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
        this.maxManaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
    }

    @Override
    public void addHealthUpgrade() {
        this.healthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
        this.maxHealthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
    }

    // Setter para el arma que actualiza el decorador
    public void setWeapon(BaseWeapon weapon) {
        this.weapon = weapon;
        updateCharacterImage(); // Reconstruye la imagen decorada
    }

    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    public void reduceHealth(Integer damage) {
        this.healthPoints = Math.max(0, this.healthPoints - damage);
    }

    public Integer getHealthPoints() {
        return this.healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Integer getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public String getName() {
        return this.name;
    }

    public Boolean isRetreatSuccessful() {
        return retreatSuccess;
    }

    public void setRetreatSuccess(Boolean retreatSuccess) {
        this.retreatSuccess = retreatSuccess;
    }

    // Metodo auxiliar para cambiar el estado
    public void transitionTo(CharacterState newState) {
        this.currentState = newState;
    }

    // "setXState" por delegación
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

    // Getter para reutilizar la lista desde los estados
    public StatesList getStates() {
        return states;
    }

    public Double getBaseAttack() {
        return baseAttack;
    }

    public Integer getId() {
        return id;
    }

    public BaseWeapon getWeapon() {
        return weapon;
    }

    public Integer getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(Integer manaPoints) {
        this.manaPoints = manaPoints;
    }

    public void decreaseManaPoints(int amount) {
        this.manaPoints = Math.max(0, manaPoints - amount);
    }

    public Integer getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setMaxManaPoints(Integer maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public void gainHealth(Integer healthPoints) {
        this.healthPoints = Math.min(healthPoints, this.maxHealthPoints);
    }

    public boolean isLowEnergy() {
        return manaPoints < DefaultAttributes.LOW_MANA_THRESHOLD;
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

    /**
     * Attack with a specific strategy
     * 
     * @param target   The character to attack
     * @param strategy The strategy to use for this attack
     */
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
            return this.retreatSuccess; // Return the retreat success value
        } else {
            System.out.println("El oponente no es un personaje válido.");
            return false; // Invalid opponent means retreat fails
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

    public Integer getHpPotions() {
        return hpPotions;
    }

    public void useHpPotion() {
        hpPotions--;
    }

    @Override
    public Image getCompleteImage() {
        if (characterImage != null) {
            return characterImage.getCompleteImage();
        }
        return null;
    }

    @Override
    public Image getImage() {
        return getCompleteImage();
    }

    // Image
    public void setImage(String path) {
        try {
            this.baseAvatar = ImageIO.read(new File(path));
        } catch (IOException e) {
            loadDefaultAvatar();
        }
        updateCharacterImage();
    }


    public void increaseManaPoints(Integer manaRecovered) {
        this.manaPoints = Math.min(this.manaPoints + manaRecovered, this.maxManaPoints);
    }

    //observer pattern management methods
    public void addObserver(PushModelObserver observer) {
        this.observers.add(observer);
    }
    public void removeObserver(PushModelObserver observer) {
        this.observers.remove(observer);
    }
    @Override
    public void notifyDeathObservers() {
        for(PushModelObserver observer : observers) {
            observer.characterHasDied(this);
        }
    }
}
