package com.utad.proyectoFinal.characterSystem.tools;

public enum WeaponType {
    // Define weapon types with their attributes
    TISSUE("Tissue", 2.0, 0.05, 1.05, 2, Constants.FILES_IMG_WEAPON_PLACEHOLDER_PNG),
    STICK("Stick", 5.0, 0.10, 1.10, 3, Constants.FILES_IMG_WEAPON_PLACEHOLDER_PNG),
    DAGGER("Dagger", 10.0, 0.20, 1.20, 4, Constants.FILES_IMG_WEAPON_PLACEHOLDER_PNG),
    SWORD("Sword", 20.0, 0.10, 1.50,5, Constants.FILES_IMG_WEAPON_PLACEHOLDER_PNG),
    SPEAR("Spear", 15.0, 0.15, 1.30, 6, Constants.FILES_IMG_WEAPON_PLACEHOLDER_PNG);

    private final String name;
    private final Double damage;
    private final Double criticalChance;
    private final Double criticalDamage;
    private final Integer durability;
    private final String imagePath;

    WeaponType(String name, Double damage, Double criticalChance, Double criticalDamage, Integer durability, String imagePath) {
        this.name = name;
        this.damage = damage;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.durability = durability;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    private static class Constants {
        public static final String FILES_IMG_WEAPON_PLACEHOLDER_PNG = "/Files/img/Weapon-placeholder.png";
    }
}