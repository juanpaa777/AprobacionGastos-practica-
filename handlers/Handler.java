package handlers;

import model.Request;

/**
 * Clase abstracta que define la estructura base para el patrón Chain of Responsibility.
 * 
 * <p>Esta clase representa un manejador en la cadena de responsabilidad. Cada manejador
 * puede procesar una solicitud o pasarla al siguiente manejador en la cadena.</p>
 * 
 * <p><b>Propósito:</b> Permite desacoplar el emisor de una petición de su receptor,
 * encadenando varios objetos que pueden manejarla. Cada eslabón decide si procesa
 * la solicitud o la delega al siguiente.</p>
 * 
 * <p><b>Funcionamiento:</b></p>
 * <ul>
 *   <li>Cada handler mantiene una referencia al siguiente handler en la cadena (nextHandler)</li>
 *   <li>El método {@link #handleRequest(Request)} debe ser implementado por las clases hijas</li>
 *   <li>Si un handler no puede procesar la solicitud, usa {@link #passToNext(Request)} para delegarla</li>
 * </ul>
 * 
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>
 * Handler supervisor = new Supervisor();
 * Handler gerente = new Gerente();
 * supervisor.setNext(gerente);
 * supervisor.handleRequest(request);
 * </pre>
 * 
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Supervisor
 * @see Gerente
 * @see Director
 * @see CEO
 */
public abstract class Handler {
    /** Referencia al siguiente manejador en la cadena de responsabilidad */
    protected Handler nextHandler;
    
    /**
     * Establece el siguiente manejador en la cadena y permite encadenamiento fluido.
     * 
     * <p>Este método permite construir la cadena de manera encadenada:
     * <pre>supervisor.setNext(gerente).setNext(director).setNext(ceo);</pre></p>
     * 
     * @param next El siguiente manejador en la cadena
     * @return El manejador pasado como parámetro para permitir encadenamiento fluido
     */
    public Handler setNext(Handler next) {
        this.nextHandler = next;
        return next;
    }
    
    /**
     * Procesa una solicitud. Este método debe ser implementado por las clases concretas.
     * 
     * <p>Cada handler concreto decide si puede procesar la solicitud basándose en sus
     * propios criterios (por ejemplo, límite de monto). Si no puede procesarla,
     * debe llamar a {@link #passToNext(Request)} para delegarla al siguiente handler.</p>
     * 
     * @param request La solicitud a procesar
     */
    public abstract void handleRequest(Request request);
    
    /**
     * Pasa la solicitud al siguiente manejador en la cadena.
     * 
     * <p>Este método se usa cuando el handler actual no puede procesar la solicitud
     * y necesita delegarla al siguiente eslabón de la cadena.</p>
     * 
     * @param request La solicitud a pasar al siguiente handler
     */
    protected void passToNext(Request request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

