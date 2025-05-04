package com.utad.proyectoFinal.characterSystem.characters;

public class CharacterAttributes {
    private Integer healthPoints;
    private Integer maxHealthPoints;
    private Integer manaPoints;
    private Integer maxManaPoints;
    private Double baseAttack;
    private Integer hpPotions;

    public CharacterAttributes(Integer maxHealthPoints, Integer maxManaPoints, Double baseAttack) {
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.maxManaPoints = maxManaPoints;
        this.manaPoints = maxManaPoints;
        this.baseAttack = baseAttack;
        this.hpPotions = 0;
    }

    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    public void reduceHealth(Integer damage) {
        this.healthPoints = Math.max(0, this.healthPoints - damage);
    }

    public void gainHealth(Integer healthPoints) {
        this.healthPoints = Math.min(this.healthPoints + healthPoints, this.maxHealthPoints);
    }

    public void decreaseManaPoints(int amount) {
        this.manaPoints = Math.max(0, manaPoints - amount);
    }

    public void increaseManaPoints(Integer manaRecovered) {
        this.manaPoints = Math.min(this.manaPoints + manaRecovered, this.maxManaPoints);
    }

    public boolean isLowEnergy() {
        return manaPoints < DefaultAttributes.LOW_MANA_THRESHOLD;
    }

    public void addHealthPotion() {
        this.hpPotions++;
    }

    public void useHpPotion() {
        if (this.hpPotions > 0) {
            this.hpPotions--;
        }
    }

    public void addManaUpgrade() {
        this.manaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
        this.maxManaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
    }

    public void addHealthUpgrade() {
        this.healthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
        this.maxHealthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
    }

    // Getters y setters
    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Integer getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public Integer getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(Integer manaPoints) {
        this.manaPoints = manaPoints;
    }

    public Integer getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setMaxManaPoints(Integer maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public Double getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(Double baseAttack) {
        this.baseAttack = baseAttack;
    }

    public Integer getHpPotions() {
        return hpPotions;
    }

    public void setHpPotions(Integer hpPotions) {
        this.hpPotions = hpPotions;
    }
} 