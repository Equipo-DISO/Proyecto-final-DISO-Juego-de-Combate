package characterSystem.characters;


import characterSystem.characters.states.CharacterState;
import characterSystem.characters.states.StatesList;

import java.awt.image.BufferedImage;

public class BaseCharacter {

    // Contador de personajes (usado para asignar un ID único a cada personaje)
    public static Integer contadorPersonajes = 0;

    // Atributos de identificación
    private Integer id;
    private String name;

    // Atributos de estado y estadísticas
    private Integer healtPoints;
    private Double baseAtack;
    private Double baseDefense;
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
    public BaseCharacter(String name, Double baseAtack, Double baseDefense /*, Arma arma, Escudo escudo*/) {
        this.name = name;
        this.baseAtack = baseAtack;
        this.baseDefense = baseDefense;
        this.states = new StatesList(this); // Inicializa el estado del personaje
        this.currentState = states.getIdleState(); // Estado inicial

        // TODO: Implement after creating arma and escudo classes
        // this.arma = arma;
        // this.escudo = escudo;

        this.retreatChance = DefaultAttributes.RETREAT_PROBABILITY;
        this.healtPoints = DefaultAttributes.HEALTH;

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
        return this.healtPoints > 0;
    }

    public String getName() {
        return this.name;
    }

    public Double getRetreatChance() {
        return retreatChance;
    }

    public Boolean getRetreatSuccess() {
        return retreatSuccess;
    }

    public void setRetreatSuccess(Boolean retreatSuccess) {
        this.retreatSuccess = retreatSuccess;
    }

    public void setIdleState() {
        setState(states.getIdleState());
    }

    private void setState(CharacterState state) {
        if (state == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.currentState = state;
    }

    public void setDeadState() {
        setState(states.getDeadState());
    }
}
