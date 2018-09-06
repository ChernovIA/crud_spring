package net.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public static Properties getProperties(String file){

        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            properties.load(classLoader.getResourceAsStream(file));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return properties;
    }
}
