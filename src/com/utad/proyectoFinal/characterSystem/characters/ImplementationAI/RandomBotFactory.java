package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import java.util.Random;

public class RandomBotFactory {
    
    private static final Random random = new Random();
        
    // Private constructor to prevent instantiation
    private RandomBotFactory() {}

    /**
     * Creates a bot with a randomly selected AI behavior
     * @return a new Bot with random AI type
     */
    public static Bot createBot() {
        // Randomly select one of the three factory types
        int type = random.nextInt(3);
        
        // Return bot based on random type
        switch(type) {
            case 0:
                return TypeABotFactory.createBot(); // Loot-seeking
            case 1: 
                return TypeBBotFactory.createBot(); // Combat-focused
            case 2:
                return TypeCBotFactory.createBot(); // Survival-focused
            default:
                return TypeABotFactory.createBot(); // Default to type A
        }
    }
}