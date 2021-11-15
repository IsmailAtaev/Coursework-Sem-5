package model.bd.dbhticket;

import com.example.model.myexception.MyException;
import com.example.model.ticket.Ticket;
import model.bd.idbhandler.IDBHandler;
import model.configs.ticketBD.ConstTicket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author  Ataeyv I.M. (ataewisma@gmail.com)
 * */

public class DBHTicket implements IDBHandler {
    @Override
    public boolean addObj(Object obj) {
        try {
            Ticket ticket = (Ticket) obj;
            String insert = "INSERT INTO " + ConstTicket.TICKET_TABLE + "("
                    + ConstTicket.TICKET_CODE + ","
                    + ConstTicket.TICKET_USER_CODE + ","
                    + ConstTicket.TICKET_TRANSPORT_TYPE + ","
                    + ConstTicket.TICKET_DEPARTURE_POINT + ","
                    + ConstTicket.TICKET_ARRIVAL_POINT + ","
                    + ConstTicket.TICKET_DEPARTURE_DATA + ")" + "VALUES(?,?,?,?,?,?)";

            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, ticket.getTicketCode());
            prSt.setString(2, ticket.getUserCode());
            prSt.setString(3, ticket.getTransportType());
            prSt.setString(4, ticket.getDeparturePoint());
            prSt.setString(5, ticket.getArrivalPoint());
            prSt.setString(6, ticket.getDepartureData());

            prSt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            new MyException(e);
            return false;
        }
        return true;

    }




    @Override
    public ArrayList<Object> getList() {
        return null;
    }

    @Override
    public boolean deleteObj(Object obj) {
        return false;
    }

    @Override
    public boolean editObj(Object obj) {
        return false;
    }
}