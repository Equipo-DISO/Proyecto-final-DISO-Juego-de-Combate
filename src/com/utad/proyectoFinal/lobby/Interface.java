package com.utad.proyectoFinal.lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

public class Interface extends JFrame {
    private JLabel nombreLabel;
    private JLabel imagenJugador;
    private ImageIcon iconoJugador = new ImageIcon("Files/img/Verde.png");
    private JPanel listaBotsPanel;
    private ArrayList<JLabel> bots = new ArrayList<>();

    public Interface() {
        setTitle("Interfaz de Jugador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con imagen y nombre
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Imagen del jugador
        Image imagenEscalada = iconoJugador.getImage().getScaledInstance(82, 90, Image.SCALE_SMOOTH);
        iconoJugador = new ImageIcon(imagenEscalada);
        
        imagenJugador = new JLabel(iconoJugador, SwingConstants.CENTER);
        imagenJugador.setPreferredSize(new Dimension(100, 100));

        /*imagenJugador = new JLabel("👤", SwingConstants.CENTER);
        imagenJugador.setPreferredSize(new Dimension(100, 100));
        imagenJugador.setFont(new Font("SansSerif", Font.PLAIN, 40));*/
        panelSuperior.add(imagenJugador, BorderLayout.WEST);

        // Nombre del jugador (editable al hacer clic)
        nombreLabel = new JLabel("Juanito coscunero", SwingConstants.LEFT);
        nombreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        nombreLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nombreLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog("Introduce un nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    nombreLabel.setText(nuevoNombre);
                }
            }
        });
        panelSuperior.add(nombreLabel, BorderLayout.CENTER);

        // Paleta de colores
        JPanel colorPanel = new JPanel(new GridLayout(2, 4));
        String[] colores = {"ROJO", "AZUL", "ROSA", "NEGRO", "AMARILLO", "VERDE", "Naranja", "BLANCO"};
        String[] valoresColores = {"Rojo", "Azul", "Rosa", "Negro", "Amarillo", "Verde", "Naranja", "Blanco"};

        for (int i = 0; i < colores.length; i++) {
            JButton colorBtn = new JButton(colores[i]);
            String path = "Files/img/"+valoresColores[i]+".png";
            colorBtn.addActionListener(e ->{
                iconoJugador = new ImageIcon(path);

                Image imagen = iconoJugador.getImage().getScaledInstance(82, 90, Image.SCALE_SMOOTH);
                iconoJugador = new ImageIcon(imagen);
                
                imagenJugador = new JLabel(iconoJugador, SwingConstants.CENTER);
                imagenJugador.setPreferredSize(new Dimension(100, 100));
            });   
            colorPanel.add(colorBtn);
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
        JButton botonAgregar = new JButton("+");
        JButton botonEliminar = new JButton("-");

        botonAgregar.addActionListener(e -> {
            JLabel nuevoBot = new JLabel("🤖 Bot " + (bots.size() + 1));
            nuevoBot.setFont(new Font("SansSerif", Font.PLAIN, 16));
            bots.add(nuevoBot);
            listaBotsPanel.add(nuevoBot);
            listaBotsPanel.revalidate();
            listaBotsPanel.repaint();
        });

        botonEliminar.addActionListener(e -> {
            if (!bots.isEmpty()) {
                JLabel bot = bots.remove(bots.size() - 1);
                listaBotsPanel.remove(bot);
                listaBotsPanel.revalidate();
                listaBotsPanel.repaint();
            }
        });

        panelBotones.add(botonAgregar);
        panelBotones.add(botonEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interface::new);
    }
}

