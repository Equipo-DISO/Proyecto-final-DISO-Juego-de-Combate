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

- **Singleton**: Utilizado en clases como `Calculator`, `GameContext`, `CombatManager` y `MapGenerator`.
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

- **Subpaquete Strategies: Sistema de Estrategias de Ataque**

  - El subpaquete `characters/states/strategies` implementa un sistema para gestionar diferentes tipos de ataques en el juego, combinando los patrones de diseño Strategy y Template Method.

  - **Estructura del Sistema de Estrategias**

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
        2. Validar si se tiene el maná requerido
        3. Consumir el maná requerido
        4. Calcular si el ataque impacta
        5. Calcular y aplicar el daño
    - Proporciona métodos auxiliares para:
      - Mensajes de golpes críticos
      - Aplicación de daño
      - Gestión de fallos

   3. **Estrategias Concretas:**
      1. **LightAttackStrategy:**
        - Ataque rápido y preciso
        - 90% probabilidad de acierto
        - Consume 7 puntos de maná
        - Daño base normal
        - Desgaste mínimo del arma

      2. **HeavyAttackStrategy:**
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
  

### Implementación del Patrón Observer

El proyecto implementa el patrón Observer mediante un modelo "Push" para gestionar las notificaciones de muerte de personajes y actualizar el estado del juego en consecuencia. Esta implementación permite desacoplar los personajes de la lógica de gestión del juego, el mapa y el sistema de victoria/derrota.

#### Componentes del Patrón Observer

1. **Subject/Observable (Objeto Observado)**:
   - **`PushModelObservable` (Interfaz)**: Define el contrato para los objetos que pueden ser observados.
     ```java
     public interface PushModelObservable {
         public void notifyDeathObservers();
     }
     ```
   - **`BaseCharacter` (Implementación Concreta)**: Mantiene una lista de observadores y les notifica cuando el personaje muere.
     ```java
     protected final List<PushModelObserver> observers = new ArrayList<>();
     
     public void addObserver(PushModelObserver observer) {
         this.observers.add(observer);
     }
     
     public void removeObserver(PushModelObserver observer) {
         this.observers.remove(observer);
     }
     
     @Override
     public void notifyDeathObservers() {
         // Limpia la casilla que ocupaba el personaje en el mapa
         if (this.currentPosition != null) {
             this.currentPosition.setOcupiedObject(null);
         }
         
         // Notifica a todos los observadores
         for (PushModelObserver observer : observers) {
             observer.characterHasDied(this);
         }
     }
     ```

2. **Observer (Observador)**:
   - **`PushModelObserver` (Interfaz)**: Define el método que será invocado cuando un personaje muera.
     ```java
     public interface PushModelObserver {
         public void characterHasDied(BaseCharacter character);
     }
     ```
   - **Implementaciones Concretas**:
     - **`GameContext`**: Gestiona el conteo de personajes vivos y determina el final del juego.
       ```java
       @Override
       public void characterHasDied(BaseCharacter character) {
           personajesVivos--;
           // Si solo queda un personaje vivo, muestra el podio (fin del juego)
           if(personajesVivos == 1 && character instanceof Bot && podium == null) {
               podium = new PodiumInterface(personajesVivos, personajesIniciales, null, personajesMuertos);
               podium.showInterface();
           }
       }
       ```
     - **`MapController`**: Actualiza la representación del mapa cuando un personaje muere.

#### Flujo de la Notificación

1. Cuando la salud de un personaje llega a cero, se llama a `setDeadState()` que transiciona al personaje al estado `DeadState`.
2. Durante esta transición, se invoca el método `notifyDeathObservers()` en `BaseCharacter`.
3. El método `notifyDeathObservers()` realiza dos acciones principales:
   - Libera la casilla que el personaje ocupaba en el mapa, previniendo inconsistencias.
   - Recorre la lista de observadores e invoca el método `characterHasDied(this)` en cada uno.
4. Los observadores (como `GameContext` y `MapController`) reaccionan a la notificación:
   - `GameContext` actualiza el conteo de personajes vivos y puede determinar el fin del juego.
   - `MapController` actualiza la representación visual del mapa.

#### Beneficios de la Implementación

- **Desacoplamiento**: Los personajes no necesitan conocer la lógica del juego o del mapa, solo notifican su muerte.
- **Centralización**: La lógica de fin de juego está centralizada en `GameContext`, lo que facilita su mantenimiento.
- **Extensibilidad**: Se pueden añadir nuevos observadores sin modificar la clase `BaseCharacter`.
- **Consistencia**: Garantiza que el mapa se actualice correctamente cuando un personaje muere, evitando referencias a objetos inválidos.

