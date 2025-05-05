package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

public class TypeBBotFactory {

    // Private constructor to prevent instantiation
    private TypeBBotFactory() {}

    /** 
     * Creates a bot with TypeB AI (combat-focused behavior)
     * @return a new Bot with TypeB AI
     */
    public static Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeBBotAI());
        return bot;
    }
}
