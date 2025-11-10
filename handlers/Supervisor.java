package handlers;

import model.Request;

/**
 * Handler concreto que representa un Supervisor en la cadena de aprobación.
 * 
 * <p><b>Responsabilidad:</b> Aprobar solicitudes de gastos hasta un límite de $1,000.00</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ul>
 *   <li>Si el monto de la solicitud es menor o igual a $1,000, el Supervisor la aprueba</li>
 *   <li>Si el monto excede $1,000, delega la solicitud al siguiente handler en la cadena</li>
 * </ul>
 * 
 * <p><b>Límite de aprobación:</b> $1,000.00</p>
 * 
 * <p><b>Ejemplo:</b></p>
 * <pre>
 * Request solicitud = new Request("Material de oficina", 750.0, "Juan");
 * Supervisor supervisor = new Supervisor();
 * supervisor.handleRequest(solicitud);
 * // Salida: "APROBADO POR: SUPERVISOR - Juan: Material de oficina - $750.00"
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Handler
 */
public class Supervisor extends Handler {
    /** Límite máximo de aprobación para el Supervisor: $1,000.00 */
    private static final double LIMITE = 1000.0;
    
    /**
     * Procesa la solicitud de aprobación.
     * 
     * <p>Evalúa si el monto de la solicitud está dentro de su límite de autoridad.
     * Si es así, aprueba la solicitud. Si no, la delega al siguiente handler.</p>
     * 
     * @param request La solicitud a evaluar y procesar
     */
    @Override
    public void handleRequest(Request request) {
        if (request.getMonto() <= LIMITE) {
            System.out.println("APROBADO POR: SUPERVISOR - " + request);
        } else {
            System.out.println("Supervisor: Excede mi limite. Delegando...");
            passToNext(request);
        }
    }
}

