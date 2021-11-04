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

    public MyThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        ServerController serverController = new ServerController();
        serverController.work(serverSocket);
    }
}

/*
* try {
            synchronized (locker) {
                // Connect connect = new Connect(serverSocket);
                IDBHandler idbHandler = new DBHClient();
                boolean flag = true;
                while (flag) {
                    switch (connect.readLine()) {
                        case "signIn": {
                            String login = connect.readLine();
                            String pass = connect.readLine();
                            ArrayList<Client> clients = (ArrayList<Client>) idbHandler.getList().clone();
                            System.out.println(clients.toString());
                            int i = 0;
                            for (Client client : clients) {
                                if (pass.equals(client.getPassword()) && login.equals(client.getLogin())) {
                                    flag = false;
                                    connect.writeLine("true");
                                    if (client.getFlag() == 1) {
                                        connect.writeLine("adminUI");
                                        IController iController = FactoryController.getType("admin");
                                        iController.start();
                                    } else if (client.getFlag() == 2) {
                                        connect.writeLine("clientUI");
                                        IController iController = FactoryController.getType("client");
                                        iController.start();
                                    } else {
                                        connect.writeLine("do not flags please view database and class Client");
                                        new MyException("do not flags please view database and class Client");
                                    }
                                    break;
                                } else {
                                    ++i;
                                }
                            }
                            if (i >= clients.size()) {
                                flag = true;
                                connect.writeLine("false");
                                connect.writeLine("do not in database please Sign Up!");
                            }
                            break;
                        }
                        case "signUp": {
                            if (idbHandler.addObj(connect.readObj())) {
                                connect.writeLine("true");
                            } else {
                                connect.writeLine("false");
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            new MyException(e);
        }
        * */