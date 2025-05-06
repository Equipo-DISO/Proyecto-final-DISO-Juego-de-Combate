package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.ui.combat.Action;

/**
 * Clase abstracta que implementa la plantilla común para todas las estrategias de ataque.
 * <p>
 * Implementa el patrón Template Method a través del método {@code execute} declarado como final,
 * que define un algoritmo con pasos secuenciales para realizar un ataque, delegando
 * los detalles específicos a las subclases mediante métodos abstractos.
 * </p>
 * <p>
 * La estructura del algoritmo de ataque es:
 * <ol>
 *   <li>Verificar que atacante y objetivo estén vivos</li>
 *   <li>Comprobar y consumir el maná requerido</li>
 *   <li>Determinar si el ataque impacta según la probabilidad</li>
 *   <li>Calcular y aplicar el daño si impacta</li>
 *   <li>Gestionar el desgaste del arma</li>
 * </ol>
 * </p>
 *
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractAttackStrategy implements AttackStrategy {

    protected final String name;

    /**
     * Constructor que inicializa la estrategia con un nombre específico.
     * 
     * @param name El nombre identificativo de la estrategia
     */
    protected AbstractAttackStrategy(String name) {
        this.name = name;
    }
    
    /* ------------------------------- Template ------------------------------ */

    /**
     * Implementa el algoritmo completo de ejecución del ataque como un Template Method.
     * <p>
     * Este método es final para garantizar que todas las estrategias sigan la misma
     * estructura, delegando los detalles variables a los métodos abstractos.
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @param target El personaje objetivo del ataque
     */
    @Override
    public final void execute(BaseCharacter attacker, BaseCharacter target) {

        // 1. Comprobar si los personajes están vivos
        if (areCharactersAlive(attacker, target)) {
            return;
        }

        // 2. Comprobar si hay suficiente maná
        int manaCost = calculateManaCost();

        if (attacker.getManaPoints() < manaCost) {
            StringBuilder message = new StringBuilder(String.format("%s intenta %s pero no tiene suficiente maná (%d requerido).%n",
                    attacker.getName(), name, manaCost));
            System.out.printf(message.toString());
            if (attacker.getFeedLogger() != null) {
                attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
            return;
        }

        // 3. Consumir maná
        attacker.decreaseManaPoints(manaCost);

        // 4. Comprobar si impacta
        if (!calculateHitSuccess(attacker)) {
            // 5b. Avisar del fallo
            applyMiss(attacker, target);
            return;
        }

        // 5. Calcular daño y aplicarlo
        double damage = calculateDamage(attacker);
        applyHit(attacker, target, damage);
    }

    private boolean areCharactersAlive(BaseCharacter attacker, BaseCharacter target) {
        return !attacker.isAlive() || !target.isAlive();
    }

    /* -------------------------- Métodos utilitarios ------------------------ */

    /**
     * Maneja la visualización y registros de un golpe crítico.
     * <p>
     * Muestra un mensaje especial cuando se produce un golpe crítico, tanto en la
     * consola como en la interfaz de combate.
     * </p>
     * 
     * @param attacker El personaje que ha realizado el golpe crítico
     */
    protected void handleCriticalHitMessage(BaseCharacter attacker) {
        StringBuilder message = new StringBuilder(String.format("¡%s asesta un golpe CRÍTICO con su %s!%n", 
                attacker.getName(), attacker.getWeapon().getName()));
        System.out.printf(message.toString());
        if (attacker.getFeedLogger() != null) {
            attacker.getFeedLogger().addFeedLine(message.toString(), Action.ATACK);
        }
    }

    /**
     * Aplica el daño al objetivo y gestiona los efectos secundarios de un ataque exitoso.
     * <p>
     * Este método reduce la salud del objetivo según el daño calculado, registra el ataque
     * en los logs, y gestiona el desgaste del arma. También verifica si el arma se rompe.
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @param target El personaje que recibe el ataque
     * @param damage La cantidad de daño a aplicar
     */
    protected void applyHit(BaseCharacter attacker, BaseCharacter target, double damage) {
        target.getCurrentState().handleReceiveAttack(damage);
        StringBuilder message = new StringBuilder(String.format("%s ejecuta %s y causa %.0f de daño a %s.%n",
                attacker.getName(), name, damage, target.getName()));
        System.out.printf(message.toString());
        if (attacker.getFeedLogger() != null) {
            attacker.getFeedLogger().addFeedLine(message.toString(), Action.ATACK);
        }

        // Ataque con éxito -> reducir durabilidad del arma
        if (attacker.getWeapon() != null) {
            attacker.getWeapon().decreaseDurability(calculateDurabilityCost());
            handleWeaponBreak(attacker);
        }
    }

    /**
     * Verifica si el arma del atacante se ha roto y muestra los mensajes correspondientes.
     * <p>
     * Si la durabilidad del arma llega a cero o menos, se notifica que está rota y
     * se elimina del inventario del personaje.
     * </p>
     * 
     * @param attacker El personaje cuya arma podría haberse roto
     */
    private void handleWeaponBreak(BaseCharacter attacker) {
        if (attacker.getWeapon() != null && attacker.getWeapon().getDurability() <= 0) {
            StringBuilder message = new StringBuilder(String.format("%s ha roto su arma.%n", attacker.getName()));
            System.out.printf(message.toString());
            if (attacker.getFeedLogger() != null) {
                attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
            attacker.setWeapon(null);
        }
    }

    /**
     * Maneja los efectos y notificaciones de un ataque fallido.
     * <p>
     * Muestra un mensaje indicando que el ataque ha fallado, tanto en la consola
     * como en la interfaz de combate.
     * </p>
     * 
     * @param attacker El personaje que intentó atacar
     * @param target El personaje objetivo que evitó el ataque
     */
    protected void applyMiss(BaseCharacter attacker, BaseCharacter target) {
        StringBuilder message = new StringBuilder(String.format("%s falla su %s contra %s.%n",
                attacker.getName(), name, target.getName()));
        System.out.printf(message.toString());
        if (attacker.getFeedLogger() != null) {
            attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
        }
    }

    /**
     * Obtiene el nombre identificativo de la estrategia de ataque.
     * 
     * @return El nombre de la estrategia
     */
    @Override
    public String getName() {
        return name;
    }
}
