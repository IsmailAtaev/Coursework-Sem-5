package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.tour.Tour;
import controller.factory.IController;
import model.bd.dbhclient.DBHClient;
import model.bd.dbhorder.DBHOrder;
import model.bd.dbhticket.DBHTicket;
import model.bd.dbhtour.DBHTour;
import model.bd.idbhandler.IDBHandler;
import model.delete.Delete;
import java.io.IOException;
import java.util.ArrayList;

public class ClientController implements IController {

    public Connect connect = ServerController.connect;
    private IDBHandler idbHandler = new DBHClient();
    private IDBHandler idbHandlerTour = new DBHTour();
    private IDBHandler idbHandlerOrder = new DBHOrder();
    private IDBHandler idbHandlerTicket = new DBHTicket();

    @Override
    public void saveDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "orderTour": {
                String tourCode = connect.readLine();
                Client client = (Client) connect.readObj();
                System.out.println("order client -> " + client);
                if (makeOrderTour(tourCode, client, idbHandlerTour.getList())) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            default: {
                new MyException("клиент сервер заказ тура case default ");
                break;
            }
        }
    }

    @Override
    public void editDate(String msg) throws IOException, ClassNotFoundException {
    }

    @Override
    public void deleteDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "deleteOrder": {
                String id = connect.readLine();
                Delete delete = new Delete();
                if (delete.deleteOrder(Integer.parseInt(id), idbHandlerOrder.getList())) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            default: {
                new MyException("client server do not response deleteOrder case default");
                break;
            }
        }
    }

    @Override
    public void getDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "viewUser": {
                connect.writeObjList(idbHandler.getList());
                break;
            }
            case "viewTicket": {
                connect.writeObjList(idbHandlerTicket.getList());
                break;
            }
            case "viewTour": {
                connect.writeObjList(idbHandlerTour.getList());
                break;
            }
            case "viewOrder": {
                final String clientCode = connect.readLine();
                boolean flagOrder = checkOrderClient(clientCode, idbHandlerOrder.getList());

                if (flagOrder) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }

                if (flagOrder) {
                    ArrayList<Object> objects = getClientOrder(clientCode, idbHandlerOrder.getList());
                    connect.writeObjList(objects);
                }
                break;
            }
            default: {
                new MyException("client server view default case ");
                break;
            }
        }
    }


    @Override
    public void start() {
        System.out.println("start client controller");
        try {
            while (true) {
                String msg = connect.readLine();
                switch (msg) {
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
                    case "edit": {
                        this.editDate(connect.readLine());
                        break;
                    }
                    case "close": {
                        connect.close();
                        return;
                    }
                    default: {
                        msg = null;
                        new MyException("поличичли что-то не то client controller ");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            new MyException(e);
        } catch (ClassNotFoundException e) {
            new MyException(e);
        }
    }


    private boolean makeOrderTour(String tourCode, Client client, ArrayList<Object> objects) {
        ArrayList<Tour> tours = (ArrayList<Tour>) idbHandlerTour.getList().clone();
        if (checkTour(tourCode, tours)) {
            for (Tour t : tours) {
                if (tourCode.equals(t.getTourCode())) {
                    Order order = new Order();
                    order.setClientCode(client.getClientCode());
                    order.setTourCode(t.getTourCode());
                    return idbHandlerOrder.addObj(order);
                }
            }
        }
        return false;
    }

    private ArrayList<Object> getClientOrder(String clientCode, ArrayList<Object> objects) {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Order> orderArrayList = (ArrayList<Order>) objects.clone();
        for (Order o : orderArrayList) {
            if (clientCode.equals(o.getClientCode())) {
                orders.add(o);
            }
        }
        return (ArrayList<Object>) orders.clone();
    }

    private boolean checkTour(String tourCode, ArrayList<Tour> tours) {
        for (Tour t : tours) {
            if (tourCode.equals(t.getTourCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkOrderClient(String clientCode, ArrayList<Object> objects) {
        ArrayList<Order> orders = (ArrayList<Order>) objects.clone();
        for (Order o : orders) {
            if (clientCode.equals(o.getClientCode())) {
                return true;
            }
        }
        return false;
    }
}