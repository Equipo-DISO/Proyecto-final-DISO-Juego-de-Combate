/**
 * El paquete {@code images} implementa la representación visual de los personajes y su equipamiento.
 * <p>
 * Este subpaquete utiliza el patrón de diseño Decorator para componer dinámicamente las imágenes
 * de los personajes con su equipamiento (armas y cascos):
 * <ul>
 *   <li>{@code CharacterImage}: Interfaz base para todas las imágenes de personajes</li>
 *   <li>{@code BaseCharacterImage}: Implementación concreta para la imagen base de un personaje</li>
 *   <li>{@code EquipmentDecorator}: Clase abstracta base para los decoradores de equipamiento</li>
 *   <li>{@code WeaponDecorator}: Añade una imagen de arma a un personaje</li>
 *   <li>{@code HelmetDecorator}: Añade una imagen de casco a un personaje</li>
 * </ul>
 * </p>
 * <p>
 * Este diseño permite añadir flexiblemente equipamiento visual a los personajes en tiempo de ejecución,
 * manteniendo una representación gráfica coherente con el equipamiento actual del personaje.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
package com.utad.proyectoFinal.characterSystem.images;