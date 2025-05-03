package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.mapa.MapObject;
public class RandomItemFactory implements RandomItemProducer {

    // TO-DO factory items
    @Override
    public MapObject giveRandomObject() {
        Integer coinflip = (int)(Math.random()*100); // coinflip de 0 a 100
        Integer subCoinflip = (int) (Math.random() * 3) + 1; // numero del 1 al 4
        Integer subCoinflip2 = (int) (Math.random() * 4) + 1; // numero del 1 al 4
        Integer subCoinflip3 = (int) (Math.random() * 5) + 1; //numero del 1 al 5
        if(coinflip < 33){
            switch(subCoinflip){
                case 1:
                    return new BaseHelmet(HelmetType.NORMAL_HELMET);
                case 2:
                    return new BaseHelmet(HelmetType.SIMPLE_HELMET);
                case 3:
                    return new BaseHelmet(HelmetType.DEMON_HELMET);
                default:
                    return null;
            }
        }else if(coinflip < 66 && coinflip > 33){
            switch(subCoinflip2) {
                case 1:
                    return new BaseWeapon(WeaponType.STICK);
                case 2:
                    return new BaseWeapon(WeaponType.KNIFE);
                case 3:
                    return new BaseWeapon(WeaponType.AXE);
                case 4:
                    return new BaseWeapon(WeaponType.SWORD);
                case 5:
                    return new BaseWeapon(WeaponType.SPEAR);
                default:
                    return null;
            }
        }else if(coinflip > 66 && coinflip < 100 ){
            switch(subCoinflip) {
                case 1:
                    return new GenericItem(GenericItemType.HEALTH_POTION);
                case 2:
                    return new GenericItem(GenericItemType.MANA_POTION);
                case 3:
                    return new GenericItem(GenericItemType.UPGRADE_HEALTH_POTION);
                case 4:
                    return new GenericItem(GenericItemType.UPGRADE_MANA_POTION);
                case 5:
                    return new Chest(this);
                default:
                    return null;
            }
        }
        return null;
    }
}