Esta implementación demuestra una aplicación práctica del patrón Observer en un contexto de juego, donde múltiples sistemas necesitan reaccionar ante eventos específicos de manera desacoplada y mantenible.

### Implementación del Patrón Strategy en Búsqueda de Caminos

El proyecto implementa el patrón Strategy para la búsqueda de caminos de los bots, permitiendo diferentes comportamientos de navegación según el objetivo que persiguen. Esta implementación facilita la extensibilidad del comportamiento de los bots sin modificar su lógica interna.

#### Componentes del Patrón Strategy para Búsqueda de Caminos

1. **Interfaz Strategy**:
   - **`PathFindingStrategy`**: Define el método común que deben implementar todas las estrategias de búsqueda.
     ```java
     public interface PathFindingStrategy {
         public Integer getTargetTileId(GenericTile currentPos, List<TileAbstract> tiles);
     }
     ```

2. **Estrategias Concretas**:
   - **`ClosestEnemyStrategy`**: Implementa la lógica para buscar el enemigo más cercano.
     ```java
     public class ClosestEnemyStrategy implements PathFindingStrategy {
         @Override
         public Integer getTargetTileId(GenericTile currentPos, List<TileAbstract> tiles) {
             Integer targetTileId = currentPos.getTileId();
             Double closestDistance = Double.MAX_VALUE;
             
             for (TileAbstract t : tiles) {
                 if (t instanceof GenericTile && t.isOcupiedByCharacter() && !t.equals(currentPos)) {
                     Double dist = TileGraph.distanceToTile(currentPos, t);
                     if (dist < closestDistance) {
                         closestDistance = dist;
                         targetTileId = t.getTileId();
                     }
                 }
             }
             return targetTileId;
         }
     }
     ```
   
   - **`ClosestLootStrategy`**: Implementa la lógica para buscar el botín más cercano.
     ```java
     public class ClosestLootStrategy implements PathFindingStrategy {
         @Override
         public Integer getTargetTileId(GenericTile currentPos, List<TileAbstract> tiles) {
             Integer targetTileId = currentPos.getTileId();
             Double closestDistance = Double.MAX_VALUE;
             
             for (TileAbstract t : tiles) {
                 if (t instanceof GenericTile && t.isOcupiedByLoot()) {
                     Double dist = TileGraph.distanceToTile(currentPos, t);
                     if (dist < closestDistance) {
                         closestDistance = dist;
                         targetTileId = t.getTileId();
                     }
                 }
             }
             return targetTileId;
         }
     }
     ```

3. **Contexto**:
   - **`BotActionType`**: Enumera los tipos de acciones que puede realizar un bot y asocia cada acción con una estrategia de búsqueda.
     ```java
     public enum BotActionType {
         LOOKING_FOR_ENEMY(new ClosestEnemyStrategy()), 
         LOOKING_FOR_ITEM(new ClosestLootStrategy());
         
         private PathFindingStrategy strategy;
         
         private BotActionType(PathFindingStrategy strategy) {
             this.strategy = strategy;
         }
         
         public PathFindingStrategy getStrategy() {
             return strategy;
         }
     }
     ```

4. **Cliente**:
   - **`BotAI`**: Utiliza la estrategia seleccionada para determinar la ruta que seguirá el bot.
     ```java
     this.targets = MapGenerator.getInstance().getPathToObjective(bot.getCurrentPosition(), bot.getBotActionType().getStrategy());
     ```

#### Funcionamiento del Sistema de Búsqueda de Caminos

1. El bot determina su objetivo actual (buscar enemigos o botín) y selecciona la acción correspondiente (`BotActionType`).
2. Cada `BotActionType` tiene asociada una estrategia de búsqueda (`PathFindingStrategy`).
3. El generador de mapas utiliza la estrategia seleccionada para calcular la ruta hacia el objetivo:
   - Si busca enemigos, se utiliza `ClosestEnemyStrategy` para encontrar el personaje enemigo más cercano.
   - Si busca botín, se utiliza `ClosestLootStrategy` para encontrar el ítem más cercano.
4. El bot sigue la ruta calculada, actualizando su posición en cada turno.

#### Beneficios de la Implementación

- **Flexibilidad**: Se pueden añadir nuevas estrategias de búsqueda sin modificar la lógica de los bots o del generador de mapas.
- **Modularidad**: Cada estrategia encapsula un algoritmo específico de búsqueda, facilitando su mantenimiento.
- **Especialización**: Permite que cada bot se comporte de manera diferente según su tipo y objetivo actual.
- **Reutilización**: Las estrategias pueden ser utilizadas por diferentes tipos de bots o incluso por el jugador si fuera necesario.

### Implementación del Patrón Abstract Factory para Creación de Tiles

