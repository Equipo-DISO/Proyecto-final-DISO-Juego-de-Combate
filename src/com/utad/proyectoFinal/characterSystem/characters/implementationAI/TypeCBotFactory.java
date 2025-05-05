package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

public class TypeCBotFactory {

    // Private constructor to prevent instantiation
    private TypeCBotFactory() {}

    /**
     * Creates a bot with TypeC AI (survival-focused behavior)
     * @return a new Bot with TypeC AI
     */
    public static Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeCBotAI());
        return bot;
    }
}