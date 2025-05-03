package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.CharacterState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.Calculator;
import com.utad.proyectoFinal.characterSystem.tools.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Test class demonstrating weapon/helmet durability and critical hit functionality
 */
public class CriticalHitAndDurabilityTest extends JFrame {

    private TestCharacter attacker;
    private TestCharacter defender;
    private JTextArea combatLog;
    private JPanel statPanel;
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
            attacker = new TestCharacter("Attacker", 20.0, 10.0, attackerImage);
            defender = new TestCharacter("Defender", 15.0, 20.0, defenderImage);

            // Initialize to idle state
            attacker.setIdleState();
            defender.setIdleState();

            // Equip weapon and helmet using character methods
            BaseWeapon sword = new BaseWeapon(WeaponType.STICK);
            BaseHelmet helmet = new BaseHelmet(HelmetType.DEMON_HELMET);
            
            attacker.setWeapon(sword);
            defender.setHelmet(helmet);
            
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
        weaponDurabilityBar.setString(attacker.getWeapon().getDurability() + " uses left");
        
        helmetDurabilityBar = new JProgressBar(0, defender.getHelmet().getDurability());
        helmetDurabilityBar.setValue(defender.getHelmet().getDurability());
        helmetDurabilityBar.setStringPainted(true);
        helmetDurabilityBar.setString(defender.getHelmet().getDurability() + " uses left");
        
