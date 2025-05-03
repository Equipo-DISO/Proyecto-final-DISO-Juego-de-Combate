package test.com.utad.proyectoFinal.characterSystem;

/**
 * Utility class for testing character states and behaviors.
 * This class provides methods to control state transitions during testing
 * without modifying production code.
 */
public class TestUtils {
    
    private static boolean testingMode = false;
    
    /**
     * Sets the testing mode for character states.
     * When in testing mode, states don't automatically transition back to Idle.
     * 
     * @param isTestingMode true to enable testing mode, false otherwise
     */
    public static void setTestingMode(boolean isTestingMode) {
        testingMode = isTestingMode;
    }
    
    /**
     * Checks if testing mode is currently enabled.
     * 
     * @return true if in testing mode, false otherwise
     */
    public static boolean isTestingMode() {
        return testingMode;
    }
}