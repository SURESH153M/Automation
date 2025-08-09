package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties = new Properties();

    public ConfigReader(String env) {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config/" + env + ".properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment config: " + env, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
