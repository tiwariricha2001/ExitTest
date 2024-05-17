package Maven.ExitTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("Utilities/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }
}