        // Status labels
        criticalHitLabel = new JLabel("Critical hits: 0/0 (0.00%)");
        weaponStatusLabel = new JLabel("Weapon: " + attacker.getWeapon().getName() + " (OK)");
        helmetStatusLabel = new JLabel("Helmet: " + defender.getHelmet().getName() + " (OK)");
        
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
                performAttack(1.0, "Light");
            }
        });
        
        JButton heavyAttackBtn = new JButton("Heavy Attack");
        heavyAttackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAttack(2.0, "Heavy");
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
                performMultipleAttacks(100);
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
        logEvent("Weapon: " + attacker.getWeapon().getName() + 
                 " - Damage: " + attacker.getWeapon().getDamage() + 
                 " - Critical Chance: " + (attacker.getWeapon().getCriticalChance() * 100) + "%" +
                 " - Critical Multiplier: " + attacker.getWeapon().getCriticalDamage() + "x");
        logEvent("Defender: " + defender.getName() + " - Base Defense: " + defender.getBaseDefense());
        logEvent("Helmet: " + defender.getHelmet().getName() + 
                 " - Defense: " + defender.getHelmet().getDefense() + 
                 " - Durability: " + defender.getHelmet().getDurability());
        logEvent("------------------------------------------");
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
                if (character != null) {
                    int x = (getWidth() - 82) / 2;
                    int y = (getHeight() - 100) / 2;
                    character.render(g2d, x, y);
                }
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
        double value = isAttacker ? character.getBaseAttack() : character.getBaseDefense();
        
        JLabel attackDefLabel = new JLabel(attackOrDefense + value);
        JLabel healthLabel = new JLabel("Health: " + character.getHealthPoints() + "/" + character.getMaxHealthPoints());
        JLabel equipLabel;
        
        if (isAttacker) {
            equipLabel = new JLabel("Weapon: " + (character.getWeapon() != null ? 
                character.getWeapon().getName() : "None"));
        } else {
            equipLabel = new JLabel("Helmet: " + (character.getHelmet() != null ? 
                character.getHelmet().getName() : "None"));
        }
        
        statsPanel.add(attackDefLabel);
        statsPanel.add(healthLabel);
        statsPanel.add(equipLabel);
        
        panel.add(statsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void performAttack(double multiplier, String attackType) {
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
        
        // Execute attack using character methods rather than direct calculation
        if (attackType.equals("Light")) {
            attacker.attack(defender, new LightAttackStrategy()); // Use character attack method
        } else {
            attacker.attack(defender, new HeavyAttackStrategy()); // Use character heavy attack method (if available)
            // If heavyAttack method doesn't exist, fall back to attack with multiplier
        }
        
        // Calculate how much damage was done
        int damageDealt = initialHealth - defender.getHealthPoints();
        
        // Decrease weapon durability through character method
        
        // Update weapon durability bar
        updateWeaponDurabilityBar();

        // Update helmet durability bar
        updateHelmetDurabilityBar();
        
        // Check if weapon breaks
        if (attacker.getWeapon() != null && attacker.getWeapon().getDurability() <= 0) {
            logEvent(attacker.getName() + "'s " + attacker.getWeapon().getName() + " broke from use!");
            attacker.setWeapon(null);
            weaponStatusLabel.setText("Weapon: BROKEN");
            
            // Repaint attacker character panel to update visual representation
            if (attackerRenderPanel != null) {
                attackerRenderPanel.repaint();
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
    
    private void performMultipleAttacks(int count) {
        // Ensure we're in testing mode to prevent auto-transitions
        TestUtils.setTestingMode(true);
        
        for (int i = 0; i < count; i++) {
            // Only perform if characters are still alive
            if (attacker.isAlive() && defender.isAlive() && attacker.getWeapon() != null) {
                // Randomly choose light or heavy attack
                boolean isLightAttack = random.nextBoolean();
                String attackType = isLightAttack ? "Light" : "Heavy";
                
                // Reset to idle state before each attack to ensure consistent behavior
                attacker.setIdleState();
                
                // Perform attack
                performAttack(isLightAttack ? 1.0 : 2.0, attackType);
                
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
        
        logEvent("------------------------------------------");
        logEvent("Attack sequence completed. Stats:");
        logEvent("Critical hits: " + criticalHits + "/" + totalHits + 
                " (" + df.format((double)criticalHits/totalHits * 100) + "%)");
        logEvent("Expected critical rate: " + 
                 df.format(WeaponType.SWORD.getCriticalChance() * 100) + "%");
        logEvent("------------------------------------------");
    }
    
    private void resetTest() {
        // Reset character health and equipment
        attacker = new TestCharacter("Attacker", 20.0, 10.0);
        defender = new TestCharacter("Defender", 15.0, 20.0);
        
        // Ensure testing mode is active
        TestUtils.setTestingMode(true);
        
        // Set characters to initial idle state
        attacker.setIdleState();
        defender.setIdleState();
        
        // Equip weapon and helmet using character methods
        BaseWeapon sword = new BaseWeapon(WeaponType.SWORD);
        BaseHelmet helmet = new BaseHelmet(HelmetType.DEMON_HELMET);
        
        attacker.setWeapon(sword);
        defender.setHelmet(helmet);
        
        // Reset statistics
        totalHits = 0;
        criticalHits = 0;
        
        // Update UI elements
        updateHealthBars();
        updateWeaponDurabilityBar();
        updateHelmetDurabilityBar();
        updateCriticalHitStats();
        
        weaponStatusLabel.setText("Weapon: " + attacker.getWeapon().getName() + " (OK)");
        helmetStatusLabel.setText("Helmet: " + defender.getHelmet().getName() + " (OK)");
        
        // Log reset
        logEvent("------------------------------------------");
        logEvent("Test reset - characters and equipment restored");
        logEvent("------------------------------------------");
        
        // Repaint character panels
        if (attackerRenderPanel != null) {
            attackerRenderPanel.repaint();
        }
        if (defenderRenderPanel != null) {
            defenderRenderPanel.repaint();
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
            weaponDurabilityBar.setString(attacker.getWeapon().getDurability() + " uses left");
        } else {
            weaponDurabilityBar.setValue(0);
            weaponDurabilityBar.setString("BROKEN");
        }
    }
    
    private void updateHelmetDurabilityBar() {
        if (defender.getHelmet() != null) {
            helmetDurabilityBar.setMaximum(HelmetType.DEMON_HELMET.getDurability());
            helmetDurabilityBar.setValue(defender.getHelmet().getDurability());
            helmetDurabilityBar.setString(defender.getHelmet().getDurability() + " uses left");
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