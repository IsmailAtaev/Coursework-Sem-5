package controller.mythread;

import controller.ServerController;
import java.net.ServerSocket;

/**
 * @author Atayev I.M.
 * */

public class MyThread extends Thread {

    private ServerSocket serverSocket;
    public MyThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        ServerController serverController = new ServerController();
        serverController.work(serverSocket);
    }
}