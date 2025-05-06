package com.utad.proyectoFinal.characterSystem.characters;

/**
 * Clase que encapsula los atributos de un personaje.
 * <p>
 * Esta clase gestiona todos los atributos básicos de los personajes como salud, maná,
 * ataque base y pociones de salud. Proporciona métodos para modificar estos atributos
 * y realizar comprobaciones de estado como verificar si el personaje está vivo o si
 * tiene poca energía.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class CharacterAttributes {
    private Integer healthPoints;
    private Integer maxHealthPoints;
    private Integer manaPoints;
    private Integer maxManaPoints;
    private Double baseAttack;
    private Integer hpPotions;

    /**
     * Constructor que inicializa los atributos del personaje.
     * 
     * @param maxHealthPoints Puntos de salud máximos iniciales
     * @param maxManaPoints Puntos de maná máximos iniciales
     * @param baseAttack Valor de ataque base
     */
    public CharacterAttributes(Integer maxHealthPoints, Integer maxManaPoints, Double baseAttack) {
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.maxManaPoints = maxManaPoints;
        this.manaPoints = maxManaPoints;
        this.baseAttack = baseAttack;
        this.hpPotions = 0;
    }

    /**
     * Verifica si el personaje está vivo.
     * 
     * @return {@code true} si el personaje tiene más de 0 puntos de salud, {@code false} en caso contrario
     */
    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    /**
     * Reduce los puntos de salud del personaje por una cantidad de daño.
     * Los puntos de salud nunca serán inferiores a 0.
     * 
     * @param damage Cantidad de daño a aplicar
     */
    public void reduceHealth(Integer damage) {
        this.healthPoints = Math.max(0, this.healthPoints - damage);
    }

    /**
     * Incrementa los puntos de salud del personaje.
     * Los puntos de salud nunca superarán el máximo establecido.
     * 
     * @param healthPoints Cantidad de salud a recuperar
     */
    public void gainHealth(Integer healthPoints) {
        this.healthPoints = Math.min(this.healthPoints + healthPoints, this.maxHealthPoints);
    }

    /**
     * Reduce los puntos de maná del personaje.
     * Los puntos de maná nunca serán inferiores a 0.
     * 
     * @param amount Cantidad de maná a reducir
     */
    public void decreaseManaPoints(int amount) {
        this.manaPoints = Math.max(0, manaPoints - amount);
    }

    /**
     * Incrementa los puntos de maná del personaje.
     * Los puntos de maná nunca superarán el máximo establecido.
     * 
     * @param manaRecovered Cantidad de maná a recuperar
     */
    public void increaseManaPoints(Integer manaRecovered) {
        this.manaPoints = Math.min(this.manaPoints + manaRecovered, this.maxManaPoints);
    }

    /**
     * Verifica si el personaje tiene poca energía (maná).
     * 
     * @return {@code true} si el maná está por debajo del umbral de maná bajo, {@code false} en caso contrario
     */
    public boolean isLowEnergy() {
        return manaPoints < DefaultAttributes.LOW_MANA_THRESHOLD;
    }

    /**
     * Añade una poción de salud al inventario del personaje.
     */
    public void addHealthPotion() {
        this.hpPotions++;
    }

    /**
     * Utiliza una poción de salud si el personaje tiene al menos una disponible.
     * Reduce el contador de pociones en 1.
     */
    public void useHpPotion() {
        if (this.hpPotions > 0) {
            this.hpPotions--;
        }
    }

    /**
     * Aplica una mejora permanente a los puntos de maná máximos del personaje.
     * También incrementa los puntos de maná actuales en la misma cantidad.
     */
    public void addManaUpgrade() {
        this.manaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
        this.maxManaPoints += DefaultAttributes.UPGRADE_MANA_AMOUNT;
    }

    /**
     * Aplica una mejora permanente a los puntos de salud máximos del personaje.
     * También incrementa los puntos de salud actuales en la misma cantidad.
     */
    public void addHealthUpgrade() {
        this.healthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
        this.maxHealthPoints += DefaultAttributes.UPGRADE_HEALTH_AMOUNT;
    }

    // Getters y setters
    /**
     * Obtiene los puntos de salud actuales del personaje.
     * 
     * @return Los puntos de salud actuales
     */
    public Integer getHealthPoints() {
        return healthPoints;
    }

    /**
     * Establece los puntos de salud actuales del personaje.
     * 
     * @param healthPoints Nuevos puntos de salud
     */
    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Obtiene los puntos de salud máximos del personaje.
     * 
     * @return Los puntos de salud máximos
     */
    public Integer getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * Establece los puntos de salud máximos del personaje.
     * 
     * @param maxHealthPoints Nuevos puntos de salud máximos
     */
    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    /**
     * Obtiene los puntos de maná actuales del personaje.
     * 
     * @return Los puntos de maná actuales
     */
    public Integer getManaPoints() {
        return manaPoints;
    }

    /**
     * Establece los puntos de maná actuales del personaje.
     * 
     * @param manaPoints Nuevos puntos de maná
     */
    public void setManaPoints(Integer manaPoints) {
        this.manaPoints = manaPoints;
    }

    /**
     * Obtiene los puntos de maná máximos del personaje.
     * 
     * @return Los puntos de maná máximos
     */
    public Integer getMaxManaPoints() {
        return maxManaPoints;
    }

    /**
     * Establece los puntos de maná máximos del personaje.
     * 
     * @param maxManaPoints Nuevos puntos de maná máximos
     */
    public void setMaxManaPoints(Integer maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    /**
     * Obtiene el valor de ataque base del personaje.
     * 
     * @return El valor de ataque base
     */
    public Double getBaseAttack() {
        return baseAttack;
    }

    /**
     * Establece el valor de ataque base del personaje.
     * 
     * @param baseAttack Nuevo valor de ataque base
     */
    public void setBaseAttack(Double baseAttack) {
        this.baseAttack = baseAttack;
    }

    /**
     * Obtiene el número de pociones de salud disponibles.
     * 
     * @return El número de pociones de salud
     */
    public Integer getHpPotions() {
        return hpPotions;
    }

    /**
     * Establece el número de pociones de salud disponibles.
     * 
     * @param hpPotions Nuevo número de pociones de salud
     */
    public void setHpPotions(Integer hpPotions) {
        this.hpPotions = hpPotions;
    }
}