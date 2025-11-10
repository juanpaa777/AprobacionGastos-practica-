public class Director extends Handler {
    private static final double LIMITE = 10000.0;
    
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

