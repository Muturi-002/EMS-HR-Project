package org.example;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException; //handling file operations under IOException class

public class LoadEnv {

    private static Properties properties = new Properties();

    // Static initializer block to load properties when the class is loaded
    static {
        // Load the properties file from its absolute path
        try (FileInputStream fis = new FileInputStream("<replace with absolute path to the 'filename'.properties file>");) {
            if (fis == null) {
                System.err.println("Error: app.properties file not found in package.");
                throw new RuntimeException("app.properties file not found");
            }
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading app.properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error loading app.properties", e);
        }
    }

    public static String getFilePath(){
        return properties.getProperty("file_path");
    }

    public static String getIP() {
        return properties.getProperty("IP_Address");
    }

    public static String getPort() {
        return properties.getProperty("Port");
    }

    public static String getDatabaseName() {
        return properties.getProperty("database_name");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("database_password");
    }

    public static String getAppUsername() {
        return properties.getProperty("App_username");
    }

    public static String getAppPassword() { // Corrected method name
        return properties.getProperty("username_password");
    }

    public static void main(String[] args) {
        // Example Usage
        System.out.println("IP Address: " + getIP());
        System.out.println("Port: " + getPort());
        System.out.println("Database Name: " + getDatabaseName());
        System.out.println("File path to environment variables: " + getFilePath());
    }
}