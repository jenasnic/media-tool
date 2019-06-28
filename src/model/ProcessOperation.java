package model;

/**
 * Model used when simulate actions (to history all simulated operations).
 */
public class ProcessOperation
{
    protected OperationType operation;
    protected String message;

    public ProcessOperation(OperationType operation, String message)
    {
        this.operation = operation;
        this.message = message;
    }

    public OperationType getOperation() {
        return operation;
    }

    public String getMessage() {
        return message;
    }
}
