import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoggerConfigurationLoader {

    public static FileLoggerConfiguration load(String filePath) {
        String pathToFile = null;
        LoggingLevel loggingLevel = null;
        long maxSizeOfFile = 0;
        String format = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line = reader.readLine();
            while (line != null) {
                String[] configurationParts = line.split(": ");
                String value = configurationParts[1];
                String key = configurationParts[0];
                switch (key) {
                    case "FILE":
                        pathToFile = value;
                        break;

                    case "LEVEL":
                        loggingLevel = LoggingLevel.valueOf(value);
                        break;

                    case "MAX-SIZE":
                        maxSizeOfFile = Long.parseLong(value);
                        break;

                    case "FORMAT":
                        format = value;
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid parameter to do config" + configurationParts[0]);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileLoggerConfiguration(pathToFile, loggingLevel, maxSizeOfFile, format);
    }
}