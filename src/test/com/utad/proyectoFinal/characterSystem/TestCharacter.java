package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

import java.awt.image.BufferedImage;

/**
 * A concrete implementation of BaseCharacter for testing purposes.
 */
public class TestCharacter extends BaseCharacter {

    /**
     * Creates a test character with the specified name, attack, and defense.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     * @param baseDefense The character's base defense value
     */
    public TestCharacter(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack, baseDefense);
    }

    /**
     * Creates a test character with the specified name, attack, defense, and avatar.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     * @param baseDefense The character's base defense value
     * @param avatar The character's custom avatar
     */
    public TestCharacter(String name, Double baseAttack, Double baseDefense, BufferedImage avatar) {
        super(name, baseAttack, baseDefense, avatar);
    }
}
