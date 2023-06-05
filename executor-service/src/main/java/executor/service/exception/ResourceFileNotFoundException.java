package executor.service.exception;

public class ResourceFileNotFoundException extends RuntimeException {
    public ResourceFileNotFoundException(String resourceName) {
        super(String.format("File %s not found in \"resources\" folder", resourceName));
    }
}
