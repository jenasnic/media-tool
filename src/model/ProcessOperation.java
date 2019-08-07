package model;

/**
 * Model used to history actions when launching process.
 */
public class ProcessOperation
{
    protected OperationType operation;
    protected String message;
    protected boolean success;

    public ProcessOperation(OperationType operation, String message, boolean success)
    {
        this.operation = operation;
        this.message = message;
        this.success = success;
    }

    public OperationType getOperation()
    {
        return operation;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isSuccess()
    {
        return success;
    }
}
