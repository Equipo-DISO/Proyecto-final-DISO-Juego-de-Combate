package test.com.utad.proyectoFinal.characterSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

/**
 * Test class that demonstrates character equipment visualization using Image
 */
public class BufferedImageCharacterTest extends JFrame {
    
    private TestCharacter character;
    private BaseWeapon sword;
    private BaseHelmet helmet;
    private JPanel renderPanel;
    private JLabel characterInfoLabel;
    
    public BufferedImageCharacterTest() {
        super("Character Equipment Test - Image");
        
        // Set up frame properties
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        
        try {
            // Initialize character with a character image
            Image characterImage = loadImage("Files/img/GreenGuy.png");
            character = new TestCharacter("Test Warrior", 
                                           DefaultAttributes.ATTACK,
                                           characterImage);


            
            // Create equipment
            sword = new BaseWeapon(WeaponType.SWORD);
            helmet = new BaseHelmet(HelmetType.SIMPLE_HELMET);
            
            // Set up UI components
            setupUI();
            
            // Center frame on screen
            setLocationRelativeTo(null);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading images: " + e.getMessage(), 
                "Image Load Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void setupUI() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Character info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Character Info"));
        
        characterInfoLabel = new JLabel("Name: " + character.getName());
        characterInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        characterInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel attackLabel = new JLabel("Base Attack: " + character.getBaseAttack());
        attackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel defenseLabel = new JLabel("Base Defense: Depracated");
        defenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(characterInfoLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(attackLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(defenseLabel);
        
        // Character render panel
        renderPanel = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                
                // Set white background
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw character centered
                if (character != null) {
                    character.render((Graphics2D)g, getWidth()/2 - 50, getHeight()/2 - 75);
                }
                
                // Draw equipment status text
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                String weaponText = "Weapon: " + ((character != null && character.getWeapon() != null) ?
                                                  character.getWeapon().getName() : "None");
                String helmetText = "Helmet: " + ((character != null && character.getHelmet() != null) ?
                                                  character.getHelmet().getName() : "None");
                g.drawString(weaponText, 10, 20);
                g.drawString(helmetText, 10, 40);
            }
        };
        renderPanel.setPreferredSize(new Dimension(400, 300));
        renderPanel.setBorder(BorderFactory.createTitledBorder("Character Visualization"));
        
        // Control buttons
        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Equipment Controls"));
        
        JButton addWeaponBtn = new JButton("Add Weapon");
        addWeaponBtn.addActionListener(e -> {
            character.setWeapon(sword);
            updateStatus("Added sword");
        });
        
        JButton removeWeaponBtn = new JButton("Remove Weapon");
        removeWeaponBtn.addActionListener(e -> {
            character.setWeapon(null);
            updateStatus("Removed weapon");
        });
        
        JButton addHelmetBtn = new JButton("Add Helmet");
        addHelmetBtn.addActionListener(e -> {
            character.setHelmet(helmet);
            updateStatus("Added helmet");
        });
        
        JButton removeHelmetBtn = new JButton("Remove Helmet");
        removeHelmetBtn.addActionListener(e -> {
            character.setHelmet(null);
            updateStatus("Removed helmet");
        });
        
        JButton statsBtn = new JButton("Show Stats");
        statsBtn.addActionListener(e -> showStats());
        
        // Add buttons to control panel
        controlPanel.add(addWeaponBtn);
        controlPanel.add(addHelmetBtn);
        controlPanel.add(statsBtn);
        controlPanel.add(removeWeaponBtn);
        controlPanel.add(removeHelmetBtn);
        
        // Assemble main panel
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(renderPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void updateStatus(String message) {
        System.out.println(message);
        characterInfoLabel.setText(character.getName() + " - " + message);
        renderPanel.repaint();
    }
    
    private void showStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("Character: ").append(character.getName()).append("\n\n");
        
        // Attack stats
        stats.append("Attack: ").append(character.getBaseAttack());
        if (character.getWeapon() != null) {
            stats.append(" + ").append(character.getWeapon().getDamage())
                 .append(" = ").append(character.getBaseAttack() + character.getWeapon().getDamage());
        }
        stats.append("\n");
        
        // Defense stats
        stats.append("Defense: ");
        if (character.getHelmet() != null) {
            stats.append(" = ").append(character.getHelmet().getDefense());
        }
        stats.append("\n\n");
        
        // Equipment details
        stats.append("Equipment:\n");
        if (character.getWeapon() != null) {
            stats.append("- ").append(character.getWeapon().getName())
                 .append(": +").append(character.getWeapon().getDamage()).append(" damage")
                 .append(", durability: ").append(character.getWeapon().getDurability())
                 .append("\n");
        } else {
            stats.append("- No weapon equipped\n");
        }
        
        if (character.getHelmet() != null) {
            stats.append("- ").append(character.getHelmet().getName())
                 .append(": +").append(character.getHelmet().getDefense()).append(" defense")
                 .append(", durability: ").append(character.getHelmet().getDurability());
        } else {
            stats.append("- No helmet equipped");
        }
        
        JOptionPane.showMessageDialog(this, stats.toString(), "Character Stats", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private Image loadImage(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        return ImageIO.read(file);
    }
    
    public static void main(String[] args) {
        // Print current working directory for debugging file paths
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            BufferedImageCharacterTest test = new BufferedImageCharacterTest();
            test.setVisible(true);
        });
    }
}