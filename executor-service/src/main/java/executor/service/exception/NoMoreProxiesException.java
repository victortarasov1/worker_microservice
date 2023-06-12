package executor.service.exception;

public class NoMoreProxiesException extends RuntimeException{
    public NoMoreProxiesException() {
        super("No more proxies");
    }
}
