package controller.main;

import controller.ServerController;
import controller.mythread.MyThread;

import java.io.IOException;
import java.net.ServerSocket;

public class MyMain {
    public static int port = 1112;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                ServerController serverController = new ServerController();
                new Thread(() -> {
                    serverController.work(serverSocket);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}