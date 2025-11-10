public class Supervisor extends Handler {
    private static final double LIMITE = 1000.0;
    
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

