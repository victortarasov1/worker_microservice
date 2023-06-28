package executor.service.logger;

enum LogMessage {
    EXECUTING_METHOD("Executing method {} in class {} with arguments {}"),
    METHOD_EXECUTION_COMPLETED("Method execution completed in class {}: Result - {}"),
    INVOCATION_TARGET_EXCEPTION("Method execution failed in class: {}");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
