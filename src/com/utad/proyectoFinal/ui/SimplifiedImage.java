package com.utad.proyectoFinal.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SimplifiedImage {
    public static final Integer DEF_WIDTH = 100;
    public static final Integer DEF_HEIGHT = 100;

    private String path;
    private Integer width;
    private Integer height;

    public SimplifiedImage(String path) {
        this(path, DEF_WIDTH, DEF_HEIGHT);
    }

    public SimplifiedImage(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return path;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPath(String path){
        this.path = path;
    }

    public Image generateImage(){
        return this.generateImage(width, height);
    }
    public Image generateImage(Integer reescalatedWidth, Integer reescalatedHeight) {
        ImageIcon iconoJugador = new ImageIcon(path);
        return iconoJugador.getImage().getScaledInstance(reescalatedWidth, reescalatedHeight, Image.SCALE_AREA_AVERAGING);
    }

    // convert BufferedImage to Image (static)
    public static Image generateImage(BufferedImage image) {
        return generateImage(image, image.getWidth(), image.getHeight());
    }
    public static Image generateImage(BufferedImage image, Integer reescalatedWidth, Integer reescalatedHeight) {
        return image.getScaledInstance(reescalatedWidth, reescalatedHeight, Image.SCALE_AREA_AVERAGING);
    }

    public ImageIcon generateImageIcon() {
        return this.generateImageIcon(width, height);
    }
    public ImageIcon generateImageIcon(Integer reescalatedWidth, Integer reescalatedHeight) {
        return new ImageIcon(this.generateImage(reescalatedWidth, reescalatedHeight));
    }

    public BufferedImage generateBufferedImage(){
        
        File file = new File(path);
        try { return ImageIO.read(file); }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: No se ha podido cargar la imagen desde la ruta: " + path);
            return null;
        }
    }

    public JLabel generateJLabel(){
        return this.generateJLabel(width, height);
    }
    public JLabel generateJLabel(Integer reescalatedWidth, Integer reescalatedHeight) {
        return this.generateJLabel(reescalatedWidth, reescalatedHeight, SwingConstants.CENTER);
    }
    public JLabel generateJLabel(Integer reescalatedWidth, Integer reescalatedHeight, int alignment) {
        JLabel label = new JLabel(this.generateImageIcon(reescalatedWidth, reescalatedHeight), alignment);
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }
}
