package handlers;

import model.Request;

/**
 * Handler concreto que representa el CEO (Director Ejecutivo) en la cadena de aprobación.
 * 
 * <p><b>Responsabilidad:</b> Aprobar solicitudes de gastos sin límite de monto</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ul>
 *   <li>El CEO es el último eslabón de la cadena y siempre aprueba las solicitudes</li>
 *   <li>No tiene límite de monto, por lo que puede aprobar cualquier solicitud</li>
 *   <li>Garantiza que todas las solicitudes sean procesadas (no hay solicitudes rechazadas)</li>
 * </ul>
 * 
 * <p><b>Límite de aprobación:</b> Sin límite (ilimitado)</p>
 * 
 * <p><b>Posición en la cadena:</b> Último eslabón (final de la cadena)</p>
 * 
 * <p><b>Importante:</b> El CEO debe ser siempre el último handler en la cadena para
 * asegurar que todas las solicitudes sean procesadas.</p>
 * 
 * <p><b>Ejemplo:</b></p>
 * <pre>
 * Request solicitud = new Request("Equipo de laboratorio", 15000.0, "Ana");
 * CEO ceo = new CEO();
 * ceo.handleRequest(solicitud);
 * // Salida: "APROBADO POR: CEO - Ana: Equipo de laboratorio - $15000.00 (Sin limite)"
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Handler
 */
public class CEO extends Handler {
    /**
     * Procesa la solicitud de aprobación.
     * 
     * <p>El CEO siempre aprueba la solicitud sin importar el monto,
     * ya que es el último eslabón de la cadena y no tiene límites de autoridad.</p>
     * 
     * @param request La solicitud a aprobar
     */
    @Override
    public void handleRequest(Request request) {
        System.out.println("APROBADO POR: CEO - " + request + " (Sin limite)");
    }
}

