package com.utad.proyectoFinal.characterSystem.tools;

public enum HelmetType {

    SIMPLE_HELMET("Simple Helmet", 4.0, 3),
    NORMAL_HELMET("Normal Helmet", 9.0, 5),
    DEMON_HELMET("Demon Helmet", 15.0, 7);

    private final String name;
    private final Double defense;
    private final Integer durability;

    private HelmetType(String name, Double defense, Integer durability) {
        this.name = name;
        this.defense = defense;
        this.durability = durability;
    }

    public String getName() {
        return name;
    }

    public Double getDefense() {
        return defense;
    }

    public Integer getDurability() {
        return durability;
    }

}
