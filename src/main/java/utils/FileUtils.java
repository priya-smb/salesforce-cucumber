package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {
    public static String readPropertiesFile(String path, String key) throws IOException {
        File f = new File(path);
        FileReader fr = new FileReader(f);
        Properties p = new Properties();
        p.load(fr);
        return p.getProperty(key);
    }

    public static Properties readAllProperties(String propertiesFilePath) {
        Properties p = new Properties();
        try {
            File f = new File(propertiesFilePath);
            FileReader fr = new FileReader(f);
            p.load(fr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p;
    }
}
