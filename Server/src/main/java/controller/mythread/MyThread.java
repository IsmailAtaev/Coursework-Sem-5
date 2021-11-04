package controller.mythread;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import controller.FactoryController;
import controller.IController;
import controller.ServerController;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import java.net.ServerSocket;
import java.util.ArrayList;

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