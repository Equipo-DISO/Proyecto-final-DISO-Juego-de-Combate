package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Interfaz que define el contrato para las diferentes estrategias de ataque.
 * <p>
 * Implementa el patrón Strategy, permitiendo diferentes algoritmos de ataque
 * intercambiables según la situación. Cada estrategia de ataque define sus propias
 * características como daño, coste de maná, probabilidad de acierto y desgaste del arma.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public interface AttackStrategy {

    /**
     * Obtiene el nombre identificativo de la estrategia de ataque.
     * <p>
     * Este nombre se utiliza para mensajes de log, interfaz de usuario y depuración.
     * </p>
     * 
     * @return El nombre de la estrategia
     */
    String getName();

    /**
     * Calcula el daño que causará el ataque antes de aplicar la defensa del objetivo.
     * <p>
     * Este método debe considerar los atributos del atacante, el tipo de arma,
     * multiplicadores de daño propios de la estrategia y posibles golpes críticos.
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @return El valor de daño calculado
     */
    double calculateDamage(BaseCharacter attacker);

    /**
     * Calcula el coste de maná requerido para ejecutar el ataque.
     * <p>
     * Cada estrategia de ataque consume una cantidad específica de maná.
     * </p>
     * 
     * @return La cantidad de maná necesaria
     */
    int calculateManaCost();

    /**
     * Determina si el ataque impacta en el objetivo.
     * <p>
     * Calcula la probabilidad de acierto basada en la estrategia de ataque,
     * retornando verdadero si el ataque tiene éxito y falso si falla.
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @return {@code true} si el ataque acierta, {@code false} si falla
     */
    boolean calculateHitSuccess(BaseCharacter attacker);

    /**
     * Método de alto nivel que orquesta la ejecución completa del ataque.
     * <p>
     * Este método coordina todo el proceso del ataque: verificar requisitos,
     * calcular acierto, calcular daño, aplicar daño y gestionar efectos secundarios.
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @param target El personaje objetivo del ataque
     */
    void execute(BaseCharacter attacker, BaseCharacter target);

    /**
     * Calcula el coste de durabilidad que sufre el arma al realizar este ataque.
     * <p>
     * Diferentes estrategias pueden desgastar el arma en mayor o menor medida.
     * </p>
     * 
     * @return La cantidad de durabilidad que se reduce del arma
     */
    int calculateDurabilityCost();
}
