package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * A concrete implementation of BaseCharacter for testing purposes.
 */
public class TestCharacter extends BaseCharacter {
    
    private Double baseDefense;

    /**
     * Creates a test character with the specified name, attack, and defense.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     * @param baseDefense The character's base defense value
     */
    public TestCharacter(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack);
        this.baseDefense = baseDefense;
    }

    /**
     * Creates a test character with the specified name, attack, defense, and avatar.
     * 
     * @param name The character's name
     * @param baseAttack The character's base attack value
     * @param baseDefense The character's base defense value
     * @param avatar The character's custom avatar
     */
    public TestCharacter(String name, Double baseAttack, Double baseDefense, Image avatar) {
        super(name, baseAttack, avatar);
        this.baseDefense = baseDefense;
    }

    /**
     * Gets the character's base defense value.
     * 
     * @return The character's base defense value
     */
    public Double getBaseDefense() {
        return baseDefense;
    }

    public void render(Graphics2D g, int x, int y) {
        if (characterImage != null) {
            g.drawImage(getCompleteImage(), x, y, null);
        }
    }
}
