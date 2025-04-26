package com.utad.proyectoFinal.characterSystem.tools;

public class BaseWeapon {

    private String name;
    private Double damage;
    private Double criticalChance; // probabilidad de asestar un golpe crítico -> %
    private Double criticalDamage; // daño adicional al asestar un golpe crítico -> %
    private Integer durability;
    private WeaponType type;

    public BaseWeapon(WeaponType type) {
        this.type = type;
        this.name = type.getName();
        this.damage = type.getDamage();
        this.criticalChance = type.getCriticalChance();
        this.criticalDamage = type.getCriticalDamage();
        this.durability = type.getDurability();
    }

    public String getName() {
        return name;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double auxDamage){
        this.damage = auxDamage; // por si asestan golpes críticos
    }

    public Double getCriticalChance() {
        return criticalChance;
    }

    public Double getCriticalDamage() {
        return criticalDamage;
    }

    public Double calculateCriticalDamage(){ //cómputo del crítico + daño base
        return this.getDamage() * this.getCriticalDamage();
    }

    public WeaponType getType() {
        return type;
    }

    public Integer getDurability() {
        return durability;
    }

    public void decreaseDurability(){
        this.durability--;
    }

    public void decreaseDurability(int i) {
        this.durability -= i;
    }
}
