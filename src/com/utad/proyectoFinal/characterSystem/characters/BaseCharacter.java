package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.gameManagement.PushModelObserver;
import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.StatesList;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapObject;
import com.utad.proyectoFinal.mapa.RenderParameters;
import com.utad.proyectoFinal.ui.combat.CombatInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para todos los personajes del juego.
 * <p>
 * Esta clase implementa la interfaz {@code CombatCharacter}, proporcionando funcionalidad
 * completa para personajes jugables y no jugables. 
 * Tambien implementa las interfaces {@code MapObject} para ser tratado por el mapa de manera abstracta,
 * y la interfaz {@code PushModelObservable} para informar al los observadores de su muerte
 * Utiliza el principio de composición para estructurar sus características en tres componentes principales:
 * <ul>
 *   <li>{@code CharacterAttributes}: Gestiona atributos como salud, maná y ataque</li>
 *   <li>{@code CharacterEquipment}: Maneja el equipamiento (armas y cascos)</li>
 *   <li>{@code CharacterVisualizer}: Controla la representación visual</li>
 * </ul>
 * </p>
 * <p>
 * Además, implementa el patrón State para gestionar los diferentes estados y comportamientos
 * del personaje durante el juego, y el patrón Observer para notificar eventos importantes.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
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

    // Atributos de feed
    protected CombatInterface combatInterface;

    // Comportamiento
    protected Boolean esControlado; // Indica si es controlado por IA

    // Lista de observers
    protected final List<PushModelObserver> observers = new ArrayList<>();

    /**
     * Constructor básico que crea un personaje con el nombre especificado y valores por defecto.
     * 
     * @param name El nombre del personaje
     */
    public BaseCharacter(String name) {
        this(name, DefaultAttributes.ATTACK);
    }

    /**
     * Constructor que crea un personaje con nombre y ataque base personalizados.
     * 
     * @param name El nombre del personaje
     * @param baseAttack El valor de ataque base
     */
    public BaseCharacter(String name, Double baseAttack) {
        this(name, baseAttack, null);
    }

    /**
     * Constructor completo que crea un personaje con nombre, ataque e imagen personalizados.
     * 
     * @param name El nombre del personaje
     * @param baseAttack El valor de ataque base
     * @param baseAvatar La imagen base del personaje (puede ser null)
     */
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

    /**
     * Compara si este personaje es igual a otro objeto.
     * Dos personajes se consideran iguales si tienen el mismo ID.
     * 
     * @param obj El objeto a comparar
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario
     */
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

    /**
     * Verifica si el personaje está vivo.
     * 
     * @return {@code true} si el personaje tiene más de 0 puntos de salud, {@code false} en caso contrario
     */
    public boolean isAlive() {
        return this.attributes.isAlive();
    }

    /**
     * Reduce los puntos de salud del personaje por una cantidad de daño.
     * Si la salud llega a cero, el personaje cambia al estado de muerto y es eliminado de su posición.
     * 
     * @param damage Cantidad de daño a aplicar
     */
    public void reduceHealth(Integer damage) {
        this.attributes.reduceHealth(damage);
        
        // If health reaches 0, handle death
        if (!this.attributes.isAlive()) {
            // Transition to DeadState
            transitionTo(states.getDeadState());
            
            // Clear from current position
            if (currentPosition != null) {
                currentPosition.setOcupiedObject(null);
            }
        }
    }

    /**
     * Obtiene los puntos de salud actuales del personaje.
     * 
     * @return Los puntos de salud actuales
     */
    public Integer getHealthPoints() {
        return this.attributes.getHealthPoints();
    }

    /**
     * Establece los puntos de salud actuales del personaje.
     * 
     * @param healthPoints Nuevos puntos de salud
     */
    public void setHealthPoints(Integer healthPoints) {
        this.attributes.setHealthPoints(healthPoints);
    }

    /**
     * Obtiene los puntos de salud máximos del personaje.
     * 
     * @return Los puntos de salud máximos
     */
    public Integer getMaxHealthPoints() {
        return this.attributes.getMaxHealthPoints();
    }

    /**
     * Establece los puntos de salud máximos del personaje.
     * 
     * @param maxHealthPoints Nuevos puntos de salud máximos
     */
    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.attributes.setMaxHealthPoints(maxHealthPoints);
    }

    /**
     * Obtiene el valor de ataque base del personaje.
     * 
     * @return El valor de ataque base
     */
    public Double getBaseAttack() {
        return this.attributes.getBaseAttack();
    }

    /**
     * Obtiene los puntos de maná actuales del personaje.
     * 
     * @return Los puntos de maná actuales
     */
    public Integer getManaPoints() {
        return this.attributes.getManaPoints();
    }

    /**
     * Establece los puntos de maná actuales del personaje.
     * 
     * @param manaPoints Nuevos puntos de maná
     */
    public void setManaPoints(Integer manaPoints) {
        this.attributes.setManaPoints(manaPoints);
    }

    /**
     * Reduce los puntos de maná del personaje.
     * 
     * @param amount Cantidad de maná a reducir
     */
    public void decreaseManaPoints(int amount) {
        this.attributes.decreaseManaPoints(amount);
    }

    /**
     * Obtiene los puntos de maná máximos del personaje.
     * 
     * @return Los puntos de maná máximos
     */
    public Integer getMaxManaPoints() {
        return this.attributes.getMaxManaPoints();
    }

    /**
     * Establece los puntos de maná máximos del personaje.
     * 
     * @param maxManaPoints Nuevos puntos de maná máximos
     */
    public void setMaxManaPoints(Integer maxManaPoints) {
        this.attributes.setMaxManaPoints(maxManaPoints);
    }

    /**
     * Incrementa los puntos de salud del personaje.
     * 
     * @param healthPoints Cantidad de salud a recuperar
     */
    public void gainHealth(Integer healthPoints) {
        this.attributes.gainHealth(healthPoints);
    }

    /**
     * Verifica si el personaje tiene poca energía (maná).
     * 
     * @return {@code true} si el maná está por debajo del umbral, {@code false} en caso contrario
     */
    public boolean isLowEnergy() {
        return this.attributes.isLowEnergy();
    }

    /**
     * Obtiene la cantidad de pociones de salud disponibles.
     * 
     * @return El número de pociones de salud
     */
    public Integer getHpPotions() {
        return this.attributes.getHpPotions();
    }

    /**
     * Utiliza una poción de salud si el personaje tiene al menos una disponible.
     */
    public void useHpPotion() {
        this.attributes.useHpPotion();
    }

    /**
     * Incrementa los puntos de maná del personaje.
     * 
     * @param manaRecovered Cantidad de maná a recuperar
     */
    public void increaseManaPoints(Integer manaRecovered) {
        this.attributes.increaseManaPoints(manaRecovered);
    }

    // ------- Implementación de la interfaz CombatCharacter -------

    /**
     * Añade una poción de salud al inventario del personaje.
     */
    @Override
    public void addHealthPotion() {
        this.attributes.addHealthPotion();
    }

    /**
     * Aplica una mejora permanente a los puntos de maná máximos del personaje.
     */
    @Override
    public void addManaUpgrade() {
        this.attributes.addManaUpgrade();
    }

    /**
     * Aplica una mejora permanente a los puntos de salud máximos del personaje.
     */
    @Override
    public void addHealthUpgrade() {
        this.attributes.addHealthUpgrade();
    }

    // ------- Delegación a CharacterEquipment -------

    /**
     * Obtiene el arma equipada actualmente por el personaje.
     * 
     * @return El arma actual, o {@code null} si no tiene ninguna equipada
     */
    public BaseWeapon getWeapon() {
        return this.equipment.getWeapon();
    }

    /**
     * Establece el arma equipada por el personaje y actualiza su imagen.
     * 
     * @param weapon La nueva arma a equipar
     */
    public void setWeapon(BaseWeapon weapon) {
        this.equipment.equipWeapon(weapon);
        this.visualizer.updateCharacterImage();
    }

    /**
     * Obtiene el casco equipado actualmente por el personaje.
     * 
     * @return El casco actual, o {@code null} si no tiene ninguno equipado
     */
    public BaseHelmet getHelmet() {
        return this.equipment.getHelmet();
    }

    /**
     * Establece el casco equipado por el personaje y actualiza su imagen.
     * 
     * @param helmet El nuevo casco a equipar
     */
    public void setHelmet(BaseHelmet helmet) {
        this.equipment.equipHelmet(helmet);
        this.visualizer.updateCharacterImage();
    }

    // ------- Delegación a CharacterVisualizer -------

    /**
     * Obtiene la imagen completa del personaje con todas las decoraciones aplicadas.
     * 
     * @return La imagen completa del personaje
     */
    @Override
    public Image getCompleteImage() {
        return this.visualizer.getCompleteImage();
    }

    /**
     * Obtiene la imagen del personaje para mostrar en el mapa.
     * 
     * @return La imagen del personaje
     */
    @Override
    public Image getImage() {
        return this.visualizer.getImage();
    }

    /**
     * Establece una nueva imagen base para el personaje a partir de una ruta de archivo.
     * 
     * @param path Ruta del archivo de imagen
     */
    public void setImage(String path) {
        this.visualizer.setImage(path);
    }

    // ------- Gestión de estados -------

    /**
     * Cambia el estado actual del personaje a un nuevo estado.
     * 
     * @param newState El nuevo estado al que transicionar
     */
    public void transitionTo(CharacterState newState) {
        this.currentState = newState;
    }

    /**
     * Cambia al estado de reposo (Idle).
     */
    public void setIdleState() {
        transitionTo(states.getIdleState());
    }

    /**
     * Cambia al estado de ataque (Attacking).
     */
    public void setAttackingState() {
        transitionTo(states.getAttackingState());
    }

    /**
     * Cambia al estado de curación (Heal).
     */
    public void setHealState() {
        transitionTo(states.getHealState());
    }

    /**
     * Cambia al estado de recuperación de maná (GainMana).
     */
    public void setGainManaState() {
        transitionTo(states.getGainManaState());
    }

    /**
     * Cambia al estado de retirada (Retreating).
     */
    public void setRetreatingState() {
        transitionTo(states.getRetreatingState());
    }

    /**
     * Cambia al estado de cansancio (Tired).
     */
    public void setTiredState() {
        transitionTo(states.getTiredState());
    }

    /**
     * Cambia al estado de movimiento en el mapa (MovingOnMap).
     */
    public void setMovingOnMapState() {
        transitionTo(states.getMovingOnMapState());
    }

    /**
     * Cambia al estado de muerte (Dead).
     */
    public void setDeadState() {
        transitionTo(states.getDeadState());
    }

    /**
     * Obtiene el estado actual del personaje.
     * 
     * @return El estado actual
     */
    public CharacterState getCurrentState() {
        return currentState;
    }

    /**
     * Obtiene la lista de estados disponibles para el personaje.
     * 
     * @return La lista de estados
     */
    public StatesList getStates() {
        return states;
    }

    // ------- Getters y setters de atributos simples -------

    /**
     * Obtiene el nombre del personaje.
     * 
     * @return El nombre del personaje
     */
    public String getName() {
        return this.name;
    }

    /**
     * Establece el nombre del personaje.
     * 
     * @param name El nuevo nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Verifica si el último intento de retirada fue exitoso.
     * 
     * @return {@code true} si la retirada fue exitosa, {@code false} en caso contrario
     */
    public Boolean isRetreatSuccessful() {
        return retreatSuccess;
    }

    /**
     * Establece el resultado del último intento de retirada.
     * 
     * @param retreatSuccess {@code true} si la retirada fue exitosa, {@code false} en caso contrario
     */
    public void setRetreatSuccess(Boolean retreatSuccess) {
        this.retreatSuccess = retreatSuccess;
    }

    /**
     * Obtiene el ID único del personaje.
     * 
     * @return El ID del personaje
     */
    public Integer getId() {
        return id;
    }

    /**
     * Obtiene la posición actual del personaje en el mapa.
     * 
     * @return La casilla actual donde se encuentra el personaje
     */
    public GenericTile getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Establece la posición actual del personaje en el mapa.
     * 
     * @param currentPosition La nueva casilla donde posicionar al personaje
     */
    public void setCurrentPosition(GenericTile currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Verifica si el personaje es controlado por IA.
     * 
     * @return {@code true} si es controlado por IA, {@code false} si es controlado por el jugador
     */
    public Boolean getEsControlado() {
        return esControlado;
    }

    // ------- Métodos de combate -------

    /**
     * Realiza un ataque contra un objetivo utilizando una estrategia específica.
     * Delega el comportamiento al estado actual del personaje.
     * 
     * @param target El personaje objetivo del ataque
     * @param strategy La estrategia de ataque a utilizar
     */
    @Override
    public void attack(CombatCharacter target, AttackStrategy strategy) {
        if (target instanceof BaseCharacter targetBaseCharacter) {
            this.currentState.handleAttack(targetBaseCharacter, strategy);
        } else {
            System.err.println("El objetivo no es un personaje válido.");
        }
    }

    /**
     * Intenta retirarse del combate frente a un oponente.
     * Delega el comportamiento al estado actual del personaje.
     * 
     * @param opponent El oponente del cual se intenta huir
     * @return {@code true} si la retirada fue exitosa, {@code false} en caso contrario
     */
    @Override
    public boolean retreat(CombatCharacter opponent) {
        if (opponent instanceof BaseCharacter opponentBaseCharacter) {
            this.currentState.handleRetreat(opponentBaseCharacter);
            return this.retreatSuccess;
        } else {
            System.err.println("El oponente no es un personaje válido.");
            return false;
        }
    }

    /**
     * Utiliza una poción para recuperar salud.
     * Delega el comportamiento al estado actual del personaje.
     */
    @Override
    public void heal() {
        this.currentState.handleHeal();
    }

    /**
     * Mueve el personaje a una casilla específica del mapa.
     * Delega el comportamiento al estado actual del personaje.
     * 
     * @param moveToTile La casilla de destino
     */
    @Override
    public void move(GenericTile moveToTile) {
        currentState.handleMove(moveToTile);
    }

    /**
     * Realiza una acción para recuperar puntos de maná.
     * Delega el comportamiento al estado actual del personaje.
     */
    @Override 
    public void gainMana() {
        this.currentState.handleGainMana();
    }

    /**
     * Equipa un arma al personaje.
     * 
     * @param weapon El arma a equipar
     */
    @Override
    public void equipWeapon(BaseWeapon weapon) {
        this.setWeapon(weapon);
    }

    /**
     * Equipa un casco al personaje.
     * 
     * @param helmet El casco a equipar
     */
    @Override
    public void equipHelmet(BaseHelmet helmet) {
        this.setHelmet(helmet);
    }

    /**
     * Obtiene el nombre del estado actual del personaje.
     * 
     * @return El nombre simple de la clase del estado actual o "No State" si no hay estado
     */
    @Override
    public String getCurrentStateName() {
        if (currentState != null) {
            return currentState.getClass().getSimpleName();
        } else {
            return "No State";
        }
    }

    /**
     * Establece la interfaz de combate para registrar las acciones y eventos.
     * 
     * @param combatInterface La interfaz de combate a utilizar para el registro
     */
    @Override
    public void setFeedLogger(CombatInterface combatInterface) {
        this.combatInterface = combatInterface;
    }   

    /**
     * Obtiene la interfaz de combate utilizada para registrar acciones.
     * 
     * @return La interfaz de combate actual
     */
    public CombatInterface getFeedLogger() {
        return this.combatInterface;
    }

    // ------- Observer pattern management methods -------

    /**
     * Añade un observador para recibir notificaciones sobre eventos del personaje.
     * 
     * @param observer El observador a añadir
     */
    public void addObserver(PushModelObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Elimina un observador para que deje de recibir notificaciones.
     * 
     * @param observer El observador a eliminar
     */
    public void removeObserver(PushModelObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados que el personaje ha muerto.
     * También limpia la casilla que ocupaba el personaje en el mapa.
     */
    @Override
    public void notifyDeathObservers() {
        // Clear the tile's occupied object when character dies
        if (this.currentPosition != null) {
            System.out.println("he eliminado mi tile mostro " + this.getName() + " y estoy " + this.isAlive());
            this.currentPosition.setOcupiedObject(null);
        }
        
        for (PushModelObserver observer : observers) {
            observer.characterHasDied(this);
        }
    }

    /**
     * Establece la ruta de la imagen base del personaje.
     * 
     * @param baseImagePath La nueva ruta de la imagen base
     */
    @Override
    public void setBaseImagePath(String baseImagePath) {
        this.visualizer.setBaseImagePath(baseImagePath);
    }

    /**
     * Obtiene la ruta de la imagen base del personaje.
     * 
     * @return La ruta de la imagen base
     */
    @Override
    public String getBaseImagePath() {
        return this.visualizer.getBaseImagePath();
    }

    /**
     * Obtiene los parámetros de renderizado para mostrar el personaje en el mapa.
     * 
     * @return Los parámetros de renderizado
     */
    @Override
    public RenderParameters getRenderParameters() 
    {
        return new RenderParameters(-10, -25, 1.2, 1.2);
    }
}
