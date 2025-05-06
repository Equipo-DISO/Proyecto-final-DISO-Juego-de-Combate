package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.Calculator;
import com.utad.proyectoFinal.ui.combat.Action;

/**
 * Estado que representa a un personaje intentando retirarse del combate.
 * <p>
 * En este estado, el personaje intenta huir del combate con una probabilidad de éxito
 * que depende de la relación entre el maná del personaje y el del oponente. La fórmula
 * aproximada es: P(retirada) = (MPpersonaje / MPoponente) / 2 * 100.
 * </p>
 * <p>
 * Si la retirada tiene éxito, el personaje podrá evitar el siguiente ataque del oponente.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
// P(ret) = (MPr_p / MPr_e) / 2 * 100
public class RetreatingState extends BaseState {

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    RetreatingState(BaseCharacter character) {
        super(character);
    }

    /**
     * Ejecuta la acción de intento de retirada del combate.
     * <p>
     * Calcula la probabilidad de retirada exitosa basada en las características
     * del personaje y su oponente, y determina aleatoriamente si el intento tiene éxito.
     * Registra el resultado en los logs y actualiza el estado del personaje.
     * </p>
     * 
     * @param opponent El oponente del cual se intenta huir
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        boolean retirada = false;

        StringBuilder message = new StringBuilder(String.format("%s intenta retirarse.%n", character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.RUN);
        }

        // Usar Calculator para calcular la probabilidad de retirada
        double probabilidadRetirada = Calculator.getInstance().calculateRetreatProbability(character, opponent);
        if (Math.random() * 100 <= probabilidadRetirada) {
            retirada = true;
            message = new StringBuilder(String.format("%s ha conseguido retirarse con éxito.%n", character.getName())); 
            
        } else {
            message = new StringBuilder(String.format("%s ha intentado retirarse pero ha fallado.%n", character.getName()));
        }

        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.RUN);
        }

        character.setRetreatSuccess(retirada);

        // Actualizar estado
        updateState();
    }

    /**
     * Actualiza el estado del personaje después de intentar retirarse.
     * <p>
     * Si no está en modo de pruebas, transiciona al estado Idle.
     * </p>
     */
    @Override
    public void updateState() {
        // In testing mode, don't transition automatically
        if (isTestingMode()) {
            return;
        }

        
        character.transitionTo(character.getStates().getIdleState());
        
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "Retreating"
     */
    @Override
    public String getName() {
        return "Retreating";
    }
}
