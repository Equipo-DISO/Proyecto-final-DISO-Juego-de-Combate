package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

public class TypeABotFactory implements BotFactory {
    @Override
    public Bot createBot(){
        Bot bot = new Bot();
        bot.setBotAI(new TypeABotAI());

        bot.setMap(MapGenerator.getInstance(1280, 720, 3, 2));
        return bot;
    }

}
