package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.mapa.GenericTile;

public abstract class BotAI {

    // Método plantilla para recoger las 3 distintas acciones
    public final void executeTurn(Bot bot) {
        analyzeSituation(bot);
        decideNextMove(bot.getCurrentPosition());
        performAction(bot);
    }

    protected abstract void analyzeSituation(Bot bot);
    protected abstract void decideNextMove(GenericTile tile);
    protected abstract void performAction(Bot bot);
}