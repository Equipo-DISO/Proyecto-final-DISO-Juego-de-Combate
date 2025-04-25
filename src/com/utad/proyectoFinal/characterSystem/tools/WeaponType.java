package com.utad.proyectoFinal.characterSystem.tools;

public enum WeaponType {
    // Define weapon types with their attributes
    TISSUE("Tissue", 2.0, 0.05, 1.05, 2),
    STICK("Stick", 5.0, 0.10, 1.10, 3),
    DAGGER("Dagger", 10.0, 0.20, 1.20, 4),
    SWORD("Sword", 20.0, 0.10, 1.50,5),
    SPEAR("Spear", 15.0, 0.15, 1.30, 6);

    private final String name;
    private final Double damage;
    private final Double criticalChance;
    private final Double criticalDamage;
    private final Integer durability;

    WeaponType(String name, Double damage, Double criticalChance, Double criticalDamage, Integer durability) {
        this.name = name;
        this.damage = damage;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.durability = durability;
    }

    public String getName() {
        return name;
    }

    public Double getDamage() {
        return damage;
    }

    public Double getCriticalChance() {
        return criticalChance;
    }

    public Double getCriticalDamage() {
        return criticalDamage;
    }

    public Integer getDurability() {
        return durability;
    }
}