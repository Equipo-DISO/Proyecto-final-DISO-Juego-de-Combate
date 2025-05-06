package com.utad.proyectoFinal;

import com.utad.proyectoFinal.gameManagement.GameControllerFacade;

public class Main {
    public static void main(String[] args) {
        GameControllerFacade gameController = new GameControllerFacade();
        gameController.startGame();
    }
} 