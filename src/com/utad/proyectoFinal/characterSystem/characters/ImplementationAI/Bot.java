package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class Bot extends BaseCharacter {

    private static final String BOTNAME = "B.O.T";
    private static final Double ATTACK = 10.00;
    private static final Double DEFENSE = 10.0;

    private GenericTile currentPosition;
    private BotAI botAi;
    // private BaseHelmet currentHelmet; Heredadas de BaseCharacter
    // private BaseWeapon currentWeapon; Heredadas de BaseCharacter

    private MapGenerator map;
    public Bot() {
        this(BOTNAME, ATTACK, DEFENSE);
    }

    public Bot(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack, baseDefense);
    }

    public void BotMove() {
        if (botAi != null) {
            botAi.executeTurn(this);
        } else {
            System.out.println("AI no definida para este bot.");
        }
    }


    //public String getSpecialAbility() {
    //    return "";


    public GenericTile getCurrentPosition() {
    return currentPosition;
}

    public void setCurrentPosition(GenericTile currentPosition) {
    this.currentPosition = currentPosition;
}

    public void setBotAI(BotAI botAi) {
    this.botAi = botAi;
}

    public BotAI getBotAI() {
    return botAi;
}

    public BaseHelmet getCurrentHelmet() {
        return getHelmet();
    }

    //public void setCurrentHelmet(BaseHelmet currentHelmet) {;}

    /*public BaseWeapon getCurrentWeapon() {
        return getWeapon();
    }
    */
    //public void setCurrentWeapon(BaseWeapon currentWeapon) {
    //this.currentWeapon = currentWeapon;
    //}

    public MapGenerator getMap(){
        return this.map;
    }

    public void setMap(MapGenerator map){
        this.map = map;
    }
}
