package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;

/**
 * Clase que encapsula el equipamiento de un personaje.
 * <p>
 * Esta clase gestiona el arma y el casco que un personaje puede equipar,
 * proporcionando métodos para equipar, verificar y acceder a estos elementos.
 * Forma parte del sistema de composición de personajes, separando la
 * gestión del equipamiento del resto de las responsabilidades del personaje.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class CharacterEquipment {
    private BaseWeapon weapon;
    private BaseHelmet helmet;

    /**
     * Constructor que inicializa el equipamiento del personaje.
     * Por defecto, el personaje no tiene equipado ni arma ni casco.
     */
    public CharacterEquipment() {
        this.weapon = null;
        this.helmet = null;
    }

    /**
     * Equipa un arma al personaje, reemplazando cualquier arma equipada anteriormente.
     * 
     * @param weapon El arma a equipar
     */
    public void equipWeapon(BaseWeapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Equipa un casco al personaje, reemplazando cualquier casco equipado anteriormente.
     * 
     * @param helmet El casco a equipar
     */
    public void equipHelmet(BaseHelmet helmet) {
        this.helmet = helmet;
    }

    /**
     * Obtiene el arma equipada actualmente por el personaje.
     * 
     * @return El arma actual, o {@code null} si no tiene ninguna equipada
     */
    public BaseWeapon getWeapon() {
        return weapon;
    }

    /**
     * Obtiene el casco equipado actualmente por el personaje.
     * 
     * @return El casco actual, o {@code null} si no tiene ninguno equipado
     */
    public BaseHelmet getHelmet() {
        return helmet;
    }

    /**
     * Verifica si el personaje tiene un arma equipada.
     * 
     * @return {@code true} si el personaje tiene un arma equipada, {@code false} en caso contrario
     */
    public boolean hasWeapon() {
        return weapon != null;
    }

    /**
     * Verifica si el personaje tiene un casco equipado.
     * 
     * @return {@code true} si el personaje tiene un casco equipado, {@code false} en caso contrario
     */
    public boolean hasHelmet() {
        return helmet != null;
    }
} 