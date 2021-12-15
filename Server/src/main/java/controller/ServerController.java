package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import controller.factory.FactoryController;
import controller.factory.IController;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerController {

    private static int countClient = 0;
    public static Connect connect;

    public ServerController(ServerSocket serverSocket) {
        this.connect = new Connect(serverSocket);
        countClient++;
    }

    public ServerController() {
    }

    public ServerController(Connect connect) {
        this.connect = connect;
        countClient++;
    }

    public void work(ServerSocket serverSocket) {
        try {
            IDBHandler idbHandler = new DBHClient();
            connect = new Connect(serverSocket);
            System.out.println("Client connect --> " + ++countClient);

            while (true) {
                String req = connect.readLine();
                switch (req) {
                    case "signIn": {
                        String login = connect.readLine();
                        String pass = connect.readLine();
                        ArrayList<Client> clients = (ArrayList<Client>) idbHandler.getList().clone();

                        for (Client client : clients) {
                            System.out.println(client.toString());
                            if (pass.equals(client.getPassword()) && login.equals(client.getLogin())) {
                                connect.writeLine("true");

                                if (client.getFlag() == 1) {

                                    connect.writeLine("adminUI");
                                    IController iController = FactoryController.getType("admin");
                                    iController.start();
                                    return;

                                } else if (client.getFlag() == 2) {
                                    System.out.println(client.toString());
                                    connect.writeLine("clientUI");
                                    connect.writeObj(client);

                                    IController iController = FactoryController.getType("client");
                                    iController.start();
                                    return;

                                } else {
                                    new MyException("do not flags please view database and class Client");
                                    break;
                                }
                            }
                        }
                        connect.writeLine("false");
                        connect.writeLine("false");
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
                    default: {
                        new MyException("class ServerController switch(connect.readLine()) error");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            new MyException(e);
        }
    }
}