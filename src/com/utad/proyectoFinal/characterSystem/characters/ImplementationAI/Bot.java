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
    private BaseHelmet currentHelmet;
    private BaseWeapon currentWeapon;
    private MapGenerator x;

    public Bot() {
        this(BOTNAME, ATTACK, DEFENSE);
    }

    public Bot(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack, baseDefense);
    }

    public void BotMove(GenericTile random) {
        if (botAi != null) {
            botAi.decideNextMove(this.getCurrentPosition());
        } else {
            System.out.println("AI no definida para este bot.");
        }
    }

    @Override
    public String getSpecialAbility() {
        return "";
    }

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
        return currentHelmet;
    }

    public void setCurrentHelmet(BaseHelmet currentHelmet) {
        this.currentHelmet = currentHelmet;
    }

    public BaseWeapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(BaseWeapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
}
