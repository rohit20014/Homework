package Util;

import java.io.*;
import java.util.Properties;

public class ReadPropFile {

    public String getPropertyValue(String key) {
        FileInputStream fileIn;
        String value = null;
        try {
            Properties configProperty = new Properties();
            File file = new File(System.getProperty("user.dir") + "/config.properties");
            fileIn = new FileInputStream(file);
            configProperty.load(fileIn);
            value = configProperty.getProperty(key);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    public void setPropertyValue(String key, String data) {
        FileOutputStream fileOut = null;
        FileInputStream fileIn;
        try {
            Properties configProperty = new Properties();
            File file = new File(System.getProperty("user.dir") + "/config.properties");
            fileIn = new FileInputStream(file);
            configProperty.load(fileIn);
            configProperty.setProperty(key, data);
            fileOut = new FileOutputStream(file);
            configProperty.store(fileOut, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
