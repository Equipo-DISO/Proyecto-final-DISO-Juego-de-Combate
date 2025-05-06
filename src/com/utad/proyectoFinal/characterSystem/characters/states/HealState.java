package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.ui.combat.Action;

/**
 * Estado que representa a un personaje que está utilizando una poción de curación.
 * <p>
 * En este estado, el personaje dedica su turno a usar una poción para recuperar salud.
 * Durante este estado, el personaje no puede realizar otras acciones como atacar o moverse.
 * </p>
 * <p>
 * Tras completar la acción, el personaje vuelve automáticamente al estado Idle.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class HealState extends BaseState {

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    HealState(BaseCharacter character) {
        super(character);
    }

    /**
     * Sobreescribe handleAttack para evitar que un personaje curándose pueda atacar.
     * <p>
     * Muestra un mensaje indicando que la acción no está permitida.
     * </p>
     * 
     * @param opponent El oponente al que se intentaría atacar
     * @param attackStrategy La estrategia de ataque que se intentaría usar
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        StringBuilder message = new StringBuilder(String.format("%s está curándose y no puede atacar en este momento.%n",
                character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
        }
    }

    /**
     * Sobreescribe handleRetreat para evitar que un personaje curándose pueda retirarse.
     * <p>
     * Muestra un mensaje indicando que la acción no está permitida.
     * </p>
     * 
     * @param opponent El oponente del que se intentaría huir
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        StringBuilder message = new StringBuilder(String.format("%s está curándose y no puede retirarse en este momento.%n",
                character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
        }
    }

    /**
     * Ejecuta la acción de curación.
     * <p>
     * Verifica si el personaje tiene pociones de salud disponibles. Si las tiene, 
     * consume una poción y recupera una cantidad fija de salud (50 puntos),
     * sin exceder el máximo permitido. Registra la acción en el feed y actualiza el
     * estado del personaje.
     * </p>
     */
    @Override
    public void handleHeal() {
        // Comprobar si tiene suficiente maná para curarse
        if (character.getHpPotions() <= 0) {
            StringBuilder message = new StringBuilder(String.format("%s intenta curarse pero no tiene pociones de salud.%n",
                    character.getName()));
            System.out.printf(message.toString());
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
        } else {
            // Consumir maná
            character.useHpPotion();

            Integer currentHealth = character.getHealthPoints();

            // Calcular nueva salud (sin exceder el máximo)
            character.gainHealth(currentHealth + DefaultAttributes.POTION_HEAL_AMOUNT);

            StringBuilder message = new StringBuilder(String.format("%s se ha curado %d puntos de salud.%n",
                    character.getName(), character.getHealthPoints() - currentHealth));
            System.out.printf(message.toString());
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
            }
        }
        // Actualizar estado
        updateState();
    }

    /**
     * Actualiza el estado del personaje después de curarse.
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

        // Volver a Idle después de curarse
        character.transitionTo(character.getStates().getIdleState());
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "Heal"
     */
    @Override
    public String getName() {
        return "Heal";
    }
}
