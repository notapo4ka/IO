import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoggerConfigurationLoader {

    public static FileLoggerConfiguration load(String filePath) {
        BufferedReader reader = null;
        String pathToFile = null;
        LoggingLevel loggingLevel = null;
        long maxSizeOfFile = 0;
        String format = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] configurationParts = line.split(": ");
                String value = configurationParts[1];
                switch (configurationParts[0]) {
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new FileLoggerConfiguration(pathToFile, loggingLevel, maxSizeOfFile, format);
    }
}