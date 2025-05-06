package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;

/**
 * Estado que representa a un personaje en proceso de ataque.
 * <p>
 * Esta clase implementa el comportamiento específico cuando un personaje está realizando un ataque.
 * Utiliza el patrón Strategy para delegar la implementación del ataque a una estrategia concreta,
 * permitiendo diferentes tipos de ataques (ligeros, pesados, etc.) con el mismo código base.
 * </p>
 * <p>
 * Después de realizar un ataque, el personaje puede transicionar al estado Tired si queda con
 * poco maná, o volver al estado Idle si todo va bien.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class AttackingState extends BaseState {

    private AttackStrategy currentStrategy;

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    AttackingState(BaseCharacter character) {
        super(character);
    }

    /**
     * Maneja el ataque con una estrategia específica.
     * <p>
     * Este método almacena la estrategia proporcionada y ejecuta el ataque contra el oponente
     * siempre que ambos personajes estén vivos y no sean el mismo.
     * </p>
     * 
     * @param opponent El objetivo del ataque
     * @param attackStrategy La estrategia a utilizar para este ataque
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        currentStrategy = attackStrategy;

        if (character.isAlive() && opponent.isAlive())
        {
            executeAttack(opponent);
        }
    }
    
    /**
     * Ejecuta la estrategia de ataque actual contra el oponente.
     * <p>
     * Verifica que exista una estrategia configurada y que el atacante no esté
     * intentando atacarse a sí mismo. Luego delega la ejecución del ataque a la
     * estrategia y actualiza el estado según corresponda.
     * </p>
     * 
     * @param opponent El objetivo del ataque
     */
    private void executeAttack(BaseCharacter opponent) {
        if (currentStrategy != null && !character.equals(opponent)) {
            currentStrategy.execute(character, opponent);
            updateState();
        }
    }

    /**
     * Actualiza el estado del personaje después de realizar un ataque.
     * <p>
     * Comprueba si el personaje tiene poco maná para cambiar al estado Tired.
     * Si no está cansado y no está en modo de pruebas, vuelve al estado Idle.
     * </p>
     */
    @Override
    public void updateState() {
        // Comprobar si el personaje tiene poco maná después del ataque
        if (!checkAndTransitionToTiredIfNeeded()) {
            // Use the common testing mode flag from BaseState
            // In testing mode, don't transition back to Idle automatically
            // This allows tests to check the state after an attack
            if (!isTestingMode()) {
                character.transitionTo(character.getStates().getIdleState()); // volver a Idle si tiene suficiente maná
            }
        }
    }
    
    /**
     * Obtiene la estrategia de ataque actual.
     * <p>
     * Útil para pruebas y para acceder a la configuración del ataque.
     * </p>
     * 
     * @return La estrategia de ataque actual
     */
    public AttackStrategy getCurrentStrategy() {
        return currentStrategy;
    }
    
    /**
     * Establece una estrategia de ataque específica.
     * <p>
     * Permite configurar manualmente la estrategia que se utilizará
     * para el próximo ataque sin tener que invocar handleAttack.
     * </p>
     * 
     * @param strategy La estrategia de ataque a establecer
     */
    public void setStrategy(AttackStrategy strategy) {
        this.currentStrategy = strategy;
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "Attacking"
     */
    @Override
    public String getName() {
        return "Attacking";
    }
}
