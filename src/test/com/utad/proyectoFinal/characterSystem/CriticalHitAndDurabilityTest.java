package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.gameManagement.Calculator;
import com.utad.proyectoFinal.characterSystem.tools.items.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.items.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Test class demonstrating weapon/helmet durability and critical hit functionality
 */
public class CriticalHitAndDurabilityTest extends JFrame {

    public static final String ATTACK_TYPE = "Light";
    public static final String USES_LEFT = " uses left";
    public static final String STATUS_INDICATOR_OK = " (OK)";
    public static final String WEAPON = "Weapon: ";
    public static final String HELMET = "Helmet: ";
    public static final String LOG_DIVIDER = "------------------------------------------";
    private TestCharacter attacker;
    private TestCharacter defender;
    private JTextArea combatLog;
    private JProgressBar attackerHealthBar;
    private JProgressBar defenderHealthBar;
    private JProgressBar weaponDurabilityBar;
    private JProgressBar helmetDurabilityBar;
    private JLabel criticalHitLabel;
    private JLabel weaponStatusLabel;
    private JLabel helmetStatusLabel;
    private Random random = new Random();
    
    // For tracking statistics
    private int totalHits = 0;
    private int criticalHits = 0;
    private DecimalFormat df = new DecimalFormat("0.00");
    
    // Keep references to character rendering panels for repainting
    private JPanel attackerRenderPanel;
    private JPanel defenderRenderPanel;

