package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

/**
 * Bot factory utility class providing static access to all bot creation methods
 */
public class BotFactory {
    // Private constructor to prevent instantiation
    private BotFactory() {}
    
    /**
     * Creates a bot with TypeA AI (loot-seeking behavior)
     * @return a new Bot with TypeA AI
     */
    public static Bot createTypeABot() {
        return TypeABotFactory.createBot();
    }
    
    /**
     * Creates a bot with TypeB AI (combat-focused behavior)
     * @return a new Bot with TypeB AI
     */
    public static Bot createTypeBBot() {
        return TypeBBotFactory.createBot();
    }
    
    /**
     * Creates a bot with TypeC AI (survival-focused behavior)
     * @return a new Bot with TypeC AI
     */
    public static Bot createTypeCBot() {
        return TypeCBotFactory.createBot();
    }
    
    /**
     * Creates a bot with randomly selected AI behavior
     * @return a new Bot with random AI type
     */
    public static Bot createRandomBot() {
        return RandomBotFactory.createBot();
    }
}
