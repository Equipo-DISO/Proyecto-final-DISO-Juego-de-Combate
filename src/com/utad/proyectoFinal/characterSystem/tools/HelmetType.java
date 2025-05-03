package com.utad.proyectoFinal.characterSystem.tools;

public enum HelmetType {

    SIMPLE_HELMET("Simple Helmet", 4.0, 3, "/Files/img/MedievalHelmet.png"),
    NORMAL_HELMET("Normal Helmet", 9.0, 5, "/Files/img/MilitarHelmet.png"),
    DEMON_HELMET("Demon Helmet", 15.0, 7, "/Files/img/DemonHelmet.png");

    private final String name;
    private final Double defense;
    private final Integer durability;
    private final String imagePath;

    private HelmetType(String name, Double defense, Integer durability, String imagePath) {
        this.name = name;
        this.defense = defense;
        this.durability = durability;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }


}
