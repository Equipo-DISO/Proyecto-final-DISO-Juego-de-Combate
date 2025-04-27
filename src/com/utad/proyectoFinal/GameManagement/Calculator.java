package com.utad.proyectoFinal.GameManagement;

public class Calculator {
    //instancia de Singleton
    private Calculator calculator;
    //atributos para gestión de cálculos ?!
    // private BaseCharacter caracter; -> caracter contiene por composición info de arma y escudo
    // private Mapa mapa ??
    // ...

    private Calculator(){
     //constructor privado y vacío
    }

    //getInstance devuelve instancia de Calculadora
    public Calculator getInstance(){
        return calculator;
    }
}
