package cgp.lib.individual.pojo;

import cgp.lib.individual.pojo.samples.Sample;
import cgp.user.simulation.input.Config;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputSamples<T> {

    private InputSamples() {

    }

    @JsonProperty("config")
    Config config;

    @JsonProperty("samples")
    List<Sample<T>> samples;

    @JsonProperty("samples")
    public List<Sample<T>> getSamples() {
        return samples;
    }

    @JsonProperty("samples")
    public void setSamples(List<Sample<T>> samples) {
        this.samples = samples;
    }

    public static class Builder<T> {
        private String fileName;
        private Class<?> targetClass;
        public Builder() {
            this.fileName = "data.json";
        }

        public Builder<T> setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public InputSamples<T> build() throws Exception {
            String fileContents = load(fileName);
            if (targetClass == null) {
                throw new Exception("Target class is null!");
            }
            return getData(fileContents);
        }

        public Builder<T> setTargetClass(Class<?> targetClass) {
            this.targetClass = targetClass;
            return this;

        }

        private String load(String fileName) throws Exception {
            ClassLoader classLoader = this.getClass().getClassLoader();
            System.out.println("Loading " + fileName);
            InputStream is = classLoader.getResourceAsStream(fileName);
            if (is != null) {
                String jsonString = new BufferedReader(
                        new InputStreamReader(is, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                return jsonString;
            } else {
                throw new FileNotFoundException("File '" + fileName + "' not found in the classpath");
            }


        }

        private InputSamples<T> getData(String jsonString) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructParametricType(InputSamples.class, targetClass);

            InputSamples<T> samples = mapper.readValue(jsonString, type);
            return samples;
        }


    }


    public Config getConfig() {
        return config;
    }
}
