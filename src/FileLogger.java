import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {

    private FileLoggerConfiguration config;
    private File logFile;
    private long fileSize;

    public FileLogger(FileLoggerConfiguration config) {
        this.config = config;
    }

    public void log(LoggingLevel loggingLevel, String message) {
        if (logFile == null) {
            logFile = new File(config.getFilePath());
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (logFile.length() > config.getMaxSizeOfFile()) {
            String newFileName = "Log_" + DateTimeUtils.getCurrentTime("dd.MM.yyyy-HH-mm") + ".txt";
            logFile = new File(logFile.getParentFile(), newFileName);
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (loggingLevel == LoggingLevel.DEBUG) {
            writeToFile(loggingLevel, message,true);
        } else {
            writeToFile(loggingLevel, message, true);
        }
    }

    private void writeToFile(LoggingLevel loggingLevel, String message, boolean append) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, append))) {
            writer.write("[" + DateTimeUtils.getCurrentTime("dd.MM.yyyy-HH-mm") + "]" + "[" + loggingLevel + "]" +
                    " Повідомлення: " + message);
            fileSize = logFile.length();
            writer.write("Розмір файлу: " + fileSize + " байтів");
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void debug(String message) {
        if (config.getLoggingLevel() == LoggingLevel.DEBUG) {
            log(LoggingLevel.valueOf(LoggingLevel.DEBUG.name()), message);
            config.setLoggingLevel(LoggingLevel.INFO);
        }
    }

    public void info(String message) {
        if (config.getLoggingLevel() == LoggingLevel.INFO) {
            log(LoggingLevel.valueOf(LoggingLevel.INFO.name()), message);
        }
    }
}