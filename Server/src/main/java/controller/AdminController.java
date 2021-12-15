package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
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

public class AdminController implements IController {

    public Connect connect = ServerController.connect;
    private IDBHandler idbHandler = new DBHClient();
    private IDBHandler idbHandlerTour = new DBHTour();
    private IDBHandler idbHandlerOrder = new DBHOrder();
    private IDBHandler idbHandlerTicket = new DBHTicket();

    @Override
    public void saveDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "addUser": {
                boolean flagAddClient = idbHandler.addObj(connect.readObj());
                if (flagAddClient) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "addTour": {
                boolean flagAddTour = idbHandlerTour.addObj(connect.readObj());
                if (flagAddTour) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "addTicket": {
                String idOrder = connect.readLine();
                Ticket ticket = (Ticket) connect.readObj();
                boolean flag = makeOrder(Integer.parseInt(idOrder), ticket);
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
    public void editDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "editUser": {
                if (idbHandler.editObj(connect.readObj())) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "editTour": {
                Object ob = connect.readObj();
                if (idbHandlerTour.editObj(ob)) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
        }
    }

    @Override
    public void deleteDate(String msg) throws IOException, ClassNotFoundException {
        switch (msg) {
            case "deleteUser": {
                String login = connect.readLine();
                String pass = connect.readLine();
                String clientCode = connect.readLine();
                Delete delete = new Delete();
                if (delete.deleteUser(login, pass, clientCode, idbHandler.getList())) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "deleteTour": {
                String idTour = connect.readLine();
                Delete delete = new Delete();
                if (delete.deleteTour(Integer.parseInt(idTour), idbHandlerTour.getList())) {
                    connect.writeLine("true");
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "ticketDelete": {
                String idTicket = connect.readLine();
                Delete delete = new Delete();
                if (delete.deleteTicket(Integer.parseInt(idTicket), idbHandlerTicket.getList())) {
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
                connect.writeObjList(idbHandlerOrder.getList());
                break;
            }
        }
    }

    @Override
    public void start() {
        System.out.println("start admin controller");
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
                    case "search": {
                        String req = connect.readLine();
                        this.search(req);
                        req = null;
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
                        new MyException("поличичли что-то не то  то client controller ");
                        break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            new MyException(e);
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
                for (Client c : list) {
                    if (fio.equals(c.getFIO()) && login.equals(c.getLogin()) && pass.equals(c.getPassword())) {
                        ++counter;
                        connect.writeLine("true");
                        connect.writeObj(c);
                    }
                }
                if (counter == 0) {
                    connect.writeLine("false");
                }
                break;
            }
            case "searchClient": {
                final String clientCode = connect.readLine();
                if (checkOrderClientCode(clientCode)) {
                    ArrayList<Object> objects = getSearchOrders(clientCode);
                    connect.writeLine("true");
                    connect.writeObjList(objects);
                } else {
                    connect.writeLine("false");
                }
                break;
            }
            case "searchTour": {
                final String tourCode = connect.readLine();
                if (checkOrderTourCode(tourCode)) {
                    ArrayList<Object> objects = getSearchOrdersTour(tourCode);
                    connect.writeLine("true");
                    connect.writeObjList(objects);
                } else {
                    connect.writeLine("false");
                }
                break;
            }
        }
    }

    private boolean checkOrderTourCode(String tourCode) {
        ArrayList<Order> orders = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        for (Order o : orders) {
            if (tourCode.equals(o.getTourCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkOrderClientCode(String clientCode) {
        ArrayList<Order> orders = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        for (Order o : orders) {
            if (clientCode.equals(o.getClientCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkClient(String clientCode,ArrayList<Client> clients) {
        for (Client c : clients) {
            if (clientCode.equals(c.getClientCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTour(String tourCode, ArrayList<Tour> tours) {
        for (Tour t : tours) {
            if (tourCode.equals(t.getTourCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTicket(int id) {
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) idbHandlerTicket.getList().clone();
        for (Ticket t : tickets) {
            if (id == t.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 1-й исход работы) Возврашаем Order из бд прохотимся по списку заказов, и если есть такоей id,
     * то вызываем запрос на добавление, если билет создан успешно, то возврашаем (CreateTicket),
     * иначе (NoCreateTicket).
     * <p>
     * 2-й исход работы) Создаём счетчик int i
     * Возврашаем Order из бд прохотимся по списку заказов, если i равен нулю, то (NoOrder)
     * если i больше и менше или равно Order.size то,(NoIdOrder) нету такого заказа.
     * @param idOrder
     * @param ticket
     */
    private boolean makeOrder(int idOrder, Ticket ticket) {
        boolean flagAddTicket;
        boolean flagClient;
        boolean flagTour;

        ArrayList<Tour> tourArrayList = (ArrayList<Tour>) idbHandlerTour.getList().clone();
        ArrayList<Order> orderArrayList = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        ArrayList<Client> clientArrayList = (ArrayList<Client>) idbHandler.getList().clone();

        for (Order o : orderArrayList) {
            if (idOrder == o.getId()) {
                flagClient = checkClient(o.getClientCode(), clientArrayList);
                flagTour = checkTour(o.getTourCode(), tourArrayList);
                if (flagClient == true && flagTour == true) {
                    ticket.setUserCode(o.getClientCode());
                    for (Tour t : tourArrayList) {
                        if (o.getTourCode().equals(t.getTourCode())) {
                            ticket.setDepartureData(t.getTourDate());
                            ticket.setArrivalPoint(t.getCountryName() + "-" + t.getCityName());
                            flagAddTicket = idbHandlerTicket.addObj(ticket);
                            boolean ff = idbHandlerOrder.deleteObj(o);
                            System.out.println("i am delete order flag -> " + ff);
                            return flagAddTicket;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private ArrayList<Object> getSearchOrders(String clientCode) {
        ArrayList<Order> orders = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        ArrayList<Object> objects = new ArrayList<>();
        for (Order o : orders) {
            if (clientCode.equals(o.getClientCode())) {
                objects.add(o);
            }
        }
        return objects;
    }

    private ArrayList<Object> getSearchOrdersTour(String tourCode) {
        ArrayList<Order> orders = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        ArrayList<Object> objects = new ArrayList<>();
        for (Order o : orders) {
            if (tourCode.equals(o.getTourCode())) {
                objects.add(o);
            }
        }
        return objects;
    }

}