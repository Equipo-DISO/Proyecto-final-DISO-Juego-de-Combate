package com.utad.proyectoFinal.characterSystem.tools.items;

public enum WeaponType {
    // Define weapon types with their attributes
    STICK("Stick", 2.0, 0.05, 1.05, 2, "Files/img/StickWeapon.png"),
    KNIFE("Stick", 5.0, 0.10, 1.10, 3, "Files/img/KnifeWeapon.png"),
    SWORD("Sword", 10.0, 0.20, 1.20, 4, "Files/img/SwordWeapon.png"),
    AXE("Axe", 15.0, 0.25, 1.30, 5, "Files/img/AxeWeapon.png"),
    SPEAR("Spear", 15.0, 0.15, 1.30, 6, "Files/img/SpearWeapon.png"),;

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

}