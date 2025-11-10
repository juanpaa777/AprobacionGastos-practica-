# Guía de Práctica - Chain of Responsibility (Versión Consola)

## 📋 Índice
1. [Introducción](#introducción)
2. [Objetivo](#objetivo)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Explicación del Patrón](#explicación-del-patrón)
5. [Paso a Paso - Implementación](#paso-a-paso---implementación)
6. [Cómo Ejecutar](#cómo-ejecutar)
7. [Ejemplo de Salida](#ejemplo-de-salida)
8. [Preguntas de Reflexión](#preguntas-de-reflexión)

---

## 🎯 Introducción

Esta práctica implementa el **patrón Chain of Responsibility (Cadena de Responsabilidad)** para un sistema de aprobación de gastos. El patrón permite que múltiples objetos tengan la oportunidad de procesar una solicitud, evitando acoplar el emisor con un receptor específico.

### ¿Qué es Chain of Responsibility?

Es un patrón de diseño que organiza objetos en una cadena. Cuando llega una solicitud, cada objeto en la cadena decide si puede manejarla o si debe pasarla al siguiente.

**Analogía:** Imagina una empresa donde:
- Un Supervisor puede aprobar gastos hasta $1,000
- Un Gerente puede aprobar hasta $5,000
- Un Director puede aprobar hasta $10,000
- El CEO puede aprobar cualquier monto

Si alguien solicita $2,500, el Supervisor no puede aprobarlo, así que lo pasa al Gerente, quien sí puede aprobarlo.

---

## 🎯 Objetivo

**Reproducir el patrón Chain of Responsibility en su forma mínima funcional (sin GUI) para comprender la lógica esencial del patrón.**

Al finalizar esta práctica, serás capaz de:
- ✅ Entender cómo funciona el patrón Chain of Responsibility
- ✅ Implementar una cadena de handlers
- ✅ Crear handlers concretos que procesen solicitudes
- ✅ Ver cómo las solicitudes fluyen por la cadena

---

## 📁 Estructura del Proyecto

```
AprobacionGastos-practica/
│
├── handlers/              # Manejadores de la cadena
│   ├── Handler.java      # Clase abstracta base
│   ├── Supervisor.java   # Handler: límite $1,000
│   ├── Gerente.java      # Handler: límite $5,000
│   ├── Director.java     # Handler: límite $10,000
│   └── CEO.java          # Handler: sin límite
│
├── model/                 # Modelos de datos
│   └── Request.java      # Clase que representa una solicitud
│
└── Main.java             # Cliente que construye y usa la cadena
```

---

## 🔍 Explicación del Patrón

### Componentes Principales

#### 1. **Handler (Manejador Abstracto)**
- Define la estructura común para todos los manejadores
- Mantiene una referencia al siguiente manejador en la cadena
- Define el método `handleRequest()` que cada handler concreto debe implementar
- Proporciona `passToNext()` para delegar al siguiente handler

#### 2. **Concrete Handlers (Manejadores Concretos)**
- Supervisor, Gerente, Director, CEO
- Cada uno implementa su propia lógica de aprobación
- Deciden si pueden procesar la solicitud o deben delegarla

#### 3. **Request (Solicitud)**
- Objeto que encapsula los datos de la solicitud
- Contiene: descripción, monto y solicitante
- Se pasa a través de toda la cadena

#### 4. **Client (Cliente - Main)**
- Construye la cadena de handlers
- Crea las solicitudes
- Inicia el procesamiento

### Flujo de Ejecución

```
Cliente (Main)
    ↓ crea la cadena
Supervisor → Gerente → Director → CEO
    ↓ crea solicitud
Request
    ↓ inicia procesamiento
Supervisor.handleRequest(request)
    ├─ ¿Puedo aprobar? (monto ≤ $1,000)
    │  ├─ SÍ → Apruebo y termino
    │  └─ NO → Paso a Gerente
    │      ├─ ¿Puedo aprobar? (monto ≤ $5,000)
    │      │  ├─ SÍ → Apruebo y termino
    │      │  └─ NO → Paso a Director
    │      │      ├─ ¿Puedo aprobar? (monto ≤ $10,000)
    │      │      │  ├─ SÍ → Apruebo y termino
    │      │      │  └─ NO → Paso a CEO
    │      │      │      └─ CEO siempre aprueba
```

---

## 📝 Paso a Paso - Implementación

### Paso 1: Crear la Jerarquía Handler

**Archivo: `handlers/Handler.java`**

```java
package handlers;
import model.Request;

public abstract class Handler {
    protected Handler nextHandler;  // Referencia al siguiente handler
    
    // Permite construir la cadena de manera fluida
    public Handler setNext(Handler next) {
        this.nextHandler = next;
        return next;  // Retorna el siguiente para encadenar
    }
    
    // Método abstracto que cada handler concreto debe implementar
    public abstract void handleRequest(Request request);
    
    // Método protegido para pasar la solicitud al siguiente handler
    protected void passToNext(Request request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}
```

**¿Qué hace?**
- Define la estructura base para todos los handlers
- Permite encadenar handlers con `setNext()`
- Proporciona `passToNext()` para delegar solicitudes

---

### Paso 2: Crear Handlers Concretos

#### 2.1 Supervisor

**Archivo: `handlers/Supervisor.java`**

```java
package handlers;
import model.Request;

public class Supervisor extends Handler {
    private static final double LIMITE = 1000.0;  // Límite: $1,000
    
    @Override
    public void handleRequest(Request request) {
        if (request.getMonto() <= LIMITE) {
            // Puedo aprobar → Apruebo y termino
            System.out.println("APROBADO POR: SUPERVISOR - " + request);
        } else {
            // No puedo aprobar → Delego al siguiente
            System.out.println("Supervisor: Excede mi limite. Delegando...");
            passToNext(request);
        }
    }
}
```

**Lógica:**
- Si monto ≤ $1,000 → **APRUEBA**
- Si monto > $1,000 → **DELEGA** al siguiente handler

---

#### 2.2 Gerente

**Archivo: `handlers/Gerente.java`**

```java
package handlers;
import model.Request;

public class Gerente extends Handler {
    private static final double LIMITE = 5000.0;  // Límite: $5,000
    
    @Override
    public void handleRequest(Request request) {
        if (request.getMonto() <= LIMITE) {
            System.out.println("APROBADO POR: GERENTE - " + request);
        } else {
            System.out.println("Gerente: Excede mi limite. Delegando...");
            passToNext(request);
        }
    }
}
```

**Lógica:**
- Si monto ≤ $5,000 → **APRUEBA**
- Si monto > $5,000 → **DELEGA** al siguiente handler

---

#### 2.3 Director

**Archivo: `handlers/Director.java`**

```java
package handlers;
import model.Request;

public class Director extends Handler {
    private static final double LIMITE = 10000.0;  // Límite: $10,000
    
    @Override
    public void handleRequest(Request request) {
        if (request.getMonto() <= LIMITE) {
            System.out.println("APROBADO POR: DIRECTOR - " + request);
        } else {
            System.out.println("Director: Excede mi limite. Delegando...");
            passToNext(request);
        }
    }
}
```

**Lógica:**
- Si monto ≤ $10,000 → **APRUEBA**
- Si monto > $10,000 → **DELEGA** al CEO

---

#### 2.4 CEO

**Archivo: `handlers/CEO.java`**

```java
package handlers;
import model.Request;

public class CEO extends Handler {
    @Override
    public void handleRequest(Request request) {
        // CEO siempre aprueba (último eslabón, sin límite)
        System.out.println("APROBADO POR: CEO - " + request + " (Sin limite)");
    }
}
```

**Lógica:**
- **SIEMPRE APRUEBA** (sin límite)
- Es el último eslabón, garantiza que todas las solicitudes se procesen

---

### Paso 3: Crear el Objeto Request

**Archivo: `model/Request.java`**

```java
package model;

public class Request {
    private String descripcion;   // Descripción del gasto
    private double monto;         // Monto solicitado
    private String solicitante;   // Nombre del solicitante
    
    // Constructor
    public Request(String descripcion, double monto, String solicitante) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.solicitante = solicitante;
    }
    
    // Getters
    public double getMonto() { return monto; }
    public String getDescripcion() { return descripcion; }
    public String getSolicitante() { return solicitante; }
    
    // Representación en cadena
    @Override
    public String toString() {
        return String.format("%s: %s - $%.2f", solicitante, descripcion, monto);
    }
}
```

**¿Qué hace?**
- Encapsula los datos de una solicitud
- Proporciona acceso a los datos mediante getters
- Formatea la información para mostrar

---

### Paso 4: Construir la Cadena y Probar

**Archivo: `Main.java`**

```java
import handlers.*;
import model.Request;

public class Main {
    public static void main(String[] args) {
        // 1. Crear instancias de todos los handlers
        Handler supervisor = new Supervisor();
        Handler gerente    = new Gerente();
        Handler director   = new Director();
        Handler ceo        = new CEO();
        
        // 2. Construir la cadena: Supervisor → Gerente → Director → CEO
        supervisor.setNext(gerente).setNext(director).setNext(ceo);

        // 3. Crear solicitudes con diferentes montos
        Request r1 = new Request("Compra de material", 750, "Ana");      // $750
        Request r2 = new Request("Licencias de software", 2500, "Luis");   // $2,500
        Request r3 = new Request("Equipo de laboratorio", 12000, "Karla"); // $12,000

        // 4. Procesar cada solicitud a través de la cadena
        System.out.println("Procesando r1...");
        supervisor.handleRequest(r1);  // Inicia desde el primer handler
        
        System.out.println("\nProcesando r2...");
        supervisor.handleRequest(r2);
        
        System.out.println("\nProcesando r3...");
        supervisor.handleRequest(r3);
    }
}
```

**Explicación del código:**

1. **Crear handlers:** Instanciamos cada handler concreto
2. **Construir cadena:** Usamos `setNext()` para encadenar. El método retorna el handler siguiente, permitiendo encadenamiento fluido
3. **Crear solicitudes:** Creamos 3 solicitudes con montos diferentes para probar diferentes escenarios
4. **Procesar:** Llamamos `handleRequest()` desde el primer handler (Supervisor). La solicitud fluye automáticamente por la cadena

---

## 🚀 Cómo Ejecutar

### Compilar

```bash
javac handlers/*.java model/*.java Main.java
```

O compilar por partes:

```bash
# 1. Compilar el modelo
javac model/Request.java

# 2. Compilar los handlers
javac handlers/*.java

# 3. Compilar el cliente
javac -cp . Main.java
```

### Ejecutar

```bash
java Main
```

---

## 📊 Ejemplo de Salida

Al ejecutar `java Main`, verás la siguiente salida:

```
Procesando r1...
APROBADO POR: SUPERVISOR - Ana: Compra de material - $750.00

Procesando r2...
Supervisor: Excede mi limite. Delegando...
APROBADO POR: GERENTE - Luis: Licencias de software - $2500.00

Procesando r3...
Supervisor: Excede mi limite. Delegando...
Gerente: Excede mi limite. Delegando...
Director: Excede mi limite. Delegando...
APROBADO POR: CEO - Karla: Equipo de laboratorio - $12000.00 (Sin limite)
```

### Análisis de la Salida

#### Solicitud 1 ($750)
- Supervisor evalúa: $750 ≤ $1,000 ✅
- **Resultado:** Aprobado por Supervisor (no necesita delegar)

#### Solicitud 2 ($2,500)
- Supervisor evalúa: $2,500 > $1,000 ❌ → Delega
- Gerente evalúa: $2,500 ≤ $5,000 ✅
- **Resultado:** Aprobado por Gerente

#### Solicitud 3 ($12,000)
- Supervisor evalúa: $12,000 > $1,000 ❌ → Delega
- Gerente evalúa: $12,000 > $5,000 ❌ → Delega
- Director evalúa: $12,000 > $10,000 ❌ → Delega
- CEO evalúa: Siempre aprueba ✅
- **Resultado:** Aprobado por CEO

---

## 💡 Preguntas de Reflexión

1. **¿Por qué el CEO siempre aprueba?**
   - Para garantizar que todas las solicitudes sean procesadas. Es el último eslabón de la cadena.

2. **¿Qué pasa si no hay un CEO al final?**
   - Las solicitudes que excedan $10,000 no serían procesadas (quedarían "colgadas").

3. **¿Puedo cambiar el orden de la cadena?**
   - Sí, pero el orden debe ser lógico (de menor a mayor autoridad). Si pones CEO primero, todas las solicitudes serían aprobadas por él.

4. **¿Cómo agrego un nuevo handler?**
   - Crea una nueva clase que extienda `Handler`, implementa `handleRequest()`, y agrégala a la cadena con `setNext()`.

5. **¿Qué ventajas tiene este patrón?**
   - Desacoplamiento: el cliente no sabe qué handler procesará la solicitud
   - Extensibilidad: fácil agregar/quitar handlers
   - Flexibilidad: la cadena se construye dinámicamente

---

## 📚 Resumen

### Conceptos Clave

1. **Handler Abstracto:** Define la estructura común
2. **Handlers Concretos:** Implementan la lógica específica
3. **Request:** Objeto que fluye por la cadena
4. **Cadena:** Se construye con `setNext()`
5. **Delegación:** Si no puedo procesar, delego con `passToNext()`

### Flujo General

```
Cliente → Construye cadena → Crea Request → Inicia procesamiento
    ↓
Handler evalúa → ¿Puedo procesar?
    ├─ SÍ → Proceso y termino
    └─ NO → Delego al siguiente → Repite evaluación
```

---

## ✅ Checklist de Comprensión

Antes de continuar, asegúrate de entender:

- [ ] Cómo se construye la cadena de handlers
- [ ] Cómo cada handler decide si procesa o delega
- [ ] Por qué el CEO debe ser el último eslabón
- [ ] Cómo fluye una solicitud por la cadena
- [ ] Qué ventajas ofrece este patrón

---

## 🎓 Siguiente Paso

Una vez que entiendas esta versión de consola, puedes:
- Modificar los límites de aprobación
- Agregar nuevos handlers (ej: Subdirector)
- Cambiar los criterios de aprobación
- Explorar la versión GUI para ver una implementación visual

---

**¡Felicitaciones!** Has implementado exitosamente el patrón Chain of Responsibility. 🎉

