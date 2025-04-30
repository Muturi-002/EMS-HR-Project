package org.example;

import java.util.*;

public class LoadEnv{
    private Properties properties;
    public LoadEnv() {
        properties = new Properties();
    }
    public static String getIP(String ipAddress){
        return ipAddress;
    }
    public static String getServerPort(String port){
        return port;
    }
    public static String getUser(String appUser){
        return appUser;
    }
    public static String getUserPass(String password){
       return password;
    }

    public static void main (String []args){
        new LoadEnv();
    }

}