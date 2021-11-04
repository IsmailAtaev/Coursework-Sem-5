package controller;

import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import java.io.IOException;

public class AdminController implements IController {

    public Connect connect = ServerController.connect;
    private IDBHandler idbHandler = new DBHClient();

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


        }

    }

    @Override
    public void editDate() {

    }

    @Override
    public void deleteDate() {

    }

    @Override
    public void getDate(String msg) throws IOException {
        switch (msg){
            case"viewUser":{
                connect.writeObjList(idbHandler.getList());
                break;
                //TODO i have more db handler and i can getter witch one ticket order client tour more )
            }
            case "viewTicket":{


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
                    default:
                        new MyException("поличичли что-то не то ");
                        break;
                }
            }
        } catch (IOException e) {
            new MyException(e);
        }
    }
}
