package com.utad.proyectoFinal.mapa;

import javax.swing.*;


public class MapTesting 
{
    public static void main(String[] args) 
    {
        // Crear la ventana
        JFrame frame = new JFrame("Hexágono 3D Testing");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 1080);
        frame.add(MapGenerator.getInstance());
        frame.setVisible(true);

    }
}