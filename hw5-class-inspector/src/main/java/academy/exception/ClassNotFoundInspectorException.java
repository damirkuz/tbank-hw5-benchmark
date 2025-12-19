package academy.exception;

public class ClassNotFoundInspectorException extends RuntimeException {
    public ClassNotFoundInspectorException(String message) {
        super(message);
    }

    public ClassNotFoundInspectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
