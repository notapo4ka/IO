import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH-mm");
        String formattedMessage = formatter.format(now);
        return formattedMessage;
    }
}