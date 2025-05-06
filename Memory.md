# Memoria del Proyecto: Juego de Combate

## Introducción

Este proyecto consiste en un juego de combate por turnos desarrollado en Java, que implementa diversos patrones de diseño y técnicas de programación avanzadas. El juego presenta las siguientes características principales:

### Arquitectura del Sistema

- **Sistema de Personajes**: Incluye personajes jugables y bots controlados por IA, cada uno con atributos, equipamiento, estados y representaciones visuales.
  - `characters/states`: Gestiona los diversos estados de los personajes (Idle, Attacking, Tired, etc.) y sus estrategias asociadas.
  - `characterSystem/tools`: Define y gestiona la creación de objetos (armas, cascos, pociones, cofres) mediante fábricas.
  - `characterSystem/images`: Maneja la representación visual de los personajes y su equipamiento.
- **Sistema de Combate**: Permite diferentes acciones como ataques ligeros, ataques pesados, curación, recuperación de maná y huida. Gestionado principalmente por `CombatManager`.
- **Generación de Mapa**: Utiliza tiles hexagonales para crear un entorno de juego dinámico, gestionado por `MapGenerator`.
- **Inteligencia Artificial**: Diferentes tipos de bots (A, B, C) con comportamientos y estrategias específicas definidas en `characterSystem/characters/implementationAI`.
- **Interfaz de Usuario (UI)**: Compuesta por varios módulos para la interacción con el jugador:
  - `ui/lobby`: Para la configuración inicial del juego (`MenuInterface`).
  - `ui/combat`: Para la visualización e interacción durante los combates (`CombatInterface`).
  - `ui/podium`: Probablemente para mostrar resultados o clasificaciones (`PodiumInterface`).
- **Gestión del Juego**: Orquestado por `GameControllerFacade` y `GameContext`, manejando el flujo general del juego.
- **Pruebas Unitarias**: Se cuenta con un conjunto de pruebas en el directorio `test` para asegurar la correcta funcionalidad de componentes clave como el sistema de personajes y el equipamiento.

### Patrones de Diseño Implementados

- **Singleton**: Utilizado en clases como `GameContext`, `CombatManager` y `MapGenerator`.
- **Factory Method / Static Factory**: Varias clases como `SimpleWeaponFactory`, `SimpleHelmetFactory`, `SimplePotionFactory`, y las fábricas de Bots (`TypeABotFactory`, etc.) se utilizan para la creación de objetos específicos.
- **Abstract Factory**: El patrón `TileFactory` (con `NormalTileFactory` como implementación concreta) se usa para crear familias de objetos `TileAbstract` (como `GenericTile` y `ObstacleTile`).
- **Strategy**: Aplicado en las estrategias de ataque (`AbstractAttackStrategy` con `LightAttackStrategy`, `HeavyAttackStrategy`) y en las estrategias de búsqueda de caminos para los bots (`PathFindingStrategy` con `ClosestEnemyStrategy`, `ClosestLootStrategy`).
- **State**: Gestiona los diferentes estados de los personajes (`CharacterState` con implementaciones como `IdleState`, `AttackingState`, `DeadState`, etc.) durante el combate y la exploración.
- **Observer**: Permite la comunicación entre componentes mediante el patrón Push Model (e.g., `GameContext` observando a `BaseCharacter`).
- **Facade**: Simplifica la interfaz del sistema mediante `GameControllerFacade`.
- **Template Method**:
  - `AbstractAttackStrategy`: Define el esqueleto del algoritmo de ataque en su método `execute`, delegando pasos específicos a subclases.
  - `BotAI`: Define la estructura del turno de un bot en `executeTurn`, con pasos implementados por IA específicas.
- **Decorator**: Utilizado en el sistema de imágenes de personajes (`characterSystem/images`) para añadir dinámicamente equipamiento visual (cascos, armas) a la imagen base del personaje (`CharacterImage`, `EquipmentDecorator`, `HelmetDecorator`, `WeaponDecorator`).

### Mecánicas de Juego

Los personajes pueden realizar varias acciones tanto fuera como dentro del combate:

- **Acciones de Combate**: Ataques ligeros/pesados, curación, recuperación de maná, y huida.
- **Acciones de Exploración**: Búsqueda y recolección de objetos (armas, cascos, pociones y cofres) y combate contra enemigos en el mapa.
- **Sistema de Objetos**: Incluye diversos tipos de objetos consumibles:
  - **Armas**: Diferentes tipos con valores de ataque y durabilidad variables.
  - **Cascos**: Proporcionan protección adicional.
  - **Pociones**: Restauran salud o mejoran estadísticas.
  - **Cofres**: Contenedores que pueden proporcionar diversos objetos al ser abiertos por el jugador.

La IA de los bots está diseñada para tomar decisiones basadas en diferentes criterios como salud, maná, y objetivos estratégicos.

