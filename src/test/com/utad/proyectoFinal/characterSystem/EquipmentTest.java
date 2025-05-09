package test.com.utad.proyectoFinal.characterSystem;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.items.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.items.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Test class that demonstrates equipping and unequipping items on a character.
 */
public class EquipmentTest {

    public static final String ATTACK = "Attack: ";
    public static final String DEFENSE = "Defense: ";

    public static void main(String[] args) {
        System.out.println("Starting Equipment Test");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        
        // Initialize test frame
        JFrame frame = new JFrame("Character Equipment Test");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Create character panel
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
        characterPanel.setBackground(Color.WHITE);
        
        // Load character image
        Image characterImage = loadImage("Files/img/OrangeGuy.png");
        if (characterImage == null) {
            System.out.println("Failed to load character image. Creating blank image.");
            characterImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        }
        
        // Create test character
        final TestCharacter character = new TestCharacter(
            "Equipment Tester", 
            DefaultAttributes.ATTACK, 
            characterImage
        );
        
        // Equipment status panel
        final JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(3, 2));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Character Status"));
        
        final JLabel nameLabel = new JLabel("Name: " + character.getName());
        final JLabel attackLabel = new JLabel(ATTACK + character.getBaseAttack());
        final JLabel defenseLabel = new JLabel(DEFENSE + "0.0"); // Default defense value
        final JLabel weaponLabel = new JLabel("Weapon: None");
        final JLabel helmetLabel = new JLabel("Helmet: None");
        JLabel spaceLabel = new JLabel("");
        
        statusPanel.add(nameLabel);
        statusPanel.add(attackLabel);
        statusPanel.add(defenseLabel);
        statusPanel.add(weaponLabel);
        statusPanel.add(helmetLabel);
        statusPanel.add(spaceLabel);
        
        // Character rendering panel with equipment
        final JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                
                // Clear panel
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw character in center
                int x = (getWidth() - 100) / 2;
                int y = (getHeight() - 150) / 2;
                character.render(g2d, x, y);
            }
        };
        renderPanel.setPreferredSize(new Dimension(400, 300));
        renderPanel.setBorder(BorderFactory.createTitledBorder("Character View"));
        
        // Control buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Equipment Controls"));
        
        final BaseWeapon sword = new BaseWeapon(WeaponType.SPEAR);
        final BaseHelmet helmet = new BaseHelmet(HelmetType.SIMPLE_HELMET);
        
        JButton addWeaponBtn = new JButton("Add Weapon");
        addWeaponBtn.addActionListener(e -> {
            character.setWeapon(sword);
            weaponLabel.setText("Weapon: " + sword.getName());
            attackLabel.setText(ATTACK +
                (character.getBaseAttack() + (character.getWeapon() != null ? character.getWeapon().getDamage() : 0)));
            renderPanel.repaint();
            System.out.println("Added weapon: " + sword.getName());
        });
        
        JButton removeWeaponBtn = new JButton("Remove Weapon");
        removeWeaponBtn.addActionListener(e -> {
            character.setWeapon(null);
            weaponLabel.setText("Weapon: None");
            attackLabel.setText(ATTACK + character.getBaseAttack());
            renderPanel.repaint();
            System.out.println("Removed weapon");
        });
        
        JButton addHelmetBtn = new JButton("Add Helmet");
        addHelmetBtn.addActionListener(e -> {
            character.setHelmet(helmet);
            helmetLabel.setText("Helmet: " + helmet.getName());
            // Update defense label with helmet defense value
            defenseLabel.setText(DEFENSE + 
                (character.getHelmet() != null ? character.getHelmet().getDefense() : 0.0));
            renderPanel.repaint();
            System.out.println("Added helmet: " + helmet.getName());
        });
        
        JButton removeHelmetBtn = new JButton("Remove Helmet");
        removeHelmetBtn.addActionListener(e -> {
            character.setHelmet(null);
            helmetLabel.setText("Helmet: None");
            defenseLabel.setText(DEFENSE + "0.0"); // Reset to default defense
            renderPanel.repaint();
            System.out.println("Removed helmet");
        });
        
        controlPanel.add(addWeaponBtn);
        controlPanel.add(addHelmetBtn);
        controlPanel.add(removeWeaponBtn);
        controlPanel.add(removeHelmetBtn);
        
        // Assemble the UI
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(renderPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        // Show frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
    
    /**
     * Utility method to load an image from a file path
     */
    private static Image loadImage(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return ImageIO.read(file);
            } else {
                System.err.println("Image file not found: " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}