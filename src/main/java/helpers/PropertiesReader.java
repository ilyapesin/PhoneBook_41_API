package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
   private static final String PROPERTY_FILE_PATH= "src/test/resources/resources.properties";

    public static String getProperty(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(PROPERTY_FILE_PATH);
        properties.load(fis);
        return properties.getProperty(key);
    }
}
