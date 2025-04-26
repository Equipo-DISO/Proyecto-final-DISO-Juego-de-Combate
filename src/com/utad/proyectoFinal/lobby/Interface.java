package com.utad.proyectoFinal.lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Interface extends JFrame {
    private JLabel nombreLabel;
    private SimplifiedImage playerSimplifiedImage = new SimplifiedImage(Path.PLAYER.getPath(Path.ColorEnum.GREEN), 92, 110);
    private JLabel playerImage = playerSimplifiedImage.generateJLabel(Path.PLAYER.getDefWidth(), Path.PLAYER.getDefHeight());
    private JPanel listaBotsPanel;
    private ArrayList<JLabel> bots = new ArrayList<>();
    
    public Interface() {
        setTitle("Juego de Combate");
        
        // TODO: Cambiar el icono de la ventana
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con imagen y nombre
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Imagen del jugador
        panelSuperior.add(playerImage, BorderLayout.WEST);

        // Nombre del jugador (editable al hacer clic)
        nombreLabel = new JLabel("Player", SwingConstants.LEFT);
        nombreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        nombreLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nombreLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog("Introduce un nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    nombreLabel.setText(nuevoNombre);
                }
            }
            public void mouseEntered(MouseEvent e) {
                nombreLabel.setFont(nombreLabel.getFont().deriveFont(Font.ITALIC | Font.BOLD));
            }
            public void mouseExited(MouseEvent e) {
                nombreLabel.setFont(nombreLabel.getFont().deriveFont(Font.BOLD));
            }
        });
        panelSuperior.add(nombreLabel, BorderLayout.CENTER);

        // Paleta de colores
        JPanel colorPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < Path.colorsList.length; i++){
            String playerPath = Path.PLAYER.getPath(Path.colorsList[i]);
            String colorPath = Path.COLOR.getPath(Path.colorsList[i]);
            JLabel botton = new SimplifiedImage(colorPath, 100, 50).generateJLabel(80, 40);
            botton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    playerSimplifiedImage.setPath(playerPath);
                    playerImage.setIcon(playerSimplifiedImage.generateImageIcon(Path.PLAYER.getDefWidth(), Path.PLAYER.getDefHeight()));
                }
                public void mouseEntered(MouseEvent e) {
                    botton.setIcon(new SimplifiedImage(colorPath, 100, 50).generateImageIcon(92, 46));
                }
                public void mouseExited(MouseEvent e) {
                    botton.setIcon(new SimplifiedImage(colorPath, 100, 50).generateImageIcon(80, 40));
                }
            });
            
            colorPanel.add(botton);
        }

        panelSuperior.add(colorPanel, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para la lista de bots (modular y redimensionable)
        listaBotsPanel = new JPanel();
        listaBotsPanel.setLayout(new BoxLayout(listaBotsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listaBotsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones (+ y -)
        JPanel panelBotones = new JPanel();
        panelBotones.setPreferredSize(new Dimension(110, 60));
        JLabel addButton = new JLabel(
            new SimplifiedImage("Files/img/AddButton.png", 100, 50)
                .generateImageIcon(80, 40));

        JLabel removeButton = new JLabel(
            new SimplifiedImage("Files/img/RemoveButton.png", 100, 50)
                .generateImageIcon(80, 40));

        addButton.setPreferredSize(new Dimension(100, 50));
        removeButton.setPreferredSize(new Dimension(100, 50));

        addButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JLabel nuevoBot = new JLabel("🤖 Bot " + (bots.size() + 1));
                nuevoBot.setFont(new Font("SansSerif", Font.PLAIN, 16));
                bots.add(nuevoBot);
                listaBotsPanel.add(nuevoBot);
                listaBotsPanel.revalidate();
                listaBotsPanel.repaint();
            }
            public void mouseEntered(MouseEvent e) {
                addButton.setIcon(new SimplifiedImage("Files/img/AddButton.png", 100, 50).generateImageIcon(92, 46));
            }
            public void mouseExited(MouseEvent e) {
                addButton.setIcon(new SimplifiedImage("Files/img/AddButton.png", 100, 50).generateImageIcon(80, 40));
            }
        });

        removeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!bots.isEmpty()) {
                    JLabel bot = bots.remove(bots.size() - 1);
                    listaBotsPanel.remove(bot);
                    listaBotsPanel.revalidate();
                    listaBotsPanel.repaint();
                }
            }
            public void mouseEntered(MouseEvent e) {
                removeButton.setIcon(new SimplifiedImage("Files/img/RemoveButton.png", 100, 50).generateImageIcon(92, 46));
            }
            public void mouseExited(MouseEvent e) {
                removeButton.setIcon(new SimplifiedImage("Files/img/RemoveButton.png", 100, 50).generateImageIcon(80, 40));
            }
        });

        panelBotones.add(addButton);
        panelBotones.add(removeButton);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interface::new);
    }
}

