package test.com.utad.proyectoFinal.characterSystem;

/**
 * Utility class for testing purposes.
 * Contains functionality that was previously mixed with production code.
 */
public class TestUtils {
    
    // Flag to indicate if we're in testing mode
    private static boolean isTestingMode = false;
    
    /**
     * Sets the testing mode flag
     * @param testing true if in testing mode, false otherwise
     */
    public static void setTestingMode(boolean testing) {
        isTestingMode = testing;
    }
    
    /**
     * Checks if we're in testing mode
     * @return true if in testing mode, false otherwise
     */
    public static boolean isTestingMode() {
        return isTestingMode;
    }
}