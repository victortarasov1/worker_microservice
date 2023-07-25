package executor.service.exception.okhttp;

public class UnsuccessfulResponseException extends RuntimeException {
    public UnsuccessfulResponseException(int code) {
        super("Request failed with status code: " + code);
    }

    public UnsuccessfulResponseException(String message) {
        super(message);
    }
}