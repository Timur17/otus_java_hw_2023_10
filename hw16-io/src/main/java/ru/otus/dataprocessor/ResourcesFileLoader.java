package ru.otus.dataprocessor;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        String stringJson;

        try (InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (ioStream == null) {
                throw new FileProcessException(fileName + " is not found");
            }
            stringJson = CharStreams.toString(new InputStreamReader(ioStream, StandardCharsets.UTF_8));
        }
        Type measurementListType = new TypeToken<ArrayList<Measurement>>() {}.getType();
        return new Gson().fromJson(stringJson, measurementListType);
    }
}
