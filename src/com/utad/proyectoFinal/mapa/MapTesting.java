package com.utad.proyectoFinal.mapa;

import javax.swing.*;




public class MapTesting 
{
    public static void main(String[] args) 
    {
        // Crear la ventana
        JFrame frame = new JFrame("Hexágono 3D Testing");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.add(MapGenerator.getInstance(900, 0, 2, 2));
        frame.setVisible(true);
        //frame.setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
    }
}