public abstract class Handler {
    protected Handler nextHandler;
    
    public Handler setNext(Handler next) {
        this.nextHandler = next;
        return next;
    }
    
    public abstract void handleRequest(Request request);
    
    protected void passToNext(Request request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

