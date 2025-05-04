package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;

public interface Consumible {
    public void restoreHealth(CombatCharacter combatCharacter);
    public void restoreUpgradeHealth(CombatCharacter combatCharacter);
    public void restoreUpgradeMana(CombatCharacter combatCharacter);
}
