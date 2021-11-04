package controller.main;

import com.example.model.myexception.MyException;
import controller.mythread.MyThread;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static int port = 1122;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Thread.sleep(1000);
                MyThread myThread = new MyThread(serverSocket);
                myThread.start();
                myThread.join();
            }
        } catch (IOException e) {
            new MyException(e);
        } catch (InterruptedException e) {
            new MyException(e);
        }
    }
}