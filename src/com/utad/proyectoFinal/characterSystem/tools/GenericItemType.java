package com.utad.proyectoFinal.characterSystem.tools;

public enum GenericItemType {

    HEALTH_POTION("Health potion", 20, 0),
    MANA_POTION("Mana potion", 0, 30),
    UPGRADE_HEALTH_POTION("Upgrade health potion", 40,0),
    UPGRADE_MANA_POTION("Upgrade mana potion", 0, 40);

    private final String name;
    private final int healthRegen;
    private final int manaRegen;

    GenericItemType(String name, int healthRegen, int manaRegen) {
        this.name = name;
        this.healthRegen = healthRegen;
        this.manaRegen = manaRegen;
    }

    public String getName() {
        return name;
    }

    public int getHealthRegen() {
        return healthRegen;
    }

    public int getManaRegen() {
        return manaRegen;
    }
}