## Análisis de Componentes

### Paquete CharacterSystem

---

#### Índice del paquete

1. Paquete CharacterSystem
   1. Characters
        - Subsistema de Estados (characters/states/)
        - Inteligencia Artificial (characters/implementationAI/)
        - Bot
   2. Subpaquete Strategies
        - Estructura del Sistema de Estrategias
        - Integración con el Resto del Sistema
        - Aplicación de Principios SOLID
   3. Tools
   4. Images

---

El paquete `characterSystem` constituye uno de los núcleos fundamentales del juego, encargándose de toda la lógica relacionada con los personajes, sus estados, comportamientos y equipamiento. Se encuentra estructurado en tres subpaquetes principales:

#### 1. Characters

Este subpaquete implementa la lógica de los personajes del juego:

- **CombatCharacter**: Interfaz que define todas las operaciones que pueden realizar los personajes en combate, sirviendo como contrato para la interacción con el sistema de combate.

- **BaseCharacter**: Clase principal que implementa la interfaz `CombatCharacter`. Utiliza el principio de composición para estructurar sus funcionalidades:
  - **CharacterAttributes**: Gestiona los atributos del personaje (salud, maná, ataque base).
  - **CharacterEquipment**: Maneja el equipamiento (armas y cascos).
  - **CharacterVisualizer**: Controla la representación visual del personaje.

- **Subsistema de Estados (characters/states/)**:
  - Implementa el patrón State para modelar los diferentes estados y comportamientos de los personajes.
  - **CharacterState**: Interfaz que define las transiciones y comportamientos.
  - **BaseState**: Clase abstracta que implementa comportamientos comunes.
  - Estados concretos: `IdleState`, `AttackingState`, `DeadState`, `TiredState`, `HealState`, `GainManaState`, `RetreatingState`, `MovingOnMapState`.
  - Incluye el diagrama de estados en `DiagramaDeEstados.png` para visualizar las transiciones.
  - **StatesList**: Mantiene referencias a todos los estados posibles de un personaje, facilitando las transiciones.
  - **Strategies**: Implementa el patrón Strategy para diferentes tipos de ataques (`LightAttackStrategy` y `HeavyAttackStrategy`).

- **Inteligencia Artificial (characters/implementationAI/)**:
  - Define el comportamiento de los bots mediante diferentes implementaciones de IA.
  - **BotAI**: Clase abstracta base que define el comportamiento común para todos los bots.
  - **TypeABotAI**, **TypeBBotAI**, **TypeCBotAI**: Implementaciones específicas con diferentes estilos de juego.
  - **CombatActionType**: Enumera las posibles acciones en combate.
  - **BotActionType**: Enumera las posibles acciones de exploración.
  - **Bot**: Clase que representa un personaje controlado por IA, extiende BaseCharacter.

#### Subpaquete Strategies: Sistema de Estrategias de Ataque

El subpaquete `characters/states/strategies` implementa un sistema sofisticado para gestionar diferentes tipos de ataques en el juego, combinando los patrones de diseño Strategy y Template Method.

##### Estructura del Sistema de Estrategias

1. **AttackStrategy (Interfaz):**
   - Define el contrato para todas las estrategias de ataque con los siguientes métodos:
     - `String getName()`: Obtiene el nombre identificativo del ataque.
     - `double calculateDamage(BaseCharacter attacker)`: Calcula el daño que causará el ataque.
     - `int calculateManaCost()`: Determina el coste de maná del ataque.
     - `boolean calculateHitSuccess(BaseCharacter attacker)`: Calcula la probabilidad de acierto.
     - `void execute(BaseCharacter attacker, BaseCharacter target)`: Método de alto nivel que orquesta el ataque.
     - `int calculateDurabilityCost()`: Determina cuánto se desgasta el arma con el ataque.

2. **AbstractAttackStrategy (Clase Abstracta):**
   - Implementa el patrón Template Method definiendo el flujo del ataque:
     1. Verificar que atacante y objetivo estén vivos
     2. Validar y consumir el maná requerido
     3. Calcular si el ataque impacta
     4. Calcular y aplicar el daño
   - Proporciona métodos auxiliares para:
     - Mensajes de golpes críticos
     - Aplicación de daño
     - Gestión de fallos

3. **Estrategias Concretas:**

   a. **LightAttackStrategy:**
   - Ataque rápido y preciso
   - 90% probabilidad de acierto
   - Consume 7 puntos de maná
   - Daño base normal
   - Desgaste mínimo del arma

   b. **HeavyAttackStrategy:**
   - Ataque potente pero impreciso
   - 60% probabilidad de acierto  
   - Consume 10 puntos de maná
   - Doble daño base
   - Mayor desgaste del arma

##### Integración con el Resto del Sistema

