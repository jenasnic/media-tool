package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import model.ProcessOperation;

public class OperationLogger
{
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    protected static String LOG_FILENAME = "log.txt";

    protected static OperationLogger operationLogger = null;
    protected Logger logger = null;

    protected OperationLogger() throws IOException
    {
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        String logFilename = String.format("%s%s%s",
            CurrentPathResolver.getCurrentPath(),
            File.separator,
            LOG_FILENAME
        );

        FileHandler fileHandler = new FileHandler(logFilename, true);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%s] - %s\r\n",
                    record.getLevel().getName(),
                    record.getMessage()
                );
            }
        });

        this.logger.addHandler(fileHandler);
    }

    public static OperationLogger getLogger() throws IOException
    {
        if (OperationLogger.operationLogger == null) {
            OperationLogger.operationLogger = new OperationLogger();
        }

        return OperationLogger.operationLogger;
    }

    public void log(ProcessOperation operation)
    {
        Level level = operation.isSuccess() ? Level.INFO : Level.WARNING;
        String time = dateFormat.format(operation.getDate());
        String message = String.format("%s : %s", time, operation.getMessage());

        this.logger.log(level, message);
    }
}
