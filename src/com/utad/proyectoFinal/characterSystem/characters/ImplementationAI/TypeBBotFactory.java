package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

public class TypeBBotFactory implements BotFactory {
    @Override
    public Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeBBotAI()); // IA defensiva, por ejemplo
        return bot;
    }
}
