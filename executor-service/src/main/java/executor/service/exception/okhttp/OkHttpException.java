package executor.service.exception.okhttp;

public class OkHttpException extends RuntimeException {
    public OkHttpException(String message) {
        super(message);
    }

    public OkHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public OkHttpException(Throwable cause) {
        super(cause);
    }

}