package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Estado que representa a un personaje muerto.
 * <p>
 * En este estado, el personaje no puede realizar ninguna acción.
 * Este es un estado final, una vez que un personaje entra en él,
 * no puede cambiar a otro estado.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class DeadState extends BaseState {
    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    public DeadState(BaseCharacter character) {
        super(character);
    }

    /**
     * Sobreescribe el método updateState para evitar que un personaje muerto
     * cambie a otro estado.
     * <p>
     * Los personajes muertos permanecen en este estado indefinidamente,
     * sin posibilidad de transicionar a otros estados.
     * </p>
     */
    @Override
    public void updateState() {
        // No se puede actualizar el estado de un personaje muerto
        System.out.println("El personaje está muerto y no puede realizar ninguna acción.");
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "Dead"
     */
    @Override
    public String getName() {
        return "Dead";
    }
}
