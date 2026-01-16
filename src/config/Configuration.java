package config;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties props = new Properties();

    static {
        try {
            InputStream input = Configuration.class
                    .getClassLoader()
                    .getResourceAsStream("config/banque.properties");

            if (input != null) {
                props.load(input);
            } else {
                System.out.println("Fichier de configuration introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getDouble(String key) {
        return Double.parseDouble(props.getProperty(key));
    }
}