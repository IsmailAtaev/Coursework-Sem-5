package controller;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
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
                connect.writeLine(makeOrder(Integer.parseInt(idOrder), ticket));
                break;
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
                //TODO i have more db handler and i can getter witch one ticket order client tour more )
            }
            case "viewTicket": {
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
        ArrayList<Order> orderArrayList = (ArrayList<Order>) idbHandlerOrder.getList().clone();
        int i = 0;
        boolean flagAddTicket;
        for (Order o : orderArrayList) {
            if (idOrder == o.getId()) {
                ticket.setUserCode(o.getClientCode());
                flagAddTicket = idbHandlerTicket.addObj(ticket);
                if (flagAddTicket) {
                    return "CreateTicket";
                } else {
                    return "NoCreateTicket";
                }
            }
            ++i;
        }
        if (i > 0 && i <= orderArrayList.size()) {
            return "NoIdOrder";
        } else if (i == 0) {
            return "NoOrder";
        }
        return "false";
    }
}