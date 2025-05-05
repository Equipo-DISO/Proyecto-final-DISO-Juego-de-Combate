package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeCBotFactory implements BotFactory {
    @Override
    public Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeCBotAI()); // sobrevive y lucha
        bot.setMap(MapGenerator.getInstance(1280, 720, 3, 2, null, null));
        return bot;
    }
}