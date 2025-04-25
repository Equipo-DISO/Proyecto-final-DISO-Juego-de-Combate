// This class has been replaced by the WeaponType enum.
// Use BaseWeapon with WeaponType.SWORD instead.
// Example: new BaseWeapon(WeaponType.SWORD)
package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

@Deprecated
public class Sword extends BaseWeapon {
    public static final String NAME = "Sword";
    public static final Double DAMAGE = 20.0;
    public static final Double CRITICAL_CHANCE = 0.10;
    public static final Double CRITICAL_DAMAGE = 1.50;

    public Sword() {
        super(WeaponType.SWORD);
    }

    public Sword(String name, Double damage) {
        super(name, damage, CRITICAL_CHANCE, CRITICAL_DAMAGE);
    }
}