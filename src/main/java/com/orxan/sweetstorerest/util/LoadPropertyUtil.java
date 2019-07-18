package com.orxan.sweetstorerest.util;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class LoadPropertyUtil {

    public static Properties loadPropertiesFile(String url) {
        Resource resource = new ClassPathResource(url);
        Properties props;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
