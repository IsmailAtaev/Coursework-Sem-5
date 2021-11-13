package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import java.io.IOException;
import java.util.ArrayList;

public class AdminController implements IController {

    public Connect connect = ServerController.connect;
    private IDBHandler idbHandler = new DBHClient();
    private  IDBHandler idbHandlerTour;

    @Override
    public void saveDate(String msg) {
        switch (msg) {
            case "addUser": {
                try {
                    boolean flagAddClient = idbHandler.addObj(connect.readObj());
                    if (flagAddClient) {
                        connect.writeLine("true");
                    } else {
                        connect.writeLine("false");
                    }
                } catch (IOException e) {
                    new MyException(e);
                } catch (ClassNotFoundException e) {
                    new MyException(e);
                }
                break;
            }
            case "addTour":{
                boolean flagAddTour;
            }

        }

    }

    @Override
    public void editDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "editUser": {
                Client client = (Client) connect.readObj();
                boolean flag = idbHandler.editObj(client);
                if (flag) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }


        }

    }

    @Override
    public void deleteDate(String msg) throws IOException {
        switch (msg) {
            case "deleteUser": {

                String login = connect.readLine();
                String pass = connect.readLine();
                String clientCode = connect.readLine();
                ArrayList<Client> list = (ArrayList<Client>) idbHandler.getList().clone();
                boolean flagDelete = false;

                for (Client c : list) {
                    if (login.equals(c.getLogin()) && pass.equals(c.getPassword()) && clientCode.equals(c.getClientCode())) {
                        flagDelete = idbHandler.deleteObj(c);
                        break;
                    }
                }

                if (flagDelete) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }


        }


    }

    @Override
    public void getDate(String msg) throws IOException {
        switch (msg) {
            case "viewUser": {
                connect.writeObjList(idbHandler.getList());
                break;
                //TODO i have more db handler and i can getter witch one ticket order client tour more )
            }
            case "viewTicket": {


            }

        }


        System.out.println("admin");
    }

    @Override
    public void start() {
        System.out.println("start admin");
        try {
            while (true) {
                switch (connect.readLine()) {
                    case "view": {
                        this.getDate(connect.readLine());
                        break;
                    }
                    case "add": {
                        this.saveDate(connect.readLine());
                        break;
                    }
                    case "delete": {
                        this.deleteDate(connect.readLine());
                        break;
                    }
                    case "search": {
                        this.search(connect.readLine());
                        break;
                    }
                    case "edit":{
                        this.editDate(connect.readLine());
                        break;
                    }
                    default:
                        new MyException("поличичли что-то не то ");
                        break;
                }
            }
        } catch (IOException e) {
            new MyException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void search(String msg) throws IOException {
        switch (msg) {
            case "searchUser": {

                String fio = connect.readLine();
                String login = connect.readLine();
                String pass = connect.readLine();
                int counter = 0;
                ArrayList<Client> list = (ArrayList<Client>) idbHandler.getList().clone();
                for(Client c : list){
                    if(fio.equals(c.getFIO()) && login.equals(c.getLogin()) && pass.equals(c.getPassword())){
                        ++counter;
                        connect.writeLine("true");
                        connect.writeObj(c);
                    }
                }

                if(counter == 0){
                    connect.writeLine("false");
                }



                break;
            }


        }
    }

}