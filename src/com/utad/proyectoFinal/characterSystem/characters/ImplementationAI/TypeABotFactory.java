package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

public class TypeABotFactory {

    // Private constructor to prevent instantiation
    private TypeABotFactory() {}

    /**
     * Creates a bot with TypeA AI (loot-seeking behavior)
     * @return a new Bot with TypeA AI
     */
    public static Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeABotAI());
        return bot;
    }
}
