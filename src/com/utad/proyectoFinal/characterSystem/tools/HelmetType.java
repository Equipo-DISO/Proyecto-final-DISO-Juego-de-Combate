package com.utad.proyectoFinal.characterSystem.tools;

public enum HelmetType {

    SIMPLE_HELMET("Simple Helmet", 4.0, 3, Constants.FILES_IMG_HELMET_PLACEHOLDER_PNG),
    NORMAL_HELMET("Normal Helmet", 9.0, 5, Constants.FILES_IMG_HELMET_PLACEHOLDER_PNG),
    DEMON_HELMET("Demon Helmet", 15.0, 7, Constants.FILES_IMG_HELMET_PLACEHOLDER_PNG);

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

    private static class Constants {
        public static final String FILES_IMG_HELMET_PLACEHOLDER_PNG = "/Files/img/Helmet-placeholder.png";
    }
}
