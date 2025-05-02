package com.utad.proyectoFinal.ui.lobby;

import javax.swing.JFrame;

import com.utad.proyectoFinal.ui.SimplifiedImage;

public class StartScreenInterface extends JFrame{

    public StartScreenInterface() {
        setTitle("Juego de Combate");
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }

    public static void main(String[] args) {
        StartScreenInterface startScreen = new StartScreenInterface();
        startScreen.setVisible(true);
        startScreen.setResizable(false); // Hacer la ventana no redimensionable
    }

}
