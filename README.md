# Sistema de Aprobación de Gastos - Chain of Responsibility

## 📋 Descripción

Implementación del patrón **Chain of Responsibility** para un sistema de aprobación de gastos. El proyecto incluye dos versiones:
- **Versión Consola**: Ejercicio guiado que demuestra el patrón de forma simple
- **Versión GUI**: Interfaz gráfica con visualización del flujo de la cadena

## 🎯 Patrón de Diseño

**Chain of Responsibility (Cadena de Responsabilidad)**: Permite que múltiples objetos tengan la oportunidad de procesar una solicitud, evitando acoplar el emisor con un receptor específico.

## 📁 Estructura del Proyecto

```
AprobacionGastos-practica/
│
├── handlers/                    # Manejadores de la cadena de responsabilidad
│   ├── Handler.java            # Clase abstracta base
│   ├── Supervisor.java         # Handler: límite $1,000
│   ├── Gerente.java            # Handler: límite $5,000
│   ├── Director.java           # Handler: límite $10,000
│   └── CEO.java                # Handler: sin límite
│
├── model/                      # Modelos de datos
│   └── Request.java            # Clase que representa una solicitud
│
├── Main.java                   # Cliente de consola (ejercicio guiado)
├── ClientGUI.java              # Cliente con interfaz gráfica
├── MANIFEST.MF                 # Archivo manifest para JAR
├── Ejecutable.bat              # Script de ejecución para Windows
├── README.md                   # Este archivo
│
└── [archivos generados]        # .class (generados al compilar)
```

## ⚙️ Requisitos

- **Java JDK 8 o superior**
- **Sistema operativo**: Windows, Linux o macOS
- **Compilador**: `javac` (incluido en JDK)
- **No se requieren dependencias externas** (solo bibliotecas estándar de Java)

### Verificar instalación de Java

```bash
java -version
javac -version
```

Si estos comandos no funcionan, instala Java JDK desde [Oracle](https://www.oracle.com/java/technologies/downloads/) o [OpenJDK](https://openjdk.org/).

## 🚀 Compilación y Ejecución

### Opción 1: Versión Consola (Ejercicio Guiado)

#### Compilar

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

#### Ejecutar

```bash
java Main
```

**Salida esperada:**
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

---

### Opción 2: Versión GUI (Interfaz Gráfica)

#### Compilar

```bash
javac handlers/*.java model/*.java ClientGUI.java
```

O compilar todo junto:

```bash
javac handlers/*.java model/*.java Main.java ClientGUI.java
```

#### Ejecutar

```bash
java ClientGUI
```

**Interfaz:**
- Formulario para ingresar descripción, monto y solicitante
- Visualización en tiempo real del flujo por la cadena
- Paneles con colores que indican el estado de cada handler

---

### Compilar Todo el Proyecto

Para compilar todas las clases de una vez:

```bash
javac handlers/*.java model/*.java Main.java ClientGUI.java
```

---

## 📦 Crear JAR Ejecutable

### Crear JAR con GUI

```bash
# 1. Compilar todas las clases
javac handlers/*.java model/*.java ClientGUI.java

# 2. Crear el JAR
jar cfm AprobacionGastos.jar MANIFEST.MF handlers/*.class model/*.class ClientGUI.class

# 3. Ejecutar
java -jar AprobacionGastos.jar
```

**Nota:** Asegúrate de que `MANIFEST.MF` contenga:
```
Main-Class: ClientGUI
```

---

## 🎓 Funcionamiento

### Versión Consola

1. El programa crea instancias de todos los handlers
2. Construye la cadena: `Supervisor → Gerente → Director → CEO`
3. Crea solicitudes con diferentes montos
4. Procesa cada solicitud iniciando desde el Supervisor
5. Cada handler evalúa si puede aprobar según su límite
6. Si no puede aprobar, delega al siguiente handler

### Versión GUI

1. Abre la interfaz gráfica
2. Completa el formulario con:
   - **Descripción**: Descripción del gasto
   - **Monto**: Cantidad en dólares
   - **Solicitante**: Nombre de la persona
3. Haz clic en "Procesar"
4. Observa cómo la solicitud fluye por la cadena visualmente
5. Cada panel se ilumina mostrando el estado de cada handler

---

## 💰 Límites de Aprobación

| Handler | Límite de Aprobación | Posición en Cadena |
|---------|---------------------|-------------------|
| **Supervisor** | Hasta $1,000 | Primero |
| **Gerente** | Hasta $5,000 | Segundo |
| **Director** | Hasta $10,000 | Tercero |
| **CEO** | Sin límite | Último |

---

## 📚 Documentación

- **JavaDoc**: Todas las clases están documentadas con comentarios JavaDoc. Genera la documentación con:
  ```bash
  javadoc -d docs handlers/*.java model/*.java Main.java ClientGUI.java
  ```
  
- **Comentarios en código**: Cada clase contiene documentación JavaDoc explicando su propósito y funcionamiento

---

## 🔍 Ejemplo de Uso

### Versión Consola

```java
// Crear la cadena
Handler supervisor = new Supervisor();
Handler gerente = new Gerente();
Handler director = new Director();
Handler ceo = new CEO();
supervisor.setNext(gerente).setNext(director).setNext(ceo);

// Crear solicitud
Request solicitud = new Request("Material de oficina", 750.0, "Juan");

// Procesar
supervisor.handleRequest(solicitud);
// Salida: "APROBADO POR: SUPERVISOR - Juan: Material de oficina - $750.00"
```

---

## 🛠️ Solución de Problemas

### Error: "cannot find symbol"
- **Causa**: No se compilaron todas las clases necesarias
- **Solución**: Compila primero `model/Request.java`, luego `handlers/*.java`, y finalmente los clientes

### Error: "package does not exist"
- **Causa**: Estás ejecutando desde un directorio incorrecto
- **Solución**: Asegúrate de estar en el directorio raíz del proyecto

### Error al ejecutar JAR
- **Causa**: El MANIFEST.MF no tiene la clase principal correcta
- **Solución**: Verifica que `MANIFEST.MF` contenga `Main-Class: ClientGUI` o `Main-Class: Main`

---

## 👥 Autores

- Aguayo Santana Carlos Samael (1223100396)
- Pardo Zamarripa Juan Diego (1223100425)
- Rodríguez Guerrero Juan Francisco (1223100441)

**Grupo:** GIDS6071-E  
**Materia:** Arquitectura de Software  
**Unidad:** III – Patrones de Diseño  
**Patrón:** Chain of Responsibility

---

## 📄 Licencia

Este proyecto es parte de una práctica académica.

---

## 🔗 Recursos Adicionales

- [Documentación Java](https://docs.oracle.com/javase/)
- [Patrón Chain of Responsibility - Refactoring Guru](https://refactoring.guru/design-patterns/chain-of-responsibility)

## 🖥️ Ejecución Rápida (Windows)

Si estás en Windows, puedes usar el script `Ejecutable.bat` para compilar y crear el JAR ejecutable:

```bash
Ejecutable.bat
```

O hacer doble clic en el archivo `Ejecutable.bat` desde el explorador de archivos.

**Nota:** Este script:
1. Compila todos los archivos Java
2. Crea el archivo MANIFEST.MF
3. Genera el JAR ejecutable `AprobacionGastos.jar`

Luego puedes ejecutar el JAR con:
```bash
java -jar AprobacionGastos.jar
```

---

