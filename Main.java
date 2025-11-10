import handlers.*;
import model.Request;

/**
 * Clase principal que demuestra el uso del patrón Chain of Responsibility.
 * 
 * <p><b>Propósito:</b> Ejercicio guiado de consola que muestra cómo funciona
 * el patrón Chain of Responsibility para aprobación de gastos.</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ol>
 *   <li>Crea instancias de todos los handlers (Supervisor, Gerente, Director, CEO)</li>
 *   <li>Construye la cadena de responsabilidad encadenando los handlers</li>
 *   <li>Crea solicitudes de aprobación con diferentes montos</li>
 *   <li>Procesa cada solicitud a través de la cadena iniciando desde el Supervisor</li>
 * </ol>
 * 
 * <p><b>Cadena de responsabilidad:</b></p>
 * <pre>
 * Supervisor → Gerente → Director → CEO
 * </pre>
 * 
 * <p><b>Límites de aprobación:</b></p>
 * <ul>
 *   <li>Supervisor: hasta $1,000</li>
 *   <li>Gerente: hasta $5,000</li>
 *   <li>Director: hasta $10,000</li>
 *   <li>CEO: sin límite</li>
 * </ul>
 * 
 * <p><b>Ejemplo de ejecución:</b></p>
 * <pre>
 * Procesando r1...
 * APROBADO POR: SUPERVISOR - Ana: Compra de material - $750.00
 * 
 * Procesando r2...
 * Supervisor: Excede mi limite. Delegando...
 * APROBADO POR: GERENTE - Luis: Licencias de software - $2500.00
 * 
 * Procesando r3...
 * Supervisor: Excede mi limite. Delegando...
 * Gerente: Excede mi limite. Delegando...
 * Director: Excede mi limite. Delegando...
 * APROBADO POR: CEO - Karla: Equipo de laboratorio - $12000.00 (Sin limite)
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class Main {
    /**
     * Método principal que ejecuta el ejemplo del patrón Chain of Responsibility.
     * 
     * <p>Este método demuestra el flujo completo del patrón:
     * construcción de la cadena, creación de solicitudes y procesamiento.</p>
     * 
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Paso 1: Crear instancias de todos los handlers
        Handler supervisor = new Supervisor();
        Handler gerente    = new Gerente();
        Handler director   = new Director();
        Handler ceo        = new CEO();
        
        // Paso 2: Construir la cadena de responsabilidad
        // El encadenamiento fluido permite construir la cadena de manera elegante
        supervisor.setNext(gerente).setNext(director).setNext(ceo);

        // Paso 3: Crear solicitudes de aprobación con diferentes montos
        Request r1 = new Request("Compra de material", 750, "Ana");      // $750 - será aprobado por Supervisor
        Request r2 = new Request("Licencias de software", 2500, "Luis");   // $2,500 - será aprobado por Gerente
        Request r3 = new Request("Equipo de laboratorio", 12000, "Karla"); // $12,000 - será aprobado por CEO

        // Paso 4: Procesar cada solicitud a través de la cadena
        // Todas las solicitudes se inician desde el primer handler (Supervisor)
        System.out.println("Procesando r1...");
        supervisor.handleRequest(r1);
        
        System.out.println("\nProcesando r2...");
        supervisor.handleRequest(r2);
        
        System.out.println("\nProcesando r3...");
        supervisor.handleRequest(r3);
    }
}
