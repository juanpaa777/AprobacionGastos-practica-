public class Request {
    private String descripcion;
    private double monto;
    private String solicitante;
    
    public Request(String descripcion, double monto, String solicitante) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.solicitante = solicitante;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getSolicitante() {
        return solicitante;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s - $%.2f", solicitante, descripcion, monto);
    }
}

