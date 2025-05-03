package com.utad.proyectoFinal.characterSystem.characters;


import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.StatesList;
import com.utad.proyectoFinal.characterSystem.images.BaseCharacterImage;
import com.utad.proyectoFinal.characterSystem.images.CharacterImage;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.images.*; // Importa los decoradores
import com.utad.proyectoFinal.mapa.GenericTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BaseCharacter {

    // Contador de personajes (usado para asignar un ID único a cada personaje)
    public static Integer contadorPersonajes = 0;

    // Atributos de identificación
    private Integer id;
    private String name;


    // Atributos de estado y estadísticas
    private Integer healthPoints;
    private Integer maxHealthPoints;
    private Double baseAttack;
    private Double baseDefense;
    private Double baseCounterAttackChance;
    private Double baseCounterAttackDamage;
    private Integer manaPoints;
    private Integer maxManaPoints;


    // Estados del personaje
    private StatesList states;
    private CharacterState currentState; // Estado actual del personaje

    // Atributos de combate
    private Double retreatChance;
    private Boolean retreatSuccess;


    // Equipamiento del personaje
    protected BaseWeapon weapon;  // Arma equipada
    protected BaseHelmet helmet;  // Casco equipado

    // Inventario y efectos
    // TODO: Implement inventory system (find best approach/pattern) State maybe?
    //    private List<Item> inventario;  // Pociones, vendas, botiquines, etc.
    //    private List<Item> efectosActivos;  // Efectos negativos de las trampas, etc.
    private Integer capacidadMaximaInventario;
    

    // Sistema de decoradores de imagen
    private CharacterImage characterImage;
    private final BufferedImage baseAvatar; // Guarda la imagen base original


    // Atributos de posicionamiento
    // TODO: Implement -> Tile
    private GenericTile currentPosition;
    //    private Tile ubicacionActual;
    //    private Tile destinoObjetivo;

    // Comportamiento
    private Boolean esControlado;  // Indica si es controlado por IA
    
    public BaseCharacter(String name, Double baseAttack, Double baseDefense) {
        this(name, baseAttack, baseDefense, loadDefaultAvatar());
    }
    
    private static BufferedImage loadDefaultAvatar() {
        try {
            return ImageIO.read(new File("Files/img/GreenGuy.png"));
        } catch (IOException e) {
            System.err.println("Error loading default avatar: " + e.getMessage());
            // Return a small blank image as fallback
            return new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }
    }
    
    public BaseCharacter(String name, Double baseAttack, Double baseDefense, BufferedImage baseAvatar) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseCounterAttackChance = DefaultAttributes.COUNTERATTACK_PROBABILITY; // Valor por defecto para el ataque de contraataque
        this.baseCounterAttackDamage = DefaultAttributes.COUNTERATTACK_DAMAGE; // Valor por defecto para el multiplicador de contraataque
        this.manaPoints = DefaultAttributes.MANA_POINTS; // Valor por defecto para los puntos de maná
        this.maxManaPoints = DefaultAttributes.MAX_MANA_POINTS; // Valor por defecto

        this.states = new StatesList(this); // Inicializa el estado del personaje
        this.currentState = states.getIdleState(); // Estado inicial

        this.weapon = null;
        this.helmet = null;

        this.retreatChance = DefaultAttributes.RETREAT_PROBABILITY;
        this.maxHealthPoints = DefaultAttributes.HEALTH;
        this.healthPoints = this.maxHealthPoints;

//        this.items = new ArrayList<Item>();
//        this.efectos = new ArrayList<Item>();

        this.id = ++BaseCharacter.contadorPersonajes;

        this.baseAvatar = baseAvatar;
        this.characterImage = new BaseCharacterImage(baseAvatar);
        
        // Todo: Implement -> re-add after Tile class is created
        //this.ubicacionActual = null;
        //this.destinoObjetivo = null;

        this.esControlado = false; // Por defecto, el personaje no es controlado por IA
    }

    // Método privado para actualizar la imagen decorada
    private void updateCharacterImage() {
        // Empieza siempre desde la imagen base
        this.characterImage = new BaseCharacterImage(this.baseAvatar);

        // Aplica el decorador de casco si existe
        if (this.helmet != null && this.helmet.getAvatar() != null) {
            this.characterImage = new HelmetDecorator(this.characterImage, this.helmet.getAvatar());
        }

        // Aplica el decorador de arma si existe
        if (this.weapon != null && this.weapon.getAvatar() != null) {
            this.characterImage = new WeaponDecorator(this.characterImage, this.weapon.getAvatar());
        }
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

    // Setter para el arma que actualiza el decorador
    public void setWeapon(BaseWeapon weapon) {
        this.weapon = weapon;
        updateCharacterImage(); // Reconstruye la imagen decorada
    }


    // Método para renderizar el personaje con todos sus decoradores
    public void render(Graphics2D g, int x, int y) {
        if (characterImage != null) {
            characterImage.render(g, x, y);
        }
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

    public Double getRetreatChance() {
        return retreatChance;
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
}
