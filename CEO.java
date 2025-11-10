public class CEO extends Handler {
    @Override
    public void handleRequest(Request request) {
        System.out.println("APROBADO POR: CEO - " + request + " (Sin limite)");
    }
}

