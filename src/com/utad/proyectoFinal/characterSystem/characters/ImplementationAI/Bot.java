package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;

public class Bot extends BaseCharacter {

    private static final String BOTNAME = "B.O.T";
    private static final Double ATTACK = 10.00;
    private static final Double DEFENSE = 10.0;
    private Tile currentPosition;
    private BotAI botAi;
    private BaseHelmet currentHelmet;
    private BaseWeapon currentWeapon;

    public Bot() {
        this(BOTNAME, ATTACK, DEFENSE);
    }

    public Bot(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack, baseDefense);
        this.botAi = new BotAI(); //importante para intanciar su cerebro -> composición fuerte como coche y motor
    }

    public void BotMove(Tile random){
        // 1.- dada tu posición actual, te doy un tile random al que puedas ir
        // 2.- dada tu posición actual, te doy una lista de tiles que conforman un camino hasta un punto de interés,
        // dentro del área de visión que tengas, si no hay punto de interés
        // te devolveré una lista con el tile de la primera función
        if(!random.isOcuppated() || random.){
            setCurrentPosition(random);
            if(random.getObject().instanceOf(BaseHelmet)){
                set
            }
        }


    }
    @Override
    public String getSpecialAbility() {
        return "";
    }

    public Tile getCurrentPosition() {
        return currentPosition;
    }

    //para setear su casilla ya que a la hora de instanciarlos no sabremos que casilla darle
    public void setCurrentPosition(Tile currentPosition) {
        this.currentPosition = currentPosition;
    }
}
