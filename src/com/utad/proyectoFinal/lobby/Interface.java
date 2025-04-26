package com.utad.proyectoFinal.lobby;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Interface extends JFrame {
    private JLabel nombreLabel;
    private SimplifiedImage playerSimplifiedImage = new SimplifiedImage(Path.PLAYER.getPath(Path.ColorEnum.GREEN), 92, 110);
    private JLabel playerImage = playerSimplifiedImage.generateJLabel(Path.PLAYER.getDefWidth(), Path.PLAYER.getDefHeight());
    private JPanel listaBotsPanel;
    private ArrayList<JPanel> bots = new ArrayList<>();
    
    public Interface() {

        setTitle("Juego de Combate");
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(1000, 500);
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
        
        JLabel addBot = new JLabel("➕ Añadir Bot", SwingConstants.LEFT);
        addBot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addBot.setAlignmentX(Component.LEFT_ALIGNMENT);
        addBot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel nuevoBotPanel = new JPanel(new BorderLayout());
                nuevoBotPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                nuevoBotPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                
                JLabel botImg = new SimplifiedImage("Files/img/BotFace.png", 30, 30).generateJLabel(20, 18);

                JLabel botName = new JLabel("Bot " + (bots.size() + 1));
                botName.setFont(new Font("SansSerif", Font.PLAIN, 14));
                botName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                JLabel removeButton = new JLabel("❌", SwingConstants.CENTER);
                removeButton.setPreferredSize(new Dimension(30, 30));
                removeButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        listaBotsPanel.remove(nuevoBotPanel);
                        bots.remove(nuevoBotPanel);
                        listaBotsPanel.revalidate();
                        listaBotsPanel.repaint();
                    }
                });

                botName.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        String nuevoNombre = JOptionPane.showInputDialog("Introduce un nuevo nombre:");
                        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                            botName.setText(nuevoNombre);
                        }
                    }

                    public void mouseEntered(MouseEvent e) {
                        botName.setFont(botName.getFont().deriveFont(Font.ITALIC));
                        nuevoBotPanel.add(removeButton, BorderLayout.EAST);
                    }
                });

                nuevoBotPanel.addMouseListener(new MouseAdapter() {
                    public void mouseExited(MouseEvent e) {
                        botName.setFont(botName.getFont().deriveFont(Font.PLAIN));
                        nuevoBotPanel.remove(removeButton);
                    }
                });

                listaBotsPanel.addMouseListener(new MouseAdapter() {
                    public void mouseExited(MouseEvent e) {
                        botName.setFont(botName.getFont().deriveFont(Font.PLAIN));
                        nuevoBotPanel.remove(removeButton);
                    }
                });

                nuevoBotPanel.add(botImg, BorderLayout.WEST);
                nuevoBotPanel.add(botName, BorderLayout.CENTER);
                bots.add(nuevoBotPanel);

                listaBotsPanel.add(nuevoBotPanel, 0);
                listaBotsPanel.revalidate();
                listaBotsPanel.repaint();
            }
        });

        listaBotsPanel.add(addBot);
        
        // Panel de botones (+ y -)
        JPanel panelBotones = new JPanel();
        panelBotones.setPreferredSize(new Dimension(110, 60));
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interface::new);
    }
}

