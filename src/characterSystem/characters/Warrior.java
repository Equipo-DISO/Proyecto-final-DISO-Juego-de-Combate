/**
 * Clase Warrior:
 * - Implementa los métodos de la mecánica de contraataque de un personaje de tipo Warrior.
 * - (Polimorfismo) Sobreescribe el método recibirAtaque para implementar la mecánica de aumento de la ira espartana.
 *
 * Nota:
 * - Mecánica de contraataque:
 *   - El guerrero aumenta su probabilidad de contraataque cada vez que recibe un ataque.
 *   - La probabilidad de contraataque se inicializa a 0 y aumenta en 5% cada vez que el guerrero recibe un ataque. (Máximo 30%)
 *
 * Equipamiento:
 * - Armas de la categoría ArmaGuerrero: EspadaBastarda, HachaDobleFilo, LanzaPuntiaguda
 * - Escudos normales: EscudoLigero, EscudoNormal, EscudoPesado
 *
 * Características:
 * - Fortaleza del guerrero (defensa +20.0%)
 * - Una gran fuerza que le permite aumentar la fuerza de su ataque (+20.0%)
 * - Una característica especial que le permite contraatacar con mayor probabilidad, llamada 'Ira Espartana'
 *   - Este atributo se inicializa a 0 y aumenta en 5% cada vez que el guerrero recibe un ataque. (Máximo 30%)
 */
package characterSystem.characters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Warrior extends BaseCharacter {
    public static final Double INITIAL_SPARTAN_RAGE = 0.0;
    public static final Double SPARTAN_RAGE_INCREMENT = 5.0;
    public static final Double MAX_SPARTAN_RAGE = 30.0;

    public static final Double INITIAL_ATK_MOD = 20.0;
    public static final Double INITIAL_DEF_MOD = 20.0;

    private Double spartanRageCounter;

    public Warrior(/*Arma arma, Escudo escudo*/) {
        this("Warrior " + contadorPersonajes/*, arma, escudo*/);
    }

    public Warrior(String nombre/*, Arma arma, Escudo escudo*/) {
        super(nombre, Warrior.INITIAL_ATK_MOD, Warrior.INITIAL_DEF_MOD/*, arma, escudo*/);
        this.spartanRageCounter = Warrior.INITIAL_SPARTAN_RAGE;
    }

    // TODO: Move to state as a strategy
//    @Override
//    protected Boolean contraAtaco() {
//        Boolean contraAtaco = false;
//        Double probabilidadContraataque = Personaje.PROBABILIDAD_CONTRAATAQUE_DEFAULT + this.iraEspartanaContraataque;
//        if (probabilidadContraataque > Math.random() * 100) {
//            contraAtaco = true;
//        }
//        return contraAtaco;
//    }

    // TODO: Move to state as a strategy
//    @Override
//    public void recibirAtaque(Double danio) {
//        super.recibirAtaque(danio);
//        this.iraEspartanaContraataque += Warrior.IRA_ESPARTANA_INCREMENTO;
//        this.iraEspartanaContraataque = Math.min(this.iraEspartanaContraataque, Warrior.IRA_ESPARTANA_MAX);
//        System.out.println("Tras recibir un ataque, la ira espartana del guerrero aumenta a "
//                + this.iraEspartanaContraataque + "%");
//    }


    public BufferedImage seleccionarImagen() {
        BufferedImage imagen = null;

        String filePrefix = isAlive() ? "guerrero" : "derrotado";
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

    public void setSpartanRageCounter(Double spartanRageCounter) {
        this.spartanRageCounter = spartanRageCounter;
    }

    public Double getSpartanRageCounter() {
        return spartanRageCounter;
    }

    @Override
    public String getSpecialAbility() {
        return "Ira Espartana: " + spartanRageCounter + "%";
    }

}