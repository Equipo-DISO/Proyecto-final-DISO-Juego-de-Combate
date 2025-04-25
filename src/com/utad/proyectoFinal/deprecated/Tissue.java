// This class has been replaced by the WeaponType enum.
// Use BaseWeapon with WeaponType.TISSUE instead.
// Example: new BaseWeapon(WeaponType.TISSUE)
package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.WeaponType;

@Deprecated
public class Tissue extends BaseWeapon {
    public static final String NAME = "Tissue";
    public static final Double DAMAGE = 2.0;
    public static final Double CRITICAL_CHANCE = 0.05;
    public static final Double CRITICAL_DAMAGE = 1.05;

    public Tissue() {
        super(WeaponType.TISSUE);
    }

    public Tissue(String name, Double damage) {
        super(name, damage, CRITICAL_CHANCE, CRITICAL_DAMAGE);
    }
}