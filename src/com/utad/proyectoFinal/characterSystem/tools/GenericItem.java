package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.mapa.MapObject;

import java.awt.*;

public class GenericItem implements MapObject, Consumible {

    private GenericItemType type;

    public GenericItem(GenericItemType type) {
        this.type = type; //para poder instanciar desde la random factory
    }
    public GenericItemType getType() {
        return type;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public void restoreUpgradeMana(CombatCharacter combatCharacter) {
        combatCharacter.addManaUpgrade();
    }

    @Override
    public void restoreHealth(CombatCharacter combatCharacter) {
        combatCharacter.addHealthPotion();
    }

    @Override
    public void restoreUpgradeHealth(CombatCharacter combatCharacter) {
        combatCharacter.addHealthUpgrade();
    }

}
