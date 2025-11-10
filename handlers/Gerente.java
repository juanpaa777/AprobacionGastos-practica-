package handlers;

import model.Request;

/**
 * Handler concreto que representa un Gerente en la cadena de aprobación.
 * 
 * <p><b>Responsabilidad:</b> Aprobar solicitudes de gastos hasta un límite de $5,000.00</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ul>
 *   <li>Si el monto de la solicitud es menor o igual a $5,000, el Gerente la aprueba</li>
 *   <li>Si el monto excede $5,000, delega la solicitud al siguiente handler en la cadena</li>
 * </ul>
 * 
 * <p><b>Límite de aprobación:</b> $5,000.00</p>
 * 
 * <p><b>Posición en la cadena:</b> Segundo eslabón (después del Supervisor)</p>
 * 
 * <p><b>Ejemplo:</b></p>
 * <pre>
 * Request solicitud = new Request("Licencias de software", 2500.0, "María");
 * Gerente gerente = new Gerente();
 * gerente.handleRequest(solicitud);
 * // Salida: "APROBADO POR: GERENTE - María: Licencias de software - $2500.00"
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Handler
 */
public class Gerente extends Handler {
    /** Límite máximo de aprobación para el Gerente: $5,000.00 */
    private static final double LIMITE = 5000.0;
    
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
            System.out.println("APROBADO POR: GERENTE - " + request);
        } else {
            System.out.println("Gerente: Excede mi limite. Delegando...");
            passToNext(request);
        }
    }
}

