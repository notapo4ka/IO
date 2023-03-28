public class FileLoggerConfiguration {

    private String filePath;
    private LoggingLevel loggingLevel;
    private long maxSizeOfFile;
    private String format;

    public FileLoggerConfiguration(String filePath, LoggingLevel loggingLevel, long maxSizeOfFile, String format) {
            this.filePath = filePath;
            this.loggingLevel = loggingLevel;
            this.maxSizeOfFile = maxSizeOfFile;
            this.format = format;

            if (filePath == null || format == null) {
                throw new NullPointerException("Parameters [fileName] and [format] must not be null");
            }

            if (maxSizeOfFile < 0) {
                throw new IllegalArgumentException("Parameter [maxSizeOfFile] must be non-negative");
            }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public void setLoggingLevel(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    public long getMaxSizeOfFile() {
        return maxSizeOfFile;
    }

    public void setMaxSizeOfFile(long maxSizeOfFile) {
        this.maxSizeOfFile = maxSizeOfFile;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}