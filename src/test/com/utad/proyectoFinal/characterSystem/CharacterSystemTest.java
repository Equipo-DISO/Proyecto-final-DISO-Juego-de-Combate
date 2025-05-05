package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.items.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.items.WeaponType;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Test class for the character system.
 * Tests attack values, damage calculation, and state transitions.
 */
public class CharacterSystemTest {

    /**
     * Main method to run the tests.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Character System Tests");

        // Set testing mode for all states to avoid automatic transitions
        TestUtils.setTestingMode(true);

        // Run all tests
        testAttackValues();
        testDamageCalculation();
        testStateTransitions();
        testAttackStateWithoutAutoTransition();
        testAllStatesWithoutAutoTransition();

        System.out.println("All tests completed");
    }

    /**
     * Tests attack values with different weapons.
     */
    private static void testAttackValues() throws Exception {
        System.out.println("\n=== Testing Attack Values ===");

        // Create a character with default attack
        TestCharacter character = new TestCharacter("TestWarrior", DefaultAttributes.ATTACK);
        System.out.println("Base attack without weapon: " + character.getBaseAttack());

        // Test with different weapons
        BaseWeapon sword = new BaseWeapon(WeaponType.SWORD);
        character.setWeapon(sword);

        // Test attack calculation
        double expectedAttack = DefaultAttributes.ATTACK + sword.getDamage();
        System.out.println("Expected attack with sword: " + expectedAttack);

        // Verify attack value is correct
        if (Math.abs(expectedAttack - (character.getBaseAttack() + character.getWeapon().getDamage())) < 0.001) {
            System.out.println("✓ Attack value test passed");
        } else {
            throw new Exception("Attack value test failed");
        }
    }

    /**
     * Tests damage calculation with defense.
     */
    private static void testDamageCalculation() throws Exception {
        System.out.println("\n=== Testing Damage Calculation ===");

        // Create attacker and defender
        TestCharacter attacker = new TestCharacter("Attacker", 20.0);
        TestCharacter defender = new TestCharacter("Defender", 0.0);

        // Set initial health
        int initialHealth = defender.getHealthPoints();
        System.out.println("Defender initial health: " + initialHealth);

        // Create a weapon for the attacker
        BaseWeapon weapon = new BaseWeapon(WeaponType.SWORD);
        attacker.setWeapon(weapon);

        // Calculate expected damage
        double baseAttack = attacker.getBaseAttack() + weapon.getDamage();
        double expectedDamage = baseAttack;
        System.out.println("Expected damage: " + expectedDamage);

        // Simulate attack
        System.out.println("Simulating attack...");
        defender.getCurrentState().handleReceiveAttack(baseAttack);

        // Check resulting health
        int expectedHealth = initialHealth - (int) expectedDamage;
        System.out.println("Expected health after attack: " + expectedHealth);
        System.out.println("Actual health after attack: " + defender.getHealthPoints());

        // Verify damage calculation
        if (Math.abs(expectedHealth - defender.getHealthPoints()) <= 1) { // Allow for rounding differences
            System.out.println("✓ Damage calculation test passed");
        } else {
            throw new Exception("Damage calculation test failed");
        }

        // Test with helmet
        System.out.println("\nTesting with helmet defense...");
        defender.setHealthPoints(initialHealth); // Reset health

        BaseHelmet helmet = new BaseHelmet(HelmetType.DEMON_HELMET);
        defender.setHelmet(helmet);

        // Calculate expected damage with helmet
        double expectedDamageWithHelmet = Math.max(0, baseAttack - helmet.getDefense());
        System.out.println("Expected damage with helmet: " + expectedDamageWithHelmet);

        // Simulate attack with helmet
        defender.getCurrentState().handleReceiveAttack(baseAttack);

        // Check resulting health
        int expectedHealthWithHelmet = initialHealth - (int) expectedDamageWithHelmet;
        System.out.println("Expected health after attack with helmet: " + expectedHealthWithHelmet);
        System.out.println("Actual health after attack with helmet: " + defender.getHealthPoints());

        // Verify damage calculation with helmet
        if (Math.abs(expectedHealthWithHelmet - defender.getHealthPoints()) <= 1) { // Allow for rounding differences
            System.out.println("✓ Damage calculation with helmet test passed");
        } else {
            throw new Exception("Damage calculation with helmet test failed");
        }
    }

    /**
     * Tests state transitions.
     */
    private static void testStateTransitions() throws Exception {
        System.out.println("\n=== Testing State Transitions ===");

        // Create characters
        TestCharacter character = new TestCharacter("StateTest", DefaultAttributes.ATTACK);
        TestCharacter opponent = new TestCharacter("Opponent", DefaultAttributes.ATTACK);

        // Test initial state (should be Idle)
        System.out.println("Initial state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Idle")) {
            System.out.println("✓ Initial state test passed");
        } else {
            throw new Exception("Initial state test failed");
        }

