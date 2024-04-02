package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWriterXML {

    private static final String PROPERTY_FILE_PATH = "src/test/resources/data.xml";
    Properties properties = new Properties();
    public void setProperties(String key, String value, boolean clearFile){
        if(!clearFile){
            try( FileInputStream inputStream = new FileInputStream(PROPERTY_FILE_PATH)){
                properties.loadFromXML(inputStream);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        properties.setProperty(key, value);
        try(FileOutputStream outputStream = new FileOutputStream(PROPERTY_FILE_PATH)){
            properties.storeToXML(outputStream, null);
        }
        catch (IOException e){e.printStackTrace();}
    }

}
