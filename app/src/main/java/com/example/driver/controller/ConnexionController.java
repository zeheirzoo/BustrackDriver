package com.example.driver.controller;

public class ConnexionController {
//    static String webUrl="http://193.194.68.62:9090/api/";
//    static String socketUrl="ws://193.194.68.62:9091/";

    static String webUrl="http://192.168.137.1:8000/api/";
    static String socketUrl="ws://192.168.137.1:8090/";

    public static String getWebUrl() {
        return webUrl;
    }

    public static void setWebUrl(String webUrl) {
        ConnexionController.webUrl = webUrl;
    }

    public static String getSocketUrl() {
        return socketUrl;
    }

    public static void setSocketUrl(String socketUrl) {
        ConnexionController.socketUrl = socketUrl;
    }
}
