package com.utad.proyectoFinal.ui.lobby;

import javax.swing.*;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.RandomBotFactory;
import com.utad.proyectoFinal.ui.Interface;
import com.utad.proyectoFinal.ui.InterfacePath;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MenuInterface extends JFrame implements Interface {
    public static final Integer MAXBOTS = 7;
    public static final String FILES_IMG_PLAY_BUTTON_PNG = "Files/img/PlayButton.png";

    private JLabel nombreLabel;

    private SimplifiedImage playerSimplifiedImage = new SimplifiedImage(InterfacePath.PLAYER.getPath(InterfacePath.ColorEnum.GREEN), 92, 110);
    private JLabel playerImage = playerSimplifiedImage.generateJLabel(InterfacePath.PLAYER.getDefWidth(), InterfacePath.PLAYER.getDefHeight());
    private String playerImagePath = InterfacePath.PLAYER.getPath(InterfacePath.ColorEnum.GREEN);
    
    private JPanel addBotPanel;
    private JPanel listaBotsPanel;
    private ArrayList<JPanel> bots = new ArrayList<>();
    
    public MenuInterface()                              { this("Juego de Combate", 1000, 500); }
    public MenuInterface(String title)                  { this(title, 1000, 500); }
    public MenuInterface(Integer width, Integer height) { this("Juego de Combate", width, height); }

    public MenuInterface(String title, Integer width, Integer height){

        setTitle(title);
        setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
        
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
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
            @Override
            public void mouseClicked(MouseEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog("Introduce un nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    nombreLabel.setText(nuevoNombre);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nombreLabel.setFont(nombreLabel.getFont().deriveFont(Font.ITALIC | Font.BOLD));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nombreLabel.setFont(nombreLabel.getFont().deriveFont(Font.BOLD));
            }
        });
        panelSuperior.add(nombreLabel, BorderLayout.CENTER);

        // Paleta de colores
        JPanel colorPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < InterfacePath.colorsList.length; i++){
            String playerPath = InterfacePath.PLAYER.getPath(InterfacePath.colorsList[i]);
            String colorPath = InterfacePath.COLOR.getPath(InterfacePath.colorsList[i]);
            JLabel botton = new SimplifiedImage(colorPath, 100, 50).generateJLabel(80, 40);
            botton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    playerImagePath = playerPath;
                    playerSimplifiedImage.setPath(playerPath);
                    playerImage.setIcon(playerSimplifiedImage.generateImageIcon(InterfacePath.PLAYER.getDefWidth(), InterfacePath.PLAYER.getDefHeight()));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    botton.setIcon(new SimplifiedImage(colorPath, 100, 50).generateImageIcon(92, 46));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    botton.setIcon(new SimplifiedImage(colorPath, 100, 50).generateImageIcon(80, 40));
                }
            });
            
            colorPanel.add(botton);
        }

        panelSuperior.add(colorPanel, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para la lista de bots
        listaBotsPanel = new JPanel();
        listaBotsPanel.setLayout(new BoxLayout(listaBotsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listaBotsPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        addBotPanel = new JPanel(new BorderLayout());
        JLabel addBot = new JLabel("➕ Añadir Bot", SwingConstants.LEFT);
        addBotPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addBotPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
        addBotPanel.add(addBot, BorderLayout.WEST);

        addBotPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewBot();
            }
        });

        createNewBot(); // bot1
        listaBotsPanel.add(addBotPanel);
        
        // Panel de Jugar
        JPanel panelBotones = new JPanel();
        panelBotones.setPreferredSize(new Dimension(110, 60));
        JLabel playButton = new JLabel(new SimplifiedImage(FILES_IMG_PLAY_BUTTON_PNG, 100, 50).generateImageIcon(90, 45), SwingConstants.CENTER);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hideInterface();                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setIcon(new SimplifiedImage(FILES_IMG_PLAY_BUTTON_PNG, 100, 50).generateImageIcon(100, 50));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setIcon(new SimplifiedImage(FILES_IMG_PLAY_BUTTON_PNG, 100, 50).generateImageIcon(90, 45));
            }
        });

        panelBotones.add(playButton, SwingConstants.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void createNewBot() {
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
            @Override
            public void mouseClicked(MouseEvent e) {
                listaBotsPanel.remove(nuevoBotPanel);
                bots.remove(nuevoBotPanel);
                if (bots.size() < MAXBOTS) addBotPanel.setVisible(true);
                if (bots.isEmpty()) createNewBot();

                listaBotsPanel.revalidate();
                listaBotsPanel.repaint();
            }
        });

        botName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog("Introduce un nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    botName.setText(nuevoNombre);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                botName.setFont(botName.getFont().deriveFont(Font.ITALIC));
                if (bots.size() > 1) nuevoBotPanel.add(removeButton, BorderLayout.EAST);
            }
        });

        nuevoBotPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                botName.setFont(botName.getFont().deriveFont(Font.PLAIN));
                nuevoBotPanel.remove(removeButton);
            }
        });

        listaBotsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                botName.setFont(botName.getFont().deriveFont(Font.PLAIN));
                nuevoBotPanel.remove(removeButton);
            }
        });

        nuevoBotPanel.add(botImg, BorderLayout.WEST);
        nuevoBotPanel.add(botName, BorderLayout.CENTER);
        bots.add(nuevoBotPanel);

        if (bots.size() >= MAXBOTS) addBotPanel.setVisible(false);

        listaBotsPanel.add(nuevoBotPanel, listaBotsPanel.getComponentCount() - 1);
        listaBotsPanel.revalidate();
        listaBotsPanel.repaint();
    }


    public void showInterface(){
        setVisible(true);
    }

    public void waitTillClose(){
        while (isVisible()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
    }
    
    public void hideInterface() {
        setVisible(false);
    }

    public List<String> getData() {
        List<String> dataList = new ArrayList<>();
        dataList.add(playerImagePath);
        dataList.add(nombreLabel.getText());

        for (JPanel bot : this.bots) {
            JLabel botNameLabel = (JLabel) bot.getComponent(1);
            String botName = botNameLabel.getText();
            dataList.add(botName);
        }

        return dataList;
    }

    public LinkedList<Bot> getBotList(){
        LinkedList<Bot> botList = new LinkedList<Bot>();
        int j = 0;

        for (int i = 0; i < bots.size(); i++){
            String path = InterfacePath.PLAYER.getPath(InterfacePath.colorsList[j++]);
            if (path.equals(playerImagePath))
            {
                path = InterfacePath.PLAYER.getPath(InterfacePath.colorsList[j++]);
            }

            String botName = ((JLabel) bots.get(i).getComponent(1)).getText();
            if (botName.toLowerCase().contains("nig")) path = path.replace(".png", "Dark.png");

            Bot bot = RandomBotFactory.createBot();
            bot.setImage(path);
            bot.setBaseImagePath(path);
            botList.add(bot);
        }

        return botList;
    }

    public LinkedList<String> getBotNames(){
        LinkedList<String> botNames = new LinkedList<String>();

        for (JPanel bot : this.bots) {
            String botName = ((JLabel) bot.getComponent(1)).getText();
            botNames.add(botName);
        }

        return botNames;
    }

    public BaseCharacter getPlayerCharacter() {

        String name = nombreLabel.getText();
            if (name.toLowerCase().contains("nig")) playerImagePath = playerImagePath.replace(".png", "Dark.png");

        BaseCharacter player = new BaseCharacter(name);
        player.setImage(playerImagePath);
        player.setBaseImagePath(playerImagePath);
        return player;
    }
}

