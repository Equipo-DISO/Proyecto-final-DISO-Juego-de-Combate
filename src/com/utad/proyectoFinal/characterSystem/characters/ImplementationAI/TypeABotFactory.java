package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

public class TypeABotFactory implements BotFactory {
    @Override
    public Bot createBot(){
        Bot bot = new Bot();
        bot.setBotAI(new TypeABotAI());
        return bot;
    }

}