    public CriticalHitAndDurabilityTest() {
        super("Critical Hit and Durability Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        
        try {
            // Set testing mode for all states to prevent automatic transitions
            TestUtils.setTestingMode(true);
            
            // Initialize characters with a character image
            Image attackerImage = loadImage("Files/img/GreenGuy.png");
            Image defenderImage = loadImage("Files/img/RedGuy.png");
            
            // Create characters
            attacker = new TestCharacter("Attacker", 20.0, attackerImage);
            defender = new TestCharacter("Defender", 15.0, defenderImage);

            // Initialize to idle state
            attacker.setIdleState();
            defender.setIdleState();

            // Equip weapon and helmet using character methods
            BaseWeapon sword = new BaseWeapon(WeaponType.SWORD);
            BaseHelmet helmet = new BaseHelmet(HelmetType.DEMON_HELMET);
            
            attacker.equipWeapon(sword);
            defender.equipHelmet(helmet);
            
            setupUI();
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading images: " + e.getMessage(), 
                "Image Load Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupUI() {
        JPanel statPanel;
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combat log panel
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Character panels
        JPanel characterPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Attacker panel
        JPanel attackerPanel = createCharacterPanel(attacker, true);
        characterPanel.add(attackerPanel);
        
        // Defender panel
        JPanel defenderPanel = createCharacterPanel(defender, false);
        characterPanel.add(defenderPanel);
        
        mainPanel.add(characterPanel, BorderLayout.NORTH);
        
        // Stats panel
        statPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        statPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        
        // Health bars
        attackerHealthBar = new JProgressBar(0, defender.getMaxHealthPoints());
        attackerHealthBar.setValue(attacker.getHealthPoints());
        attackerHealthBar.setStringPainted(true);
        attackerHealthBar.setString(attacker.getHealthPoints() + "/" + attacker.getMaxHealthPoints());
        
        defenderHealthBar = new JProgressBar(0, defender.getMaxHealthPoints());
        defenderHealthBar.setValue(defender.getHealthPoints());
        defenderHealthBar.setStringPainted(true);
        defenderHealthBar.setString(defender.getHealthPoints() + "/" + defender.getMaxHealthPoints());
        
        // Equipment durability bars
        weaponDurabilityBar = new JProgressBar(0, attacker.getWeapon().getDurability());
        weaponDurabilityBar.setValue(attacker.getWeapon().getDurability());
        weaponDurabilityBar.setStringPainted(true);
        weaponDurabilityBar.setString(attacker.getWeapon().getDurability() + USES_LEFT);
        
        helmetDurabilityBar = new JProgressBar(0, defender.getHelmet().getDurability());
        helmetDurabilityBar.setValue(defender.getHelmet().getDurability());
        helmetDurabilityBar.setStringPainted(true);
        helmetDurabilityBar.setString(defender.getHelmet().getDurability() + USES_LEFT);
        
        // Status labels
        criticalHitLabel = new JLabel("Critical hits: 0/0 (0.00%)");
        weaponStatusLabel = new JLabel(WEAPON + attacker.getWeapon().getName() + STATUS_INDICATOR_OK);
        helmetStatusLabel = new JLabel(HELMET + defender.getHelmet().getName() + STATUS_INDICATOR_OK);
        
        statPanel.add(new JLabel("Attacker Health:"));
        statPanel.add(attackerHealthBar);
        statPanel.add(new JLabel("Defender Health:"));
        statPanel.add(defenderHealthBar);
        statPanel.add(new JLabel("Weapon Durability:"));
        statPanel.add(weaponDurabilityBar);
        statPanel.add(new JLabel("Helmet Durability:"));
        statPanel.add(helmetDurabilityBar);
        
        JPanel statsInfoPanel = new JPanel(new GridLayout(3, 1));
        statsInfoPanel.add(criticalHitLabel);
        statsInfoPanel.add(weaponStatusLabel);
        statsInfoPanel.add(helmetStatusLabel);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(statPanel, BorderLayout.CENTER);
        southPanel.add(statsInfoPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // Control buttons
        JPanel controlPanel = new JPanel();
        
        JButton lightAttackBtn = new JButton("Light Attack");
        lightAttackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAttack(ATTACK_TYPE);
            }
        });
        
        JButton heavyAttackBtn = new JButton("Heavy Attack");
        heavyAttackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAttack("Heavy");
            }
        });
        
        JButton resetBtn = new JButton("Reset Test");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTest();
            }
        });
        
        JButton criticalTestBtn = new JButton("Critical Hit Test (x100)");
        criticalTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performMultipleAttacks();
            }
        });
        
        controlPanel.add(lightAttackBtn);
        controlPanel.add(heavyAttackBtn);
        controlPanel.add(criticalTestBtn);
        controlPanel.add(resetBtn);
        
        mainPanel.add(controlPanel, BorderLayout.EAST);
        
        add(mainPanel);
        
        // Initialize combat log
        logEvent("Test initialized with:");
        logEvent("Attacker: " + attacker.getName() + " - Base Attack: " + attacker.getBaseAttack());
        logEvent(WEAPON + attacker.getWeapon().getName() +
                 " - Damage: " + attacker.getWeapon().getDamage() + 
                 " - Critical Chance: " + (attacker.getWeapon().getCriticalChance() * 100) + "%" +
                 " - Critical Multiplier: " + attacker.getWeapon().getCriticalDamage() + "x");
        logEvent("Defender: " + defender.getName() + " - Base Defense: 0.0");
        logEvent(HELMET + defender.getHelmet().getName() +
                 " - Defense: " + defender.getHelmet().getDefense() + 
                 " - Durability: " + defender.getHelmet().getDurability());
        logEvent(LOG_DIVIDER);
    }
    
    private JPanel createCharacterPanel(TestCharacter character, boolean isAttacker) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            isAttacker ? "Attacker: " + character.getName() : "Defender: " + character.getName()));
        
        // Character rendering panel
        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                
                // Clear panel
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw character in center
                int x = (getWidth() - 82) / 2;
                int y = (getHeight() - 100) / 2;
                character.render(g2d, x, y);
            }
        };
        renderPanel.setPreferredSize(new Dimension(120, 150));
        panel.add(renderPanel, BorderLayout.CENTER);
        
        // Store reference to render panel for later repainting
        if (isAttacker) {
            attackerRenderPanel = renderPanel;
        } else {
            defenderRenderPanel = renderPanel;
        }
        
        // Stats
        JPanel statsPanel = new JPanel(new GridLayout(4, 1));
        
        String attackOrDefense = isAttacker ? "Attack: " : "Defense: ";
        double value = isAttacker ? character.getBaseAttack() : 0.0;
        
        JLabel attackDefLabel = new JLabel(attackOrDefense + value);
        JLabel healthLabel = new JLabel("Health: " + character.getHealthPoints() + "/" + character.getMaxHealthPoints());
        JLabel equipLabel;
        
        if (isAttacker) {
            equipLabel = new JLabel(WEAPON + (character.getWeapon() != null ?
                character.getWeapon().getName() : "None"));
        } else {
            equipLabel = new JLabel(HELMET + (character.getHelmet() != null ?
                character.getHelmet().getName() : "None"));
        }
        
        statsPanel.add(attackDefLabel);
        statsPanel.add(healthLabel);
        statsPanel.add(equipLabel);
        
        panel.add(statsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void performAttack(String attackType) {
        if (!attacker.isAlive() || !defender.isAlive()) {
            logEvent("Combat ended! Reset to continue testing.");
            return;
        }
        
        if (attacker.getWeapon() == null) {
            logEvent("Cannot attack - weapon is broken!");
            return;
        }
        
        // Set testing mode to prevent automatic state transitions
        TestUtils.setTestingMode(true);
        
        // Store initial damage stats before attack
        boolean willBeCritical = Calculator.getInstance().isCriticalHit(attacker);
        int initialHealth = defender.getHealthPoints();
        
        // Record hit statistics
        totalHits++;
        if (willBeCritical) criticalHits++;
        
        // Execute attack using state system and strategy pattern
        if (attackType.equals(ATTACK_TYPE)) {
            attacker.attack(defender, new LightAttackStrategy());
        } else {
            attacker.attack(defender, new HeavyAttackStrategy());
        }
        
        // Calculate how much damage was done
        int damageDealt = initialHealth - defender.getHealthPoints();
        
        // Update weapon durability bar
        updateWeaponDurabilityBar();

        // Update helmet durability bar
        updateHelmetDurabilityBar();
        
        // Check if weapon breaks
        if (attacker.getWeapon() == null) {
            logEvent(attacker.getName() + "'s weapon broke from use!");
            attacker.setWeapon(null);
            weaponStatusLabel.setText("Weapon: BROKEN");
            
            // Explicitly render character after weapon breaks
            if (attackerRenderPanel != null) {
                attackerRenderPanel.getGraphics().clearRect(0, 0, attackerRenderPanel.getWidth(), attackerRenderPanel.getHeight());
                int x = (getWidth() - 82) / 2;
                int y = (getHeight() - 100) / 2;
                attacker.render((Graphics2D)attackerRenderPanel.getGraphics(), 
                                x, 
                                y);
                attackerRenderPanel.repaint();
            }
        }
        
        // Check if helmet breaks
        if (defender.getHelmet() == null) {
            logEvent(defender.getName() + "'s helmet broke from damage!");
            defender.setHelmet(null);
            helmetStatusLabel.setText("Helmet: BROKEN");
            
            // Explicitly render character after helmet breaks
            if (defenderRenderPanel != null) {
                defenderRenderPanel.getGraphics().clearRect(0, 0, defenderRenderPanel.getWidth(), defenderRenderPanel.getHeight());
                int x = (getWidth() - 82) / 2;
                int y = (getHeight() - 100) / 2;    
                defender.render((Graphics2D)defenderRenderPanel.getGraphics(), 
                                x, 
                                y);
                defenderRenderPanel.repaint();
            }
        }
        
        // Log the attack
        String criticalText = willBeCritical ? "CRITICAL! " : "";
        logEvent(attacker.getName() + " performs " + attackType + " Attack: " + criticalText + 
                 damageDealt + " damage to " + defender.getName());
        
        // Update health bars
        updateHealthBars();
        
        // Update critical hit statistics
        updateCriticalHitStats();
        
        // Check for death
        if (!defender.isAlive()) {
            logEvent(defender.getName() + " has been defeated!");
        }
    }
    
    private void performMultipleAttacks() {
        // Ensure we're in testing mode to prevent auto-transitions
        TestUtils.setTestingMode(true);
        
        for (int i = 0; i < 100; i++) {
            // Only perform if characters are still alive
            if (attacker.isAlive() && defender.isAlive() && attacker.getWeapon() != null) {
                // Randomly choose light or heavy attack
                boolean isLightAttack = random.nextBoolean();
                String attackType = isLightAttack ? ATTACK_TYPE : "Heavy";
                
                // Reset to idle state before each attack to ensure consistent behavior
                attacker.setIdleState();
                
                // Perform attack
                performAttack(attackType);
                
                // If weapon broke, stop attacks
                if (attacker.getWeapon() == null) {
                    logEvent("Multiple attack sequence stopped - weapon broke!");
                    break;
                }
                
                // If defender died, stop attacks
                if (!defender.isAlive()) {
                    logEvent("Multiple attack sequence stopped - defender died!");
                    break;
                }
            }
        }
        
        logEvent(LOG_DIVIDER);
        logEvent("Attack sequence completed. Stats:");
        logEvent("Critical hits: " + criticalHits + "/" + totalHits + 
                " (" + df.format((double)criticalHits/totalHits * 100) + "%)");
        logEvent("Expected critical rate: " + 
                 df.format(WeaponType.SWORD.getCriticalChance() * 100) + "%");
        logEvent(LOG_DIVIDER);
    }
    
    private void resetTest() {
        try {
            // Load character images again
            Image defenderImage = loadImage("Files/img/RedGuy.png");
            
            // Reset character health and equipment
            defender = new TestCharacter("Defender", 15.0, defenderImage);
            
            // Ensure testing mode is active
            TestUtils.setTestingMode(true);
            
            // Set characters to initial idle state
            attacker.setIdleState();
            defender.setIdleState();
            
            // Equip weapon and helmet using character methods
            BaseWeapon sword = new BaseWeapon(WeaponType.SWORD);
            BaseHelmet helmet = new BaseHelmet(HelmetType.DEMON_HELMET);
            
            attacker.equipWeapon(sword);
            defender.equipHelmet(helmet);
            
            // Reset statistics
            totalHits = 0;
            criticalHits = 0;
            
            // Update UI elements
            updateHealthBars();
            updateWeaponDurabilityBar();
            updateHelmetDurabilityBar();
            updateCriticalHitStats();
            
            weaponStatusLabel.setText(WEAPON + attacker.getWeapon().getName() + STATUS_INDICATOR_OK);
            helmetStatusLabel.setText(HELMET + defender.getHelmet().getName() + STATUS_INDICATOR_OK);
            
            // Log reset
            logEvent(LOG_DIVIDER);
            logEvent("Test reset - characters and equipment restored");
            logEvent("Current states: Attacker: " + attacker.getCurrentStateName() + 
                    ", Defender: " + defender.getCurrentStateName());
            logEvent(LOG_DIVIDER);
            
            // Simple UI refresh - just repaint the render panels
            if (attackerRenderPanel != null) {
                attackerRenderPanel.getGraphics().clearRect(0, 0, attackerRenderPanel.getWidth(), attackerRenderPanel.getHeight());
                int x = (getWidth() - 82) / 2;
                int y = (getHeight() - 100) / 2;
                attacker.render((Graphics2D)attackerRenderPanel.getGraphics(), 
                                x, 
                                y);
                attackerRenderPanel.repaint();
            }
            if (defenderRenderPanel != null) {
                defenderRenderPanel.getGraphics().clearRect(0, 0, defenderRenderPanel.getWidth(), defenderRenderPanel.getHeight());
                int x = (getWidth() - 82) / 2;  
                int y = (getHeight() - 100) / 2;
                defender.render((Graphics2D)defenderRenderPanel.getGraphics(), 
                                x, 
                                y);
                defenderRenderPanel.repaint();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            logEvent("Error during reset: " + e.getMessage());
        }
    }
    
    private void updateHealthBars() {
        attackerHealthBar.setValue(attacker.getHealthPoints());
        attackerHealthBar.setString(attacker.getHealthPoints() + "/" + attacker.getMaxHealthPoints());
        
        defenderHealthBar.setValue(defender.getHealthPoints());
        defenderHealthBar.setString(defender.getHealthPoints() + "/" + defender.getMaxHealthPoints());
    }
    
    private void updateWeaponDurabilityBar() {
        if (attacker.getWeapon() != null) {
            weaponDurabilityBar.setMaximum(WeaponType.SWORD.getDurability());
            weaponDurabilityBar.setValue(attacker.getWeapon().getDurability());
            weaponDurabilityBar.setString(attacker.getWeapon().getDurability() + USES_LEFT);
        } else {
            weaponDurabilityBar.setValue(0);
            weaponDurabilityBar.setString("BROKEN");
        }
    }
    
    private void updateHelmetDurabilityBar() {
        if (defender.getHelmet() != null) {
            helmetDurabilityBar.setMaximum(HelmetType.DEMON_HELMET.getDurability());
            helmetDurabilityBar.setValue(defender.getHelmet().getDurability());
            helmetDurabilityBar.setString(defender.getHelmet().getDurability() + USES_LEFT);
        } else {
            helmetDurabilityBar.setValue(0);
            helmetDurabilityBar.setString("BROKEN");
        }
    }
    
    private void updateCriticalHitStats() {
        if (totalHits > 0) {
            double percentage = (double)criticalHits / totalHits * 100;
            criticalHitLabel.setText("Critical hits: " + criticalHits + "/" + totalHits + 
                                     " (" + df.format(percentage) + "%)");
        } else {
            criticalHitLabel.setText("Critical hits: 0/0 (0.00%)");
        }
    }
    
    private void logEvent(String message) {
        combatLog.append(message + "\n");
        // Scroll to bottom
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
    }
    
    private Image loadImage(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        return ImageIO.read(file);
    }
    
    public static void main(String[] args) {
        // Print current working directory for debugging
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                CriticalHitAndDurabilityTest test = new CriticalHitAndDurabilityTest();
                test.setVisible(true);
            }
        });
    }
}