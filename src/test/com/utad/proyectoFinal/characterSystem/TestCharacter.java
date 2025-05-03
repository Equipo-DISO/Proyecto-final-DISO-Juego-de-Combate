package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * A concrete implementation of BaseCharacter for testing purposes.
 */
public class TestCharacter extends BaseCharacter {

    /**
     * Creates a test character with the specified name and attack.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     */
    public TestCharacter(String name, Double baseAttack) {
        super(name, baseAttack);
    }

    /**
     * Creates a test character with the specified name, attack, and avatar.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     * @param avatar The character's custom avatar
     */
    public TestCharacter(String name, Double baseAttack, Image avatar) {
        super(name, baseAttack, avatar);
    }

    public void render(Graphics2D g, int x, int y) {
        if (characterImage != null) {
            g.drawImage(getCompleteImage(), x, y, null);
        }
    }
}
