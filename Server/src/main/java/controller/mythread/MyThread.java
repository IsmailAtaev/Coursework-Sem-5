package controller.mythread;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import controller.FactoryController;
import controller.IController;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MyThread extends Thread {

    private Object locker;
    private ServerSocket serverSocket;

    public MyThread(ServerSocket serverSocket, Object locker) {
        this.serverSocket = serverSocket;
        this.locker = locker;
    }


    /**
     *
     */

    @Override
    public void run() {
        try {
            synchronized (locker) {
                Connect connect = new Connect(serverSocket);
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
                                } else{
                                    ++i;
                                }
                            }
                            if(i >= clients.size()){
                                flag = true;
                                connect.writeLine("false");
                                connect.writeLine("do not in database please Sign Up!");
                            }
                            break;
                        }
                        case "signUp": {
                            Client client = (Client) connect.readObj();
                            break;
                        }
                    }
                    /*String msgButton = connect.readLine();
                    String login = null;
                    String pass = null;
                    if (msgButton.equals("signIn")) {
                        login = connect.readLine();
                        pass = connect.readLine();
                        ArrayList<Client> clients = (ArrayList<Client>) idbHandler.getList().clone();
                        System.out.println(clients.toString());

                        for(Client client : clients){
                            if(pass.equals(client.getPassword()) && login.equals(client.getLogin())){
                                flag = false;
                                connect.writeLine("true");
                                IController controller = new AdminController();
                                controller.start();




                            }else {

                            }


                        }


                        if (pass.equals(clients.get(1).getFIO())) {
                            flag = false;
                            connect.writeLine("true");
                            IController controller = new AdminController();
                            controller.start();
                        } else {
                            flag = true;
                            connect.writeLine("false");
                        }
                    } else if (msgButton.equals("signUp")) {
                        Client client = (Client) connect.readObj();
                    }*/
                }
            }
        } catch (Exception e) {
            new MyException(e);
        }
    }
}