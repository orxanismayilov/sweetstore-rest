package com.orxan.sweetstorerest.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertyUtil {

    public static Properties loadPropertiesFile(String url) {
        Properties properties = new Properties();
        try {
            InputStream input =new FileInputStream(url);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
