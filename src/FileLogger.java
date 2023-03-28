import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private FileLoggerConfiguration config;
    private File logFile;
    private long fileSize;

    public File getLogFile() {
        return logFile;
    }
    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public FileLogger(FileLoggerConfiguration config) {
        this.config = config;
        this.logFile = new File(config.getFilePath());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void log(String logLevel, String message) throws FileMaxSizeReachedException {
        if (logFile.length() + CurrentTime.getCurrentTime().getBytes().length > config.getMaxSizeOfFile()) {
            String newFileName = "Log_" + CurrentTime.getCurrentTime() + ".txt";
            logFile = new File(logFile.getParentFile(), newFileName);
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileSize = 0;
        }

        if (logLevel.equals(LoggingLevel.DEBUG.name())) {
            if (logFile.length() > 0) {
                writeToFile(logLevel, message,true);
            } else {
                writeToFile(logLevel, message,false);
            }
        } else if (logLevel.equals(LoggingLevel.INFO.name())) {
            writeToFile(logLevel, message, true);
        }
    }

    private void writeToFile(String logLevel, String message, boolean append) throws FileMaxSizeReachedException {
        BufferedWriter writer = null;
        try {
            if (logFile.length() >= config.getMaxSizeOfFile()) {
                String newFileName = "Log_" + CurrentTime.getCurrentTime() + ".txt";
                logFile = new File(logFile.getParentFile(), newFileName);
                logFile.createNewFile();
                fileSize = 0;
            }
            writer = new BufferedWriter(new FileWriter(logFile, append));
            writer.write("[" + CurrentTime.getCurrentTime() + "]" + "[" + logLevel + "]" +
                    " Повідомлення: " + message);
            writer.newLine();
            fileSize = logFile.length();
            writer.write("Розмір файлу: " + fileSize + " байтів");
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void debug(String message) {
        if (config.getLoggingLevel() == LoggingLevel.DEBUG) {
            try {
                log(LoggingLevel.DEBUG.name(), message);
                config.setLoggingLevel(LoggingLevel.INFO);
            } catch (FileMaxSizeReachedException e) {
                System.err.println("File size reached maximum size: " + config.getMaxSizeOfFile()  + ". Current size is: " + logFile.length() + ". File path: " + logFile.getPath());
            }
        }
    }

    public void info(String message) {
        if (config.getLoggingLevel() == LoggingLevel.INFO) {
            try {
                log(LoggingLevel.INFO.name(), message);
            } catch (FileMaxSizeReachedException e) {
                System.err.println("File size reached maximum size: " + config.getMaxSizeOfFile()  + ". Current size is: " + logFile.length() + ". File path: " + logFile.getPath());
            }
        }
    }
}