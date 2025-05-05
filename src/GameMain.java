import java.util.LinkedList;
import java.util.List;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.gameManagement.GameContext;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.ui.lobby.MenuInterface;

public class GameMain 
{
    public static void main(String[] args) 
    {
        GameContext gameContext = GameContext.getInstance();

        MenuInterface interfaceMain = new MenuInterface();
        interfaceMain.showInterface();
        interfaceMain.waitTillClose();


       
        LinkedList<Bot> bots = interfaceMain.getBotList();
        BaseCharacter player = interfaceMain.getPlayerCharacter();
        
        player.addObserver(gameContext);
        bots.forEach(b -> { b.addObserver(gameContext); });

        gameContext.setInitialCharacters(bots.size() + 1);

        MapGenerator instance = MapGenerator.getInstance(1500, 0, 6, bots.size() + 1, bots, player);
        instance.displayMap();
    }

}