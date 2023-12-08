package uni.graduate.fitwiz.util.logging.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class LoggingService {

    @Value("${LOGS}")
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

    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line + "\n");
            }
        } catch (IOException e) {
            logs.add("Error reading logs from file");
        }

        return logs;
    }
}
