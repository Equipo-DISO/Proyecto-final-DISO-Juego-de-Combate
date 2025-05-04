package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;
import java.util.*;

import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeABotFactory implements BotFactory {
    @Override
    public Bot createBot(){
        Bot bot = new Bot();
        bot.setBotAI(new TypeABotAI());

        bot.setMap(MapGenerator.getInstance(1280, 720, 3, 2, null, null));
        return bot;
    }

}
