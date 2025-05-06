package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.ui.combat.Action;

/**
 * Estado que representa cuando el personaje está recuperando maná (concentrándose).
 * <p>
 * En este estado, el personaje dedica su turno a recuperar puntos de maná para
 * poder realizar acciones que requieren energía en turnos posteriores. Durante este
 * estado, el personaje no puede realizar otras acciones como atacar o moverse.
 * </p>
 * <p>
 * Tras completar la acción, el personaje vuelve automáticamente al estado Idle.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class GainManaState extends BaseState {

    // Cantidad de maná que se recupera
    private static final int MANA_GAIN_AMOUNT = 15;

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    GainManaState(BaseCharacter character) {
        super(character);
    }

    /**
     * Sobreescribe handleAttack para evitar que un personaje recuperando maná pueda atacar.
     * <p>
     * Muestra un mensaje indicando que la acción no está permitida.
     * </p>
     * 
     * @param opponent El oponente al que se intentaría atacar
     * @param attackStrategy La estrategia de ataque que se intentaría usar
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        System.out.printf("%s está recuperando energía y no puede atacar en este momento.%n",
                character.getName());
    }

    /**
     * Sobreescribe handleRetreat para evitar que un personaje recuperando maná pueda retirarse.
     * <p>
     * Muestra un mensaje indicando que la acción no está permitida.
     * </p>
     * 
     * @param opponent El oponente del que se intentaría huir
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s está recuperando energía y no puede retirarse en este momento.%n",
                character.getName());
    }

    /**
     * Ejecuta la acción de recuperar maná.
     * <p>
     * Incrementa los puntos de maná del personaje en una cantidad fija (15 puntos),
     * sin exceder el máximo permitido. Registra la acción en el feed y actualiza el
     * estado del personaje.
     * </p>
     */
    @Override
    public void handleGainMana() {
        // Calcular nuevo maná (sin exceder el máximo)
        int currentMana = character.getManaPoints();
        int newMana = Math.min(currentMana + MANA_GAIN_AMOUNT, character.getMaxManaPoints());
        character.setManaPoints(newMana);

        StringBuilder message = new StringBuilder(String.format("%s ha recuperado %d puntos de energía.%n",
                character.getName(), newMana - currentMana));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.CONCENTRATE);
        }

        // Actualizar estado
        updateState();
    }

    /**
     * Actualiza el estado del personaje después de recuperar maná.
     * <p>
     * Si no está en modo de pruebas, transiciona al estado Idle.
     * </p>
     */
    @Override
    public void updateState() {
        // In testing mode, don't transition back to Idle automatically
        if (isTestingMode()) {
            return;
        }

        // Volver a Idle después de ganar maná
        character.transitionTo(character.getStates().getIdleState());
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "GainMana"
     */
    @Override
    public String getName() {
        return "GainMana";
    }
}