        // Test transition to Attacking state by calling handleAttack on Idle state
        System.out.println("\nTransitioning to Attacking state via handleAttack...");
        // Save current state to verify it's Idle
        CharacterState initialState = character.getCurrentState();
        // Call handleAttack on the current state (which should be Idle)
        initialState.handleAttack(opponent, new HeavyAttackStrategy());
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Attacking")) {
            System.out.println("✓ Transition to Attacking state via handleAttack test passed");
        } else {
            throw new Exception("Transition to Attacking state via handleAttack test failed");
        }

        // Reset to Idle state for next test
        character.setIdleState();

        // Test transition to Retreating state by calling handleRetreat on Idle state
        System.out.println("\nTransitioning to Retreating state via handleRetreat...");
        // Save current state to verify it's Idle
        initialState = character.getCurrentState();
        // Call handleRetreat on the current state (which should be Idle)
        initialState.handleRetreat(opponent);
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Retreating")) {
            System.out.println("✓ Transition to Retreating state via handleRetreat test passed");
        } else {
            throw new Exception("Transition to Retreating state via handleRetreat test failed");
        }

        // Reset to Idle state for next test
        character.setIdleState();

        // Test transition to Tired state
        System.out.println("\nTransitioning to Tired state...");
        // First, reduce mana to trigger tired state
        character.setManaPoints(DefaultAttributes.LOW_MANA_THRESHOLD - 1);
        // Call handleAttack which should check mana and transition to Tired
        character.getCurrentState().handleAttack(opponent, new HeavyAttackStrategy());
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Tired")) {
            System.out.println("✓ Transition to Tired state test passed");
        } else {
            throw new Exception("Transition to Tired state test failed");
        }

        // Reset mana and set to Idle state for next test
        character.setManaPoints(DefaultAttributes.MAX_MANA_POINTS);
        character.setIdleState();

        // Test transition to Dead state
        System.out.println("\nTransitioning to Dead state...");
        character.setHealthPoints(0);
        // Call updateState which should check health and transition to Dead
        character.getCurrentState().updateState();
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Dead")) {
            System.out.println("✓ Transition to Dead state test passed");
        } else {
            throw new Exception("Transition to Dead state test failed");
        }
    }

    /**
     * Tests that the AttackingState doesn't automatically transition back to Idle in testing mode.
     * This demonstrates how to check the state after an attack without it immediately changing back to Idle.
     */
    private static void testAttackStateWithoutAutoTransition() throws Exception {
        System.out.println("\n=== Testing Attack State Without Auto Transition ===");

        // Create characters
        TestCharacter character = new TestCharacter("StateTest", DefaultAttributes.ATTACK);
        TestCharacter opponent = new TestCharacter("Opponent", DefaultAttributes.ATTACK);

        // Ensure we're in testing mode
        TestUtils.setTestingMode(true);

        // Transition to Attacking state by calling handleAttack on Idle state
        System.out.println("Transitioning to Attacking state via handleAttack...");
        character.getCurrentState().handleAttack(opponent, new HeavyAttackStrategy());
        System.out.println("Current state after attack: " + character.getCurrentState().getName());

        // In testing mode, the state should remain Attacking and not auto-transition to Idle
        if (character.getCurrentState().getName().equals("Attacking")) {
            System.out.println("✓ State remains Attacking after attack (no auto-transition to Idle)");
        } else {
            throw new Exception("State did not remain Attacking after attack");
        }

        // Now manually transition back to Idle
        System.out.println("Manually transitioning back to Idle...");
        character.setIdleState();
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Idle")) {
            System.out.println("✓ Manual transition to Idle state successful");
        } else {
            throw new Exception("Manual transition to Idle state failed");
        }
    }

    /**
     * Tests that all states don't automatically transition back to Idle in testing mode.
     * This demonstrates how to check each state without it immediately changing back to Idle.
     */
    private static void testAllStatesWithoutAutoTransition() throws Exception {
        System.out.println("\n=== Testing All States Without Auto Transition ===");

        // Create characters
        TestCharacter character = new TestCharacter("StateTest", DefaultAttributes.ATTACK);
        TestCharacter opponent = new TestCharacter("Opponent", DefaultAttributes.ATTACK);

        // Ensure we're in testing mode
        TestUtils.setTestingMode(true);

        // Test Retreating State
        System.out.println("\nTesting Retreating State without auto-transition...");
        character.setIdleState(); // Start from Idle
        character.getCurrentState().handleRetreat(opponent);
        System.out.println("Current state after retreat: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Retreating")) {
            System.out.println("✓ State remains Retreating after retreat (no auto-transition)");
        } else {
            throw new Exception("State did not remain Retreating after retreat");
        }

        // Test MovingOnMap State
        System.out.println("\nTesting MovingOnMap State without auto-transition...");
        character.setIdleState(); // Start from Idle
        character.getCurrentState().handleMove(new GenericTile(1, 1, 1)); // Placeholder object
        System.out.println("Current state after move: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("MovingOnMap")) {
            System.out.println("✓ State remains MovingOnMap after move (no auto-transition)");
        } else {
            throw new Exception("State did not remain MovingOnMap after move");
        }

        // Test Tired State
        System.out.println("\nTesting Tired State without auto-transition...");
        character.setIdleState(); // Start from Idle
        character.setManaPoints(DefaultAttributes.LOW_MANA_THRESHOLD - 1); // Set low mana
        character.getCurrentState().handleAttack(opponent, new HeavyAttackStrategy()); // Should transition to Tired
        System.out.println("Current state after attack with low mana: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Tired")) {
            System.out.println("✓ State remains Tired after attack with low mana (no auto-transition)");
        } else {
            throw new Exception("State did not remain Tired after attack with low mana");
        }

        // Reset mana for next tests
        character.setManaPoints(DefaultAttributes.MAX_MANA_POINTS);

        // Manually transition back to Idle
        System.out.println("\nManually transitioning back to Idle...");
        character.setIdleState();
        System.out.println("Current state: " + character.getCurrentState().getName());
        if (character.getCurrentState().getName().equals("Idle")) {
            System.out.println("✓ Manual transition to Idle state successful");
        } else {
            throw new Exception("Manual transition to Idle state failed");
        }
    }
}