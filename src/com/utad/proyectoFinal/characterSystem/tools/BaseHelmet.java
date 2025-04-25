package com.utad.proyectoFinal.characterSystem.tools;

public class BaseHelmet {

    private String name;
    private Double defense;
    private HelmetType type;
    private Integer durability;

    //probabilidad de romperse?!

    //Constructor dependiendo del Tipo de casco
    public BaseHelmet(HelmetType type) {
        this.type = type;
        this.name = type.getName();
        this.defense = type.getDefense();
        this.durability = type.getDurability();
    }

    public String getName() {
        return name;
    }

    public Double getDefense() {
        return defense;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefense(Double defense) {
        this.defense = defense;
    }

    public HelmetType getType() {
        return type;
    }
}
