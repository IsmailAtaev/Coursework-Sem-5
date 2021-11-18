package controller.mythread;

import controller.ServerController;
import java.net.ServerSocket;

/**
 * @author Atayev I.M.
 * */

public class MyThread extends Thread {

    private Object locker;
    private ServerSocket serverSocket;
    public MyThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public MyThread(ServerSocket serverSocket,Object locker) {
        this.serverSocket = serverSocket;
        this.locker = locker;
    }

    @Override
    public void run() {
        synchronized (this.locker){
            ServerController serverController = new ServerController();
            serverController.work(serverSocket);
        }
    }
}