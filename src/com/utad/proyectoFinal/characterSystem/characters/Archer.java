/**
 * Clase Archer:
 * - Implementa la mecánica de puntería de un personaje de tipo arquero.
 *
 * Nota:
 * - Mecánica de puntería:
 *   - Cuanto mayor sea la puntería, menor probabilidad tiene el enemigo de huir.
 *   - Esta mecánica aplica un porcentaje reductor de probabilidad de huida del enemigo.
 *
 * Equipamiento:
 * - Armas de la categoría ArmaArquero: ArcoDeGuerrila, ArcoDePrecision, Ballesta
 * - Escudos normales: EscudoLigero, EscudoNormal, EscudoPesado
 *
 * Características:
 * - Agilidad del arquero (defensa +10.0%)
 * - Sin bonus de ataque inicial (ataque +0.0%)
 * - Con una puntería inicial de 10, que puede aumentar hasta 20.
 */
package com.utad.proyectoFinal.characterSystem.characters;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;



public class Archer extends BaseCharacter {

    public static final Double INITIAL_ACCURACY = 10.0;
    public static final Double ACCURACY_INCREMENT = 2.0;
    public static final Double MAX_ACCURACY = 20.0;
    public static final Double INITIAL_DEF_MODIFIER = 10.0;

    private Double accuracy;

    public Archer(/*Arma arco, Escudo escudo*/) {
        this("Archer " + contadorPersonajes/*, arco, escudo*/);

    }

    public Archer(String nombre/*, Arma arco, Escudo escudo*/) {
        super(nombre, DefaultAttributes.ATTACK, Archer.INITIAL_DEF_MODIFIER/*,  arco, escudo*/);
        this.accuracy = Archer.INITIAL_ACCURACY;
    }

    public BufferedImage seleccionarImagen() {
        BufferedImage imagen = null;

        String filePrefix = isAlive() ? "arquero" : "derrotado";
        String fileExtension = ".png";

        try {
            // Intentar cargar la imagen desde el .jar usando InputStream
            InputStream inputStream = getClass().getResourceAsStream("/" + filePrefix + fileExtension);
            if (inputStream != null) {
                imagen = ImageIO.read(inputStream);
            } else {
                System.err.println("Archivo no encontrado en el .jar: " + filePrefix + fileExtension);
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar la imagen desde el .jar: " + filePrefix + fileExtension);
            ex.printStackTrace();
        }

        return imagen;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }


    public String getSpecialAbility() {
        return "Puntería: " + accuracy;
    }

}