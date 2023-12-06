package uni.graduate.fitwiz.util.logging.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Scope("singleton")
public class LoggingService {

    @Value("${logging.file.path}")
    private String logFilePath;

    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public LoggingService(ModelMapper modelMapper, Gson gson) {
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    public void log(Object object) {
        String logMessage = buildLogMessage(object);
        writeLogToFile(logMessage);
    }

    private String buildLogMessage(Object object) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timestamp = LocalDateTime.now();
        String formattedTimestamp = timestamp.format(formatter);

        String jsonString = gson.toJson(modelMapper.map(object, String.class));
        return formattedTimestamp + " - " + jsonString;
    }

    private void writeLogToFile(String logMessage) {
        try (FileWriter fileWriter = new FileWriter(logFilePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(logMessage);
        } catch (IOException e) {
            writeLogToFile("Error logging the data");
        }
    }
}
