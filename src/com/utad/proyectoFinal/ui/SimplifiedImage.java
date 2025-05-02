package com.utad.proyectoFinal.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
        return iconoJugador.getImage().getScaledInstance(reescalatedWidth, reescalatedHeight, Image.SCALE_SMOOTH);
    }

    public ImageIcon generateImageIcon() {
        return this.generateImageIcon(width, height);
    }
    public ImageIcon generateImageIcon(Integer reescalatedWidth, Integer reescalatedHeight) {
        return new ImageIcon(this.generateImage(reescalatedWidth, reescalatedHeight));
    }

    public BufferedImage generateBufferedImage() {
        return this.generateBufferedImage(width, height, 0, 0);
    }
    public BufferedImage generateBufferedImage(Integer x, Integer y) {
        return this.generateBufferedImage(width, height, x, y);
    }
    public BufferedImage generateBufferedImage(Integer x, Integer y, Integer reescalatedWidth, Integer reescalatedHeight) {
        BufferedImage bufferedImage = new BufferedImage(reescalatedWidth, reescalatedHeight, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(this.generateImage(), x, y, null);
        return bufferedImage;
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
