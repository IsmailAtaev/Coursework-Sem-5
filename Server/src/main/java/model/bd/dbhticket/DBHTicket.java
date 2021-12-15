package model.bd.dbhticket;

import com.example.model.myexception.MyException;
import com.example.model.ticket.Ticket;
import model.bd.idbhandler.IDBHandler;
import model.configs.ticketBD.ConstTicket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            String select = "SELECT * FROM " + ConstTicket.TICKET_TABLE;
            Statement statement = getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery(select);
            while (resSet.next()) {
                Ticket t = new Ticket();
                t.setId(resSet.getInt(1));
                t.setTicketCode(resSet.getString(2));
                t.setUserCode(resSet.getString(3));
                t.setTransportType(resSet.getString(4));
                t.setDeparturePoint(resSet.getString(5));
                t.setArrivalPoint(resSet.getString(6));
                t.setDepartureData(resSet.getString(7));
                arrayList.add(t);
            }
        } catch (SQLException e) {
            new MyException(e);
        } catch (ClassNotFoundException e) {
            new MyException(e);
        }
        return arrayList;
    }

    @Override
    public boolean deleteObj(Object obj) {
        try {
            Ticket ticket = (Ticket) obj;
            String DELETE = "DELETE FROM " + ConstTicket.TICKET_TABLE + " WHERE " + ConstTicket.TICKET_ID + "='" + ticket.getId() + "'; ";
            PreparedStatement preparedStatementDelete = getDbConnection().prepareStatement(DELETE);
            preparedStatementDelete.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            new MyException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean editObj(Object obj) {
        return false;
    }
}