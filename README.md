# Sistema de Aprobación de Gastos - Chain of Responsibility

## Descripción
Ejemplo simplificado del patrón Chain of Responsibility.

## Estructura
- `Request.java` - Clase de solicitud
- `Handler.java` - Handler abstracto
- `Supervisor.java`, `Gerente.java`, `Director.java`, `CEO.java` - Handlers concretos
- `ClientGUI.java` - Interfaz gráfica

## Compilación y Ejecución

### Compilar
```bash
javac -encoding UTF-8 *.java
```

### Ejecutar
```bash
java ClientGUI
```

### Crear JAR
```bash
jar cfm app.jar MANIFEST.MF *.class
java -jar app.jar
```

## Funcionamiento
1. Complete el formulario con descripción, monto y solicitante
2. Haga clic en "Procesar"
3. Observe cómo la solicitud fluye por la cadena de handlers
4. Cada handler evalúa si puede aprobar según su límite
5. Si no puede, delega al siguiente en la cadena

## Límites de Aprobación
- Supervisor: hasta $1,000
- Gerente: hasta $5,000
- Director: hasta $10,000
- CEO: sin límite

