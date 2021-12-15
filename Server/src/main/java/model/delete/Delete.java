package model.delete;

import com.example.model.client.Client;
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
import com.example.model.tour.Tour;
import model.bd.dbhclient.DBHClient;
import model.bd.dbhorder.DBHOrder;
import model.bd.dbhticket.DBHTicket;
import model.bd.dbhtour.DBHTour;
import model.bd.idbhandler.IDBHandler;
import java.util.ArrayList;

public class Delete {

    private IDBHandler idbHandlerClient = new DBHClient();
    private IDBHandler idbHandlerTour = new DBHTour();
    private IDBHandler idbHandlerOrder = new DBHOrder();
    private IDBHandler idbHandlerTicket = new DBHTicket();

    public boolean deleteOrder(int id, ArrayList<Object> objects) {
        ArrayList<Order> orders = (ArrayList<Order>) objects.clone();
        for (Order o : orders) {
            if (id == o.getId()) {
                return idbHandlerOrder.deleteObj(o);
            }
        }
        return false;
    }

    public boolean deleteTour(int id, ArrayList<Object> objects) {
        ArrayList<Tour> tours = (ArrayList<Tour>) objects.clone();
        for (Tour t : tours) {
            if (id == t.getId()) {
                return idbHandlerTour.deleteObj(t);
            }
        }
        return false;
    }

    public boolean deleteUser(String login, String pass, String clientCode, ArrayList<Object> objects) {
        ArrayList<Client> clients = (ArrayList<Client>) objects.clone();
        for (Client c : clients) {
            if (login.equals(c.getLogin()) && pass.equals(c.getPassword()) && clientCode.equals(c.getClientCode())) {
                return idbHandlerClient.deleteObj(c);
            }
        }
        return false;
    }

    public boolean deleteTicket(int id, ArrayList<Object> objects) {
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) objects.clone();
        for (Ticket t : tickets) {
            if (id == t.getId()) {
                return idbHandlerTicket.deleteObj(t);
            }
        }
        return false;
    }
}
