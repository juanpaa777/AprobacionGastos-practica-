public class Gerente extends Handler {
    private static final double LIMITE = 5000.0;
    
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

