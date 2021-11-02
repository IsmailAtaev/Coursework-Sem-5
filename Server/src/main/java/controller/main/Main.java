package controller.main;

import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import controller.mythread.MyThread;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static int port = 8189;

    public static void main(String[] args) {
        try {
            while (true) {
                Thread.sleep(1000);
                MyThread myThread = new MyThread(new ServerSocket(port), new Object());
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
