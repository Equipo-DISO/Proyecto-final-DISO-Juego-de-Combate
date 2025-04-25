// This class has been replaced by the WeaponType enum.
// Use BaseWeapon with WeaponType.DAGGER instead.
// Example: new BaseWeapon(WeaponType.DAGGER)
package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

@Deprecated
public class Dagger extends BaseWeapon {
    public static final String NAME = "Dagger";
    public static final Double DAMAGE = 10.0;
    public static final Double CRITICAL_CHANCE = 0.20;
    public static final Double CRITICAL_DAMAGE = 1.20;

    public Dagger() {
        super(WeaponType.DAGGER);
    }

    public Dagger(String name, Double damage) {
        super(name, damage, CRITICAL_CHANCE, CRITICAL_DAMAGE);
    }
}