package com.utad.proyectoFinal.characterSystem.tools;

public enum GenericItemType {

    HEALTH_POTION(20, 0),
    MANA_POTION(0, 30),
    UPGRADE_HEALTH_POTION(40,0),
    UPGRADE_MANA_POTION(0, 40);

    private final int healthRegen;
    private final int manaRegen;

    GenericItemType(int healthRegen, int manaRegen) {
        this.healthRegen = healthRegen;
        this.manaRegen = manaRegen;
    }

    public int getHealthRegen() {
        return healthRegen;
    }

    public int getManaRegen() {
        return manaRegen;
    }
}
