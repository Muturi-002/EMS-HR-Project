package org.example;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException; //handling file operations under IOException class

public class LoadEnv {

    private static Properties properties = new Properties();

    // Static initializer block to load properties when the class is loaded
    static {
        // Load the properties file from its absolute path. Replace the filepath value with the correct path to your app.properties file.
        try (FileInputStream filepath = new FileInputStream("/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/app.properties");) {
            if (filepath == null) {
                System.err.println("Error: app.properties file not found in package.");
                throw new RuntimeException("app.properties file not found");
            }
            properties.load(filepath);
        } catch (IOException e) {
            System.err.println("Error loading app.properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error loading app.properties", e);
        }
    }


    public static String getURL() {
        return properties.getProperty("db_url");
    }
    public static String getTNSADMIN(){
        return properties.getProperty("TNS_ADMIN");
    }
    public static String getDatabaseUser() {
        return properties.getProperty("database_user");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("database_password");
    }
    public static String getDatabaseName() {
        return properties.getProperty("database_name");
    }
    public static String getAppUsername() {
        return properties.getProperty("App_username");
    }

    public static String getAppPassword() { // Corrected method name
        return properties.getProperty("username_password");
    }
}
