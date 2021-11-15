package model.bd.dbhticket;

import com.example.model.ticket.Ticket;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBHTicketTest {

    @Test
    void addObj() {
        IDBHandler idbHandler = new DBHTicket();
        Ticket ticket = new Ticket();
        ticket.setTicketCode("B1");
        ticket.setUserCode("C1");
        ticket.setTransportType("AirLine");
        ticket.setDeparturePoint("Belarus-Minsk");
        ticket.setArrivalPoint("Turkish-Istanbul");
        ticket.setDepartureData("13-11-2021");
        boolean actually = idbHandler.addObj(ticket);
        Assertions.assertEquals(true, actually);
    }

    @Test
    void getList() {
    }

    @Test
    void deleteObj() {
    }

    @Test
    void editObj() {
    }
}