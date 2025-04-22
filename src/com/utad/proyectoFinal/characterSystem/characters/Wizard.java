/**
 * Clase Wizard:
 * - Implementa la lógica de la mecánica de crítico de un personaje de tipo Wizard.
 *
 * Nota:
 * - La clase Wizard empieza con 2 pociones: una de defensa y otra de ataque.
 *
 * Equipamiento:
 * - Armas de la categoría ArmaMago: BastonDeSabiduria, OrbeAncestral, VaritaDeCristal
 * - Escudos normales: EscudoLigero, EscudoNormal, EscudoPesado
 *
 * Características:
 * - Hechizo de defensor mágico que aumenta su defensa base (defensa +5.0%)
 * - Magia rúnica que aumenta su daño base (ataque +5.0%)
 * - Un bastón mágico que le permite lanzar hechizos mágicos y tiene una pequeña probabilidad (2%) de hacer un ataque crítico (+50%)
 *   - La probabilidad de ataque crítico aumenta en +2 hasta un 10%.
 */
package com.utad.proyectoFinal.characterSystem.characters;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Wizard extends BaseCharacter {

    public static final Double CRITICAL_ATTACK = 50.0;

    public static final Double INITIAL_CRIT_CHANCE = 2.0;
    public static final Double CRIT_CHANCE_INCREMENT = 2.0;
    public static final Double MAX_CRIT_CHANCE = 10.0;

    public static final Double INITIAL_ATK_MOD = 5.0;
    public static final Double INITIAL_DEF_MOD = 5.0;

    public static final Integer DEFAULT_ITEMS_NUMBER = 2;

    private Double critChance;

    public Wizard(/*Arma arma, Escudo escudo*/) {
        this("Wizard " + contadorPersonajes/*, arma, escudo*/);
    }

    public Wizard(String nombre/*, Arma arma, Escudo escudo*/) {
        super(nombre, Wizard.INITIAL_ATK_MOD, Wizard.INITIAL_DEF_MOD/*, arma, escudo*/);
        this.critChance = Wizard.INITIAL_CRIT_CHANCE;
        // this.aniadaPociones(); TODO: Re-add after items are implemented
    }

    // TODO: Move to state as a strategy
//    @Override
//    protected Double calcularDanio() {
//        // Obtenemos el daño base del arma
//        Double danioTotal = super.armaPersonaje.getDanio() * (1 + (super.ataque / 100));
//
//        if (this.momentoAtaqueCritico()) {
//            System.out.println("Ataque crítico! Un " + Wizard.ATAQUE_CRITICO + "% de daño adicional");
//            danioTotal += danioTotal * (Wizard.ATAQUE_CRITICO / 100);
//        }
//        return danioTotal;
//    }

    // TODO: Move to state as a part of an strategy
//    // Calcula si el ataque es crítico
//    protected boolean momentoAtaqueCritico() {
//        boolean critico = false;
//        Double probabilidad = Math.random() * 100; // Lanza un número aleatorio entre 0 y 100
//        if (probabilidad <= this.critChance) {
//            critico = true;
//        }
//        return critico;
//    }

    // TODO: Re-add after items are implemented
//    // Añade las pociones iniciales
//    private void aniadaPociones() {
//        super.items.add(new PocionDeAtaque(PocionDeAtaque.VALOR_EFECTO));
//        super.items.add(new PocionDeDefensa(PocionDeDefensa.VALOR_EFECTO));
//    }

    public BufferedImage seleccionarImagen() {
        BufferedImage imagen = null;

        String filePrefix = isAlive() ? "mago" : "derrotado";
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

    public Double getCritChance() {
        return critChance;
    }

    public void setCritChance(Double critChance) {
        this.critChance = critChance;
    }

    @Override
    public String getSpecialAbility() {
        return "Probabilidad de crítico: " + critChance + "%";
    }

}