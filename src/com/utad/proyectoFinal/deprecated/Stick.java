// This class has been replaced by the WeaponType enum.
// Use BaseWeapon with WeaponType.STICK instead.
// Example: new BaseWeapon(WeaponType.STICK)
package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

@Deprecated
public class Stick extends BaseWeapon {
    public static final String NAME = "Stick";
    public static final Double DAMAGE = 5.0;
    public static final Double CRITICAL_CHANCE = 0.10;
    public static final Double CRITICAL_DAMAGE = 1.10;

    public Stick() {
        super(WeaponType.STICK);
    }

    public Stick(String name, Double damage) {
        super(name, damage, CRITICAL_CHANCE, CRITICAL_DAMAGE);
    }
}