El proyecto implementa el patrón Abstract Factory para la creación de diferentes tipos de tiles que componen el mapa de juego. Esta implementación permite la creación de familias de objetos relacionados sin especificar sus clases concretas.

#### Componentes del Patrón Abstract Factory para Tiles

1. **Abstract Factory**:
   - **`TileFactory` (Clase Abstracta)**: Define la interfaz para crear los diferentes tipos de tiles.
     ```java
     public abstract class TileFactory {
         protected Integer totalNumberOfTiles;
         protected Integer numberOfSpawns;
         protected LinkedList<Bot> bots;
         protected BaseCharacter player;
         protected PushModelObserver obs;
         
         protected TileFactory(Integer tiles, Integer spawns, LinkedList<Bot> bots, BaseCharacter player, PushModelObserver obs) {
             this.totalNumberOfTiles = tiles;
             this.numberOfSpawns = spawns;
             this.bots = new LinkedList<Bot>(bots);
             this.player = player;
             this.obs = obs;
         }
         
         public abstract GenericTile createTile(Integer x, Integer y, Integer tileId);
         public abstract ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId);
         public abstract TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId);
     }
     ```

2. **Concrete Factory**:
   - **`NormalTileFactory`**: Implementación concreta de la fábrica que crea tiles normales y obstáculos.
     ```java
     public class NormalTileFactory extends TileFactory {
         private RandomItemFactory itemFactory;
         
         public NormalTileFactory(Integer tiles, Integer spawns, LinkedList<Bot> bots, BaseCharacter player, PushModelObserver obs) {
             super(tiles, spawns, bots, player, obs);
             this.itemFactory = new RandomItemFactory();
         }
         
         @Override
         public GenericTile createTile(Integer x, Integer y, Integer tileId) {
             return new GenericTile(x, y, tileId);
         }
         
         @Override
         public ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId) {
             return new ObstacleTile(x, y, tileId);
         }
         
         @Override
         public TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId) {
             // Lógica para generar un tile aleatorio (normal, obstáculo o con objeto/personaje)
             // ...
         }
     }
     ```

3. **Abstract Product**:
   - **`TileAbstract`**: Clase base para todos los tipos de tiles.

4. **Concrete Products**:
   - **`GenericTile`**: Implementación de un tile normal por el que se puede transitar.
   - **`ObstacleTile`**: Implementación de un tile que representa un obstáculo intransitable.

5. **Cliente**:
   - **`MapGenerator`**: Utiliza la fábrica para crear los diferentes tiles que componen el mapa.
     ```java
     private TileFactory tileFactory;
     
     // En algún método de inicialización
     this.tileFactory = new NormalTileFactory(totalTiles, spawns, bots, player, observer);
     
     // Al generar el mapa
     for(Integer i = 0; i < tiles; i++) {
         TileAbstract tile = tileFactory.generateRandomTile(x, y, tileId);
         // Añadir el tile al mapa
     }
     ```

#### Funcionamiento del Sistema de Creación de Tiles

1. El generador de mapas inicializa la fábrica de tiles con los parámetros necesarios (número total de tiles, puntos de aparición, bots, jugador y observador).
2. Durante la generación del mapa, el generador solicita a la fábrica que cree los diferentes tipos de tiles:
   - Tiles normales transitables.
   - Tiles de obstáculos intransitables.
   - Tiles aleatorios que pueden ser normales, obstáculos o contener objetos/personajes.
3. La fábrica se encarga de crear las instancias apropiadas y configurarlas según los parámetros proporcionados.
4. Los tiles creados se añaden al mapa para formar el entorno de juego.

#### Características del Método `generateRandomTile`

El método `generateRandomTile` de `NormalTileFactory` es particularmente interesante, ya que combina varias responsabilidades:

1. Decide aleatoriamente si crear un tile normal o un obstáculo basándose en probabilidades predefinidas.
2. Determina si el tile debe ser un punto de aparición para un personaje (jugador o bot).
3. Si es un punto de aparición, asigna el personaje correspondiente al tile y configura las relaciones necesarias.
4. Si no es un punto de aparición y es un tile normal, puede decidir aleatoriamente añadir un objeto de botín.
5. Actualiza los contadores internos de la fábrica para mantener el control sobre el número total de tiles y puntos de aparición.

#### Beneficios de la Implementación

- **Encapsulación**: La lógica de creación de tiles está encapsulada en clases específicas, separándola del generador de mapas.
- **Extensibilidad**: Es fácil añadir nuevos tipos de tiles o fábricas sin modificar el código existente.
- **Coherencia**: Garantiza que los tiles creados sean compatibles entre sí y con el sistema de juego.
- **Configurabilidad**: Permite configurar fácilmente diferentes tipos de generación de mapas cambiando la fábrica utilizada.