- El sistema de estrategias se integra con el singleton `Calculator` (del paquete `gameManagement`), que centraliza todos los cálculos relacionados con el combate:
  - **Cálculos de daño**: `calculateAttackDamage` determina el daño final aplicando multiplicadores según el tipo de ataque.
  - **Golpes críticos**: `isCriticalHit` y `applyWeaponCriticalDamage` gestionan la probabilidad y el multiplicador de daño para golpes críticos.
  - **Probabilidad de acierto**: `calculateHitProbability` determina si un ataque impacta basándose en la probabilidad configurada.
  - **Desgaste de armas**: `calculateWeaponDurabilityCost` calcula cuánto se reduce la durabilidad del arma tras cada ataque.
- Las estrategias se implementan en el estado `AttackingState` cuando un personaje decide atacar.
- El sistema es flexible y permite añadir nuevas estrategias de ataque simplemente implementando nuevas clases que extiendan `AbstractAttackStrategy`.

##### Aplicación de Principios SOLID

Este diseño demuestra la aplicación de los principios SOLID:

- **Principio de Responsabilidad Única (SRP)**: Cada estrategia tiene una única razón para cambiar.
- **Principio Abierto/Cerrado (OCP)**: El sistema está abierto para extensión (nuevas estrategias) pero cerrado para modificación.
- **Principio de Sustitución de Liskov (LSP)**: Cualquier implementación de `AttackStrategy` puede usarse donde se espera la interfaz.
- **Principio de Segregación de Interfaces (ISP)**: La interfaz `AttackStrategy` define solo los métodos necesarios.
- **Principio de Inversión de Dependencias (DIP)**: Las clases de alto nivel dependen de abstracciones, no de implementaciones concretas.

#### 2. Tools

Este subpaquete gestiona los objetos y herramientas que pueden usar los personajes:

- **Items (tools/items/)**:
  - **GenericItem**: Clase base abstracta para todos los ítems.
  - **Consumable**: Interfaz para ítems que pueden ser consumidos por un personaje.
  - **BaseWeapon**: Implementa las armas con diferentes tipos (`WeaponType`) y atributos.
  - **BaseHelmet**: Implementa los cascos con diferentes tipos (`HelmetType`) y atributos.
  - **HealthPotion**: Pociones para recuperar salud.
  - **UpgradeHealthPotion** y **UpgradeManaPotion**: Mejoras permanentes para salud y maná.
  - **Chest**: Contenedores que pueden ofrecer múltiples objetos aleatorios al ser abiertos.

- **Factories (tools/factory/)**:
  - Implementa el patrón Factory Method para la creación de diferentes tipos de objetos.
  - **SimpleWeaponFactory**: Crea diferentes tipos de armas.
  - **SimpleHelmetFactory**: Crea diferentes tipos de cascos.
  - **SimplePotionFactory**: Crea diferentes tipos de pociones.
  - **RandomItemFactory**: Implementa `RandomItemProducer` para generar ítems aleatorios.
  - **RandomItemGenerator**: Clase utilitaria para generar ítems con distribuciones de probabilidad específicas.

#### 3. Images

Este subpaquete implementa la representación visual de los personajes y su equipamiento:

- **CharacterImage**: Interfaz base para todas las imágenes de personajes.
- **BaseCharacterImage**: Implementación concreta para la imagen base del personaje.
- Implementa el patrón Decorator para añadir equipamiento a la imagen base:
  - **EquipmentDecorator**: Clase abstracta base para decoradores de equipamiento.
  - **WeaponDecorator**: Añade una imagen de arma a un personaje.
  - **HelmetDecorator**: Añade una imagen de casco a un personaje.
  
La organización de este paquete refleja un diseño orientado a objetos bien estructurado, utilizando principios SOLID y patrones de diseño como State, Strategy, Factory Method, Decorator y composición para crear un sistema flexible y extensible.

### Interfaz Gráfica del Juego

El juego cuenta con una interfaz gráfica desarrollada con Swing que permite una experiencia visual e interactiva:

- **Mapa de Exploración**:
  - Formado por casillas hexagonales que conforman islas en un entorno acuático.
  - Los personajes y objetos están representados por iconos simples con diferentes colores.
  - Elementos visuales incluyen personajes (jugador y bots en varios colores), objetos consumibles (pociones, armas, cascos y cofres).
  - Instrucciones en la parte inferior que guían al jugador sobre cómo moverse y actuar.

- **Interfaz de Combate**:
  - Ventana emergente que muestra el enfrentamiento entre el jugador y un bot.
  - Paneles laterales con información de ambos combatientes: imagen, equipamiento, barras de salud (HP) y maná (MP).
  - Panel central para el registro de acciones durante el combate (feed).
  - Botones en la parte inferior para las diferentes acciones: Ataque Ligero, Ataque Potente, Curarse, Concentrarse, y Huir.
  - Información visual sobre el estado del combate y las acciones disponibles.
