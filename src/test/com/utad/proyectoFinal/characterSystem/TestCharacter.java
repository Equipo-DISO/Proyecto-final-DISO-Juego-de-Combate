package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

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

}
