package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.mapa.GenericTile;

import java.util.*;

public abstract class BotAI {

    protected List<GenericTile> targets;
    protected GenericTile currentStepTile;

    // Metodo plantilla para recoger las 3 distintas acciones
    public final void executeTurn(Bot bot) {
        analyzeSituation(bot);
        decideNextMove(bot.getCurrentPosition(), bot);
        performAction(bot);
    }

    protected abstract void analyzeSituation(Bot bot);
    protected abstract void decideNextMove(GenericTile tile, Bot bot);
    protected abstract void performAction(Bot bot);
}