package controller.main;

import controller.mythread.MyThread;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static int port = 1112;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Object ob = new Object();
                Thread.sleep(1000);
                MyThread myThread = new MyThread(serverSocket,ob);
                myThread.start();
               //  myThread.join();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}