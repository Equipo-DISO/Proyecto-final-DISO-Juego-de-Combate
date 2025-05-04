package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeBBotFactory implements BotFactory {
    @Override
    public Bot createBot() {
        Bot bot = new Bot();
        bot.setBotAI(new TypeBBotAI()); // IA defensiva, por ejemplo
        bot.setMap(MapGenerator.getInstance(1280, 720, 3, 2, null, null));
        return bot;
    }
}
