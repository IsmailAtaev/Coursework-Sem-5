package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.tour.Tour;
import model.bd.dbhclient.DBHClient;
import model.bd.dbhorder.DBHOrder;
import model.bd.dbhtour.DBHTour;
import model.bd.idbhandler.IDBHandler;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController implements IController {

    public Connect connect = ServerController.connect;
    private IDBHandler idbHandler = new DBHClient();
    private IDBHandler idbHandlerTour = new DBHTour();
    private IDBHandler idbHandlerOrder = new DBHOrder();


    @Override
    public void saveDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "orderTour": {
                String tourCode = connect.readLine();
                Client client = (Client) connect.readObj();
                ArrayList<Tour> tourArrayList = (ArrayList<Tour>) idbHandlerTour.getList().clone();
                if (makeOrderTour(tourCode, client, tourArrayList)) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
        }
    }

    @Override
    public void editDate(String msg) throws IOException, ClassNotFoundException {

    }

    @Override
    public void deleteDate(String msg) throws IOException, ClassNotFoundException {

    }

    @Override
    public void getDate(String msg) throws IOException {
        switch (msg) {
            case "viewUser": {
                connect.writeObjList(idbHandler.getList());
                break;
            }
            case "viewTicket": {
                break;
            }
            case "viewTour": {
                connect.writeObjList(idbHandlerTour.getList());
                break;
            }
        }
    }

    @Override
    public void start() {
        System.out.println("start client controller");
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
                        //     this.search(connect.readLine());
                        break;
                    }
                    case "edit": {
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


    private boolean makeOrderTour(String tourCode, Client client, ArrayList<Tour> tourArrayList) {
        for (Tour t : tourArrayList) {
            if (tourCode.equals(t.getTourCode())) {
                Order order = new Order();
                order.setClientCode(client.getClientCode());
                order.setTourCode(t.getTourCode());
                return idbHandlerOrder.addObj(order);
            }
        }
        return false;
    }
}