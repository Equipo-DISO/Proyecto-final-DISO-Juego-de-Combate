# Investigar sobre soluciones a problemas de estados

* Acoplamiento fuerte: Los estados tienen acceso directo al personaje y a otros estados a través de él, lo que puede dificultar las pruebas unitarias y aumentar la dependencia entre clases. 
* Manejo de errores limitado: Algunos métodos lanzan UnsupportedOperationException en lugar de manejar adecuadamente los casos no soportados. 
* Transiciones de estado inconsistentes: Algunos estados manejan las transiciones en updateState, mientras que otros lo hacen en los métodos de acción.