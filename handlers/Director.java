package handlers;

import model.Request;

/**
 * Handler concreto que representa un Director en la cadena de aprobación.
 * 
 * <p><b>Responsabilidad:</b> Aprobar solicitudes de gastos hasta un límite de $10,000.00</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ul>
 *   <li>Si el monto de la solicitud es menor o igual a $10,000, el Director la aprueba</li>
 *   <li>Si el monto excede $10,000, delega la solicitud al siguiente handler en la cadena (CEO)</li>
 * </ul>
 * 
 * <p><b>Límite de aprobación:</b> $10,000.00</p>
 * 
 * <p><b>Posición en la cadena:</b> Tercer eslabón (después del Gerente, antes del CEO)</p>
 * 
 * <p><b>Ejemplo:</b></p>
 * <pre>
 * Request solicitud = new Request("Equipo de cómputo", 8500.0, "Carlos");
 * Director director = new Director();
 * director.handleRequest(solicitud);
 * // Salida: "APROBADO POR: DIRECTOR - Carlos: Equipo de cómputo - $8500.00"
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Handler
 */
public class Director extends Handler {
    /** Límite máximo de aprobación para el Director: $10,000.00 */
    private static final double LIMITE = 10000.0;
    
    /**
     * Procesa la solicitud de aprobación.
     * 
     * <p>Evalúa si el monto de la solicitud está dentro de su límite de autoridad.
     * Si es así, aprueba la solicitud. Si no, la delega al siguiente handler (CEO).</p>
     * 
     * @param request La solicitud a evaluar y procesar
     */
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

