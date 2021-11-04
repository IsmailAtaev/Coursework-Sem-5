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
    public void saveDate() {

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

                }
            }
        } catch (IOException e) {
            new MyException(e);
        }
    }
}
