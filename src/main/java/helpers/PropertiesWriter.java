package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesWriter {
    private static final String PROPERTY_FILE_PATH = "src/test/resources/resources.properties";
    public static void writeProperties(String key,String value,boolean cleanFile){
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        if(!Files.exists(Paths.get(PROPERTY_FILE_PATH))){
            try {
                Files.createFile(Paths.get(PROPERTY_FILE_PATH));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            inputStream=new FileInputStream(PROPERTY_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(cleanFile==true){
            properties.clear();
        }

        properties.setProperty(key,value);
        try {
            outputStream=new FileOutputStream(PROPERTY_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.store(outputStream,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
