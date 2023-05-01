import java.io.IOException;

public class Main {

    static String infoMessage = "Test info message";
    static String debugMessage = "Test debug message";

    public static void main(String[] args) {
        FileLoggerConfiguration config = new FileLoggerConfiguration("src/log.txt", LoggingLevel.DEBUG, 1024, ".txt");
        FileLogger fileLogger = new FileLogger(config);
        fileLogger.debug(debugMessage);
        fileLogger.info(infoMessage);
        FileLoggerConfiguration downloadedConfig = FileLoggerConfigurationLoader.load("src/configFile.txt");
        System.out.println("downloadedConfigPath = " + downloadedConfig.getFilePath());
        System.out.println("downloadedConfigLevel = " + downloadedConfig.getLoggingLevel());
        System.out.println("downloadedConfigMaxSize = " + downloadedConfig.getMaxSizeOfFile());
        System.out.println("downloadedConfigFormat = " + downloadedConfig.getFormat());
    }
}