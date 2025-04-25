// This class has been replaced by the WeaponType enum.
// Use BaseWeapon with WeaponType.SPEAR instead.
// Example: new BaseWeapon(WeaponType.SPEAR)
package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

@Deprecated
public class Spear extends BaseWeapon {
    public static final String NAME = "Spear";
    public static final Double DAMAGE = 15.0;
    public static final Double CRITICAL_CHANCE = 0.15;
    public static final Double CRITICAL_DAMAGE = 1.30;

    public Spear() {
        super(WeaponType.SPEAR);
    }

    public Spear(String name, Double damage) {
        super(name, damage, CRITICAL_CHANCE, CRITICAL_DAMAGE);
    }
}