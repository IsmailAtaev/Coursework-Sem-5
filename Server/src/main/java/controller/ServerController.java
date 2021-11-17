package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
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


    public synchronized void work(ServerSocket serverSocket) {
        try {
            connect = new Connect(serverSocket);

            System.out.println("Client connect --> " + ++countClient);

            IDBHandler idbHandler = new DBHClient();
            boolean flag = true;
            while (true) {
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
                                    connect.writeObj(client);
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
                    default:
                        new MyException("class ServerController switch(connect.readLine()) error");
                        break;
                }
            }
        } catch (Exception e) {

        }
    }
}