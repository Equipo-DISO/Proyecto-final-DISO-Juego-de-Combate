package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;

public class CharacterEquipment {
    private BaseWeapon weapon;
    private BaseHelmet helmet;

    public CharacterEquipment() {
        this.weapon = null;
        this.helmet = null;
    }

    public void equipWeapon(BaseWeapon weapon) {
        this.weapon = weapon;
    }

    public void equipHelmet(BaseHelmet helmet) {
        this.helmet = helmet;
    }

    public BaseWeapon getWeapon() {
        return weapon;
    }

    public BaseHelmet getHelmet() {
        return helmet;
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    public boolean hasHelmet() {
        return helmet != null;
    }
} 