package model;

/**
 * Clase que representa una solicitud de aprobación de gastos.
 * 
 * <p><b>Propósito:</b> Encapsular los datos necesarios para una solicitud de aprobación
 * de gastos en el sistema de cadena de responsabilidad.</p>
 * 
 * <p><b>Datos que contiene:</b></p>
 * <ul>
 *   <li><b>descripcion:</b> Descripción del gasto o compra solicitada</li>
 *   <li><b>monto:</b> Cantidad de dinero solicitada (en dólares)</li>
 *   <li><b>solicitante:</b> Nombre de la persona que realiza la solicitud</li>
 * </ul>
 * 
 * <p><b>Uso en el patrón:</b> Esta clase es el objeto que se pasa a través de la cadena
 * de handlers. Cada handler evalúa el monto para decidir si puede aprobar la solicitud.</p>
 * 
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>
 * Request solicitud = new Request("Compra de material de oficina", 750.50, "Juan Pérez");
 * System.out.println(solicitud);
 * // Salida: "Juan Pérez: Compra de material de oficina - $750.50"
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 */
public class Request {
    /** Descripción del gasto o compra solicitada */
    private String descripcion;
    
    /** Monto de dinero solicitado (en dólares) */
    private double monto;
    
    /** Nombre de la persona que realiza la solicitud */
    private String solicitante;
    
    /**
     * Constructor que crea una nueva solicitud de aprobación.
     * 
     * @param descripcion Descripción del gasto o compra
     * @param monto Monto solicitado (debe ser mayor a 0)
     * @param solicitante Nombre de la persona que solicita
     */
    public Request(String descripcion, double monto, String solicitante) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.solicitante = solicitante;
    }
    
    /**
     * Obtiene el monto de la solicitud.
     * 
     * <p>Este método es usado por los handlers para evaluar si pueden aprobar la solicitud
     * según su límite de autoridad.</p>
     * 
     * @return El monto de la solicitud
     */
    public double getMonto() {
        return monto;
    }
    
    /**
     * Obtiene la descripción del gasto.
     * 
     * @return La descripción de la solicitud
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene el nombre del solicitante.
     * 
     * @return El nombre de la persona que realizó la solicitud
     */
    public String getSolicitante() {
        return solicitante;
    }
    
    /**
     * Retorna una representación en cadena de la solicitud.
     * 
     * <p>Formato: "Solicitante: Descripción - $Monto"</p>
     * 
     * @return Cadena formateada con la información de la solicitud
     */
    @Override
    public String toString() {
        return String.format("%s: %s - $%.2f", solicitante, descripcion, monto);
    }
}

