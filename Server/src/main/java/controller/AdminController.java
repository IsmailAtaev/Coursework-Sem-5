package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
import com.example.model.tour.Tour;
import model.bd.dbhclient.DBHClient;
import model.bd.dbhorder.DBHOrder;
import model.bd.dbhticket.DBHTicket;
import model.bd.dbhtour.DBHTour;
import model.bd.idbhandler.IDBHandler;
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
                boolean flag = makeOrderrr(Integer.parseInt(idOrder), ticket);
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
                ///Client client = (Client) connect.readObj();
                //boolean flag = idbHandler.editObj(connect.readObj());
                if (idbHandler.editObj(connect.readObj())) {
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
            case "deleteTour": {
                boolean flagTour = idbHandlerTour.deleteObj(connect.readObj());
                if (flagTour) {
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
            e.getMessage();
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


        }
    }

    /**
     * 1-й исход работы) Возврашаем Order из бд прохотимся по списку заказов, и если есть такоей id,
     * то вызываем запрос на добавление, если билет создан успешно, то возврашаем (CreateTicket),
     * иначе (NoCreateTicket).
     * <p>
     * 2-й исход работы) Создаём счетчик int i
     * Возврашаем Order из бд прохотимся по списку заказов, если i равен нулю, то (NoOrder)
     * если i больше и менше или равно Order.size то,(NoIdOrder) нету такого заказа.
     *
     * @param idOrder
     * @param ticket
     */

    private String makeOrder(int idOrder, Ticket ticket) {

        int i = 0;
        boolean flag = false;
        boolean flagAddTicket = false;
        boolean flagClient = false;
        boolean flagTour = false;
        boolean flagCT = false; // client and tour
        ArrayList<Tour> tourArrayList = (ArrayList<Tour>) idbHandlerTour.getList().clone();
        ArrayList<Order> orderArrayList = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        ArrayList<Client> clientArrayList = (ArrayList<Client>) idbHandler.getList().clone();


        System.out.println(" i am idOrder " + idOrder);
        for (Order a : orderArrayList) {
            System.out.println(a.toString());
        }

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
                            flagCT = true;
                            i = 0;
                            break;
                        }
                    }
                } else {
                    flagCT = false;
                    break;
                }
            }
            flag = true;
            ++i;
        }

        if (flagCT == false && flagClient == false || flagTour == false) {
            return "NoClientOrTour";
        } else if (flagAddTicket == true) {
            return "CreateTicket";
        } else if (flag == false) {
            return "NoOrder";
        } else if (i > 0 && i <= orderArrayList.size()) {
            return "NoIdOrder";
        } else {
            return "NoCreateTicket";
        }
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

    private boolean makeOrderrr(int idOrder, Ticket ticket) {

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
}