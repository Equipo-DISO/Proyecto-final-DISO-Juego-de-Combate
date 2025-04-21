package characterSystem.characters.states;

import characterSystem.characters.Archer;
import characterSystem.characters.BaseCharacter;
import characterSystem.characters.DefaultAttributes;

public class RetreatingState extends BaseState {

    RetreatingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        Boolean retirada = false;
        Double probabilidadRetirada = DefaultAttributes.COUNTERATTACK_PROBABILITY;

        // Si el ataque es un arquero, se reduce la probabilidad de retirada
        if (opponent instanceof Archer archer) {
            probabilidadRetirada = Math.max(probabilidadRetirada - archer.getAccuracy(), 0);
            System.out.println("El oponente es un arquero muy preciso y redujo tu probabilidad de retirada en "
                    + archer.getAccuracy() + "%");
        }

        // Comprobar si el personaje tiene un escudo y aumentar/disminuir la
        // probabilidad de retirada
        // TODO: Uncomment after shield implementation
//        probabilidadRetirada = Math.max(probabilidadRetirada + character.shield.getProbabilidadEscape(), 0);
//        System.out.println("Las características del escudo dan un modificador de "
//                + character.shield.getProbabilidadEscape() + "% a la probabilidad de retirada, para un total de " + probabilidadRetirada + "%");

        // Calcular la probabilidad de retirada
        if (Math.random() * 100 <= probabilidadRetirada) {
            retirada = true;
        }

        character.setRetreatSuccess(retirada);
    }

    @Override
    public String getName() {
        return "Retreating";
    }
}
